package sanko.quiz.answer;

import java.util.*; //UUID, Set

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.*; //MockMvc, ResultActions
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import sanko.quiz.user.User;
import sanko.quiz.quiz.Quiz;
import sanko.quiz.question.Question;
import sanko.quiz.session.SessionServ;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; //status, jsonPath
import static org.mockito.Mockito.*; //when, verify, times
import static org.mockito.ArgumentMatchers.*; //any, eq
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WebMvcTest(AnswerCont.class)
class AnswerContTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AnswerServ answerServ;

	@MockBean
	private SessionServ sessionServ;

	@Test
	void testAnswerUpdate() throws Exception {
		//given
		Long userId = 1L;
		UUID quizId = UUID.randomUUID();
		Long questionId = 3L;
		Long answerId = 4L;
		Long number = 5L;
		String text = "text";
		Boolean correct = true;
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
		setField(quiz, "quizId", quizId);

		Question question = Question.builder()
			.quiz(quiz)
			.build();
		setField(question, "id", questionId);

		Answer answer = Answer.builder()
			.question(question)
			.number(number)
			.text(text)
			.correct(correct)
			.build();

		AnswerUpdateReq req = AnswerUpdateReq.builder()
			.quizId(quizId)
			.questionId(questionId)
			.answerId(answerId)
			.text(text)
			.correct(correct)
			.build();

		when(sessionServ.getUser())
			.thenReturn(user);

		when(answerServ.update(any(AnswerUpdateReq.class), eq(user)))
			.thenReturn(AnswerUpdateRes.success(answer));

		//when
		ResultActions res = mockMvc.perform(
			put("/answer")
				.contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(req))
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.update").value("true"))
			.andExpect(jsonPath("$.text").value(text))
			.andExpect(jsonPath("$.correct").value(correct));

		verify(answerServ, times(1)).update(any(AnswerUpdateReq.class), eq(user));
		verify(sessionServ, times(1)).getUser();
	}

}
