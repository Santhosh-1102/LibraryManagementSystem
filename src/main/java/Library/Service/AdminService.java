package Library.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import Library.Entity.Admin;
import Library.Entity.AdminLogin;
import Library.Entity.Student;

@Service
public interface AdminService {

	Admin Adminlogin(AdminLogin login);

	Admin AdminRegistration(Admin admin);

	List<Student> fetchAll();
}
