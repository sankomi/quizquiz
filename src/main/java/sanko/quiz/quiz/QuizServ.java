package sanko.quiz.quiz;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.User;

@RequiredArgsConstructor
@Service
public class QuizServ {

	private final QuizRepo quizRepo;

	@Transactional
	public QuizCreateRes create(User currentUser) {
		if (currentUser == null) return QuizCreateRes.fail("not logged in");

		Quiz quiz = Quiz.builder()
			.user(currentUser)
			.build();
		quiz = quizRepo.save(quiz);

		return QuizCreateRes.success(quiz);
	}

	public QuizFetchRes fetch(Long quizId, User currentUser) {
		if (currentUser == null) return QuizFetchRes.fail("not logged in");

		Quiz quiz = quizRepo.findOneByIdAndUser(quizId, currentUser);
		if (quiz == null) return QuizFetchRes.fail("not found");

		return QuizFetchRes.success(quiz);
	}

	public QuizUpdateRes update(QuizUpdateReq req, User currentUser) {
		if (currentUser == null) return QuizUpdateRes.fail("not logged in");

		Quiz quiz = quizRepo.findOneByIdAndUser(req.quizId(), currentUser);
		if (quiz == null) return QuizUpdateRes.fail("not found");

		quiz.update(req.title());

		return QuizUpdateRes.success(quiz);
	}

}
