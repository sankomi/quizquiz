package sanko.quiz.question;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuestionCreateRes {

	private boolean create;
	private String message;
	private Long questionId;
	private Long number;

	@Builder
	public QuestionCreateRes(boolean create, String message, Long questionId, Long number) {
		this.create = create;
		this.message = message;
		this.questionId = questionId;
		this.number = number;
	}

	public static QuestionCreateRes success(Question question) {
		return QuestionCreateRes.builder()
			.create(true)
			.questionId(question.id())
			.number(question.number())
			.build();
	}

	public static QuestionCreateRes fail(String message) {
		return QuestionCreateRes.builder()
			.create(false)
			.message(message)
			.build();
	}

}
