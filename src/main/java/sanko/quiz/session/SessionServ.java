package sanko.quiz.session;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import sanko.quiz.user.User;

@RequiredArgsConstructor
@Service
public class SessionServ {

	private final HttpSession httpSession;

	public SessionUser getUser() {
		return (SessionUser) httpSession.getAttribute("user");
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
