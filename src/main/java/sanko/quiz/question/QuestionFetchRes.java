package sanko.quiz.question;

import java.util.List;
import java.util.stream.Collectors;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.answer.AnswerFetchRes;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuestionFetchRes {

	private Long questionId;
	private Long number;
	private String text;
	private List<AnswerFetchRes> answers;

	public QuestionFetchRes(Question question) {
		List<AnswerFetchRes> answers = question.answers()
			.stream()
			.map(AnswerFetchRes::new)
			.collect(Collectors.toList());

		this.questionId = question.id();
		this.number = question.number();
		this.text = question.text();
		this.answers = answers;
	}

}
