package sanko.quiz.question;

import java.util.*; //List, ArrayList

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import sanko.quiz.Const;
import sanko.quiz.common.Response;
import sanko.quiz.user.User;
import sanko.quiz.quiz.*; //Quiz, QuizRepo
import sanko.quiz.answer.*; //Answer, AnswerRepo

@RequiredArgsConstructor
@Service
public class QuestionServ {

	private final QuestionRepo questionRepo;
	private final QuizRepo quizRepo;
	private final AnswerRepo answerRepo;

	@Transactional
	public QuestionCreateRes create(QuestionCreateReq req, User currentUser) {
		if (currentUser == null) return Response.fail(QuestionCreateRes.class, Const.NOT_LOGGED_IN);

		Quiz quiz = quizRepo.findOneByQuizId(req.quizId());
		if (quiz == null) return Response.fail(QuestionCreateRes.class, Const.NOT_FOUND);
		if (!quiz.user().id().equals(currentUser.id())) return Response.fail(QuestionCreateRes.class, Const.NOT_FOUND);

		Long number = 1L;
		for (Question q : quiz.questions()) {
			if (q.number() >= number) {
				number = q.number() + 1;
			}
		}

		Question question = Question.builder()
			.quiz(quiz)
			.number(number)
			.text("question " + String.valueOf(number))
			.build();
		question = questionRepo.save(question);

		List<Answer> answers = new ArrayList<>();
		for (long i = 0L; i < 4L; i++) {
			Answer answer = Answer.builder()
				.question(question)
				.number(i)
				.text("answer " + String.valueOf(i))
				.build();
			answer = answerRepo.save(answer);
			answers.add(answer);
		}

		question.addAnswers(answers);

		return QuestionCreateRes.success(question);
	}

	@Transactional
	public QuestionUpdateRes update(QuestionUpdateReq req, User currentUser) {
		if (currentUser == null) return Response.fail(QuestionUpdateRes.class, Const.NOT_LOGGED_IN);

		Quiz quiz = quizRepo.findOneByQuizId(req.quizId());
		if (quiz == null) return Response.fail(QuestionUpdateRes.class, Const.NOT_FOUND);
		if (!quiz.user().id().equals(currentUser.id())) return Response.fail(QuestionUpdateRes.class, Const.NOT_FOUND);

		Question question = questionRepo.findOneById(req.questionId());
		if (question == null) return Response.fail(QuestionUpdateRes.class, Const.NOT_FOUND);
		if (!question.quiz().id().equals(quiz.id())) return Response.fail(QuestionUpdateRes.class, Const.NOT_FOUND);

		question.update(req.text());

		return QuestionUpdateRes.success(question);
	}

}
