# 程序员个人简历展示网站（label-2775）

## 🛠 技术栈

- Frontend: Vite + Vue 3 + TypeScript + Element Plus
- Backend: Spring Boot 3 + Spring Data JPA + Springdoc OpenAPI
- Database: MySQL 8.0

## 📂 目录结构

- `resume/backend`：后端 Spring Boot 服务代码
- `resume/frontend`：前端 Vite + Vue 单页应用代码
- `../2775/DESIGN.md`：项目详细设计文档（数据模型、API 与 UI 说明）

## 🚀 启动指南 (How to Run)

1. 确保本机已安装并启动 Docker / Docker Compose。
2. 进入项目目录：

   ```bash
   cd label-2775
   ```

3. 一键启动前后端与数据库：

   ```bash
   docker compose up --build
   ```

4. 等待所有容器启动完成后（`db` 变为 healthy，`backend` 与 `frontend` 处于 Up 状态），在浏览器中访问前端地址查看简历页面。

> 如需后台运行可追加 `-d` 参数：`docker compose up --build -d`

## 🔗 服务地址 (Services)

- Frontend: `http://localhost:3207`
- Backend API (聚合简历接口): `http://localhost:8207/api/v1/resume`
- Backend Swagger UI: `http://localhost:8207/swagger-ui/index.html`
- Database: `localhost:3306` (user: `root` / pass: `root`)

## 🧪 测试数据

- 后端在启动时会自动插入一份较为完整的示例简历数据（多段教育背景、工作经历、项目经验与技能特长），
  方便直接通过前端页面或 `GET /api/v1/resume` 进行验证，无需手工初始化数据库。
- 如需重置为最新种子数据，可在 `label-2775` 目录执行：

  ```bash
  docker compose down -v
  docker compose up --build
  ```


