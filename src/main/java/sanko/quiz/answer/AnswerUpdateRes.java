package sanko.quiz.answer;

import lombok.*; //Getter, AllArgsConstructor, NoArgsConstructor
import lombok.experimental.*; //SuperBuilder, Accessors
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.common.Response;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class AnswerUpdateRes extends Response {

	private String text;
	private Boolean correct;

	public static AnswerUpdateRes success(Answer answer) {
		return AnswerUpdateRes.builder()
			.ok(true)
			.text(answer.text())
			.correct(answer.correct())
			.build();
	}

}
