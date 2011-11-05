package com.android.exchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.exchange.Eas;

public class SecretCodeReceiver extends BroadcastReceiver {

   private static final String CLOSE_EAS_LOG = "0102";
   private static final String OPEN_EAS_LOG = "2010";
   private static final String TAG = "EAS SecretCodeReceiver";


   public SecretCodeReceiver() {}

   public void onReceive(Context var1, Intent var2) {
      String var3 = "onReceive " + var2;
      int var4 = Log.i("EAS SecretCodeReceiver", var3);
      String var5;
      if(var2.getData() != null) {
         var5 = var2.getData().getHost();
      } else {
         var5 = null;
      }

      if("2010".equals(var5)) {
         int var6 = Log.i("EAS SecretCodeReceiver", "Open EAS log");
         Eas.USER_LOG = (boolean)1;
         Eas.PARSER_LOG = (boolean)1;
         Eas.SERIALIZER_LOG = (boolean)1;
      } else if("0102".equals(var5)) {
         int var7 = Log.i("EAS SecretCodeReceiver", "Close EAS log");
         Eas.USER_LOG = (boolean)0;
         Eas.PARSER_LOG = (boolean)0;
         Eas.SERIALIZER_LOG = (boolean)0;
      }
   }
}
