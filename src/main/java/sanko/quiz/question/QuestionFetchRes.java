package sanko.quiz.question;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuestionFetchRes {

	private Long questionId;
	private String text;

	public QuestionFetchRes(Question question) {
		this.questionId = question.id();
		this.text = question.text();
	}

}
