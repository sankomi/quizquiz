package sanko.quiz;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*; //WebMvcConfigurer, PathMatchConfigurer
import lombok.RequiredArgsConstructor;

import sanko.quiz.session.CurrentUserArgumentResolver;

@RequiredArgsConstructor
@Configuration
public class WebConf implements WebMvcConfigurer {

	private final CurrentUserArgumentResolver currentUserArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserArgumentResolver);
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseTrailingSlashMatch(true);
	}

}
