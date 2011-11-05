package com.htc.android.mail.eassvc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.EASAppSvc;
import com.htc.android.mail.eassvc.util.EASLog;

public class EASEventReceiver extends BroadcastReceiver {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private final String TAG = "EASBootEventReceiver";
   private Context mContext;


   public EASEventReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      this.mContext = var1;
      String var3 = var2.getAction();
      if("android.net.conn.CONNECTIVITY_CHANGE".equals(var3)) {
         NetworkInfo var4 = (NetworkInfo)var2.getParcelableExtra("networkInfo");
         State var5 = var4.getState();
         State var6 = State.CONNECTED;
         if(var5 != var6) {
            State var7 = var4.getState();
            State var8 = State.DISCONNECTED;
            if(var7 != var8) {
               if(!DEBUG) {
                  return;
               }

               String var9 = "CONNECTIVITY_ACTION : not connect/dissconnect event, " + var4;
               EASLog.d("EASBootEventReceiver", var9);
               return;
            }
         }
      } else {
         String var10 = var2.getAction();
         if("android.intent.action.ANY_DATA_STATE".equals(var10)) {
            String var11 = var2.getStringExtra("state");
            String var12 = var2.getStringExtra("reason");
            if(!"roamingOn".equals(var12) && !"roamingOff".equals(var12)) {
               if(!DEBUG) {
                  return;
               }

               String var13 = "ACTION_ANY_DATA_CONNECTION_STATE_CHANGED: not roaming, state = " + var11 + ", reason = " + var12;
               EASLog.v("EASBootEventReceiver", var13);
               return;
            }
         }
      }

      var2.setClass(var1, EASAppSvc.class);
      var1.startService(var2);
   }
}
