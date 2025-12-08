package Library.Controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Library.Binding.BookBinding;
import Library.Entity.Books;
import Library.Service.BookService;

@Controller
@RequestMapping("admin/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/addbooks")
	public String redirectAddBook(Model model) {
		model.addAttribute("book", new BookBinding());
		return "addbook";
	}

	@PostMapping("/addbooks")
	public String addBook(@ModelAttribute("book") BookBinding bookBinding, Model model) {

		Books book = new Books();
		BeanUtils.copyProperties(bookBinding, book);
		Books books = bookService.addBooks(book);
		if (books != null) {
			model.addAttribute("book", books);
			return "home";
		} else {
			model.addAttribute("error", "Books not been Added");
			return "addbook";
		}
	}

	@GetMapping("/fetchallbooks")
	public String fetchbook(Model model) {
		List<Books> fetchBooks = bookService.fetchBooks();
		if (fetchBooks != null) {
			model.addAttribute("books", fetchBooks);
			return "displaybook";
		} else {
			model.addAttribute("error", "error while fetching the books");
			return "home";
		}
	}

	@GetMapping("/deletebook/{id}")
	public String Book(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("id", id);
		return "deletebook";
	}

	@PostMapping("/delete/{id}")
	public String deleteBook(@PathVariable("id") Integer id) {
		bookService.deleteBook(id);
		return "redirect:/admin/books/fetchallbooks";
	}

	@GetMapping("/fetchstudents")
	public String getStudents() {

		return null;
	}
}
