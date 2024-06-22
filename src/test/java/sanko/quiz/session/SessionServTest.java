package sanko.quiz.session;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import sanko.quiz.user.User;

import static org.mockito.Mockito.*; //when, verify, times
import static org.mockito.ArgumentMatchers.*; //eq, argThat
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(SpringExtension.class)
@Import(SessionServ.class)
class SessionServTest {

	@Autowired
	private SessionServ sessionServ;

	@MockBean
	private HttpSession httpSession;

	@Test
	void testGetUser() {
		//given
		Long id = 1L;
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", id);

		SessionUser currentUser = SessionUser.builder()
			.user(user)
			.build();

		when(httpSession.getAttribute(eq("user")))
			.thenReturn(currentUser);

		//when
		SessionUser sessionUser = sessionServ.getUser();

		//then
		assertEquals(id, sessionUser.id());

		verify(httpSession, times(1)).getAttribute(eq("user"));
	}

	@Test
	void testSetUser() {
		//given
		Long id = 1L;
		String username = "username";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "id", id);

		//when
		sessionServ.setUser(user);

		//then
		verify(httpSession, times(1)).setAttribute(
			eq("user"),
			argThat((SessionUser sessionUser) -> sessionUser.id() == id)
		);
	}

	@Test
	void testRemoveUser() {
		//given

		//when
		sessionServ.removeUser();

		//then
		verify(httpSession, times(1)).removeAttribute(eq("user"));
	}

}
