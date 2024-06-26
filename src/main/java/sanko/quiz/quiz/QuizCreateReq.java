package sanko.quiz.quiz;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizCreateReq {

	private String title;

	@Builder
	public QuizCreateReq(@JsonProperty("title") String title) {
		this.title = title;
	}

}
