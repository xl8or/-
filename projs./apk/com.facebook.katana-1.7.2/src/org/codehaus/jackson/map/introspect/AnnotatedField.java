package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.AnnotationMap;
import org.codehaus.jackson.map.util.ClassUtil;

public final class AnnotatedField extends Annotated {

   final AnnotationMap _annotations;
   final Field _field;


   public AnnotatedField(Field var1, AnnotationMap var2) {
      this._field = var1;
      this._annotations = var2;
   }

   public void fixAccess() {
      ClassUtil.checkAndFixAccess(this._field);
   }

   public Field getAnnotated() {
      return this._field;
   }

   public <A extends Object & Annotation> A getAnnotation(Class<A> var1) {
      return this._annotations.get(var1);
   }

   public int getAnnotationCount() {
      return this._annotations.size();
   }

   public Class<?> getDeclaringClass() {
      return this._field.getDeclaringClass();
   }

   public String getFullName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.getDeclaringClass().getName();
      StringBuilder var3 = var1.append(var2).append("#");
      String var4 = this.getName();
      return var3.append(var4).toString();
   }

   public Type getGenericType() {
      return this._field.getGenericType();
   }

   public int getModifiers() {
      return this._field.getModifiers();
   }

   public String getName() {
      return this._field.getName();
   }

   public Class<?> getType() {
      return this._field.getType();
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("[field ");
      String var2 = this.getName();
      StringBuilder var3 = var1.append(var2).append(", annotations: ");
      AnnotationMap var4 = this._annotations;
      return var3.append(var4).append("]").toString();
   }
}
