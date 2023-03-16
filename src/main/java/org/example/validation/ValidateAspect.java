package org.example.validation;

import java.lang.reflect.Field;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
class ValidationAspect {

  @Pointcut("execution(* *(..))")
  public void anyMethod() {
  }

  @Before("anyMethod() && @annotation(validation)")
  public void validate(JoinPoint joinPoint, Validate validation) {
    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
      for (Field field : arg.getClass().getDeclaredFields()) {
        if (field.isAnnotationPresent(Validate.class)) {
          Validate validateAnnotation = field.getAnnotation(Validate.class);
          String regex = validateAnnotation.regExp();
          String message = validateAnnotation.message();
          field.setAccessible(true);
          try {
            Object value = field.get(arg);
            if (value == null || !Pattern.matches(regex, value.toString())) {
              throw new IllegalArgumentException(message);
            }
          } catch (IllegalAccessException e) {
            log.error(e.toString());
          }
        }
      }
    }
  }
}
