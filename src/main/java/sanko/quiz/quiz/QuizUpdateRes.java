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
	private Boolean open;

	@Builder
	public QuizUpdateRes(boolean update, String message, String title, Boolean open) {
		this.update = update;
		this.message = message;
		this.title = title;
		this.open = open;
	}

	public static QuizUpdateRes success(Quiz quiz) {
		return QuizUpdateRes.builder()
			.update(true)
			.title(quiz.title())
			.open(quiz.open())
			.build();
	}

	public static QuizUpdateRes fail(String message) {
		return QuizUpdateRes.builder()
			.update(false)
			.message(message)
			.build();
	}

}
