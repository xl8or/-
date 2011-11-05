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
import java.net.SocketTimeoutException;
import org.apache.http.HttpResponse;

public class EasEmptyTrashSvc extends EasSyncService {

   private static final int COMMAND_TIMEOUT = 20000;


   public EasEmptyTrashSvc(Context var1, EmailContent.Mailbox var2) {
      super(var1, var2);
      String var3 = this.mAccount.mProtocolVersion;
      this.mProtocolVersion = var3;
      Double var4 = Double.valueOf(Double.parseDouble(this.mProtocolVersion));
      this.mProtocolVersionDouble = var4;
   }

   private int emptyTrash() throws IOException, DeviceAccessException {
      long var1 = this.mAccount.mId;
      this.emptyTrashCb(var1, 1, 0);
      HttpResponse var3 = this.makeEmptyTrashRequest((String)null, (String)null);
      byte var4;
      if(var3 == null) {
         var4 = 0;
      } else {
         int var5 = var3.getStatusLine().getStatusCode();
         this.userLog("emptyTrash(): sendHttpClientPost HTTP response code: ", var5);
         byte var6;
         if(var5 == 200) {
            label51:
            switch(this.parseEmptyTrashResponse(var3)) {
            case 0:
               var6 = 0;
               long var17 = this.mAccount.mId;
               this.emptyTrashCb(var17, var6, 100);
               break;
            case 25:
               String var9 = this.mUserName;
               String var10 = this.mPassword;
               HttpResponse var11 = this.makeEmptyTrashRequest(var9, var10);
               if(var11 == null) {
                  var4 = 0;
                  return var4;
               }

               int var12 = var11.getStatusLine().getStatusCode();
               this.userLog("emptyTrash(): sendHttpClientPost HTTP response code: ", var12);
               if(var5 == 200) {
                  switch(this.parseEmptyTrashResponse(var11)) {
                  case 0:
                     var6 = 0;
                     break label51;
                  default:
                     if(this.isProvisionError(var5)) {
                        var6 = 23;
                     } else if(this.isAuthError(var5)) {
                        var6 = 22;
                     } else {
                        var6 = 21;
                     }

                     long var13 = this.mAccount.mId;
                     this.emptyTrashCb(var13, var6, 100);
                  }
               } else {
                  var6 = 21;
                  if(this.isAuthError(var5)) {
                     ;
                  }

                  long var15 = this.mAccount.mId;
                  this.emptyTrashCb(var15, var6, 100);
               }
               break;
            default:
               if(this.isProvisionError(var5)) {
                  var6 = 23;
               } else if(this.isAuthError(var5)) {
                  var6 = 22;
               } else {
                  var6 = 21;
               }

               long var7 = this.mAccount.mId;
               this.emptyTrashCb(var7, var6, 100);
            }
         } else {
            byte var19 = 21;
            if(this.isProvisionError(var5)) {
               var19 = 23;
            } else if(this.isAuthError(var5)) {
               var19 = 22;
            }

            long var20 = this.mAccount.mId;
            this.emptyTrashCb(var20, var19, 100);
            var6 = 0;
         }

         var4 = var6;
      }

      return var4;
   }

   private void emptyTrashCb(long var1, int var3, int var4) {
      try {
         SyncManager.callback().emptyTrashStatus(var1, var3, var4);
      } catch (RemoteException var6) {
         ;
      }
   }

   private HttpResponse makeEmptyTrashRequest(String var1, String var2) throws IOException {
      Serializer var3 = new Serializer();
      this.prepareCommand(var3, var1, var2);
      return this.sendCommand(var3);
   }

   private int parseEmptyTrashResponse(HttpResponse param1) throws IOException, DeviceAccessException {
      // $FF: Couldn't be decompiled
   }

   private void prepareCommand(Serializer var1, String var2, String var3) throws IOException {
      if(var1 != null) {
         if(this.mProtocolVersionDouble.doubleValue() >= 12.0D) {
            Serializer var4 = var1.start(1285).start(1298);
            String var5 = this.mMailbox.mServerId;
            Serializer var6 = var4.data(18, var5).start(1288).tag(1299);
            if(var2 != null && var3 != null && this.mProtocolVersionDouble.doubleValue() >= 14.0D) {
               var1.data(1300, var2);
               var1.data(1301, var3);
            }

            var1.end().end().end().done();
         }
      }
   }

   private HttpResponse sendCommand(Serializer var1) {
      HttpResponse var2 = null;
      if(this.mProtocolVersionDouble.doubleValue() >= 12.0D) {
         String var3 = "ItemOperations";
         if(var1 != null) {
            HttpResponse var10;
            try {
               if(Eas.PARSER_LOG) {
                  String[] var4 = new String[]{"emptyTrash(): Wbxml:"};
                  this.userLog(var4);
                  byte[] var5 = var1.toByteArray();
                  ByteArrayInputStream var6 = new ByteArrayInputStream(var5);
                  EmailContent.Mailbox var7 = this.mMailbox;
                  boolean var8 = (new LogAdapter(var7, this)).parse(var6);
               }

               byte[] var9 = var1.toByteArray();
               var10 = this.sendHttpClientPost(var3, var9);
            } catch (SocketTimeoutException var25) {
               String[] var12 = new String[1];
               StringBuilder var13 = (new StringBuilder()).append("emptyTrash(): Exception obtained: ");
               String var14 = var25.getMessage();
               String var15 = var13.append(var14).toString();
               var12[0] = var15;
               this.userLog(var12);
               long var16 = this.mAccount.mId;
               this.emptyTrashCb(var16, 393216, 100);
               var2 = null;
               return var2;
            } catch (Exception var26) {
               String[] var19 = new String[1];
               StringBuilder var20 = (new StringBuilder()).append("emptyTrash(): Exception obtained: ");
               String var21 = var26.getMessage();
               String var22 = var20.append(var21).toString();
               var19[0] = var22;
               this.userLog(var19);
               long var23 = this.mAccount.mId;
               this.emptyTrashCb(var23, -1, 100);
               var2 = null;
               return var2;
            }

            var2 = var10;
         }
      }

      return var2;
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }
}
