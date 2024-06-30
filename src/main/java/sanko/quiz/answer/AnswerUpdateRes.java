package sanko.quiz.answer;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class AnswerUpdateRes {

	private boolean update;
	private String message;
	private String text;
	private Boolean correct;

	@Builder
	public AnswerUpdateRes(boolean update, String message, String text, Boolean correct) {
		this.update = update;
		this.message = message;
		this.text = text;
		this.correct = correct;
	}

	public static AnswerUpdateRes success(Answer answer) {
		return AnswerUpdateRes.builder()
			.update(true)
			.text(answer.text())
			.correct(answer.correct())
			.build();
	}

	public static AnswerUpdateRes fail(String message) {
		return AnswerUpdateRes.builder()
			.update(false)
			.message(message)
			.build();
	}

}
