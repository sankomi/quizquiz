package sanko.quiz.quiz;

import java.util.List;
import java.util.stream.Collectors;

import lombok.*; //Getter, Builder
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.quiz.QuizListRes;

@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizListRes {

	private boolean list;
	private String message;

	private List<QuizFetchRes> quizs;

	@Builder
	public QuizListRes(boolean list, String message, List<QuizFetchRes> quizs) {
		this.list = list;
		this.message = message;
		this.quizs = quizs;
	}

	public static QuizListRes success(List<Quiz> list) {
		List<QuizFetchRes> quizs = list.stream()
			.map(QuizFetchRes::simple)
			.collect(Collectors.toList());

		return QuizListRes.builder()
			.list(true)
			.quizs(quizs)
			.build();
	}

	public static QuizListRes fail(String message) {
		return QuizListRes.builder()
			.list(false)
			.message(message)
			.build();
	}

}
