package sanko.quiz.user;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserLoginRes {

	private boolean login;
	private String message;

	@Builder
	public UserLoginRes(boolean login, String message) {
		this.login = login;
		this.message = message;
	}

	public static UserLoginRes success() {
		return UserLoginRes.builder()
			.login(true)
			.build();
	}

	public static UserLoginRes fail(String message) {
		return UserLoginRes.builder()
			.login(false)
			.message(message)
			.build();
	}

}
