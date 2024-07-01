package sanko.quiz.answer;

import org.junit.jupiter.api.Test;

import sanko.quiz.question.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnswerTest {

	@Test
	void testNewAnswer() {
		//given
		Long number = 1L;
		String text = "text";
		Boolean correct = true;

		Question question = Question.builder()
			.build();

		//when
		Answer answer = Answer.builder()
			.question(question)
			.number(number)
			.text(text)
			.correct(correct)
			.build();

		//then
		assertEquals(number, answer.number());
		assertEquals(text, answer.text());
		assertEquals(correct, answer.correct());
	}

}
