package com.google.android.finsky.activities;

import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.activities.BackgroundDataDialog;
import com.google.android.finsky.activities.GetMarketMetadataAction;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.android.vending.remoting.protos.VendingProtos;

public abstract class PhoneskyActivity extends AuthenticatedActivity implements BackgroundDataDialog.BackgroundDataSettingListener {

   public PhoneskyActivity() {}

   protected void handleAuthenticationError(Response.ErrorCode var1, String var2, NetworkError var3) {}

   protected void initialize(Runnable var1) {
      GetMarketMetadataAction var2 = new GetMarketMetadataAction();
      String var3 = FinskyApp.get().getCurrentAccountName();
      PhoneskyActivity.1 var4 = new PhoneskyActivity.1(var1);
      PhoneskyActivity.2 var5 = new PhoneskyActivity.2();
      var2.run(this, var3, var4, var5);
   }

   protected void onApisChanged() {}

   public void onBackgroundDataNotEnabled() {
      this.finish();
   }

   protected void onCleanup() {}

   protected void onResume() {
      super.onResume();
      if(!Utils.isBackgroundDataEnabled(this)) {
         this.showBackgroundDataDialog();
      } else {
         BackgroundDataDialog.dismissExisting(this.getSupportFragmentManager());
      }
   }

   protected void onTocLoaded(DfeToc var1) {}

   protected void showBackgroundDataDialog() {
      BackgroundDataDialog.show(this.getSupportFragmentManager(), this);
   }

   class 2 implements Response.ErrorListener {

      2() {}

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         StringBuilder var4 = (new StringBuilder()).append("Metadata failed ");
         String var5 = var1.name();
         String var6 = var4.append(var5).append(", ").append(var2).toString();
         Object[] var7 = new Object[0];
         FinskyLog.d(var6, var7);
         PhoneskyActivity.this.handleAuthenticationError(var1, var2, var3);
      }
   }

   class 1 implements Response.Listener<VendingProtos.GetMarketMetadataResponseProto> {

      // $FF: synthetic field
      final Runnable val$successCallback;


      1(Runnable var2) {
         this.val$successCallback = var2;
      }

      public void onResponse(VendingProtos.GetMarketMetadataResponseProto var1) {
         FinskyInstance.get().setMarketMetadata(var1);
         FinskyInstance.get().getSelfUpdateScheduler().checkForSelfUpdate(var1);
         this.val$successCallback.run();
      }
   }
}
