# CodeLens — Real-time AI Code Review IDE

A production-grade AI-powered code review system built with microservices architecture.

## Tech Stack
- Java Spring Boot — WebSocket backend with STOMP
- Python + CodeBERT — Microsoft AI model for code analysis
- React + Monaco Editor — VS Code style frontend
- Redis — Cache-aside pattern for ML inference
- PostgreSQL — Review history persistence
- Docker Compose — Full containerization

## Run Locally
Open Docker Desktop, then:
cd Desktop
docker-compose up

Open http://localhost:3000

## Architecture
3 microservices + Redis + PostgreSQL all containerized with Docker Compose.
