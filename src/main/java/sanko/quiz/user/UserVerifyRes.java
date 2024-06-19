package sanko.quiz.user;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserVerifyRes {

	private boolean verify;
	private String message;

	@Builder
	public UserVerifyRes(boolean verify, String message) {
		this.verify = verify;
		this.message = message;
	}

	public static UserVerifyRes success() {
		return UserVerifyRes.builder()
			.verify(true)
			.build();
	}

	public static UserVerifyRes fail(String message) {
		return UserVerifyRes.builder()
			.verify(false)
			.message(message)
			.build();
	}

}
