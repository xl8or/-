package com.facebook.katana.activity.media;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.facebook.katana.AlertDialogs;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.util.EclairKeyHandler;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.Toaster;

public class CreateEditAlbumActivity extends BaseFacebookActivity implements OnClickListener {

   private static final int PROGRESS_CREATE_DIALOG_ID = 1;
   private static final int PROGRESS_EDIT_DIALOG_ID = 2;
   private static final int QUIT_DIALOG_ID = 3;
   private FacebookAlbum mAlbum;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private String mCreateReqId;
   private String mEditReqId;


   public CreateEditAlbumActivity() {}

   private boolean checkChanges() {
      boolean var1;
      if(this.getIntent().getAction().equals("android.intent.action.EDIT")) {
         var1 = this.checkEditChanges();
      } else {
         var1 = this.checkCreateChanges();
      }

      return var1;
   }

   private boolean checkCreateChanges() {
      boolean var1;
      if(((TextView)this.findViewById(2131623941)).getText().length() > 0) {
         var1 = true;
      } else if(((TextView)this.findViewById(2131623945)).getText().length() > 0) {
         var1 = true;
      } else if(((TextView)this.findViewById(2131623947)).getText().length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean checkEditChanges() {
      String var1 = ((TextView)this.findViewById(2131623941)).getText().toString();
      String var2;
      if(this.mAlbum.getName() != null) {
         var2 = this.mAlbum.getName();
      } else {
         var2 = "";
      }

      boolean var3;
      if(!var1.equals(var2)) {
         var3 = true;
      } else {
         String var4 = ((TextView)this.findViewById(2131623947)).getText().toString();
         String var5;
         if(this.mAlbum.getLocation() != null) {
            var5 = this.mAlbum.getLocation();
         } else {
            var5 = "";
         }

         if(!var4.equals(var5)) {
            var3 = true;
         } else {
            String var6 = ((TextView)this.findViewById(2131623945)).getText().toString();
            String var7;
            if(this.mAlbum.getDescription() != null) {
               var7 = this.mAlbum.getDescription();
            } else {
               var7 = "";
            }

            if(!var6.equals(var7)) {
               var3 = true;
            } else {
               String var8 = visibilityFromSpinnerPosition(((Spinner)this.findViewById(2131624019)).getSelectedItemPosition());
               String var9 = this.mAlbum.getVisibility();
               if(!var8.equals(var9)) {
                  var3 = true;
               } else {
                  var3 = false;
               }
            }
         }
      }

      return var3;
   }

   private void createAlbum() {
      String var1 = ((TextView)this.findViewById(2131623941)).getText().toString().trim();
      if(var1.length() == 0) {
         Toaster.toast(this, 2131361861);
      } else {
         String var2 = ((TextView)this.findViewById(2131623947)).getText().toString().trim();
         if(var2.length() == 0) {
            var2 = null;
         }

         String var3 = ((TextView)this.findViewById(2131623945)).getText().toString().trim();
         if(var3.length() == 0) {
            var3 = null;
         }

         String var4 = visibilityFromSpinnerPosition(((Spinner)this.findViewById(2131624019)).getSelectedItemPosition());
         AppSession var5 = this.mAppSession;
         String var7 = var5.photoCreateAlbum(this, var1, var2, var3, var4);
         this.mCreateReqId = var7;
         this.showDialog(1);
      }
   }

   private boolean onBackKeyPressed() {
      boolean var1;
      if(this.checkChanges()) {
         this.showDialog(3);
         var1 = true;
      } else {
         this.finish();
         var1 = true;
      }

      return var1;
   }

   private static int spinnerPositionFromString(String var0) {
      byte var1;
      if(var0.equals("everyone")) {
         var1 = 0;
      } else if(var0.equals("networks")) {
         var1 = 1;
      } else if(var0.equals("friends-of-friends")) {
         var1 = 2;
      } else if(var0.equals("friends")) {
         var1 = 3;
      } else {
         if(!var0.equals("custom")) {
            String var2 = "Invalid visibility: " + var0;
            throw new IllegalArgumentException(var2);
         }

         var1 = 4;
      }

      return var1;
   }

   private void updateAlbum() {
      if(!this.checkEditChanges()) {
         Toaster.toast(this, 2131361863);
      } else {
         String var1 = ((TextView)this.findViewById(2131623941)).getText().toString().trim();
         if(var1.length() == 0) {
            Toaster.toast(this, 2131361861);
         } else {
            String var2 = ((TextView)this.findViewById(2131623947)).getText().toString().trim();
            String var3 = ((TextView)this.findViewById(2131623945)).getText().toString().trim();
            if(var2.length() == 0) {
               var2 = " ";
            }

            if(var3.length() == 0) {
               var3 = " ";
            }

            String var4 = visibilityFromSpinnerPosition(((Spinner)this.findViewById(2131624019)).getSelectedItemPosition());
            AppSession var5 = this.mAppSession;
            String var6 = this.mAlbum.getAlbumId();
            String var8 = var5.photoEditAlbum(this, var6, var1, var2, var3, var4);
            this.mEditReqId = var8;
            this.showDialog(2);
         }
      }
   }

   private static String visibilityFromSpinnerPosition(int var0) {
      String var2;
      switch(var0) {
      case 0:
         var2 = "everyone";
         break;
      case 1:
         var2 = "networks";
         break;
      case 2:
         var2 = "friends-of-friends";
         break;
      case 3:
         var2 = "friends";
         break;
      case 4:
         var2 = "custom";
         break;
      default:
         String var1 = "Invalid position: " + var0;
         throw new IllegalArgumentException(var1);
      }

      return var2;
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131624020:
         if(this.getIntent().getAction().equals("android.intent.action.EDIT")) {
            this.updateAlbum();
            return;
         }

         this.createAlbum();
         return;
      case 2131624021:
         if(this.checkChanges()) {
            this.showDialog(3);
            return;
         }

         this.finish();
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903060);
      ArrayAdapter var2 = new ArrayAdapter(this, 17367048);
      var2.setDropDownViewResource(17367049);
      String var3 = this.getString(2131362108);
      var2.add(var3);
      String var4 = this.getString(2131362110);
      var2.add(var4);
      String var5 = this.getString(2131362109);
      var2.add(var5);
      String var6 = this.getString(2131362111);
      var2.add(var6);
      this.findViewById(2131624020).setOnClickListener(this);
      this.findViewById(2131624021).setOnClickListener(this);
      boolean var7 = false;
      if(this.getIntent().getAction().equals("android.intent.action.EDIT")) {
         ((TextView)this.findViewById(2131624049)).setText(2131361858);
         ((TextView)this.findViewById(2131624050)).setText(2131361856);
         ((Button)this.findViewById(2131624020)).setText(2131361854);
         String var8 = (String)this.getIntent().getData().getPathSegments().get(2);
         FacebookAlbum var9 = FacebookAlbum.readFromContentProvider(this, var8);
         this.mAlbum = var9;
         TextView var10 = (TextView)this.findViewById(2131623941);
         String var11 = this.mAlbum.getName();
         var10.setText(var11);
         TextView var12 = (TextView)this.findViewById(2131623947);
         String var13 = this.mAlbum.getLocation();
         var12.setText(var13);
         TextView var14 = (TextView)this.findViewById(2131623945);
         String var15 = this.mAlbum.getDescription();
         var14.setText(var15);
         if(this.mAlbum.getVisibility().equals("custom")) {
            String var16 = this.getString(2131362113);
            var2.add(var16);
         }

         var7 = true;
      } else {
         ((TextView)this.findViewById(2131624049)).setText(2131361851);
         ((TextView)this.findViewById(2131624050)).setText(2131361849);
         ((Button)this.findViewById(2131624020)).setText(2131361847);
      }

      Spinner var17 = (Spinner)this.findViewById(2131624019);
      var17.setAdapter(var2);
      String var18 = this.getString(2131361864);
      var17.setPrompt(var18);
      if(var7) {
         int var19 = spinnerPositionFromString(this.mAlbum.getVisibility());
         var17.setSelection(var19);
      }

      CreateEditAlbumActivity.CreateEditAlbumAppSessionListener var20 = new CreateEditAlbumActivity.CreateEditAlbumAppSessionListener((CreateEditAlbumActivity.1)null);
      this.mAppSessionListener = var20;
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 1:
         ProgressDialog var3 = new ProgressDialog(this);
         var3.setProgressStyle(0);
         CharSequence var4 = this.getText(2131361852);
         var3.setMessage(var4);
         var3.setIndeterminate((boolean)1);
         var3.setCancelable((boolean)0);
         var2 = var3;
         break;
      case 2:
         ProgressDialog var5 = new ProgressDialog(this);
         var5.setProgressStyle(0);
         CharSequence var6 = this.getText(2131361859);
         var5.setMessage(var6);
         var5.setIndeterminate((boolean)1);
         var5.setCancelable((boolean)0);
         var2 = var5;
         break;
      case 3:
         CreateEditAlbumActivity.1 var7 = new CreateEditAlbumActivity.1();
         int var8;
         if(this.getIntent().getAction().equals("android.intent.action.EDIT")) {
            var8 = 2131361807;
         } else {
            var8 = 2131361801;
         }

         String var9 = this.getString(var8);
         String var10 = this.getString(2131361865);
         String var11 = this.getString(2131362355);
         String var12 = this.getString(2131362004);
         Object var14 = null;
         var2 = AlertDialogs.createAlert(this, var9, 17301543, var10, var11, var7, var12, (android.content.DialogInterface.OnClickListener)null, (OnCancelListener)var14, (boolean)1);
         break;
      default:
         var2 = null;
      }

      return (Dialog)var2;
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      byte var3;
      if(var1 == 4) {
         if(PlatformUtils.isEclairOrLater()) {
            if(EclairKeyHandler.onKeyDown(var2)) {
               var3 = 1;
               return (boolean)var3;
            }
         } else if(this.onBackKeyPressed()) {
            var3 = 1;
            return (boolean)var3;
         }
      }

      var3 = super.onKeyDown(var1, var2);
      return (boolean)var3;
   }

   public boolean onKeyUp(int var1, KeyEvent var2) {
      byte var3;
      if(var1 == 4 && PlatformUtils.isEclairOrLater() && EclairKeyHandler.onKeyUp(var2) && this.onBackKeyPressed()) {
         var3 = 1;
      } else {
         var3 = super.onKeyUp(var1, var2);
      }

      return (boolean)var3;
   }

   protected void onPause() {
      super.onPause();
      if(this.mAppSession != null) {
         AppSession var1 = this.mAppSession;
         AppSessionListener var2 = this.mAppSessionListener;
         var1.removeListener(var2);
      }
   }

   protected void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         if(this.mCreateReqId != null) {
            AppSession var2 = this.mAppSession;
            String var3 = this.mCreateReqId;
            if(!var2.isRequestPending(var3)) {
               this.removeDialog(1);
               this.mCreateReqId = null;
            }
         }

         if(this.mEditReqId != null) {
            AppSession var4 = this.mAppSession;
            String var5 = this.mEditReqId;
            if(!var4.isRequestPending(var5)) {
               this.removeDialog(2);
               this.mEditReqId = null;
            }
         }

         AppSession var6 = this.mAppSession;
         AppSessionListener var7 = this.mAppSessionListener;
         var6.addListener(var7);
      }
   }

   private class CreateEditAlbumAppSessionListener extends AppSessionListener {

      private CreateEditAlbumAppSessionListener() {}

      // $FF: synthetic method
      CreateEditAlbumAppSessionListener(CreateEditAlbumActivity.1 var2) {
         this();
      }

      public void onPhotoCreateAlbumComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookAlbum var6) {
         CreateEditAlbumActivity.this.removeDialog(1);
         String var7 = CreateEditAlbumActivity.this.mCreateReqId = null;
         if(var3 == 200) {
            Toaster.toast(CreateEditAlbumActivity.this, 2131361850);
            CreateEditAlbumActivity.this.setResult(-1);
            CreateEditAlbumActivity.this.finish();
         } else {
            Toaster.toast(CreateEditAlbumActivity.this, 2131361848);
         }
      }

      public void onPhotoEditAlbumComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6) {
         CreateEditAlbumActivity.this.removeDialog(2);
         String var7 = CreateEditAlbumActivity.this.mEditReqId = null;
         if(var3 == 200) {
            Toaster.toast(CreateEditAlbumActivity.this, 2131361857);
            CreateEditAlbumActivity.this.setResult(-1);
            CreateEditAlbumActivity.this.finish();
         } else {
            Toaster.toast(CreateEditAlbumActivity.this, 2131361855);
         }
      }
   }

   class 1 implements android.content.DialogInterface.OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         CreateEditAlbumActivity.this.finish();
      }
   }
}
