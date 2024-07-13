package sanko.quiz.quiz;

import java.util.UUID;

import lombok.*; //Getter, AllArgsConstructor, NoArgsConstructor
import lombok.experimental.*; //SuperBuilder, Accessors
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.common.Response;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizCreateRes extends Response {

	private UUID quizId;

	public static QuizCreateRes success(Quiz quiz) {
		return QuizCreateRes.builder()
			.ok(true)
			.quizId(quiz.quizId())
			.build();
	}

}
