package com.samsung.android.bt.app.sap;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController.AlertParams;
import com.broadcom.bt.service.framework.BluetoothProxyManager;
import com.broadcom.bt.service.framework.IBluetoothProxyCallback;
import com.broadcom.bt.service.sap.BluetoothSAP;

public class BluetoothSAPDialog extends AlertActivity implements OnClickListener, IBluetoothProxyCallback {

   private static final String TAG = "BluetoothSAPDialog";
   protected BluetoothSAP mSAPProxy;


   public BluetoothSAPDialog() {}

   private View createView() {
      int var1 = Log.v("BluetoothSAPDialog", "createView");
      View var2 = this.getLayoutInflater().inflate(2130903054, (ViewGroup)null);
      TextView var3 = (TextView)var2.findViewById(2131427353);
      String var4 = this.getString(2131232630);
      var3.setText(var4);
      return var2;
   }

   public void onClick(DialogInterface var1, int var2) {
      int var3 = Log.v("BluetoothSAPDialog", "onClick");
      switch(var2) {
      case -2:
         this.finish();
         return;
      case -1:
         this.onDisconnect();
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      int var2 = Log.v("BluetoothSAPDialog", "onCreate");
      String var3 = this.getIntent().getAction();
      String var4 = this.getIntent().getStringExtra("SVC_NAME");
      if(!var3.equals("broadcom.android.bluetooth.intent.action.BT_SERVICE_CONNECTION") || !var4.equals("bluetooth_sap")) {
         int var5 = Log.e("BluetoothSAPDialog", "Not BT_SERVICE_CONNECTION_ACTION, SERVICE_NAME");
         this.finish();
      }

      AlertParams var6 = this.mAlertParams;
      var6.mIconId = 17301995;
      String var7 = this.getString(2131232628);
      var6.mTitle = var7;
      View var8 = this.createView();
      var6.mView = var8;
      String var9 = this.getString(2131232562);
      var6.mPositiveButtonText = var9;
      var6.mPositiveButtonListener = this;
      String var10 = this.getString(2131232563);
      var6.mNegativeButtonText = var10;
      var6.mNegativeButtonListener = this;
      this.setupAlert();
   }

   protected void onDestroy() {
      int var1 = Log.v("BluetoothSAPDialog", "onDestroy");
      if(this.mSAPProxy != null) {
         this.mSAPProxy.finish();
      }

      super.onDestroy();
   }

   public void onDisconnect() {
      int var1 = Log.v("BluetoothSAPDialog", "onDisconnect");
      if(BluetoothProxyManager.isServiceSupported("bluetooth_sap")) {
         boolean var2 = BluetoothProxyManager.getProxy("bluetooth_sap", this, this);
      }
   }

   public void onProxyAvailable(Object var1) {
      BluetoothSAP var2 = (BluetoothSAP)var1;
      this.mSAPProxy = var2;
      int var3 = Log.v("BluetoothSAPDialog", "onProxyAvailable");
      if(this.mSAPProxy != null) {
         this.mSAPProxy.disconnect((byte)1);
      }
   }
}
