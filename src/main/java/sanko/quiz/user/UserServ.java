package sanko.quiz.user;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServ {

	private final UserRepo userRepo;

	private final PasswordServ passwordServ;

	public UserCreateRes create(UserCreateReq req) {
		User exist = userRepo.findOneByUsername(req.username());
		if (exist != null) return UserCreateRes.fail("username exists");

		User user = User.builder()
			.username(req.username())
			.password(req.password())
			.build();
		user = userRepo.save(user);

		return UserCreateRes.success();
	}

	public UserLoginRes login(UserLoginReq req) {
		User user = userRepo.findOneByUsername(req.username());

		if (user == null) return UserLoginRes.fail("username not found");
		if (!user.password().equals(req.password())) return UserLoginRes.fail("password incorrect");

		return UserLoginRes.success();
	}

}
