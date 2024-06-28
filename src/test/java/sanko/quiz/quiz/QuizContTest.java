package sanko.quiz.quiz;

import java.util.Set;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.*; //MockMvc, ResultActions
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import sanko.quiz.user.User;
import sanko.quiz.session.SessionServ;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; //post, put, delete
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; //status, jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.*; //when, verify, times
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WebMvcTest(QuizCont.class)
class QuizContTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuizServ quizServ;

	@MockBean
	private SessionServ sessionServ;

	@Test
	void testQuizCreate() throws Exception {
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
			.build();
		setField(quiz, "id", quizId);

		when(sessionServ.getUser())
			.thenReturn(user);

		when(quizServ.create(eq(user)))
			.thenReturn(QuizCreateRes.success(quiz));

		//when
		ResultActions res = mockMvc.perform(post("/quiz"));

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.create").value("true"))
			.andExpect(jsonPath("$.quizId").value(quizId));

		verify(quizServ, times(1)).create(eq(user));
		verify(sessionServ, times(1)).getUser();
	}

	@Test
	void testQuizFetch() throws Exception {
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

		when(sessionServ.getUser())
			.thenReturn(user);

		when(quizServ.fetch(eq(quizId), eq(user)))
			.thenReturn(QuizFetchRes.success(quiz));

		//when
		ResultActions res = mockMvc.perform(
			get("/quiz/" + String.valueOf(quizId))
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.fetch").value("true"))
			.andExpect(jsonPath("$.quizId").value(quizId))
			.andExpect(jsonPath("$.title").value(title));

		verify(quizServ, times(1)).fetch(eq(quizId), eq(user));
		verify(sessionServ, times(1)).getUser();
	}

}
