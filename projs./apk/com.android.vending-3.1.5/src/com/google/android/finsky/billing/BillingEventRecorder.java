package com.google.android.finsky.billing;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.protos.VendingProtos;

public class BillingEventRecorder {

   private static final Response.ErrorListener LOGGING_ERROR_LISTENER = new BillingEventRecorder.2();
   private static final Response.Listener<VendingProtos.BillingEventResponseProto> NOP_LISTENER = new BillingEventRecorder.1();


   private BillingEventRecorder() {}

   public static void recordError(String var0, int var1, Response.ErrorCode var2, String var3) {
      if(((Boolean)BillingPreferences.LOG_BILLING_EVENTS.get()).booleanValue()) {
         VendingProtos.BillingEventRequestProto var4 = (new VendingProtos.BillingEventRequestProto()).setBillingParametersId(var0).setEventType(var1).setResultSuccess((boolean)0);
         String var5 = var2.name();
         VendingProtos.BillingEventRequestProto var6 = var4.setClientMessage(var5);
         VendingApi var7 = FinskyApp.get().getVendingApi();
         Response.Listener var8 = NOP_LISTENER;
         Response.ErrorListener var9 = LOGGING_ERROR_LISTENER;
         var7.recordBillingEvent(var6, var8, var9);
      }
   }

   public static void recordSuccess(String var0, int var1, boolean var2) {
      if(((Boolean)BillingPreferences.LOG_BILLING_EVENTS.get()).booleanValue()) {
         VendingProtos.BillingEventRequestProto var3 = (new VendingProtos.BillingEventRequestProto()).setBillingParametersId(var0).setEventType(var1).setResultSuccess(var2);
         VendingApi var4 = FinskyApp.get().getVendingApi();
         Response.Listener var5 = NOP_LISTENER;
         Response.ErrorListener var6 = LOGGING_ERROR_LISTENER;
         var4.recordBillingEvent(var3, var5, var6);
      }
   }

   static class 1 implements Response.Listener<VendingProtos.BillingEventResponseProto> {

      1() {}

      public void onResponse(VendingProtos.BillingEventResponseProto var1) {}
   }

   static class 2 implements Response.ErrorListener {

      2() {}

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Object[] var4 = new Object[2];
         String var5 = var1.name();
         var4[0] = var5;
         var4[1] = var2;
         FinskyLog.w("BillingEventRecorder got error %s: %s", var4);
      }
   }
}
