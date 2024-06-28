package sanko.quiz.question;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.User;
import sanko.quiz.quiz.*; //Quiz, QuizRepo

@RequiredArgsConstructor
@Service
public class QuestionServ {

	private final QuestionRepo questionRepo;
	private final QuizRepo quizRepo;

	@Transactional
	public QuestionCreateRes create(QuestionCreateReq req, User currentUser) {
		if (currentUser == null) return QuestionCreateRes.fail("not logged in");

		Quiz quiz = quizRepo.findOneById(req.quizId());
		if (quiz == null) return QuestionCreateRes.fail("quiz not found");
		if (!quiz.user().id().equals(currentUser.id())) return QuestionCreateRes.fail("quiz not found");


		Long number = 1L;
		for (Question q : quiz.questions()) {
			if (q.number() >= number) {
				number = q.number() + 1;
			}
		}

		Question question = Question.builder()
			.quiz(quiz)
			.number(number)
			.build();
		question = questionRepo.save(question);

		return QuestionCreateRes.success(question);
	}

}
