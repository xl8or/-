package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookFriendInfo;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FqlSyncUsersQuery extends FqlQuery {

   private static final String FRIENDS_QUERY = "SELECT uid,first_name,last_name,name,pic_square,cell,other_phone,contact_email,birthday_date FROM user WHERE (uid IN (SELECT uid2 FROM friend WHERE uid1=%1));";
   private final List<FacebookFriendInfo> mFriends;


   public FqlSyncUsersQuery(Context var1, Intent var2, String var3, long var4, ApiMethodListener var6) {
      String var7 = "" + var4;
      String var8 = "SELECT uid,first_name,last_name,name,pic_square,cell,other_phone,contact_email,birthday_date FROM user WHERE (uid IN (SELECT uid2 FROM friend WHERE uid1=%1));".replaceFirst("%1", var7);
      super(var1, var2, var3, var8, var6);
      ArrayList var14 = new ArrayList();
      this.mFriends = var14;
   }

   public List<FacebookFriendInfo> getFriends() {
      return this.mFriends;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         int var4 = -1;
         String var5 = null;
         var2 = var1.nextToken();

         while(true) {
            JsonToken var6 = JsonToken.END_OBJECT;
            if(var2 == var6) {
               if(var4 <= 0) {
                  return;
               }

               throw new FacebookApiException(var4, var5);
            }

            JsonToken var7 = JsonToken.VALUE_NUMBER_INT;
            if(var2 == var7) {
               if(var1.getCurrentName().equals("error_code")) {
                  var4 = var1.getIntValue();
               }
            } else {
               JsonToken var8 = JsonToken.VALUE_STRING;
               if(var2 == var8 && var1.getCurrentName().equals("error_msg")) {
                  var5 = var1.getText();
               }
            }

            var2 = var1.nextToken();
         }
      } else {
         JsonToken var9 = JsonToken.START_ARRAY;
         if(var2 == var9) {
            while(true) {
               JsonToken var10 = JsonToken.END_ARRAY;
               if(var2 == var10) {
                  return;
               }

               JsonToken var11 = JsonToken.START_OBJECT;
               if(var2 == var11) {
                  List var12 = this.mFriends;
                  FacebookFriendInfo var13 = (FacebookFriendInfo)FacebookUser.parseFromJSON(FacebookFriendInfo.class, var1);
                  var12.add(var13);
               }

               var2 = var1.nextToken();
            }
         }
      }
   }
}
