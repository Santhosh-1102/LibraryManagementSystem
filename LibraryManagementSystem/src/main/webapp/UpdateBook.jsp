<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="userEntity.Books" %>
<%
    Books book = (Books) request.getAttribute("book");
    String notFound = (String) request.getAttribute("notFound");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Book by ISBN</title>
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: #f0f8ff;
            padding: 40px;
        }

        .container {
            max-width: 500px;
            margin: auto;
            background: white;
            padding: 30px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
            border-radius: 10px;
        }

        h2 {
            text-align: center;
            color: #2196f3;
        }

        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 12px;
            margin: 8px 0;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        input[readonly] {
            background-color: #e0e0e0;
            cursor: not-allowed;
        }

        button {
            background-color: #2196f3;
            color: white;
            padding: 12px;
            width: 100%;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .not-found {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Search Book by ISBN</h2>
    <form method="get" action="FindBookByIsbnServlet">
        <input type="text" name="isbn" placeholder="Enter ISBN Number" required />
        <button type="submit">Search</button>
    </form>

    <% if (notFound != null) { %>
        <p class="not-found"><%= notFound %></p>
    <% } %>

    <% if (book != null) { %>
        <hr>
        <h2>Update Book</h2>
        <form method="post" action="UpdateBookByIsbnServlet">
            <input type="text" name="title" value="<%= book.getTitle() %>" required />
            <input type="text" name="author" value="<%= book.getAuthor() %>" required />
            <select name="category" required>
			    <option value="">-- Select Category --</option>
			    <option value="Fiction" <%= "Fiction".equals(book.getCategory()) ? "selected" : "" %>>Fiction</option>
			    <option value="Non-Fiction" <%= "Non-Fiction".equals(book.getCategory()) ? "selected" : "" %>>Non-Fiction</option>
			    <option value="Science" <%= "Science".equals(book.getCategory()) ? "selected" : "" %>>Science</option>
			    <option value="Technology" <%= "Technology".equals(book.getCategory()) ? "selected" : "" %>>Technology</option>
			    <option value="Education" <%= "Education".equals(book.getCategory()) ? "selected" : "" %>>Education</option>
			    <option value="Biography" <%= "Biography".equals(book.getCategory()) ? "selected" : "" %>>Biography</option>
			</select>

            <input type="number" name="quantity" value="<%= book.getQuantity() %>" required />

            <!-- ISBN is readonly (not editable) -->
            <input type="text" name="isbn" value="<%= book.getIsbn() %>" readonly />

            <button type="submit">Update Book</button>
        </form>
    <% } %>
</div>

</body>
</html>
