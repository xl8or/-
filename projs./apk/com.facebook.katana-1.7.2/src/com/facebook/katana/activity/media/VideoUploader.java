package com.facebook.katana.activity.media;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import com.facebook.katana.activity.media.UploadVideoActivity;
import com.facebook.katana.util.Log;

public class VideoUploader {

   private static final String TAG = "VideoUploader";
   private final Activity mActivity;
   private final int mPickExistingVideoRequestCode;
   private final long mProfileId;
   private final int mTakeCameraVideoRequestCode;


   public VideoUploader(Activity var1, long var2, int var4, int var5) {
      this.mActivity = var1;
      this.mProfileId = var2;
      this.mTakeCameraVideoRequestCode = var4;
      this.mPickExistingVideoRequestCode = var5;
   }

   public Dialog createDialog() {
      CharSequence[] var1 = new CharSequence[2];
      CharSequence var2 = this.mActivity.getText(2131362331);
      var1[0] = var2;
      CharSequence var3 = this.mActivity.getText(2131362330);
      var1[1] = var3;
      Activity var4 = this.mActivity;
      Builder var5 = new Builder(var4);
      String var6 = this.mActivity.getString(2131362333);
      var5.setTitle(var6);
      VideoUploader.1 var8 = new VideoUploader.1();
      var5.setItems(var1, var8);
      return var5.create();
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      int var4 = this.mTakeCameraVideoRequestCode;
      if(var1 != var4) {
         int var5 = this.mPickExistingVideoRequestCode;
         if(var1 != var5) {
            Log.e("VideoUploader", "illegal requestcode");
            return;
         }
      }

      Activity var6 = this.mActivity;
      Intent var7 = new Intent(var6, UploadVideoActivity.class);
      Intent var8 = var7.setAction("com.facebook.katana.upload.uri");
      long var9 = this.mProfileId;
      var7.putExtra("extra_profile_id", var9);
      Uri var12 = var3.getData();
      var7.putExtra("android.intent.extra.STREAM", var12);
      this.mActivity.startActivity(var7);
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         switch(var2) {
         case 0:
            Intent var3 = new Intent("android.intent.action.GET_CONTENT");
            Intent var4 = var3.setType("video/*");
            Activity var5 = VideoUploader.this.mActivity;
            int var6 = VideoUploader.this.mPickExistingVideoRequestCode;
            var5.startActivityForResult(var3, var6);
            break;
         case 1:
            Intent var7 = new Intent("android.media.action.VIDEO_CAPTURE");
            Activity var8 = VideoUploader.this.mActivity;
            int var9 = VideoUploader.this.mTakeCameraVideoRequestCode;
            var8.startActivityForResult(var7, var9);
         }

         var1.dismiss();
      }
   }
}
