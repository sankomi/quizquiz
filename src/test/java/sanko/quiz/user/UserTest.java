package sanko.quiz.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*; //assertEquals, assertFalse

class UserTest {

	@Test
	void testNewUser() {
		//given
		String username = "username";
		String key = "key";

		//when
		User user = User.builder()
			.username(username)
			.key(key)
			.build();

		assertEquals(username, user.username());
		assertEquals(key, user.key());
		assertFalse(user.verified());
	}

}
