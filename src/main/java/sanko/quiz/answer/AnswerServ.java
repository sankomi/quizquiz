package sanko.quiz.answer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.User;
import sanko.quiz.quiz.*; //Quiz, QuizRepo
import sanko.quiz.question.*; //Question, QuestionRepo;

@RequiredArgsConstructor
@Service
public class AnswerServ {

	private final AnswerRepo answerRepo;
	private final QuizRepo quizRepo;
	private final QuestionRepo questionRepo;

	@Transactional
	public AnswerUpdateRes update(AnswerUpdateReq req, User currentUser) {
		if (currentUser == null) return AnswerUpdateRes.fail("not logged in");

		Quiz quiz = quizRepo.findOneByIdAndUser(req.quizId(), currentUser);
		if (quiz == null) return AnswerUpdateRes.fail("answer not found");
		if (!quiz.user().id().equals(currentUser.id())) return AnswerUpdateRes.fail("answer not found");

		Question question = questionRepo.findOneById(req.questionId());
		if (question == null) return AnswerUpdateRes.fail("answer not found");

		Answer answer = answerRepo.findOneById(req.answerId());
		if (answer == null) return AnswerUpdateRes.fail("answer not found");

		answer.update(req.text(), req.correct());

		return AnswerUpdateRes.success(answer);
	}

}
