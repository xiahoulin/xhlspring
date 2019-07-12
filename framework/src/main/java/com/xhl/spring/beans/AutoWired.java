package com.xhl.spring.beans;

import java.lang.annotation.*;

/**
 * 创建人：xiahoulin
 * 创建时间：2019-07-12  18:55
 * 描述：
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoWired {
}
