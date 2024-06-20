package sanko.quiz.user;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserCreateRes {

	private boolean create;
	private String key;
	private String image;
	private String message;

	@Builder
	public UserCreateRes(boolean create, String key, String image, String message) {
		this.create = create;
		this.key = key;
		this.image = image;
		this.message = message;
	}

	public static UserCreateRes success(String key, String image) {
		return UserCreateRes.builder()
			.create(true)
			.key(key)
			.image(image)
			.build();
	}

	public static UserCreateRes fail(String message) {
		return UserCreateRes.builder()
			.create(false)
			.message(message)
			.build();
	}

}
