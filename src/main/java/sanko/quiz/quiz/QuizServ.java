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
	public QuizCreateRes create(QuizCreateReq req, User currentUser) {
		if (currentUser == null) return QuizCreateRes.fail("not logged in");

		Quiz quiz = Quiz.builder()
			.user(currentUser)
			.title(req.title())
			.build();
		quizRepo.save(quiz);

		return QuizCreateRes.success();
	}

}
