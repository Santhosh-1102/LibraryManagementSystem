package Library.Service;

import org.springframework.stereotype.Service;

import Library.Entity.Student;
import Library.Entity.StudentLogin;

@Service
public interface StudentService {

	Student Registration(Student student);

	Student StudentValidate(StudentLogin studentlogin);

	Boolean existsByUsername(String username);
}
