package org.acra;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionCollector {

   public ReflectionCollector() {}

   public static String collectConstants(Class<? extends Object> var0) {
      StringBuilder var1 = new StringBuilder();
      Field[] var2 = var0.getFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field var5 = var2[var4];
         String var6 = var5.getName();
         StringBuilder var7 = var1.append(var6).append("=");
         Object var8 = null;

         try {
            String var9 = var5.get(var8).toString();
            var1.append(var9);
         } catch (IllegalArgumentException var16) {
            StringBuilder var13 = var1.append("N/A");
         } catch (IllegalAccessException var17) {
            StringBuilder var15 = var1.append("N/A");
         }

         StringBuilder var11 = var1.append("\n");
      }

      return var1.toString();
   }

   public static String collectStaticGettersResults(Class<? extends Object> var0) {
      StringBuilder var1 = new StringBuilder();
      Method[] var2 = var0.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method var5 = var2[var4];
         if(var5.getParameterTypes().length == 0 && (var5.getName().startsWith("get") || var5.getName().startsWith("is")) && !var5.getName().equals("getClass")) {
            try {
               String var6 = var5.getName();
               StringBuilder var7 = var1.append(var6).append('=');
               Object[] var8 = (Object[])false;
               Object var9 = var5.invoke((Object)null, var8);
               StringBuilder var10 = var7.append(var9).append("\n");
            } catch (IllegalArgumentException var14) {
               ;
            } catch (IllegalAccessException var15) {
               ;
            } catch (InvocationTargetException var16) {
               ;
            }
         }
      }

      return var1.toString();
   }
}
