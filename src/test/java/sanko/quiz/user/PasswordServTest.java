package sanko.quiz.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.context.annotation.Import;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Import(PasswordServ.class)
class PasswordServTest {

	@Autowired
	private PasswordServ passwordServ;

	@Test
	void testPasswordFromCount() {
		//given
		String key = "SQHGCGSKLKWLK6KNKWH5Y4RBY7BO7T43";
		long count = 57307514L;
		String correct = "062334";

		//when
		String password = passwordServ.fromCount(key, count);

		//then
		assertEquals(correct, password);
	}

}
