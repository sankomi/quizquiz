package sanko.quiz.quiz;

import java.util.List;
import java.util.stream.Collectors;

import lombok.*; //Getter, AllArgsConstructor, NoArgsConstructor
import lombok.experimental.*; //SuperBuilder, Accessors
import com.fasterxml.jackson.annotation.JsonProperty;

import sanko.quiz.common.Response;
import sanko.quiz.quiz.QuizListRes;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizListRes extends Response {

	private List<QuizFetchRes> quizs;

	public static QuizListRes success(List<Quiz> list) {
		List<QuizFetchRes> quizs = list.stream()
			.map(QuizFetchRes::simple)
			.collect(Collectors.toList());

		return QuizListRes.builder()
			.ok(true)
			.quizs(quizs)
			.build();
	}

}
