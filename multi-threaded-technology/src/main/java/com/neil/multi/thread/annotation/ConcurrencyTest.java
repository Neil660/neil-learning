package com.neil.multi.thread.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/12/10 14:10
 * @Version 1.0
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ConcurrencyTest {
    int iterations() default 20000;
    int thinkTime() default 0;
}
