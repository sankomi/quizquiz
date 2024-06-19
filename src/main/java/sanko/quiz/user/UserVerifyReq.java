package sanko.quiz.user;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class UserVerifyReq {

	private String username;
	private String password;

	@Builder
	public UserVerifyReq(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
