package com.google.android.finsky.activities;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Response;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.billing.InAppBillingSetting;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.services.ReconstructDatabaseService;
import com.google.android.finsky.utils.VendingPreferences;
import com.google.android.vending.remoting.protos.VendingProtos;

public class GetMarketMetadataAction {

   public GetMarketMetadataAction() {}

   private void showWarningIfNecessary(Context var1, VendingProtos.GetMarketMetadataResponseProto var2) {
      boolean var3 = false;
      long var4 = ((Long)VendingPreferences.LAST_METADATA_WARNING_TIMESTAMP.get()).longValue();
      long var6 = System.currentTimeMillis();
      if(var4 == 0L) {
         var3 = true;
      } else {
         long var11 = ((Long)G.vendingWarningMessageFrequencyMs.get()).longValue();
         if(var4 + var11 < var6) {
            var3 = true;
         }
      }

      if(var3) {
         PreferenceFile.SharedPreference var8 = VendingPreferences.LAST_METADATA_WARNING_TIMESTAMP;
         Long var9 = Long.valueOf(var6);
         var8.put(var9);
         String var10 = var2.getWarningMessage();
         Toast.makeText(var1, var10, 1).show();
      }
   }

   public void run(Context param1, String param2, Response.Listener<VendingProtos.GetMarketMetadataResponseProto> param3, Response.ErrorListener param4) {
      // $FF: Couldn't be decompiled
   }

   class 1 implements Response.Listener<VendingProtos.GetMarketMetadataResponseProto> {

      // $FF: synthetic field
      final String val$account;
      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final Response.Listener val$listener;


      1(String var2, Context var3, Response.Listener var4) {
         this.val$account = var2;
         this.val$context = var3;
         this.val$listener = var4;
      }

      public void onResponse(VendingProtos.GetMarketMetadataResponseProto var1) {
         String var2 = this.val$account;
         boolean var3 = var1.getInAppBillingEnabled();
         InAppBillingSetting.setEnabled(var2, var3);
         PreferenceFile.SharedPreference var4 = BillingPreferences.LOG_BILLING_EVENTS;
         Boolean var5 = Boolean.valueOf(var1.getBillingEventsEnabled());
         var4.put(var5);
         ReconstructDatabaseService.reconstructIfNecessary(this.val$context);
         if(!((Boolean)G.vendingHideWarningMessage.get()).booleanValue() && var1.hasWarningMessage()) {
            GetMarketMetadataAction var6 = GetMarketMetadataAction.this;
            Context var7 = this.val$context;
            var6.showWarningIfNecessary(var7, var1);
         }

         if(this.val$listener != null) {
            this.val$listener.onResponse(var1);
         }
      }
   }
}
