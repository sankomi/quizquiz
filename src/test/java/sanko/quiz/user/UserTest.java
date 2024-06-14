package sanko.quiz.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

	@Test
	void testNewUser() {
		//given
		String username = "username";
		String password = "password";

		//when
		User user = User.builder()
			.username(username)
			.password(password)
			.build();

		assertEquals(username, user.username());
		assertEquals(password, user.password());
	}

}
