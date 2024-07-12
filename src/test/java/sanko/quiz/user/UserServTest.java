package sanko.quiz.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import sanko.quiz.Const;
import sanko.quiz.common.QrServ;
import sanko.quiz.session.SessionServ;

import static org.mockito.Mockito.*; //when, verify, times, never
import static org.mockito.ArgumentMatchers.*; //eq, any, contains, anyString;
import static org.junit.jupiter.api.Assertions.*; //assertTrue, assertFalse, assertNull, assertEquals
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(SpringExtension.class)
@Import(UserServ.class)
class UserServTest {

	@Autowired
	private UserServ userServ;

	@MockBean
	private UserRepo userRepo;

	@MockBean
	private PasswordServ passwordServ;

	@MockBean
	private QrServ qrServ;

	@MockBean
	private SessionServ sessionServ;

	@Test
	void testUserCreate() {
		//given
		String username = "username";
		String key = "key";
		String image = "image";

		UserCreateReq req = UserCreateReq.builder()
			.username(username)
			.build();

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		when(userRepo.findOneByUsername(eq(username)))
			.thenReturn(null);

		when(passwordServ.createKey())
			.thenReturn(key);

		when(qrServ.create(anyString()))
			.thenReturn(image);

		when(userRepo.save(any(User.class)))
			.thenReturn(user);

		//when
		UserCreateRes res = userServ.create(req, null);

		//then
		assertTrue(res.create());
		assertEquals(key, res.key());
		assertEquals(image, res.image());
		assertNull(res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(userRepo, times(1)).save(any(User.class));
		verify(passwordServ, times(1)).createKey();
		verify(qrServ, times(1)).create(contains(key));
	}

	@Test
	void testUserCreateLoggedIn() {
		//given
		String username = "username";
		String key = "key";

		UserCreateReq req = UserCreateReq.builder()
			.username(username)
			.build();

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		//when
		UserCreateRes res = userServ.create(req, user);

		//then
		assertFalse(res.create());
		assertEquals(Const.ALREADY_LOGGED_IN, res.message());

		verify(userRepo, never()).findOneByUsername(anyString());
		verify(userRepo, never()).save(any(User.class));
		verify(passwordServ, never()).createKey();
		verify(qrServ, never()).create(anyString());
	}

	@Test
	void testUserCreateExists() {
		//given
		String username = "username";
		String key = "key";

		UserCreateReq req = UserCreateReq.builder()
			.username(username)
			.build();

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		when(userRepo.findOneByUsername(eq(username)))
			.thenReturn(user);

		//when
		UserCreateRes res = userServ.create(req, null);

		//then
		assertFalse(res.create());
		assertNull(res.key());
		assertNull(res.image());
		assertEquals(Const.USERNAME_EXISTS, res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(userRepo, never()).save(any(User.class));
		verify(passwordServ, never()).createKey();
		verify(qrServ, never()).create(anyString());
	}

	@Test
	void testUserVerify() {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		UserVerifyReq req = UserVerifyReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(anyString()))
			.thenReturn(user);

		when(passwordServ.verify(eq(key), eq(password)))
			.thenReturn(true);

		when(userRepo.save(eq(user)))
			.thenReturn(user);

		//when
		UserVerifyRes res = userServ.verify(req, null);

		//then
		assertTrue(res.verify());
		assertNull(res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(passwordServ, times(1)).verify(eq(key), eq(password));
		verify(userRepo, times(1)).save(eq(user));
		verify(sessionServ, times(1)).setUser(eq(user));
	}

	@Test
	void testUserVerifyLoggedIn() {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		UserVerifyReq req = UserVerifyReq.builder()
			.username(username)
			.password(password)
			.build();

		//when
		UserVerifyRes res = userServ.verify(req, user);

		//then
		assertFalse(res.verify());
		assertEquals(Const.ALREADY_LOGGED_IN, res.message());

		verify(userRepo, never()).findOneByUsername(anyString());
		verify(passwordServ, never()).verify(anyString(), anyString());
		verify(userRepo, never()).save(any(User.class));
		verify(sessionServ, never()).setUser(any(User.class));
	}

	@Test
	void testUserVerifyNotFound() {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		UserVerifyReq req = UserVerifyReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(anyString()))
			.thenReturn(null);

		//when
		UserVerifyRes res = userServ.verify(req, null);

		//then
		assertFalse(res.verify());
		assertEquals(Const.NOT_FOUND, res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(passwordServ, never()).verify(anyString(), anyString());
		verify(userRepo, never()).save(any(User.class));
		verify(sessionServ, never()).setUser(any(User.class));
	}

	@Test
	void testUserVerifyIncorrectPassword() {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		UserVerifyReq req = UserVerifyReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(anyString()))
			.thenReturn(user);

		when(passwordServ.verify(eq(key), eq(password)))
			.thenReturn(false);

		//when
		UserVerifyRes res = userServ.verify(req, null);

		//then
		assertFalse(res.verify());
		assertEquals(Const.PASSWORD_INCORRECT, res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(passwordServ, times(1)).verify(eq(key), eq(password));
		verify(userRepo, never()).save(any(User.class));
		verify(sessionServ, never()).setUser(any(User.class));
	}

	@Test
	void testUserLogin() {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "verified", true);

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(anyString()))
			.thenReturn(user);

		when(passwordServ.verify(eq(key), eq(password)))
			.thenReturn(true);

		//when
		UserLoginRes res = userServ.login(req, null);

		//then
		assertTrue(res.login());
		assertNull(res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(passwordServ, times(1)).verify(eq(key), eq(password));
		verify(sessionServ, times(1)).setUser(eq(user));
	}

	@Test
	void testUserLoginLoggedIn() {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "verified", true);

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		//when
		UserLoginRes res = userServ.login(req, user);

		//then
		assertFalse(res.login());
		assertEquals(Const.ALREADY_LOGGED_IN, res.message());

		verify(userRepo, never()).findOneByUsername(anyString());
		verify(passwordServ, never()).verify(anyString(), anyString());
		verify(sessionServ, never()).setUser(any(User.class));
	}

	@Test
	void testUserLoginNotFound() {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(anyString()))
			.thenReturn(null);

		//when
		UserLoginRes res = userServ.login(req, null);

		//then
		assertFalse(res.login());
		assertEquals(Const.NOT_FOUND, res.message());

		verify(userRepo, times(1)).findOneByUsername(anyString());
		verify(passwordServ, never()).verify(anyString(), anyString());
		verify(sessionServ, never()).setUser(any(User.class));
	}

	@Test
	void testUserLoginNotVerified() {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "verified", false);

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(anyString()))
			.thenReturn(user);

		//when
		UserLoginRes res = userServ.login(req, null);

		//then
		assertFalse(res.login());
		assertEquals(Const.NOT_VERIFIED, res.message());

		verify(userRepo, times(1)).findOneByUsername(anyString());
		verify(passwordServ, never()).verify(anyString(), anyString());
		verify(sessionServ, never()).setUser(any(User.class));
	}

	@Test
	void testUserLoginIncorrectPassword() {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();
		setField(user, "verified", true);

		UserLoginReq req = UserLoginReq.builder()
			.username(username)
			.password(password)
			.build();

		when(userRepo.findOneByUsername(anyString()))
			.thenReturn(user);

		when(passwordServ.verify(eq(key), eq(password)))
			.thenReturn(false);

		//when
		UserLoginRes res = userServ.login(req, null);

		//then
		assertFalse(res.login());
		assertEquals(Const.PASSWORD_INCORRECT, res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(passwordServ, times(1)).verify(eq(key), eq(password));
		verify(sessionServ, never()).setUser(any(User.class));
	}

	@Test
	void testUserLogout() {
		//given
		String username = "username";
		String password = "password";
		String key = "key";

		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		//when
		UserLogoutRes res = userServ.logout(user);

		//then
		assertTrue(res.logout());
		assertNull(res.message());

		verify(sessionServ, times(1)).removeUser();
	}

	@Test
	void testUserLogoutNotLoggedIn() {
		//given

		//when
		UserLogoutRes res = userServ.logout(null);

		//then
		assertFalse(res.logout());
		assertEquals(Const.NOT_LOGGED_IN, res.message());

		verify(sessionServ, never()).removeUser();
	}

}
