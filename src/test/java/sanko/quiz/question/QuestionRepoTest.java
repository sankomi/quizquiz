package sanko.quiz.question;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.junit.jupiter.api.Test;

import sanko.quiz.user.User;
import sanko.quiz.quiz.Quiz;

import static org.junit.jupiter.api.Assertions.*; //assertTrue, assertEquals;

@DataJpaTest
class QuestionRepoTest {

	@Autowired
	private QuestionRepo questionRepo;

	@Test
	void testQuestionSave() {
		//given
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

		Question question = Question.builder()
			.quiz(quiz)
			.text(text)
			.build();

		//when
		questionRepo.save(question);

		//then
		assertTrue(question.id() > 0L);
	}

}
