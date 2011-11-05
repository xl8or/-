package com.google.android.finsky.billing.carrierbilling.debug;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.Toast;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.GetMarketMetadataAction;
import com.google.android.finsky.activities.PhoneskyActivity;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.carrierbilling.CarrierBillingUtils;
import com.google.android.finsky.billing.carrierbilling.action.CarrierCredentialsAction;
import com.google.android.finsky.billing.carrierbilling.action.CarrierParamsAction;
import com.google.android.finsky.billing.carrierbilling.action.CarrierProvisioningAction;
import com.google.android.finsky.billing.carrierbilling.debug.DcbDebugDetailsFragment;
import com.google.android.finsky.billing.carrierbilling.debug.DcbDetail;
import com.google.android.finsky.billing.carrierbilling.debug.DcbDetailExtractor;
import com.google.android.finsky.billing.carrierbilling.debug.GServicesDetail;
import com.google.android.finsky.billing.carrierbilling.debug.ReflectionDcbDetailExtractor;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingCredentials;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingParameters;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingStorage;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.GservicesValue;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Utils;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.Collection;
import java.util.Collections;

public class DcbDebugActivity extends PhoneskyActivity {

   private static final int CONTEXT_MENU_CLEAR_ITEM = 3;
   private static final int CONTEXT_MENU_ITEM_MASK = 15;
   private static final int CONTEXT_MENU_REFRESH_ITEM = 2;
   private static final int CONTEXT_MENU_VIEW_DETAILS = 1;
   private static final int DCB_STATUS_FIELD = 16;
   private static final int GET_CRED_STATUS_FIELD = 48;
   private static final int GET_PROV_STATUS_FIELD = 32;
   private static final Collection<DcbDetail> GSERVICES_DETAILS;
   private static final int STATUS_FIELD_MASK = 240;
   private RatingBar mCredStatus;
   private RatingBar mDcbParamStatus;
   private CarrierBillingStorage mDcbStorage;
   private RatingBar mProvStatus;
   private final Runnable updateStatusRunnable;


   static {
      DcbDetail[] var0 = new DcbDetail[6];
      GservicesValue var1 = G.vendingDcbConnectionType;
      GServicesDetail var2 = new GServicesDetail(var1);
      var0[0] = var2;
      GservicesValue var3 = G.vendingDcbProxyHost;
      GServicesDetail var4 = new GServicesDetail(var3);
      var0[1] = var4;
      GservicesValue var5 = G.vendingDcbProxyPort;
      GServicesDetail var6 = new GServicesDetail(var5);
      var0[2] = var6;
      GservicesValue var7 = G.vendingCarrierCredentialsBufferMs;
      GServicesDetail var8 = new GServicesDetail(var7);
      var0[3] = var8;
      GservicesValue var9 = G.vendingCarrierProvisioningRefreshFrequencyMs;
      GServicesDetail var10 = new GServicesDetail(var9);
      var0[4] = var10;
      GservicesValue var11 = G.vendingCarrierProvisioningRetryMs;
      GServicesDetail var12 = new GServicesDetail(var11);
      var0[5] = var12;
      GSERVICES_DETAILS = Collections.unmodifiableCollection(Lists.newArrayList(var0));
   }

   public DcbDebugActivity() {
      DcbDebugActivity.1 var1 = new DcbDebugActivity.1();
      this.updateStatusRunnable = var1;
   }

   private void displayCredentials() {
      FragmentTransaction var1 = this.getSupportFragmentManager().beginTransaction();
      CarrierBillingCredentials var2 = this.mDcbStorage.getCredentials();
      ReflectionDcbDetailExtractor var3 = new ReflectionDcbDetailExtractor(var2, "cred");
      int var4 = (new DcbDebugDetailsFragment(var3, "Credentials")).show(var1, "showCredentials");
   }

   private void displayDcbParams() {
      FragmentTransaction var1 = this.getSupportFragmentManager().beginTransaction();
      CarrierBillingParameters var2 = this.mDcbStorage.getParams();
      ReflectionDcbDetailExtractor var3 = new ReflectionDcbDetailExtractor(var2, "dcb");
      int var4 = (new DcbDebugDetailsFragment(var3, "DCB Params")).show(var1, "showDcbParams");
   }

   private void displayProvisioning() {
      FragmentTransaction var1 = this.getSupportFragmentManager().beginTransaction();
      CarrierBillingProvisioning var2 = this.mDcbStorage.getProvisioning();
      ReflectionDcbDetailExtractor var3 = new ReflectionDcbDetailExtractor(var2, "prov");
      int var4 = (new DcbDebugDetailsFragment(var3, "Provisioning")).show(var1, "showProvisioning");
   }

   private void handleMenuClearItem(int var1) {
      switch(var1) {
      case 16:
         this.mDcbStorage.clearParams();
         break;
      case 32:
         this.mDcbStorage.clearProvisioning();
         break;
      case 48:
         this.mDcbStorage.clearCredentials();
         break;
      default:
         Object[] var2 = new Object[1];
         Integer var3 = Integer.valueOf(var1);
         var2[0] = var3;
         FinskyLog.wtf("Got unexpected whichField %s", var2);
      }

      this.updateStatus();
   }

   private void handleMenuRefreshItem(int var1) {
      switch(var1) {
      case 16:
         Runnable var4 = this.updateStatusRunnable;
         Runnable var5 = this.updateStatusRunnable;
         this.refreshDcbParams(var4, var5);
         return;
      case 32:
         Runnable var6 = this.updateStatusRunnable;
         Runnable var7 = this.updateStatusRunnable;
         this.refreshProvisioning(var6, var7);
         return;
      case 48:
         Runnable var8 = this.updateStatusRunnable;
         Runnable var9 = this.updateStatusRunnable;
         this.refreshCredentials(var8, var9);
         return;
      default:
         Object[] var2 = new Object[1];
         Integer var3 = Integer.valueOf(var1);
         var2[0] = var3;
         FinskyLog.wtf("Got unexpected whichField %s", var2);
      }
   }

   private void handleMenuViewDetails(int var1) {
      switch(var1) {
      case 16:
         this.displayDcbParams();
         return;
      case 32:
         this.displayProvisioning();
         return;
      case 48:
         this.displayCredentials();
         return;
      default:
         Object[] var2 = new Object[1];
         Integer var3 = Integer.valueOf(var1);
         var2[0] = var3;
         FinskyLog.wtf("Got unexpected whichField %s", var2);
      }
   }

   private void refreshAllInfo() {
      DcbDebugActivity.2 var1 = new DcbDebugActivity.2();
      DcbDebugActivity.3 var2 = new DcbDebugActivity.3(var1);
      BillingLocator.initCarrierBillingStorage(new DcbDebugActivity.4(var2));
   }

   private void refreshCredentials(Runnable var1, Runnable var2) {
      DcbDebugActivity.ErrorRunnable var3 = new DcbDebugActivity.ErrorRunnable("Error refreshing credentials", var2);
      CarrierCredentialsAction var4 = new CarrierCredentialsAction();
      String var5 = this.mDcbStorage.getProvisioning().getProvisioningId();
      var4.run(var5, (String)null, var1, var3);
   }

   private void refreshDcbParams(Runnable var1, Runnable var2) {
      GetMarketMetadataAction var3 = new GetMarketMetadataAction();
      String var4 = FinskyApp.get().getCurrentAccountName();
      DcbDebugActivity.9 var5 = new DcbDebugActivity.9(var1);
      DcbDebugActivity.10 var6 = new DcbDebugActivity.10(var2);
      var3.run(this, var4, var5, var6);
   }

   private void refreshProvisioning(Runnable var1, Runnable var2) {
      DcbDebugActivity.ErrorRunnable var3 = new DcbDebugActivity.ErrorRunnable("Error refreshing provisioning", var2);
      (new CarrierProvisioningAction()).forceRun(var1, var3);
   }

   private void updateStatus() {
      float var1 = 1.0F;
      boolean var2 = CarrierBillingUtils.isProvisioned(this.mDcbStorage);
      boolean var3 = CarrierBillingUtils.areCredentialsValid(this.mDcbStorage);
      boolean var4;
      if(this.mDcbStorage.getParams() != null) {
         var4 = true;
      } else {
         var4 = false;
      }

      RatingBar var5 = this.mProvStatus;
      float var6;
      if(var2) {
         var6 = 1.0F;
      } else {
         var6 = 0.0F;
      }

      var5.setRating(var6);
      RatingBar var7 = this.mCredStatus;
      float var8;
      if(var3) {
         var8 = 1.0F;
      } else {
         var8 = 0.0F;
      }

      var7.setRating(var8);
      RatingBar var9 = this.mDcbParamStatus;
      if(!var4) {
         var1 = (float)false;
      }

      var9.setRating(var1);
   }

   private static int whichField(View var0) {
      byte var3;
      switch(var0.getId()) {
      case 2131755122:
      case 2131755123:
         var3 = 16;
         break;
      case 2131755124:
      case 2131755125:
         var3 = 32;
         break;
      case 2131755126:
      case 2131755127:
         var3 = 48;
         break;
      default:
         Object[] var1 = new Object[1];
         Integer var2 = Integer.valueOf(var0.getId());
         var1[0] = var2;
         FinskyLog.wtf("Unknown view id %d", var1);
         var3 = -1;
      }

      return var3;
   }

   public boolean onContextItemSelected(MenuItem var1) {
      boolean var2 = true;
      int var3 = var1.getItemId() & 15;
      int var4 = var1.getItemId() & 240;
      switch(var3) {
      case 1:
         this.handleMenuViewDetails(var4);
         break;
      case 2:
         this.handleMenuRefreshItem(var4);
         break;
      case 3:
         this.handleMenuClearItem(var4);
         break;
      default:
         var2 = super.onContextItemSelected(var1);
      }

      return var2;
   }

   public void onCreate(Bundle var1) {
      this.setContentView(2130968608);
      super.onCreate(var1);
      RatingBar var2 = (RatingBar)this.findViewById(2131755124);
      this.mProvStatus = var2;
      this.mProvStatus.setEnabled((boolean)0);
      RatingBar var3 = (RatingBar)this.findViewById(2131755126);
      this.mCredStatus = var3;
      this.mCredStatus.setEnabled((boolean)0);
      RatingBar var4 = (RatingBar)this.findViewById(2131755122);
      this.mDcbParamStatus = var4;
      this.mDcbParamStatus.setEnabled((boolean)0);
      CarrierBillingStorage var5 = BillingLocator.getCarrierBillingStorage();
      this.mDcbStorage = var5;
      View var6 = this.findViewById(2131755124);
      this.registerForContextMenu(var6);
      View var7 = this.findViewById(2131755125);
      this.registerForContextMenu(var7);
      View var8 = this.findViewById(2131755126);
      this.registerForContextMenu(var8);
      View var9 = this.findViewById(2131755127);
      this.registerForContextMenu(var9);
      View var10 = this.findViewById(2131755122);
      this.registerForContextMenu(var10);
      View var11 = this.findViewById(2131755123);
      this.registerForContextMenu(var11);
      View var12 = this.findViewById(2131755125);
      DcbDebugActivity.5 var13 = new DcbDebugActivity.5();
      var12.setOnClickListener(var13);
      View var14 = this.findViewById(2131755127);
      DcbDebugActivity.6 var15 = new DcbDebugActivity.6();
      var14.setOnClickListener(var15);
      View var16 = this.findViewById(2131755123);
      DcbDebugActivity.7 var17 = new DcbDebugActivity.7();
      var16.setOnClickListener(var17);
      View var18 = this.findViewById(2131755128);
      DcbDebugActivity.8 var19 = new DcbDebugActivity.8();
      var18.setOnClickListener(var19);
   }

   public void onCreateContextMenu(ContextMenu var1, View var2, ContextMenuInfo var3) {
      int var4 = whichField(var2);
      byte var5 = 0;
      boolean var6 = CarrierBillingUtils.isProvisioned(this.mDcbStorage);
      boolean var7;
      if(this.mDcbStorage.getParams() != null) {
         var7 = true;
      } else {
         var7 = false;
      }

      if(var4 == 32 && var7) {
         var5 = 1;
      }

      if(var4 == 48 && var7 && var6) {
         var5 = 1;
      }

      if(var4 == 16) {
         var5 = 1;
      }

      int var8 = var4 | 1;
      var1.add(0, var8, 0, 2131230837);
      int var10 = var4 | 2;
      MenuItem var11 = var1.add(0, var10, 1, 2131230839).setEnabled((boolean)var5);
      int var12 = var4 | 3;
      var1.add(0, var12, 2, 2131230840);
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      this.getMenuInflater().inflate(2131689475, var1);
      return true;
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2;
      switch(var1.getItemId()) {
      case 2131755340:
         this.refreshAllInfo();
         var2 = true;
         break;
      default:
         var2 = super.onOptionsItemSelected(var1);
      }

      return var2;
   }

   protected void onReady(boolean var1) {}

   class 1 implements Runnable {

      1() {}

      public void run() {
         Utils.ensureOnMainThread();
         DcbDebugActivity.this.updateStatus();
      }
   }

   class 10 implements Response.ErrorListener {

      // $FF: synthetic field
      final Runnable val$errorRunnable;


      10(Runnable var2) {
         this.val$errorRunnable = var2;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Object[] var4 = new Object[]{var1, var2};
         String var5 = String.format("Error getting dcb params (%s): %s", var4);
         Toast.makeText(DcbDebugActivity.this, var5, 1).show();
         this.val$errorRunnable.run();
      }
   }

   private class ErrorRunnable implements Runnable {

      private final Runnable mChainedRunnable;
      private final String mErrorMessage;


      public ErrorRunnable(String var2, Runnable var3) {
         this.mErrorMessage = var2;
         this.mChainedRunnable = var3;
      }

      public void run() {
         DcbDebugActivity var1 = DcbDebugActivity.this;
         String var2 = this.mErrorMessage;
         Toast.makeText(var1, var2, 1).show();
         this.mChainedRunnable.run();
      }
   }

   class 8 implements OnClickListener {

      8() {}

      public void onClick(View var1) {
         FragmentTransaction var2 = DcbDebugActivity.this.getSupportFragmentManager().beginTransaction();
         DcbDebugActivity.8.1 var3 = new DcbDebugActivity.8.1();
         int var4 = (new DcbDebugDetailsFragment(var3, "DCB GServices Values")).show(var2, "dcbGservices");
      }

      class 1 implements DcbDetailExtractor {

         1() {}

         public Collection<DcbDetail> getDetails() {
            return DcbDebugActivity.GSERVICES_DETAILS;
         }
      }
   }

   class 9 implements Response.Listener<VendingProtos.GetMarketMetadataResponseProto> {

      // $FF: synthetic field
      final Runnable val$successRunnable;


      9(Runnable var2) {
         this.val$successRunnable = var2;
      }

      public void onResponse(VendingProtos.GetMarketMetadataResponseProto var1) {
         CarrierParamsAction var2 = new CarrierParamsAction(var1);
         Runnable var3 = this.val$successRunnable;
         var2.run(var3);
      }
   }

   class 6 implements OnClickListener {

      6() {}

      public void onClick(View var1) {
         DcbDebugActivity.this.displayCredentials();
      }
   }

   class 7 implements OnClickListener {

      7() {}

      public void onClick(View var1) {
         DcbDebugActivity.this.displayDcbParams();
      }
   }

   class 4 implements Runnable {

      // $FF: synthetic field
      final Runnable val$postRefreshDcbParamsRunnable;


      4(Runnable var2) {
         this.val$postRefreshDcbParamsRunnable = var2;
      }

      public void run() {
         DcbDebugActivity.this.updateStatus();
         DcbDebugActivity var1 = DcbDebugActivity.this;
         Runnable var2 = this.val$postRefreshDcbParamsRunnable;
         Runnable var3 = DcbDebugActivity.this.updateStatusRunnable;
         var1.refreshDcbParams(var2, var3);
      }
   }

   class 5 implements OnClickListener {

      5() {}

      public void onClick(View var1) {
         DcbDebugActivity.this.displayProvisioning();
      }
   }

   class 2 implements Runnable {

      2() {}

      public void run() {
         DcbDebugActivity.this.updateStatus();
         DcbDebugActivity var1 = DcbDebugActivity.this;
         Runnable var2 = DcbDebugActivity.this.updateStatusRunnable;
         Runnable var3 = DcbDebugActivity.this.updateStatusRunnable;
         var1.refreshCredentials(var2, var3);
      }
   }

   class 3 implements Runnable {

      // $FF: synthetic field
      final Runnable val$postRefreshProvRunnable;


      3(Runnable var2) {
         this.val$postRefreshProvRunnable = var2;
      }

      public void run() {
         DcbDebugActivity.this.updateStatus();
         DcbDebugActivity var1 = DcbDebugActivity.this;
         Runnable var2 = this.val$postRefreshProvRunnable;
         Runnable var3 = DcbDebugActivity.this.updateStatusRunnable;
         var1.refreshProvisioning(var2, var3);
      }
   }
}
