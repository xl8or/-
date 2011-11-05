package com.seven.Z7.shared;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContentProviderOperation.Builder;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import com.seven.Z7.provider.Z7Content;
import com.seven.Z7.shared.Z7DBSharedPreferences;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Z7DBPrefsEditor implements Editor {

   public static final String TAG = "Z7DBPrefsEditor";
   private boolean dbCleared = 0;
   private String mCategory;
   private Uri mContentUri;
   private Context mContext;
   private Z7DBSharedPreferences mParent;
   private Map<String, ContentValues> mValues;
   private ArrayList<ContentProviderOperation> ops;


   public Z7DBPrefsEditor(String var1, Z7DBSharedPreferences var2, Context var3) {
      this.mCategory = var1;
      this.mParent = var2;
      this.mContext = var3;
      Uri var4 = Z7Content.Z7DBPrefs.CONTENT_URI;
      String var5 = this.mCategory;
      Uri var6 = Uri.withAppendedPath(var4, var5);
      this.mContentUri = var6;
      ArrayList var7 = new ArrayList();
      this.ops = var7;
      HashMap var8 = new HashMap();
      this.mValues = var8;
   }

   private void appendContentOperations(Map<String, Object> var1) {
      Iterator var2 = this.mValues.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         Builder var8;
         if(var1.containsKey(var3) && !this.dbCleared) {
            Builder var4 = ContentProviderOperation.newUpdate(this.mContentUri);
            ContentValues var5 = (ContentValues)this.mValues.get(var3);
            Builder var6 = var4.withValues(var5);
            String[] var7 = new String[]{var3};
            var8 = var6.withSelection("key=?", var7);
         } else {
            Builder var12 = ContentProviderOperation.newInsert(this.mContentUri);
            ContentValues var13 = (ContentValues)this.mValues.get(var3);
            var8 = var12.withValues(var13);
         }

         ArrayList var9 = this.ops;
         ContentProviderOperation var10 = var8.build();
         var9.add(var10);
      }

   }

   private Z7DBPrefsEditor put(String var1, int var2, String var3) {
      return this.put(var1, var2, var3, 0);
   }

   private Z7DBPrefsEditor put(String var1, int var2, String var3, int var4) {
      if(this.mValues.containsKey(var1)) {
         this.mValues.remove(var1);
      }

      ContentValues var6 = new ContentValues();
      String var7 = this.mCategory;
      var6.put("category", var7);
      var6.put("key", var1);
      Integer var8 = Integer.valueOf(var2);
      var6.put("type", var8);
      var6.put("value", var3);
      Integer var9 = Integer.valueOf(var4);
      var6.put("flags", var9);
      this.mValues.put(var1, var6);
      return this;
   }

   public void apply() {
      boolean var1 = this.commit();
   }

   public Z7DBPrefsEditor clear() {
      this.mValues.clear();
      this.ops.clear();
      this.dbCleared = (boolean)1;
      Builder var1 = ContentProviderOperation.newDelete(this.mContentUri);
      ArrayList var2 = this.ops;
      ContentProviderOperation var3 = var1.build();
      var2.add(var3);
      return this;
   }

   public boolean commit() {
      // $FF: Couldn't be decompiled
   }

   public Z7DBPrefsEditor putBoolean(String var1, boolean var2) {
      String var3 = String.valueOf(var2);
      return this.put(var1, 4, var3);
   }

   public Z7DBPrefsEditor putFloat(String var1, float var2) {
      String var3 = String.valueOf(var2);
      return this.put(var1, 3, var3);
   }

   public Z7DBPrefsEditor putInt(String var1, int var2) {
      String var3 = String.valueOf(var2);
      return this.put(var1, 1, var3);
   }

   public Z7DBPrefsEditor putLong(String var1, long var2) {
      String var4 = String.valueOf(var2);
      return this.put(var1, 2, var4);
   }

   public Z7DBPrefsEditor putString(String var1, String var2) {
      return this.put(var1, 5, var2);
   }

   public Z7DBPrefsEditor putZ7SettingValue(String var1, int var2, String var3, int var4) {
      return this.put(var1, var2, var3, var4);
   }

   public Z7DBPrefsEditor remove(String var1) {
      if(this.mValues.containsKey(var1)) {
         this.mValues.remove(var1);
      }

      Builder var3 = ContentProviderOperation.newDelete(this.mContentUri);
      String[] var4 = new String[]{var1};
      Builder var5 = var3.withSelection("key=?", var4);
      ArrayList var6 = this.ops;
      ContentProviderOperation var7 = var5.build();
      var6.add(var7);
      return this;
   }
}
