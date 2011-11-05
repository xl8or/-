package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class MailboxMarkThread extends ApiMethod {

   private final int mFolder;
   private final boolean mRead;


   public MailboxMarkThread(Context var1, Intent var2, String var3, long var4, boolean var6, ApiMethodListener var7) {
      String var8;
      if(var6) {
         var8 = "mailbox.markRead";
      } else {
         var8 = "mailbox.markUnread";
      }

      String var9 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", var8, var9, var7);
      int var14 = var2.getIntExtra("folder", 0);
      this.mFolder = var14;
      Map var15 = this.mParams;
      StringBuilder var16 = (new StringBuilder()).append("");
      long var17 = System.currentTimeMillis();
      String var19 = var16.append(var17).toString();
      var15.put("call_id", var19);
      this.mParams.put("session_key", var3);
      Map var22 = this.mParams;
      String var23 = "" + var4;
      var22.put("tid", var23);
      this.mRead = var6;
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      } else {
         ContentValues var3 = new ContentValues();
         String var4 = "unread_count";
         byte var5;
         if(this.mRead) {
            var5 = 0;
         } else {
            var5 = 1;
         }

         Integer var6 = Integer.valueOf(var5);
         var3.put(var4, var6);
         String var7 = (String)this.mParams.get("tid");
         ContentResolver var8 = this.mContext.getContentResolver();
         Uri var9 = Uri.withAppendedPath(MailboxProvider.getThreadsTidFolderUri(this.mFolder), var7);
         var8.update(var9, var3, (String)null, (String[])null);
         boolean var11;
         byte var12;
         if(this.mFolder == 0) {
            var11 = true;
            var12 = 1;
         } else {
            int var15 = this.mFolder;
            if(1 == var15) {
               var11 = true;
               var12 = 0;
            } else {
               var11 = false;
               var12 = 0;
            }
         }

         if(var11) {
            Uri var13 = Uri.withAppendedPath(MailboxProvider.getThreadsTidFolderUri(var12), var7);
            var8.update(var13, var3, (String)null, (String[])null);
         }
      }
   }
}
