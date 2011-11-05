package com.android.settings.vpn;

import android.content.Context;
import android.net.vpn.PptpProfile;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.Preference.OnPreferenceChangeListener;
import com.android.settings.vpn.VpnProfileEditor;

class PptpEditor extends VpnProfileEditor {

   private CheckBoxPreference mEncryption;


   public PptpEditor(PptpProfile var1) {
      super(var1);
   }

   private Preference createEncryptionPreference(Context var1) {
      PptpProfile var2 = (PptpProfile)this.getProfile();
      CheckBoxPreference var3 = new CheckBoxPreference(var1);
      this.mEncryption = var3;
      boolean var4 = var2.isEncryptionEnabled();
      this.setCheckBoxTitle(var3, 2131231947);
      var3.setChecked(var4);
      this.setEncryptionSummary(var3, var4);
      PptpEditor.1 var5 = new PptpEditor.1(var2);
      var3.setOnPreferenceChangeListener(var5);
      return var3;
   }

   private void setEncryptionSummary(CheckBoxPreference var1, boolean var2) {
      Context var3 = var1.getContext();
      int var4;
      if(var2) {
         var4 = 2131231963;
      } else {
         var4 = 2131231964;
      }

      String var5 = var3.getString(var4);
      Object[] var6 = new Object[1];
      String var7 = var3.getString(2131231948);
      var6[0] = var7;
      String var8 = String.format(var5, var6);
      var1.setSummary(var8);
   }

   protected void loadExtraPreferencesTo(PreferenceGroup var1) {
      Context var2 = var1.getContext();
      Preference var3 = this.createEncryptionPreference(var2);
      var1.addPreference(var3);
      PptpProfile var5 = (PptpProfile)this.getProfile();
   }

   class 1 implements OnPreferenceChangeListener {

      // $FF: synthetic field
      final PptpProfile val$profile;


      1(PptpProfile var2) {
         this.val$profile = var2;
      }

      public boolean onPreferenceChange(Preference var1, Object var2) {
         boolean var3 = ((Boolean)var2).booleanValue();
         this.val$profile.setEncryptionEnabled(var3);
         PptpEditor var4 = PptpEditor.this;
         CheckBoxPreference var5 = PptpEditor.this.mEncryption;
         var4.setEncryptionSummary(var5, var3);
         return true;
      }
   }
}
