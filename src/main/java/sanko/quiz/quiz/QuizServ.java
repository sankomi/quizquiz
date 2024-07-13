package sanko.quiz.quiz;

import java.util.*; //UUID, List

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;

import sanko.quiz.Const;
import sanko.quiz.common.*; //Response, QrServ;
import sanko.quiz.user.User;

@RequiredArgsConstructor
@Service
public class QuizServ {

	@Value("${env.base-url}")
	private String baseUrl;

	private final QuizRepo quizRepo;
	private final QrServ qrServ;

	@Transactional
	public QuizCreateRes create(User currentUser) {
		if (currentUser == null) return Response.fail(QuizCreateRes.class, Const.NOT_LOGGED_IN);

		Quiz quiz = Quiz.builder()
			.user(currentUser)
			.title("untitled quiz")
			.build();
		quiz.setQuizId(UUID.randomUUID());
		quiz = quizRepo.save(quiz);

		return QuizCreateRes.success(quiz);
	}

	public QuizListRes list(User currentUser) {
		if (currentUser == null) return Response.fail(QuizListRes.class, Const.NOT_LOGGED_IN);

		List<Quiz> list = quizRepo.findByUser(currentUser);

		return QuizListRes.success(list);
	}

	public QuizFetchRes fetch(UUID quizId, User currentUser) {
		Quiz quiz = quizRepo.findOneByQuizId(quizId);
		if (quiz == null) return Response.fail(QuizFetchRes.class, Const.NOT_FOUND);

		if (!quiz.open()) {
			if (currentUser == null) return Response.fail(QuizFetchRes.class, Const.NOT_FOUND);

			if (!quiz.user().id().equals(currentUser.id())) {
				return Response.fail(QuizFetchRes.class, Const.NOT_FOUND);
			}
		}

		return QuizFetchRes.success(quiz);
	}

	public QuizQrRes qr(UUID quizId, User currentUser) {
		Quiz quiz = quizRepo.findOneByQuizId(quizId);
		if (quiz == null) return Response.fail(QuizQrRes.class, Const.NOT_FOUND);

		if (!quiz.user().id().equals(currentUser.id())) {
			return Response.fail(QuizQrRes.class, Const.NOT_FOUND);
		}

		String url = baseUrl + "/play/" + quiz.quizId().toString();
		String qr = qrServ.create(url);
		return QuizQrRes.success(qr);
	}

	public QuizUpdateRes update(QuizUpdateReq req, User currentUser) {
		if (currentUser == null) return Response.fail(QuizUpdateRes.class, Const.NOT_LOGGED_IN);

		Quiz quiz = quizRepo.findOneByQuizId(req.quizId());
		if (quiz == null) return Response.fail(QuizUpdateRes.class, Const.NOT_FOUND);
		if (!quiz.user().id().equals(currentUser.id())) return Response.fail(QuizUpdateRes.class, Const.NOT_FOUND);

		String title = req.title();
		Boolean open = req.open();
		Boolean shuffleQuestions = req.shuffleQuestions();
		Boolean shuffleAnswers = req.shuffleAnswers();

		quiz.update(title, open, shuffleQuestions, shuffleAnswers);

		return QuizUpdateRes.success(quiz);
	}

}
