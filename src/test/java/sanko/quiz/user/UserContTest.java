package sanko.quiz.user;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.*; //MockMvc, ResultActions
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; //status, jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.*; //when, verify, times
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(UserCont.class)
class UserContTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServ userServ;

	@Test
	void testUserLogin() throws Exception {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userServ.login(any(UserLoginReq.class)))
			.thenReturn(UserLoginRes.success());

		//when
		ResultActions res = mockMvc.perform(
			post("/user/login")
				.contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(req))
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.login").value("true"));

		verify(userServ, times(1)).login(any(UserLoginReq.class));
	}

	@Test
	void testUserLoginFail() throws Exception {
		//given
		String username = "username";
		String password = "password";
		String key = "key";
		String message = "message";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userServ.login(any(UserLoginReq.class)))
			.thenReturn(UserLoginRes.fail(message));

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

		verify(userServ, times(1)).login(any(UserLoginReq.class));
	}

}
