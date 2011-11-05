package javax.annotation;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.When;

@AnnotationDefault(   @Untainted(
      when = When.ALWAYS
   ))
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TypeQualifier
public @interface Untainted {

   When when();
}
