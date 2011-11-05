package org.codehaus.jackson.map.annotate;

import dalvik.annotation.AnnotationDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.codehaus.jackson.annotate.JacksonAnnotation;
import org.codehaus.jackson.annotate.NoClass;
import org.codehaus.jackson.map.JsonSerializer;

@AnnotationDefault(   @JsonSerialize(
      as = NoClass.class,
      include = JsonSerialize.Inclusion.ALWAYS,
      using = JsonSerializer.None.class
   ))
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@JacksonAnnotation
public @interface JsonSerialize {

   Class<?> as();

   JsonSerialize.Inclusion include();

   Class<? extends JsonSerializer<?>> using();

   public static enum Inclusion {

      // $FF: synthetic field
      private static final JsonSerialize.Inclusion[] $VALUES;
      ALWAYS("ALWAYS", 0),
      NON_DEFAULT("NON_DEFAULT", 2),
      NON_NULL("NON_NULL", 1);


      static {
         JsonSerialize.Inclusion[] var0 = new JsonSerialize.Inclusion[3];
         JsonSerialize.Inclusion var1 = ALWAYS;
         var0[0] = var1;
         JsonSerialize.Inclusion var2 = NON_NULL;
         var0[1] = var2;
         JsonSerialize.Inclusion var3 = NON_DEFAULT;
         var0[2] = var3;
         $VALUES = var0;
      }

      private Inclusion(String var1, int var2) {}
   }
}
