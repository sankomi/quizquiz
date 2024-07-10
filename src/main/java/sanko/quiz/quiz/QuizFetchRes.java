package sanko.quiz.quiz;

import java.util.*; //UUID, Set
import java.util.stream.Collectors;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.question.QuestionFetchRes;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizFetchRes {

	private boolean fetch;
	private String message;

	private UUID quizId;
	private String title;
	private Set<QuestionFetchRes> questions;
	private Boolean open;

	@Builder
	public QuizFetchRes(boolean fetch, String message, UUID quizId, String title, Set<QuestionFetchRes> questions, Boolean open) {
		this.fetch = fetch;
		this.message = message;
		this.quizId = quizId;
		this.title = title;
		this.questions = questions;
		this.open = open;
	}

	public static QuizFetchRes success(Quiz quiz) {
		Set<QuestionFetchRes> questions = quiz.questions()
			.stream()
			.map(QuestionFetchRes::new)
			.collect(Collectors.toSet());

		return QuizFetchRes.builder()
			.fetch(true)
			.quizId(quiz.quizId())
			.title(quiz.title())
			.questions(questions)
			.open(quiz.open())
			.build();
	}

	public static QuizFetchRes simple(Quiz quiz) {
		return QuizFetchRes.builder()
			.quizId(quiz.quizId())
			.title(quiz.title())
			.open(quiz.open())
			.build();
	}

	public static QuizFetchRes fail(String message) {
		return QuizFetchRes.builder()
			.fetch(false)
			.message(message)
			.build();
	}

}
