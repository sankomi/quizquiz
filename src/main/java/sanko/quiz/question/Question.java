package sanko.quiz.question;

import java.util.List;
import jakarta.persistence.*; //Entity, Table, Id, Column, GeneratedValue, GenerationType, JoinColumn, ManyToOne, OneToMany, OrderBy

import lombok.*; //NoArgsConstructor, Getter, Builder
import lombok.experimental.Accessors;

import sanko.quiz.quiz.Quiz;
import sanko.quiz.answer.Answer;

@NoArgsConstructor
@Accessors(fluent = true)
@Getter
@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "quiz")
	private Quiz quiz;

	private Long number;

	private String text;

	@OneToMany(mappedBy = "question")
	@OrderBy("number ASC")
	private List<Answer> answers;

	@Builder
	public Question(Quiz quiz, Long number, String text) {
		this.quiz = quiz;
		this.number = number;
		this.text = text;
	}

	public void update(String text) {
		this.text = text;
	}

	public void addAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}
