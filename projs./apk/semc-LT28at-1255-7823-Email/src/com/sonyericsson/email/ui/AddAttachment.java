package com.sonyericsson.email.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.widget.SimpleAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddAttachment extends Activity implements OnClickListener, OnCancelListener {

   private static final int ADD_ATTACHMENT_DIALOG = 0;
   private static final int ID_ADD_MUSIC = 4;
   private static final int ID_ADD_PICTURE = 0;
   private static final int ID_ADD_VIDEO = 2;
   private static final int ID_RECORD_VIDEO = 3;
   private static final int ID_TAKE_PHOTO = 1;
   private static final String MAP_ICON = "icon";
   private static final String MAP_ID = "_id";
   private static final String MAP_TEXT = "text";
   private static final Uri TEMP_LOCATION_URI;
   List<HashMap<String, ?>> mItems;


   static {
      File var0 = Environment.getExternalStorageDirectory();
      TEMP_LOCATION_URI = Uri.fromFile(new File(var0, "temporary.jpg"));
   }

   public AddAttachment() {
      ArrayList var1 = new ArrayList();
      this.mItems = var1;
   }

   private void addItem(int var1, int var2, int var3) {
      HashMap var4 = new HashMap();
      Integer var5 = Integer.valueOf(var1);
      var4.put("_id", var5);
      String var7 = this.getString(var2);
      var4.put("text", var7);
      Integer var9 = Integer.valueOf(var3);
      var4.put("icon", var9);
      this.mItems.add(var4);
   }

   private Dialog createAddAttachmentDialog() {
      Builder var1 = new Builder(this);
      Builder var2 = var1.setIcon(2130837582);
      Builder var3 = var1.setTitle(2131165244);
      this.populateItems();
      String[] var4 = new String[]{"text", "icon"};
      int[] var5 = new int[]{2131558454, 2131558453};
      List var6 = this.mItems;
      SimpleAdapter var8 = new SimpleAdapter(this, var6, 2130903052, var4, var5);
      var1.setAdapter(var8, this);
      var1.setOnCancelListener(this);
      return var1.create();
   }

   private void onAddMusic() {
      Intent var1 = new Intent("android.intent.action.GET_CONTENT");
      Intent var2 = var1.addCategory("android.intent.category.OPENABLE");
      Intent var3 = var1.setType("audio/*");
      String var4 = this.getString(2131165246);
      Intent var5 = Intent.createChooser(var1, var4);
      this.startActivityForResult(var5, 4);
   }

   private void onAddPicture() {
      Intent var1 = new Intent("android.intent.action.GET_CONTENT");
      Intent var2 = var1.addCategory("android.intent.category.OPENABLE");
      Intent var3 = var1.setType("image/*");
      String var4 = this.getString(2131165246);
      Intent var5 = Intent.createChooser(var1, var4);
      this.startActivityForResult(var5, 0);
   }

   private void onAddVideo() {
      Intent var1 = new Intent("android.intent.action.GET_CONTENT");
      Intent var2 = var1.addCategory("android.intent.category.OPENABLE");
      Intent var3 = var1.setType("video/*");
      String var4 = this.getString(2131165246);
      Intent var5 = Intent.createChooser(var1, var4);
      this.startActivityForResult(var5, 2);
   }

   private void onRecordVideo() {
      Intent var1 = new Intent("android.media.action.VIDEO_CAPTURE");
      Intent var2 = var1.putExtra("android.intent.extra.videoQuality", 0);
      Intent var3 = var1.putExtra("android.intent.extra.sizeLimit", 5242880);
      String var4 = this.getString(2131165246);
      Intent var5 = Intent.createChooser(var1, var4);
      this.startActivityForResult(var5, 3);
   }

   private void onTakePhoto() {
      Intent var1 = new Intent("android.media.action.IMAGE_CAPTURE");
      Uri var2 = TEMP_LOCATION_URI;
      var1.putExtra("output", var2);
      Intent var4 = var1.putExtra("addToMediaStore", (boolean)1);
      String var5 = this.getString(2131165246);
      Intent var6 = Intent.createChooser(var1, var5);
      this.startActivityForResult(var6, 1);
   }

   private void populateItems() {
      this.mItems.clear();
      this.addItem(0, 2131165190, 2130837545);
      this.addItem(1, 2131165191, 2130837563);
      this.addItem(2, 2131165192, 2130837546);
      this.addItem(3, 2131165193, 2130837562);
      this.addItem(4, 2131165194, 2130837544);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      this.setResult(0);
      switch(var1) {
      case 0:
      case 2:
      case 3:
      case 4:
         if(var2 == -1 && var3 != null && var3.getData() != null) {
            this.setResultFromUri(var3);
         }
         break;
      case 1:
         if(var2 == -1) {
            if(var3 != null && var3.getData() != null) {
               this.setResultFromUri(var3);
            } else {
               this.setResultFromTmpFile();
            }
         }
      }

      this.finish();
   }

   public void onCancel(DialogInterface var1) {
      this.finish();
   }

   public void onClick(DialogInterface var1, int var2) {
      switch(((Integer)((HashMap)this.mItems.get(var2)).get("_id")).intValue()) {
      case 0:
         this.onAddPicture();
         return;
      case 1:
         this.onTakePhoto();
         return;
      case 2:
         this.onAddVideo();
         return;
      case 3:
         this.onRecordVideo();
         return;
      case 4:
         this.onAddMusic();
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(var1 == null) {
         this.showDialog(0);
      }
   }

   protected Dialog onCreateDialog(int var1, Bundle var2) {
      Dialog var3;
      switch(var1) {
      case 0:
         var3 = this.createAddAttachmentDialog();
         break;
      default:
         var3 = super.onCreateDialog(var1, var2);
      }

      return var3;
   }

   protected void setResultFromTmpFile() {
      String var1 = TEMP_LOCATION_URI.getPath();

      try {
         Uri var2 = Uri.parse(Media.insertImage(this.getContentResolver(), var1, (String)null, (String)null));
         Intent var3 = new Intent((String)null, var2);
         this.setResult(-1, var3);
      } catch (FileNotFoundException var8) {
         int var5 = Log.e("Email", "tmp picture file missing.");
      } catch (OutOfMemoryError var9) {
         int var7 = Log.e("Email", "OutOfMemoryError, cound not insert image to the media store.");
      }

      if(!(new File(var1)).delete()) {
         ;
      }
   }

   protected void setResultFromUri(Intent var1) {
      Uri var2 = var1.getData();
      Intent var3 = new Intent((String)null, var2);
      this.setResult(-1, var3);
   }
}
