package sanko.quiz.answer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import sanko.quiz.Const;
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
		if (currentUser == null) return AnswerUpdateRes.fail(Const.NOT_LOGGED_IN);

		Quiz quiz = quizRepo.findOneByQuizId(req.quizId());
		if (quiz == null) return AnswerUpdateRes.fail(Const.NOT_FOUND);
		if (!quiz.user().id().equals(currentUser.id())) return AnswerUpdateRes.fail(Const.NOT_FOUND);

		Question question = questionRepo.findOneById(req.questionId());
		if (question == null) return AnswerUpdateRes.fail(Const.NOT_FOUND);
		if (!question.quiz().id().equals(quiz.id())) return AnswerUpdateRes.fail(Const.NOT_FOUND);

		Answer answer = answerRepo.findOneById(req.answerId());
		if (answer == null) return AnswerUpdateRes.fail(Const.NOT_FOUND);
		if (!answer.question().id().equals(question.id())) return AnswerUpdateRes.fail(Const.NOT_FOUND);

		answer.update(req.text(), req.correct());

		return AnswerUpdateRes.success(answer);
	}

}
