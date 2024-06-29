package sanko.quiz.question;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuestionUpdateReq {

	private Long quizId;
	private Long questionId;
	private String text;

	@Builder
	public QuestionUpdateReq(Long quizId, Long questionId, String text) {
		this.quizId = quizId;
		this.questionId = questionId;
		this.text = text;
	}

}
