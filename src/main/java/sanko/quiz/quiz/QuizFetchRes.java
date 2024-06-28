package sanko.quiz.quiz;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.question.*; //Question, QuestionFetchRes

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizFetchRes {

	private boolean fetch;
	private String message;

	private Long quizId;
	private String title;
	private Set<QuestionFetchRes> questions;

	@Builder
	public QuizFetchRes(boolean fetch, String message, Long quizId, String title, Set<QuestionFetchRes> questions) {
		this.fetch = fetch;
		this.message = message;
		this.quizId = quizId;
		this.title = title;
		this.questions = questions;
	}

	public static QuizFetchRes success(Quiz quiz) {
		Set<QuestionFetchRes> questions = quiz.questions()
			.stream()
			.map(QuestionFetchRes::new)
			.collect(Collectors.toSet());

		return QuizFetchRes.builder()
			.fetch(true)
			.quizId(quiz.id())
			.title(quiz.title())
			.questions(questions)
			.build();
	}

	public static QuizFetchRes fail(String message) {
		return QuizFetchRes.builder()
			.fetch(false)
			.message(message)
			.build();
	}

}
