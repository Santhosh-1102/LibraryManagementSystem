package servlet.book;

import java.io.IOException;

import facotry.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import userEntity.Books;

@WebServlet("/UpdateBookByIsbnServlet")
public class UpdateBookMain extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String isbn = req.getParameter("isbn");
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String category = req.getParameter("category");
		String description = req.getParameter("description");
		String quantityStr = req.getParameter("quantity");

		if (isbn == null || isbn.trim().isEmpty()) {
			resp.sendRedirect("UpdateBook.jsp?error=InvalidISBN");
			return;
		}

		int quantity = 1;
		try {
			if (quantityStr != null && !quantityStr.trim().isEmpty()) {
				quantity = Integer.parseInt(quantityStr.trim());
			}
		} catch (NumberFormatException e) {
			quantity = 1;
		}

		EntityManager manager = null;
		EntityTransaction t = null;

		try {
			manager = Manager.getEntityManager();
			t = manager.getTransaction();

			t.begin();
			Books book = manager.find(Books.class, isbn.trim());
			if (book != null) {
				if (title != null && !title.trim().isEmpty()) book.setTitle(title.trim());
				if (author != null && !author.trim().isEmpty()) book.setAuthor(author.trim());
				if (category != null && !category.trim().isEmpty()) book.setCategory(category.trim());
				if (description != null) book.setDescription(description.trim());
				book.setQuantity(quantity);

				t.commit();
				resp.sendRedirect("viewBooks?success=BookUpdated");
			} else {
				if (t.isActive()) t.rollback();
				resp.sendRedirect("UpdateBook.jsp?error=BookNotFound");
			}
		} catch (Exception e) {
			if (t != null && t.isActive()) {
				t.rollback();
			}
			e.printStackTrace();
			resp.sendRedirect("UpdateBook.jsp?error=" + java.net.URLEncoder.encode(e.getMessage() != null ? e.getMessage() : "Error", "UTF-8"));
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}
	}
}
