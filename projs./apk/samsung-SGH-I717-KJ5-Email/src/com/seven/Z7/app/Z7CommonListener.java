package com.seven.Z7.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import com.android.email.Email;
import com.seven.Z7.app.Z7AppBaseActivity;

public class Z7CommonListener {

   private static Z7CommonListener instance;
   private final String ACTION_KEY = "action";
   private final String TAG = "Z7CommonListener";
   protected Email mApp;
   protected Z7CommonListener.MyServiceListener mListener;


   public Z7CommonListener(Context var1) {
      Email var2 = Email.getApplication(var1);
      this.mApp = var2;
      Handler var3 = Z7AppBaseActivity.getUIActionHandler();
      Z7CommonListener.MyServiceListener var4 = new Z7CommonListener.MyServiceListener(var3);
      this.mListener = var4;
      Email var5 = this.mApp;
      Z7CommonListener.MyServiceListener var6 = this.mListener;
      var5.registerListener(var6);
   }

   public static final Z7CommonListener getInstance(Context var0) {
      if(instance == null) {
         instance = new Z7CommonListener(var0);
      }

      return instance;
   }

   public void unRegisterListener() {
      int var1 = Log.d("Z7CommonListener", "unRegisterListener");
      Email var2 = this.mApp;
      Z7CommonListener.MyServiceListener var3 = this.mListener;
      var2.unregisterListener(var3);
   }

   private final class MyServiceListener extends Email.Z7ConnectionListener {

      public MyServiceListener(Handler var2) {
         super(var2);
      }

      public void onCallback(Bundle var1) {
         int var2 = var1.getInt("event-id");
         StringBuilder var3 = (new StringBuilder()).append("");
         String var4 = var1.toString();
         String var5 = var3.append(var4).toString();
         int var6 = Log.d("Z7CommonListener", var5);
         if(var2 == 93) {
            if(var1.containsKey("action")) {
               if(var1.getInt("action") == 60) {
                  Email.deleteSevenAccount(Z7CommonListener.this.mApp.getApplicationContext());
                  Process.killProcess(Process.myPid());
               }
            }
         }
      }
   }
}
