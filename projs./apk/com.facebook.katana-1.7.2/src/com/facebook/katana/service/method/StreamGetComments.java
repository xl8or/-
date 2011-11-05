package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class StreamGetComments extends ApiMethod implements ApiMethodListener {

   private final List<FacebookPost.Comment> mComments;


   public StreamGetComments(Context var1, Intent var2, String var3, String var4, ApiMethodListener var5) {
      String var6 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "stream.getComments", var6, var5);
      Map var11 = this.mParams;
      StringBuilder var12 = (new StringBuilder()).append("");
      long var13 = System.currentTimeMillis();
      String var15 = var12.append(var13).toString();
      var11.put("call_id", var15);
      this.mParams.put("session_key", var3);
      this.mParams.put("post_id", var4);
      ArrayList var19 = new ArrayList();
      this.mComments = var19;
   }

   // $FF: synthetic method
   static void access$001(StreamGetComments var0, int var1, String var2, Exception var3) {
      var0.onHttpComplete(var1, var2, var3);
   }

   public List<FacebookPost.Comment> getComments() {
      return this.mComments;
   }

   protected void onHttpComplete(int var1, String var2, Exception var3) {
      if(var1 != 200) {
         super.onHttpComplete(var1, var2, var3);
      } else {
         HashMap var4 = new HashMap();
         Iterator var5 = this.mComments.iterator();

         while(var5.hasNext()) {
            Long var6 = Long.valueOf(((FacebookPost.Comment)var5.next()).fromId);
            var4.put(var6, (Object)null);
         }

         Context var8 = this.mContext;
         Intent var9 = this.mReqIntent;
         String var10 = (String)this.mParams.get("session_key");
         (new FqlGetProfile(var8, var9, var10, this, var4)).start();
      }
   }

   public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
      if(var2 == 200) {
         Map var5 = ((FqlGetProfile)var1).getProfiles();
         Iterator var6 = this.mComments.iterator();

         while(var6.hasNext()) {
            FacebookPost.Comment var7 = (FacebookPost.Comment)var6.next();
            Long var8 = Long.valueOf(var7.fromId);
            FacebookProfile var9 = (FacebookProfile)var5.get(var8);
            if(var9 == null) {
               long var10 = var7.fromId;
               String var12 = this.mContext.getString(2131361895);
               FacebookProfile var13 = new FacebookProfile(var10, var12, (String)null, 0);
               var7.setProfile(var13);
            } else {
               String var14 = var9.mDisplayName;
               if(var14 == null || var14.length() == 0) {
                  String var15 = this.mContext.getString(2131361895);
               }

               var7.setProfile(var9);
            }
         }
      }

      access$001(this, var2, var3, var4);
   }

   public void onOperationProgress(ApiMethod var1, long var2, long var4) {}

   public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {}

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
                  return;
               }

               JsonToken var6 = JsonToken.START_OBJECT;
               if(var2 == var6) {
                  List var7 = this.mComments;
                  FacebookPost.Comment var8 = FacebookPost.Comment.parseJson(var1);
                  var7.add(var8);
               }

               var2 = var1.nextToken();
            }
         } else {
            throw new IOException("Malformed JSON");
         }
      }
   }
}
