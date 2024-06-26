package sanko.quiz.quiz;

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
		String title = "title";
		String username = "username";
		String key = "key";

		QuizCreateReq req = QuizCreateReq.builder()
			.title(title)
			.build();

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		//when
		QuizCreateRes res = quizServ.create(req, user);

		//then
		assertTrue(res.create());
		assertNull(res.message());

		verify(quizRepo, times(1)).save(any(Quiz.class));
	}

	@Test
	void testQuizCreateNotLoggedIn() {
		//given
		String title = "title";

		QuizCreateReq req = QuizCreateReq.builder()
			.title(title)
			.build();

		//when
		QuizCreateRes res = quizServ.create(req, null);

		//then
		assertFalse(res.create());
		assertEquals("not logged in", res.message());

		verify(quizRepo, never()).save(any(Quiz.class));
	}
}
