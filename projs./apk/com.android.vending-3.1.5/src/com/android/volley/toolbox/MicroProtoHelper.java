package com.android.volley.toolbox;

import com.google.protobuf.micro.MessageMicro;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MicroProtoHelper {

   private static final Map<Class<?>, Method> sGetMethodCache = new HashMap();
   private static final Map<Class<?>, Method> sSetMethodCache = new HashMap();


   public MicroProtoHelper() {}

   public static void clearCaches() {
      sGetMethodCache.clear();
      sSetMethodCache.clear();
   }

   private static Method findGetMethod(Class<?> var0, Class<?> var1) {
      Method var2 = (Method)sGetMethodCache.get(var1);
      if(var2 == null) {
         Method[] var3 = var0.getMethods();
         int var4 = var3.length;
         int var5 = 0;

         while(true) {
            if(var5 >= var4) {
               String var8 = "No getter for " + var1 + " in " + var0;
               throw new IllegalArgumentException(var8);
            }

            Method var6 = var3[var5];
            if(var6.getReturnType().equals(var1) && var6.getName().startsWith("get")) {
               sGetMethodCache.put(var1, var6);
               var2 = var6;
               break;
            }

            ++var5;
         }
      }

      return var2;
   }

   private static Method findSetMethod(Class<?> var0, Class<?> var1) {
      Method var2 = (Method)sSetMethodCache.get(var1);
      if(var2 == null) {
         Method[] var3 = var0.getMethods();
         int var4 = var3.length;
         int var5 = 0;

         while(true) {
            if(var5 >= var4) {
               String var10 = "No setter for " + var1 + " in " + var0;
               throw new IllegalArgumentException(var10);
            }

            Method var6 = var3[var5];
            Class[] var7 = var6.getParameterTypes();
            if(var7.length == 1) {
               Class var8 = var7[0];
               if(var1.equals(var8) && var6.getName().startsWith("set")) {
                  sSetMethodCache.put(var1, var6);
                  var2 = var6;
                  break;
               }
            }

            ++var5;
         }
      }

      return var2;
   }

   public static <X extends MessageMicro, Y extends MessageMicro> Y getParsedResponseFromWrapper(X var0, Class<X> var1, Class<Y> var2) {
      try {
         Method var3 = findGetMethod(var1, var2);
         Object[] var4 = new Object[0];
         MessageMicro var5 = (MessageMicro)var3.invoke(var0, var4);
         return var5;
      } catch (Exception var7) {
         throw new RuntimeException(var7);
      }
   }

   public static <X extends MessageMicro, Y extends MessageMicro> void setRequestInWrapper(X var0, Class<X> var1, Y var2, Class<Y> var3) {
      try {
         Method var4 = findSetMethod(var1, var3);
         Object[] var5 = new Object[]{var2};
         var4.invoke(var0, var5);
      } catch (Exception var8) {
         throw new RuntimeException(var8);
      }
   }
}
