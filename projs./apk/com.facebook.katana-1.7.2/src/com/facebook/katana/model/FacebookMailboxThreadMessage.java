package com.facebook.katana.model;

import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FacebookMailboxThreadMessage {

   private final long mAuthorUserId;
   private final String mBody;
   private final long mMessageId;
   private final long mThreadId;
   private final long mTimeSent;


   public FacebookMailboxThreadMessage(JsonParser var1) throws JsonParseException, IOException {
      this(var1, 65535L);
   }

   public FacebookMailboxThreadMessage(JsonParser var1, long var2) throws JsonParseException, IOException {
      long var4 = 65535L;
      long var6 = 65535L;
      long var8 = 65535L;
      String var10 = null;
      JsonToken var11 = var1.nextToken();

      while(true) {
         JsonToken var12 = JsonToken.END_OBJECT;
         if(var11 == var12) {
            this.mThreadId = var2;
            this.mMessageId = var4;
            this.mAuthorUserId = var6;
            this.mTimeSent = var8;
            this.mBody = var10;
            return;
         }

         JsonToken var13 = JsonToken.VALUE_STRING;
         String var14;
         if(var11 == var13) {
            var14 = var1.getCurrentName();
            if(var14.equals("body")) {
               var10 = var1.getText();
            } else if(var14.equals("message_id")) {
               String var15 = var1.getText();
               int var16 = var15.indexOf(95) + 1;
               var4 = Long.parseLong(var15.substring(var16));
            } else if(var14.equals("thread_id")) {
               var2 = Long.parseLong(var1.getText());
            }
         } else {
            JsonToken var17 = JsonToken.VALUE_NUMBER_INT;
            if(var11 == var17) {
               var14 = var1.getCurrentName();
               if(var14.equals("thread_id")) {
                  var2 = var1.getLongValue();
               } else if(var14.equals("author_id")) {
                  var6 = var1.getLongValue();
               } else if(var14.equals("created_time")) {
                  var8 = var1.getLongValue();
               }
            } else {
               JsonToken var18 = JsonToken.START_OBJECT;
               if(var11 == var18) {
                  int var19 = 1;

                  while(var19 > 0) {
                     var11 = var1.nextToken();
                     JsonToken var20 = JsonToken.START_OBJECT;
                     if(var11 == var20) {
                        ++var19;
                     } else {
                        JsonToken var21 = JsonToken.END_OBJECT;
                        if(var11 == var21) {
                           var19 += -1;
                        }
                     }
                  }
               }
            }
         }

         var11 = var1.nextToken();
      }
   }

   public long getAuthorId() {
      return this.mAuthorUserId;
   }

   public String getBody() {
      return this.mBody;
   }

   public long getMessageId() {
      return this.mMessageId;
   }

   public long getThreadId() {
      return this.mThreadId;
   }

   public long getTimeSent() {
      return this.mTimeSent;
   }
}
