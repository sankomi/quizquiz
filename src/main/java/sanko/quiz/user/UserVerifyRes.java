package sanko.quiz.user;

import lombok.*; //Getter, NoArgsConstructor
import lombok.experimental.*; //SuperBuilder, Accessors
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.common.Response;

@NoArgsConstructor
@SuperBuilder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserVerifyRes extends Response {

	public static UserVerifyRes success() {
		return UserVerifyRes.builder()
			.ok(true)
			.build();
	}

}
