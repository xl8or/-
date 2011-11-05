package com.facebook.katana.service.method;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.FacebookStreamContainer;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookStreamType;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class StreamPublish extends ApiMethod implements ApiMethodCallback {

   public static final String ACTOR_PARAM = "uid";
   public static final String PLACE_PARAM = "place";
   public static final String PRIVACY_PARAM = "privacy";
   private static final String TAG = com.facebook.katana.util.Utils.getClassName(StreamPublish.class);
   public final String formattedMessage;
   private final FacebookProfile mActor;
   private String mPostId;
   public final String rawMessage;
   public final Set<FacebookProfile> tagged;
   public final long targetId;
   public final boolean updateWidget;


   public StreamPublish(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, String var7, String var8, Set<FacebookProfile> var9, Set<Long> var10, Long var11, FacebookProfile var12, String var13, boolean var14) {
      String var15 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "stream.publish", var15, var4);
      this.targetId = var5;
      this.rawMessage = var7;
      this.formattedMessage = var8;
      if(var9 != null) {
         Set var22 = Collections.unmodifiableSet(var9);
         this.tagged = var22;
      } else {
         this.tagged = null;
      }

      this.updateWidget = var14;
      this.mActor = var12;
      Map var25 = this.mParams;
      String var26 = String.valueOf(System.currentTimeMillis());
      var25.put("call_id", var26);
      this.mParams.put("session_key", var3);
      Map var29 = this.mParams;
      String var30 = String.valueOf(var5);
      var29.put("target_id", var30);
      Map var32 = this.mParams;
      String var33 = "message";
      var32.put(var33, var7);
      if(var10 != null) {
         Map var36 = this.mParams;
         String var39 = this.formatTaggedIds(var10);
         var36.put("tags", var39);
      }

      if(var11 != null) {
         Map var41 = this.mParams;
         String var42 = String.valueOf(var11);
         var41.put("place", var42);
      }

      if(var12 != null) {
         Map var44 = this.mParams;
         String var45 = String.valueOf(var12.mId);
         var44.put("uid", var45);
      }

      if(var13 != null) {
         Map var47 = this.mParams;
         String var48 = "privacy";
         var47.put(var48, var13);
      }
   }

   public static String Publish(Context var0, long var1, String var3, String var4, Set<Long> var5, Long var6, String var7, boolean var8, FacebookProfile var9) {
      byte var11 = 0;
      AppSession var12 = AppSession.getActiveSession(var0, (boolean)var11);
      String var13 = var12.getSessionInfo().sessionKey;
      StreamPublish var24 = new StreamPublish(var0, (Intent)null, var13, (ApiMethodListener)null, var1, var3, var4, (Set)null, var5, var6, var9, var7, var8);
      return var12.postToService(var0, var24, 1001, 1020, (Bundle)null);
   }

   public static String Publish(Context var0, long var1, String var3, String var4, Set<FacebookProfile> var5, boolean var6, FacebookProfile var7) {
      byte var9 = 0;
      AppSession var10 = AppSession.getActiveSession(var0, (boolean)var9);
      String var11 = var10.getSessionInfo().sessionKey;
      StreamPublish var20 = new StreamPublish(var0, (Intent)null, var11, (ApiMethodListener)null, var1, var3, var4, var5, (Set)null, (Long)null, var7, (String)null, var6);
      return var10.postToService(var0, var20, 1001, 1020, (Bundle)null);
   }

   private String formatTaggedIds(Set<Long> var1) {
      StringBuilder var2 = new StringBuilder("[");
      Object[] var3 = new Object[]{var1};
      String var4 = StringUtils.join(",", var3);
      var2.append(var4);
      StringBuilder var6 = var2.append("]");
      return var2.toString();
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      FacebookSessionInfo var8 = var1.getSessionInfo();
      FacebookPost var9 = new FacebookPost;
      String var10 = this.mPostId;
      long var11 = var8.userId;
      long var13 = this.targetId;
      long var15 = var8.userId;
      long var17;
      if(var13 != var15) {
         var17 = this.targetId;
      }

      String var19 = this.formattedMessage;
      Set var20 = this.tagged;
      int var22 = 2131362463;
      String var23 = var2.getString(var22);
      var9.<init>(var10, 350685531728L, var11, var17, var19, (FacebookPost.Attachment)null, (Set)null, var20, var23);
      if(this.mActor == null) {
         long var24 = var8.userId;
         String var26 = var8.getProfile().mDisplayName;
         String var27 = var8.getProfile().mImageUrl;
         FacebookProfile var28 = new FacebookProfile(var24, var26, var27, 0);
         var9.setProfile(var28);
      } else {
         FacebookProfile var61 = this.mActor;
         var9.setProfile(var61);
      }

      short var30 = 200;
      if(var5 == var30) {
         long var31 = this.targetId;
         long var33 = var1.getSessionInfo().userId;
         if(var31 == var33) {
            FacebookStreamType var35 = FacebookStreamType.NEWSFEED_STREAM;
            long var37 = 65535L;
            FacebookStreamContainer var40 = var1.getStreamContainer(var37, var35);
            if(var40 != null) {
               var40.insertFirst(var9);
            }
         }

         long var43 = this.targetId;
         FacebookStreamType var45 = FacebookStreamType.PROFILE_WALL_STREAM;
         FacebookStreamContainer var50 = var1.getStreamContainer(var43, var45);
         if(var50 != null) {
            var50.insertFirst(var9);
         }
      }

      Iterator var53 = var1.getListeners().iterator();

      while(var53.hasNext()) {
         AppSessionListener var54 = (AppSessionListener)var53.next();
         var54.onStreamPublishComplete(var1, var4, var5, var6, var7, var9);
      }

      if(this.updateWidget) {
         try {
            Intent var62 = new Intent();
            String var64 = "com.facebook.katana.widget.publish.result";
            var62.setAction(var64);
            String var67 = "extra_error_code";
            var62.putExtra(var67, var5);
            byte var71 = 0;
            byte var73 = 0;
            PendingIntent.getBroadcast(var2, var71, var62, var73).send();
         } catch (Exception var78) {
            String var75 = TAG;
            String var76 = "widget update failed: ";
            Log.e(var75, var76, var78);
         }
      }
   }

   public String getPostId() {
      return this.mPostId;
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      } else {
         String var3 = removeChar(var1, '\"');
         this.mPostId = var3;
      }
   }
}
