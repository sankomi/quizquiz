package sanko.quiz.quiz;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@AllArgsConstructor
@Builder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizUpdateRes {

	private boolean update;
	private String message;
	private String title;
	private Boolean open;
	private Boolean shuffleQuestions;
	private Boolean shuffleAnswers;

	public static QuizUpdateRes success(Quiz quiz) {
		return QuizUpdateRes.builder()
			.update(true)
			.title(quiz.title())
			.open(quiz.open())
			.shuffleQuestions(quiz.shuffleQuestions())
			.shuffleAnswers(quiz.shuffleAnswers())
			.build();
	}

	public static QuizUpdateRes fail(String message) {
		return QuizUpdateRes.builder()
			.update(false)
			.message(message)
			.build();
	}

}
