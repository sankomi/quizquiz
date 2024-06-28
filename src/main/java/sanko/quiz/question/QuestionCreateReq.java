package sanko.quiz.question;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuestionCreateReq {

	private Long quizId;

	@Builder
	public QuestionCreateReq(@JsonProperty("quizId") Long quizId) {
		this.quizId = quizId;
	}

}
