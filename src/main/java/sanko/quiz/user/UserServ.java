package sanko.quiz.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import sanko.quiz.Const;
import sanko.quiz.common.*; //Response, QrServ
import sanko.quiz.session.SessionServ;

@RequiredArgsConstructor
@Service
public class UserServ {

	private final UserRepo userRepo;

	private final PasswordServ passwordServ;
	private final QrServ qrServ;
	private final SessionServ sessionServ;

	@Transactional
	public UserCreateRes create(UserCreateReq req, User currentUser) {
		if (currentUser != null) return Response.fail(UserCreateRes.class, Const.ALREADY_LOGGED_IN);

		User exist = userRepo.findOneByUsername(req.username());
		if (exist != null) return Response.fail(UserCreateRes.class, Const.USERNAME_EXISTS);

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
	public UserVerifyRes verify(UserVerifyReq req, User currentUser) {
		if (currentUser != null) return Response.fail(UserVerifyRes.class, Const.ALREADY_LOGGED_IN);
		User user = userRepo.findOneByUsername(req.username());

		if (user == null) return Response.fail(UserVerifyRes.class, Const.NOT_FOUND);
		if (!passwordServ.verify(user.key(), req.password())) {
			return Response.fail(UserVerifyRes.class, Const.PASSWORD_INCORRECT);
		}

		user.verify();
		user = userRepo.save(user);

		sessionServ.setUser(user);
		return UserVerifyRes.success();
	}

	public UserLoginRes login(UserLoginReq req, User currentUser) {
		if (currentUser != null) return Response.fail(UserLoginRes.class, Const.ALREADY_LOGGED_IN);

		User user = userRepo.findOneByUsername(req.username());

		if (user == null) return Response.fail(UserLoginRes.class, Const.NOT_FOUND);
		if (!user.verified()) return Response.fail(UserLoginRes.class, Const.NOT_VERIFIED);
		if (!passwordServ.verify(user.key(), req.password())) {
			return Response.fail(UserLoginRes.class, Const.PASSWORD_INCORRECT);
		}

		sessionServ.setUser(user);
		return UserLoginRes.success();
	}

	public UserLogoutRes logout(User currentUser) {
		if (currentUser == null) return Response.fail(UserLogoutRes.class, Const.NOT_LOGGED_IN);

		sessionServ.removeUser();

		return UserLogoutRes.success();
	}

}
