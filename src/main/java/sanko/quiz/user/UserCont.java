package sanko.quiz.user;

import org.springframework.web.bind.annotation.*; //RestController, RequestMapping, PostMapping, RequestBody
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserCont {

	private final UserServ userServ;

	@PostMapping("/login")
	public UserLoginRes login(@RequestBody UserLoginReq req) {
		return userServ.login(req);
	}

}
