package org.codehaus.jackson.map.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.AnnotationMap;
import org.codehaus.jackson.map.util.ClassUtil;

public final class AnnotatedMethod extends Annotated {

   final AnnotationMap _annotations;
   final Method _method;
   public Class<?>[] _paramTypes;


   public AnnotatedMethod(Method var1, AnnotationMap var2) {
      this._method = var1;
      this._annotations = var2;
   }

   public void addIfNotPresent(Annotation var1) {
      this._annotations.addIfNotPresent(var1);
   }

   public void fixAccess() {
      ClassUtil.checkAndFixAccess(this._method);
   }

   public Method getAnnotated() {
      return this._method;
   }

   public <A extends Object & Annotation> A getAnnotation(Class<A> var1) {
      return this._annotations.get(var1);
   }

   public int getAnnotationCount() {
      return this._annotations.size();
   }

   public Class<?> getDeclaringClass() {
      return this._method.getDeclaringClass();
   }

   public String getFullName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.getDeclaringClass().getName();
      StringBuilder var3 = var1.append(var2).append("#");
      String var4 = this.getName();
      StringBuilder var5 = var3.append(var4).append("(");
      int var6 = this.getParameterCount();
      return var5.append(var6).append(" params)").toString();
   }

   public Type[] getGenericParameterTypes() {
      return this._method.getGenericParameterTypes();
   }

   public Type getGenericReturnType() {
      return this._method.getGenericReturnType();
   }

   public int getModifiers() {
      return this._method.getModifiers();
   }

   public String getName() {
      return this._method.getName();
   }

   public int getParameterCount() {
      return this.getParameterTypes().length;
   }

   public Class<?>[] getParameterTypes() {
      if(this._paramTypes == null) {
         Class[] var1 = this._method.getParameterTypes();
         this._paramTypes = var1;
      }

      return this._paramTypes;
   }

   public Class<?> getReturnType() {
      return this._method.getReturnType();
   }

   public Class<?> getType() {
      return this.getReturnType();
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("[method ");
      String var2 = this.getName();
      StringBuilder var3 = var1.append(var2).append(", annotations: ");
      AnnotationMap var4 = this._annotations;
      return var3.append(var4).append("]").toString();
   }
}
