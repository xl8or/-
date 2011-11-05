package com.android.exchange;

import android.content.Context;
import android.os.RemoteException;
import com.android.email.mail.DeviceAccessException;
import com.android.email.provider.EmailContent;
import com.android.exchange.Eas;
import com.android.exchange.EasSyncService;
import com.android.exchange.SyncManager;
import com.android.exchange.adapter.LogAdapter;
import com.android.exchange.adapter.Serializer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.http.HttpResponse;

public class EasLoadMoreSvc extends EasSyncService {

   private static final int COMMAND_TIMEOUT = 20000;
   public boolean eas12p = 0;
   public EmailContent.Message mMsg;
   private boolean userCancelledFlag;


   public EasLoadMoreSvc(Context var1, EmailContent.Message var2) {
      super(var1, var2);
      this.mMsg = var2;
      String var3 = this.mAccount.mProtocolVersion;
      this.mProtocolVersion = var3;
      Double var4 = Double.valueOf(Double.parseDouble(this.mProtocolVersion));
      this.mProtocolVersionDouble = var4;
      this.userCancelledFlag = (boolean)0;
   }

   private int loadMore() throws IOException, DeviceAccessException {
      long var1 = this.mMsg.mId;
      this.loadMoreCb(var1, 1, 0);
      int var3 = 0;
      HttpResponse var4 = this.makeLoadMoreRequest((String)null, (String)null);
      int var5;
      if(var4 == null) {
         if(!this.userCancelledFlag) {
            var3 = 0;
         } else {
            var3 = 1048576;
         }

         var5 = var3;
      } else {
         int var6 = var4.getStatusLine().getStatusCode();
         this.userLog("loadMore(): sendHttpClientPost HTTP response code: ", var6);
         if(var6 == 200) {
            label47:
            switch(this.parseLoadMoreResponse(var4)) {
            case 0:
               var3 = 0;
               break;
            case 25:
               HttpResponse var11 = this.makeLoadMoreRequest((String)null, (String)null);
               if(var11 == null) {
                  long var12 = this.mMsg.mId;
                  this.loadMoreCb(var12, 0, 100);
                  var5 = 0;
                  return var5;
               }

               int var14 = var11.getStatusLine().getStatusCode();
               this.userLog("loadMore(): sendHttpClientPost HTTP response code: ", var14);
               if(var6 == 200) {
                  switch(this.parseLoadMoreResponse(var11)) {
                  case 0:
                     var3 = 0;
                     break label47;
                  default:
                     if(this.isProvisionError(var6)) {
                        var3 = 23;
                     } else {
                        var3 = 196608;
                     }

                     long var15 = this.mMsg.mId;
                     this.loadMoreCb(var15, var3, 100);
                     var5 = var3;
                     return var5;
                  }
               }
               break;
            default:
               if(this.isProvisionError(var6)) {
                  var3 = 23;
               } else {
                  var3 = 196608;
               }

               long var7 = this.mMsg.mId;
               this.loadMoreCb(var7, var3, 100);
            }

            long var9 = this.mMsg.mId;
            this.loadMoreCb(var9, var3, 100);
         } else {
            byte var17 = 21;
            if(this.isProvisionError(var6)) {
               var17 = 23;
            } else if(this.isAuthError(var6)) {
               var17 = 22;
            }

            long var18 = this.mMsg.mId;
            this.loadMoreCb(var18, var17, 100);
            var3 = 0;
         }

         var5 = var3;
      }

      return var5;
   }

   private void loadMoreCb(long var1, int var3, int var4) {
      try {
         SyncManager.callback().loadMoreStatus(var1, var3, var4);
      } catch (RemoteException var6) {
         ;
      }
   }

   private HttpResponse makeLoadMoreRequest(String var1, String var2) throws IOException, DeviceAccessException {
      Serializer var3 = new Serializer();
      this.prepareCommand(var3, var1, var2);
      return this.sendCommand(var3);
   }

   private int parseLoadMoreResponse(HttpResponse param1) throws IOException, DeviceAccessException {
      // $FF: Couldn't be decompiled
   }

   private void prepareCommand(Serializer var1, String var2, String var3) throws IOException {
      if(this.mProtocolVersionDouble.doubleValue() < 12.0D) {
         Serializer var14 = var1.start(5).start(28).start(15).data(16, "Email");
         String var15 = this.mMailbox.mSyncKey;
         Serializer var16 = var14.data(11, var15);
         String var17 = this.mMailbox.mServerId;
         Serializer var18 = var16.data(18, var17).start(23).data(35, "8").data(34, "2").end().start(22).start(10);
         String var19 = this.mMsg.mServerId;
         var18.data(13, var19).end().end().end().end().end().done();
      } else {
         Serializer var4 = var1.start(1285).start(1286).data(1287, "Mailbox");
         if(this.mMailbox.mServerId != null && this.mMsg.mServerId != null) {
            String var5 = this.mMailbox.mServerId;
            var1.data(18, var5);
            String var7 = this.mMsg.mServerId;
            var1.data(13, var7);
         } else if(this.mMsg.mServerId != null) {
            String var12 = this.mMsg.mServerId;
            var1.data(984, var12);
         }

         Serializer var9 = var1.start(1288).data(34, "2").start(1093).data(1094, "4").end();
         if(var2 != null && var3 != null && this.mProtocolVersionDouble.doubleValue() >= 14.0D) {
            var1.data(1300, var2);
            var1.data(1301, var3);
         }

         var1.end().end().end().done();
         this.eas12p = (boolean)1;
      }
   }

   private HttpResponse sendCommand(Serializer var1) throws DeviceAccessException {
      String var2;
      if(this.mProtocolVersionDouble.doubleValue() >= 12.0D) {
         var2 = "ItemOperations";
      } else {
         var2 = "Sync";
      }

      HttpResponse var9;
      HttpResponse var10;
      try {
         if(Eas.PARSER_LOG) {
            String[] var3 = new String[]{"loadMore(): Wbxml:"};
            this.userLog(var3);
            byte[] var4 = var1.toByteArray();
            ByteArrayInputStream var5 = new ByteArrayInputStream(var4);
            EmailContent.Mailbox var6 = this.mMailbox;
            boolean var7 = (new LogAdapter(var6, this)).parse(var5);
         }

         byte[] var8 = var1.toByteArray();
         var9 = this.sendHttpClientPost(var2, var8);
      } catch (IOException var14) {
         var10 = null;
         if(!this.userCancelledFlag) {
            long var12 = this.mMsg.mId;
            this.loadMoreCb(var12, -1, 100);
         }

         return var10;
      }

      var10 = var9;
      return var10;
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   public void userCancelled() {
      this.userCancelledFlag = (boolean)1;
   }
}
