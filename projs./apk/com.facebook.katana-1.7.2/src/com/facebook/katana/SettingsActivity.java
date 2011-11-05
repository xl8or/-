package com.facebook.katana;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.facebook.katana.Constants;
import com.facebook.katana.SyncContactsChangeActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.service.method.AuthDeepLinkMethod;
import com.facebook.katana.service.method.HttpOperation;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.webview.MRoot;
import java.util.ArrayList;

public class SettingsActivity extends PreferenceActivity {

   public static final String PREF_NOTIFICATIONS = "notif";
   public static final String PREF_NOTIF_EVENT_INVITES = "notif_event_invites";
   public static final String PREF_NOTIF_FRIEND_REQUESTS = "notif_friend_requests";
   public static final String PREF_NOTIF_MESSAGES = "notif_messages";
   public static final String PREF_POLLING_INTERVAL = "polling_interval";
   public static final String PREF_RELOAD_PAGE = "reload_page";
   public static final String PREF_RESET = "reset";
   public static final String PREF_RINGTONE = "ringtone";
   public static final String PREF_SANDBOX = "sandbox";
   public static final String PREF_SSL_CHECK_CERTS = "check_certs";
   public static final String PREF_SYNC_CONTACTS = "sync_contacts";
   public static final String PREF_USE_LED = "use_led";
   public static final String PREF_VIBRATE = "vibrate";
   protected boolean mPollingSettingChanged;


   public SettingsActivity() {}

   private PreferenceScreen createPreferenceHierarchy() {
      PreferenceManager var1 = this.getPreferenceManager();
      PreferenceScreen var3 = var1.createPreferenceScreen(this);
      PreferenceCategory var4 = new PreferenceCategory(this);
      var4.setTitle(2131362195);
      boolean var9 = var3.addPreference(var4);
      ListPreference var10 = new ListPreference(this);
      ArrayList var13 = new ArrayList();
      int var15 = 2131362188;
      String var16 = this.getString(var15);
      boolean var19 = var13.add(var16);
      int var21 = 2131362189;
      String var22 = this.getString(var21);
      boolean var25 = var13.add(var22);
      int var27 = 2131362190;
      String var28 = this.getString(var27);
      boolean var31 = var13.add(var28);
      int var33 = 2131362191;
      String var34 = this.getString(var33);
      boolean var37 = var13.add(var34);
      int var39 = 2131362192;
      String var40 = this.getString(var39);
      boolean var43 = var13.add(var40);
      ArrayList var44 = new ArrayList();
      String var46 = "30";
      var44.add(var46);
      String var49 = "60";
      var44.add(var49);
      String var52 = "120";
      var44.add(var52);
      String var55 = "240";
      var44.add(var55);
      String var58 = "0";
      var44.add(var58);
      if(FacebookAffiliation.hasEmployeeEverLoggedInOnThisPhone()) {
         String var61 = "Every minute (beta-only option)";
         var13.add(var61);
         String var64 = "1";
         var44.add(var64);
      }

      CharSequence[] var66 = new CharSequence[var13.size()];
      CharSequence[] var69 = (CharSequence[])var13.toArray(var66);
      CharSequence[] var70 = new CharSequence[var44.size()];
      CharSequence[] var73 = (CharSequence[])var44.toArray(var70);
      var10.setEntries(var69);
      var10.setEntryValues(var73);
      CharSequence var78 = var73[1];
      var10.setDefaultValue(var78);
      var10.setDialogTitle(2131362203);
      var10.setKey("polling_interval");
      var10.setTitle(2131362203);
      SharedPreferences var79 = PreferenceManager.getDefaultSharedPreferences(this);
      String var80 = var73[1].toString();
      String var81 = var79.getString("polling_interval", var80);
      String var86 = this.valueToString(var73, var69, var81);
      var10.setSummary(var86);
      var4.addPreference(var10);
      SettingsActivity.1 var88 = new SettingsActivity.1(var73, var69);
      var10.setOnPreferenceChangeListener(var88);
      CheckBoxPreference var93 = new CheckBoxPreference(this);
      String var97 = "notif";
      var93.setKey(var97);
      int var99 = 2131362200;
      var93.setTitle(var99);
      int var100;
      if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notif", (boolean)1)) {
         var100 = 2131362198;
      } else {
         var100 = 2131362199;
      }

      var93.setSummary(var100);
      Boolean var103 = Boolean.valueOf((boolean)1);
      var93.setDefaultValue(var103);
      boolean var108 = var4.addPreference(var93);
      PreferenceCategory var109 = new PreferenceCategory(this);
      int var113 = 2131362197;
      var109.setTitle(var113);
      boolean var116 = var3.addPreference(var109);
      CheckBoxPreference var117 = new CheckBoxPreference(this);
      var117.setKey("notif_messages");
      var117.setTitle(2131362196);
      Boolean var120 = Boolean.valueOf((boolean)1);
      var117.setDefaultValue(var120);
      boolean var123 = var109.addPreference(var117);
      CheckBoxPreference var124 = new CheckBoxPreference(this);
      var124.setKey("notif_friend_requests");
      var124.setTitle(2131362194);
      Boolean var127 = Boolean.valueOf((boolean)1);
      var124.setDefaultValue(var127);
      boolean var130 = var109.addPreference(var124);
      CheckBoxPreference var131 = new CheckBoxPreference(this);
      var131.setKey("notif_event_invites");
      var131.setTitle(2131362193);
      Boolean var134 = Boolean.valueOf((boolean)1);
      var131.setDefaultValue(var134);
      boolean var137 = var109.addPreference(var131);
      CheckBoxPreference var138 = new CheckBoxPreference(this);
      var138.setKey("vibrate");
      var138.setTitle(2131362213);
      var138.setSummary(2131362212);
      boolean var143 = var109.addPreference(var138);
      CheckBoxPreference var144 = new CheckBoxPreference(this);
      var144.setKey("use_led");
      var144.setTitle(2131362211);
      var144.setSummary(2131362210);
      boolean var149 = var109.addPreference(var144);
      RingtonePreference var150 = new RingtonePreference(this);
      var150.setRingtoneType(2);
      var150.setKey("ringtone");
      var150.setShowSilent((boolean)1);
      var150.setShowDefault((boolean)1);
      var150.setTitle(2131362207);
      var150.setSummary(2131362206);
      boolean var155 = var109.addPreference(var150);
      SettingsActivity.2 var157 = new SettingsActivity.2(var10, var138, var144, var150, var117, var124, var131);
      var93.setOnPreferenceChangeListener(var157);
      if(!var93.isChecked()) {
         var10.setEnabled((boolean)0);
         var138.setEnabled((boolean)0);
         var144.setEnabled((boolean)0);
         var150.setEnabled((boolean)0);
         var117.setEnabled((boolean)0);
         var124.setEnabled((boolean)0);
         var131.setEnabled((boolean)0);
      }

      PreferenceCategory var160 = new PreferenceCategory(this);
      int var164 = 2131362201;
      var160.setTitle(var164);
      boolean var167 = var3.addPreference(var160);
      if(PlatformUtils.platformStorageSupported(this)) {
         byte var169 = 0;
         AppSession var170 = AppSession.getActiveSession(this, (boolean)var169);
         if(var170 != null) {
            PreferenceManager var171 = this.getPreferenceManager();
            PreferenceScreen var173 = var171.createPreferenceScreen(this);
            String var175 = "sync_contacts";
            var173.setKey(var175);
            int var177 = 2131362208;
            var173.setTitle(var177);
            this.updateSyncContactsSummary(var170, var173);
            Intent var181 = new Intent;
            Class var184 = SyncContactsChangeActivity.class;
            var181.<init>(this, var184);
            var173.setIntent(var181);
            boolean var189 = var160.addPreference(var173);
         }
      }

      PreferenceManager var190 = this.getPreferenceManager();
      PreferenceScreen var192 = var190.createPreferenceScreen(this);
      int var194 = 2131362187;
      var192.setTitle(var194);
      Intent var195 = new Intent;
      String var196 = Constants.URL.getPrivacySettingsUrl(this);
      Uri var199 = Uri.parse(AuthDeepLinkMethod.getDeepLinkUrl(this, var196));
      String var201 = "android.intent.action.VIEW";
      var195.<init>(var201, var199);
      var192.setIntent(var195);
      var160.addPreference(var192);
      if(FacebookAffiliation.hasEmployeeEverLoggedInOnThisPhone()) {
         EditTextPreference var206 = new EditTextPreference(this);
         String var210 = "sandbox";
         var206.setKey(var210);
         String var212 = "facebook.com";
         var206.setDefaultValue(var212);
         String var214 = "Sandbox";
         var206.setTitle(var214);
         String var216 = "e.g., dev.facebook.com, facebook.com";
         var206.setSummary(var216);
         String var218 = "Sandbox";
         var206.setDialogTitle(var218);
         SettingsActivity.3 var219 = new SettingsActivity.3();
         var206.setOnPreferenceChangeListener(var219);
         boolean var226 = var160.addPreference(var206);
         CheckBoxPreference var227 = new CheckBoxPreference(this);
         var227.setKey("check_certs");
         var227.setTitle("Check SSL Certificates");
         var227.setSummary("Should be off when using your sandbox.");
         SettingsActivity.4 var230 = new SettingsActivity.4();
         var227.setOnPreferenceChangeListener(var230);
         Boolean var233 = Boolean.valueOf((boolean)1);
         var227.setDefaultValue(var233);
         boolean var236 = var160.addPreference(var227);
         ListPreference var237 = new ListPreference(this);
         CharSequence[] var240 = new CharSequence[]{"Clear cookies", "Clear cache"};
         var237.setEntries(var240);
         CharSequence[] var243 = new CharSequence[]{"cookies", "cache"};
         var237.setEntryValues(var243);
         String var247 = "Reset webviews";
         var237.setDialogTitle(var247);
         String var249 = "Webview control";
         var237.setTitle(var249);
         String var251 = "Clears webview cookies or caches";
         var237.setSummary(var251);
         String var253 = "Clear";
         var237.setPositiveButtonText(var253);
         String var255 = "Cancel";
         var237.setNegativeButtonText(var255);
         String var257 = "reset";
         var237.setKey(var257);
         SettingsActivity.5 var258 = new SettingsActivity.5();
         var237.setOnPreferenceChangeListener(var258);
         boolean var265 = var160.addPreference(var237);
      }

      return var3;
   }

   private void updateSyncContactsSummary(AppSession var1, Preference var2) {
      if(var1 != null) {
         String var3 = var1.getSessionInfo().username;
         boolean var4 = FacebookAuthenticationService.isSyncEnabled(this, var3);
         boolean var5 = FacebookAuthenticationService.doesShowUngroupedContacts(this, var3);
         if(var4) {
            if(var5) {
               var2.setSummary(2131362275);
            } else {
               var2.setSummary(2131362277);
            }
         } else {
            var2.setSummary(2131362271);
         }
      }
   }

   private String valueToString(CharSequence[] var1, CharSequence[] var2, String var3) {
      int var4 = 0;

      String var6;
      while(true) {
         int var5 = var1.length;
         if(var4 >= var5) {
            var6 = null;
            break;
         }

         if(var1[var4].toString().equals(var3)) {
            var6 = var2[var4].toString();
            break;
         }

         ++var4;
      }

      return var6;
   }

   public void onBackPressed() {
      if(this.mPollingSettingChanged) {
         this.setResult(-1);
      }

      this.finish();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      PreferenceScreen var2 = this.createPreferenceHierarchy();
      this.setPreferenceScreen(var2);
      this.mPollingSettingChanged = (boolean)0;
   }

   public void onResume() {
      super.onResume();
      Preference var1 = this.findPreference("sync_contacts");
      if(var1 != null) {
         AppSession var2 = AppSession.getActiveSession(this, (boolean)0);
         this.updateSyncContactsSummary(var2, var1);
      }
   }

   class 1 implements OnPreferenceChangeListener {

      // $FF: synthetic field
      final CharSequence[] val$timeouts;
      // $FF: synthetic field
      final CharSequence[] val$values;


      1(CharSequence[] var2, CharSequence[] var3) {
         this.val$values = var2;
         this.val$timeouts = var3;
      }

      public boolean onPreferenceChange(Preference var1, Object var2) {
         ListPreference var3 = (ListPreference)var1;
         SettingsActivity var4 = SettingsActivity.this;
         CharSequence[] var5 = this.val$values;
         CharSequence[] var6 = this.val$timeouts;
         String var7 = (String)var2;
         String var8 = var4.valueToString(var5, var6, var7);
         var3.setSummary(var8);
         SettingsActivity.this.mPollingSettingChanged = (boolean)1;
         return true;
      }
   }

   class 2 implements OnPreferenceChangeListener {

      // $FF: synthetic field
      final ListPreference val$checkIntervalPref;
      // $FF: synthetic field
      final CheckBoxPreference val$eventInvites;
      // $FF: synthetic field
      final CheckBoxPreference val$friendRequests;
      // $FF: synthetic field
      final CheckBoxPreference val$messages;
      // $FF: synthetic field
      final RingtonePreference val$ringtonePref;
      // $FF: synthetic field
      final CheckBoxPreference val$useLedPref;
      // $FF: synthetic field
      final CheckBoxPreference val$vibratePref;


      2(ListPreference var2, CheckBoxPreference var3, CheckBoxPreference var4, RingtonePreference var5, CheckBoxPreference var6, CheckBoxPreference var7, CheckBoxPreference var8) {
         this.val$checkIntervalPref = var2;
         this.val$vibratePref = var3;
         this.val$useLedPref = var4;
         this.val$ringtonePref = var5;
         this.val$messages = var6;
         this.val$friendRequests = var7;
         this.val$eventInvites = var8;
      }

      public boolean onPreferenceChange(Preference var1, Object var2) {
         boolean var3 = ((Boolean)var2).booleanValue();
         int var4;
         if(var3) {
            var4 = 2131362198;
         } else {
            var4 = 2131362199;
         }

         var1.setSummary(var4);
         this.val$checkIntervalPref.setEnabled(var3);
         this.val$vibratePref.setEnabled(var3);
         this.val$useLedPref.setEnabled(var3);
         this.val$ringtonePref.setEnabled(var3);
         this.val$messages.setEnabled(var3);
         this.val$friendRequests.setEnabled(var3);
         this.val$eventInvites.setEnabled(var3);
         return true;
      }
   }

   class 3 implements OnPreferenceChangeListener {

      3() {}

      public boolean onPreferenceChange(Preference var1, Object var2) {
         boolean var6;
         if(var2 instanceof String && var1 instanceof EditTextPreference) {
            String var3 = (String)var2;
            EditTextPreference var4 = (EditTextPreference)var1;
            String var5 = var3.trim();
            if(!var5.equals(var3)) {
               var4.setText(var5);
               var6 = false;
               return var6;
            }
         }

         var6 = true;
         return var6;
      }
   }

   class 5 implements OnPreferenceChangeListener {

      5() {}

      public boolean onPreferenceChange(Preference var1, Object var2) {
         if(var2.equals("cookies")) {
            CookieSyncManager var3 = CookieSyncManager.createInstance(SettingsActivity.this);
            CookieManager.getInstance().removeAllCookie();
         } else if(var2.equals("cache")) {
            SettingsActivity var4 = SettingsActivity.this;
            (new WebView(var4)).clearCache((boolean)1);
            MRoot.reset(SettingsActivity.this);
         }

         return false;
      }
   }

   class 4 implements OnPreferenceChangeListener {

      4() {}

      public boolean onPreferenceChange(Preference var1, Object var2) {
         HttpOperation.initSchemeRegistry(((Boolean)var2).booleanValue());
         return true;
      }
   }
}
