package sanko.quiz.question;

import java.util.*; //UUID, List

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.*; //MockMvc, ResultActions
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import sanko.quiz.user.User;
import sanko.quiz.quiz.Quiz;
import sanko.quiz.session.SessionServ;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; //post, put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; //status, jsonPath
import static org.mockito.Mockito.*; //when, verify, times
import static org.mockito.ArgumentMatchers.*; //any, eq
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WebMvcTest(QuestionCont.class)
class QuestionContTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuestionServ questionServ;

	@MockBean
	private SessionServ sessionServ;

	@Test
	void testQuestionCreate() throws Exception {
		//given
		Long userId = 1L;
		UUID quizId = UUID.randomUUID();
		Long questionId = 3L;
		Long number = 4L;
		String text = "text";
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
			.build();
		setField(quiz, "quizId", quizId);

		Question question = Question.builder()
			.quiz(quiz)
			.number(number)
			.text(text)
			.build();
		setField(question, "id", questionId);

		QuestionCreateReq req = QuestionCreateReq.builder()
			.quizId(quizId)
			.build();

		when(sessionServ.getUser())
			.thenReturn(user);

		when(questionServ.create(any(QuestionCreateReq.class), eq(user)))
			.thenReturn(QuestionCreateRes.success(question));

		//when
		ResultActions res = mockMvc.perform(
			post("/question")
				.contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(req))
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.ok").value(true))
			.andExpect(jsonPath("$.questionId").value(questionId))
			.andExpect(jsonPath("$.number").value(number));

		verify(questionServ, times(1)).create(any(QuestionCreateReq.class), eq(user));
		verify(sessionServ, times(1)).getUser();
	}

	@Test
	void testQuestionUpdate() throws Exception {
		//given
		Long userId = 1L;
		UUID quizId = UUID.randomUUID();
		Long questionId = 3L;
		Long number = 4L;
		String text = "text";
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
			.build();
		setField(quiz, "quizId", quizId);

		Question question = Question.builder()
			.quiz(quiz)
			.number(number)
			.text(text)
			.build();
		setField(question, "id", questionId);

		QuestionUpdateReq req = QuestionUpdateReq.builder()
			.quizId(quizId)
			.questionId(questionId)
			.text(text)
			.build();

		when(sessionServ.getUser())
			.thenReturn(user);

		when(questionServ.update(any(QuestionUpdateReq.class), eq(user)))
			.thenReturn(QuestionUpdateRes.success(question));

		//when
		ResultActions res = mockMvc.perform(
			put("/question")
				.contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(req))
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.ok").value(true))
			.andExpect(jsonPath("$.text").value(text));

		verify(questionServ, times(1)).update(any(QuestionUpdateReq.class), eq(user));
		verify(sessionServ, times(1)).getUser();
	}

}
