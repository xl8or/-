package com.facebook.katana.activity.profilelist;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.activity.ProfileFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.ui.SectionedListAdapter;
import com.facebook.katana.util.ApplicationUtils;
import java.util.Iterator;
import java.util.List;

public abstract class ProfileListActivity extends ProfileFacebookListActivity {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   protected ProfileListActivity.ProfileListAdapter mAdapter;
   protected AppSession mAppSession;
   protected ProfileListActivity.ProfileListListener mAppSessionListener;


   static {
      byte var0;
      if(!ProfileListActivity.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public ProfileListActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
   }

   public void onListItemClick(ListView var1, View var2, int var3, long var4) {
      FacebookProfile var6 = (FacebookProfile)this.mAdapter.getItem(var3);
      long var7 = var6.mId;
      ApplicationUtils.OpenUserProfile(this, var7, var6);
   }

   protected void onPause() {
      super.onPause();
      if(this.mAppSession != null) {
         if(!$assertionsDisabled && this.mAppSessionListener == null) {
            throw new AssertionError();
         } else {
            AppSession var1 = this.mAppSession;
            ProfileListActivity.ProfileListListener var2 = this.mAppSessionListener;
            var1.removeListener(var2);
         }
      }
   }

   public void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else if(!$assertionsDisabled && this.mAppSessionListener == null) {
         throw new AssertionError();
      } else {
         AppSession var2 = this.mAppSession;
         ProfileListActivity.ProfileListListener var3 = this.mAppSessionListener;
         var2.addListener(var3);
      }
   }

   public abstract static class ProfileListAdapter extends SectionedListAdapter {

      protected List<ViewHolder<Long>> mViewHolders;


      public ProfileListAdapter() {}

      public void updateUserImage(ProfileImage var1) {
         Iterator var2 = this.mViewHolders.iterator();

         while(var2.hasNext()) {
            ViewHolder var3 = (ViewHolder)var2.next();
            Long var4 = (Long)var3.getItemId();
            if(var4 != null) {
               Long var5 = Long.valueOf(var1.id);
               if(var4.equals(var5)) {
                  ImageView var6 = var3.mImageView;
                  Bitmap var7 = var1.getBitmap();
                  var6.setImageBitmap(var7);
               }
            }
         }

      }
   }

   public class ProfileListListener extends AppSessionListener {

      public ProfileListListener() {}

      public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {
         if(var3 == 200) {
            ProfileListActivity.this.mAdapter.updateUserImage(var6);
         }
      }

      public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {
         ProfileListActivity.this.mAdapter.updateUserImage(var2);
      }
   }
}
