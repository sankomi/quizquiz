package sanko.quiz.quiz;

import lombok.*; //Getter, Builder, AllArgsConstructor
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonProperty;

@AllArgsConstructor
@Builder
@Accessors(fluent = true)
@Getter(onMethod_ = @JsonProperty)
public class QuizQrRes {

	private boolean qr;
	private String message;

	private String qrCode;

	public static QuizQrRes success(String qrCode) {
		return QuizQrRes.builder()
			.qr(true)
			.qrCode(qrCode)
			.build();
	}

	public static QuizQrRes fail(String message) {
		return QuizQrRes.builder()
			.qr(false)
			.message(message)
			.build();
	}

}
