package com.android.settings.vpn;

import android.content.Context;
import android.net.vpn.VpnProfile;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.Preference.OnPreferenceChangeListener;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.InputFilter.LengthFilter;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

class VpnProfileEditor {

   private static final String KEY_VPN_NAME = "vpn_name";
   private EditTextPreference mDomainSuffices;
   private EditTextPreference mName;
   private VpnProfile mProfile;
   private EditTextPreference mServerName;


   public VpnProfileEditor(VpnProfile var1) {
      this.mProfile = var1;
   }

   private Preference createServerNamePreference(Context var1) {
      String var2 = this.mProfile.getServerName();
      VpnProfileEditor.3 var3 = new VpnProfileEditor.3();
      EditTextPreference var6 = this.createEditTextPreference(var1, 2131231952, 2131231953, var2, var3);
      this.mServerName = var6;
      var6.getEditText().setInputType(16);
      return var6;
   }

   private void setName(String var1) {
      String var2;
      if(var1 == null) {
         var2 = "";
      } else {
         var2 = var1.trim();
      }

      this.mName.setText(var2);
      this.getProfile().setName(var2);
      EditTextPreference var3 = this.mName;
      this.setSummary(var3, 2131231934, var2);
   }

   protected EditTextPreference createDomainSufficesPreference(Context var1) {
      String var2 = this.mProfile.getDomainSuffices();
      VpnProfileEditor.2 var3 = new VpnProfileEditor.2();
      EditTextPreference var6 = this.createEditTextPreference(var1, 2131231956, 2131231957, var2, var3);
      this.mDomainSuffices = var6;
      var6.getEditText().setInputType(16);
      return var6;
   }

   protected EditTextPreference createEditTextPreference(Context var1, int var2, int var3, String var4, OnPreferenceChangeListener var5) {
      EditTextPreference var6 = new EditTextPreference(var1);
      var6.setTitle(var2);
      var6.setDialogTitle(var2);
      this.setSummary(var6, var3, var4);
      var6.setText(var4);
      var6.setPersistent((boolean)1);
      var6.setOnPreferenceChangeListener(var5);
      return var6;
   }

   public VpnProfile getProfile() {
      return this.mProfile;
   }

   protected void loadExtraPreferencesTo(PreferenceGroup var1) {}

   public void loadPreferencesTo(PreferenceGroup var1) {
      Context var2 = var1.getContext();
      EditTextPreference var3 = (EditTextPreference)var1.findPreference("vpn_name");
      this.mName = var3;
      EditTextPreference var4 = this.mName;
      VpnProfileEditor.1 var5 = new VpnProfileEditor.1();
      var4.setOnPreferenceChangeListener(var5);
      String var6 = this.getProfile().getName();
      this.setName(var6);
      this.mName.getEditText().setInputType(16385);
      EditText var7 = this.mName.getEditText();
      InputFilter[] var8 = new InputFilter[1];
      LengthFilter var9 = new LengthFilter(64);
      var8[0] = var9;
      var7.setFilters(var8);
      Preference var10 = this.createServerNamePreference(var2);
      var1.addPreference(var10);
      this.loadExtraPreferencesTo(var1);
      EditTextPreference var12 = this.createDomainSufficesPreference(var2);
      var1.addPreference(var12);
   }

   protected void setCheckBoxTitle(CheckBoxPreference var1, int var2) {
      Context var3 = var1.getContext();
      String var4 = var3.getString(2131231961);
      Object[] var5 = new Object[1];
      String var6 = var3.getString(var2);
      var5[0] = var6;
      String var7 = String.format(var4, var5);
      var1.setTitle(var7);
   }

   protected void setSummary(Preference var1, int var2, String var3) {
      this.setSummary(var1, var2, var3, (boolean)1);
   }

   protected void setSummary(Preference var1, int var2, String var3, boolean var4) {
      Context var5 = var1.getContext();
      String var6;
      if(var4) {
         var6 = var5.getString(2131231959);
      } else {
         var6 = var5.getString(2131231960);
      }

      String var9;
      if(TextUtils.isEmpty(var3)) {
         Object[] var7 = new Object[1];
         String var8 = var5.getString(var2);
         var7[0] = var8;
         var9 = String.format(var6, var7);
      } else {
         var9 = var3;
      }

      var1.setSummary(var9);
   }

   public String validate() {
      EditTextPreference var1 = this.mName;
      String var2 = this.validate(var1, 2131231935);
      String var3;
      if(var2 != null) {
         var3 = var2;
      } else {
         EditTextPreference var4 = this.mServerName;
         var3 = this.validate(var4, 2131231954);
      }

      return var3;
   }

   protected String validate(Preference var1, int var2) {
      Context var3 = var1.getContext();
      String var4;
      if(var1 instanceof EditTextPreference) {
         var4 = ((EditTextPreference)var1).getText();
      } else {
         var4 = ((ListPreference)var1).getValue();
      }

      String var5;
      if(var1 instanceof EditTextPreference) {
         var5 = var3.getString(2131231910);
      } else {
         var5 = var3.getString(2131231911);
      }

      String var8;
      if(TextUtils.isEmpty(var4)) {
         Object[] var6 = new Object[1];
         String var7 = var3.getString(var2);
         var6[0] = var7;
         var8 = String.format(var5, var6);
      } else {
         var8 = null;
      }

      return var8;
   }

   class 1 implements OnPreferenceChangeListener {

      1() {}

      public boolean onPreferenceChange(Preference var1, Object var2) {
         VpnProfileEditor var3 = VpnProfileEditor.this;
         String var4 = (String)var2;
         var3.setName(var4);
         return true;
      }
   }

   protected abstract static class SecretHandler {

      private int mFieldNameId;
      private boolean mHadSecret;
      private EditTextPreference mPref;


      protected SecretHandler(Context var1, int var2, int var3) {
         String var4 = this.getSecretFromProfile();
         byte var5;
         if(!TextUtils.isEmpty(var4)) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         this.mHadSecret = (boolean)var5;
         this.mFieldNameId = var3;
         EditTextPreference var6 = new EditTextPreference(var1);
         this.mPref = var6;
         var6.setTitle(var2);
         var6.setDialogTitle(var2);
         var6.getEditText().setInputType(128);
         EditText var7 = var6.getEditText();
         PasswordTransformationMethod var8 = new PasswordTransformationMethod();
         var7.setTransformationMethod(var8);
         var6.setText("");
         EditText var9 = var6.getEditText();
         int var10;
         if(this.mHadSecret) {
            var10 = 2131231967;
         } else {
            var10 = 2131231968;
         }

         var9.setHint(var10);
         this.setSecretSummary(var4);
         var6.setPersistent((boolean)1);
         this.saveSecretToProfile("");
         VpnProfileEditor.SecretHandler.1 var11 = new VpnProfileEditor.SecretHandler.1();
         var6.setOnPreferenceChangeListener(var11);
      }

      private void setSecretSummary(String var1) {
         EditTextPreference var2 = this.mPref;
         Context var3 = var2.getContext();
         String var4;
         if(TextUtils.isEmpty(var1) && !this.mHadSecret) {
            var4 = var3.getString(2131231959);
         } else {
            var4 = var3.getString(2131231958);
         }

         Object[] var5 = new Object[1];
         int var6 = this.mFieldNameId;
         String var7 = var3.getString(var6);
         var5[0] = var7;
         String var8 = String.format(var4, var5);
         var2.setSummary(var8);
      }

      protected EditTextPreference getPreference() {
         return this.mPref;
      }

      protected abstract String getSecretFromProfile();

      protected abstract void saveSecretToProfile(String var1);

      protected String validate() {
         Context var1 = this.mPref.getContext();
         String var6;
         if(TextUtils.isEmpty(this.mPref.getText()) && !this.mHadSecret) {
            String var2 = var1.getString(2131231910);
            Object[] var3 = new Object[1];
            int var4 = this.mFieldNameId;
            String var5 = var1.getString(var4);
            var3[0] = var5;
            var6 = String.format(var2, var3);
         } else {
            var6 = null;
         }

         return var6;
      }

      class 1 implements OnPreferenceChangeListener {

         1() {}

         public boolean onPreferenceChange(Preference var1, Object var2) {
            VpnProfileEditor.SecretHandler var3 = SecretHandler.this;
            String var4 = (String)var2;
            var3.saveSecretToProfile(var4);
            VpnProfileEditor.SecretHandler var5 = SecretHandler.this;
            String var6 = (String)var2;
            var5.setSecretSummary(var6);
            return true;
         }
      }
   }

   class 2 implements OnPreferenceChangeListener {

      2() {}

      public boolean onPreferenceChange(Preference var1, Object var2) {
         String var3 = ((String)var2).trim();
         VpnProfileEditor.this.mProfile.setDomainSuffices(var3);
         VpnProfileEditor.this.setSummary(var1, 2131231957, var3, (boolean)0);
         return true;
      }
   }

   class 3 implements OnPreferenceChangeListener {

      3() {}

      public boolean onPreferenceChange(Preference var1, Object var2) {
         String var3 = ((String)var2).trim();
         VpnProfileEditor.this.mProfile.setServerName(var3);
         VpnProfileEditor.this.setSummary(var1, 2131231953, var3);
         return true;
      }
   }
}
