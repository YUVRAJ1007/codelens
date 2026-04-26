import React from 'react';

function ReviewPanel({ review, analyzing }) {
  if (analyzing) {
    return (
      <div className="review-empty">
        <div className="spinner"></div>
        <p>AI is analyzing your code...</p>
      </div>
    );
  }

  if (!review) {
    return (
      <div className="review-empty">
        <p>Click <strong>Analyze Code</strong> to get AI review</p>
      </div>
    );
  }

  return (
    <div className="review-content">
      <div className="cards-row">
        <div className="card score-card">
          <div className="card-label">Quality Score</div>
          <div className="score-num">{review.quality_score}</div>
        </div>
        <div className="card complexity-card">
          <div className="card-label">Complexity</div>
          <div className="card-value">{review.complexity}</div>
          <div className="card-sub">{review.lines} lines</div>
        </div>
      </div>

      <div className="card bugs-card">
        <div className="card-label">🐛 Bugs Detected</div>
        <ul>{toArray(review.bugs).map((b, i) => <li key={i}>{b}</li>)}</ul>
      </div>

      <div className="card smells-card">
        <div className="card-label">⚠ Code Smells</div>
        <ul>{toArray(review.code_smells).map((s, i) => <li key={i}>{s}</li>)}</ul>
      </div>

      <div className="card suggestions-card">
        <div className="card-label">💡 AI Suggestions</div>
        <ul>{toArray(review.suggestions).map((s, i) => <li key={i}>{s}</li>)}</ul>
      </div>
    </div>
  );
}

function toArray(val) {
  if (!val) return [];
  return Array.isArray(val) ? val : [val];
}

export default ReviewPanel;
