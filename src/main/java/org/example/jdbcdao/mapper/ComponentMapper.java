package org.example.jdbcdao.mapper;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ComponentMapper {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
