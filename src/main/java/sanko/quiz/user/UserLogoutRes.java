package sanko.quiz.user;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserLogoutRes {

	private boolean logout;
	private String message;

	@Builder
	public UserLogoutRes(boolean logout, String message) {
		this.logout = logout;
		this.message = message;
	}

	public static UserLogoutRes success() {
		return UserLogoutRes.builder()
			.logout(true)
			.build();
	}

	public static UserLogoutRes fail(String message) {
		return UserLogoutRes.builder()
			.logout(false)
			.message(message)
			.build();
	}

}
