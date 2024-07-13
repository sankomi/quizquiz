package sanko.quiz.question;

import lombok.*; //Getter, AllArgsConstructor, NoArgsConstructor
import lombok.experimental.*; //SuperBuilder, Accessors
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.common.Response;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuestionUpdateRes extends Response {

	private String text;

	public static QuestionUpdateRes success(Question question) {
		return QuestionUpdateRes.builder()
			.ok(true)
			.text(question.text())
			.build();
	}

}
