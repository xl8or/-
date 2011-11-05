package com.android.settings.vpn;

import android.content.Context;
import android.net.vpn.L2tpIpsecPskProfile;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import com.android.settings.vpn.L2tpEditor;
import com.android.settings.vpn.VpnProfileEditor;

class L2tpIpsecPskEditor extends L2tpEditor {

   private EditTextPreference mPresharedKey;
   private VpnProfileEditor.SecretHandler mPskHandler;


   public L2tpIpsecPskEditor(L2tpIpsecPskProfile var1) {
      super(var1);
   }

   private Preference createPresharedKeyPreference(Context var1) {
      L2tpIpsecPskEditor.1 var2 = new L2tpIpsecPskEditor.1(var1, 2131231949, 2131231950);
      this.mPskHandler = var2;
      return var2.getPreference();
   }

   protected void loadExtraPreferencesTo(PreferenceGroup var1) {
      Context var2 = var1.getContext();
      Preference var3 = this.createPresharedKeyPreference(var2);
      var1.addPreference(var3);
      super.loadExtraPreferencesTo(var1);
   }

   public String validate() {
      String var1 = super.validate();
      String var2;
      if(var1 != null) {
         var2 = var1;
      } else {
         var2 = this.mPskHandler.validate();
      }

      return var2;
   }

   class 1 extends VpnProfileEditor.SecretHandler {

      1(Context var2, int var3, int var4) {
         super(var2, var3, var4);
      }

      protected String getSecretFromProfile() {
         return ((L2tpIpsecPskProfile)L2tpIpsecPskEditor.this.getProfile()).getPresharedKey();
      }

      protected void saveSecretToProfile(String var1) {
         ((L2tpIpsecPskProfile)L2tpIpsecPskEditor.this.getProfile()).setPresharedKey(var1);
      }
   }
}
