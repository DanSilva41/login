package br.com.felipe.login.support.annotation;

import br.com.felipe.login.support.enums.Author;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Documentation {

    String doc() default "";

    String date() default "";

    String task() default "";

    Author author();
}
