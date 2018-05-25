package com.wingjay.lego;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LegoViewHolder
 *
 * @author wingjay
 * @date 2018/05/25
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface LegoViewHolder {
    String id() default "";
    Class bean() default Void.class;
}
