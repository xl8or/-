package com.facebook.katana.activity.places;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.TabProgressListener;
import com.facebook.katana.TabProgressSource;
import com.facebook.katana.activity.ProfileFacebookListActivity;
import com.facebook.katana.activity.places.PlacesInfoAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookPlace;

public class PlacesInfoActivity extends ProfileFacebookListActivity implements TabProgressSource {

   private PlacesInfoAdapter mAdapter;
   private AppSession mAppSession;
   private FacebookPlace mPlaceInfo;
   private TabProgressListener mProgressListener;
   private boolean mShowingProgress;


   public PlacesInfoActivity() {}

   private void handleInfo(FacebookPlace var1) {
      this.mAdapter.setPlaceInfo(var1);
   }

   private void setupEmptyView() {
      ((TextView)this.findViewById(2131624022)).setText(2131362138);
      ((TextView)this.findViewById(2131624024)).setText(2131362135);
   }

   public void onCreate(Bundle var1) {
      this.mHasFatTitleHeader = (boolean)1;
      FacebookPlace var2 = (FacebookPlace)this.getIntent().getParcelableExtra("extra_place");
      this.mPlaceInfo = var2;
      long var3 = this.mPlaceInfo.mPageId;
      this.mProfileId = var3;
      super.onCreate(var1);
      this.setContentView(2130903138);
      AppSession var5 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var5;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         if(this.getIntent().getBooleanExtra("within_tab", (boolean)0)) {
            this.findViewById(2131623958).setVisibility(8);
         }

         this.setupListHeaders();
         this.setupFatTitleHeader();
         PlacesInfoAdapter var6 = new PlacesInfoAdapter(this);
         this.mAdapter = var6;
         PlacesInfoAdapter var7 = this.mAdapter;
         FacebookPlace var8 = this.mPlaceInfo;
         var7.setPlaceInfo(var8);
         ListView var9 = this.getListView();
         PlacesInfoAdapter var10 = this.mAdapter;
         var9.setAdapter(var10);
         PlacesInfoActivity.ActivityBlob var11 = (PlacesInfoActivity.ActivityBlob)this.getLastNonConfigurationInstance();
         if(var11 != null) {
            FacebookPlace var12 = var11.mBlobInfo;
            this.handleInfo(var12);
         }

         this.setupEmptyView();
      }
   }

   public void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      }
   }

   public Object onRetainNonConfigurationInstance() {
      PlacesInfoActivity.ActivityBlob var2;
      if(this.mPlaceInfo != null) {
         FacebookPlace var1 = this.mPlaceInfo;
         var2 = new PlacesInfoActivity.ActivityBlob(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public void setProgressListener(TabProgressListener var1) {
      this.mProgressListener = var1;
      if(this.mProgressListener != null) {
         TabProgressListener var2 = this.mProgressListener;
         boolean var3 = this.mShowingProgress;
         var2.onShowProgress(var3);
      }
   }

   private class ActivityBlob {

      final FacebookPlace mBlobInfo;


      public ActivityBlob(FacebookPlace var2) {
         this.mBlobInfo = var2;
      }
   }
}
