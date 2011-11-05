package org.codehaus.jackson.map.introspect;

import java.lang.reflect.Method;

public final class MethodKey {

   static final Class<?>[] NO_CLASSES = new Class[0];
   final Class<?>[] _argTypes;
   final String _name;


   public MethodKey(String var1, Class<?>[] var2) {
      this._name = var1;
      Class[] var3;
      if(var2 == null) {
         var3 = NO_CLASSES;
      } else {
         var3 = var2;
      }

      this._argTypes = var3;
   }

   public MethodKey(Method var1) {
      String var2 = var1.getName();
      Class[] var3 = var1.getParameterTypes();
      this(var2, var3);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 == null) {
         var2 = false;
      } else {
         Class var3 = var1.getClass();
         Class var4 = this.getClass();
         if(var3 != var4) {
            var2 = false;
         } else {
            MethodKey var13 = (MethodKey)var1;
            String var5 = this._name;
            String var6 = var13._name;
            if(!var5.equals(var6)) {
               var2 = false;
            } else {
               Class[] var7 = var13._argTypes;
               int var8 = this._argTypes.length;
               if(var7.length != var8) {
                  var2 = false;
               } else {
                  byte var9 = 0;

                  while(true) {
                     if(var9 >= var8) {
                        var2 = true;
                        break;
                     }

                     Class var10 = var7[var9];
                     Class var11 = this._argTypes[var9];
                     if(var10 != var11 && !var10.isAssignableFrom(var11) && !var11.isAssignableFrom(var10)) {
                        var2 = false;
                        break;
                     }

                     int var12 = var9 + 1;
                  }
               }
            }
         }
      }

      return var2;
   }

   public int hashCode() {
      int var1 = this._name.hashCode();
      int var2 = this._argTypes.length;
      return var1 + var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this._name;
      StringBuilder var3 = var1.append(var2).append("(");
      int var4 = this._argTypes.length;
      return var3.append(var4).append("-args)").toString();
   }
}
