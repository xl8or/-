package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.FacebookStreamContainer;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookCheckinDetails;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.service.method.FqlGetStream;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetPlaceCheckins extends FqlGetStream implements ApiMethodCallback {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final String TAG = "FqlGetStream";
   public final long mEndTime;
   public final int mLimit;
   public final int mOpType;
   public final FacebookPlace mPlace;
   public final long mStartTime;


   static {
      byte var0;
      if(!FqlGetPlaceCheckins.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   protected FqlGetPlaceCheckins(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, long var7, FacebookPlace var9, int var10, int var11) {
      long var12 = var9.mPageId;
      LinkedHashMap var22 = buildQueries(var1, var2, var3, var5, var7, var12, var10);
      super(var1, var2, var3, var4, var22);
      this.mStartTime = var5;
      this.mEndTime = var7;
      this.mPlace = var9;
      this.mLimit = var10;
      this.mOpType = var11;
   }

   public static String RequestPlaceCheckins(Context var0, long var1, long var3, FacebookPlace var5, int var6, int var7) {
      byte var9 = 0;
      AppSession var10 = AppSession.getActiveSession(var0, (boolean)var9);
      String var11 = var10.getSessionInfo().sessionKey;
      FqlGetPlaceCheckins var21 = new FqlGetPlaceCheckins(var0, (Intent)null, var11, (ApiMethodListener)null, var1, var3, var5, var6, var7);
      Bundle var22 = new Bundle();
      long var23 = var5.mPageId;
      String var26 = "subject";
      var22.putLong(var26, var23);
      return var10.postToService(var0, var21, 1001, 1020, var22);
   }

   protected static String buildPostsQuery(long var0) {
      StringBuilder var2 = new StringBuilder("post_id IN (SELECT post_id FROM checkin WHERE page_id=");
      var2.append(var0);
      StringBuilder var4 = var2.append(") ORDER BY created_time DESC");
      return var2.toString();
   }

   protected static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2, long var3, long var5, long var7, int var9) {
      LinkedHashMap var10 = new LinkedHashMap();
      String var11 = buildPostsQuery(var7);
      FqlGetStream.FqlGetPosts var20 = new FqlGetStream.FqlGetPosts(var0, var1, var2, (ApiMethodListener)null, var3, var5, var11, var9);
      var10.put("posts", var20);
      String var22 = profileWhereClause;
      FqlGetProfile var26 = new FqlGetProfile(var0, var1, var2, (ApiMethodListener)null, var22);
      String var28 = "profiles";
      var10.put(var28, var26);
      return var10;
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      FacebookStreamContainer var8 = null;
      List var9 = null;
      short var11 = 200;
      if(var5 == var11) {
         var9 = this.getPosts();
         Map var12 = var1.mPlacesActivityContainerMap;
         Long var13 = Long.valueOf(this.mPlace.mPageId);
         var8 = (FacebookStreamContainer)var12.get(var13);
         if(var8 == null) {
            FacebookStreamContainer var14 = new FacebookStreamContainer();
            Map var15 = var1.mPlacesActivityContainerMap;
            Long var16 = Long.valueOf(this.mPlace.mPageId);
            var15.put(var16, var14);
         }

         int var18 = this.mLimit;
         int var19 = this.mOpType;
         var8.addPosts(var9, var18, var19);
      }

      Iterator var20 = var1.getListeners().iterator();

      while(var20.hasNext()) {
         AppSessionListener var21 = (AppSessionListener)var20.next();
         long var22 = this.mPlace.mPageId;
         int var24 = this.mOpType;
         var21.onStreamGetComplete(var1, var4, var5, var6, var7, var22, var24, var8, var9);
      }

   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      Iterator var3 = this.getPosts().iterator();

      while(var3.hasNext()) {
         FacebookPost var4 = (FacebookPost)var3.next();
         if(var4.getPostType() == 4) {
            FacebookPost.Attachment var5 = var4.getAttachment();
            if(!$assertionsDisabled) {
               long var6 = var5.mCheckinDetails.mPageId;
               long var8 = this.mPlace.mPageId;
               if(var6 != var8) {
                  throw new AssertionError();
               }
            }

            FacebookCheckinDetails var10 = var5.mCheckinDetails;
            FacebookPlace var11 = this.mPlace;
            var10.setPlaceInfo(var11);
         }
      }

   }
}
