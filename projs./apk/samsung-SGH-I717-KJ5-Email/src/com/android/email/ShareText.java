package com.android.email;

import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebTextSelectionControls;
import android.webkit.WebView;
import com.android.email.TextSelector;
import com.android.email.ToolTipItem;
import java.io.File;

class ShareText extends ToolTipItem implements OnClickListener {

   static final String LOGTAG = "ShareText";
   private static final int SHARE_AS_IMAGE = 2;
   private static final int SHARE_AS_TEXT = 1;
   private static final String imgfilepath = "/sdcard/native_browser_share_image.jpg";
   File mFile;
   TextSelector mTextSelector = null;


   ShareText(TextSelector var1, int var2) {
      if(var1 == null) {
         int var3 = Log.e("ShareText", "ShareText : ts is null");
      } else {
         String var4 = var1.getTopWindow().getContext().getString(var2);
         this.mstrText = var4;
         StringBuilder var5 = (new StringBuilder()).append("ShareText : String - ");
         String var6 = this.mstrText;
         String var7 = var5.append(var6).toString();
         int var8 = Log.v("ShareText", var7);
      }

      this.mTextSelector = var1;
   }

   public void onClick(View var1) {
      this.onItemSelected();
   }

   public void onItemSelected() {
      if(this.mTextSelector == null) {
         int var1 = Log.e("ShareText", "mTextSelector is null");
      } else {
         String var2;
         Context var3;
         WebView var4;
         try {
            var2 = this.mTextSelector.getTopWindow().getWebTextSelectionControls().getSelectionText();
            var3 = this.mTextSelector.getTopWindow().getContext();
            var4 = this.mTextSelector.getTopWindow();
         } catch (NullPointerException var12) {
            String var10 = var12.toString();
            int var11 = Log.e("ShareText", var10);
            return;
         }

         Context var5 = var4.getContext();
         Builder var6 = new Builder(var5);
         Builder var7 = var6.setTitle(2131167049);
         ShareText.1 var8 = new ShareText.1(var2, var3);
         var6.setItems(2131296317, var8);
         var6.create().show();
      }
   }

   class 1 implements android.content.DialogInterface.OnClickListener {

      // $FF: synthetic field
      final Context val$mBrowserActivity;
      // $FF: synthetic field
      final String val$mSelectedText;


      1(String var2, Context var3) {
         this.val$mSelectedText = var2;
         this.val$mBrowserActivity = var3;
      }

      public void onClick(DialogInterface var1, int var2) {
         switch(var2 + 1) {
         case 1:
            Intent var3 = new Intent("android.intent.action.SEND");
            Intent var4 = var3.setType("text/plain");
            String var5 = this.val$mSelectedText;
            var3.putExtra("android.intent.extra.TEXT", var5);

            try {
               Context var7 = this.val$mBrowserActivity;
               String var8 = this.val$mBrowserActivity.getString(2131167050);
               Intent var9 = Intent.createChooser(var3, var8);
               var7.startActivity(var9);
            } catch (ActivityNotFoundException var33) {
               ;
            }

            ShareText.this.mTextSelector.clearSelection();
            return;
         case 2:
            WebTextSelectionControls var10 = ShareText.this.mTextSelector.getTopWindow().getWebTextSelectionControls();
            if(var10 == null) {
               int var11 = Log.e("ShareText", "selectionControls is null");
               return;
            } else {
               Rect var12 = null;

               label41: {
                  Rect var14;
                  try {
                     Rect var13 = var10.getSelectionRect();
                     var14 = new Rect(var13);
                  } catch (Exception var34) {
                     var34.printStackTrace();
                     break label41;
                  }

                  var12 = var14;
               }

               ShareText.this.mTextSelector.clearSelection();
               long var15 = 1000L;

               try {
                  Thread.sleep(var15);
               } catch (InterruptedException var32) {
                  int var29 = Log.e("ShareText", "Thread.sleep Interrupted");
               }

               var10.saveImage("/sdcard/native_browser_share_image.jpg", var12);
               ShareText var18 = ShareText.this;
               File var19 = new File("/sdcard/native_browser_share_image.jpg");
               var18.mFile = var19;
               Intent var20 = new Intent("android.intent.action.SEND");
               Intent var21 = var20.setType("image/jpg");
               Uri var22 = Uri.fromFile(ShareText.this.mFile);
               var20.putExtra("android.intent.extra.STREAM", var22);

               try {
                  Context var24 = this.val$mBrowserActivity;
                  String var25 = this.val$mBrowserActivity.getString(2131167050);
                  Intent var26 = Intent.createChooser(var20, var25);
                  var24.startActivity(var26);
                  return;
               } catch (ActivityNotFoundException var31) {
                  return;
               }
            }
         default:
         }
      }
   }
}
