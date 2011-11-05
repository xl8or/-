package javax.annotation;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@AnnotationDefault(   @Nonnull(
      when = When.ALWAYS
   ))
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TypeQualifier
public @interface Nonnull {

   When when();

   public static class Checker implements TypeQualifierValidator<Nonnull> {

      public Checker() {}

      public When forConstantValue(Nonnull var1, Object var2) {
         When var3;
         if(var2 == null) {
            var3 = When.NEVER;
         } else {
            var3 = When.ALWAYS;
         }

         return var3;
      }
   }
}
