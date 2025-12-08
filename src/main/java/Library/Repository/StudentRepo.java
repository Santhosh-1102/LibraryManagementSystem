package Library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Library.Entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

	Student findByUsername(String username);

	Boolean existsByUsername(String usrename);
}
