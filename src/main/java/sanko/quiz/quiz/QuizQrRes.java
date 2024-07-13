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
public class QuizQrRes extends Response {

	private String qrCode;

	public static QuizQrRes success(String qrCode) {
		return QuizQrRes.builder()
			.ok(true)
			.qrCode(qrCode)
			.build();
	}

}
