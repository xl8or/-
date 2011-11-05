package com.facebook.katana;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.RequestsAdapter;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.Toaster;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestsActivity extends BaseFacebookListActivity {

   public static final String EXTRA_FRIEND_REQUESTS = "extra_frend_requests";
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private RequestsAdapter mRequestsAdapter;
   private boolean mSkipFetchOnResume = 0;


   public RequestsActivity() {
      RequestsActivity.1 var1 = new RequestsActivity.1();
      this.mAppSessionListener = var1;
   }

   private void createAdapter(Map<Long, FacebookUser> var1) {
      if(this.mRequestsAdapter == null) {
         AppSession var2 = this.mAppSession;
         RequestsAdapter var3 = new RequestsAdapter(this, var2, var1);
         this.mRequestsAdapter = var3;
         RequestsAdapter var4 = this.mRequestsAdapter;
         this.setListAdapter(var4);
      } else {
         this.mRequestsAdapter.setupRequestors(var1);
         this.mRequestsAdapter.notifyDataSetChanged();
      }
   }

   private void showNoRequestors() {
      View var1 = (View)this.getListView().getParent();
      TextView var2 = (TextView)var1.findViewById(2131624022);
      var2.setVisibility(0);
      var2.setText(2131362181);
      var1.findViewById(2131624023).setVisibility(4);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903150);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         Intent var3 = this.getIntent();
         LoginActivity.toLogin(this, var3);
      } else {
         Intent var4 = this.getIntent();
         if(var4 != null) {
            if(var4.hasExtra("extra_frend_requests")) {
               Parcelable[] var5 = var4.getExtras().getParcelableArray("extra_frend_requests");
               LinkedHashMap var6 = new LinkedHashMap();
               Parcelable[] var7 = var5;
               int var8 = var5.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  FacebookUser var10 = (FacebookUser)var7[var9];
                  Long var11 = Long.valueOf(var10.mUserId);
                  var6.put(var11, var10);
               }

               this.createAdapter(var6);
               if(var6.size() == 0) {
                  this.showNoRequestors();
               }

               this.mSkipFetchOnResume = (boolean)1;
            }
         }
      }
   }

   protected void onDestroy() {
      super.onDestroy();
      if(this.mRequestsAdapter != null) {
         this.mRequestsAdapter.onDestroy();
      }
   }

   protected void onPause() {
      super.onPause();
      this.mSkipFetchOnResume = (boolean)0;
      if(this.mRequestsAdapter != null && this.mRequestsAdapter.mSyncRequired) {
         String var1 = this.mAppSession.syncFriends(this);
         this.mRequestsAdapter.mSyncRequired = (boolean)0;
      }

      AppSession var2 = this.mAppSession;
      AppSessionListener var3 = this.mAppSessionListener;
      var2.removeListener(var3);
   }

   protected void onResume() {
      super.onResume();
      if(!this.mSkipFetchOnResume) {
         AppSession var1 = this.mAppSession;
         AppSessionListener var2 = this.mAppSessionListener;
         var1.addListener(var2);
         long var3 = this.mAppSession.getSessionInfo().userId;
         this.mAppSession.getFriendRequests(this, var3);
         this.logStepDataRequested();
      }
   }

   class 1 extends AppSessionListener {

      1() {}

      public void onUserGetFriendRequestsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, Map<Long, FacebookUser> var6) {
         if(var6 == null) {
            Toaster.toast(RequestsActivity.this, 2131362177);
         } else {
            RequestsActivity.this.logStepDataReceived();
            RequestsActivity.this.createAdapter(var6);
            if(var6.size() == 0) {
               RequestsActivity.this.showNoRequestors();
            }
         }

         var1.removeListener(this);
      }
   }
}
