package com.android.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;

public class NetworkConnectivityListener {

   private static final boolean DBG = false;
   private static final String TAG = "NetworkConnectivityListener";
   private Context mContext;
   private HashMap<Handler, Integer> mHandlers;
   private boolean mIsFailover;
   private boolean mListening;
   private NetworkInfo mNetworkInfo;
   private NetworkInfo mOtherNetworkInfo;
   private String mReason;
   private NetworkConnectivityListener.ConnectivityBroadcastReceiver mReceiver;
   private NetworkConnectivityListener.State mState;


   public NetworkConnectivityListener() {
      HashMap var1 = new HashMap();
      this.mHandlers = var1;
      NetworkConnectivityListener.State var2 = NetworkConnectivityListener.State.UNKNOWN;
      this.mState = var2;
      NetworkConnectivityListener.ConnectivityBroadcastReceiver var3 = new NetworkConnectivityListener.ConnectivityBroadcastReceiver((NetworkConnectivityListener.1)null);
      this.mReceiver = var3;
   }

   public NetworkInfo getNetworkInfo() {
      return this.mNetworkInfo;
   }

   public NetworkInfo getOtherNetworkInfo() {
      return this.mOtherNetworkInfo;
   }

   public String getReason() {
      return this.mReason;
   }

   public NetworkConnectivityListener.State getState() {
      return this.mState;
   }

   public boolean isFailover() {
      return this.mIsFailover;
   }

   public void registerHandler(Handler var1, int var2) {
      HashMap var3 = this.mHandlers;
      Integer var4 = Integer.valueOf(var2);
      var3.put(var1, var4);
   }

   public void startListening(Context var1) {
      synchronized(this){}

      try {
         if(!this.mListening) {
            this.mContext = var1;
            IntentFilter var2 = new IntentFilter();
            var2.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            NetworkConnectivityListener.ConnectivityBroadcastReceiver var3 = this.mReceiver;
            var1.registerReceiver(var3, var2);
            this.mListening = (boolean)1;
         }
      } finally {
         ;
      }

   }

   public void stopListening() {
      synchronized(this){}

      try {
         if(this.mListening) {
            Context var1 = this.mContext;
            NetworkConnectivityListener.ConnectivityBroadcastReceiver var2 = this.mReceiver;
            var1.unregisterReceiver(var2);
            this.mContext = null;
            this.mNetworkInfo = null;
            this.mOtherNetworkInfo = null;
            this.mIsFailover = (boolean)0;
            this.mReason = null;
            this.mListening = (boolean)0;
         }
      } finally {
         ;
      }

   }

   public void unregisterHandler(Handler var1) {
      this.mHandlers.remove(var1);
   }

   public static enum State {

      // $FF: synthetic field
      private static final NetworkConnectivityListener.State[] $VALUES;
      CONNECTED("CONNECTED", 1),
      NOT_CONNECTED("NOT_CONNECTED", 2),
      UNKNOWN("UNKNOWN", 0);


      static {
         NetworkConnectivityListener.State[] var0 = new NetworkConnectivityListener.State[3];
         NetworkConnectivityListener.State var1 = UNKNOWN;
         var0[0] = var1;
         NetworkConnectivityListener.State var2 = CONNECTED;
         var0[1] = var2;
         NetworkConnectivityListener.State var3 = NOT_CONNECTED;
         var0[2] = var3;
         $VALUES = var0;
      }

      private State(String var1, int var2) {}
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class ConnectivityBroadcastReceiver extends BroadcastReceiver {

      private ConnectivityBroadcastReceiver() {}

      // $FF: synthetic method
      ConnectivityBroadcastReceiver(NetworkConnectivityListener.1 var2) {
         this();
      }

      public void onReceive(Context var1, Intent var2) {
         if(var2.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && NetworkConnectivityListener.this.mListening) {
            if(var2.getBooleanExtra("noConnectivity", (boolean)0)) {
               NetworkConnectivityListener var7 = NetworkConnectivityListener.this;
               NetworkConnectivityListener.State var8 = NetworkConnectivityListener.State.NOT_CONNECTED;
               var7.mState = var8;
            } else {
               NetworkConnectivityListener var27 = NetworkConnectivityListener.this;
               NetworkConnectivityListener.State var28 = NetworkConnectivityListener.State.CONNECTED;
               var27.mState = var28;
            }

            NetworkConnectivityListener var10 = NetworkConnectivityListener.this;
            NetworkInfo var11 = (NetworkInfo)var2.getParcelableExtra("networkInfo");
            var10.mNetworkInfo = var11;
            NetworkConnectivityListener var13 = NetworkConnectivityListener.this;
            NetworkInfo var14 = (NetworkInfo)var2.getParcelableExtra("otherNetwork");
            var13.mOtherNetworkInfo = var14;
            NetworkConnectivityListener var16 = NetworkConnectivityListener.this;
            String var17 = var2.getStringExtra("reason");
            var16.mReason = var17;
            NetworkConnectivityListener var19 = NetworkConnectivityListener.this;
            boolean var20 = var2.getBooleanExtra("isFailover", (boolean)0);
            var19.mIsFailover = var20;
            Iterator var22 = NetworkConnectivityListener.this.mHandlers.keySet().iterator();

            while(var22.hasNext()) {
               Handler var23 = (Handler)var22.next();
               int var24 = ((Integer)NetworkConnectivityListener.this.mHandlers.get(var23)).intValue();
               Message var25 = Message.obtain(var23, var24);
               var23.sendMessage(var25);
            }

         } else {
            StringBuilder var3 = (new StringBuilder()).append("onReceived() called with ");
            String var4 = NetworkConnectivityListener.this.mState.toString();
            String var5 = var3.append(var4).append(" and ").append(var2).toString();
            int var6 = Log.w("NetworkConnectivityListener", var5);
         }
      }
   }
}
