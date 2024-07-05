package sanko.quiz.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.User;
import sanko.quiz.session.CurrentUser;

@RequiredArgsConstructor
@Controller
public class WebCont {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String login(@CurrentUser User currentUser) {
		if (currentUser == null) {
			return "login";
		} else {
			return "redirect:/list";
		}
	}

	@GetMapping("/logout")
	public String logout(@CurrentUser User currentUser) {
		if (currentUser == null) {
			return "login";
		} else {
			return "logout";
		}
	}

	@GetMapping("/list")
	public String list(@CurrentUser User currentUser) {
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			return "list";
		}
	}

	@GetMapping("/edit")
	public String edit(@CurrentUser User currentUser) {
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			return "edit";
		}
	}

}
