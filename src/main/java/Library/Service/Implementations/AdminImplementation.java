package Library.Service.Implementations;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Library.Entity.Admin;
import Library.Entity.AdminLogin;
import Library.Entity.Student;
import Library.Repository.AdminRepo;
import Library.Repository.StudentRepo;
import Library.Service.AdminService;

@Component
public class AdminImplementation implements AdminService {

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private StudentRepo repo;

	@Override
	public Admin Adminlogin(AdminLogin adminLogin) {
		Admin byUsername = adminRepo.findByUsername(adminLogin.getUsername());
		if (byUsername != null && adminLogin.getPassword().equals(byUsername.getPassword())) {
			byUsername.setLastLogin(LocalDateTime.now());
			adminRepo.save(byUsername);
			return byUsername;
		} else
			return null;
	}

	@Override
	public Admin AdminRegistration(Admin admin) {

		return adminRepo.save(admin);
	}

	@Override
	public List<Student> fetchAll() {
		List<Student> all = repo.findAll();
		return all;
	}

}
