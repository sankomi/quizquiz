package sanko.quiz.quiz;

import java.util.*; //UUID, List
import jakarta.persistence.*; //Entity, Table, Id, Column, GeneratedValue, GenerationType, JoinColumn, ManyToOne, OneToMany, OrderBy

import lombok.*; //NoArgsConstructor, Getter, Builder
import lombok.experimental.Accessors;

import sanko.quiz.user.User;

import sanko.quiz.question.Question;

@NoArgsConstructor
@Accessors(fluent = true)
@Getter
@Entity
@Table(name = "quizs")
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private UUID quizId;

	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	private String title;

	@OneToMany(mappedBy = "quiz")
	@OrderBy("number ASC")
	private List<Question> questions;

	private boolean open;

	private boolean shuffleQuestions;
	private boolean shuffleAnswers;

	@Builder
	public Quiz(User user, String title) {
		this.user = user;
		this.title = title;
	}

	public void update(
		String title,
		Boolean open,
		Boolean shuffleQuestions,
		Boolean shuffleAnswers
	) {
		if (title != null) this.title = title;
		if (open != null) this.open = open;
		if (shuffleQuestions != null) this.shuffleQuestions = shuffleQuestions;
		if (shuffleAnswers != null) this.shuffleAnswers = shuffleAnswers;
	}

	void setQuizId(UUID quizId) {
		this.quizId = quizId;
	}

}
