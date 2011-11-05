package com.android.common;

import android.content.SharedPreferences.Editor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SharedPreferencesCompat {

   private static Method sApplyMethod;


   static {
      try {
         Class[] var0 = new Class[0];
         sApplyMethod = Editor.class.getMethod("apply", var0);
      } catch (NoSuchMethodException var2) {
         sApplyMethod = null;
      }
   }

   public SharedPreferencesCompat() {}

   public static void apply(Editor var0) {
      if(sApplyMethod != null) {
         try {
            Method var1 = sApplyMethod;
            Object[] var2 = new Object[0];
            var1.invoke(var0, var2);
            return;
         } catch (InvocationTargetException var7) {
            ;
         } catch (IllegalAccessException var8) {
            ;
         }
      }

      boolean var5 = var0.commit();
   }
}
