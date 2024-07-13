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
public class QuestionCreateRes extends Response {

	private Long questionId;
	private Long number;

	public static QuestionCreateRes success(Question question) {
		return QuestionCreateRes.builder()
			.ok(true)
			.questionId(question.id())
			.number(question.number())
			.build();
	}

}
