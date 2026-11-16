<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Add Book</title>
  <meta charset="UTF-8">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(to right, #e3f2fd, #ffffff);
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }
    .form-box {
      background: white;
      padding: 30px;
      border-radius: 14px;
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
      width: 90%;
      max-width: 500px;
    }
    .form-box h2 {
      text-align: center;
      margin-bottom: 20px;
    }
    .form-box input,
    .form-box textarea,
    .form-box select {
      width: 100%;
      padding: 10px;
      margin: 10px 0;
      border-radius: 8px;
      border: 1px solid #ccc;
    }
    .form-box button {
      width: 100%;
      padding: 12px;
      background-color: #6ea8fe;
      color: white;
      border: none;
      border-radius: 10px;
      font-weight: bold;
      transition: background 0.3s ease;
    }
    .form-box button:hover {
      background-color: #5e9bf2;
    }
  </style>
</head>
<body>
  <div class="form-box">
    <h2>Add New Book</h2>
    <form action="AddBookServlet" method="post" enctype="multipart/form-data">
      <input type="text" name="title" placeholder="Book Title" required />
      <input type="text" name="author" placeholder="Author Name" required />
      <input type="text" name="isbn" placeholder="ISBN" required />
      <select name="category" required>
        <option value="">-- Select Category --</option>
        <option>Science</option>
        <option>Fiction</option>
        <option>Technology</option>
        <option>Education</option>
        <option>History</option>
      </select>
      <textarea name="description" rows="3" placeholder="Description"></textarea>
      <input type="number" name="quantity" placeholder="Quantity" required />
      <input type="file" name="coverImage" accept="image/*" required />
      <button type="submit">Add Book</button>
    </form>
  </div>
</body>
</html>
