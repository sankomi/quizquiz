package sanko.quiz.quiz;

import org.springframework.data.jpa.repository.JpaRepository;

import sanko.quiz.user.User;

public interface QuizRepo extends JpaRepository<Quiz, Long> {

	Quiz findOneById(Long id);
	Quiz findOneByIdAndUser(Long id, User user);

}
