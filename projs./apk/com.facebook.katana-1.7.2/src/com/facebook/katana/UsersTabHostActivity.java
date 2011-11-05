package com.facebook.katana;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.TextView;
import com.facebook.katana.FriendsActivity;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.MyTabHost;
import com.facebook.katana.PageSearchResultsActivity;
import com.facebook.katana.RequestsActivity;
import com.facebook.katana.TabProgressListener;
import com.facebook.katana.UsersTabProgressSource;
import com.facebook.katana.activity.BaseFacebookTabActivity;
import com.facebook.katana.activity.findfriends.FindFriendsActivity;
import com.facebook.katana.activity.findfriends.LegalDisclaimerActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.GrowthUtils;
import java.util.Iterator;
import java.util.Map;

public class UsersTabHostActivity extends BaseFacebookTabActivity implements MyTabHost.OnTabChangeListener, TabProgressListener {

   public static final int IMPORT_CONTACT_CONSENT = 0;
   public static final String INTENT_DEFAULT_TAB_KEY = "com.facebook.katana.DefaultTab";
   public static final String TAG_FRIENDS = "friends";
   public static final String TAG_PAGES_SEARCH = "page_search";
   public static final String TAG_REQUESTS = "requests";
   private Activity mCurrentTab;
   private Integer mNumRequests;
   private boolean mRequestsActivityStarted = 0;
   private AppSessionListener mRequestsListener;
   private TextView mTextBox;


   public UsersTabHostActivity() {
      UsersTabHostActivity.1 var1 = new UsersTabHostActivity.1();
      this.mRequestsListener = var1;
   }

   private void setUpProgressListener() {
      if(this.mCurrentTab instanceof UsersTabProgressSource) {
         ((UsersTabProgressSource)this.mCurrentTab).setProgressListener(this);
      }
   }

   private void setUpTextHint(String var1) {
      if(var1.equals("friends")) {
         this.mTextBox.setHint(2131361900);
         this.findViewById(2131624275).setVisibility(0);
      } else if(var1.equals("page_search")) {
         this.mTextBox.setHint(2131361907);
         this.findViewById(2131624275).setVisibility(0);
      } else if(var1.equals("requests")) {
         this.findViewById(2131624275).setVisibility(8);
         InputMethodManager var2 = (InputMethodManager)this.getSystemService("input_method");
         IBinder var3 = this.mTextBox.getWindowToken();
         var2.hideSoftInputFromWindow(var3, 0);
         this.mRequestsActivityStarted = (boolean)1;
      }
   }

   protected void findFriendsActivityConsentCheck() {
      if(!GrowthUtils.shouldShowLegalScreen(this)) {
         Intent var1 = new Intent(this, FindFriendsActivity.class);
         this.startActivity(var1);
      } else {
         Intent var2 = new Intent(this, LegalDisclaimerActivity.class);
         this.startActivityForResult(var2, 0);
      }
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 == 0) {
         if(var2 == -1) {
            GrowthUtils.setFindFriendsConsentApproved(this);
            GrowthUtils.stopFindFriendsDialog(this);
            Intent var4 = new Intent(this, FindFriendsActivity.class);
            this.startActivity(var4);
         }
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903180);
      this.hideSearchButton();
      String var2 = this.getString(2131362437);
      this.setPrimaryActionFace(-1, var2);
      MyTabHost var3 = (MyTabHost)this.getTabHost();
      var3.handleTouchMode((boolean)0);
      RadioButton var4 = this.setupAndGetTabView("friends", 2131361901);
      MyTabHost.TabSpec var5 = var3.myNewTabSpec("friends", var4);
      Intent var6 = new Intent(this, FriendsActivity.class);
      Intent var7 = var6.putExtra("within_tab", (boolean)1);
      String var8 = this.getTag();
      var6.putExtra("extra_parent_tag", var8);
      var5.setContent(var6);
      var3.addTab(var5);
      RadioButton var11 = this.setupAndGetTabView("page_search", 2131362069);
      MyTabHost.TabSpec var12 = var3.myNewTabSpec("page_search", var11);
      Intent var13 = new Intent(this, PageSearchResultsActivity.class);
      Intent var14 = var13.putExtra("within_tab", (boolean)1);
      String var15 = this.getTag();
      var13.putExtra("extra_parent_tag", var15);
      var12.setContent(var13);
      var3.addTab(var12);
      RadioButton var18 = this.setupAndGetTabView("requests", 2131361928);
      MyTabHost.TabSpec var19 = var3.myNewTabSpec("requests", var18);
      Intent var20 = new Intent(this, RequestsActivity.class);
      Intent var21 = var20.putExtra("within_tab", (boolean)1);
      var19.setContent(var20);
      var3.addTab(var19);
      TextView var23 = (TextView)this.findViewById(2131623961);
      this.mTextBox = var23;
      TextView var24 = this.mTextBox;
      UsersTabHostActivity.2 var25 = new UsersTabHostActivity.2();
      var24.addTextChangedListener(var25);
      Activity var26 = this.getCurrentActivity();
      this.mCurrentTab = var26;
      if(this.mCurrentTab instanceof UsersTabProgressSource) {
         ((UsersTabProgressSource)this.mCurrentTab).setProgressListener(this);
      }

      String var27 = "friends";
      String var28 = this.getIntent().getStringExtra("com.facebook.katana.DefaultTab");
      if(var28 != null) {
         var27 = var28;
      }

      var3.setCurrentTabByTag(var27);
      Activity var29 = this.getCurrentActivity();
      this.mCurrentTab = var29;
      this.setUpTextHint(var27);
      this.setUpProgressListener();
      boolean var30 = this.mTextBox.requestFocus();
      var3.setOnTabChangedListener(this);
      this.setupTabs();
   }

   protected void onDestroy() {
      super.onDestroy();
      if(this.mCurrentTab != null) {
         if(this.mCurrentTab instanceof UsersTabProgressSource) {
            ((UsersTabProgressSource)this.mCurrentTab).setProgressListener((TabProgressListener)null);
         }
      }
   }

   public void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      if(var1 == null) {
         Intent var2 = this.getIntent();
         LoginActivity.toLogin(this, var2);
         this.finish();
      } else {
         AppSessionListener var7 = this.mRequestsListener;
         var1.addListener(var7);
         long var8 = var1.getSessionInfo().userId;
         var1.getFriendRequests(this, var8);
      }

      if(this.mNumRequests != null) {
         Iterator var3 = ((MyTabHost)this.getTabHost()).getTabSpecs().iterator();

         while(var3.hasNext()) {
            MyTabHost.TabSpec var4 = (MyTabHost.TabSpec)var3.next();
            if(var4.tag.equals("requests")) {
               RadioButton var5 = (RadioButton)var4.view;
               if(this.mNumRequests.intValue() == 0) {
                  String var6 = this.getString(2131361928);
                  var5.setText(var6);
               } else {
                  Object[] var11 = new Object[1];
                  Integer var12 = this.mNumRequests;
                  var11[0] = var12;
                  String var13 = this.getString(2131362173, var11);
                  var5.setText(var13);
               }
            }
         }

      }
   }

   public boolean onSearchRequested() {
      return true;
   }

   public void onShowProgress(boolean var1) {
      View var2 = this.findViewById(2131624177);
      byte var3;
      if(var1) {
         var3 = 0;
      } else {
         var3 = 8;
      }

      var2.setVisibility(var3);
   }

   public void onTabChanged(String var1) {
      if(this.mCurrentTab != null && this.mCurrentTab instanceof UsersTabProgressSource) {
         ((UsersTabProgressSource)this.mCurrentTab).setProgressListener((TabProgressListener)null);
      }

      Activity var2 = this.getCurrentActivity();
      this.mCurrentTab = var2;
      if(this.mCurrentTab instanceof UsersTabProgressSource) {
         ((UsersTabProgressSource)this.mCurrentTab).setProgressListener(this);
         UsersTabProgressSource var3 = (UsersTabProgressSource)this.mCurrentTab;
         String var4 = this.mTextBox.getText().toString().trim();
         var3.search(var4);
      }

      this.setUpTextHint(var1);
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      this.findFriendsActivityConsentCheck();
   }

   class 1 extends AppSessionListener {

      1() {}

      public void onUserGetFriendRequestsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, Map<Long, FacebookUser> var6) {
         if(var6 != null) {
            Parcelable[] var7 = new Parcelable[var6.size()];
            int var8 = 0;

            int var11;
            for(Iterator var9 = var6.values().iterator(); var9.hasNext(); var8 = var11) {
               FacebookUser var10 = (FacebookUser)var9.next();
               var11 = var8 + 1;
               var7[var8] = var10;
            }

            Iterator var12 = ((MyTabHost)UsersTabHostActivity.this.getTabHost()).getTabSpecs().iterator();

            while(var12.hasNext()) {
               MyTabHost.TabSpec var13 = (MyTabHost.TabSpec)var12.next();
               if(var13.tag.equals("requests")) {
                  RadioButton var14 = (RadioButton)var13.view;
                  if(UsersTabHostActivity.this.isOnTop()) {
                     if(var7.length == 0) {
                        String var15 = UsersTabHostActivity.this.getString(2131361928);
                        var14.setText(var15);
                     } else {
                        UsersTabHostActivity var24 = UsersTabHostActivity.this;
                        Object[] var25 = new Object[1];
                        Integer var26 = Integer.valueOf(var6.size());
                        var25[0] = var26;
                        String var27 = var24.getString(2131362173, var25);
                        var14.setText(var27);
                     }
                  }

                  UsersTabHostActivity var16 = UsersTabHostActivity.this;
                  Integer var17 = Integer.valueOf(var6.size());
                  var16.mNumRequests = var17;
                  if(!UsersTabHostActivity.this.mRequestsActivityStarted) {
                     UsersTabHostActivity var19 = UsersTabHostActivity.this;
                     Intent var20 = new Intent(var19, RequestsActivity.class);
                     Intent var21 = var20.putExtra("within_tab", (boolean)1);
                     var20.putExtra("extra_frend_requests", var7);
                     var13.setContent(var20);
                  }
               }
            }
         }

         var1.removeListener(this);
      }
   }

   class 2 implements TextWatcher {

      2() {}

      public void afterTextChanged(Editable var1) {}

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
         String var5 = var1.toString().trim();
         if(UsersTabHostActivity.this.mCurrentTab instanceof UsersTabProgressSource) {
            ((UsersTabProgressSource)UsersTabHostActivity.this.mCurrentTab).search(var5);
         }
      }
   }
}
