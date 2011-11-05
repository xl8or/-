package com.android.settings.vpn;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.net.vpn.L2tpIpsecProfile;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.security.KeyStore;
import android.util.Log;
import com.android.settings.vpn.L2tpEditor;

class L2tpIpsecEditor extends L2tpEditor {

   private static final String TAG = L2tpIpsecEditor.class.getSimpleName();
   private boolean Is_CaCert;
   private boolean Is_UserCert;
   private ListPreference mCaCertificate;
   private Context mContext;
   private KeyStore mKeyStore;
   private L2tpIpsecProfile mProfile;
   private ListPreference mUserCertificate;


   public L2tpIpsecEditor(L2tpIpsecProfile var1) {
      super(var1);
      KeyStore var2 = KeyStore.getInstance();
      this.mKeyStore = var2;
      this.Is_UserCert = (boolean)0;
      this.Is_CaCert = (boolean)0;
      this.mProfile = var1;
   }

   private void NoCertificatePopup(Context var1) {
      Builder var2 = new Builder(var1);
      Builder var3 = var2.setCancelable((boolean)0);
      String var4 = var1.getString(2131231720);
      var2.setTitle(var4);
      String var6 = var1.getString(2131232084);
      var2.setMessage(var6);
      L2tpIpsecEditor.4 var8 = new L2tpIpsecEditor.4();
      var2.setNegativeButton(2131231632, var8);
      Builder var10 = var2.setCancelable((boolean)1);
      L2tpIpsecEditor.5 var11 = new L2tpIpsecEditor.5();
      var2.setOnCancelListener(var11);
      AlertDialog var13 = var2.show();
   }

   private Preference createCaCertificatePreference(Context var1) {
      String var2 = this.mProfile.getCaCertificate();
      String[] var3 = this.mKeyStore.saw("CACERT_");
      L2tpIpsecEditor.2 var4 = new L2tpIpsecEditor.2();
      ListPreference var7 = this.createListPreference(var1, 2131231941, var2, var3, var4);
      this.mCaCertificate = var7;
      ListPreference var8 = this.mCaCertificate;
      String var9 = this.mProfile.getCaCertificate();
      this.setSummary(var8, 2131231942, var9);
      return this.mCaCertificate;
   }

   private ListPreference createListPreference(Context var1, int var2, String var3, String[] var4, OnPreferenceChangeListener var5) {
      ListPreference var6 = new ListPreference(var1);
      var6.setTitle(var2);
      var6.setDialogTitle(var2);
      var6.setPersistent((boolean)1);
      var6.setEntries(var4);
      var6.setEntryValues(var4);
      var6.setValue(var3);
      var6.setOnPreferenceChangeListener(var5);
      return var6;
   }

   private Preference createNoCertificatePreference(Context var1, int var2, int var3) {
      Preference var4 = new Preference(var1);
      var4.setTitle(var2);
      L2tpIpsecEditor.3 var5 = new L2tpIpsecEditor.3();
      var4.setOnPreferenceClickListener(var5);
      String var6 = this.mProfile.getUserCertificate();
      this.setSummary(var4, var3, var6);
      return var4;
   }

   private Preference createUserCertificatePreference(Context var1) {
      String var2 = this.mProfile.getUserCertificate();
      String[] var3 = this.mKeyStore.saw("USRCERT_");
      L2tpIpsecEditor.1 var4 = new L2tpIpsecEditor.1();
      ListPreference var7 = this.createListPreference(var1, 2131231938, var2, var3, var4);
      this.mUserCertificate = var7;
      ListPreference var8 = this.mUserCertificate;
      String var9 = this.mProfile.getUserCertificate();
      this.setSummary(var8, 2131231939, var9);
      return this.mUserCertificate;
   }

   protected void loadExtraPreferencesTo(PreferenceGroup var1) {
      super.loadExtraPreferencesTo(var1);
      Context var2 = var1.getContext();
      this.mContext = var2;
      if(this.mKeyStore.saw("USRCERT_").length == 0) {
         Context var3 = this.mContext;
         Preference var4 = this.createNoCertificatePreference(var3, 2131231938, 2131231939);
         var1.addPreference(var4);
         this.Is_UserCert = (boolean)0;
      } else {
         Context var9 = this.mContext;
         Preference var10 = this.createUserCertificatePreference(var9);
         var1.addPreference(var10);
      }

      if(this.mKeyStore.saw("CACERT_").length == 0) {
         Context var6 = this.mContext;
         Preference var7 = this.createNoCertificatePreference(var6, 2131231941, 2131231942);
         var1.addPreference(var7);
         this.Is_CaCert = (boolean)0;
      } else {
         Context var12 = this.mContext;
         Preference var13 = this.createCaCertificatePreference(var12);
         var1.addPreference(var13);
      }
   }

   public String validate() {
      String var1;
      if(this.Is_UserCert && this.Is_CaCert) {
         String var2 = super.validate();
         if(var2 == null) {
            ListPreference var3 = this.mUserCertificate;
            var2 = this.validate(var3, 2131231940);
         }

         if(var2 == null) {
            ListPreference var4 = this.mCaCertificate;
            var2 = this.validate(var4, 2131231943);
         }

         var1 = var2;
      } else {
         var1 = this.mContext.getString(2131232085);
      }

      return var1;
   }

   class 5 implements OnCancelListener {

      5() {}

      public void onCancel(DialogInterface var1) {}
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(DialogInterface var1, int var2) {}
   }

   class 3 implements OnPreferenceClickListener {

      3() {}

      public boolean onPreferenceClick(Preference var1) {
         int var2 = Log.d("VPN", "onPreferenceClick : createNoUserCertificatePreference");
         L2tpIpsecEditor var3 = L2tpIpsecEditor.this;
         Context var4 = var1.getContext();
         var3.NoCertificatePopup(var4);
         return false;
      }
   }

   class 2 implements OnPreferenceChangeListener {

      2() {}

      public boolean onPreferenceChange(Preference var1, Object var2) {
         L2tpIpsecProfile var3 = L2tpIpsecEditor.this.mProfile;
         String var4 = (String)var2;
         var3.setCaCertificate(var4);
         L2tpIpsecEditor var5 = L2tpIpsecEditor.this;
         String var6 = (String)var2;
         var5.setSummary(var1, 2131231942, var6);
         boolean var7 = (boolean)(L2tpIpsecEditor.this.Is_CaCert = (boolean)1);
         return true;
      }
   }

   class 1 implements OnPreferenceChangeListener {

      1() {}

      public boolean onPreferenceChange(Preference var1, Object var2) {
         L2tpIpsecProfile var3 = L2tpIpsecEditor.this.mProfile;
         String var4 = (String)var2;
         var3.setUserCertificate(var4);
         L2tpIpsecEditor var5 = L2tpIpsecEditor.this;
         String var6 = (String)var2;
         var5.setSummary(var1, 2131231939, var6);
         boolean var7 = (boolean)(L2tpIpsecEditor.this.Is_UserCert = (boolean)1);
         return true;
      }
   }
}
