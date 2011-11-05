package com.google.android.gsf;

import android.content.ContentResolver;
import android.content.Context;
import com.google.android.gsf.Gservices;

public abstract class GservicesValue<T extends Object> {

   private static ContentResolver sContentResolver = null;
   protected final T mDefaultValue;
   protected final String mKey;


   protected GservicesValue(String var1, T var2) {
      this.mKey = var1;
      this.mDefaultValue = var2;
   }

   public static void init(Context var0) {
      sContentResolver = var0.getContentResolver();
   }

   public static GservicesValue<Integer> value(String var0, Integer var1) {
      return new GservicesValue.3(var0, var1);
   }

   public static GservicesValue<Long> value(String var0, Long var1) {
      return new GservicesValue.2(var0, var1);
   }

   public static GservicesValue<String> value(String var0, String var1) {
      return new GservicesValue.4(var0, var1);
   }

   public static GservicesValue<Boolean> value(String var0, boolean var1) {
      Boolean var2 = Boolean.valueOf(var1);
      return new GservicesValue.1(var0, var2);
   }

   public final T get() {
      String var1 = this.mKey;
      return this.retrieve(var1);
   }

   protected abstract T retrieve(String var1);

   static class 2 extends GservicesValue<Long> {

      2(String var1, Long var2) {
         super(var1, var2);
      }

      protected Long retrieve(String var1) {
         ContentResolver var2 = GservicesValue.sContentResolver;
         String var3 = this.mKey;
         long var4 = ((Long)this.mDefaultValue).longValue();
         return Long.valueOf(Gservices.getLong(var2, var3, var4));
      }
   }

   static class 1 extends GservicesValue<Boolean> {

      1(String var1, Boolean var2) {
         super(var1, var2);
      }

      protected Boolean retrieve(String var1) {
         ContentResolver var2 = GservicesValue.sContentResolver;
         String var3 = this.mKey;
         boolean var4 = ((Boolean)this.mDefaultValue).booleanValue();
         return Boolean.valueOf(Gservices.getBoolean(var2, var3, var4));
      }
   }

   static class 4 extends GservicesValue<String> {

      4(String var1, String var2) {
         super(var1, var2);
      }

      protected String retrieve(String var1) {
         ContentResolver var2 = GservicesValue.sContentResolver;
         String var3 = this.mKey;
         String var4 = (String)this.mDefaultValue;
         return Gservices.getString(var2, var3, var4);
      }
   }

   static class 3 extends GservicesValue<Integer> {

      3(String var1, Integer var2) {
         super(var1, var2);
      }

      protected Integer retrieve(String var1) {
         ContentResolver var2 = GservicesValue.sContentResolver;
         String var3 = this.mKey;
         int var4 = ((Integer)this.mDefaultValue).intValue();
         return Integer.valueOf(Gservices.getInt(var2, var3, var4));
      }
   }
}
