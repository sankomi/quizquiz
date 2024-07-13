package sanko.quiz.user;

import lombok.*; //Getter, NoArgsConstructor
import lombok.experimental.*; //SuperBuilder, Accessors
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.common.Response;

@NoArgsConstructor
@SuperBuilder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserLoginRes extends Response {

	public static UserLoginRes success() {
		return UserLoginRes.builder()
			.ok(true)
			.build();
	}

}
