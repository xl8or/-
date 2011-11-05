package com.android.exchange;

import android.content.Context;
import android.os.RemoteException;
import com.android.email.mail.DeviceAccessException;
import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.SyncManager;
import com.android.exchange.adapter.ItemOperationsAdapter;
import com.android.exchange.adapter.ItemOperationsParser;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class EasConvSvc extends EasSyncService {

   private byte[] mConversationId;
   int mIgnore;
   private EmailContent.Message mMsg;
   long mToMailboxId;


   public EasConvSvc(Context var1, EmailContent.Message var2, long var3, byte[] var5, int var6) {
      super(var1, var2);
      this.mMsg = var2;
      this.mConversationId = var5;
      this.mToMailboxId = var3;
      this.mIgnore = var6;
      String var7 = this.mAccount.mProtocolVersion;
      this.mProtocolVersion = var7;
      Double var8 = Double.valueOf(Double.parseDouble(this.mProtocolVersion));
      this.mProtocolVersionDouble = var8;
   }

   private void moveConvAlwaysCb(byte[] var1, int var2, int var3, int var4) {
      try {
         SyncManager.callback().moveConvAlwaysStatus(var1, var2, var3, var4);
      } catch (RemoteException var6) {
         ;
      }
   }

   private int moveConversationAlways() throws IOException, DeviceAccessException {
      byte[] var1 = this.mConversationId;
      int var2 = this.mIgnore;
      byte var5 = 1;
      byte var6 = 0;
      this.moveConvAlwaysCb(var1, var5, var6, var2);
      byte var8 = 0;
      Serializer var9 = new Serializer();
      byte var55;
      if(this.mProtocolVersionDouble.doubleValue() >= 14.0D) {
         String var10 = "ItemOperations";
         short var12 = 1285;
         var9.start(var12);
         Context var14 = this.mContext;
         long var15 = this.mToMailboxId;
         EmailContent.Mailbox var17 = EmailContent.Mailbox.restoreMailboxWithId(var14, var15);
         if(var17 != null) {
            short var19 = 1302;
            Serializer var20 = var9.start(var19);
            byte[] var21 = this.mConversationId;
            Serializer var22 = var20.dataOpaque(1304, var21);
            String var23 = var17.mServerId;
            Serializer var24 = var22.data(1303, var23).start(1288).tag(1305).end().end();
            Serializer var25 = var9.end();
            var9.done();
         }

         HttpResponse var30;
         try {
            byte[] var26 = var9.toByteArray();
            var30 = this.sendHttpClientPost(var10, var26);
         } catch (Exception var108) {
            byte[] var57 = this.mConversationId;
            int var58 = this.mIgnore;
            char var61 = '\ufffd';
            byte var62 = 100;
            this.moveConvAlwaysCb(var57, var61, var62, var58);
            var55 = 0;
            return var55;
         }

         int var32 = var30.getStatusLine().getStatusCode();
         short var34 = 200;
         if(var32 == var34) {
            label43: {
               HttpEntity var35 = var30.getEntity();
               if((int)var35.getContentLength() > 0) {
                  InputStream var36 = var35.getContent();
                  if(var36 != null) {
                     byte var47;
                     label38: {
                        byte var46;
                        try {
                           ItemOperationsParser var37 = new ItemOperationsParser;
                           ItemOperationsAdapter var38 = new ItemOperationsAdapter;
                           EmailContent.Mailbox var39 = this.mMailbox;
                           var38.<init>(var39, this);
                           var37.<init>(var36, var38);
                           var46 = var37.parse();
                        } catch (IOException var106) {
                           var47 = 0;
                           byte[] var65 = this.mConversationId;
                           int var66 = this.mIgnore;
                           int var69 = 524288;
                           byte var70 = 100;
                           this.moveConvAlwaysCb(var65, var69, var70, var66);
                           break label38;
                        } catch (OutOfMemoryError var107) {
                           var47 = 0;
                           byte[] var73 = this.mConversationId;
                           int var74 = this.mIgnore;
                           int var77 = 589824;
                           byte var78 = 100;
                           this.moveConvAlwaysCb(var73, var77, var78, var74);
                           break label38;
                        }

                        var47 = var46;
                     }

                     if(var47 != 0) {
                        byte[] var48 = this.mConversationId;
                        int var49 = this.mIgnore;
                        byte var52 = 0;
                        byte var53 = 100;
                        this.moveConvAlwaysCb(var48, var52, var53, var49);
                     } else if(this.isProvisionError(var32)) {
                        byte[] var82 = this.mConversationId;
                        int var83 = this.mIgnore;
                        byte var86 = 23;
                        byte var87 = 100;
                        this.moveConvAlwaysCb(var82, var86, var87, var83);
                     }

                     var8 = 0;
                     break label43;
                  }
               }

               var8 = 0;
               byte[] var89 = this.mConversationId;
               int var90 = this.mIgnore;
               byte var93 = 21;
               byte var94 = 100;
               this.moveConvAlwaysCb(var89, var93, var94, var90);
            }
         } else {
            byte var96 = 21;
            if(this.isAuthError(var32)) {
               var96 = 22;
            }

            byte[] var99 = this.mConversationId;
            int var100 = this.mIgnore;
            byte var104 = 100;
            this.moveConvAlwaysCb(var99, var96, var104, var100);
            var8 = 0;
         }
      }

      var55 = var8;
      return var55;
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }
}
