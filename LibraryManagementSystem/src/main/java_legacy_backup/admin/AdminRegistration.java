package admin;

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
import userEntity.Admin;

@WebServlet("/AdminRegisterServlet")
public class AdminRegistration extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("adminName");
		String id = req.getParameter("adminId");
		String pass = req.getParameter("adminPass");

		if (name == null || id == null || pass == null || name.trim().isEmpty() || id.trim().isEmpty() || pass.trim().isEmpty()) {
			req.setAttribute("error", "All fields are required.");
			RequestDispatcher dispatcher = req.getRequestDispatcher("AdminRegistration.jsp");
			dispatcher.forward(req, resp);
			return;
		}

		EntityManager manager = null;
		EntityTransaction t = null;

		try {
			manager = Manager.getEntityManager();
			t = manager.getTransaction();

			Admin existing = manager.find(Admin.class, id.trim());
			if (existing != null) {
				req.setAttribute("error", "Admin ID already exists. Please choose a different ID or Login.");
				RequestDispatcher dispatcher = req.getRequestDispatcher("AdminRegistration.jsp");
				dispatcher.forward(req, resp);
				return;
			}

			Admin a = new Admin();
			a.setAId(id.trim());
			a.setAname(name.trim());
			a.setPass(pass);

			t.begin();
			manager.persist(a);
			t.commit();

			req.setAttribute("message", "Registered successfully! Please login with your Admin ID.");
			RequestDispatcher dispatcher = req.getRequestDispatcher("AdminLogin.jsp");
			dispatcher.forward(req, resp);

		} catch (Exception e) {
			if (t != null && t.isActive()) {
				t.rollback();
			}
			e.printStackTrace();
			req.setAttribute("error", "Registration failed: " + e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("AdminRegistration.jsp");
			dispatcher.forward(req, resp);
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}
}
