<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Admin Dashboard</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <style>
    body {
      margin: 0;
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(to right, #f4f7f9, #e2ecf2);
    }

    header {
      background: #6ea8fe;
      color: white;
      padding: 20px;
      text-align: center;
      font-size: 26px;
      letter-spacing: 1px;
      box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    }

    .container {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
      gap: 25px;
      padding: 40px;
      max-width: 1100px;
      margin: auto;
    }

    .card {
      background-color: rgba(255, 255, 255, 0.9);
      border-radius: 16px;
      padding: 30px 20px;
      text-align: center;
      box-shadow: 0 6px 18px rgba(0, 0, 0, 0.07);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
      animation: fadeIn 0.6s ease-in-out;
    }

    .card:hover {
      transform: translateY(-6px);
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
    }

    .card h3 {
      margin-bottom: 15px;
      font-weight: 600;
      font-size: 20px;
    }

    .card button, .card a {
      background-color: #6ea8fe;
      color: white;
      border: none;
      padding: 10px 16px;
      border-radius: 10px;
      font-size: 15px;
      cursor: pointer;
      text-decoration: none;
      display: inline-block;
      transition: background 0.3s ease;
    }

    .card button:hover, .card a:hover {
      background-color: #5e97f1;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }
  </style>
</head>
<body>

  <header>Admin Dashboard</header>

  <div class="container">

    <div class="card">
      <h3>Add Book</h3>
      <form action="addBook" method="get">
        <button type="submit">Go</button>
      </form>
    </div>

    <div class="card">
      <h3>Update Book</h3>
      <a href="UpdateBook.jsp">Go</a>
    </div>

    <div class="card">
      <h3>Delete Book</h3>
      <a href="DeleteBook.jsp">Go</a>
    </div>

    <div class="card">
      <h3>View All Books</h3>
      <form action="viewBooks" method="get">
        <button type="submit">Go</button>
      </form>
    </div>

    <div class="card">
      <h3>View Students</h3>
      <form action="viewStudents" method="get">
        <button type="submit">Go</button>
      </form>
    </div>

    <div class="card">
      <h3>Logout</h3>
      <a href="logout"><button type="submit">Logout</button></a>
    </div>

  </div>

</body>
</html>
