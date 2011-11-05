package org.codehaus.jackson.annotate;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.codehaus.jackson.annotate.JacksonAnnotation;
import org.codehaus.jackson.annotate.JsonMethod;

@AnnotationDefault(   @JsonAutoDetect({JsonMethod.ALL}))
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@JacksonAnnotation
public @interface JsonAutoDetect {

   JsonMethod[] value();
}
