package sanko.quiz.question;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuestionUpdateRes {

	private boolean update;
	private String message;
	private String text;

	@Builder
	public QuestionUpdateRes(boolean update, String message, String text) {
		this.update = update;
		this.message = message;
		this.text = text;
	}

	public static QuestionUpdateRes success(Question question) {
		return QuestionUpdateRes.builder()
			.update(true)
			.text(question.text())
			.build();
	}

	public static QuestionUpdateRes fail(String message) {
		return QuestionUpdateRes.builder()
			.update(false)
			.message(message)
			.build();
	}

}
