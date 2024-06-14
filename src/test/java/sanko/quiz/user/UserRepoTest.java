package sanko.quiz.user;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*; //assertTrue, assertEquals;

@DataJpaTest
class UserRepoTest {

	@Autowired
	private UserRepo userRepo;

	@Test
	void testUserSave() {
		//given
		String username = "username";
		String password = "password";
		User user = User.builder()
			.username(username)
			.password(password)
			.build();

		//when
		userRepo.save(user);

		//then
		assertTrue(user.id() > 0L);
		assertEquals(username, user.username());
		assertEquals(password, user.password());
	}

}
