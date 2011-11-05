package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookMailboxThread;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class MailboxGetThreads extends FqlQuery {

   List<FacebookMailboxThread> mResult;


   public MailboxGetThreads(Context var1, Intent var2, String var3, int var4, int var5, int var6, ApiMethodListener var7) {
      String var8 = buildQuery(var4, var5, var6);
      super(var1, var2, var3, var8, var7);
      ArrayList var14 = new ArrayList();
      this.mResult = var14;
   }

   private static String buildQuery(int var0, int var1, int var2) {
      if(var1 == -1) {
         var1 = 0;
      }

      if(var2 == -1) {
         var2 = 20;
      }

      return "SELECT thread_id,subject,recipients,updated_time,message_count,snippet,snippet_author,unread,object_id FROM thread WHERE (folder_id=" + var0 + ") LIMIT " + var1 + "," + var2;
   }

   List<FacebookMailboxThread> getMailboxThreads() {
      return this.mResult;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         FacebookApiException var4 = new FacebookApiException(var1);
         if(var4.getErrorCode() != -1) {
            throw var4;
         }
      } else {
         JsonToken var5 = JsonToken.START_ARRAY;
         if(var2 == var5) {
            while(true) {
               JsonToken var6 = JsonToken.END_ARRAY;
               if(var2 == var6) {
                  return;
               }

               JsonToken var7 = JsonToken.START_OBJECT;
               if(var2 == var7) {
                  List var8 = this.mResult;
                  FacebookMailboxThread var9 = new FacebookMailboxThread(var1);
                  var8.add(var9);
               }

               var2 = var1.nextToken();
            }
         } else {
            throw new IOException("Malformed JSON");
         }
      }
   }
}
