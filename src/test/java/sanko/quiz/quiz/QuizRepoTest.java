package sanko.quiz.quiz;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.junit.jupiter.api.Test;

import sanko.quiz.user.User;

import static org.junit.jupiter.api.Assertions.*; //assertTrue, assertEquals;

@DataJpaTest
class QuizRepoTest {

	@Autowired
	private QuizRepo quizRepo;

	@Test
	void testQuizSave() {
		//given
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
		quizRepo.save(quiz);

		//then
		assertTrue(quiz.id() > 0L);
	}

}
