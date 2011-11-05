package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController.AlertParams;
import com.android.settings.bluetooth.LocalBluetoothManager;
import com.broadcom.bt.service.framework.BluetoothAccessResponse;
import com.broadcom.bt.service.framework.BluetoothServiceConfig;

public class BluetoothRequestServiceDialog extends AlertActivity implements OnClickListener {

   private static final String TAG = "BluetoothRequestServiceDialog";
   private static final boolean V;
   private String mActionString;
   private String mFileName;
   private Intent mIntent;
   private LocalBluetoothManager mLocalManager;
   private byte mOpCode;
   private String mRemoteAddress;
   private String mRemoteName;
   private String mRequestedServiceDisplayName;
   private String mRequestedServiceName;


   public BluetoothRequestServiceDialog() {}

   private View createView() {
      View var1 = this.getLayoutInflater().inflate(2130903053, (ViewGroup)null);
      TextView var2 = (TextView)var1.findViewById(2131427353);
      Object[] var3 = new Object[2];
      String var4 = this.mRemoteName;
      var3[0] = var4;
      String var5 = this.mActionString;
      var3[1] = var5;
      String var6 = this.getString(2131232558, var3);
      var2.setText(var6);
      return var1;
   }

   private void loadAccessRequestParams(Intent var1) {
      String var2 = this.mIntent.getStringExtra("SVC_NAME");
      this.mRequestedServiceName = var2;
      byte var3 = this.mIntent.getByteExtra("OPERATION", (byte)0);
      this.mOpCode = var3;
      String var4 = this.mIntent.getStringExtra("FILEPATH");
      this.mFileName = var4;
      String var5 = this.mIntent.getStringExtra("RMT_DEV_NAME");
      this.mRemoteName = var5;
      if(TextUtils.isEmpty(this.mRemoteName)) {
         String var6 = this.mIntent.getStringExtra("RMT_DEV_ADDR");
         this.mRemoteAddress = var6;
         BluetoothAdapter var7 = this.mLocalManager.getBluetoothAdapter();
         String var8 = this.mRemoteAddress;
         String var9 = var7.getRemoteDevice(var8).getName();
         this.mRemoteName = var9;
      }
   }

   private void onAllow(boolean var1) {
      BluetoothAccessResponse var2 = new BluetoothAccessResponse();
      Intent var3 = this.mIntent;
      var2.process(this, var3, var1);
      this.mRequestedServiceName = null;
   }

   private void setActionAndServerString() {
      String var1 = this.mRequestedServiceName;
      if("bluetooth_ftp".equals(var1)) {
         switch(this.mOpCode) {
         case 1:
            String var4 = this.getString(2131232568);
            this.mActionString = var4;
            break;
         case 2:
            String var5 = this.getString(2131232569);
            this.mActionString = var5;
            break;
         case 3:
            String var6 = this.getString(2131232570);
            this.mActionString = var6;
            break;
         case 4:
            String var7 = this.getString(2131232571);
            this.mActionString = var7;
            break;
         case 5:
            String var8 = this.getString(2131232572);
            this.mActionString = var8;
            break;
         case 6:
            String var9 = this.getString(2131232573);
            this.mActionString = var9;
         }

         String var2 = this.getString(2131232567);
         this.mRequestedServiceDisplayName = var2;
      } else {
         String var10 = this.mRequestedServiceName;
         if("bluetooth_pbap".equals(var10)) {
            switch(this.mOpCode) {
            case 1:
               String var12 = this.getString(2131232575);
               this.mActionString = var12;
               break;
            case 2:
               String var13 = this.getString(2131232576);
               this.mActionString = var13;
               break;
            case 3:
               String var14 = this.getString(2131232577);
               this.mActionString = var14;
               break;
            case 4:
               String var15 = this.getString(2131232578);
               this.mActionString = var15;
            }

            String var11 = this.getString(2131232574);
            this.mRequestedServiceDisplayName = var11;
         } else {
            String var16 = this.mRequestedServiceName;
            if("bluetooth_opp_service".equals(var16)) {
               switch(this.mOpCode) {
               case 1:
                  String var19 = this.getString(2131232583);
                  this.mActionString = var19;
                  break;
               case 2:
                  String var18 = this.getString(2131232582);
                  this.mActionString = var18;
               }

               String var17 = this.getString(2131232581);
               this.mRequestedServiceDisplayName = var17;
            }
         }
      }

      if(this.mActionString == null) {
         String var3 = this.getString(2131232559);
         this.mActionString = var3;
      }
   }

   public void onClick(DialogInterface var1, int var2) {
      switch(var2) {
      case -2:
         this.onAllow((boolean)0);
         return;
      case -1:
         this.onAllow((boolean)1);
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(!BluetoothServiceConfig.isAccessRequestEnabled()) {
         BluetoothAccessResponse var2 = new BluetoothAccessResponse();
         Intent var3 = this.getIntent();
         var2.process(this, var3, (boolean)1);
         this.finish();
      } else {
         LocalBluetoothManager var4 = LocalBluetoothManager.getInstance(this);
         this.mLocalManager = var4;
         Intent var5 = this.getIntent();
         this.mIntent = var5;
         AlertParams var6 = this.mAlertParams;
         var6.mIconId = 17301995;
         if(this.mIntent.getAction().equals("broadcom.android.bluetooth.intent.action.BT_SERVICE_ACCESS")) {
            Intent var7 = this.mIntent;
            this.loadAccessRequestParams(var7);
            this.setActionAndServerString();
            String var8 = this.getString(2131232557);
            var6.mTitle = var8;
         } else {
            int var12 = Log.e("BluetoothRequestServiceDialog", "Unknown Intent ");
         }

         View var9 = this.createView();
         var6.mView = var9;
         String var10 = this.getString(2131232564);
         var6.mPositiveButtonText = var10;
         var6.mPositiveButtonListener = this;
         String var11 = this.getString(2131232565);
         var6.mNegativeButtonText = var11;
         var6.mNegativeButtonListener = this;
      }
   }

   protected void onResume() {
      super.onResume();
      this.setupAlert();
   }
}
