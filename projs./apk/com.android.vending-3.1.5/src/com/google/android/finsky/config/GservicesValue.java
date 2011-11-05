package com.google.android.finsky.config;

import android.content.ContentResolver;
import android.content.Context;
import com.google.android.gsf.GoogleSettingsContract;
import com.google.android.gsf.Gservices;

public abstract class GservicesValue<T extends Object> {

   private static GservicesValue.GservicesReader sGservicesReader = null;
   protected final T mDefaultValue;
   protected final String mKey;
   private T mOverride = null;


   protected GservicesValue(String var1, T var2) {
      this.mKey = var1;
      this.mDefaultValue = var2;
   }

   public static void init(Context var0) {
      ContentResolver var1 = var0.getContentResolver();
      sGservicesReader = new GservicesValue.GservicesReaderImpl(var1);
   }

   public static void initForTests() {
      sGservicesReader = new GservicesValue.GservicesReaderForTests((GservicesValue.1)null);
   }

   public static GservicesValue<String> partnerSetting(String var0, String var1) {
      return new GservicesValue.5(var0, var1);
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
      Object var1;
      if(this.mOverride != null) {
         var1 = this.mOverride;
      } else {
         String var2 = this.mKey;
         var1 = this.retrieve(var2);
      }

      return var1;
   }

   public String getKey() {
      return this.mKey;
   }

   public void override(T var1) {
      this.mOverride = var1;
   }

   protected abstract T retrieve(String var1);

   static class 3 extends GservicesValue<Integer> {

      3(String var1, Integer var2) {
         super(var1, var2);
      }

      protected Integer retrieve(String var1) {
         GservicesValue.GservicesReader var2 = GservicesValue.sGservicesReader;
         String var3 = this.mKey;
         Integer var4 = (Integer)this.mDefaultValue;
         return var2.getInt(var3, var4);
      }
   }

   static class 2 extends GservicesValue<Long> {

      2(String var1, Long var2) {
         super(var1, var2);
      }

      protected Long retrieve(String var1) {
         GservicesValue.GservicesReader var2 = GservicesValue.sGservicesReader;
         String var3 = this.mKey;
         Long var4 = (Long)this.mDefaultValue;
         return var2.getLong(var3, var4);
      }
   }

   static class 1 extends GservicesValue<Boolean> {

      1(String var1, Boolean var2) {
         super(var1, var2);
      }

      protected Boolean retrieve(String var1) {
         GservicesValue.GservicesReader var2 = GservicesValue.sGservicesReader;
         String var3 = this.mKey;
         Boolean var4 = (Boolean)this.mDefaultValue;
         return var2.getBoolean(var3, var4);
      }
   }

   private interface GservicesReader {

      Boolean getBoolean(String var1, Boolean var2);

      Integer getInt(String var1, Integer var2);

      Long getLong(String var1, Long var2);

      String getPartnerString(String var1, String var2);

      String getString(String var1, String var2);
   }

   private static class GservicesReaderForTests implements GservicesValue.GservicesReader {

      private GservicesReaderForTests() {}

      // $FF: synthetic method
      GservicesReaderForTests(GservicesValue.1 var1) {
         this();
      }

      public Boolean getBoolean(String var1, Boolean var2) {
         return var2;
      }

      public Integer getInt(String var1, Integer var2) {
         return var2;
      }

      public Long getLong(String var1, Long var2) {
         return var2;
      }

      public String getPartnerString(String var1, String var2) {
         return var2;
      }

      public String getString(String var1, String var2) {
         return var2;
      }
   }

   static class 5 extends GservicesValue<String> {

      5(String var1, String var2) {
         super(var1, var2);
      }

      protected String retrieve(String var1) {
         GservicesValue.GservicesReader var2 = GservicesValue.sGservicesReader;
         String var3 = this.mKey;
         String var4 = (String)this.mDefaultValue;
         return var2.getPartnerString(var3, var4);
      }
   }

   private static class GservicesReaderImpl implements GservicesValue.GservicesReader {

      private final ContentResolver mContentResolver;


      public GservicesReaderImpl(ContentResolver var1) {
         this.mContentResolver = var1;
      }

      public Boolean getBoolean(String var1, Boolean var2) {
         ContentResolver var3 = this.mContentResolver;
         boolean var4 = var2.booleanValue();
         return Boolean.valueOf(Gservices.getBoolean(var3, var1, var4));
      }

      public Integer getInt(String var1, Integer var2) {
         ContentResolver var3 = this.mContentResolver;
         int var4 = var2.intValue();
         return Integer.valueOf(Gservices.getInt(var3, var1, var4));
      }

      public Long getLong(String var1, Long var2) {
         ContentResolver var3 = this.mContentResolver;
         long var4 = var2.longValue();
         return Long.valueOf(Gservices.getLong(var3, var1, var4));
      }

      public String getPartnerString(String var1, String var2) {
         return GoogleSettingsContract.Partner.getString(this.mContentResolver, var1, var2);
      }

      public String getString(String var1, String var2) {
         return Gservices.getString(this.mContentResolver, var1, var2);
      }
   }

   static class 4 extends GservicesValue<String> {

      4(String var1, String var2) {
         super(var1, var2);
      }

      protected String retrieve(String var1) {
         GservicesValue.GservicesReader var2 = GservicesValue.sGservicesReader;
         String var3 = this.mKey;
         String var4 = (String)this.mDefaultValue;
         return var2.getString(var3, var4);
      }
   }
}
