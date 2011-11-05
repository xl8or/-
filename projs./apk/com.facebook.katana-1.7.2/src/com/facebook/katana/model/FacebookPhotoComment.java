package com.facebook.katana.model;

import com.facebook.katana.model.FacebookProfile;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FacebookPhotoComment {

   private final String mBody;
   private FacebookProfile mFromProfile;
   private long mFromProfileId;
   private final long mId;
   private final String mPhotoId;
   private final long mTime;


   public FacebookPhotoComment(String param1, long param2, String param4, long param5, long param7) {
      // $FF: Couldn't be decompiled
   }

   public FacebookPhotoComment(JsonParser var1) throws JsonParseException, IOException {
      String var2 = null;
      long var3 = 65535L;
      String var5 = null;
      long var6 = 65535L;
      long var8 = 65535L;
      JsonToken var10 = var1.nextToken();

      while(true) {
         JsonToken var11 = JsonToken.END_OBJECT;
         if(var10 == var11) {
            this.mPhotoId = var2;
            this.mFromProfileId = var3;
            this.mBody = var5;
            this.mTime = var6;
            this.mId = var8;
            return;
         }

         JsonToken var12 = JsonToken.VALUE_STRING;
         String var13;
         if(var10 == var12) {
            var13 = var1.getCurrentName();
            if(var13.equals("pid")) {
               var2 = var1.getText();
            } else if(var13.equals("body")) {
               var5 = var1.getText();
            }
         } else {
            JsonToken var14 = JsonToken.VALUE_NUMBER_INT;
            if(var10 == var14) {
               var13 = var1.getCurrentName();
               if(var13.equals("from")) {
                  var3 = var1.getLongValue();
               } else if(var13.equals("time")) {
                  var6 = var1.getLongValue();
               } else if(var13.equals("pcid")) {
                  var8 = var1.getLongValue();
               }
            }
         }

         var10 = var1.nextToken();
      }
   }

   public String getBody() {
      return this.mBody;
   }

   public FacebookProfile getFromProfile() {
      return this.mFromProfile;
   }

   public long getFromProfileId() {
      return this.mFromProfileId;
   }

   public long getId() {
      return this.mId;
   }

   public String getPhotoId() {
      return this.mPhotoId;
   }

   public long getTime() {
      return this.mTime;
   }

   public void setFromProfile(FacebookProfile var1) {
      long var2 = var1.mId;
      this.mFromProfileId = var2;
      this.mFromProfile = var1;
   }
}
