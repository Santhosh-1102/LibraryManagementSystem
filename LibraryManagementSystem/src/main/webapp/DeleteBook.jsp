<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Book - Library Management System</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background: linear-gradient(to right, #00c6ff, #0072ff);
            font-family: Arial, sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            color: #fff;
        }

        .container {
            background-color: rgba(255, 255, 255, 0.1);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.3);
            text-align: center;
            width: 350px;
            animation: fadeIn 0.8s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-30px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h2 {
            margin-bottom: 20px;
            color: #fff;
        }

        input[type="text"] {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: none;
            outline: none;
            margin-bottom: 20px;
            font-size: 16px;
        }

        button {
            padding: 12px 25px;
            background-color: #fff;
            color: #0072ff;
            border: none;
            border-radius: 10px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s, color 0.3s;
        }

        button:hover {
            background-color: #0072ff;
            color: #fff;
        }

        a {
            display: block;
            margin-top: 15px;
            color: #fff;
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Delete Book</h2>
        <form action="deleteBook" method="post">
            <input type="text" name="bookId" placeholder="Enter Book ISBN Number" required>
            <button type="submit">Delete Book</button>
        </form>
        <a href="AdminHome.jsp">Back to Admin Home</a>
    </div>

</body>
</html>
