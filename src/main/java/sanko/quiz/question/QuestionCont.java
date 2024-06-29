package sanko.quiz.question;

import org.springframework.web.bind.annotation.*; //RestController, RequestMapping, PostMapping, RequestBody
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.User;
import sanko.quiz.session.CurrentUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("/question")
public class QuestionCont {

	private final QuestionServ questionServ;

	@PostMapping
	public QuestionCreateRes create(
		@RequestBody QuestionCreateReq req,
		@CurrentUser User currentUser
	) {
		return questionServ.create(req, currentUser);
	}

	@PutMapping
	public QuestionUpdateRes update(
		@RequestBody QuestionUpdateReq req,
		@CurrentUser User currentUser
	) {
		return questionServ.update(req, currentUser);
	}

}
