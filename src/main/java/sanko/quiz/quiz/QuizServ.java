package sanko.quiz.quiz;

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
			.build();
		quiz = quizRepo.save(quiz);

		return QuizCreateRes.success(quiz);
	}

	public QuizFetchRes fetch(Long quizId, User currentUser) {
		if (currentUser == null) return QuizFetchRes.fail(Const.NOT_LOGGED_IN);

		Quiz quiz = quizRepo.findOneById(quizId);
		if (quiz == null) return QuizFetchRes.fail(Const.NOT_FOUND);
		if (!quiz.user().id().equals(currentUser.id())) return QuizFetchRes.fail(Const.NOT_FOUND);

		return QuizFetchRes.success(quiz);
	}

	public QuizUpdateRes update(QuizUpdateReq req, User currentUser) {
		if (currentUser == null) return QuizUpdateRes.fail(Const.NOT_LOGGED_IN);

		Quiz quiz = quizRepo.findOneById(req.quizId());
		if (quiz == null) return QuizUpdateRes.fail(Const.NOT_FOUND);
		if (!quiz.user().id().equals(currentUser.id())) return QuizUpdateRes.fail(Const.NOT_FOUND);

		quiz.update(req.title());

		return QuizUpdateRes.success(quiz);
	}

}
