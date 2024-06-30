package sanko.quiz.answer;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class AnswerUpdateReq {

	private Long quizId;
	private Long questionId;
	private Long answerId;
	private String text;
	private Boolean correct;

	@Builder
	public AnswerUpdateReq(Long quizId, Long questionId, Long answerId, String text, Boolean correct) {
		this.quizId = quizId;
		this.questionId = questionId;
		this.answerId = answerId;
		this.text = text;
		this.correct = correct;
	}

}
