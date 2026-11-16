package admin;

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
import userEntity.Admin;

@WebServlet("/AdminLoginServlet")
public class AdminValidation extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String adminId = req.getParameter("adminId");
		String adminPass = req.getParameter("adminPass");

		if (adminId == null || adminPass == null || adminId.trim().isEmpty() || adminPass.trim().isEmpty()) {
			req.setAttribute("error", "Admin ID and Password are required.");
			RequestDispatcher dispatcher = req.getRequestDispatcher("AdminLogin.jsp");
			dispatcher.forward(req, resp);
			return;
		}

		EntityManager manager = null;

		try {
			manager = Manager.getEntityManager();
			Admin admin = manager.find(Admin.class, adminId.trim());

			if (admin != null && admin.getPass() != null && admin.getPass().equals(adminPass)) {
				HttpSession session = req.getSession();
				session.setAttribute("AdminId", admin.getAId());
				session.setAttribute("adminName", admin.getAname());
				session.setAttribute("userRole", "admin");
				session.setMaxInactiveInterval(60 * 60);
				resp.sendRedirect("AdminHome.jsp");
			} else {
				req.setAttribute("error", "Invalid Admin ID or Password");
				RequestDispatcher dispatcher = req.getRequestDispatcher("AdminLogin.jsp");
				dispatcher.forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Server error during login: " + e.getMessage());
			RequestDispatcher dispatcher = req.getRequestDispatcher("AdminLogin.jsp");
			dispatcher.forward(req, resp);
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}
}
