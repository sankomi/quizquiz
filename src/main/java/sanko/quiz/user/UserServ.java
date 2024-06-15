package sanko.quiz.user;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServ {

	private final UserRepo userRepo;

	public UserLoginRes login(UserLoginReq req) {
		User user = userRepo.findOneByUsername(req.username());

		if (user == null) return UserLoginRes.fail("username not found");
		if (!user.password().equals(req.password())) return UserLoginRes.fail("password incorrect");

		return UserLoginRes.success();
	}

}
