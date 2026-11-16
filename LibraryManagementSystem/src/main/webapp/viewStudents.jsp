<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="userEntity.Student, userEntity.Borrow, userEntity.Books, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Students & Borrowed Books</title>
    <style>
        table {
            width: 90%;
            border-collapse: collapse;
            margin: 20px auto;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background: #6ea8fe;
            color: white;
        }
        .home-button {
            display: block;
            width: fit-content;
            margin: 20px auto;
            padding: 10px 20px;
            background-color: #6ea8fe;
            color: white;
            text-decoration: none;
            font-weight: 600;
            border-radius: 8px;
            transition: background 0.3s ease;
        }
        .home-button:hover {
            background-color: #5e97f1;
        }
    </style>
</head>
<body>
    <h2 style="text-align:center;">Students and Borrowed Books</h2>

    <!-- ✅ Back to Home Button -->
    <a href="AdminHome.jsp" class="home-button">← Back to Admin Home</a>

    <table>
        <tr>
            <th>Student ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Borrowed Books</th>
            <th>Status</th>
        </tr>

        <%
            List<Student> students = (List<Student>) request.getAttribute("students");
            if (students != null) {
                for (Student student : students) {
        %>
        <tr>
            <td><%= student.getUserId() %></td>
            <td><%= student.getUserName() %></td>
            <td><%= student.getEmail() %></td>
            <td><%= student.getPhone() %></td>
            <td>
                <ul>
                <%
                    if (student.getBorrows() != null && !student.getBorrows().isEmpty()) {
                        for (Borrow borrow : student.getBorrows()) {
                            Books book = borrow.getBook();
                %>
                    <li><%= book.getTitle() %></li>
                <%
                        }
                    } else {
                %>
                    No books borrowed.
                <%
                    }
                %>
                </ul>
            </td>
            <td>
                <ul>
                <%
                    if (student.getBorrows() != null && !student.getBorrows().isEmpty()) {
                        for (Borrow borrow : student.getBorrows()) {
                %>
                    <li><%= borrow.getStatus() %></li>
                <%
                        }
                    } else {
                %>
                    N/A
                <%
                    }
                %>
                </ul>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
