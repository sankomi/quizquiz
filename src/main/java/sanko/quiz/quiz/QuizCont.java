package sanko.quiz.quiz;

import org.springframework.web.bind.annotation.*; //RestController, RequestMapping, PostMapping, RequestBody
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.User;
import sanko.quiz.session.CurrentUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz")
public class QuizCont {

	private final QuizServ quizServ;

	@PostMapping("/create")
	public QuizCreateRes create(
		@RequestBody QuizCreateReq req,
		@CurrentUser User currentUser
	) {
		return quizServ.create(req, currentUser);
	}

}
