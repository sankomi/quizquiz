package sanko.quiz.quiz;

import org.springframework.web.bind.annotation.*; //RestController, RequestMapping, PostMapping, RequestBody, PathVariable
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.User;
import sanko.quiz.session.CurrentUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz")
public class QuizCont {

	private final QuizServ quizServ;

	@PostMapping()
	public QuizCreateRes create(@CurrentUser User currentUser) {
		return quizServ.create(currentUser);
	}

	@GetMapping("/{quizId}")
	public QuizFetchRes fetch(
		@PathVariable("quizId") Long quizId,
		@CurrentUser User currentUser
	) {
		return quizServ.fetch(quizId, currentUser);
	}

}
