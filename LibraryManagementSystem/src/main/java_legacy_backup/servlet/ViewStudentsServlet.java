package servlet;

import java.io.IOException;
import java.util.List;

import facotry.Manager;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import userEntity.Student;

@WebServlet("/viewStudents")
public class ViewStudentsServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManager em = new Manager().getManager().createEntityManager();

		List<Student> students = em.createQuery("SELECT s FROM Student s", Student.class).getResultList();

		// Force load borrows for each student
		for (Student s : students) {
			s.getBorrows().size(); // triggers Hibernate to load borrows
		}

		request.setAttribute("students", students);
		request.getRequestDispatcher("viewStudents.jsp").forward(request, response);

		em.close();
	}
}
