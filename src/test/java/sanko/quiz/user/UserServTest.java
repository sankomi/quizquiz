package sanko.quiz.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*; //when, verify, times, never
import static org.mockito.ArgumentMatchers.*; //eq, any, anyString;
import static org.junit.jupiter.api.Assertions.*; //assertTrue, assertFalse, assertNull, assertEquals

@ExtendWith(SpringExtension.class)
@Import(UserServ.class)
class UserServTest {

	@Autowired
	private UserServ userServ;

	@MockBean
	private UserRepo userRepo;

	@Test
	void testUserCreate() {
		//given
		String username = "username";
		String password = "password";

		UserCreateReq req = UserCreateReq.builder()
			.username(username)
			.password(password)
			.build();

		User user = User.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(eq(username)))
			.thenReturn(null);

		when(userRepo.save(any(User.class)))
			.thenReturn(user);

		//when
		UserCreateRes res = userServ.create(req);

		//then
		assertTrue(res.create());
		assertNull(res.message());

		verify(userRepo, times(1)).findOneByUsername(username);
		verify(userRepo, times(1)).save(any(User.class));
	}

	@Test
	void testUserCreateExists() {
		//given
		String username = "username";
		String password = "password";

		UserCreateReq req = UserCreateReq.builder()
			.username(username)
			.password(password)
			.build();

		User user = User.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(eq(username)))
			.thenReturn(user);

		//when
		UserCreateRes res = userServ.create(req);

		//then
		assertFalse(res.create());
		assertEquals(res.message(), "username exists");

		verify(userRepo, times(1)).findOneByUsername(username);
		verify(userRepo, never()).save(any(User.class));
	}

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

		verify(userRepo, times(1)).findOneByUsername(username);
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

		verify(userRepo, times(1)).findOneByUsername(username);
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

		verify(userRepo, times(1)).findOneByUsername(username);
	}

}
