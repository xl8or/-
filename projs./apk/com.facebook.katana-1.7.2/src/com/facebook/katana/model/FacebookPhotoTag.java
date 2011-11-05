package com.facebook.katana.model;

import com.facebook.katana.model.FacebookPhotoTagBase;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONObject;

public class FacebookPhotoTag extends FacebookPhotoTagBase {

   private final long mCreated;
   private final String mPhotoId;
   private final String mSubject;
   private final long mUserId;
   private final double mX;
   private final double mY;


   public FacebookPhotoTag(String var1, String var2, long var3, double var5, double var7, long var9) {
      this.mPhotoId = var1;
      this.mSubject = var2;
      this.mUserId = var3;
      this.mX = var5;
      this.mY = var7;
      this.mCreated = var9;
   }

   public FacebookPhotoTag(JsonParser var1) throws JsonParseException, IOException {
      String var2 = null;
      String var3 = null;
      long var4 = 65535L;
      long var6 = 65535L;
      double var8 = 0.0D;
      double var10 = 0.0D;
      JsonToken var12 = var1.nextToken();

      while(true) {
         JsonToken var13 = JsonToken.END_OBJECT;
         if(var12 == var13) {
            this.mPhotoId = var2;
            this.mSubject = var3;
            this.mUserId = var4;
            this.mX = var8;
            this.mY = var10;
            this.mCreated = var6;
            return;
         }

         JsonToken var14 = JsonToken.VALUE_STRING;
         String var15;
         if(var12 == var14) {
            var15 = var1.getCurrentName();
            if(var15.equals("pid")) {
               var2 = var1.getText();
            } else if(var15.equals("subject")) {
               var3 = var1.getText();
            }
         } else {
            JsonToken var16 = JsonToken.VALUE_NUMBER_INT;
            if(var12 == var16) {
               var15 = var1.getCurrentName();
               if(var15.equals("uid")) {
                  var4 = var1.getLongValue();
               } else if(var15.equals("created")) {
                  var6 = var1.getLongValue();
               }
            } else {
               JsonToken var17 = JsonToken.VALUE_NUMBER_FLOAT;
               if(var12 == var17) {
                  var15 = var1.getCurrentName();
                  if(var15.equals("xcoord")) {
                     var8 = (double)var1.getFloatValue();
                  } else if(var15.equals("ycoord")) {
                     var10 = (double)var1.getFloatValue();
                  }
               }
            }
         }

         var12 = var1.nextToken();
      }
   }

   public long getCreated() {
      return this.mCreated;
   }

   public String getPhotoId() {
      return this.mPhotoId;
   }

   public String getSubject() {
      return this.mSubject;
   }

   public long getUid() {
      return this.mUserId;
   }

   public double getX() {
      return this.mX;
   }

   public double getY() {
      return this.mY;
   }

   public JSONObject toJSON() {
      // $FF: Couldn't be decompiled
   }
}
