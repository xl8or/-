package com.facebook.katana.activity.media;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.service.method.VideoUpload;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Toaster;
import java.io.File;

public class UploadVideoActivity extends BaseFacebookActivity implements OnClickListener {

   private AppSession mAppSession;
   private boolean mDeleteFileOnDestroy = 0;
   private String mFilename;
   private AppSessionListener mListener;
   private FacebookProfile mProfile;
   private long mProfileId;


   public UploadVideoActivity() {}

   private void runUI() {
      String var1 = this.getIntent().getAction();
      Uri var2 = (Uri)this.getIntent().getParcelableExtra("android.intent.extra.STREAM");
      if(var1.startsWith("com.facebook.katana.upload.notification.error")) {
         AppSession var3 = this.mAppSession;
         StringBuilder var4 = (new StringBuilder()).append("");
         int var5 = this.getIntent().getExtras().getInt("android.intent.extra.SUBJECT");
         String var6 = var4.append(var5).toString();
         var3.cancelUploadNotification(this, var6);
      } else {
         if(var1.startsWith("com.facebook.katana.upload.notification.ok")) {
            AppSession var10 = this.mAppSession;
            StringBuilder var11 = (new StringBuilder()).append("");
            int var12 = this.getIntent().getExtras().getInt("android.intent.extra.SUBJECT");
            String var13 = var11.append(var12).toString();
            var10.cancelUploadNotification(this, var13);

            try {
               String var14 = var2.getPath();
               boolean var15 = (new File(var14)).delete();
            } catch (Exception var17) {
               Log.e(this.getTag(), "Cannot delete local video file", var17);
            }

            this.finish();
            return;
         }

         if(var1.startsWith("com.facebook.katana.upload.notification.pending")) {
            this.finish();
            return;
         }
      }

      String var7 = var2.toString();
      this.mFilename = var7;
      this.setContentView(2130903178);
      ((Button)this.findViewById(2131624271)).setOnClickListener(this);
      ((Button)this.findViewById(2131624243)).setOnClickListener(this);
      this.setupFatTitleBar();
      String var8 = this.getIntent().getStringExtra("android.intent.extra.TITLE");
      if(var8 != null) {
         ((TextView)this.findViewById(2131624272)).setText(var8);
      }

      this.findViewById(2131624177).setVisibility(8);
      this.hideSearchButton();
      UploadVideoActivity.1 var9 = new UploadVideoActivity.1();
      this.mListener = var9;
   }

   private void setupFatTitleBar() {
      if(this.mProfileId == 65535L) {
         ImageButton var1 = (ImageButton)this.findViewById(2131624051);
         var1.setVisibility(0);
         var1.setImageResource(2130837710);
         var1.setOnClickListener(this);
      }
   }

   private void updateFatTitleBar() {
      ((TextView)this.findViewById(2131624049)).setText(2131362333);
      String var4;
      if(this.mProfile == null) {
         long var1 = this.mProfileId;
         FqlGetProfile.RequestSingleProfile(this, var1);
         var4 = this.getString(2131362325);
      } else {
         Object[] var5 = new Object[1];
         String var6 = this.mProfile.mDisplayName;
         var5[0] = var6;
         var4 = this.getString(2131362326, var5);
      }

      ((TextView)this.findViewById(2131624050)).setText(var4);
   }

   private void upload() {
      String var1 = ((EditText)this.findViewById(2131624272)).getText().toString().trim();
      if(var1.length() == 0) {
         var1 = null;
      }

      String var2 = ((EditText)this.findViewById(2131624273)).getText().toString().trim();
      if(var2.length() == 0) {
         var2 = null;
      }

      String var3 = this.mFilename;
      long var4 = this.mProfileId;
      VideoUpload.RequestVideoUpload(this, var1, var2, var3, var4);
      this.mDeleteFileOnDestroy = (boolean)0;
      Toaster.toast(this, 2131362329);
      this.finish();
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if(var2 != 0) {
         switch(var1) {
         case 2210:
            this.runUI();
            return;
         default:
         }
      }
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131624243:
         this.finish();
         return;
      case 2131624271:
         TextView var2 = (TextView)this.findViewById(2131624272);
         InputMethodManager var3 = (InputMethodManager)this.getSystemService("input_method");
         IBinder var4 = var2.getWindowToken();
         var3.hideSoftInputFromWindow(var4, 0);
         this.upload();
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      long var2 = this.getIntent().getLongExtra("extra_profile_id", 65535L);
      this.mProfileId = var2;
      AppSession var4 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var4;
      if(this.mAppSession == null) {
         LoginActivity.redirectThroughLogin(this);
      } else {
         this.runUI();
      }
   }

   protected void onDestroy() {
      super.onDestroy();
      if(this.mFilename != null) {
         if(this.mDeleteFileOnDestroy) {
            String var1 = this.mFilename;
            boolean var2 = (new File(var1)).delete();
         }
      }
   }

   protected void onPause() {
      if(this.mAppSession != null && this.mListener != null) {
         AppSession var1 = this.mAppSession;
         AppSessionListener var2 = this.mListener;
         var1.removeListener(var2);
      }

      super.onPause();
   }

   protected void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         Intent var2 = this.getIntent();
         LoginActivity.toLogin(this, var2);
      } else {
         this.updateFatTitleBar();
         if(this.mFilename == null && (Uri)this.getIntent().getExtras().getParcelable("android.intent.extra.STREAM") == null) {
            this.finish();
         } else {
            AppSession var3 = this.mAppSession;
            AppSessionListener var4 = this.mListener;
            var3.addListener(var4);
         }
      }
   }

   class 1 extends AppSessionListener {

      1() {}

      public void onGetProfileComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookProfile var6) {
         if(var3 == 200) {
            if(var6 != null) {
               long var7 = var6.mId;
               long var9 = UploadVideoActivity.this.mProfileId;
               if(var7 == var9) {
                  UploadVideoActivity.this.mProfile = var6;
                  UploadVideoActivity.this.updateFatTitleBar();
               }
            }
         }
      }
   }

   public static final class Extras {

      public static final String EXTRA_PROFILE_ID = "extra_profile_id";


      public Extras() {}
   }
}
