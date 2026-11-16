<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Admin Login</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
  <style>
    * { box-sizing: border-box; font-family: 'Poppins', sans-serif; }
    body {
      margin: 0; padding: 0;
      background: linear-gradient(to right, #dfe9f3, #ffffff);
      display: flex; justify-content: center; align-items: center;
      height: 100vh;
    }
    .container {
      background: rgba(255, 255, 255, 0.4);
      backdrop-filter: blur(15px);
      border-radius: 18px;
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
      padding: 30px 25px; width: 95%; max-width: 400px;
      animation: fadeIn 0.7s ease;
    }
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }
    form { display: flex; flex-direction: column; margin-bottom: 10px; }
    .input-group {
      position: relative; margin-bottom: 18px;
    }
    input {
      width: 100%; padding: 12px 40px 12px 14px;
      border: none; border-radius: 10px;
      font-size: 15px;
      background: rgba(255, 255, 255, 0.6); color: #333;
      box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.05);
    }
    input::placeholder { color: #888; }
    .input-group i {
      position: absolute; right: 12px; top: 50%;
      transform: translateY(-50%); color: #888;
      cursor: pointer;
    }
    button {
      padding: 12px;
      background: #6ea8fe; color: white;
      font-size: 15px; font-weight: 600;
      border: none; border-radius: 10px;
      transition: background 0.3s ease;
    }
    button:hover { background: #5e9bf2; }

    .register-btn {
      background: transparent;
      color: #0077cc;
      font-weight: 600;
      border: 2px solid #0077cc;
      margin-top: 8px;
    }

    .register-btn:hover {
      background: #0077cc;
      color: #fff;
    }

    .error-msg {
      color: red;
      text-align: center;
      margin-bottom: 15px;
      font-size: 14px;
      font-weight: 500;
    }
  </style>
</head>
<body>
  <div class="container">

    <!-- Admin Login Form -->
    <form action="AdminLoginServlet" method="get">
      <h2 style="text-align:center; margin-bottom:20px;">Admin Login</h2>

      <%-- Optional: Display error message from servlet --%>
      <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
      %>
        <div class="error-msg"><%= error %></div>
      <% } %>

      <div class="input-group">
        <input type="text" name="adminId" placeholder="Admin ID" required />
      </div>
      <div class="input-group">
        <input type="password" id="adminPass" name="adminPass" placeholder="Password" required />
        <i class="fas fa-eye" onclick="togglePassword('adminPass', this)"></i>
      </div>
      <button type="submit">Login as Admin</button>
    </form>

    <!-- Admin Register Form -->
    <form action="admin_register" method="get">
      <button type="submit" class="register-btn">Register as Admin</button>
    </form>

  </div>

  <script>
    function togglePassword(inputId, icon) {
      const input = document.getElementById(inputId);
      if (input.type === "password") {
        input.type = "text";
        icon.classList.add("fa-eye-slash");
      } else {
        input.type = "password";
        icon.classList.remove("fa-eye-slash");
      }
    }
  </script>
</body>
</html>
