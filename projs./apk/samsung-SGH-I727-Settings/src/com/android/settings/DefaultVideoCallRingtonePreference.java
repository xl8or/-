package com.android.settings;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.RingtonePreference;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import com.android.internal.R.styleable;

public class DefaultVideoCallRingtonePreference extends RingtonePreference {

   private static final String TAG = "DefaultVideoCallRingtonePreference";
   private static final String VIDEO_CALL_RINGTONE = "video_call_ringtone";
   private Context mContext;
   private int mRingtoneType;
   private boolean mShowDefault;
   private boolean mShowSilent;


   public DefaultVideoCallRingtonePreference(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      int[] var3 = styleable.RingtonePreference;
      boolean var4 = var1.obtainStyledAttributes(var2, var3, 0, 0).getBoolean(2, (boolean)1);
      this.mShowSilent = var4;
   }

   protected void onPrepareRingtonePickerIntent(Intent var1) {
      Uri var2 = null;
      String var3 = System.getString(this.mContext.getContentResolver(), "video_call_ringtone");
      if(TextUtils.isEmpty(var3)) {
         String var4 = "Video call ringtone is empty [" + var3 + "]";
         int var5 = Log.e("DefaultVideoCallRingtonePreference", var4);
      } else {
         String var16 = "Video call ringtone [" + var3 + "]";
         int var17 = Log.i("DefaultVideoCallRingtonePreference", var16);
         var2 = Uri.parse(System.getString(this.mContext.getContentResolver(), "video_call_ringtone"));
      }

      var1.putExtra("android.intent.extra.ringtone.EXISTING_URI", var2);
      boolean var7 = this.mShowDefault;
      var1.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", var7);
      if(this.mShowDefault) {
         Uri var9 = RingtoneManager.getDefaultUri(this.getRingtoneType());
         var1.putExtra("android.intent.extra.ringtone.DEFAULT_URI", var9);
      }

      boolean var11 = this.mShowSilent;
      var1.putExtra("android.intent.extra.ringtone.SHOW_SILENT", var11);
      Intent var13 = var1.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", (boolean)0);
      String var14 = this.mContext.getString(2131231242);
      var1.putExtra("android.intent.extra.ringtone.TITLE", var14);
   }

   protected Uri onRestoreRingtone() {
      Context var1 = this.getContext();
      int var2 = this.getRingtoneType();
      return RingtoneManager.getActualDefaultRingtoneUri(var1, var2);
   }

   protected void onSaveRingtone(Uri var1) {
      RingtoneManager.setActualDefaultVideoCallRingtoneUri(this.getContext(), var1);
   }
}
