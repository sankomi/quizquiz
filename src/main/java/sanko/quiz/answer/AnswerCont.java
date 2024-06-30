package sanko.quiz.answer;

import org.springframework.web.bind.annotation.*; //RestController, RequestMapping, PostMapping, RequestBody
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.User;
import sanko.quiz.session.CurrentUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("/answer")
public class AnswerCont {

	private final AnswerServ answerServ;

	@PutMapping
	public AnswerUpdateRes update(
		@RequestBody AnswerUpdateReq req,
		@CurrentUser User currentUser
	) {
		return answerServ.update(req, currentUser);
	}

}
