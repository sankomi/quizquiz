package sanko.quiz.quiz;

import java.util.UUID;

import org.springframework.web.bind.annotation.*; //RestController, RequestMapping, PostMapping, RequestBody, PathVariable
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.User;
import sanko.quiz.session.CurrentUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz")
public class QuizCont {

	private final QuizServ quizServ;

	@PostMapping
	public QuizCreateRes create(@CurrentUser User currentUser) {
		return quizServ.create(currentUser);
	}

	@GetMapping
	public QuizListRes list(@CurrentUser User currentUser) {
		return quizServ.list(currentUser);
	}

	@GetMapping("/{quizId}")
	public QuizFetchRes fetch(
		@PathVariable("quizId") UUID quizId,
		@CurrentUser User currentUser
	) {
		return quizServ.fetch(quizId, currentUser);
	}

	@GetMapping("/{quizId}/qr")
	public QuizQrRes qr(
		@PathVariable("quizId") UUID quizId,
		@CurrentUser User currentUser
	) {
		return quizServ.qr(quizId, currentUser);
	}

	@PutMapping
	public QuizUpdateRes update(
		@RequestBody QuizUpdateReq req,
		@CurrentUser User currentUser
	) {
		return quizServ.update(req, currentUser);
	}

}
