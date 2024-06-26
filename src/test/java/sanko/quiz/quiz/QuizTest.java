package sanko.quiz.quiz;

import org.junit.jupiter.api.Test;

import sanko.quiz.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuizTest {

	@Test
	void testNewQuiz() {
		//given
		String title = "title";
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		//when
		Quiz quiz = Quiz.builder()
			.user(user)
			.title(title)
			.build();

		//then
		assertEquals(title, quiz.title());
		assertEquals(username, quiz.user().username());
	}

}
