# Smart Incident Monitor

Full-stack demo (Spring Boot + Vue 3) para gestionar incidencias con comentarios y sugerencias de IA (Ollama). Arranca todo con Docker.

## 🚀 Arranque rápido

## Requisitos
- Docker Desktop / Docker Engine
- Puertos libres: 80 (web), 8080 (api si la expones), 11434 (ollama), 5432 (db)

## 1) Clonar y crear `.env`

git clone <TU_REPO>.git
cd <TU_REPO>

### Linux/macOS
./scripts/gen-env.sh   

### Windows PowerShell:
scripts\gen-env.ps1

## 2)  Levantar servicios
docker compose build
docker compose up -d

El primer arranque descargará el modelo IA ligero (qwen2:1.5b). Tarda unos minutos.

## 3) Probar
Frontend: http://localhost
Healthchecker API: http://localhost/api/v1/health

### Credenciales -> agonzalez / Secret123!



