FROM python:3.11-slim
WORKDIR /app
COPY app.py .
RUN pip install flask transformers torch requests --no-cache-dir
EXPOSE 5001
CMD ["python", "app.py"]
