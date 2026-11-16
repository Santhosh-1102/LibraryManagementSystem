@echo off
title LibraryHub Server
echo ====================================================
echo       Starting LibraryHub Enterprise System
echo ====================================================
echo.

:: 1. Start PostgreSQL Database if not running
echo [1/3] Checking Database Engine...
netstat -ano | findstr :5433 >nul
if %errorlevel% neq 0 (
    echo Starting PostgreSQL on port 5433...
    start "" /b "C:\Program Files\PostgreSQL\18\bin\pg_ctl.exe" -D "d:\LibraryManagementSystem\pgdata" -o "-p 5433" start
    timeout /t 3 /nobreak >nul
) else (
    echo PostgreSQL is already running on port 5433.
)

:: 2. Start Spring Boot Application
echo [2/3] Launching Spring Boot 3 & Security 6 Application...
cd /d "d:\LibraryManagementSystem\LibraryManagementSystem"
start "" "javaw" -jar "target\LibraryManagementSystem-1.0.0.jar"

echo [3/3] Waiting for server initialization...
timeout /t 6 /nobreak >nul

:: 3. Open in Browser
echo Opening LibraryHub in default browser...
start http://localhost:8085/

echo.
echo ====================================================
echo   LibraryHub is RUNNING at http://localhost:8085/
echo   Admin:   admin / admin123
echo   Student: student1 / student123
echo ====================================================
echo.
pause
