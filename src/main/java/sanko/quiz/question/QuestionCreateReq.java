package sanko.quiz.question;

import java.util.UUID;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuestionCreateReq {

	private UUID quizId;

	@Builder
	public QuestionCreateReq(@JsonProperty("quizId") UUID quizId) {
		this.quizId = quizId;
	}

}
