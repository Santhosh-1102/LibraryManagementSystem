<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Student Registration</title>
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
      padding: 30px 25px; width: 95%; max-width: 450px;
      animation: fadeIn 0.7s ease;
    }
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }
    form { display: flex; flex-direction: column; }
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
  </style>
</head>
<body>
  <div class="container">
    <form action="RegistrationServlet" method="get">
      <h2 style="text-align:center; margin-bottom:20px;">Student Registration</h2>
      
      <%-- Optional: Show error/success message from request attribute --%>
      <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
      %>
        <p style="color: red; text-align:center;"><%= message %></p>
      <% } %>

      <div class="input-group"><input type="text" name="user" placeholder="Full Name" required /></div>
      <div class="input-group"><input type="text" name="id" placeholder="Student ID" required /></div>
      <div class="input-group"><input type="number" name="age" placeholder="Age" required /></div>
      <div class="input-group"><input type="email" name="email" placeholder="Email" required /></div>
      <div class="input-group"><input type="text" name="phno" placeholder="Phone Number" required /></div>
      <div class="input-group">
        <input type="password" id="regPass" name="pass" placeholder="Password" required />
        <i class="fas fa-eye" onclick="togglePassword('regPass', this)"></i>
      </div>
      <button type="submit">Register</button>
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
