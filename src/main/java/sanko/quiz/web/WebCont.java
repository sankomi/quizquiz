package sanko.quiz.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*; //GetMapping, PathVariable, 
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

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

	@GetMapping("/create")
	public String create(@CurrentUser User currentUser) {
		if (currentUser == null) {
			return "create";
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

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable String id, @CurrentUser User currentUser, Model model) {
		if (currentUser == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("id", id);
			return "edit";
		}
	}

	@GetMapping("/play/{id}")
	public String play(@PathVariable String id, Model model) {
		model.addAttribute("id", id);
		return "play";
	}

	@GetMapping("/prompt")
	public String prompt() {
		return "prompt";
	}

}
