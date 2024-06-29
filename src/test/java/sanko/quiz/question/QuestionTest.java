package sanko.quiz.question;

import org.junit.jupiter.api.Test;

import sanko.quiz.user.User;
import sanko.quiz.quiz.Quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionTest {

	@Test
	void testNewQuestion() {
		//given
		Long number = 1L;
		String text = "text";
		String title = "title";
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		Quiz quiz = Quiz.builder()
			.user(user)
			.title(title)
			.build();

		//when
		Question question = Question.builder()
			.quiz(quiz)
			.number(number)
			.text(text)
			.build();

		//then
		assertEquals(number, question.number());
		assertEquals(text, question.text());
		assertEquals(username, question.quiz().user().username());
	}

}
