package servlet;

import java.io.IOException;

import facotry.Manager;
import jakarta.persistence.EntityManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import userEntity.Student;

@WebServlet("/StudentLoginServlet")
public class Validation extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String pass = req.getParameter("pass");

		if (id == null || pass == null || id.trim().isEmpty() || pass.trim().isEmpty()) {
			req.setAttribute("error", "Student ID and Password are required.");
			RequestDispatcher dispatcher = req.getRequestDispatcher("StudentLogin.jsp");
			dispatcher.forward(req, resp);
			return;
		}

		EntityManager manager = null;

		try {
			manager = Manager.getEntityManager();
			Student student = manager.find(Student.class, id.trim());

			if (student != null && student.getPass() != null && student.getPass().equals(pass)) {
				HttpSession session = req.getSession();
				session.setAttribute("studentId", student.getUserId());
				session.setAttribute("studentName", student.getUserName());
				session.setAttribute("studentEmail", student.getEmail());
				session.setAttribute("studentPhone", student.getPhone());
				session.setAttribute("userRole", "student");
				resp.sendRedirect("studentHome");
			} else {
				req.setAttribute("error", "Invalid Student ID or Password");
				RequestDispatcher dispatcher = req.getRequestDispatcher("StudentLogin.jsp");
				dispatcher.forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Server error during login: " + e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("StudentLogin.jsp");
			dispatcher.forward(req, resp);
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}
}
