package javax.annotation;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@AnnotationDefault(   @Nonnegative(
      when = When.ALWAYS
   ))
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TypeQualifier(
   applicableTo = Number.class
)
public @interface Nonnegative {

   When when();

   public static class Checker implements TypeQualifierValidator<Nonnegative> {

      public Checker() {}

      public When forConstantValue(Nonnegative var1, Object var2) {
         boolean var3 = true;
         When var4;
         if(!(var2 instanceof Number)) {
            var4 = When.NEVER;
         } else {
            Number var5 = (Number)var2;
            if(var5 instanceof Long) {
               if(var5.longValue() >= 0L) {
                  var3 = false;
               }
            } else if(var5 instanceof Double) {
               if(var5.doubleValue() >= 0.0D) {
                  var3 = false;
               }
            } else if(var5 instanceof Float) {
               if(var5.floatValue() >= 0.0F) {
                  var3 = false;
               }
            } else if(var5.intValue() >= 0) {
               var3 = false;
            }

            if(var3) {
               var4 = When.NEVER;
            } else {
               var4 = When.ALWAYS;
            }
         }

         return var4;
      }
   }
}
