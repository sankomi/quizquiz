package sanko.quiz.question;

import jakarta.persistence.*; //Entity, Table, Id, Column, GeneratedValue, GenerationType, JoinColumn, ManyToOne

import lombok.*; //NoArgsConstructor, Getter, Builder
import lombok.experimental.Accessors;

import sanko.quiz.quiz.Quiz;

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

	private String text;

	@Builder
	public Question(Quiz quiz, String text) {
		this.quiz = quiz;
		this.text = text;
	}

}
