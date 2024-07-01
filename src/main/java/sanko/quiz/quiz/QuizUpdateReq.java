package sanko.quiz.quiz;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizUpdateReq {

	private Long quizId;
	private String title;

	@Builder
	public QuizUpdateReq(Long quizId, String title) {
		this.quizId = quizId;
		this.title = title;
	}

}
