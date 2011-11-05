package org.codehaus.jackson.type;

import java.lang.reflect.Modifier;

public abstract class JavaType {

   protected final Class<?> _class;
   protected int _hashCode;


   protected JavaType(Class<?> var1) {
      this._class = var1;
      int var2 = var1.getName().hashCode();
      this._hashCode = var2;
   }

   protected void _assertSubclass(Class<?> var1, Class<?> var2) {
      if(!this._class.isAssignableFrom(var1)) {
         StringBuilder var3 = (new StringBuilder()).append("Class ");
         String var4 = var1.getName();
         StringBuilder var5 = var3.append(var4).append(" is not assignable to ");
         String var6 = this._class.getName();
         String var7 = var5.append(var6).toString();
         throw new IllegalArgumentException(var7);
      }
   }

   protected abstract JavaType _narrow(Class<?> var1);

   protected JavaType _widen(Class<?> var1) {
      return this._narrow(var1);
   }

   public abstract boolean equals(Object var1);

   public JavaType findVariableType(String var1) {
      return null;
   }

   public final Class<?> getRawClass() {
      return this._class;
   }

   public final boolean hasRawClass(Class<?> var1) {
      boolean var2;
      if(this._class == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final int hashCode() {
      return this._hashCode;
   }

   public final boolean isAbstract() {
      return Modifier.isAbstract(this._class.getModifiers());
   }

   public final boolean isArrayType() {
      return this._class.isArray();
   }

   public abstract boolean isContainerType();

   public final boolean isEnumType() {
      return this._class.isEnum();
   }

   public abstract boolean isFullyTyped();

   public final boolean isInterface() {
      return this._class.isInterface();
   }

   public final boolean isPrimitive() {
      return this._class.isPrimitive();
   }

   public final JavaType narrowBy(Class<?> var1) {
      Class var2 = this._class;
      JavaType var3;
      if(var1 == var2) {
         var3 = this;
      } else {
         Class var4 = this._class;
         this._assertSubclass(var1, var4);
         var3 = this._narrow(var1);
      }

      return var3;
   }

   public abstract JavaType narrowContentsBy(Class<?> var1);

   public abstract String toString();

   public final JavaType widenBy(Class<?> var1) {
      Class var2 = this._class;
      JavaType var3;
      if(var1 == var2) {
         var3 = this;
      } else {
         Class var4 = this._class;
         this._assertSubclass(var4, var1);
         var3 = this._widen(var1);
      }

      return var3;
   }
}
