package com.android.email;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.email.Controller;
import com.android.internal.widget.LockPatternUtils;

public class SecurityReceiver extends BroadcastReceiver {

   public static final String ACTION_PASSWORD_RECOVERY = "com.android.security.intent.action.PASSWORD_RECOVERY";
   private static final String TAG = SecurityReceiver.class.getSimpleName();
   private Context mContext;
   private Controller mController;


   public SecurityReceiver() {}

   private void handlePasswordRecovery() {
      int var1 = Log.d(TAG, "handlePasswordRecovery");
      Context var2 = this.mContext;
      String var3 = (new LockPatternUtils(var2)).getRecoveryPassword();
      String var4 = "Recovery password created: " + var3;
      int var5 = Log.d("Email", var4);
      this.mController.sendRecoveryPassword(var3);
   }

   public void onReceive(Context var1, Intent var2) {
      int var3 = Log.d(TAG, "onReceive");
      this.mContext = var1;
      Controller var4 = Controller.getInstance(var1);
      this.mController = var4;
      if(var2 != null) {
         String var5 = var2.getAction();
         if(var5 != null) {
            if(var5.equals("com.android.security.intent.action.PASSWORD_RECOVERY")) {
               this.handlePasswordRecovery();
            }
         }
      }
   }
}
