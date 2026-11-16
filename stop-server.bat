@echo off
title Stop LibraryHub Server
echo Stopping Spring Boot Application...
taskkill /f /im javaw.exe 2>nul
echo Stopping PostgreSQL Database...
"C:\Program Files\PostgreSQL\18\bin\pg_ctl.exe" -D "d:\LibraryManagementSystem\pgdata" stop
echo Server stopped successfully.
pause
