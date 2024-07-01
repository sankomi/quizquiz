package sanko.quiz.quiz;

import java.util.Set;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

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

	@Test
	void testQuizCreate() {
		//given
		Long userId = 1L;
		Long quizId = 2L;
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
		setField(quiz, "id", quizId);
		setField(quiz, "questions", Set.of());

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
		assertEquals("not logged in", res.message());

		verify(quizRepo, never()).save(any(Quiz.class));
	}

	@Test
	void testQuizFetch() {
		//given
		Long userId = 1L;
		Long quizId = 2L;
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
		setField(quiz, "id", quizId);
		setField(quiz, "questions", Set.of());

		when(quizRepo.findOneByIdAndUser(eq(quizId), eq(user)))
			.thenReturn(quiz);

		//when
		QuizFetchRes res = quizServ.fetch(quizId, user);

		//then
		assertTrue(res.fetch());
		assertNull(res.message());
		assertEquals(title, res.title());

		verify(quizRepo, times(1)).findOneByIdAndUser(eq(quizId), eq(user));
	}

	@Test
	void testQuizFetchNotFound() {
		//given
		Long userId = 1L;
		Long quizId = 2L;
		String title = "title";
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", userId);

		when(quizRepo.findOneByIdAndUser(eq(quizId), eq(user)))
			.thenReturn(null);

		//when
		QuizFetchRes res = quizServ.fetch(quizId, user);

		//then
		assertFalse(res.fetch());
		assertEquals("not found", res.message());
		assertNull(res.title());

		verify(quizRepo, times(1)).findOneByIdAndUser(eq(quizId), eq(user));
	}

	@Test
	void testQuizUpdate() {
		//given
		Long userId = 1L;
		Long quizId = 2L;
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
		setField(quiz, "id", quizId);
		setField(quiz, "questions", Set.of());

		QuizUpdateReq req = QuizUpdateReq.builder()
			.quizId(quizId)
			.title(title)
			.build();

		when(quizRepo.findOneByIdAndUser(eq(quizId), eq(user)))
			.thenReturn(quiz);

		//when
		QuizUpdateRes res = quizServ.update(req, user);

		//then
		assertTrue(res.update());
		assertNull(res.message());
		assertEquals(title, res.title());

		verify(quizRepo, times(1)).findOneByIdAndUser(eq(quizId), eq(user));
	}

}
