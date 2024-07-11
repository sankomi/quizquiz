package sanko.quiz.quiz;

import java.util.UUID;

import lombok.*; //Getter, Builder, AllArgsConstructor
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
@AllArgsConstructor
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizUpdateReq {

	private UUID quizId;
	private String title;
	private Boolean open;
	private Boolean shuffleQuestions;
	private Boolean shuffleAnswers;

}
