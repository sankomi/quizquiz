package sanko.quiz.quiz;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizCreateRes {

	private boolean create;
	private String message;

	private Long quizId;

	@Builder
	public QuizCreateRes(boolean create, Long quizId, String message) {
		this.create = create;
		this.quizId = quizId;
		this.message = message;
	}

	public static QuizCreateRes success(Quiz quiz) {
		return QuizCreateRes.builder()
			.create(true)
			.quizId(quiz.id())
			.build();
	}

	public static QuizCreateRes fail(String message) {
		return QuizCreateRes.builder()
			.create(false)
			.message(message)
			.build();
	}

}
