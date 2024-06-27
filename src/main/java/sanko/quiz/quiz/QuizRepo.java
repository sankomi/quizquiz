package sanko.quiz.quiz;

import org.springframework.data.jpa.repository.JpaRepository;

import sanko.quiz.user.User;

public interface QuizRepo extends JpaRepository<Quiz, Long> {

	Quiz findOneByTitleAndUser(String title, User user);

}
