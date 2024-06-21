package sanko.quiz.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

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
		UserCreateRes res = userServ.create(req);

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
		UserCreateRes res = userServ.create(req);

		//then
		assertFalse(res.create());
		assertNull(res.key());
		assertNull(res.image());
		assertEquals("username exists", res.message());

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
		UserVerifyRes res = userServ.verify(req);

		//then
		assertTrue(res.verify());
		assertNull(res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(passwordServ, times(1)).verify(eq(key), eq(password));
		verify(userRepo, times(1)).save(eq(user));
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
		UserVerifyRes res = userServ.verify(req);

		//then
		assertFalse(res.verify());
		assertEquals("username not found", res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(passwordServ, never()).verify(anyString(), anyString());
		verify(userRepo, never()).save(any(User.class));
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
		UserVerifyRes res = userServ.verify(req);

		//then
		assertFalse(res.verify());
		assertEquals("password incorrect", res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(passwordServ, times(1)).verify(eq(key), eq(password));
		verify(userRepo, never()).save(any(User.class));
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
		UserLoginRes res = userServ.login(req);

		//then
		assertTrue(res.login());
		assertNull(res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(passwordServ, times(1)).verify(eq(key), eq(password));
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
		UserLoginRes res = userServ.login(req);

		//then
		assertFalse(res.login());
		assertEquals("username not found", res.message());

		verify(userRepo, times(1)).findOneByUsername(anyString());
		verify(passwordServ, never()).verify(anyString(), anyString());
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
		UserLoginRes res = userServ.login(req);

		//then
		assertFalse(res.login());
		assertEquals("user not verified", res.message());

		verify(userRepo, times(1)).findOneByUsername(anyString());
		verify(passwordServ, never()).verify(anyString(), anyString());
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
		UserLoginRes res = userServ.login(req);

		//then
		assertFalse(res.login());
		assertEquals("password incorrect", res.message());

		verify(userRepo, times(1)).findOneByUsername(eq(username));
		verify(passwordServ, times(1)).verify(eq(key), eq(password));
	}

}
