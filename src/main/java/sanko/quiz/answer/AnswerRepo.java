package sanko.quiz.answer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepo extends JpaRepository<Answer, Long> {

	Answer findOneById(Long id);

}
