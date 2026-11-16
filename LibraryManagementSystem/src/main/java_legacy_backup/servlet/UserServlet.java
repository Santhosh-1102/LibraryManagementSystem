package servlet;

import java.io.IOException;

import facotry.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import userEntity.Student;

@WebServlet("/RegistrationServlet")
public class UserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("user");
		String id = req.getParameter("id");
		String ageStr = req.getParameter("age");
		String email = req.getParameter("email");
		String phno = req.getParameter("phno");
		String pass = req.getParameter("pass");

		if (name == null || id == null || pass == null || name.trim().isEmpty() || id.trim().isEmpty() || pass.trim().isEmpty()) {
			req.setAttribute("error", "All required fields must be filled.");
			RequestDispatcher dispatcher = req.getRequestDispatcher("StudentRegistration.jsp");
			dispatcher.forward(req, resp);
			return;
		}

		Integer age = 18;
		try {
			if (ageStr != null && !ageStr.trim().isEmpty()) {
				age = Integer.parseInt(ageStr.trim());
			}
		} catch (NumberFormatException e) {
			age = 18;
		}

		EntityManager manager = null;
		EntityTransaction t = null;

		try {
			manager = Manager.getEntityManager();
			t = manager.getTransaction();

			Student existing = manager.find(Student.class, id.trim());
			if (existing != null) {
				req.setAttribute("error", "Student ID already exists. Please choose a different ID or Login.");
				RequestDispatcher dispatcher = req.getRequestDispatcher("StudentRegistration.jsp");
				dispatcher.forward(req, resp);
				return;
			}

			Student s = new Student();
			s.setUserId(id.trim());
			s.setUserName(name.trim());
			s.setPhone(phno != null ? phno.trim() : "");
			s.setPass(pass);
			s.setAge(age);
			s.setEmail(email != null ? email.trim() : "");

			t.begin();
			manager.persist(s);
			t.commit();

			req.setAttribute("message", "Registered successfully! Please login with your Student ID.");
			RequestDispatcher dispatcher = req.getRequestDispatcher("StudentLogin.jsp");
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			if (t != null && t.isActive()) {
				t.rollback();
			}
			e.printStackTrace();
			req.setAttribute("error", "Registration failed: " + e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("StudentRegistration.jsp");
			dispatcher.forward(req, resp);
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}
}
