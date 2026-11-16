<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Logout Successful</title>
    <style>
        body {
            background: linear-gradient(to right, #dfe9f3, #ffffff);
            font-family: 'Poppins', sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }
        .logout-container {
            background-color: white;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            text-align: center;
            animation: fadeIn 1s ease;
        }
        .logout-container h2 {
            color: #4CAF50;
            margin-bottom: 10px;
        }
        .logout-container p {
            margin-bottom: 20px;
        }
        .logout-container a {
            text-decoration: none;
            color: white;
            background-color: #4CAF50;
            padding: 10px 25px;
            border-radius: 30px;
            transition: 0.3s;
        }
        .logout-container a:hover {
            background-color: #45a049;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-30px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
    <div class="logout-container">
        <h2>Logged Out Successfully</h2>
        <p>You have been logged out of your session.</p>
        <a href="StudentLogin.jsp">Login Again</a>
    </div>
</body>
</html>
