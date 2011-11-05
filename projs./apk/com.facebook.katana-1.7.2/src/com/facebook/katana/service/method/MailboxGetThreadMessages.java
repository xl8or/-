package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookMailboxThreadMessage;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class MailboxGetThreadMessages extends FqlQuery {

   private final int mFolder;
   private final List<FacebookMailboxThreadMessage> mMessages;
   private final long mThreadId;


   public MailboxGetThreadMessages(Context var1, Intent var2, String var3, int var4, long var5, int var7, int var8, ApiMethodListener var9) {
      String var10 = buildQuery(var4, var5, var7, var8);
      super(var1, var2, var3, var10, var9);
      this.mFolder = var4;
      ArrayList var16 = new ArrayList();
      this.mMessages = var16;
      this.mThreadId = var5;
   }

   private static String buildQuery(int var0, long var1, int var3, int var4) {
      if(var3 == -1) {
         var3 = 0;
      }

      if(var4 == -1) {
         var4 = 100;
      }

      return "SELECT message_id,author_id,created_time,body,attachment FROM message WHERE (thread_id=" + var1 + ")" + " ORDER BY created_time DESC" + " LIMIT " + var3 + "," + var4;
   }

   private void saveMessages() {
      ContentValues[] var1 = new ContentValues[this.mMessages.size()];
      int var2 = 0;
      Iterator var3 = this.mMessages.iterator();

      while(var3.hasNext()) {
         FacebookMailboxThreadMessage var4 = (FacebookMailboxThreadMessage)var3.next();
         ContentValues var5 = new ContentValues();
         var1[var2] = var5;
         ContentValues var6 = var1[var2];
         ++var2;
         Integer var7 = Integer.valueOf(this.mFolder);
         var6.put("folder", var7);
         Long var8 = Long.valueOf(var4.getThreadId());
         var6.put("tid", var8);
         Long var9 = Long.valueOf(var4.getMessageId());
         var6.put("mid", var9);
         Long var10 = Long.valueOf(var4.getAuthorId());
         var6.put("author_id", var10);
         Long var11 = Long.valueOf(var4.getTimeSent());
         var6.put("sent", var11);
         String var12 = var4.getBody();
         var6.put("body", var12);
      }

      ContentResolver var13 = this.mContext.getContentResolver();
      Uri var14 = MailboxProvider.MESSAGES_CONTENT_URI;
      var13.bulkInsert(var14, var1);
   }

   public List<FacebookMailboxThreadMessage> getMessages() {
      return this.mMessages;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         throw new FacebookApiException(var1);
      } else {
         JsonToken var4 = JsonToken.START_ARRAY;
         if(var2 == var4) {
            while(true) {
               JsonToken var5 = JsonToken.END_ARRAY;
               if(var2 == var5) {
                  this.saveMessages();
                  return;
               }

               JsonToken var6 = JsonToken.START_OBJECT;
               if(var2 == var6) {
                  List var7 = this.mMessages;
                  long var8 = this.mThreadId;
                  FacebookMailboxThreadMessage var10 = new FacebookMailboxThreadMessage(var1, var8);
                  var7.add(var10);
               }

               var2 = var1.nextToken();
            }
         } else {
            throw new IOException("Malformed JSON");
         }
      }
   }
}
