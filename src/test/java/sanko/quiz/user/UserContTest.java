package sanko.quiz.user;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.*; //MockMvc, ResultActions
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import sanko.quiz.session.*; //SessionServ, SessionUser

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; //post, put, delete
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
	void testUserCreate() throws Exception {
		//given
		String username = "username";
		String password = "password";
		String key = "key";
		String image = "image";

		UserCreateReq req = UserCreateReq.builder()
			.username(username)
			.build();

		when(userServ.create(any(UserCreateReq.class), eq(null)))
			.thenReturn(UserCreateRes.success(key, image));

		when(sessionServ.getUser())
			.thenReturn(null);

		//when
		ResultActions res = mockMvc.perform(
			post("/user/create")
				.contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(req))
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.create").value("true"))
			.andExpect(jsonPath("$.key").value(key))
			.andExpect(jsonPath("$.image").value(image));

		verify(userServ, times(1)).create(any(UserCreateReq.class), eq(null));
		verify(sessionServ, times(1)).getUser();
	}

	@Test
	void testUserVerify() throws Exception {
		//given
		String username = "username";
		String password = "password";

		UserVerifyReq req = UserVerifyReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userServ.verify(any(UserVerifyReq.class), eq(null)))
			.thenReturn(UserVerifyRes.success());

		when(sessionServ.getUser())
			.thenReturn(null);

		//when
		ResultActions res = mockMvc.perform(
			put("/user/verify")
				.contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(req))
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.verify").value("true"));

		verify(userServ, times(1)).verify(any(UserVerifyReq.class), eq(null));
		verify(sessionServ, times(1)).getUser();
	}

	@Test
	void testUserLogin() throws Exception {
		//given
		String username = "username";
		String password = "password";

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userServ.login(any(UserLoginReq.class), eq(null)))
			.thenReturn(UserLoginRes.success());

		when(sessionServ.getUser())
			.thenReturn(null);

		//when
		ResultActions res = mockMvc.perform(
			post("/user/login")
				.contentType("application/json")
				.content(new ObjectMapper().writeValueAsString(req))
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.login").value("true"));

		verify(userServ, times(1)).login(any(UserLoginReq.class), eq(null));
		verify(sessionServ, times(1)).getUser();
	}

	@Test
	void testUserLoginFail() throws Exception {
		//given
		String username = "username";
		String password = "password";
		String message = "message";

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userServ.login(any(UserLoginReq.class), eq(null)))
			.thenReturn(UserLoginRes.fail(message));

		when(sessionServ.getUser())
			.thenReturn(null);

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

		verify(userServ, times(1)).login(any(UserLoginReq.class), eq(null));
		verify(sessionServ, times(1)).getUser();
	}

	@Test
	void testUserLogout() throws Exception {
		//given
		Long id = 1L;
		String username = "username";

		User user = User.builder()
			.username(username)
			.build();
		setField(user, "id", id);

		SessionUser sessionUser = SessionUser.builder()
			.user(user)
			.build();

		when(sessionServ.getUser())
			.thenReturn(user);

		when(userServ.logout(eq(user)))
			.thenReturn(UserLogoutRes.success());

		//when
		ResultActions res = mockMvc.perform(
			delete("/user/login")
				.contentType("application/json")
		);

		//then
		res.andExpect(status().isOk())
			.andExpect(jsonPath("$.logout").value("true"));

		verify(userServ, times(1)).logout(eq(user));
		verify(sessionServ, times(1)).getUser();
	}

}
