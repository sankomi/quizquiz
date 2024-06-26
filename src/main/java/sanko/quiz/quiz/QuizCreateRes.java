package sanko.quiz.quiz;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizCreateRes {

	private boolean create;
	private String message;

	@Builder
	public QuizCreateRes(boolean create, String message) {
		this.create = create;
		this.message = message;
	}

	public static QuizCreateRes success() {
		return QuizCreateRes.builder()
			.create(true)
			.build();
	}

	public static QuizCreateRes fail(String message) {
		return QuizCreateRes.builder()
			.create(false)
			.message(message)
			.build();
	}

}
