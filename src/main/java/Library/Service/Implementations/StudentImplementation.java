package Library.Service.Implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Library.Entity.Student;
import Library.Entity.StudentLogin;
import Library.Repository.StudentRepo;
import Library.Service.StudentService;

@Component
public class StudentImplementation implements StudentService {

	@Autowired
	private StudentRepo repo;

	@Override
	public Student Registration(Student student) {
		return repo.save(student);

	}

	@Override
	public Student StudentValidate(StudentLogin studentlogin) {
		Student byUsername = repo.findByUsername(studentlogin.getUsername());

		if (byUsername != null) {
			String dbPassword = byUsername.getPassword();
			String inputPassword = studentlogin.getPassword();

			if (dbPassword != null && inputPassword != null && dbPassword.equals(inputPassword)) {
				return byUsername;
			}
		}

		return null;
	}

	@Override
	public Boolean existsByUsername(String username) {
		return repo.existsByUsername(username);
	}

}
