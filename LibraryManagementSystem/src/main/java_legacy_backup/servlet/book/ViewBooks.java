package servlet.book;

import java.io.IOException;
import java.util.List;

import facotry.Manager;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import userEntity.Books;

@WebServlet("/viewBooks")
public class ViewBooks extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query = req.getParameter("query");
		String category = req.getParameter("category");

		EntityManager manager = null;
		try {
			manager = Manager.getEntityManager();
			List<Books> bookList;

			if (query != null && !query.trim().isEmpty()) {
				String q = "%" + query.trim().toLowerCase() + "%";
				bookList = manager.createQuery(
					"FROM Books b WHERE LOWER(b.title) LIKE :q OR LOWER(b.author) LIKE :q OR LOWER(b.isbn) LIKE :q",
					Books.class
				).setParameter("q", q).getResultList();
			} else if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("All")) {
				bookList = manager.createQuery(
					"FROM Books b WHERE LOWER(b.category) = :cat",
					Books.class
				).setParameter("cat", category.trim().toLowerCase()).getResultList();
			} else {
				bookList = manager.createQuery("FROM Books ORDER BY b.title ASC", Books.class).getResultList();
			}

			req.setAttribute("BookList", bookList);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Error loading books: " + e.getMessage());
		} finally {
			if (manager != null && manager.isOpen()) {
				manager.close();
			}
		}

		req.getRequestDispatcher("ViewBook.jsp").forward(req, resp);
	}
}
