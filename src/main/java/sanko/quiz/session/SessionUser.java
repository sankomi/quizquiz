package sanko.quiz.session;

import java.io.Serializable;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;

import sanko.quiz.user.User;

@Accessors(fluent = true)
@Getter
public class SessionUser implements Serializable {

	private Long id;

	@Builder
	public SessionUser(User user) {
		id = user.id();
	}

}
