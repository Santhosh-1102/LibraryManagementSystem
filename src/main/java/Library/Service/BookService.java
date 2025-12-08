package Library.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import Library.Entity.Books;

@Service
public interface BookService {

	Books addBooks(Books books);

	void deleteBook(Integer id);

	List<Books> fetchBooks();

}
