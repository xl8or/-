package com.android.email.irm;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.email.Controller;
import com.android.email.provider.EmailContent;
import com.android.exchange.Eas;

public class IRMContentExpiryReciever extends BroadcastReceiver {

   public IRMContentExpiryReciever() {}

   public void onReceive(Context var1, Intent var2) {
      if(var2.getAction().equals("expiry")) {
         if(Eas.USER_LOG) {
            int var3 = Log.i("IRM", "IRMContentExpiryReciever:onreceive");
         }

         String var4 = var2.getStringExtra("MessageId");
         if(var4 != null) {
            ContentResolver var5 = var1.getContentResolver();
            Uri var6 = EmailContent.Message.CONTENT_URI;
            String[] var7 = new String[]{"_id"};
            String[] var8 = new String[]{var4};
            Cursor var9 = var5.query(var6, var7, "syncServerId=?", var8, (String)null);
            Controller var10 = Controller.getInstance(var1);
            if(var9 != null) {
               boolean var11 = var9.moveToFirst();
               Long var12 = Long.valueOf(Long.parseLong(var9.getString(0)));
               if(Eas.USER_LOG) {
                  int var13 = Log.i("IRM", "IRMContentExpiryReciever:start load more");
               }

               long var14 = var12.longValue();
               var10.loadMore(var14);
            }
         }
      }
   }
}
