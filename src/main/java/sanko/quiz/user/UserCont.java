package sanko.quiz.user;

import org.springframework.web.bind.annotation.*; //RestController, RequestMapping, PostMapping, RequestBody
import lombok.RequiredArgsConstructor;

import sanko.quiz.session.CurrentUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserCont {

	private final UserServ userServ;

	@PostMapping("/create")
	public UserCreateRes create(
		@RequestBody UserCreateReq req,
		@CurrentUser User currentUser
	) {
		return userServ.create(req, currentUser);
	}

	@PutMapping("/verify")
	public UserVerifyRes verify(
		@RequestBody UserVerifyReq req,
		@CurrentUser User currentUser
	) {
		return userServ.verify(req, currentUser);
	}

	@PostMapping("/login")
	public UserLoginRes login(
		@RequestBody UserLoginReq req,
		@CurrentUser User currentUser
	) {
		return userServ.login(req, currentUser);
	}

	@DeleteMapping("/login")
	public UserLogoutRes logout(@CurrentUser User currentUser) {
		return userServ.logout(currentUser);
	}

}
