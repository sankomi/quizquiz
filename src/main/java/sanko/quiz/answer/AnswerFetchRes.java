package sanko.quiz.answer;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class AnswerFetchRes {

	private Long answerId;
	private Long number;
	private String text;
	private Boolean correct;

	public AnswerFetchRes(Answer answer) {
		this.answerId = answer.id();
		this.number = answer.number();
		this.text = answer.text();
		this.correct = answer.correct();
	}

}
