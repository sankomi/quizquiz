package sanko.quiz.quiz;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizUpdateRes {

	private boolean update;
	private String message;
	private String title;

	@Builder
	public QuizUpdateRes(boolean update, String message, String title) {
		this.update = update;
		this.message = message;
		this.title = title;
	}

	public static QuizUpdateRes success(Quiz quiz) {
		return QuizUpdateRes.builder()
			.update(true)
			.title(quiz.title())
			.build();
	}

	public static QuizUpdateRes fail(String message) {
		return QuizUpdateRes.builder()
			.update(false)
			.message(message)
			.build();
	}

}
