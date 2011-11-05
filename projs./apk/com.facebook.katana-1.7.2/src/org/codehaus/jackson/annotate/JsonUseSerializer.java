package org.codehaus.jackson.annotate;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.codehaus.jackson.annotate.JacksonAnnotation;
import org.codehaus.jackson.annotate.NoClass;

@AnnotationDefault(   @JsonUseSerializer(NoClass.class))
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@JacksonAnnotation
public @interface JsonUseSerializer {

   Class<?> value();
}
