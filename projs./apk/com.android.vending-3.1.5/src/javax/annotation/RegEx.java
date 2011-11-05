package javax.annotation;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.annotation.Syntax;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@AnnotationDefault(   @RegEx(
      when = When.ALWAYS
   ))
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Syntax("RegEx")
@TypeQualifierNickname
public @interface RegEx {

   When when();

   public static class Checker implements TypeQualifierValidator<RegEx> {

      public Checker() {}

      public When forConstantValue(RegEx var1, Object var2) {
         When var3;
         if(!(var2 instanceof String)) {
            var3 = When.NEVER;
         } else {
            try {
               Pattern var4 = Pattern.compile((String)var2);
            } catch (PatternSyntaxException var6) {
               var3 = When.NEVER;
               return var3;
            }

            var3 = When.ALWAYS;
         }

         return var3;
      }
   }
}
