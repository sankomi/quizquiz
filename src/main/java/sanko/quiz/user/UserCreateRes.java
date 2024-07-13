package sanko.quiz.user;

import lombok.*; //Getter, AllArgsConstructor, NoArgsConstructor
import lombok.experimental.*; //SuperBuilder, Accessors
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.common.Response;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserCreateRes extends Response {

	private String key;
	private String image;

	public static UserCreateRes success(String key, String image) {
		return UserCreateRes.builder()
			.ok(true)
			.key(key)
			.image(image)
			.build();
	}

}
