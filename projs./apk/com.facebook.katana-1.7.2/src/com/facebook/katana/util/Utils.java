package com.facebook.katana.util;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookSessionInfo;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import org.acra.ErrorReporter;

public class Utils {

   public static final Random RNG = new Random();


   public Utils() {}

   public static <K extends Object, V extends Object> Map<K, V> constantMap(Object ... var0) {
      LinkedHashMap var1 = new LinkedHashMap();
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return Collections.unmodifiableMap(var1);
         }

         Object var4 = var0[var2];
         int var5 = var2 + 1;
         Object var6 = var0[var5];
         var1.put(var4, var6);
         var2 += 2;
      }
   }

   public static String convertMentionTags(Editable var0) {
      SpannableStringBuilder var1 = new SpannableStringBuilder(var0);
      int var2 = var1.length();
      FacebookProfile[] var3 = (FacebookProfile[])var1.getSpans(0, var2, FacebookProfile.class);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         FacebookProfile var6 = var3[var5];
         int var7 = var1.getSpanStart(var6);
         int var8 = var1.getSpanEnd(var6);
         Object[] var9 = new Object[2];
         Long var10 = Long.valueOf(var6.mId);
         var9[0] = var10;
         String var11 = var6.mDisplayName;
         var9[1] = var11;
         String var12 = String.format("@[%d:%s]", var9);
         var1.replace(var7, var8, var12);
      }

      return var1.toString().trim();
   }

   public static String getClassName(Class<?> var0) {
      String var1 = var0.getName();
      int var2 = var1.lastIndexOf(".");
      String var4;
      if(var2 != -1) {
         int var3 = var2 + 1;
         var4 = var1.substring(var3);
      } else {
         var4 = var1;
      }

      return var4;
   }

   public static String getStringValue(Bundle var0, String var1) {
      String var2;
      if(var0.getBoolean(var1, (boolean)1) && !var0.getBoolean(var1, (boolean)0)) {
         if(var0.getLong(var1, 65535L) == 65535L && var0.getLong(var1, 0L) == 0L) {
            if(var0.getInt(var1, -1) == -1 && var0.getInt(var1, 0) == 0) {
               var2 = var0.getString(var1);
            } else {
               var2 = Integer.toString(var0.getInt(var1));
            }
         } else {
            var2 = Long.toString(var0.getLong(var1));
         }
      } else {
         var2 = Boolean.toString(var0.getBoolean(var1));
      }

      return var2;
   }

   public static int hashItems(Object ... var0) {
      long var1 = hashItemsLong(var0);
      long var3 = var1 >>> 32;
      return (int)(var1 ^ var3);
   }

   public static long hashItemsLong(Object ... var0) {
      long var1 = 0L;
      Object[] var3 = var0;
      int var4 = var0.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object var6 = var3[var5];
         long var7 = var1 * 31L;
         if(var6 != null) {
            long var9 = (long)var6.hashCode();
            var1 = var7 + var9;
         } else {
            var1 = var7 + 8795404838907415129L;
         }
      }

      return var1;
   }

   public static Bundle makeBundle(Object ... var0) {
      Bundle var1 = new Bundle();
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return var1;
         }

         String var4 = (String)var0[var2];
         int var5 = var2 + 1;
         Object var6 = var0[var5];
         if(var6 instanceof String) {
            String var7 = (String)var6;
            var1.putString(var4, var7);
         } else if(var6 instanceof Long) {
            long var8 = ((Long)var6).longValue();
            var1.putLong(var4, var8);
         } else if(var6 instanceof Integer) {
            int var10 = ((Integer)var6).intValue();
            var1.putInt(var4, var10);
         } else if(var6 instanceof Boolean) {
            boolean var11 = ((Boolean)var6).booleanValue();
            var1.putBoolean(var4, var11);
         } else if(var6 instanceof Serializable) {
            Serializable var12 = (Serializable)var6;
            var1.putSerializable(var4, var12);
         }

         var2 += 2;
      }
   }

   public static void reportSoftError(String var0, String var1) {
      Exception var2 = new Exception("Soft error");
      Utils.1 var3 = new Utils.1(var0, var1, var2);
      (new Thread(var3)).start();
   }

   public static void updateErrorReportingUserId(Context var0) {
      updateErrorReportingUserId(var0, (boolean)0);
   }

   public static void updateErrorReportingUserId(Context var0, boolean var1) {
      if(!var1) {
         AppSession var2 = AppSession.getActiveSession(var0, (boolean)0);
         if(var2 != null) {
            FacebookSessionInfo var3 = var2.getSessionInfo();
            if(var3 != null && var3.userId != 65535L) {
               ErrorReporter var4 = ErrorReporter.getInstance();
               String var5 = String.valueOf(var3.userId);
               var4.putCustomData("uid", var5);
               return;
            }
         }
      }

      String var7 = ErrorReporter.getInstance().removeCustomData("uid");
   }

   static class 1 implements Runnable {

      // $FF: synthetic field
      final String val$category;
      // $FF: synthetic field
      final Exception val$ex;
      // $FF: synthetic field
      final String val$message;


      1(String var1, String var2, Exception var3) {
         this.val$category = var1;
         this.val$message = var2;
         this.val$ex = var3;
      }

      public void run() {
         ErrorReporter var1 = ErrorReporter.getInstance();
         String var2 = this.val$category.replace("\n", "\\n");
         var1.putCustomData("soft_error_category", var2);
         ErrorReporter var4 = ErrorReporter.getInstance();
         String var5 = this.val$message.replace("\n", "\\n");
         var4.putCustomData("soft_error_message", var5);
         ErrorReporter var7 = ErrorReporter.getInstance();
         Exception var8 = this.val$ex;
         var7.handleException(var8);
         String var10 = ErrorReporter.getInstance().removeCustomData("soft_error_category");
         String var11 = ErrorReporter.getInstance().removeCustomData("soft_error_message");
      }
   }
}
