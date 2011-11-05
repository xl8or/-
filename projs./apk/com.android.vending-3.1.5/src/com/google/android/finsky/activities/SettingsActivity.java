package com.google.android.finsky.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.MenuItem;
import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.ContentFilterActivity;
import com.google.android.finsky.activities.FakeNavigationManager;
import com.google.android.finsky.activities.PinEntryDialog;
import com.google.android.finsky.ads.AdSettings;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.layout.CustomActionBar;
import com.google.android.finsky.layout.CustomActionBarFactory;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.LinkPreference;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.finsky.utils.SharedPreferencesUtils;
import com.google.android.finsky.utils.VendingPreferences;

public class SettingsActivity extends PreferenceActivity {

   private static final String PREFS_KEY_FILTER_PASSCODE = "pin_code";
   private static final String PREFS_KEY_PURCHASE_LOCK = "purchase-lock";
   private static final int REQUEST_CODE_CONFIRM_PIN = 33;
   private static final int REQUEST_CODE_CONTENT_FILTER = 30;
   private static final int REQUEST_CODE_EDIT_PIN = 32;
   private static final int REQUEST_CODE_GET_UNLOCK_PIN = 31;
   public static final int RESULT_CODE_CONTENT_LEVEL = 40;
   private static final String RESULT_KEY_NEW_PIN = "new-pin";
   private static final String SETTINGS_KEY_ADMOB = "admob-ad";
   private static final String SETTINGS_KEY_ADMOB_DESC = "admob-ad-desc";
   private static final String SETTINGS_KEY_BUILD_VERSION = "build-version";
   private static final String SETTINGS_KEY_CHANGE_PASSCODE = "change-passcode";
   private static final String SETTINGS_KEY_CLEAR_HISTORY = "clear-history";
   private static final String SETTINGS_KEY_CONTENT_LEVEL = "content-level";
   private static final String SETTINGS_KEY_PURCHASE_LOCK = "purchase-lock";
   private static final String SETTINGS_KEY_UNLOCK_SETTINGS = "unlock-settings";
   private static final String SETTINGS_KEY_UPDATE_NOTIFICATIONS = "update-notifications";
   private String mAccountName;
   private CustomActionBar mActionBar;
   private boolean mDestroyed;
   private NavigationManager mNavigationManager;
   private SharedPreferences mSettings;
   private boolean mUserSettingsEnabled;


   public SettingsActivity() {
      FakeNavigationManager var1 = new FakeNavigationManager(this);
      this.mNavigationManager = var1;
   }

   private void changePasscode(String var1) {
      if(TextUtils.isEmpty(var1)) {
         Editor var2 = this.mSettings.edit();
         Editor var3 = var2.remove("pin_code");
         Editor var4 = var2.putBoolean("purchase-lock", (boolean)0);
         ((CheckBoxPreference)this.getPreferenceScreen().findPreference("purchase-lock")).setChecked((boolean)0);
         boolean var5 = SharedPreferencesUtils.commit(var2);
      } else {
         Editor var6 = this.mSettings.edit();
         var6.putString("pin_code", var1);
         boolean var8 = SharedPreferencesUtils.commit(var6);
      }
   }

   private void configureAboutSection(PreferenceScreen var1) {
      Preference var2 = var1.findPreference("build-version");
      PackageInfoCache var3 = FinskyApp.get().getPackageInfoCache();
      String var4 = FinskyApp.get().getPackageName();
      String var5 = var3.getPackageVersionName(var4);
      Object[] var6 = new Object[]{var5};
      String var7 = this.getString(2131231116, var6);
      var2.setSummary(var7);
   }

   private void configureAdPrefsSection(PreferenceScreen var1) {
      CheckBoxPreference var2 = (CheckBoxPreference)var1.findPreference("admob-ad");
      boolean var3 = ((Boolean)VendingPreferences.INTEREST_BASED_ADS_ENABLED.get()).booleanValue();
      this.setInterestBasedAds(var2, var3);
      LinkPreference var4 = (LinkPreference)var1.findPreference("admob-ad-desc");
      var4.setLayoutResource(2130968661);
      String var5 = (String)G.adPrefsLearnMoreUrl.get();
      String var6 = AdSettings.getSigString(this.getApplicationContext());
      if(var6 != null) {
         var5 = var5 + "?sig=" + var6;
      }

      Object[] var7 = new Object[]{var5};
      Spanned var8 = Html.fromHtml(this.getString(2131231129, var7));
      var4.setLink(var8);
   }

   private void configureUpdateNotifications(PreferenceScreen var1) {
      CheckBoxPreference var2 = (CheckBoxPreference)var1.findPreference("update-notifications");
      boolean var3 = ((Boolean)VendingPreferences.NOTIFY_UPDATES.get()).booleanValue();
      var2.setChecked(var3);
   }

   private void configureUserControlsSection(PreferenceScreen var1) {
      if(((Boolean)G.vendingHideContentRating.get()).booleanValue()) {
         Preference var2 = var1.findPreference("content-level");
         var1.removePreference(var2);
      }

      boolean var4 = this.mSettings.getBoolean("purchase-lock", (boolean)0);
      ((CheckBoxPreference)var1.findPreference("purchase-lock")).setChecked(var4);
      this.syncUserControlsState();
   }

   private String getCurrentPin() {
      SharedPreferences var1 = this.mSettings;
      return SharedPreferencesUtils.getCurrentPasscode(this, var1);
   }

   private void processAdSettingChange(CheckBoxPreference var1, boolean var2) {
      var1.setEnabled((boolean)0);
      FinskyApp var3 = FinskyApp.get();
      RequestQueue var4 = FinskyApp.get().getRequestQueue();
      AdSettings var5 = new AdSettings(var3, var4);
      SettingsActivity.1 var6 = new SettingsActivity.1(var2, var1);
      SettingsActivity.2 var7 = new SettingsActivity.2(var1, var2);
      var5.enableInterestBasedAds(var2, var6, var7);
   }

   private void setInterestBasedAds(CheckBoxPreference var1, boolean var2) {
      if(!this.mDestroyed) {
         var1.setChecked(var2);
         var1.setEnabled((boolean)1);
      }
   }

   private void setupActionBar() {
      CustomActionBar var1 = CustomActionBarFactory.getInstance(this);
      this.mActionBar = var1;
      CustomActionBar var2 = this.mActionBar;
      NavigationManager var3 = this.mNavigationManager;
      var2.initialize(var3, this);
      this.mActionBar.updateCurrentBackendId(0);
      CustomActionBar var4 = this.mActionBar;
      String var5 = this.getString(2131231073);
      var4.updateBreadcrumb(var5);
   }

   private void syncUserControlsState() {
      Preference var1 = this.findPreference("unlock-settings");
      byte var2;
      if(!this.mUserSettingsEnabled) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      var1.setEnabled((boolean)var2);
      Preference var3 = this.findPreference("content-level");
      boolean var4 = this.mUserSettingsEnabled;
      var3.setEnabled(var4);
      byte var5;
      if(this.mUserSettingsEnabled && !TextUtils.isEmpty(this.getCurrentPin())) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      this.findPreference("purchase-lock").setEnabled((boolean)var5);
      Preference var6 = this.findPreference("change-passcode");
      boolean var7 = this.mUserSettingsEnabled;
      var6.setEnabled(var7);
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 == 30 && var2 == -1) {
         this.setResult(40);
         this.finish();
      } else if(var1 == 31 && var2 == -1) {
         this.mUserSettingsEnabled = (boolean)1;
         this.syncUserControlsState();
      } else if(var1 == 32 && var2 == -1) {
         String var4 = var3.getStringExtra("new-pin");
         if(TextUtils.isEmpty(var4)) {
            this.changePasscode((String)null);
         } else {
            Intent var5 = PinEntryDialog.getIntent(this, 2131231068, var4, "new-pin", (boolean)0);
            this.startActivityForResult(var5, 33);
         }
      } else if(var1 == 33 && var2 == -1) {
         String var6 = var3.getStringExtra("new-pin");
         this.changePasscode(var6);
      } else {
         super.onActivityResult(var1, var2, var3);
      }
   }

   protected void onCreate(Bundle var1) {
      boolean var2 = CustomActionBarFactory.shouldUseSystemActionBar();
      byte var3;
      if(var2) {
         var3 = 8;
      } else {
         var3 = 7;
      }

      this.requestWindowFeature(var3);
      super.onCreate(var1);
      if(!var2) {
         this.getWindow().setFeatureInt(7, 2130968576);
      }

      this.addPreferencesFromResource(2130968706);
      FinskyApp.get().getAnalytics().logPageView((String)null, (String)null, "settings");
      SharedPreferences var5 = this.getSharedPreferences("finsky", 0);
      this.mSettings = var5;
      String var6 = FinskyApp.get().getDfeApi().getCurrentAccountName();
      this.mAccountName = var6;
      this.setupActionBar();
      boolean var7 = TextUtils.isEmpty(this.getCurrentPin());
      this.mUserSettingsEnabled = var7;
   }

   protected void onDestroy() {
      this.mDestroyed = (boolean)1;
      super.onDestroy();
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2;
      switch(var1.getItemId()) {
      case 16908332:
         this.onBackPressed();
         var2 = true;
         break;
      default:
         var2 = super.onOptionsItemSelected(var1);
      }

      return var2;
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      String var3 = var2.getKey();
      boolean var4 = true;
      boolean var7;
      if("admob-ad".equals(var3)) {
         CheckBoxPreference var5 = (CheckBoxPreference)var2;
         boolean var6 = var5.isChecked();
         this.processAdSettingChange(var5, var6);
      } else if("update-notifications".equals(var3)) {
         CheckBoxPreference var8 = (CheckBoxPreference)var2;
         PreferenceFile.SharedPreference var9 = VendingPreferences.NOTIFY_UPDATES;
         Boolean var10 = Boolean.valueOf(var8.isChecked());
         var9.put(var10);
      } else if("clear-history".equals(var3)) {
         FinskyApp.get().getRecentSuggestions().clearHistory();
      } else {
         if("purchase-lock".equals(var3)) {
            boolean var11 = ((CheckBoxPreference)var2).isChecked();
            Editor var12 = this.mSettings.edit();
            var12.putBoolean("purchase-lock", var11);
            boolean var14 = SharedPreferencesUtils.commit(var12);
            var7 = false;
            return var7;
         }

         if("unlock-settings".equals(var3)) {
            String var15 = this.getCurrentPin();
            Intent var16 = PinEntryDialog.getIntent(this, 2131231070, var15, (String)null, (boolean)0);
            this.startActivityForResult(var16, 31);
         } else if("content-level".equals(var3)) {
            String var17 = this.mAccountName;
            Intent var18 = IntentUtils.createAccountSpecificIntent(this, ContentFilterActivity.class, "authAccount", var17);
            this.startActivityForResult(var18, 30);
         } else if("change-passcode".equals(var3)) {
            byte var19;
            if(!TextUtils.isEmpty(this.getCurrentPin())) {
               var19 = 1;
            } else {
               var19 = 0;
            }

            int var20;
            if(var19 != 0) {
               var20 = 2131231066;
            } else {
               var20 = 2131231067;
            }

            Intent var21 = PinEntryDialog.getIntent(this, var20, (String)null, "new-pin", (boolean)var19);
            this.startActivityForResult(var21, 32);
         } else {
            var4 = false;
         }
      }

      var7 = var4;
      return var7;
   }

   protected void onResume() {
      super.onResume();
      PreferenceScreen var1 = this.getPreferenceScreen();
      this.configureUpdateNotifications(var1);
      this.configureUserControlsSection(var1);
      this.configureAdPrefsSection(var1);
      this.configureAboutSection(var1);
   }

   class 1 implements Response.Listener<Boolean> {

      // $FF: synthetic field
      final boolean val$isChecked;
      // $FF: synthetic field
      final CheckBoxPreference val$preference;


      1(boolean var2, CheckBoxPreference var3) {
         this.val$isChecked = var2;
         this.val$preference = var3;
      }

      public void onResponse(Boolean var1) {
         PreferenceFile.SharedPreference var2 = VendingPreferences.INTEREST_BASED_ADS_ENABLED;
         Boolean var3 = Boolean.valueOf(this.val$isChecked);
         var2.put(var3);
         SettingsActivity var4 = SettingsActivity.this;
         CheckBoxPreference var5 = this.val$preference;
         boolean var6 = this.val$isChecked;
         var4.setInterestBasedAds(var5, var6);
      }
   }

   class 2 implements Response.ErrorListener {

      // $FF: synthetic field
      final boolean val$isChecked;
      // $FF: synthetic field
      final CheckBoxPreference val$preference;


      2(CheckBoxPreference var2, boolean var3) {
         this.val$preference = var2;
         this.val$isChecked = var3;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         SettingsActivity var4 = SettingsActivity.this;
         CheckBoxPreference var5 = this.val$preference;
         byte var6;
         if(!this.val$isChecked) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         var4.setInterestBasedAds(var5, (boolean)var6);
      }
   }
}
