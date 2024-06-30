package sanko.quiz.question;

import java.util.*; //Set, HashSet

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

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
		if (currentUser == null) return QuestionCreateRes.fail("not logged in");

		Quiz quiz = quizRepo.findOneByIdAndUser(req.quizId(), currentUser);
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

		Set<Answer> answers = new HashSet<>();
		for (long i = 0L; i < 4L; i++) {
			Answer answer = Answer.builder()
				.question(question)
				.number(i)
				.build();
			answer = answerRepo.save(answer);
			answers.add(answer);
		}

		question.addAnswers(answers);

		return QuestionCreateRes.success(question);
	}

	@Transactional
	public QuestionUpdateRes update(QuestionUpdateReq req, User currentUser) {
		if (currentUser == null) return QuestionUpdateRes.fail("not logged in");

		Quiz quiz = quizRepo.findOneById(req.quizId());
		if (quiz == null) return QuestionUpdateRes.fail("question not found");
		if (!quiz.user().id().equals(currentUser.id())) return QuestionUpdateRes.fail("question not found");

		Question question = questionRepo.findOneById(req.questionId());
		if (question == null) return QuestionUpdateRes.fail("question not found");

		question.update(req.text());

		return QuestionUpdateRes.success(question);
	}

}
