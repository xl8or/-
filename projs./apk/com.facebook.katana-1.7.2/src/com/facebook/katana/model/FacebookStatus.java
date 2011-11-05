package com.facebook.katana.model;

import com.facebook.katana.model.FacebookUser;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FacebookStatus {

   private final String mMessage;
   private final long mTime;
   private FacebookUser mUser;


   public FacebookStatus(FacebookUser var1, String var2, long var3) {
      this.mUser = var1;
      this.mMessage = var2;
      this.mTime = var3;
   }

   public FacebookStatus(JsonParser var1) throws JsonParseException, IOException {
      long var2 = 65535L;
      String var4 = null;
      String var5 = null;
      String var6 = null;
      String var7 = null;
      long var8 = 0L;
      String var10 = null;
      JsonToken var11 = var1.nextToken();

      while(true) {
         JsonToken var12 = JsonToken.END_OBJECT;
         if(var11 == var12) {
            if(var4 != null && var4.equals("null")) {
               var4 = null;
            }

            if(var5 != null && var5.equals("null")) {
               var5 = null;
            }

            FacebookUser var22 = new FacebookUser(var2, var4, var5, var6, var7);
            this.mUser = var22;
            if(var10 != null && var10.length() > 0) {
               this.mMessage = var10;
            } else {
               this.mMessage = null;
            }

            this.mTime = var8;
            return;
         }

         JsonToken var13 = JsonToken.VALUE_STRING;
         if(var11 == var13) {
            String var14 = var1.getCurrentName();
            if(var14.equals("first_name")) {
               var4 = var1.getText();
            } else if(var14.equals("last_name")) {
               var5 = var1.getText();
            } else if(var14.equals("name")) {
               var6 = var1.getText();
            } else if(var14.equals("pic_square")) {
               var7 = var1.getText();
               if(var7.length() == 0) {
                  var7 = null;
               }
            }
         } else {
            JsonToken var15 = JsonToken.VALUE_NUMBER_INT;
            if(var11 == var15) {
               if(var1.getCurrentName().equals("uid")) {
                  var2 = var1.getLongValue();
               }
            } else {
               JsonToken var16 = JsonToken.START_OBJECT;
               if(var11 == var16) {
                  if(false) {
                     if(null.equals("status")) {
                        while(true) {
                           JsonToken var17 = JsonToken.END_OBJECT;
                           if(var11 == var17) {
                              break;
                           }

                           JsonToken var18 = JsonToken.VALUE_STRING;
                           if(var11 == var18) {
                              if(var1.getCurrentName().equals("message")) {
                                 var10 = var1.getText();
                              }
                           } else {
                              JsonToken var19 = JsonToken.VALUE_NUMBER_INT;
                              if(var11 == var19 && var1.getCurrentName().equals("time")) {
                                 var8 = var1.getLongValue();
                              }
                           }

                           var11 = var1.nextToken();
                        }
                     } else {
                        var1.skipChildren();
                     }
                  }
               } else {
                  JsonToken var20 = JsonToken.FIELD_NAME;
                  if(var11 == var20) {
                     String var21 = var1.getText();
                  }
               }
            }
         }

         var11 = var1.nextToken();
      }
   }

   public String getMessage() {
      return this.mMessage;
   }

   public long getTime() {
      return this.mTime;
   }

   public FacebookUser getUser() {
      return this.mUser;
   }

   public void setUser(FacebookUser var1) {
      this.mUser = var1;
   }
}
