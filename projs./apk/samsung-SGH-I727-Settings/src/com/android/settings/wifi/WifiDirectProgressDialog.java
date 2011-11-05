package com.android.settings.wifi;

import android.app.Activity;
import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.sec.android.touchwiz.widget.TwProgressDialog;

public class WifiDirectProgressDialog extends Activity implements OnClickListener, OnDismissListener {

   private static final String TAG = "WifiDirectProgressDialog";
   private static final int WPS_MAX_TIME = 120000;
   private String mAddr;
   private Context mContext;
   private IntentFilter mFilter;
   private int mLapseTime;
   private TextView mMessageView;
   private String mPin;
   private PowerManager mPowerManager;
   private TwProgressDialog mProgressDialog;
   private String mProgressNumberFormat;
   private final BroadcastReceiver mReceiver;
   private CountDownTimer mTimer;
   protected WakeLock mWakeLock;
   protected WakeLock mWakeLockforToast;
   private WifiManager mWifiManager;
   private boolean mWpsInProgress;


   public WifiDirectProgressDialog() {
      WifiDirectProgressDialog.1 var1 = new WifiDirectProgressDialog.1();
      this.mReceiver = var1;
   }

   // $FF: synthetic method
   static int access$320(WifiDirectProgressDialog var0, int var1) {
      int var2 = var0.mLapseTime - var1;
      var0.mLapseTime = var2;
      return var2;
   }

   private void dismissProgressDialog() {
      int var1 = Log.d("WifiDirectProgressDialog", "dismissProgressDialog");
      boolean var2 = this.mWifiManager.setSupplicantAutoScan((boolean)1);
      if(this.mTimer != null) {
         this.mTimer.cancel();
      }

      if(this.mProgressDialog != null) {
         this.mProgressDialog.dismiss();
         this.mProgressDialog = null;
         this.mLapseTime = 120000;
         this.mWpsInProgress = (boolean)0;
      }

      this.finish();
   }

   public static String getTimeString(int var0) {
      int var1 = var0 / 1000 / 60;
      int var2 = var0 / 1000 % 60;
      Object[] var3 = new Object[2];
      Integer var4 = Integer.valueOf(var1);
      var3[0] = var4;
      Integer var5 = Integer.valueOf(var2);
      var3[1] = var5;
      return String.format("%02d:%02d", var3);
   }

   private void onCancel() {
      if(!this.mWifiManager.isGOCreated()) {
         int var1 = Log.d("WifiDirectProgressDialog", "GO isn\'t created, so it will be disconnected!!");
         boolean var2 = this.mWifiManager.disconnectWifiDirectPeer((String)null);
      } else {
         int var3 = Log.d("WifiDirectProgressDialog", "GO is created, so cannot disconnect!!");
         boolean var4 = this.mWifiManager.stopWifiDirectWps();
         WifiManager var5 = this.mWifiManager;
         String var6 = this.mAddr;
         var5.disconnectWifiDirectPeer(var6);
      }
   }

   private void showProgressDialog() {
      int var1 = Log.d("WifiDirectProgressDialog", "showProgressDialog");
      this.mWakeLock.acquire();
      TwProgressDialog var2 = new TwProgressDialog(this);
      this.mProgressDialog = var2;
      this.mLapseTime = 120000;
      this.mProgressDialog.setIcon(0);
      this.mProgressDialog.setProgressStyle(1);
      TwProgressDialog var3 = this.mProgressDialog;
      CharSequence var4 = this.getText(2131231633);
      WifiDirectProgressDialog.2 var5 = new WifiDirectProgressDialog.2();
      var3.setButton(var4, var5);
      this.mProgressDialog.setOnDismissListener(this);
      this.mProgressDialog.setTitle(2131231097);
      String var6 = this.getString(2131231098);
      if(this.mPin != null) {
         String var7 = var6 + "\n";
         StringBuilder var8 = (new StringBuilder()).append(var7);
         String var9 = this.getString(2131231082);
         String var10 = var8.append(var9).toString();
         String var11 = var10 + ": ";
         StringBuilder var12 = (new StringBuilder()).append(var11);
         String var13 = this.mPin;
         var6 = var12.append(var13).toString();
      }

      this.mProgressDialog.setMessage(var6);
      this.mProgressDialog.setProgress(0);
      this.mProgressDialog.setMax(120000);
      this.mProgressDialog.setProgressPercentVisibility(8);
      this.mProgressDialog.setProgressNumberVisibility(0);
      this.mProgressDialog.setProgressNumberFormat("%2d:%2d");
      this.mProgressDialog.show();
      if(this.mTimer != null) {
         this.mTimer.cancel();
      }

      CountDownTimer var15 = (new WifiDirectProgressDialog.3(121000L, 1000L)).start();
      this.mTimer = var15;
   }

   private void showWpsToast(int var1) {
      WakeLock var2 = this.mPowerManager.newWakeLock(536870938, "WifiDirectProgressDialog");
      this.mWakeLockforToast = var2;
      Toast.makeText(this, var1, 1).show();
      this.mWakeLockforToast.acquire();
      if(this.mWakeLockforToast.isHeld()) {
         this.mWakeLockforToast.release();
      }
   }

   public void onClick(DialogInterface var1, int var2) {
      int var3 = Log.d("WifiDirectProgressDialog", "onClick, Disconnect Peer.");
      switch(var2) {
      case -2:
         this.onCancel();
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Intent var2 = this.getIntent();
      Context var3 = this.getApplicationContext();
      this.mContext = var3;
      boolean var4 = this.requestWindowFeature(1);
      this.mWpsInProgress = (boolean)1;
      PowerManager var5 = (PowerManager)this.getSystemService("power");
      this.mPowerManager = var5;
      WakeLock var6 = this.mPowerManager.newWakeLock(26, "WifiDirectProgressDialog");
      this.mWakeLock = var6;
      int var7 = Log.d("WifiDirectProgressDialog", "onCreate");
      ((StatusBarManager)this.mContext.getSystemService("statusbar")).collapse();
      WifiManager var8 = (WifiManager)this.mContext.getSystemService("wifi");
      this.mWifiManager = var8;
      boolean var9 = this.mWifiManager.setSupplicantAutoScan((boolean)0);
      String var10 = var2.getStringExtra("dev_address");
      this.mAddr = var10;
      String var11 = var2.getStringExtra("PIN_number");
      this.mPin = var11;
      IntentFilter var12 = new IntentFilter();
      this.mFilter = var12;
      this.mFilter.addAction("android.net.wifidirect.DIRECT_PEER_CONNECTED");
      this.mFilter.addAction("android.net.wifidirect.CREATE_LINK_FAIL");
      this.mFilter.addAction("android.net.wifidirect.DIRECT_GO_READY");
      Context var13 = this.mContext;
      BroadcastReceiver var14 = this.mReceiver;
      IntentFilter var15 = this.mFilter;
      var13.registerReceiver(var14, var15);
      this.showProgressDialog();
   }

   protected void onDestroy() {
      super.onDestroy();
      Context var1 = this.mContext;
      BroadcastReceiver var2 = this.mReceiver;
      var1.unregisterReceiver(var2);
      int var3 = Log.d("WifiDirectProgressDialog", "onDestroy() finish");
      if(this.mWakeLock.isHeld()) {
         this.mWakeLock.release();
      }
   }

   public void onDismiss(DialogInterface var1) {
      int var2 = Log.d("WifiDirectProgressDialog", "onDismiss");
      if(!this.mWifiManager.isDirectConnected()) {
         this.onCancel();
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         String var4 = "action: " + var3;
         int var5 = Log.i("WifiDirectProgressDialog", var4);
         if(!"android.net.wifidirect.DIRECT_PEER_CONNECTED".equals(var3) && !"android.net.wifidirect.DIRECT_GO_READY".equals(var3)) {
            if("android.net.wifidirect.CREATE_LINK_FAIL".equals(var3)) {
               WifiDirectProgressDialog.this.showWpsToast(2131231103);
               if(WifiDirectProgressDialog.this.mWakeLock.isHeld()) {
                  WifiDirectProgressDialog.this.mWakeLock.release();
               }

               WifiDirectProgressDialog.this.dismissProgressDialog();
            } else if("android.net.wifidirect.DHCP_Failure".equals(var3)) {
               WifiDirectProgressDialog.this.showWpsToast(2131231103);
               if(WifiDirectProgressDialog.this.mWakeLock.isHeld()) {
                  WifiDirectProgressDialog.this.mWakeLock.release();
               }

               WifiDirectProgressDialog.this.dismissProgressDialog();
            }
         } else {
            WifiDirectProgressDialog.this.showWpsToast(2131231100);
            if(WifiDirectProgressDialog.this.mWakeLock.isHeld()) {
               WifiDirectProgressDialog.this.mWakeLock.release();
            }

            WifiDirectProgressDialog.this.dismissProgressDialog();
         }
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         int var3 = Log.d("WifiDirectProgressDialog", "mProgressDialog: onClick(), CANCEL clicked. Disconnectpeer wiill be called. ");
         if(WifiDirectProgressDialog.this.mWakeLock.isHeld()) {
            WifiDirectProgressDialog.this.mWakeLock.release();
         }

         WifiDirectProgressDialog.this.onCancel();
      }
   }

   class 3 extends CountDownTimer {

      3(long var2, long var4) {
         super(var2, var4);
      }

      public void onFinish() {
         WifiDirectProgressDialog.this.dismissProgressDialog();
         WifiDirectProgressDialog.this.showWpsToast(2131231103);
      }

      public void onTick(long var1) {
         int var3 = WifiDirectProgressDialog.access$320(WifiDirectProgressDialog.this, 1000);
         WifiDirectProgressDialog.this.mProgressDialog.incrementProgressBy(1000);
         WifiDirectProgressDialog var4 = WifiDirectProgressDialog.this;
         String var5 = WifiDirectProgressDialog.getTimeString(WifiDirectProgressDialog.this.mLapseTime);
         var4.mProgressNumberFormat = var5;
         TwProgressDialog var7 = WifiDirectProgressDialog.this.mProgressDialog;
         String var8 = WifiDirectProgressDialog.this.mProgressNumberFormat;
         var7.setProgressNumberFormat(var8);
      }
   }
}
