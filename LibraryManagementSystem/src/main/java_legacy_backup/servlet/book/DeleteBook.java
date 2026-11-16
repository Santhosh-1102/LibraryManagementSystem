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

@WebServlet("/deleteBook")
public class DeleteBook extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processDelete(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processDelete(req, resp);
	}

	private void processDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isbn = request.getParameter("isbn");
		if (isbn == null || isbn.trim().isEmpty()) {
			isbn = request.getParameter("bookId"); // backwards compatibility with DeleteBook.jsp
		}

		if (isbn == null || isbn.trim().isEmpty()) {
			response.sendRedirect("viewBooks?error=InvalidISBN");
			return;
		}

		isbn = isbn.trim();
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Manager.getEntityManager();
			tx = em.getTransaction();
			tx.begin();

			Books book = em.find(Books.class, isbn);
			if (book != null) {
				em.remove(book);
				tx.commit();
				response.sendRedirect("viewBooks?success=BookDeleted");
			} else {
				if (tx.isActive()) {
					tx.rollback();
				}
				response.sendRedirect("viewBooks?error=BookNotFound");
			}

		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
			response.sendRedirect("viewBooks?error=" + java.net.URLEncoder.encode(e.getMessage() != null ? e.getMessage() : "Error", "UTF-8"));
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}
}
