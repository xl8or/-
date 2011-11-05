package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.AnnotationMap;
import org.codehaus.jackson.map.util.ClassUtil;

public final class AnnotatedConstructor extends Annotated {

   final AnnotationMap _annotations;
   final Constructor<?> _constructor;


   public AnnotatedConstructor(Constructor<?> var1, AnnotationMap var2) {
      this._constructor = var1;
      this._annotations = var2;
   }

   public void fixAccess() {
      ClassUtil.checkAndFixAccess(this._constructor);
   }

   public Constructor<?> getAnnotated() {
      return this._constructor;
   }

   public <A extends Object & Annotation> A getAnnotation(Class<A> var1) {
      return this._annotations.get(var1);
   }

   public int getModifiers() {
      return this._constructor.getModifiers();
   }

   public String getName() {
      return this._constructor.getName();
   }

   public Class<?>[] getParameterTypes() {
      return this._constructor.getParameterTypes();
   }

   public Class<?> getType() {
      return this._constructor.getDeclaringClass();
   }
}
