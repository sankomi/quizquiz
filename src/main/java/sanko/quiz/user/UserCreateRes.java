package sanko.quiz.user;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserCreateRes {

	private boolean create;
	private String key;
	private String message;

	@Builder
	public UserCreateRes(boolean create, String key, String message) {
		this.create = create;
		this.key = key;
		this.message = message;
	}

	public static UserCreateRes success(String key) {
		return UserCreateRes.builder()
			.create(true)
			.key(key)
			.build();
	}

	public static UserCreateRes fail(String message) {
		return UserCreateRes.builder()
			.create(false)
			.message(message)
			.build();
	}

}
