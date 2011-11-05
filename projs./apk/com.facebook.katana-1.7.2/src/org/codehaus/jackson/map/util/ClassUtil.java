package org.codehaus.jackson.map.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public final class ClassUtil {

   private ClassUtil() {}

   private static void _addSuperTypes(Class<?> var0, Class<?> var1, ArrayList<Class<?>> var2, boolean var3) {
      if(var0 != var1) {
         if(var0 != null) {
            if(var0 != Object.class) {
               if(var3) {
                  if(var2.contains(var0)) {
                     return;
                  }

                  var2.add(var0);
               }

               Class[] var5 = var0.getInterfaces();
               int var6 = var5.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  _addSuperTypes(var5[var7], var1, var2, (boolean)1);
               }

               _addSuperTypes(var0.getSuperclass(), var1, var2, (boolean)1);
            }
         }
      }
   }

   public static String canBeABeanType(Class<?> var0) {
      String var1;
      if(var0.isAnnotation()) {
         var1 = "annotation";
      } else if(var0.isArray()) {
         var1 = "array";
      } else if(var0.isEnum()) {
         var1 = "enum";
      } else if(var0.isPrimitive()) {
         var1 = "primitive";
      } else {
         var1 = null;
      }

      return var1;
   }

   public static void checkAndFixAccess(Member var0) {
      AccessibleObject var1 = (AccessibleObject)var0;

      try {
         var1.setAccessible((boolean)1);
      } catch (SecurityException var9) {
         if(!var1.isAccessible()) {
            Class var3 = var0.getDeclaringClass();
            StringBuilder var4 = (new StringBuilder()).append("Can not access ").append(var0).append(" (from class ");
            String var5 = var3.getName();
            StringBuilder var6 = var4.append(var5).append("; failed to set access: ");
            String var7 = var9.getMessage();
            String var8 = var6.append(var7).toString();
            throw new IllegalArgumentException(var8);
         }
      }
   }

   public static Object createInstance(Class<?> var0, boolean var1) throws IllegalArgumentException {
      Constructor var4;
      label33: {
         Constructor var3;
         try {
            Class[] var2 = new Class[0];
            var3 = var0.getDeclaredConstructor(var2);
         } catch (Exception var26) {
            StringBuilder var9 = (new StringBuilder()).append("Failed to find default constructor of class ");
            String var10 = var0.getName();
            StringBuilder var11 = var9.append(var10).append(", problem: ");
            String var12 = var26.getMessage();
            String var13 = var11.append(var12).toString();
            unwrapAndThrowAsIAE(var26, var13);
            var4 = null;
            break label33;
         }

         var4 = var3;
      }

      if(var4 == null) {
         StringBuilder var5 = (new StringBuilder()).append("Class ");
         String var6 = var0.getName();
         String var7 = var5.append(var6).append(" has no default (no arg) constructor").toString();
         throw new IllegalArgumentException(var7);
      } else {
         if(var1) {
            checkAndFixAccess(var4);
         } else if(!Modifier.isPublic(var4.getModifiers())) {
            StringBuilder var16 = (new StringBuilder()).append("Default constructor for ");
            String var17 = var0.getName();
            String var18 = var16.append(var17).append(" is not accessible (non-public?): not allowed to try modify access via Reflection: can not instantiate type").toString();
            throw new IllegalArgumentException(var18);
         }

         byte var14 = 0;

         Object var27;
         Object var28;
         try {
            Object[] var15 = new Object[var14];
            var27 = var4.newInstance(var15);
         } catch (Exception var25) {
            StringBuilder var20 = (new StringBuilder()).append("Failed to instantiate class ");
            String var21 = var0.getName();
            StringBuilder var22 = var20.append(var21).append(", problem: ");
            String var23 = var25.getMessage();
            String var24 = var22.append(var23).toString();
            unwrapAndThrowAsIAE(var25, var24);
            var28 = null;
            return var28;
         }

         var28 = var27;
         return var28;
      }
   }

   public static List<Class<?>> findSuperTypes(Class<?> var0, Class<?> var1) {
      ArrayList var2 = new ArrayList();
      _addSuperTypes(var0, var1, var2, (boolean)0);
      return var2;
   }

   public static Throwable getRootCause(Throwable var0) {
      Throwable var1;
      for(var1 = var0; var1.getCause() != null; var1 = var1.getCause()) {
         ;
      }

      return var1;
   }

   public static boolean hasGetterSignature(Method var0) {
      boolean var1;
      if(Modifier.isStatic(var0.getModifiers())) {
         var1 = false;
      } else {
         Class[] var2 = var0.getParameterTypes();
         if(var2 != null && var2.length != 0) {
            var1 = false;
         } else {
            Class var3 = Void.TYPE;
            Class var4 = var0.getReturnType();
            if(var3 == var4) {
               var1 = false;
            } else {
               var1 = true;
            }
         }
      }

      return var1;
   }

   public static boolean isConcrete(Class<?> var0) {
      boolean var1;
      if((var0.getModifiers() & 1536) == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static String isLocalType(Class<?> var0) {
      String var1;
      if(var0.getEnclosingMethod() != null) {
         var1 = "local/anonymous";
      } else if(var0.getEnclosingClass() != null && !Modifier.isStatic(var0.getModifiers())) {
         var1 = "non-static member class";
      } else {
         var1 = null;
      }

      return var1;
   }

   public static boolean isProxyType(Class<?> var0) {
      boolean var1;
      if(Proxy.isProxyClass(var0)) {
         var1 = true;
      } else {
         String var2 = var0.getName();
         if(!var2.startsWith("net.sf.cglib.proxy.") && !var2.startsWith("org.hibernate.proxy.")) {
            var1 = false;
         } else {
            var1 = true;
         }
      }

      return var1;
   }

   public static void throwAsIAE(Throwable var0) {
      String var1 = var0.getMessage();
      throwAsIAE(var0, var1);
   }

   public static void throwAsIAE(Throwable var0, String var1) {
      if(var0 instanceof RuntimeException) {
         throw (RuntimeException)var0;
      } else if(var0 instanceof Error) {
         throw (Error)var0;
      } else {
         throw new IllegalArgumentException(var1, var0);
      }
   }

   public static void unwrapAndThrowAsIAE(Throwable var0) {
      throwAsIAE(getRootCause(var0));
   }

   public static void unwrapAndThrowAsIAE(Throwable var0, String var1) {
      throwAsIAE(getRootCause(var0), var1);
   }
}
