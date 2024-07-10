package sanko.quiz.quiz;

import java.util.*; //UUID, List

import org.springframework.data.jpa.repository.JpaRepository;

import sanko.quiz.user.User;

public interface QuizRepo extends JpaRepository<Quiz, Long> {

	Quiz findOneByQuizId(UUID id);
	Quiz findOneByIdAndUser(Long id, User user);
	List<Quiz> findByUser(User user);

}
