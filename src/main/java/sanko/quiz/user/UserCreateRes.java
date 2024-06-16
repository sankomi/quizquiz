package sanko.quiz.user;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserCreateRes {

	private boolean create;
	private String message;

	@Builder
	public UserCreateRes(boolean create, String message) {
		this.create = create;
		this.message = message;
	}

	public static UserCreateRes success() {
		return UserCreateRes.builder()
			.create(true)
			.build();
	}

	public static UserCreateRes fail(String message) {
		return UserCreateRes.builder()
			.create(false)
			.message(message)
			.build();
	}

}
