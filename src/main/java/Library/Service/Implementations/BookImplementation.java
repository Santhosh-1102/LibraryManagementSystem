package Library.Service.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Library.Entity.Books;
import Library.Repository.BookRepo;
import Library.Service.BookService;

@Component
public class BookImplementation implements BookService {

	@Autowired
	private BookRepo bookRepo;

	@Override
	public Books addBooks(Books books) {
		return bookRepo.save(books);
	}

	@Override
	public void deleteBook(Integer id) {
		bookRepo.deleteById(id);

	}

	@Override
	public List<Books> fetchBooks() {
		return bookRepo.findAll();
	}

}
