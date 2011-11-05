package com.facebook.katana.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {

   public ReflectionUtils() {}

   public static <T extends Object> Map<? extends AccessibleObject, T> getComponents(Class<?> var0, ReflectionUtils.Filter<T> var1, EnumSet<ReflectionUtils.IncludeFlags> var2) {
      HashMap var3 = new HashMap();
      getComponents(var0, var1, var2, var3);
      return var3;
   }

   protected static <T extends Object> void getComponents(Class<?> var0, ReflectionUtils.Filter<T> var1, EnumSet<ReflectionUtils.IncludeFlags> var2, Map<AccessibleObject, T> var3) {
      ReflectionUtils.IncludeFlags var4 = ReflectionUtils.IncludeFlags.INCLUDE_SUPERCLASSES;
      if(var2.contains(var4)) {
         Class var5 = var0.getSuperclass();
         if(var5 != null) {
            getComponents(var5, var1, var2, var3);
         }
      }

      ReflectionUtils.IncludeFlags var6 = ReflectionUtils.IncludeFlags.INCLUDE_CONSTRUCTORS;
      int var8;
      if(var2.contains(var6)) {
         Constructor[] var7 = var0.getDeclaredConstructors();
         var8 = 0;

         while(true) {
            int var9 = var7.length;
            if(var8 >= var9) {
               break;
            }

            Constructor var10 = var7[var8];
            Object var11 = var1.mapper(var10);
            if(var11 != null) {
               Constructor var12 = var7[var8];
               var3.put(var12, var11);
            }

            ++var8;
         }
      }

      ReflectionUtils.IncludeFlags var14 = ReflectionUtils.IncludeFlags.INCLUDE_METHODS;
      if(var2.contains(var14)) {
         Method[] var15 = var0.getDeclaredMethods();
         var8 = 0;

         while(true) {
            int var16 = var15.length;
            if(var8 >= var16) {
               break;
            }

            Method var17 = var15[var8];
            Object var18 = var1.mapper(var17);
            if(var18 != null) {
               Method var19 = var15[var8];
               var3.put(var19, var18);
            }

            ++var8;
         }
      }

      ReflectionUtils.IncludeFlags var21 = ReflectionUtils.IncludeFlags.INCLUDE_FIELDS;
      if(var2.contains(var21)) {
         Field[] var22 = var0.getDeclaredFields();
         var8 = 0;

         while(true) {
            int var23 = var22.length;
            if(var8 >= var23) {
               return;
            }

            Field var24 = var22[var8];
            Object var25 = var1.mapper(var24);
            if(var25 != null) {
               Field var26 = var22[var8];
               var3.put(var26, var25);
            }

            ++var8;
         }
      }
   }

   public static enum IncludeFlags {

      // $FF: synthetic field
      private static final ReflectionUtils.IncludeFlags[] $VALUES;
      INCLUDE_CONSTRUCTORS("INCLUDE_CONSTRUCTORS", 1),
      INCLUDE_FIELDS("INCLUDE_FIELDS", 3),
      INCLUDE_METHODS("INCLUDE_METHODS", 2),
      INCLUDE_SUPERCLASSES("INCLUDE_SUPERCLASSES", 0);


      static {
         ReflectionUtils.IncludeFlags[] var0 = new ReflectionUtils.IncludeFlags[4];
         ReflectionUtils.IncludeFlags var1 = INCLUDE_SUPERCLASSES;
         var0[0] = var1;
         ReflectionUtils.IncludeFlags var2 = INCLUDE_CONSTRUCTORS;
         var0[1] = var2;
         ReflectionUtils.IncludeFlags var3 = INCLUDE_METHODS;
         var0[2] = var3;
         ReflectionUtils.IncludeFlags var4 = INCLUDE_FIELDS;
         var0[3] = var4;
         $VALUES = var0;
      }

      private IncludeFlags(String var1, int var2) {}
   }

   public interface Filter<T extends Object> {

      T mapper(AccessibleObject var1);
   }
}
