package servlet.book;

import java.io.IOException;

import facotry.Manager;
import jakarta.persistence.EntityManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import userEntity.Books;

@WebServlet("/FindBookByIsbnServlet")
public class UpdateBook extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String isbn = req.getParameter("isbn");
		if (isbn != null) {
			isbn = isbn.trim();
		}

		EntityManager manager = null;
		try {
			manager = Manager.getEntityManager();
			Books book = (isbn != null && !isbn.isEmpty()) ? manager.find(Books.class, isbn) : null;
			if (book != null) {
				req.setAttribute("book", book);
			} else {
				req.setAttribute("notFound", "Book with ISBN '" + isbn + "' was not found.");
				req.setAttribute("not found", "Book with ISBN '" + isbn + "' was not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("notFound", "Error searching book: " + e.getMessage());
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}

		RequestDispatcher rd = req.getRequestDispatcher("UpdateBook.jsp");
		rd.forward(req, resp);
	}
}
