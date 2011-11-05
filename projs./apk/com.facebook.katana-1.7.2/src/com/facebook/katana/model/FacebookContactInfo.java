package com.facebook.katana.model;

import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FacebookContactInfo {

   private final String mCellPhone;
   private final String mEmail;
   private final String mOtherPhone;
   private final long mUserId;


   public FacebookContactInfo(JsonParser var1) throws JsonParseException, IOException {
      long var2 = 65535L;
      String var4 = null;
      String var5 = null;
      String var6 = null;
      JsonToken var7 = var1.nextToken();

      while(true) {
         JsonToken var8 = JsonToken.END_OBJECT;
         if(var7 == var8) {
            this.mUserId = var2;
            String var12;
            if(var4 != null) {
               if(var4.length() > 0) {
                  var12 = var4;
               } else {
                  var12 = null;
               }
            } else {
               var12 = null;
            }

            this.mEmail = var12;
            String var13;
            if(var5 != null) {
               if(var5.length() > 0) {
                  var13 = var5;
               } else {
                  var13 = null;
               }
            } else {
               var13 = null;
            }

            this.mCellPhone = var13;
            String var14;
            if(var6 != null) {
               if(var6.length() > 0) {
                  var14 = var6;
               } else {
                  var14 = null;
               }
            } else {
               var14 = null;
            }

            this.mOtherPhone = var14;
            return;
         }

         JsonToken var9 = JsonToken.VALUE_STRING;
         if(var7 == var9) {
            String var10 = var1.getCurrentName();
            if(var10.equals("cell")) {
               var5 = var1.getText();
            } else if(var10.equals("phone")) {
               var6 = var1.getText();
            } else if(var10.equals("email")) {
               var4 = var1.getText();
            }
         } else {
            JsonToken var11 = JsonToken.VALUE_NUMBER_INT;
            if(var7 == var11 && var1.getCurrentName().equals("uid")) {
               var2 = var1.getLongValue();
            }
         }

         var7 = var1.nextToken();
      }
   }

   public String getCellPhone() {
      return this.mCellPhone;
   }

   public String getEmail() {
      return this.mEmail;
   }

   public String getOtherPhone() {
      return this.mOtherPhone;
   }

   public long getUserId() {
      return this.mUserId;
   }
}
