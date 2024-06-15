package sanko.quiz.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*; //when, verify, times
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.*; //assertTrue, assertFalse, assertNull, assertEquals

@ExtendWith(SpringExtension.class)
@Import(UserServ.class)
class UserServTest {

	@Autowired
	private UserServ userServ;

	@MockBean
	private UserRepo userRepo;

	@Test
	void testUserLogin() {
		//given
		String username = "username";
		String password = "password";

		User user = User.builder()
			.username(username)
			.password(password)
			.build();

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(anyString()))
			.thenReturn(user);

		//when
		UserLoginRes res = userServ.login(req);

		//then
		assertTrue(res.login());
		assertNull(res.message());
	}

	@Test
	void testUserLoginNotFound() {
		//given
		String username = "username";
		String password = "password";

		User user = User.builder()
			.username(username)
			.password(password)
			.build();

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(anyString()))
			.thenReturn(null);

		//when
		UserLoginRes res = userServ.login(req);

		//then
		assertFalse(res.login());
		assertEquals(res.message(), "username not found");
	}

	@Test
	void testUserLoginIncorrectPassword() {
		//given
		String username = "username";
		String password = "password";
		String anotherUsername = "another_username";
		String anotherPassword = "another_password";

		User user = User.builder()
			.username(username)
			.password(password)
			.build();

		User anotherUser = User.builder()
			.username(anotherUsername)
			.password(anotherPassword)
			.build();

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(anyString()))
			.thenReturn(anotherUser);

		//when
		UserLoginRes res = userServ.login(req);

		//then
		assertFalse(res.login());
		assertEquals(res.message(), "password incorrect");
	}

}
