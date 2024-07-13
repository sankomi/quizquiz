package sanko.quiz.user;

import lombok.*; //Getter, NoArgsConstructor
import lombok.experimental.*; //SuperBuilder, Accessors
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.common.Response;

@NoArgsConstructor
@SuperBuilder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserLogoutRes extends Response {

	public static UserLogoutRes success() {
		return UserLogoutRes.builder()
			.ok(true)
			.build();
	}

}
