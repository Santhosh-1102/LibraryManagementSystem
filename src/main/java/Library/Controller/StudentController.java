package Library.Controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Library.Binding.StudentBinding;
import Library.Entity.Student;
import Library.Entity.StudentLogin;
import Library.Service.StudentService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService service;

	private HttpSession session;

	@GetMapping("/sturegister")
	public String showStudentRegisterForm(Model model) {
		model.addAttribute("studentbinding", new StudentBinding());
		return "studentregister";
	}

	@PostMapping("/sturegistration")
	public String StudentRegistration(@ModelAttribute("studentbinding") StudentBinding studentbinding, Model model) {
		String username = studentbinding.getUsername().trim().toLowerCase();

		if (service.existsByUsername(username)) {
			model.addAttribute("errorMessage", "Username already exists. Please choose another one.");
			return "studentregister";
		}

		Student student = new Student();
		BeanUtils.copyProperties(studentbinding, student);
		student.setUsername(username);

		try {
			Student save = service.Registration(student);
			if (save != null) {
				model.addAttribute("student", save);
				return "studentlogin";
			} else {
				model.addAttribute("errorMessage", "Error while registering the user.");
				return "studentregister";
			}
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("errorMessage", "Username already exists. Please choose another one.");
			return "studentregister";
		}
	}

	@GetMapping("/stulogin")
	public String showStudentLoginForm(Model model) {
		model.addAttribute("login", new StudentLogin());
		return "studentlogin";
	}

	@PostMapping("/studentLogin")
	public String Studentlogin(@ModelAttribute("login") StudentLogin login, Model model) {
		Student studentValidate = service.StudentValidate(login);
		if (studentValidate != null) {
			session.setAttribute("student", studentValidate);
			model.addAttribute("student", studentValidate);
			return "studenthome";
		} else {
			model.addAttribute("error", "login credintials are wrong");
			model.addAttribute("login", new StudentLogin());
			return "studentlogin";
		}
	}
}
