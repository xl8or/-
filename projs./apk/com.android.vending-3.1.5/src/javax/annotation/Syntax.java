package javax.annotation;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.When;

@AnnotationDefault(   @Syntax(
      when = When.ALWAYS
   ))
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TypeQualifier(
   applicableTo = String.class
)
public @interface Syntax {

   String value();

   When when();
}
