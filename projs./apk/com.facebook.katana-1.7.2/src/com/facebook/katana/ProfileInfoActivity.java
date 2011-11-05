package com.facebook.katana;

import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ProfileInfoAdapter;
import com.facebook.katana.TabProgressListener;
import com.facebook.katana.TabProgressSource;
import com.facebook.katana.activity.ProfileFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;

public class ProfileInfoActivity extends ProfileFacebookListActivity implements TabProgressSource {

   protected static final int MESSAGE_DIALOG = 1;
   protected static final int PROGRESS_DIALOG = 2;
   protected static final int VIEW_PROFILE = 2;
   protected ProfileInfoAdapter mAdapter;
   protected TabProgressListener mProgressListener;
   protected String mReqId;
   protected boolean mShowingProgress;


   public ProfileInfoActivity() {}

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      MenuItem var3 = var1.add(0, 2, 0, 2131362167).setIcon(2130837697);
      return true;
   }

   protected void onPause() {
      super.onPause();
      if(this.mAppSession != null) {
         AppSession var1 = this.mAppSession;
         AppSessionListener var2 = this.mAppSessionListener;
         var1.removeListener(var2);
      }
   }

   public void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         AppSession var2 = this.mAppSession;
         AppSessionListener var3 = this.mAppSessionListener;
         var2.addListener(var3);
         if(this.mReqId != null) {
            AppSession var4 = this.mAppSession;
            String var5 = this.mReqId;
            if(!var4.isRequestPending(var5)) {
               this.removeDialog(2);
               this.mReqId = null;
            }
         }
      }
   }

   public void setProgressListener(TabProgressListener var1) {
      this.mProgressListener = var1;
      if(this.mProgressListener != null) {
         TabProgressListener var2 = this.mProgressListener;
         boolean var3 = this.mShowingProgress;
         var2.onShowProgress(var3);
      }
   }

   protected void showProgress(boolean var1) {
      if(this.mProgressListener != null) {
         this.mProgressListener.onShowProgress(var1);
      }

      View var2 = this.findViewById(2131624177);
      this.mShowingProgress = var1;
      if(var1) {
         if(var2 != null) {
            var2.setVisibility(0);
         }

         this.findViewById(2131624022).setVisibility(8);
         this.findViewById(2131624023).setVisibility(0);
      } else {
         if(var2 != null) {
            var2.setVisibility(8);
         }

         this.findViewById(2131624022).setVisibility(0);
         this.findViewById(2131624023).setVisibility(8);
      }
   }

   protected class InfoAppSessionListener extends ProfileFacebookListActivity.FBListActivityAppSessionListener {

      protected InfoAppSessionListener() {
         super();
      }

      public void onPhotoDecodeComplete(AppSession var1, Bitmap var2, String var3) {
         ProfileInfoActivity.this.mAdapter.updatePhoto();
         ProfileInfoActivity.this.updateFatTitleHeader();
      }
   }
}
