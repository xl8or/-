package com.facebook.katana;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.PageInfoAdapter;
import com.facebook.katana.ProfileInfoActivity;
import com.facebook.katana.ProfileInfoAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookPageFull;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetPageFanStatus;
import com.facebook.katana.service.method.FqlGetPages;
import com.facebook.katana.service.method.PagesAddFan;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;

public class PageInfoActivity extends ProfileInfoActivity implements OnItemClickListener {

   private boolean mCanLike;
   private FacebookPageFull mInfo;
   private boolean mIsTab;
   private long mPageId;
   private String mPendingLikeReqId;


   public PageInfoActivity() {}

   private void handleInfo(FacebookPageFull var1) {
      ((PageInfoAdapter)this.mAdapter).setPageInfo(var1);
      this.mInfo = var1;
   }

   private void setupEmptyView() {
      ((TextView)this.findViewById(2131624022)).setText(2131362138);
      ((TextView)this.findViewById(2131624024)).setText(2131362038);
   }

   public void onCreate(Bundle var1) {
      if(this.getIntent().getBooleanExtra("within_tab", (boolean)0)) {
         this.mHasFatTitleHeader = (boolean)1;
      }

      super.onCreate(var1);
      this.setContentView(2130903117);
      long var2 = this.getIntent().getLongExtra("com.facebook.katana.profile.id", 0L);
      this.mPageId = var2;
      AppSession var4 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var4;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         if(this.getParent() != null) {
            this.findViewById(2131623958).setVisibility(8);
         } else {
            long var11 = this.mProfileId;
            FqlGetPageFanStatus.RequestPageFanStatus(this, var11);
         }

         this.setupListHeaders();
         this.setupFatTitleHeader();
         PageInfoActivity.PageInfoAppSessionListener var5 = new PageInfoActivity.PageInfoAppSessionListener((PageInfoActivity.1)null);
         this.mAppSessionListener = var5;
         StreamPhotosCache var6 = this.mAppSession.getPhotosCache();
         boolean var7 = this.getIntent().getBooleanExtra("com.facebook.katana.profile.show_photo", (boolean)1);
         PageInfoAdapter var8 = new PageInfoAdapter(this, var6, var7);
         this.mAdapter = var8;
         ListView var9 = this.getListView();
         ProfileInfoAdapter var10 = this.mAdapter;
         var9.setAdapter(var10);
         this.setupEmptyView();
         var9.setOnItemClickListener(this);
      }
   }

   protected Dialog onCreateDialog(int var1) {
      ProgressDialog var2;
      switch(var1) {
      case 2:
         ProgressDialog var3 = new ProgressDialog(this);
         var3.setProgressStyle(0);
         CharSequence var4 = this.getText(2131362040);
         var3.setMessage(var4);
         var3.setIndeterminate((boolean)1);
         var3.setCancelable((boolean)0);
         var2 = var3;
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      ProfileInfoAdapter var6 = this.mAdapter;
      int var7 = this.getCursorPosition(var3);
      ProfileInfoAdapter.Item var8 = var6.getItemByPosition(var7);
      switch(var8.getType()) {
      case 0:
         if(this.mAppSession == null) {
            return;
         } else if(this.mInfo == null) {
            return;
         } else {
            String var9 = this.mInfo.mUrl;
            if(var9 == null) {
               return;
            }

            this.mAppSession.openURL(this, var9);
            return;
         }
      case 1:
      default:
         return;
      case 2:
         String var11 = var8.getSubTitle();
         Uri var12 = Uri.fromParts("tel", var11, (String)null);
         Intent var13 = new Intent("android.intent.action.DIAL", var12);
         Intent var14 = var13.setFlags(268435456);
         this.startActivity(var13);
      }
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      switch(var1.getItemId()) {
      case 2:
         AppSession var2 = this.mAppSession;
         String var3 = this.mInfo.mUrl;
         var2.openURL(this, var3);
      default:
         return super.onOptionsItemSelected(var1);
      }
   }

   public void onResume() {
      super.onResume();
      if(this.mInfo == null) {
         StringBuilder var1 = (new StringBuilder()).append("page_id = ");
         long var2 = this.mPageId;
         String var4 = var1.append(var2).toString();
         FqlGetPages.RequestPageInfo(this, var4, FacebookPageFull.class);
         this.logStepDataRequested();
         this.showProgress((boolean)1);
      }
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      if(this.mCanLike) {
         String var2 = this.mAppSession.getSessionInfo().sessionKey;
         long var3 = this.mProfileId;
         Object var6 = null;
         PagesAddFan var7 = new PagesAddFan(this, (Intent)null, var2, var3, (ApiMethodListener)var6);
         AppSession var8 = this.mAppSession;
         Object var11 = null;
         String var12 = var8.postToService(this, var7, 1001, 1020, (Bundle)var11);
         this.mPendingLikeReqId = var12;
         this.setPrimaryActionFace(-1, (String)null);
         this.showProgress((boolean)1);
      }
   }

   private class PageInfoAppSessionListener extends ProfileInfoActivity.InfoAppSessionListener {

      private PageInfoAppSessionListener() {
         super();
      }

      // $FF: synthetic method
      PageInfoAppSessionListener(PageInfoActivity.1 var2) {
         this();
      }

      public void onGetPageFanStatusComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, boolean var8) {
         if(!PageInfoActivity.this.mIsTab) {
            long var9 = PageInfoActivity.this.mProfileId;
            if(var6 == var9) {
               if(!var8) {
                  boolean var11 = (boolean)(PageInfoActivity.this.mCanLike = (boolean)1);
                  PageInfoActivity var12 = PageInfoActivity.this;
                  String var13 = PageInfoActivity.this.getString(2131362231);
                  var12.setPrimaryActionFace(-1, var13);
               } else {
                  boolean var14 = (boolean)(PageInfoActivity.this.mCanLike = (boolean)0);
                  PageInfoActivity.this.setPrimaryActionFace(-1, (String)null);
               }
            }
         }
      }

      public void onPagesAddFanComplete(AppSession var1, String var2, int var3, String var4, Exception var5, boolean var6) {
         if(var3 == 200) {
            String var7 = PageInfoActivity.this.mPendingLikeReqId;
            if(var2.equals(var7)) {
               if(PageInfoActivity.this.isOnTop()) {
                  PageInfoActivity var8 = PageInfoActivity.this;
                  long var9 = PageInfoActivity.this.mProfileId;
                  ApplicationUtils.OpenPageProfile(var8, var9, (FacebookProfile)null);
                  PageInfoActivity.this.findViewById(2131624177).setVisibility(8);
                  PageInfoActivity.this.finish();
               }
            }
         }
      }

      public <typeClass extends Object> void onPagesGetInfoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, typeClass var8) {
         long var9 = PageInfoActivity.this.mPageId;
         if(var6 == var9) {
            PageInfoActivity.this.showProgress((boolean)0);
            if(var3 == 200) {
               PageInfoActivity.this.logStepDataReceived();
               if(var8 != false) {
                  if(var8 instanceof FacebookPageFull) {
                     PageInfoActivity var11 = PageInfoActivity.this;
                     FacebookPageFull var12 = (FacebookPageFull)var8;
                     var11.handleInfo(var12);
                  }
               } else {
                  Toaster.toast(PageInfoActivity.this, 2131362044);
                  PageInfoActivity.this.finish();
               }
            } else {
               PageInfoActivity var13 = PageInfoActivity.this;
               String var14 = PageInfoActivity.this.getString(2131362045);
               String var15 = StringUtils.getErrorString(var13, var14, var3, var4, var5);
               Toaster.toast(PageInfoActivity.this, var15);
            }
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
