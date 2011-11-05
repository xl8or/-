package com.android.email.activity.setup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.android.email.Email;
import com.android.email.activity.setup.AccountSetupBasicExchange;
import com.android.email.activity.setup.AccountSetupBasicsPremium;
import com.digc.seven.Z7MailHandler;
import com.seven.Z7.app.Z7AppBaseActivity;
import com.seven.Z7.common.Z7ServiceCallback;
import com.seven.Z7.common.provisioning.Z7ProvisioningResponse;
import com.seven.Z7.shared.ANSharedCommon;
import com.seven.Z7.shared.Z7ServiceConstants;
import com.seven.Z7.util.Z7ErrorCode2;
import com.seven.util.IntArrayMap;
import com.seven.util.Z7Result;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountSetupProvisioning extends Z7AppBaseActivity implements OnItemClickListener {

   public static final String ACTIVESYNC = "eas";
   public static final String CLARO = "claro";
   public static final String COMCEL = "comcel";
   public static final String EXCHAGE = "owa";
   public static final String GMAIL = "gmail";
   public static final String IS_PREMIUM_USER = "is_premium_user";
   public static final String OTHER = null;
   public static final String PROVISION_ISP_TYPE = "provision_isp_type";
   public static final int REQUEST_CODE = 9;
   public static final String SELECTED_ISP_ID = "isp_id";
   public static final String SELECTED_ISP_NAME = "isp_name";
   public static final String UOL = "uol";
   public static final String WINDOW_LIVE = "msn";
   public static final String YAHOO = "yahoo";
   public static final String YAHOO_JP = "yahoo_jp";
   private static boolean needRefresh;
   private final String COUNTRY_CODE;
   private final String SALES_CODE;
   private AccountSetupProvisioning.ProvisionAdapter adapter;
   private List<IntArrayMap> connectors;
   private boolean isPremiumUser;
   private ListView listProvisioning;
   private AccountSetupProvisioning.MyListener mListener;
   private View usePremiumButtonView;


   public AccountSetupProvisioning() {
      String var1 = SystemProperties.get("ro.csc.country_code", "unknown");
      this.COUNTRY_CODE = var1;
      String var2 = SystemProperties.get("ro.csc.sales_code", "unknown");
      this.SALES_CODE = var2;
   }

   public static void actionNewAccount(Context var0) {
      Intent var1 = new Intent(var0, AccountSetupProvisioning.class);
      var0.startActivity(var1);
   }

   private void callGACForSevenEngine() {
      this.showProgressDialogForProvision();
      AccountSetupProvisioning.MyListener var1 = this.mListener;
      this.initializeSevenEngineAndCallGAC(var1);
      this.startConnectionTimeOut();
   }

   private void completeGetList(List<IntArrayMap> var1) {
      this.connectors = var1;
      AccountSetupProvisioning.3 var2 = new AccountSetupProvisioning.3();
      this.runOnUiThread(var2);
   }

   private List<IntArrayMap> getBasicConnectorList() {
      this.isPremiumUser = (boolean)0;
      ArrayList var1 = new ArrayList();
      IntArrayMap var2 = new IntArrayMap();
      String var3 = this.getString(2131166948);
      var2.put(11, var3);
      Object var5 = var2.put(49, "eas");
      var1.add(var2);
      IntArrayMap var7 = new IntArrayMap();
      String var8 = this.getString(2131166201);
      var7.put(11, var8);
      Object var10 = var7.put(49, "gmail");
      var1.add(var7);
      IntArrayMap var12 = new IntArrayMap();
      String var13 = this.getString(2131166209);
      var12.put(11, var13);
      Object var15 = var12.put(49, "msn");
      var1.add(var12);
      IntArrayMap var17 = new IntArrayMap();
      String var18 = this.getString(2131166941);
      var17.put(11, var18);
      Object var20 = var17.put(49, "yahoo");
      var1.add(var17);
      IntArrayMap var22 = new IntArrayMap();
      String var23 = this.getString(2131166211);
      var22.put(11, var23);
      String var25 = OTHER;
      var22.put(49, var25);
      var1.add(var22);
      return var1;
   }

   private void getPremiumISPList() {}

   protected static void refresh() {
      needRefresh = (boolean)1;
   }

   private void showProgressDialogForProvision() {
      AccountSetupProvisioning.2 var1 = new AccountSetupProvisioning.2();
      this.showProgressDialog(var1);
   }

   protected void doAfterPremiumAdded() {
      List var1 = this.getBasicConnectorList();
      this.completeGetList(var1);
   }

   protected void negativeActionForDialog(int var1) {
      if(var1 == 10002) {
         Z7MailHandler var2 = this.zHandler;
         AccountSetupProvisioning.MyListener var3 = this.mListener;
         var2.unRegisterListener(var3);
         List var4 = this.getBasicConnectorList();
         this.completeGetList(var4);
      }
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if(var2 == -1) {
         if(var1 == 9) {
            this.finish();
         }
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Handler var2 = this.handler;
      AccountSetupProvisioning.MyListener var3 = new AccountSetupProvisioning.MyListener(var2);
      this.mListener = var3;
      this.setContentView(2130903064);
      ListView var4 = (ListView)this.findViewById(2131361950);
      this.listProvisioning = var4;
      this.listProvisioning.setOnItemClickListener(this);
      View var5 = this.getLayoutInflater().inflate(2130903069, (ViewGroup)null);
      this.usePremiumButtonView = var5;
      View var6 = this.usePremiumButtonView.findViewById(2131361975);
      AccountSetupProvisioning.1 var7 = new AccountSetupProvisioning.1();
      var6.setOnClickListener(var7);
      if(((TelephonyManager)this.getSystemService("phone")).getSimState() == 1) {
         List var8 = this.getBasicConnectorList();
         this.completeGetList(var8);
         if(this.listProvisioning.getFooterViewsCount() == 1) {
            ListView var9 = this.listProvisioning;
            View var10 = this.usePremiumButtonView;
            var9.removeFooterView(var10);
         }
      } else {
         List var12 = this.getBasicConnectorList();
         this.completeGetList(var12);
      }
   }

   protected void onDestroy() {
      this.stopConnectionTimeOut();
      super.onDestroy();
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      String var6 = ((IntArrayMap)this.connectors.get(var3)).getString(49);
      if(var6 == null) {
         var6 = "";
      }

      Class var7 = AccountSetupBasicsPremium.class;
      if(var6.equals("eas")) {
         var7 = AccountSetupBasicExchange.class;
      }

      Intent var8 = new Intent(this, var7);
      String var9 = ((IntArrayMap)this.connectors.get(var3)).getString(11);
      var8.putExtra("isp_name", var9);
      var8.putExtra("isp_id", var6);
      boolean var12 = this.isPremiumUser;
      var8.putExtra("is_premium_user", var12);
      if(this.isPremiumUser && !var6.equals("eas")) {
         int var14 = ((IntArrayMap)this.connectors.get(var3)).getInt(50, 1);
         var8.putExtra("provision_isp_type", var14);
      }

      this.startActivityForResult(var8, 9);
   }

   protected void onPause() {
      super.onPause();
   }

   protected void onResume() {
      if(needRefresh) {
         SharedPreferences var1 = this.getSharedPreferences("sharedPreferenceCB", 0);
         if(var1.contains("is_premium_connector") && !var1.getBoolean("is_premium_connector", (boolean)1)) {
            List var2 = this.getBasicConnectorList();
            this.completeGetList(var2);
         } else {
            this.callGACForSevenEngine();
         }

         needRefresh = (boolean)0;
      }

      super.onResume();
   }

   protected void onStop() {
      super.onStop();
   }

   private class ProvisionAdapter extends BaseAdapter {

      private List<IntArrayMap> connectorList;
      private LayoutInflater mInflater;
      private int offset;


      public ProvisionAdapter(List var2) {
         LayoutInflater var3 = LayoutInflater.from(AccountSetupProvisioning.this);
         this.mInflater = var3;
         this.connectorList = var2;
      }

      private int getEmailIspImage(String var1) {
         int var2 = 2130837798;
         if(var1 != null) {
            if(var1.equals("eas")) {
               var2 = 2130837799;
            } else if(var1.equals("owa")) {
               var2 = 2130837798;
            } else if(var1.equals("gmail")) {
               var2 = 2130837798;
            } else if(var1.equals("msn")) {
               var2 = 2130837798;
            } else if(var1.equals("yahoo")) {
               var2 = 2130837798;
            } else if(var1.equals("claro")) {
               var2 = 2130837798;
            } else if(var1.equals("uol")) {
               var2 = 2130837798;
            } else if(var1.equals("comcel")) {
               var2 = 2130837798;
            }
         }

         return var2;
      }

      public int getCount() {
         return this.connectorList.size();
      }

      public Object getItem(int var1) {
         return Integer.valueOf(var1);
      }

      public long getItemId(int var1) {
         long var2;
         if(this.offset == 0) {
            var2 = (long)var1;
         } else {
            var2 = (long)(this.offset + var1 - 1);
         }

         return var2;
      }

      public View getView(int var1, View var2, ViewGroup var3) {
         AccountSetupProvisioning.ProvisionAdapter.ViewHolder var4;
         if(var2 == null) {
            var2 = this.mInflater.inflate(2130903065, (ViewGroup)null);
            var4 = new AccountSetupProvisioning.ProvisionAdapter.ViewHolder();
            TextView var5 = (TextView)var2.findViewById(2131361952);
            var4.text = var5;
            ImageView var6 = (ImageView)var2.findViewById(2131361951);
            var4.icon = var6;
            var2.setTag(var4);
         } else {
            var4 = (AccountSetupProvisioning.ProvisionAdapter.ViewHolder)var2.getTag();
         }

         IntArrayMap var7 = (IntArrayMap)this.connectorList.get(var1);
         String var8 = var7.getString(11);
         String var9 = var7.getString(49);
         var4.text.setText(var8);
         ImageView var10 = var4.icon;
         int var11 = this.getEmailIspImage(var9);
         var10.setImageResource(var11);
         return var2;
      }

      public void setConnectorList(List<IntArrayMap> var1) {
         this.connectorList = var1;
      }

      class ViewHolder {

         ImageView icon;
         TextView text;


         ViewHolder() {}
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         AccountSetupProvisioning.this.callGACForSevenEngine();
      }
   }

   // $FF: synthetic class
   static class 4 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$seven$Z7$shared$Z7ServiceConstants$SystemCallbackType = new int[Z7ServiceConstants.SystemCallbackType.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$seven$Z7$shared$Z7ServiceConstants$SystemCallbackType;
            int var1 = Z7ServiceConstants.SystemCallbackType.Z7_CALLBACK_PROVISIONING_CONNECTORS.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }
      }
   }

   class 2 implements OnKeyListener {

      2() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         boolean var4;
         if(var2 == 4) {
            AccountSetupProvisioning.this.dismissProgressDialog();
            AccountSetupProvisioning.this.finish();
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }
   }

   class 3 implements Runnable {

      3() {}

      public void run() {
         AccountSetupProvisioning.this.dismissProgressDialog();
         if(AccountSetupProvisioning.this.isPremiumUser) {
            if(AccountSetupProvisioning.this.listProvisioning.getFooterViewsCount() == 1) {
               ListView var1 = AccountSetupProvisioning.this.listProvisioning;
               View var2 = AccountSetupProvisioning.this.usePremiumButtonView;
               var1.removeFooterView(var2);
            }
         } else if(AccountSetupProvisioning.this.listProvisioning.getFooterViewsCount() == 0) {
            ListView var13 = AccountSetupProvisioning.this.listProvisioning;
            View var14 = AccountSetupProvisioning.this.usePremiumButtonView;
            var13.addFooterView(var14);
         }

         if(AccountSetupProvisioning.this.adapter == null) {
            AccountSetupProvisioning var4 = AccountSetupProvisioning.this;
            AccountSetupProvisioning var5 = AccountSetupProvisioning.this;
            List var6 = AccountSetupProvisioning.this.connectors;
            AccountSetupProvisioning.ProvisionAdapter var7 = var5.new ProvisionAdapter(var6);
            var4.adapter = var7;
            ListView var9 = AccountSetupProvisioning.this.listProvisioning;
            AccountSetupProvisioning.ProvisionAdapter var10 = AccountSetupProvisioning.this.adapter;
            var9.setAdapter(var10);
            ListView var11 = AccountSetupProvisioning.this.listProvisioning;
            ListView var12 = AccountSetupProvisioning.this.listProvisioning;
            var11.focusableViewAvailable(var12);
         } else {
            AccountSetupProvisioning.ProvisionAdapter var15 = AccountSetupProvisioning.this.adapter;
            List var16 = AccountSetupProvisioning.this.connectors;
            var15.setConnectorList(var16);
            AccountSetupProvisioning.this.adapter.notifyDataSetChanged();
         }
      }
   }

   private class MyListener extends Email.Z7ConnectionListener {

      public MyListener(Handler var2) {
         super(var2);
      }

      private List<IntArrayMap> addActiveSyncToList(List<IntArrayMap> var1) {
         String var6;
         int var7;
         for(Iterator var2 = var1.iterator(); var2.hasNext(); var7 = Log.d("###", var6)) {
            IntArrayMap var3 = (IntArrayMap)var2.next();
            StringBuilder var4 = (new StringBuilder()).append("11-PROVISION_ISP_BRAND_ID:");
            String var5 = var3.getString(49);
            var6 = var4.append(var5).append(":").toString();
         }

         Iterator var8 = var1.iterator();

         while(var8.hasNext()) {
            IntArrayMap var9 = (IntArrayMap)var8.next();
            if(var9.getString(49) != null && var9.getString(49).equals("owa")) {
               var1.remove(var9);
               break;
            }
         }

         IntArrayMap var11 = new IntArrayMap();
         String var12 = AccountSetupProvisioning.this.getString(2131166948);
         var11.put(11, var12);
         Object var14 = var11.put(49, "eas");
         Short var15 = Short.valueOf((short)0);
         var11.put(10, var15);
         var1.add(0, var11);
         return var1;
      }

      private boolean isNoGoogleSalesCode() {
         boolean var1 = false;
         String[] var2 = AccountSetupProvisioning.this.getResources().getStringArray(2131296303);
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            if(AccountSetupProvisioning.this.COUNTRY_CODE.equalsIgnoreCase(var5)) {
               var1 = true;
            }
         }

         return var1;
      }

      public void onCallback(Z7ServiceCallback var1) {
         Z7ServiceConstants.SystemCallbackType var2 = var1.getSystemCallbackType();
         if(var2 != null) {
            int[] var3 = AccountSetupProvisioning.4.$SwitchMap$com$seven$Z7$shared$Z7ServiceConstants$SystemCallbackType;
            int var4 = var2.ordinal();
            switch(var3[var4]) {
            case 1:
               int var5 = var1.getResultCode();
               Z7Result var6 = new Z7Result(var5);
               AccountSetupProvisioning.this.stopConnectionTimeOut();
               if(Z7Result.Z7_SUCCEEDED(var6)) {
                  IntArrayMap var7 = (IntArrayMap)var1.getObject();
                  Z7ProvisioningResponse var8 = new Z7ProvisioningResponse(var7);
                  if(var8.isImScope()) {
                     return;
                  }

                  boolean var9 = (boolean)(AccountSetupProvisioning.this.isPremiumUser = (boolean)1);
                  AccountSetupProvisioning var10 = AccountSetupProvisioning.this;
                  List var11 = var8.getConnectors();
                  List var12 = this.addActiveSyncToList(var11);
                  var10.completeGetList(var12);
                  AccountSetupProvisioning.this.changePrefToPremiumConnector();
                  return;
               } else {
                  int var13 = var1.getErrorCode();
                  int var14 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SUBSCRIPTION_EXPIRED.getValue();
                  if(var13 == var14) {
                     boolean var15 = (boolean)(AccountSetupProvisioning.this.isPremiumUser = (boolean)0);
                     AccountSetupProvisioning.this.dismissProgressDialog();
                     AccountSetupProvisioning.this.showDialog(10002);
                     return;
                  } else {
                     int var16 = var1.getErrorCode();
                     int var17 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SERVICE_SUBSCRIPTION_REQUIRED.getValue();
                     if(var16 == var17) {
                        boolean var18 = (boolean)(AccountSetupProvisioning.this.isPremiumUser = (boolean)0);
                        AccountSetupProvisioning.this.dismissProgressDialog();
                        AccountSetupProvisioning var19 = AccountSetupProvisioning.this;
                        List var20 = AccountSetupProvisioning.this.getBasicConnectorList();
                        var19.completeGetList(var20);
                        return;
                     } else {
                        Z7Result var21 = Z7Result.Z7_E_TIMEOUT;
                        if(var6.equals(var21)) {
                           AccountSetupProvisioning.this.dismissProgressDialog();
                           AccountSetupProvisioning var22 = AccountSetupProvisioning.this;
                           String var23 = ANSharedCommon.getErrorText(Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SEND_TIMEDOUT.getValue());
                           AccountSetupProvisioning.MyListener.1 var24 = new AccountSetupProvisioning.MyListener.1();
                           var22.showPopupReport("", var23, var24);
                           return;
                        } else {
                           int var25 = var1.getErrorCode();
                           int var26 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_NO_AVAILABLE_CONNECTORS.getValue();
                           if(var25 == var26) {
                              AccountSetupProvisioning.this.dismissProgressDialog();
                              AccountSetupProvisioning var27 = AccountSetupProvisioning.this;
                              String var28 = AccountSetupProvisioning.this.getString(2131165808);
                              String var29 = AccountSetupProvisioning.this.getString(2131165809);
                              AccountSetupProvisioning.MyListener.2 var30 = new AccountSetupProvisioning.MyListener.2();
                              var27.showPopupReport(var28, var29, var30);
                              return;
                           }

                           String var31;
                           if(var1.hasErrorText()) {
                              var31 = var1.getErrorText();
                           } else if(var1.hasErrorCode()) {
                              var31 = ANSharedCommon.getErrorText(var1.getErrorCode());
                           } else {
                              var31 = ANSharedCommon.fetchString(2131166954);
                           }

                           AccountSetupProvisioning.this.dismissProgressDialog();
                           AccountSetupProvisioning var32 = AccountSetupProvisioning.this;
                           AccountSetupProvisioning.MyListener.3 var33 = new AccountSetupProvisioning.MyListener.3();
                           var32.showPopupReport("", var31, var33);
                           return;
                        }
                     }
                  }
               }
            default:
            }
         }
      }

      class 3 implements android.content.DialogInterface.OnClickListener {

         3() {}

         public void onClick(DialogInterface var1, int var2) {
            var1.dismiss();
            AccountSetupProvisioning.this.finish();
         }
      }

      class 2 implements android.content.DialogInterface.OnClickListener {

         2() {}

         public void onClick(DialogInterface var1, int var2) {
            var1.dismiss();
            AccountSetupProvisioning.this.finish();
         }
      }

      class 1 implements android.content.DialogInterface.OnClickListener {

         1() {}

         public void onClick(DialogInterface var1, int var2) {
            var1.dismiss();
            AccountSetupProvisioning.this.finish();
         }
      }
   }
}
