package javax.annotation;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;
import javax.annotation.RegEx;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@AnnotationDefault(   @MatchesPattern(
      flags = 0
   ))
@Documented
@Retention(RetentionPolicy.RUNTIME)
@TypeQualifier(
   applicableTo = String.class
)
public @interface MatchesPattern {

   int flags();

   @RegEx
   String value();

   public static class Checker implements TypeQualifierValidator<MatchesPattern> {

      public Checker() {}

      public When forConstantValue(MatchesPattern var1, Object var2) {
         String var3 = var1.value();
         int var4 = var1.flags();
         Pattern var5 = Pattern.compile(var3, var4);
         String var6 = (String)var2;
         When var7;
         if(var5.matcher(var6).matches()) {
            var7 = When.ALWAYS;
         } else {
            var7 = When.NEVER;
         }

         return var7;
      }
   }
}
