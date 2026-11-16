package servlet.book;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import facotry.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import userEntity.Books;

@WebServlet("/AddBookServlet")
@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	maxFileSize = 1024 * 1024 * 10,      // 10 MB
	maxRequestSize = 1024 * 1024 * 15   // 15 MB
)
public class AddBook extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String isbn = req.getParameter("isbn");
		String category = req.getParameter("category");
		String description = req.getParameter("description");
		String quantityStr = req.getParameter("quantity");

		if (title == null || isbn == null || title.trim().isEmpty() || isbn.trim().isEmpty()) {
			req.setAttribute("error", "Book Title and ISBN are required.");
			req.getRequestDispatcher("AddBook.jsp").forward(req, resp);
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

		String relativeImagePath = null;
		try {
			Part filePart = req.getPart("coverImage");
			if (filePart != null && filePart.getSize() > 0) {
				String submittedName = filePart.getSubmittedFileName();
				if (submittedName != null && !submittedName.trim().isEmpty()) {
					String fileName = Paths.get(submittedName).getFileName().toString();
					// Sanitize filename and prepend timestamp
					String cleanName = System.currentTimeMillis() + "_" + fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
					String uploadPath = req.getServletContext().getRealPath("") + File.separator + "uploads";
					File uploadDir = new File(uploadPath);
					if (!uploadDir.exists()) {
						uploadDir.mkdirs();
					}

					File destFile = new File(uploadDir, cleanName);
					try (InputStream fileContent = filePart.getInputStream()) {
						Files.copy(fileContent, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					}
					relativeImagePath = "uploads/" + cleanName;
				}
			}
		} catch (Exception ex) {
			System.err.println("File upload notice: " + ex.getMessage());
		}

		EntityManager em = null;
		EntityTransaction et = null;

		try {
			em = Manager.getEntityManager();
			et = em.getTransaction();

			et.begin();
			Books existing = em.find(Books.class, isbn.trim());
			if (existing != null) {
				// Update existing book's quantity and details
				existing.setTitle(title.trim());
				if (author != null) existing.setAuthor(author.trim());
				if (category != null) existing.setCategory(category.trim());
				if (description != null) existing.setDescription(description.trim());
				existing.setQuantity(existing.getQuantity() + quantity);
				if (relativeImagePath != null) existing.setImagePath(relativeImagePath);
			} else {
				Books book = new Books();
				book.setIsbn(isbn.trim());
				book.setTitle(title.trim());
				book.setAuthor(author != null ? author.trim() : "");
				book.setCategory(category != null ? category.trim() : "General");
				book.setDescription(description != null ? description.trim() : "");
				book.setQuantity(quantity);
				book.setImagePath(relativeImagePath);
				em.persist(book);
			}
			et.commit();

			resp.sendRedirect("viewBooks?success=BookAdded");
		} catch (Exception e) {
			if (et != null && et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
			req.setAttribute("error", "Error saving book: " + e.getMessage());
			req.getRequestDispatcher("AddBook.jsp").forward(req, resp);
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}
}
