<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="userEntity.Books" %>
<!DOCTYPE html>
<html>
<head>
  <title>View Books</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      background: linear-gradient(to right, #e0eafc, #cfdef3);
      padding: 20px 10px;
    }

    h1 {
      text-align: center;
      color: #333;
      margin-bottom: 20px;
    }

    .container {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
      gap: 15px;
      max-width: 1100px;
      margin: auto;
    }

    .card {
      background: #fff;
      padding: 10px;
      border-radius: 12px;
      box-shadow: 0 3px 8px rgba(0,0,0,0.08);
      text-align: center;
      transition: transform 0.3s ease;
      max-width: 160px; /* ✅ Shrink card width */
      margin: auto;
    }

    .card:hover {
      transform: translateY(-5px);
    }

    .card img {
      width: 90px;
      height: 130px;
      object-fit: cover;
      border-radius: 6px;
      margin-bottom: 8px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }

    .card h2 {
      color: #6c63ff;
      font-size: 14px;
      margin: 6px 0;
    }

    .card p {
      margin: 2px 0;
      font-size: 11px;
      color: #555;
      line-height: 1.2;
    }

    .back-btn {
      margin: 25px auto;
      display: block;
      width: fit-content;
      padding: 8px 18px;
      background: #6c63ff;
      color: white;
      border-radius: 8px;
      text-decoration: none;
      font-size: 14px;
    }
  </style>
</head>
<body>

<h1>📚 View All Books</h1>

<div class="container">
<%
    List<Books> books = (List<Books>) request.getAttribute("BookList");
    if (books != null) {
        for (Books book : books) {
            String imgPath = book.getImagePath();
%>
  <div class="card">
    <img src="<%= imgPath %>" alt="<%= book.getTitle() %>">
    <h2><%= book.getTitle() %></h2>
    <p><strong>ISBN:</strong> <%= book.getIsbn() %></p>
    <p><strong>Author:</strong> <%= book.getAuthor() %></p>
    <p><strong>Category:</strong> <%= book.getCategory() %></p>
    <p><strong>Quantity:</strong> <%= book.getQuantity() %></p>
  </div>
<%
        }
    } else {
%>
  <p style="text-align: center;">No books found.</p>
<%
    }
%>
</div>

<a href="AdminHome.jsp" class="back-btn">⬅ Back to Admin Home</a>

</body>
</html>
