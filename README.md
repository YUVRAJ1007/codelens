<div align="center">

# ⚡ CodeLens

### Real-time AI Code Review IDE

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io)
[![Python](https://img.shields.io/badge/Python-3.11-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://python.org)
[![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://reactjs.org)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://docker.com)
[![Redis](https://img.shields.io/badge/Redis-Cache-DC382D?style=for-the-badge&logo=redis&logoColor=white)](https://redis.io)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://postgresql.org)

> Paste your code and get **bugs**, **Big-O complexity**, **code smells**, and **AI fix suggestions** in under **2 seconds**.

![Status](https://img.shields.io/badge/Status-Production_Ready-brightgreen?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)

</div>

---

## 🎯 What is CodeLens?

CodeLens is a production-grade AI-powered code review IDE built with a 6-service microservices architecture. It analyzes your code in real time using Microsoft's **CodeBERT** model trained on 6 million code functions and delivers instant feedback in a VS Code-style editor.

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🔴 **Bug Detection** | Detects null pointer risks, wrong initializations, empty catch blocks |
| 📊 **Big-O Complexity** | Automatically detects O(1), O(n), O(n²) via AST analysis |
| ⚠️ **Code Smell Detection** | Flags magic numbers, long methods, single-letter variables |
| 💡 **AI Fix Suggestions** | Concrete actionable fixes powered by CodeBERT |
| ⚡ **Real-time WebSocket** | Results appear in under 2 seconds via STOMP protocol |
| 🎯 **Quality Score** | AI-powered code quality score out of 100 |
| 💾 **Review History** | Every review saved to PostgreSQL permanently |
| 🚀 **40x Cache Speedup** | Redis cache-aside pattern skips ML inference on repeat code |

---

## 🏗️ System Architecture
---

## 🛠️ Tech Stack

### Backend
- **Java 17** + **Spring Boot 3.2** — WebSocket server + REST API
- **STOMP over SockJS** — Real-time bidirectional communication
- **JavaParser** — AST-based code structure analysis

### AI Engine
- **Python 3.11** + **Flask** — ML microservice
- **Microsoft CodeBERT** — Trained on 6M code functions
- **HuggingFace Transformers** + **PyTorch** — Inference

### Frontend
- **React 18** + **Monaco Editor** — VS Code style web IDE
- **SockJS + STOMP.js** — WebSocket client

### Infrastructure
- **Redis** — Cache-aside pattern
- **PostgreSQL 15** — Review history
- **Docker Compose** — All 6 services containerized

---

## 🚀 Quick Start

```bash
# Clone the repo
git clone https://github.com/YUVRAJ1007/codelens.git
cd codelens

# Start everything
docker-compose up
```

Wait for:
Open: **http://localhost:3000**

---

## 📁 Project Structure
---

## 🔌 Service Ports

| Service | Port |
|---------|------|
| React Frontend | 3000 |
| Java Backend | 8080 |
| Python ML Service | 5001 |
| Redis | 6379 |
| PostgreSQL | 5432 |

---

## 🛠️ Troubleshooting

**Spinner stuck?**
```bash
docker exec desktop-redis-1 redis-cli FLUSHALL
```

**Stop all services**
```bash
docker-compose down
```

**Rebuild after changes**
```bash
cd backend && mvn clean package -DskipTests
cd .. && docker-compose up --build
```

---

## 🔄 How It Works
---

<div align="center">

**⚡ Built with Java · Python · React · Redis · PostgreSQL · Docker**

[![GitHub](https://img.shields.io/badge/GitHub-YUVRAJ1007-181717?style=flat-square&logo=github)](https://github.com/YUVRAJ1007)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-yuvraj1007-0077B5?style=flat-square&logo=linkedin)](https://linkedin.com/in/yuvraj1007)

*If this project helped you, give it a ⭐*

</div>
