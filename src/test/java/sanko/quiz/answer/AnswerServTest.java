package sanko.quiz.answer;

import java.util.*; //UUID, List

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import sanko.quiz.user.User;
import sanko.quiz.quiz.*; //Quiz, QuizRepo
import sanko.quiz.question.*; //Question, QuestionRepo

import static org.mockito.Mockito.*; //when, verify, times
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*; //assertTrue, assertNull, assertEquals
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(SpringExtension.class)
@Import(AnswerServ.class)
class AnswerServTest {

	@Autowired
	private AnswerServ answerServ;

	@MockBean
	private QuizRepo quizRepo;

	@MockBean
	private QuestionRepo questionRepo;

	@MockBean
	private AnswerRepo answerRepo;

	@Test
	void testAnswerUpdate() {
		//given
		Long userId = 1L;
		Long qId = 2L;
		UUID quizId = UUID.randomUUID();
		Long questionId = 3L;
		Long answerId = 4L;
		Long number = 3L;
		String text = "text";
		Boolean correct = true;
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", userId);

		Quiz quiz = Quiz.builder()
			.user(user)
			.build();
		setField(quiz, "id", qId);
		setField(quiz, "quizId", quizId);

		Question question = Question.builder()
			.quiz(quiz)
			.text(text)
			.build();
		setField(question, "id", questionId);

		Answer answer = Answer.builder()
			.question(question)
			.number(number)
			.text(text)
			.correct(correct)
			.build();

		AnswerUpdateReq req = AnswerUpdateReq.builder()
			.quizId(quizId)
			.questionId(questionId)
			.answerId(answerId)
			.text(text)
			.correct(correct)
			.build();

		when(quizRepo.findOneByQuizId(eq(quizId)))
			.thenReturn(quiz);

		when(questionRepo.findOneById(eq(questionId)))
			.thenReturn(question);

		when(answerRepo.findOneById(eq(answerId)))
			.thenReturn(answer);

		//when
		AnswerUpdateRes res = answerServ.update(req, user);

		//then
		assertTrue(res.ok());
		assertNull(res.message());
		assertEquals(text, res.text());
		assertEquals(correct, res.correct());

		verify(quizRepo, times(1)).findOneByQuizId(eq(quizId));
		verify(questionRepo, times(1)).findOneById(eq(questionId));
		verify(answerRepo, times(1)).findOneById(eq(answerId));
	}

}
