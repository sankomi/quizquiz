package sanko.quiz.session;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.*; //HandlerMethodArgumentResolver, ModelAndViewContainer
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final SessionServ sessionServ;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean checkAnnotation = parameter.getParameterAnnotation(CurrentUser.class) != null;
		boolean checkClass = SessionUser.class.equals(parameter.getParameterType());

		return checkAnnotation && checkClass;
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) throws Exception {
		return sessionServ.getUser();
	}

}
