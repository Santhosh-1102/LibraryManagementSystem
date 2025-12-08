package Library.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/borrow")
public class BorrowController {

	@GetMapping
	public String borrowBooks() {

		return "";
	}
}
