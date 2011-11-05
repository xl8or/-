package com.android.exchange;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import java.util.ArrayList;

public class SmsRelayService extends Service {

   public static final String SMS_RELAY_THREAD = "smsRelay";
   private static final String TAG = "SmsRelayService";
   private Thread mRelayThread;
   private ArrayList<SmsRelayService.SmsPayload> mSmsQueue;
   private boolean mStop;
   private Object mSyncObject;
   private TelephonyManager mTm;


   public SmsRelayService() {
      Object var1 = new Object();
      this.mSyncObject = var1;
      ArrayList var2 = new ArrayList();
      this.mSmsQueue = var2;
      this.mRelayThread = null;
      this.mStop = (boolean)0;
      this.mTm = null;
   }

   // $FF: synthetic method
   static Object access$100(SmsRelayService var0) {
      return var0.mSyncObject;
   }

   // $FF: synthetic method
   static TelephonyManager access$200(SmsRelayService var0) {
      return var0.mTm;
   }

   // $FF: synthetic method
   static ArrayList access$300(SmsRelayService var0) {
      return var0.mSmsQueue;
   }

   // $FF: synthetic method
   static boolean access$400(SmsRelayService var0) {
      return var0.mStop;
   }

   // $FF: synthetic method
   static void access$500(SmsRelayService var0) {
      var0.onDone();
   }

   private void onDone() {
      this.mRelayThread = null;
   }

   public IBinder onBind(Intent var1) {
      return null;
   }

   public void onCreate() {
      super.onCreate();
      TelephonyManager var1 = (TelephonyManager)this.getSystemService("phone");
      this.mTm = var1;
   }

   public void onDestroy() {
      super.onDestroy();
      this.mStop = (boolean)1;
      this.mRelayThread = null;
      if(this.mSmsQueue != null) {
         this.mSmsQueue.clear();
      }

      this.mSmsQueue = null;
   }

   public void onLowMemory() {
      super.onLowMemory();
      this.mStop = (boolean)1;
   }

   public int onStartCommand(Intent param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class RelaySmsRunnable implements Runnable {

      private RelaySmsRunnable() {}

      // $FF: synthetic method
      RelaySmsRunnable(SmsRelayService.1 var2) {
         this();
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   private class SmsPayload {

      SmsMessage[] mMessages;


      public SmsPayload(SmsMessage[] var2) {
         this.mMessages = var2;
      }
   }
}
