package sanko.quiz.quiz;

import java.util.*; //UUID, List

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import sanko.quiz.Const;
import sanko.quiz.user.User;

@RequiredArgsConstructor
@Service
public class QuizServ {

	private final QuizRepo quizRepo;

	@Transactional
	public QuizCreateRes create(User currentUser) {
		if (currentUser == null) return QuizCreateRes.fail(Const.NOT_LOGGED_IN);

		Quiz quiz = Quiz.builder()
			.user(currentUser)
			.title("untitled quiz")
			.build();
		quiz.setQuizId(UUID.randomUUID());
		quiz = quizRepo.save(quiz);

		return QuizCreateRes.success(quiz);
	}

	public QuizListRes list(User currentUser) {
		if (currentUser == null) return QuizListRes.fail(Const.NOT_LOGGED_IN);

		List<Quiz> list = quizRepo.findByUser(currentUser);

		return QuizListRes.success(list);
	}

	public QuizFetchRes fetch(UUID quizId, User currentUser) {
		Quiz quiz = quizRepo.findOneByQuizId(quizId);
		if (quiz == null) return QuizFetchRes.fail(Const.NOT_FOUND);
		if (!quiz.open()) {
			if (currentUser == null) return QuizFetchRes.fail(Const.NOT_FOUND);

			if (!quiz.user().id().equals(currentUser.id())) {
				return QuizFetchRes.fail(Const.NOT_FOUND);
			}
		}

		return QuizFetchRes.success(quiz);
	}

	public QuizUpdateRes update(QuizUpdateReq req, User currentUser) {
		if (currentUser == null) return QuizUpdateRes.fail(Const.NOT_LOGGED_IN);

		Quiz quiz = quizRepo.findOneByQuizId(req.quizId());
		if (quiz == null) return QuizUpdateRes.fail(Const.NOT_FOUND);
		if (!quiz.user().id().equals(currentUser.id())) return QuizUpdateRes.fail(Const.NOT_FOUND);

		String title = req.title();
		Boolean open = req.open();
		Boolean shuffleQuestions = req.shuffleQuestions();
		Boolean shuffleAnswers = req.shuffleAnswers();

		quiz.update(title, open, shuffleQuestions, shuffleAnswers);

		return QuizUpdateRes.success(quiz);
	}

}
