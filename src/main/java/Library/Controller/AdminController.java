package Library.Controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Library.Binding.AdminBinding;
import Library.Entity.Admin;
import Library.Entity.AdminLogin;
import Library.Entity.Student;
import Library.Service.AdminService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService service;

	@GetMapping("/register")
	public String showAdminRegistrationForm(Model model) {
		model.addAttribute("adminBinding", new AdminBinding());
		return "adminregistration";
	}

	@PostMapping("/registerafter")
	public String AdminRegistration(@ModelAttribute AdminBinding admin, Model model) {
		Admin s = new Admin();
		BeanUtils.copyProperties(admin, s);
		Admin ad = service.AdminRegistration(s);
		if (ad != null) {
			model.addAttribute("admin", s);
			return "adminlogin";
		} else {
			model.addAttribute("error", "error while registeration");
			return "adminregistration";
		}
	}

	@GetMapping("/adminLogin")
	public String AdminLogin(Model model) {
		model.addAttribute("adminlogin", new AdminLogin());
		return "adminlogin";
	}

	@PostMapping("/adminlogin")
	public String adminLogin(HttpSession session, AdminLogin login, Model model) {
		Admin adminlogin = service.Adminlogin(login);
		if (adminlogin != null) {
			model.addAttribute("admindetails", adminlogin);
			session.setAttribute("admin", adminlogin);
			return "home";
		} else {
			model.addAttribute("error", "login credentials are not valid");
			return "adminlogin";
		}
	}

	@GetMapping("/logout")
	public String adminLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/admin/adminLogin";
	}

	@GetMapping("/fetchstudents")
	public String getStudent(Model model) {
		return "redirect:/admin/getstudents";
	}

	@GetMapping("/getstudents")
	public String fetchStudents(Model model) {
		List<Student> allStudents = service.fetchAll();
		if (allStudents != null && !allStudents.isEmpty()) {
			model.addAttribute("students", allStudents);
			for (Student all : allStudents) {
				System.out.println(all.getBorrows());
			}
			return "viewstudent";
		} else {
			model.addAttribute("error", "no data found ");
			return "home";
		}

	}

}
