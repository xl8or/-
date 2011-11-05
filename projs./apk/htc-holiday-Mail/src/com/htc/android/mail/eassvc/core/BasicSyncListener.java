package com.htc.android.mail.eassvc.core;

import android.content.Context;
import android.content.Intent;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailEventBroadcaster;
import com.htc.android.mail.eassvc.core.SyncListener;

public class BasicSyncListener implements SyncListener {

   private Context mContext;
   private int mSrcType;


   public BasicSyncListener(Context var1) {
      this.mContext = var1;
   }

   public void endSync(long var1, int var3) {
      Intent var4 = new Intent("com.htc.eas.intent.stop_sync");
      int var5 = this.mSrcType;
      var4.putExtra("extra.syncsrc_type", var5);
      var4.putExtra("extra.sync_result", var3);
      this.mContext.sendBroadcast(var4);
      if(this.mSrcType == 3) {
         Mail.mMailEvent.setMailSyncFinish(var1);
         MailEventBroadcaster var8 = Mail.mMailEvent;
         Context var9 = this.mContext;
         var8.flush(var9);
      }
   }

   public void setSyncSrcType(int var1) {
      this.mSrcType = var1;
   }

   public void startSync(long var1) {
      Intent var3 = new Intent("com.htc.eas.intent.start_sync");
      int var4 = this.mSrcType;
      var3.putExtra("extra.syncsrc_type", var4);
      this.mContext.sendBroadcast(var3);
      int var6 = this.mSrcType;
      if(3 == var6) {
         Mail.mMailEvent.setMailSyncStart(var1);
         MailEventBroadcaster var7 = Mail.mMailEvent;
         Context var8 = this.mContext;
         var7.flush(var8);
      }
   }
}
