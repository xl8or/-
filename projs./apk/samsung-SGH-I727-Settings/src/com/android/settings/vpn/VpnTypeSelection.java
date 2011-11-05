package com.android.settings.vpn;

import android.content.Intent;
import android.net.vpn.VpnManager;
import android.net.vpn.VpnType;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import com.android.settings.vpn.VpnSettings;
import java.util.HashMap;
import java.util.Map;

public class VpnTypeSelection extends PreferenceActivity {

   private Map<String, VpnType> mTypeMap;


   public VpnTypeSelection() {
      HashMap var1 = new HashMap();
      this.mTypeMap = var1;
   }

   private void initTypeList() {
      PreferenceScreen var1 = this.getPreferenceScreen();
      VpnType[] var2 = VpnManager.getSupportedVpnTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         VpnType var5 = var2[var4];
         String var6 = var5.getDisplayName();
         String var7 = this.getString(2131231927);
         Object[] var8 = new Object[]{var6};
         String var9 = String.format(var7, var8);
         this.mTypeMap.put(var9, var5);
         Preference var11 = new Preference(this);
         var11.setTitle(var9);
         int var12 = var5.getDescriptionId();
         var11.setSummary(var12);
         var1.addPreference(var11);
      }

   }

   private void setResult(VpnType var1) {
      Intent var2 = new Intent(this, VpnSettings.class);
      String var3 = var1.toString();
      var2.putExtra("vpn_type", var3);
      this.setResult(-1, var2);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.addPreferencesFromResource(2130968625);
      this.initTypeList();
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      Map var3 = this.mTypeMap;
      String var4 = var2.getTitle().toString();
      VpnType var5 = (VpnType)var3.get(var4);
      this.setResult(var5);
      this.finish();
      return true;
   }
}
