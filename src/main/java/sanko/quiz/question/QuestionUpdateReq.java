package sanko.quiz.question;

import java.util.UUID;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuestionUpdateReq {

	private UUID quizId;
	private Long questionId;
	private String text;

	@Builder
	public QuestionUpdateReq(UUID quizId, Long questionId, String text) {
		this.quizId = quizId;
		this.questionId = questionId;
		this.text = text;
	}

}
