package com.android.settings.wifi;

import android.content.Context;
import android.preference.Preference;
import android.view.View;
import org.json.JSONException;
import org.json.JSONObject;

public class IwlanNetwork extends Preference {

   private static final String JSON_KEY_IWLAN_PDG_ADDRESS = "pdg_ipaddr";
   private static final String JSON_KEY_IWLAN_PDG_NAME = "pdg_name";
   private String defaultPdgDomainName;
   private boolean isConnected;
   private String pdgAddress;
   private String pdgDomainName;


   public IwlanNetwork(Context var1, JSONObject var2, boolean var3, String var4) {
      super(var1);

      try {
         this.isConnected = var3;
         if(var2.has("pdg_name")) {
            String var5 = var2.getString("pdg_name");
            this.pdgDomainName = var5;
         }

         if(var2.has("pdg_ipaddr")) {
            String var6 = var2.getString("pdg_ipaddr");
            this.pdgAddress = var6;
         }
      } catch (JSONException var7) {
         var7.printStackTrace();
      }

      this.defaultPdgDomainName = var4;
   }

   public String getDefaultPdgDomainName() {
      return this.defaultPdgDomainName;
   }

   public String getPdgDomainName() {
      return this.pdgDomainName;
   }

   public String getPdgIpAddress() {
      return this.pdgAddress;
   }

   public boolean isConnected() {
      return this.isConnected;
   }

   protected void onBindView(View var1) {
      String var2 = this.getPdgDomainName();
      String var3 = this.getDefaultPdgDomainName();
      if(var2.equals(var3)) {
         String var4 = this.getContext().getString(2131231165);
         this.setTitle(var4);
         String var5 = this.getContext().getString(2131231168);
         this.setSummary(var5);
      } else {
         String var6 = this.getPdgDomainName();
         this.setTitle(var6);
         String var7 = this.pdgAddress;
         this.setSummary(var7);
      }

      super.onBindView(var1);
   }
}
