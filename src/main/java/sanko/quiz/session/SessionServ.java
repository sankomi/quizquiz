package sanko.quiz.session;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.*; //User, UserRepo

@RequiredArgsConstructor
@Service
public class SessionServ {

	private final UserRepo userRepo;

	private final HttpSession httpSession;

	public User getUser() {
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
		if (sessionUser == null) return null;

		Long id = sessionUser.id();
		User user = userRepo.findOneById(id);
		if (user == null) return null;

		return user;
	}

	public void setUser(User user) {
		SessionUser sessionUser = SessionUser.builder()
			.user(user)
			.build();
		httpSession.setAttribute("user", sessionUser);
	}

	public void removeUser() {
		httpSession.removeAttribute("user");
	}

}
