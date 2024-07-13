package sanko.quiz.quiz;

import lombok.*; //Getter, AllArgsConstructor, NoArgsConstructor
import lombok.experimental.*; //SuperBuilder, Accessors
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.common.Response;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizUpdateRes extends Response {

	private String title;
	private Boolean open;
	private Boolean shuffleQuestions;
	private Boolean shuffleAnswers;

	public static QuizUpdateRes success(Quiz quiz) {
		return QuizUpdateRes.builder()
			.ok(true)
			.title(quiz.title())
			.open(quiz.open())
			.shuffleQuestions(quiz.shuffleQuestions())
			.shuffleAnswers(quiz.shuffleAnswers())
			.build();
	}

}
