package sanko.quiz.common;

import java.lang.reflect.InvocationTargetException;

import lombok.*; //Getter, NoArgsConstructor
import lombok.experimental.*; //SuperBuilder, Accessors
import com.fasterxml.jackson.annotation.JsonProperty;

@NoArgsConstructor
@SuperBuilder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class Response {

	protected boolean ok;
	protected String message;

	public static <T extends Response> T fail(Class<T> response, String message) {
		T res;

		try {
			res = response.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		res.ok = false;
		res.message = message;
		return res;
	}

}
