package Library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Library.Entity.Books;

@Repository
public interface BookRepo extends JpaRepository<Books, Integer> {

}
