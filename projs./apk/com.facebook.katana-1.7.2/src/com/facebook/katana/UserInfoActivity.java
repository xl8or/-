package com.facebook.katana;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ProfileInfoActivity;
import com.facebook.katana.ProfileInfoAdapter;
import com.facebook.katana.UserInfoAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.dialog.Dialogs;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookUserFull;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.List;

public class UserInfoActivity extends ProfileInfoActivity implements OnItemClickListener {

   private static final int VIEW_PROFILE = 2;
   private FacebookUserFull mInfo;
   private long mUserId;


   public UserInfoActivity() {}

   private void handleInfo(FacebookUserFull var1, boolean var2) {
      ((UserInfoAdapter)this.mAdapter).setUserInfo(var1, var2);
      this.mInfo = var1;
      if(!var2) {
         ViewStub var3 = (ViewStub)this.findViewById(2131624195);
         if(var3 != null) {
            View var4 = var3.inflate();
            View var5 = this.findViewById(2131624274);
            UserInfoActivity.2 var6 = new UserInfoActivity.2();
            var5.setOnClickListener(var6);
         }
      }
   }

   private void setupEmptyView() {
      ((TextView)this.findViewById(2131624022)).setText(2131362138);
      ((TextView)this.findViewById(2131624024)).setText(2131362135);
   }

   public void onCreate(Bundle var1) {
      if(this.getIntent().getBooleanExtra("within_tab", (boolean)0)) {
         this.mHasFatTitleHeader = (boolean)1;
      }

      super.onCreate(var1);
      this.setContentView(2130903138);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         if(this.getParent() != null) {
            this.findViewById(2131623958).setVisibility(8);
         }

         long var3 = this.getIntent().getLongExtra("com.facebook.katana.profile.id", 0L);
         this.mUserId = var3;
         this.setupListHeaders();
         this.setupFatTitleHeader();
         UserInfoActivity.UserInfoAppSessionListener var5 = new UserInfoActivity.UserInfoAppSessionListener((UserInfoActivity.1)null);
         this.mAppSessionListener = var5;
         StreamPhotosCache var6 = this.mAppSession.getPhotosCache();
         boolean var7 = this.getIntent().getBooleanExtra("com.facebook.katana.profile.show_photo", (boolean)1);
         boolean var8 = this.getIntent().getBooleanExtra("com.facebook.katana.profile.is.limited", (boolean)0);
         UserInfoAdapter var9 = new UserInfoAdapter(this, var6, var7, var8);
         this.mAdapter = var9;
         ListView var10 = this.getListView();
         ProfileInfoAdapter var11 = this.mAdapter;
         var10.setAdapter(var11);
         this.setupEmptyView();
         var10.setOnItemClickListener(this);
      }
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 1:
         long var3 = this.mUserId;
         UserInfoActivity.1 var5 = new UserInfoActivity.1();
         var2 = Dialogs.addFriend(this, var3, var5);
         break;
      case 2:
         ProgressDialog var6 = new ProgressDialog(this);
         var6.setProgressStyle(0);
         CharSequence var7 = this.getText(2131362119);
         var6.setMessage(var7);
         var6.setIndeterminate((boolean)1);
         var6.setCancelable((boolean)0);
         var2 = var6;
         break;
      default:
         var2 = null;
      }

      return (Dialog)var2;
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
         return;
      case 3:
         Uri var15 = Uri.parse("mailto:");
         Intent var16 = new Intent("android.intent.action.SENDTO", var15);
         String[] var17 = new String[1];
         String var18 = var8.getSubTitle();
         var17[0] = var18;
         var16.putExtra("android.intent.extra.EMAIL", var17);
         String var20 = this.getString(2131362126);
         Intent var21 = Intent.createChooser(var16, var20);
         this.startActivity(var21);
         return;
      case 4:
         long var22 = var8.getTargetId();
         ApplicationUtils.OpenUserProfile(this, var22, (FacebookProfile)null);
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

   public boolean onPrepareOptionsMenu(Menu var1) {
      super.onPrepareOptionsMenu(var1);
      MenuItem var3 = var1.findItem(2);
      byte var4;
      if(this.mInfo != null && this.mInfo.mUrl != null) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      var3.setEnabled((boolean)var4);
      return true;
   }

   public void onResume() {
      super.onResume();
      if(this.mInfo == null) {
         AppSession var1 = this.mAppSession;
         long var2 = this.mUserId;
         var1.usersGetInfo(this, var2);
         this.logStepDataRequested();
         this.showProgress((boolean)1);
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         UserInfoActivity.this.showDialog(1);
      }
   }

   private class UserInfoAppSessionListener extends ProfileInfoActivity.InfoAppSessionListener {

      private UserInfoAppSessionListener() {
         super();
      }

      // $FF: synthetic method
      UserInfoAppSessionListener(UserInfoActivity.1 var2) {
         this();
      }

      public void onFriendsAddFriendComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<Long> var6) {
         UserInfoActivity.this.removeDialog(2);
         UserInfoActivity.this.mReqId = null;
      }

      public void onUsersGetInfoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, FacebookUserFull var8, boolean var9) {
         long var10 = UserInfoActivity.this.mUserId;
         if(var6 == var10) {
            UserInfoActivity.this.showProgress((boolean)0);
            if(var3 == 200) {
               UserInfoActivity.this.logStepDataReceived();
               if(var8 != null) {
                  UserInfoActivity.this.handleInfo(var8, var9);
               } else {
                  Toaster.toast(UserInfoActivity.this, 2131362139);
                  UserInfoActivity.this.finish();
               }
            } else {
               UserInfoActivity var12 = UserInfoActivity.this;
               String var13 = UserInfoActivity.this.getString(2131362127);
               String var14 = StringUtils.getErrorString(var12, var13, var3, var4, var5);
               Toaster.toast(UserInfoActivity.this, var14);
            }
         }
      }
   }

   class 1 implements Dialogs.AddFriendListener {

      1() {}

      public void onAddFriendStart(String var1) {
         UserInfoActivity.this.mReqId = var1;
         UserInfoActivity.this.showDialog(2);
      }
   }
}
