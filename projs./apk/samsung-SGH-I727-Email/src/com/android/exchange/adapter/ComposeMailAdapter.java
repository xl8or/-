package com.android.exchange.adapter;

import android.util.Log;
import com.android.email.mail.DeviceAccessException;
import com.android.email.provider.EmailContent;
import com.android.exchange.Eas;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.AbstractCommandAdapter;
import com.android.exchange.adapter.AbstractSyncParser;
import com.android.exchange.adapter.Parser;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;
import java.io.InputStream;

public class ComposeMailAdapter extends AbstractCommandAdapter {

   private static final int HTTP_NEED_PROVISIONING = 449;
   private static final String LOG_TAG = "SmartRF";
   private final int STATUS_DEVICE_NOT_PROVISIONED = 142;
   private final int STATUS_INVALID_POLICY_KEY = 144;
   private final int STATUS_POLICY_REFRESH = 143;
   private final int STATUS_SERVER_ERROR = 110;
   private final int STATUS_SUCCESS = 1;
   private String collectionId;
   private int httpCode = 200;
   private boolean isReply;
   private boolean isSaveInSentItems;
   private boolean isSmartSend;
   private String itemId;
   private String msgId;


   public ComposeMailAdapter(EmailContent.Mailbox var1, EasSyncService var2, String var3, String var4, String var5, boolean var6, boolean var7, boolean var8) {
      super(var1, var2);
      this.msgId = var3;
      this.itemId = var4;
      this.collectionId = var5;
      this.isSaveInSentItems = var6;
      this.isSmartSend = var7;
      this.isReply = var8;
   }

   public static void log(String var0) {
      if(Eas.USER_LOG) {
         int var1 = Log.d("SmartRF", var0);
      }
   }

   public void callback(int var1) {}

   public void cleanup() {}

   public boolean commit() throws IOException {
      return false;
   }

   public String getCollectionName() {
      return null;
   }

   public String getCommandName() {
      return "SmartReply/SmartForward";
   }

   public int getHttpCode() {
      return this.httpCode;
   }

   public boolean hasChangedItems() {
      return true;
   }

   public boolean isSyncable() {
      return false;
   }

   public boolean parse(InputStream var1) throws IOException, DeviceAccessException {
      log("Entering parse");
      return (new ComposeMailAdapter.SmartReplyForwardParser(var1, this)).parse();
   }

   public boolean sendLocalChanges(Serializer var1) throws IOException {
      log("Enter sendLocalChanges");
      StringBuilder var2 = (new StringBuilder()).append("msgId: ");
      String var3 = this.msgId;
      log(var2.append(var3).toString());
      StringBuilder var4 = (new StringBuilder()).append("itemId: ");
      String var5 = this.itemId;
      log(var4.append(var5).toString());
      StringBuilder var6 = (new StringBuilder()).append("collectionId: ");
      String var7 = this.collectionId;
      log(var6.append(var7).toString());
      boolean var19;
      if(this.msgId != null) {
         short var8;
         if(this.isSmartSend) {
            if(this.isReply) {
               var8 = 1351;
            } else {
               var8 = 1350;
            }
         } else {
            var8 = 1349;
         }

         Serializer var9 = var1.start(var8);
         String var10 = this.msgId;
         var9.data(1361, var10);
         if(this.isSaveInSentItems) {
            Serializer var12 = var1.data(1352, "");
         }

         if(this.isSmartSend) {
            Serializer var13 = var1.start(1355);
            String var14 = this.collectionId;
            Serializer var15 = var13.data(1356, var14);
            String var16 = this.itemId;
            Serializer var17 = var15.data(1357, var16).end();
         }

         Serializer var18 = var1.data(1360, "").end();
         log("Exit 0 sendLocalChanges");
         var19 = true;
      } else {
         log("Exit 1 sendLocalChanges");
         var19 = false;
      }

      return var19;
   }

   public void setHttpCode(int var1) {
      this.httpCode = var1;
   }

   public class SmartReplyForwardParser extends AbstractSyncParser {

      public SmartReplyForwardParser(InputStream var2, AbstractCommandAdapter var3) throws IOException {
         super(var2, var3);
      }

      public void commandsParser() throws IOException {}

      public void commit() throws IOException {}

      public void itemOperationsResponsesParser() throws IOException {}

      public void moveResponseParser() throws IOException {}

      public boolean parse() throws IOException, DeviceAccessException {
         short var1;
         if(ComposeMailAdapter.this.isSmartSend) {
            if(ComposeMailAdapter.this.isReply) {
               var1 = 1351;
            } else {
               var1 = 1350;
            }
         } else {
            var1 = 1349;
         }

         if(this.nextTag(0) != var1) {
            throw new Parser.EasParserException();
         } else {
            while(this.nextTag(0) != 3) {
               if(this.tag == 1362) {
                  int var2 = this.getValueInt();
                  String var3 = "status: " + var2;
                  this.log(var3);
                  switch(var2) {
                  case 1:
                     int var5 = ComposeMailAdapter.this.httpCode = 200;
                     break;
                  case 120:
                  case 129:
                     int var6 = Log.i("SmartReplyForwardParser", "Status code 129 received, to block the device");
                     throw new DeviceAccessException(262145, 2131166810);
                  case 142:
                  case 143:
                  case 144:
                     int var7 = ComposeMailAdapter.this.httpCode = 449;
                  case 110:
                     int var8 = ComposeMailAdapter.this.httpCode = 500;
                  default:
                     int var4 = ComposeMailAdapter.this.httpCode = 400;
                  }
               }
            }

            StringBuilder var9 = (new StringBuilder()).append("httpCode mapped for 14.0: ");
            int var10 = ComposeMailAdapter.this.httpCode;
            String var11 = var9.append(var10).toString();
            this.log(var11);
            return true;
         }
      }

      public void responsesParser() throws IOException {}

      public void wipe() {}
   }
}
