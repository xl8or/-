package com.facebook.katana.activity.media;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import com.facebook.katana.activity.media.PhotoUploader;
import com.facebook.katana.activity.media.VideoUploader;
import com.facebook.katana.util.Log;

public class MediaUploader {

   public static final int MEDIA_SOURCE_CHOOSER_DIALOG_ID = 255255255;
   public static final int PHOTO_SOURCE_CHOOSER_DIALOG_ID = 255255256;
   public static final int PICK_EXISTING_PHOTO_REQUEST_CODE = 133702;
   public static final int PICK_EXISTING_VIDEO_REQUEST_CODE = 133704;
   private static final String TAG = "MediaUploader";
   public static final int TAKE_CAMERA_PHOTO_REQUEST_CODE = 133701;
   public static final int TAKE_CAMERA_VIDEO_REQUEST_CODE = 133703;
   public static final int VIDEO_SOURCE_CHOOSER_DIALOG_ID = 255255257;
   private final Activity mActivity;
   private final String mAlbumId;
   private PhotoUploader mPhotoUploader;
   private final long mProfileId;
   private VideoUploader mVideoUploader;


   public MediaUploader(Activity var1, long var2) {
      this.mActivity = var1;
      this.mAlbumId = null;
      this.mProfileId = var2;
      Activity var4 = this.mActivity;
      long var5 = this.mProfileId;
      PhotoUploader var7 = new PhotoUploader(var4, var5, 133701, 133702);
      this.mPhotoUploader = var7;
      Activity var8 = this.mActivity;
      long var9 = this.mProfileId;
      VideoUploader var11 = new VideoUploader(var8, var9, 133703, 133704);
      this.mVideoUploader = var11;
   }

   public MediaUploader(Activity var1, String var2) {
      this.mActivity = var1;
      this.mAlbumId = var2;
      this.mProfileId = 65535L;
      Activity var3 = this.mActivity;
      String var4 = this.mAlbumId;
      PhotoUploader var5 = new PhotoUploader(var3, var4, 133701, 133702);
      this.mPhotoUploader = var5;
      Activity var6 = this.mActivity;
      long var7 = this.mProfileId;
      VideoUploader var9 = new VideoUploader(var6, var7, 133703, 133704);
      this.mVideoUploader = var9;
   }

   public Dialog createDialog() {
      CharSequence[] var1 = new CharSequence[2];
      CharSequence var2 = this.mActivity.getText(2131362321);
      var1[0] = var2;
      CharSequence var3 = this.mActivity.getText(2131362332);
      var1[1] = var3;
      Activity var4 = this.mActivity;
      Builder var5 = new Builder(var4);
      CharSequence var6 = this.mActivity.getText(2131362304);
      var5.setTitle(var6);
      MediaUploader.1 var8 = new MediaUploader.1();
      var5.setItems(var1, var8);
      return var5.create();
   }

   public Dialog createPhotoDialog() {
      return this.mPhotoUploader.createDialog();
   }

   public Dialog createVideoDialog() {
      return this.mVideoUploader.createDialog();
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 != 133701 && var1 != 133702) {
         if(var1 != 133703 && var1 != 133704) {
            Log.e("MediaUploader", "illegal requestcode");
         } else {
            this.mVideoUploader.onActivityResult(var1, var2, var3);
         }
      } else {
         this.mPhotoUploader.onActivityResult(var1, var2, var3);
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         switch(var2) {
         case 0:
            MediaUploader.this.mActivity.showDialog(255255256);
            break;
         case 1:
            MediaUploader.this.mActivity.showDialog(255255257);
         }

         var1.dismiss();
      }
   }
}
