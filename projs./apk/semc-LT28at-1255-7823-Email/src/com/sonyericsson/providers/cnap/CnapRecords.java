package com.sonyericsson.providers.cnap;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class CnapRecords implements BaseColumns {

   public static final Uri CONTENT_URI = Uri.parse("content://com.sonyericsson.providers.cnap/cnaprecords");
   public static final String DATE = "date";
   public static final String DEFAULT_SORT_ORDER = "date DESC";
   public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
   public static final String MIME_ITEM = "vnd.semc.cnaprecords";
   public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";
   public static final String MIME_TYPE_MULTIPLE = "vnd.android.cursor.dir/vnd.semc.cnaprecords";
   public static final String MIME_TYPE_SINGLE = "vnd.android.cursor.item/vnd.semc.cnaprecords";
   public static final String NAME = "name";
   public static final String NUMBER = "number";
   public static final String UPDATABLE = "updatable";


   public CnapRecords() {}

   public static Uri addOrUpdateRecord(Context var0, String var1, String var2, long var3) {
      ContentResolver var5 = var0.getContentResolver();
      ContentValues var6 = new ContentValues();
      String[] var7 = new String[]{var1};
      Uri var8 = CONTENT_URI;
      String[] var9 = new String[]{"_id"};
      Cursor var10 = var5.query(var8, var9, "number=?", var7, (String)null);
      Uri var25;
      if(var10 != null) {
         label84: {
            boolean var23 = false;

            Uri var17;
            try {
               var23 = true;
               if(!var10.moveToFirst()) {
                  var23 = false;
                  break label84;
               }

               var6.put("name", var2);
               Long var11 = Long.valueOf(var3);
               var6.put("date", var11);
               Integer var12 = Integer.valueOf(0);
               var6.put("updatable", var12);
               Uri var13 = CONTENT_URI;
               var5.update(var13, var6, "number=?", var7);
               int var15 = var10.getColumnIndexOrThrow("_id");
               String var16 = var10.getString(var15);
               var17 = Uri.withAppendedPath(CONTENT_URI, var16);
               var23 = false;
            } finally {
               if(var23) {
                  if(var10 != null) {
                     var10.close();
                  }

               }
            }

            if(var10 != null) {
               var10.close();
            }

            var25 = var17;
            return var25;
         }
      }

      if(var10 != null) {
         var10.close();
      }

      var6.put("name", var2);
      var6.put("number", var1);
      Long var18 = Long.valueOf(var3);
      var6.put("date", var18);
      Integer var19 = Integer.valueOf(0);
      var6.put("updatable", var19);
      Uri var20 = CONTENT_URI;
      var25 = var5.insert(var20, var6);
      return var25;
   }

   public static boolean isUpdatable(Context var0, String var1) {
      ContentResolver var2 = var0.getContentResolver();
      Uri var3 = CONTENT_URI;
      String[] var4 = new String[]{"updatable"};
      String[] var5 = new String[]{var1};
      Cursor var6 = var2.query(var3, var4, "number=?", var5, (String)null);
      boolean var9;
      if(var6 != null) {
         label90: {
            boolean var12 = false;

            int var8;
            try {
               var12 = true;
               if(!var6.moveToFirst()) {
                  var12 = false;
                  break label90;
               }

               int var7 = var6.getColumnIndexOrThrow("updatable");
               var8 = var6.getInt(var7);
               var12 = false;
            } finally {
               if(var12) {
                  if(var6 != null) {
                     var6.close();
                  }

               }
            }

            if(var8 != 0) {
               var9 = true;
            } else {
               var9 = false;
            }

            if(var6 != null) {
               var6.close();
            }

            return var9;
         }
      }

      if(var6 != null) {
         var6.close();
      }

      var9 = true;
      return var9;
   }

   public static int makeUpdatable(Context var0, String var1) {
      ContentResolver var2 = var0.getContentResolver();
      ContentValues var3 = new ContentValues();
      Integer var4 = Integer.valueOf(1);
      var3.put("updatable", var4);
      Uri var5 = CONTENT_URI;
      String[] var6 = new String[]{var1};
      return var2.update(var5, var3, "number=?", var6);
   }
}
