package com.android.settings.vpn;

import android.content.Context;
import android.net.vpn.L2tpProfile;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.Preference.OnPreferenceChangeListener;
import com.android.settings.vpn.VpnProfileEditor;

class L2tpEditor extends VpnProfileEditor {

   private CheckBoxPreference mSecret;
   private VpnProfileEditor.SecretHandler mSecretHandler;


   public L2tpEditor(L2tpProfile var1) {
      super(var1);
   }

   private Preference createSecretPreference(Context var1) {
      L2tpProfile var2 = (L2tpProfile)this.getProfile();
      CheckBoxPreference var3 = new CheckBoxPreference(var1);
      this.mSecret = var3;
      boolean var4 = var2.isSecretEnabled();
      this.setCheckBoxTitle(var3, 2131231945);
      var3.setChecked(var4);
      this.setSecretSummary(var3, var4);
      L2tpEditor.1 var5 = new L2tpEditor.1(var2);
      var3.setOnPreferenceChangeListener(var5);
      return var3;
   }

   private Preference createSecretStringPreference(Context var1) {
      L2tpEditor.2 var2 = new L2tpEditor.2(var1, 2131231944, 2131231945);
      this.mSecretHandler = var2;
      EditTextPreference var3 = var2.getPreference();
      boolean var4 = this.mSecret.isChecked();
      var3.setEnabled(var4);
      return var3;
   }

   private void setSecretSummary(CheckBoxPreference var1, boolean var2) {
      Context var3 = var1.getContext();
      int var4;
      if(var2) {
         var4 = 2131231963;
      } else {
         var4 = 2131231964;
      }

      String var5 = var3.getString(var4);
      Object[] var6 = new Object[1];
      String var7 = var3.getString(2131231945);
      var6[0] = var7;
      String var8 = String.format(var5, var6);
      var1.setSummary(var8);
   }

   protected void loadExtraPreferencesTo(PreferenceGroup var1) {
      Context var2 = var1.getContext();
      Preference var3 = this.createSecretPreference(var2);
      var1.addPreference(var3);
      Preference var5 = this.createSecretStringPreference(var2);
      var1.addPreference(var5);
      L2tpProfile var7 = (L2tpProfile)this.getProfile();
   }

   public String validate() {
      String var1 = super.validate();
      String var2;
      if(!this.mSecret.isChecked()) {
         var2 = var1;
      } else if(var1 != null) {
         var2 = var1;
      } else {
         var2 = this.mSecretHandler.validate();
      }

      return var2;
   }

   class 1 implements OnPreferenceChangeListener {

      // $FF: synthetic field
      final L2tpProfile val$profile;


      1(L2tpProfile var2) {
         this.val$profile = var2;
      }

      public boolean onPreferenceChange(Preference var1, Object var2) {
         boolean var3 = ((Boolean)var2).booleanValue();
         this.val$profile.setSecretEnabled(var3);
         L2tpEditor.this.mSecretHandler.getPreference().setEnabled(var3);
         L2tpEditor var4 = L2tpEditor.this;
         CheckBoxPreference var5 = L2tpEditor.this.mSecret;
         var4.setSecretSummary(var5, var3);
         return true;
      }
   }

   class 2 extends VpnProfileEditor.SecretHandler {

      2(Context var2, int var3, int var4) {
         super(var2, var3, var4);
      }

      protected String getSecretFromProfile() {
         return ((L2tpProfile)L2tpEditor.this.getProfile()).getSecretString();
      }

      protected void saveSecretToProfile(String var1) {
         ((L2tpProfile)L2tpEditor.this.getProfile()).setSecretString(var1);
      }
   }
}
