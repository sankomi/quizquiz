package sanko.quiz.question;

import java.util.*; //UUID, List

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import sanko.quiz.user.User;
import sanko.quiz.quiz.*; //Quiz, QuizRepo
import sanko.quiz.answer.AnswerRepo;

import static org.mockito.Mockito.*; //when, verify, times, never
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*; //assertTrue, assertNull, assertEquals
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(SpringExtension.class)
@Import(QuestionServ.class)
class QuestionServTest {

	@Autowired
	private QuestionServ questionServ;

	@MockBean
	private QuizRepo quizRepo;

	@MockBean
	private QuestionRepo questionRepo;

	@MockBean
	private AnswerRepo answerRepo;

	@Test
	void testQuestionCreate() {
		//given
		Long userId = 1L;
		UUID quizId = UUID.randomUUID();
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
		setField(quiz, "questions", List.of());

		QuestionCreateReq req = QuestionCreateReq.builder()
			.quizId(quizId)
			.build();

		when(quizRepo.findOneByQuizId(eq(quizId)))
			.thenReturn(quiz);

		when(questionRepo.save(any(Question.class)))
			.thenAnswer(inv -> inv.getArguments()[0]);

		//when
		QuestionCreateRes res = questionServ.create(req, user);

		//then
		assertTrue(res.ok());
		assertNull(res.message());

		verify(quizRepo, times(1)).findOneByQuizId(eq(quizId));
		verify(questionRepo, times(1)).save(any(Question.class));
	}

	@Test
	void testQuestionUpdate() {
		//given
		Long userId = 1L;
		Long qId = 2L;
		UUID quizId = UUID.randomUUID();
		Long questionId = 3L;
		String text = "text";
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

		QuestionUpdateReq req = QuestionUpdateReq.builder()
			.quizId(quizId)
			.questionId(questionId)
			.text(text)
			.build();

		when(quizRepo.findOneByQuizId(eq(quizId)))
			.thenReturn(quiz);

		when(questionRepo.findOneById(eq(questionId)))
			.thenReturn(question);

		//when
		QuestionUpdateRes res = questionServ.update(req, user);

		//then
		assertTrue(res.ok());
		assertNull(res.message());
		assertEquals(text, res.text());

		verify(quizRepo, times(1)).findOneByQuizId(eq(quizId));
		verify(questionRepo, times(1)).findOneById(eq(questionId));
	}

}
