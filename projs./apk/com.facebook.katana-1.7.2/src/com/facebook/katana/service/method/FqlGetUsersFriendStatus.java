package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FqlGetUsersFriendStatus extends FqlQuery {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   private boolean mFriends;
   private long uid1;
   private long uid2;


   static {
      byte var0;
      if(!FqlGetUsersFriendStatus.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public FqlGetUsersFriendStatus(Context var1, Intent var2, String var3, long var4, long var6, ApiMethodListener var8) {
      String var9 = "SELECT uid1, uid2 FROM friend WHERE uid1=" + var4 + " AND uid2=" + var6;
      super(var1, var2, var3, var9, var8);
      this.uid1 = var4;
      this.uid2 = var6;
   }

   public boolean areFriends() {
      return this.mFriends;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      JsonToken var2 = var1.getCurrentToken();
      Long var3 = null;
      Long var4 = null;
      JsonToken var5 = JsonToken.START_ARRAY;
      if(var2 != var5) {
         throw new IOException("Unexpected JSON response");
      } else {
         JsonToken var6 = var1.nextToken();
         JsonToken var7 = JsonToken.END_ARRAY;
         if(var6 != var7) {
            if(!$assertionsDisabled) {
               JsonToken var17 = JsonToken.START_OBJECT;
               if(var6 != var17) {
                  throw new AssertionError();
               }
            }

            var6 = var1.nextToken();

            label60:
            while(true) {
               JsonToken var18 = JsonToken.END_OBJECT;
               if(var6 == var18) {
                  while(true) {
                     JsonToken var23 = JsonToken.END_ARRAY;
                     if(var6 == var23) {
                        break label60;
                     }

                     label53: {
                        JsonToken var24 = JsonToken.START_ARRAY;
                        if(var6 != var24) {
                           JsonToken var25 = JsonToken.START_OBJECT;
                           if(var6 != var25) {
                              break label53;
                           }
                        }

                        var1.skipChildren();
                        if(!$assertionsDisabled) {
                           JsonToken var26 = var1.getCurrentToken();
                           JsonToken var27 = JsonToken.END_ARRAY;
                           if(var26 != var27) {
                              JsonToken var28 = var1.getCurrentToken();
                              JsonToken var29 = JsonToken.END_OBJECT;
                              if(var28 != var29) {
                                 throw new AssertionError();
                              }
                           }
                        }
                     }

                     JsonToken var22 = var1.nextToken();
                  }
               }

               JsonToken var19 = JsonToken.VALUE_STRING;
               if(var6 == var19) {
                  String var20 = var1.getCurrentName();
                  if(var20.equals("uid1")) {
                     var3 = Long.valueOf(var1.getText());
                  } else if(var20.equals("uid2")) {
                     var4 = Long.valueOf(var1.getText());
                  }
               }

               JsonToken var21 = var1.nextToken();
            }
         }

         JsonToken var8 = var1.nextToken();
         if(false && false) {
            long var9 = null.longValue();
            long var11 = this.uid1;
            if(var9 == var11) {
               long var13 = null.longValue();
               long var15 = this.uid2;
               if(var13 == var15) {
                  this.mFriends = (boolean)1;
                  return;
               }
            }
         }

         this.mFriends = (boolean)0;
      }
   }
}
