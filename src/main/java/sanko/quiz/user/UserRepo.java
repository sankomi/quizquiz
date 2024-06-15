package sanko.quiz.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

	User findOneByUsername(String username);

}
