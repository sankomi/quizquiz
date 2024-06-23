package sanko.quiz.session;

import java.lang.annotation.*; //ElementType, Retention, RetentionPolicy, Target

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
