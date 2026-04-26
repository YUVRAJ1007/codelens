from flask import Flask, request, jsonify
from transformers import AutoTokenizer, AutoModelForSequenceClassification
import torch
import re

app = Flask(__name__)

print("Loading CodeBERT model... please wait")
tokenizer = AutoTokenizer.from_pretrained("microsoft/codebert-base")
model = AutoModelForSequenceClassification.from_pretrained("microsoft/codebert-base", num_labels=2)
model.eval()
print("CodeBERT model loaded successfully!")

def detect_bugs(code):
    bugs = []
    if "int[] arr" in code and "arr == null" not in code:
        bugs.append("Missing null check for array parameter")
    if "max = 0" in code or "min = 0" in code:
        bugs.append("Initializing max/min to 0 fails for all-negative inputs — use arr[0]")
    if "/" in code and "!= 0" not in code and "denominator" in code.lower():
        bugs.append("Possible division by zero — add a zero check")
    if "catch" in code and "{}" in code.replace(" ", ""):
        bugs.append("Empty catch block detected — handle or log the exception")
    if "return null" in code:
        bugs.append("Returning null — consider Optional<> to avoid NullPointerException")
    return bugs if bugs else ["No obvious bugs detected"]

def detect_code_smells(code):
    smells = []
    lines = code.split("\n")
    if len(lines) > 20:
        smells.append(f"Long method ({len(lines)} lines) — consider breaking it down")
    magic = re.findall(r'\b(?<!\.)\d{2,}\b', code)
    if magic:
        smells.append(f"Magic numbers found: {set(magic)} — use named constants")
    loop_count = len(re.findall(r'\b(for|while)\b', code))
    if loop_count >= 2:
        smells.append("Nested loops detected — consider optimizing to reduce complexity")
    single = re.findall(r'\b(int|String|double)\s+([a-z])\b', code)
    if len(single) > 2:
        smells.append("Multiple single-letter variables — use descriptive names")
    return smells if smells else ["No code smells detected"]

def suggest_improvement(code, bugs, smells):
    suggestions = []
    if "Nested loops" in str(smells):
        suggestions.append("Replace nested loop with HashMap for O(n) lookup")
    if "null check" in str(bugs):
        suggestions.append("Add: if (arr == null || arr.length == 0) throw new IllegalArgumentException()")
    if "max/min to 0" in str(bugs):
        suggestions.append("Initialize: int max = arr[0] instead of max = 0")
    if not suggestions:
        suggestions.append("Code looks decent — add Javadoc comments and unit tests")
    return suggestions

@app.route('/analyze', methods=['POST'])
def analyze():
    data = request.json
    code = data.get('code', '')
    language = data.get('language', 'java')
    bugs = detect_bugs(code)
    smells = detect_code_smells(code)
    suggestions = suggest_improvement(code, bugs, smells)
    inputs = tokenizer(code[:512], return_tensors="pt",
                      truncation=True, padding=True, max_length=512)
    with torch.no_grad():
        outputs = model(**inputs)
        score = torch.softmax(outputs.logits, dim=1)[0][1].item()
    quality_score = round((1 - score) * 100, 1)
    return jsonify({
        "language": language,
        "quality_score": f"{quality_score}/100",
        "bugs": bugs,
        "code_smells": smells,
        "suggestions": suggestions
    })

@app.route('/health', methods=['GET'])
def health():
    return jsonify({"status": "CodeLens ML service running"})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001, debug=False)
