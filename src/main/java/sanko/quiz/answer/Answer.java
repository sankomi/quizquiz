package sanko.quiz.answer;

import jakarta.persistence.*; //Entity, Table, Id, Column, GeneratedValue, GenerationType, JoinColumn, ManyToOne

import lombok.*; //NoArgsConstructor, Getter, Builder
import lombok.experimental.Accessors;

import sanko.quiz.question.Question;

@NoArgsConstructor
@Accessors(fluent = true)
@Getter
@Entity
@Table(name = "answers")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;

	private Long number;

	private String text;

	private Boolean correct;

	@Builder
	public Answer(Question question, Long number, String text, Boolean correct) {
		this.question = question;
		this.number = number;
		this.text = text;
		this.correct = correct;
	}

	public void update(String text, Boolean correct) {
		this.text = text;
		this.correct = correct;
	}

}
