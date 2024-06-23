package sanko.quiz.user;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.*; //MockMvc, ResultActions
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import sanko.quiz.session.*; //SessionServ, SessionUser

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; //status, jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.*; //when, verify, times
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@WebMvcTest(UserCont.class)
class UserContTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServ userServ;

	@MockBean
	private SessionServ sessionServ;

	@Test
	void testUserLogin() throws Exception {
		//given
		Long id = 1L;
		String username = "username";
		String password = "password";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", id);

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userServ.login(any(UserLoginReq.class), any(SessionUser.class)))
			.thenReturn(UserLoginRes.success());

		SessionUser sessionUser = SessionUser.builder()
			.user(user)
			.build();

		when(sessionServ.getUser())
			.thenReturn(sessionUser);

		//when
		ResultActions res = mockMvc.perform(
			post("/user/login")
				.contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(req))
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.login").value("true"));

		verify(userServ, times(1)).login(any(UserLoginReq.class), any(SessionUser.class));
		verify(sessionServ, times(1)).getUser();
	}

	@Test
	void testUserLoginFail() throws Exception {
		//given
		Long id = 1L;
		String username = "username";
		String password = "password";
		String key = "key";
		String message = "message";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", id);

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userServ.login(any(UserLoginReq.class), any(SessionUser.class)))
			.thenReturn(UserLoginRes.fail(message));

		SessionUser sessionUser = SessionUser.builder()
			.user(user)
			.build();

		when(sessionServ.getUser())
			.thenReturn(sessionUser);

		//when
		ResultActions res = mockMvc.perform(
			post("/user/login")
				.contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(req))
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.login").value("false"))
			.andExpect(jsonPath("$.message").value(message));

		verify(userServ, times(1)).login(any(UserLoginReq.class), any(SessionUser.class));
		verify(sessionServ, times(1)).getUser();
	}

}
