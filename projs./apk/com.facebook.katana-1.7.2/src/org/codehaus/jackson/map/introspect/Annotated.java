package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;

public abstract class Annotated {

   public Annotated() {}

   public abstract AnnotatedElement getAnnotated();

   public abstract <A extends Object & Annotation> A getAnnotation(Class<A> var1);

   protected abstract int getModifiers();

   public abstract String getName();

   public abstract Class<?> getType();

   public final <A extends Object & Annotation> boolean hasAnnotation(Class<A> var1) {
      boolean var2;
      if(this.getAnnotation(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final boolean isPublic() {
      return Modifier.isPublic(this.getModifiers());
   }
}
