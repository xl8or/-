package com.facebook.katana.model;

import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookSessionInfo extends JMCachingDictDestination {

   public static final String FILTER_KEY = "filter";
   public static final String OAUTH_TOKEN_KEY = "access_token";
   public static final String PROFILE_KEY = "profile";
   public static final String SECRET_KEY = "secret";
   public static final String SESSION_KEY = "session_key";
   public static final String USERNAME_KEY = "username";
   public static final String USER_ID_KEY = "uid";
   @JMAutogen.InferredType(
      jsonFieldName = "filter"
   )
   private String mFilterKey;
   @JMAutogen.InferredType(
      jsonFieldName = "profile"
   )
   private FacebookUser mMyself;
   @JMAutogen.InferredType(
      jsonFieldName = "access_token"
   )
   public final String oAuthToken;
   @JMAutogen.InferredType(
      jsonFieldName = "session_key"
   )
   public final String sessionKey;
   @JMAutogen.InferredType(
      jsonFieldName = "secret"
   )
   public final String sessionSecret;
   @JMAutogen.InferredType(
      jsonFieldName = "uid"
   )
   public final long userId;
   @JMAutogen.InferredType(
      jsonFieldName = "username"
   )
   public final String username;


   protected FacebookSessionInfo() {
      this.username = null;
      this.sessionKey = null;
      this.sessionSecret = null;
      this.oAuthToken = null;
      this.userId = 65535L;
   }

   public FacebookSessionInfo(FacebookSessionInfo var1, String var2) {
      String var3 = var1.username;
      this.username = var3;
      String var4 = var1.sessionKey;
      this.sessionKey = var4;
      String var5 = var1.sessionSecret;
      this.sessionSecret = var5;
      this.oAuthToken = var2;
      long var6 = var1.userId;
      this.userId = var6;
      String var8 = var1.mFilterKey;
      this.mFilterKey = var8;
      FacebookUser var9 = var1.mMyself;
      this.mMyself = var9;
   }

   public static FacebookSessionInfo parseFromJson(String var0) throws JsonParseException, IOException, JMException {
      JsonParser var1 = (new JsonFactory()).createJsonParser(var0);
      JsonToken var2 = var1.nextToken();
      return (FacebookSessionInfo)JMParser.parseObjectJson(var1, FacebookSessionInfo.class);
   }

   public String getFilterKey() {
      return this.mFilterKey;
   }

   public FacebookUser getProfile() {
      return this.mMyself;
   }

   public void setFilterKey(String var1) {
      this.mFilterKey = var1;
   }

   public void setProfile(FacebookUser var1) {
      this.mMyself = var1;
   }

   public JSONObject toJSONObject() {
      JSONObject var17;
      JSONObject var1;
      try {
         var1 = new JSONObject();
         String var2 = this.username;
         var1.put("username", var2);
         long var4 = this.userId;
         var1.put("uid", var4);
         String var7 = this.sessionKey;
         var1.put("session_key", var7);
         String var9 = this.sessionSecret;
         var1.put("secret", var9);
         String var11 = this.oAuthToken;
         var1.put("access_token", var11);
         if(this.mMyself != null) {
            JSONObject var13 = this.mMyself.toJSONObject();
            var1.put("profile", var13);
         }

         if(this.mFilterKey != null) {
            String var15 = this.mFilterKey;
            var1.put("filter", var15);
         }
      } catch (JSONException var19) {
         var17 = new JSONObject();
         return var17;
      }

      var17 = var1;
      return var17;
   }
}
