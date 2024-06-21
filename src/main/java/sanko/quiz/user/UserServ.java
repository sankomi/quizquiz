package sanko.quiz.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServ {

	private final UserRepo userRepo;

	private final PasswordServ passwordServ;
	private final QrServ qrServ;

	@Transactional
	public UserCreateRes create(UserCreateReq req) {
		User exist = userRepo.findOneByUsername(req.username());
		if (exist != null) return UserCreateRes.fail("username exists");

		String key = passwordServ.createKey();
		User user = User.builder()
			.username(req.username())
			.key(key)
			.build();
		user = userRepo.save(user);

		String url = "otpauth://totp/sanko:" + req.username() + "?secret=" + key + "&issuer=sanko";
		String image = qrServ.create(url);

		return UserCreateRes.success(key, image);
	}

	@Transactional
	public UserVerifyRes verify(UserVerifyReq req) {
		User user = userRepo.findOneByUsername(req.username());

		if (user == null) return UserVerifyRes.fail("username not found");
		if (!passwordServ.verify(user.key(), req.password())) {
			return UserVerifyRes.fail("password incorrect");
		}

		user.verify();
		userRepo.save(user);

		return UserVerifyRes.success();
	}

	public UserLoginRes login(UserLoginReq req) {
		User user = userRepo.findOneByUsername(req.username());

		if (user == null) return UserLoginRes.fail("username not found");
		if (!user.verified()) return UserLoginRes.fail("user not verified");
		if (!passwordServ.verify(user.key(), req.password())) {
			return UserLoginRes.fail("password incorrect");
		}

		return UserLoginRes.success();
	}

}
