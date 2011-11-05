package com.google.android.finsky.billing.carrierbilling.action;

import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingParameters;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.Iterator;
import java.util.List;

public class CarrierParamsAction {

   private final VendingProtos.GetMarketMetadataResponseProto mResponse;


   public CarrierParamsAction(VendingProtos.GetMarketMetadataResponseProto var1) {
      this.mResponse = var1;
   }

   CarrierBillingParameters createCarrierBillingParameters(VendingProtos.GetMarketMetadataResponseProto var1) {
      CarrierBillingParameters var2 = null;
      VendingProtos.BillingParameterProto var3 = null;
      if(var1 != null) {
         Iterator var4 = var1.getBillingParameterList().iterator();

         while(var4.hasNext()) {
            VendingProtos.BillingParameterProto var5 = (VendingProtos.BillingParameterProto)var4.next();
            if(var5.getBillingInstrumentType() == 1) {
               var3 = var5;
               break;
            }
         }

         if(var3 != null) {
            CarrierBillingParameters var26;
            try {
               CarrierBillingParameters.Builder var6 = new CarrierBillingParameters.Builder();
               String var7 = var3.getId();
               CarrierBillingParameters.Builder var8 = var6.setId(var7);
               String var9 = var3.getName();
               CarrierBillingParameters.Builder var10 = var8.setName(var9);
               List var11 = var3.getMncMccList();
               CarrierBillingParameters.Builder var12 = var10.setMncMccs(var11);
               String var13 = var3.getBackendUrl(0);
               CarrierBillingParameters.Builder var14 = var12.setGetProvisioningUrl(var13);
               String var15 = var3.getBackendUrl(1);
               CarrierBillingParameters.Builder var16 = var14.setGetCredentialsUrl(var15);
               String var17 = var3.getIconId();
               CarrierBillingParameters.Builder var18 = var16.setCarrierIconId(var17);
               boolean var19 = var3.getInstrumentTosRequired();
               CarrierBillingParameters.Builder var20 = var18.setShowCarrierTos(var19);
               int var21 = var3.getApiVersion();
               CarrierBillingParameters.Builder var22 = var20.setCarrierApiVersion(var21);
               boolean var23 = var3.getPerTransactionCredentialsRequired();
               CarrierBillingParameters.Builder var24 = var22.setPerTransactionCredentialsRequired(var23);
               boolean var25 = var3.getSendSubscriberIdWithCarrierBillingRequests();
               var26 = var24.setSendSubscriberInfoWithCarrierRequests(var25).build();
            } catch (IllegalArgumentException var29) {
               Object[] var28 = new Object[0];
               FinskyLog.e("Missing fields for creating carrier billing parameters", var28);
               var2 = false;
               return var2;
            }

            var2 = var26;
         }
      }

      return var2;
   }

   public void run(Runnable var1) {
      VendingProtos.GetMarketMetadataResponseProto var2 = this.mResponse;
      CarrierBillingParameters var3 = this.createCarrierBillingParameters(var2);
      if(var3 != null) {
         BillingLocator.getCarrierBillingStorage().setParams(var3);
      } else {
         Object[] var4 = new Object[0];
         FinskyLog.w("Saving carrier billing params failed.", var4);
      }

      var1.run();
   }
}
