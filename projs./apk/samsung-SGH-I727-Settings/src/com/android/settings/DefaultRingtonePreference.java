package com.android.settings;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.RingtonePreference;
import android.provider.MediaStore.Audio.Media;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.util.Log;

public class DefaultRingtonePreference extends RingtonePreference {

   public static final int ID_COLUMN_INDEX = 0;
   private static final String TAG = "DefaultRingtonePreference";
   public static final int URI_COLUMN_INDEX = 2;
   private Cursor cursor;
   private Context mContext;
   private int mRingtoneType;


   public DefaultRingtonePreference(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
   }

   public int getRingtonePosition(Uri var1) {
      int var2;
      if(var1 == null) {
         var2 = -1;
      } else {
         int var3 = this.cursor.getCount();
         if(!this.cursor.moveToFirst()) {
            var2 = -1;
         } else {
            StringBuilder var4 = (new StringBuilder()).append("ringtoneUri : ");
            String var5 = var1.toString();
            String var6 = var4.append(var5).toString();
            int var7 = Log.e("DefaultRingtonePreference", var6);
            Uri var8 = null;
            String var9 = null;
            int var10 = 0;

            while(true) {
               if(var10 >= var3) {
                  var2 = -1;
                  break;
               }

               String var11 = this.cursor.getString(2);
               String var12 = "uriString : " + var11;
               int var13 = Log.e("DefaultRingtonePreference", var12);
               if(var8 == null || !var11.equals(var9)) {
                  var8 = Uri.parse(var11);
               }

               StringBuilder var14 = (new StringBuilder()).append("currentUri : ");
               long var15 = this.cursor.getLong(0);
               String var17 = ContentUris.withAppendedId(var8, var15).toString();
               String var18 = var14.append(var17).toString();
               int var19 = Log.e("DefaultRingtonePreference", var18);
               long var20 = this.cursor.getLong(0);
               Uri var22 = ContentUris.withAppendedId(var8, var20);
               if(var1.equals(var22)) {
                  var2 = var10;
                  break;
               }

               boolean var23 = this.cursor.move(1);
               var9 = var11;
               ++var10;
            }
         }
      }

      return var2;
   }

   protected void onPrepareRingtonePickerIntent(Intent var1) {
      super.onPrepareRingtonePickerIntent(var1);
      Intent var2 = var1.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", (boolean)0);
      int var3 = super.getRingtoneType();
      this.mRingtoneType = var3;
      if(this.mRingtoneType == 1) {
         String var4 = this.mContext.getString(2131231241);
         var1.putExtra("android.intent.extra.ringtone.TITLE", var4);
      } else {
         String var20 = this.mContext.getString(2131231251);
         var1.putExtra("android.intent.extra.ringtone.TITLE", var20);
      }

      Context var6 = this.mContext;
      int var7 = this.mRingtoneType;
      Uri var8 = RingtoneManager.getActualDefaultRingtoneUri(var6, var7);
      ContentResolver var9 = this.mContext.getContentResolver();
      String[] var10 = new String[]{"_id", "title", null, null};
      StringBuilder var11 = (new StringBuilder()).append("\"");
      Uri var12 = Media.EXTERNAL_CONTENT_URI;
      String var13 = var11.append(var12).append("\"").toString();
      var10[2] = var13;
      var10[3] = "title_key";
      Uri var14 = Media.EXTERNAL_CONTENT_URI;
      Object var15 = null;
      Cursor var16 = var9.query(var14, var10, (String)null, (String[])var15, "title_key");
      this.cursor = var16;
      if(this.cursor == null) {
         int var27 = Log.e("DefaultRingtonePreference", "cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MEDIA_COLUMNS, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER) : cursor is null");
      } else {
         try {
            if(this.getRingtonePosition(var8) != -1) {
               ContentValues var17 = new ContentValues();
               if(this.mRingtoneType == 1) {
                  var17.put("is_ringtone", "1");
                  int var18 = Log.e("DefaultRingtonePreference", "IS_RINGTONE set to 1");
               } else {
                  var17.put("is_notification", "1");
                  int var22 = Log.e("DefaultRingtonePreference", "IS_NOTIFICATION set to 1");
               }

               var9.update(var8, var17, (String)null, (String[])null);
            }

            return;
         } catch (Exception var30) {
            String var24 = "Exception occurred : " + var30;
            int var25 = Log.e("DefaultRingtonePreference", var24);
            var30.printStackTrace();
         } finally {
            this.cursor.close();
         }

      }
   }

   protected Uri onRestoreRingtone() {
      Context var1 = this.getContext();
      int var2 = this.getRingtoneType();
      return RingtoneManager.getActualDefaultRingtoneUri(var1, var2);
   }

   protected void onSaveRingtone(Uri var1) {
      Context var2 = this.getContext();
      int var3 = this.getRingtoneType();
      RingtoneManager.setActualDefaultRingtoneUri(var2, var3, var1);
      if(var1 != null) {
         ContentResolver var4 = this.mContext.getContentResolver();
         StringBuilder var5 = (new StringBuilder()).append("DefaultRingtonePreference : ");
         String var6 = var1.toString();
         String var7 = var5.append(var6).toString();
         System.putString(var4, "DEBUG_RINGTONE", var7);
      }
   }
}
