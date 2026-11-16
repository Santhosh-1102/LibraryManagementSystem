# 📚 LibraryHub - Modern Enterprise Library Management System

A state-of-the-art, secure, and responsive Library Management System modernized with **Spring Boot 3.3.5**, **Java 21**, **Spring Security 6**, **Spring Data JPA / Hibernate**, **PostgreSQL**, and a modern **Tailwind CSS + FontAwesome** frontend.

---

## 🚀 Live Hosted URL
The application is currently running and hosted live locally at:
👉 **[http://localhost:8085/](http://localhost:8085/)**

---

## 🔑 Demo Access Credentials
You can log in immediately using the one-click demo credentials provided on the login page:

| Role | Username / ID | Password | Access Rights |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin` | `admin123` | Full access to Admin Console, inventory management, student records, and borrow logs |
| **Student** | `student1` | `student123` | Student Dashboard, 1-click book borrowing, personal loan history, profile management |

*(New student accounts can also be created anytime from the **Register** page).*

---

## 🌟 Key Features & Capabilities

### 🛡️ Modern Security Architecture (Spring Security 6 & BCrypt)
- **Role-Based Access Control (RBAC)**: Distinct permissions for `ROLE_ADMIN` and `ROLE_STUDENT`.
- **Automatic Smart Redirection**: Role-based authentication success routing (Admins to `/admin/dashboard`, Students to `/student/dashboard`).
- **Cryptographic Password Hashing**: Passwords stored using industry-standard `BCryptPasswordEncoder`.
- **CSRF & Session Protection**: Built-in cross-site request forgery defense and secure session management.

### 📖 Student Experience
- **Interactive Book Catalog**: Live search across titles, authors, categories, and ISBNs with real-time stock availability badges.
- **1-Click Checkout**: Direct book borrowing with instant loan tracking and a 14-day return countdown.
- **Student Console**: Personal dashboard tracking active borrowed books, available quota (up to 5 books), and personalized book recommendations.
- **Loan History & Auditing**: Detailed timeline showing borrow dates, due dates, return timestamps, and overdue alerts.
- **One-Click Returns**: Students can return borrowed books with a single click, instantly replenishing inventory.
- **Profile Management**: View and edit student member details.

### ⚡ Administrator Command Center
- **Executive Analytics Dashboard**:
  - Live metric cards: Total Titles, Volumes in Stock, Active Loans, Overdue Borrows, Enrolled Students.
  - Interactive **Chart.js** charts: Category distribution doughnut chart and circulation bar graph.
- **Book Inventory Management**:
  - Add, edit, and delete books with image file uploads or cover URLs.
  - Quantity control with automatic available vs. total stock management.
- **Student Roster**: Directory of all enrolled students with contact info and active loan counters.
- **Master Circulation Records**: Audit trail of every borrow transaction across the institution with one-click return approval.

---

## 🛠️ Technology Stack
- **Backend**: Spring Boot 3.3.5, Spring Security 6, Spring Data JPA, Hibernate 6
- **Language**: Java 21 LTS
- **Database**: PostgreSQL 18
- **Frontend**: Thymeleaf, Tailwind CSS, FontAwesome 6, Chart.js
- **Server**: Embedded Apache Tomcat (Port 8085)

---

## ☁️ Cloud Hosting & Deployment Guide

This project is fully containerized and cloud-ready with multi-stage `Dockerfile`, `docker-compose.yml`, and `render.yaml`.

### Option A: Free Cloud Deployment on Render.com (Recommended)
1. **Push to GitHub**:
   ```bash
   git remote add origin https://github.com/<your-username>/<your-repo-name>.git
   git branch -M main
   git push -u origin main
   ```
2. **Deploy on Render**:
   - Sign up / Log into [Render.com](https://render.com) with GitHub.
   - Click **New +** -> **Blueprint**.
   - Select your repository.
   - Render will automatically read `render.yaml`, provision a free PostgreSQL database, build the Docker container, and host your site on a public HTTPS URL (e.g. `https://library-management-system.onrender.com`).

---

### Option B: Deploy on Railway.app
1. Push your repository to GitHub.
2. Go to [Railway.app](https://railway.app) and click **New Project** -> **Deploy from GitHub repo**.
3. Click **+ Add Database** -> **PostgreSQL**.
4. Railway will automatically link the database environment variables, build the Docker image, and generate a live public URL.

---

### Option C: Run Anywhere with Docker Compose
If you or anyone else wants to run both the Spring Boot app and the PostgreSQL database on any VPS (AWS EC2, DigitalOcean, Hetzner, or local machine) with a single command:
```bash
docker compose up -d
```
The entire application with database will automatically start on `http://localhost:8085`.

---

## 💻 Manual Local Commands

### Build from source:
```powershell
mvn clean package -DskipTests
```

### Run the application:
```powershell
java -jar LibraryManagementSystem\target\LibraryManagementSystem-1.0.0.jar
```
