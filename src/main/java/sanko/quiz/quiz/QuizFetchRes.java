package sanko.quiz.quiz;

import java.util.*; //UUID, List, Collections
import java.util.stream.Collectors;

import lombok.*; //Getter, Builder, AllArgsConstructor
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.question.QuestionFetchRes;

@AllArgsConstructor
@Builder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizFetchRes {

	private boolean fetch;
	private String message;

	private UUID quizId;
	private String title;
	private List<QuestionFetchRes> questions;
	private Boolean open;
	private Boolean shuffleQuestions;
	private Boolean shuffleAnswers;

	public static QuizFetchRes success(Quiz quiz) {
		if (quiz.shuffleQuestions()) {
			Collections.shuffle(quiz.questions());
		}
		if (quiz.shuffleAnswers()) {
			quiz.questions().forEach(question -> {
				Collections.shuffle(question.answers());
			});
		}

		List<QuestionFetchRes> questions = quiz.questions()
			.stream()
			.map(QuestionFetchRes::new)
			.collect(Collectors.toList());

		return QuizFetchRes.builder()
			.fetch(true)
			.quizId(quiz.quizId())
			.title(quiz.title())
			.questions(questions)
			.open(quiz.open())
			.shuffleQuestions(quiz.shuffleQuestions())
			.shuffleAnswers(quiz.shuffleAnswers())
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
