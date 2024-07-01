package sanko.quiz.answer;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

import sanko.quiz.question.Question;

import static org.junit.jupiter.api.Assertions.*; //assertTrue, assertEquals;

@DataJpaTest
class AnswerRepoTest {

	@Autowired
	private AnswerRepo answerRepo;

	@Test
	void testAnswerSave() {
		//given
		Long number = 1L;
		String text = "text";
		Boolean correct = true;

		Question question = Question.builder()
			.build();

		Answer answer = Answer.builder()
			.question(question)
			.number(number)
			.text(text)
			.correct(correct)
			.build();

		//when
		answerRepo.save(answer);

		//then
		assertTrue(answer.id() > 0L);
	}

}
