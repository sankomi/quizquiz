package sanko.quiz.quiz;

import java.util.*; //UUID, List

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import sanko.quiz.Const;
import sanko.quiz.common.QrServ;
import sanko.quiz.user.User;

import static org.mockito.Mockito.*; //when, verify, times, never
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*; //assertTrue, assertFalse, assertNull, assertEquals
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(SpringExtension.class)
@Import(QuizServ.class)
class QuizServTest {

	@Autowired
	private QuizServ quizServ;

	@MockBean
	private QuizRepo quizRepo;

	@MockBean
	private QrServ qrServ;

	@Test
	void testQuizCreate() {
		//given
		Long userId = 1L;
		UUID quizId = UUID.randomUUID();
		String title = "title";
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", userId);

		Quiz quiz = Quiz.builder()
			.user(user)
			.title(title)
			.build();
		setField(quiz, "quizId", quizId);
		setField(quiz, "questions", List.of());

		when(quizRepo.save(any(Quiz.class)))
			.thenReturn(quiz);

		//when
		QuizCreateRes res = quizServ.create(user);

		//then
		assertTrue(res.create());
		assertNull(res.message());

		verify(quizRepo, times(1)).save(any(Quiz.class));
	}

	@Test
	void testQuizCreateNotLoggedIn() {
		//given
		String title = "title";

		//when
		QuizCreateRes res = quizServ.create(null);

		//then
		assertFalse(res.create());
		assertEquals(Const.NOT_LOGGED_IN, res.message());

		verify(quizRepo, never()).save(any(Quiz.class));
	}

	@Test
	void testQuizFetch() {
		//given
		Long userId = 1L;
		UUID quizId = UUID.randomUUID();
		String title = "title";
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", userId);

		Quiz quiz = Quiz.builder()
			.user(user)
			.title(title)
			.build();
		setField(quiz, "quizId", quizId);
		setField(quiz, "questions", List.of());

		when(quizRepo.findOneByQuizId(eq(quizId)))
			.thenReturn(quiz);

		//when
		QuizFetchRes res = quizServ.fetch(quizId, user);

		//then
		assertTrue(res.fetch());
		assertNull(res.message());
		assertEquals(title, res.title());

		verify(quizRepo, times(1)).findOneByQuizId(eq(quizId));
	}

	@Test
	void testQuizFetchNotOpen() {
		//given
		UUID quizId = UUID.randomUUID();
		String title = "title";
		Boolean open = false;

		Quiz quiz = Quiz.builder()
			.title(title)
			.build();
		setField(quiz, "quizId", quizId);
		setField(quiz, "questions", List.of());
		setField(quiz, "open", open);

		when(quizRepo.findOneByQuizId(eq(quizId)))
			.thenReturn(quiz);

		//when
		QuizFetchRes res = quizServ.fetch(quizId, null);

		//then
		assertFalse(res.fetch());
		assertEquals(Const.NOT_FOUND, res.message());
		assertNull(res.title());

		verify(quizRepo, times(1)).findOneByQuizId(eq(quizId));
	}

	@Test
	void testQuizFetchNotUser() {
		//given
		Long userId = 1L;
		Long anotherUserId = 3L;
		UUID quizId = UUID.randomUUID();
		String title = "title";
		Boolean open = false;
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", userId);

		User anotherUser = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(anotherUser, "id", anotherUserId);

		Quiz quiz = Quiz.builder()
			.user(anotherUser)
			.title(title)
			.build();
		setField(quiz, "quizId", quizId);
		setField(quiz, "questions", List.of());
		setField(quiz, "open", open);

		when(quizRepo.findOneByQuizId(eq(quizId)))
			.thenReturn(quiz);

		//when
		QuizFetchRes res = quizServ.fetch(quizId, user);

		//then
		assertFalse(res.fetch());
		assertEquals(Const.NOT_FOUND, res.message());
		assertNull(res.title());

		verify(quizRepo, times(1)).findOneByQuizId(eq(quizId));
	}

	@Test
	void testQuizFetchNotFound() {
		//given
		Long userId = 1L;
		UUID quizId = UUID.randomUUID();
		String title = "title";
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", userId);

		when(quizRepo.findOneByQuizId(eq(quizId)))
			.thenReturn(null);

		//when
		QuizFetchRes res = quizServ.fetch(quizId, user);

		//then
		assertFalse(res.fetch());
		assertEquals(Const.NOT_FOUND, res.message());
		assertNull(res.title());

		verify(quizRepo, times(1)).findOneByQuizId(eq(quizId));
	}

	@Test
	void testQuizUpdate() {
		//given
		Long userId = 1L;
		UUID quizId = UUID.randomUUID();
		String title = "title";
		Boolean open = true;
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", userId);

		Quiz quiz = Quiz.builder()
			.user(user)
			.title(title)
			.build();
		setField(quiz, "quizId", quizId);
		setField(quiz, "questions", List.of());
		setField(quiz, "open", open);

		QuizUpdateReq req = QuizUpdateReq.builder()
			.quizId(quizId)
			.title(title)
			.open(open)
			.build();

		when(quizRepo.findOneByQuizId(eq(quizId)))
			.thenReturn(quiz);

		//when
		QuizUpdateRes res = quizServ.update(req, user);

		//then
		assertTrue(res.update());
		assertNull(res.message());
		assertEquals(title, res.title());
		assertEquals(open, res.open());

		verify(quizRepo, times(1)).findOneByQuizId(eq(quizId));
	}

	@Test
	void testQuizUpdateNotFound() {
		//given
		Long userId = 1L;
		UUID quizId = UUID.randomUUID();
		String title = "title";
		Boolean open = true;
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", userId);

		QuizUpdateReq req = QuizUpdateReq.builder()
			.quizId(quizId)
			.title(title)
			.open(open)
			.build();

		when(quizRepo.findOneByQuizId(eq(quizId)))
			.thenReturn(null);

		//when
		QuizUpdateRes res = quizServ.update(req, user);

		//then
		assertFalse(res.update());
		assertEquals(Const.NOT_FOUND, res.message());
		assertNull(res.title());

		verify(quizRepo, times(1)).findOneByQuizId(eq(quizId));
	}

}
