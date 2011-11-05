package com.facebook.katana.model;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.ProfileTabHostActivity;
import com.facebook.katana.activity.events.EventDetailsActivity;
import com.facebook.katana.activity.feedback.FeedbackActivity;
import com.facebook.katana.activity.media.PhotoFeedbackActivity;
import com.facebook.katana.provider.EventsProvider;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FacebookNotification extends JMCachingDictDestination {

   public static final String EVENT_TYPE = "event";
   public static final String GROUP_TYPE = "group";
   public static final String PHOTO_TYPE = "photo";
   public static final String PROFILE_TYPE = "friend";
   public static final String STREAM_TYPE = "stream";
   @JMAutogen.InferredType(
      jsonFieldName = "app_id"
   )
   private final long mAppId = 0L;
   @JMAutogen.InferredType(
      jsonFieldName = "body_text"
   )
   private final String mBody = null;
   @JMAutogen.InferredType(
      jsonFieldName = "created_time"
   )
   private final long mCreatedTime = 0L;
   @JMAutogen.InferredType(
      jsonFieldName = "mobile_href"
   )
   private final String mHref = null;
   @JMAutogen.InferredType(
      jsonFieldName = "is_unread"
   )
   private final boolean mIsUnread = 0;
   @JMAutogen.InferredType(
      jsonFieldName = "notification_id"
   )
   private final long mNotificationId = 0L;
   @JMAutogen.InferredType(
      jsonFieldName = "object_id"
   )
   private final String mObjectId = null;
   @JMAutogen.InferredType(
      jsonFieldName = "object_type"
   )
   private final String mObjectType = null;
   @JMAutogen.InferredType(
      jsonFieldName = "sender_id"
   )
   private final long mSenderId = 65535L;
   @JMAutogen.InferredType(
      jsonFieldName = "title_text"
   )
   private final String mTitle = null;


   private FacebookNotification() {}

   public static Intent getIntentForNotification(String var0, String var1, long var2, String var4, Activity var5) {
      Intent var7;
      if(var0 == null) {
         String var6 = "Null object type for: " + var4;
         Log.v("Notifications", var6);
         var7 = null;
      } else if(var0.equals("stream")) {
         Intent var8 = new Intent(var5, FeedbackActivity.class);
         var8.putExtra("extra_post_id", var1);
         var8.putExtra("extra_uid", var2);
         var7 = var8;
      } else if(var0.equals("photo")) {
         Intent var11 = new Intent(var5, PhotoFeedbackActivity.class);
         Uri var12 = Uri.withAppendedPath(PhotosProvider.PHOTOS_PID_CONTENT_URI, var1);
         var11.setData(var12);
         var7 = var11;
      } else if(var0.equals("friend")) {
         long var14 = Long.parseLong(var1);
         var7 = ProfileTabHostActivity.intentForProfile(var5, var14);
      } else if(var0.equals("event")) {
         Intent var16 = new Intent(var5, EventDetailsActivity.class);
         Uri var17 = Uri.withAppendedPath(EventsProvider.EVENT_EID_CONTENT_URI, var1);
         var16.setData(var17);
         var7 = var16;
      } else if(var0.equals("group")) {
         long var19 = Long.parseLong(var1);
         Intent var21 = ProfileTabHostActivity.intentForProfile(var5, var19);
         Intent var22 = var21.putExtra("extra_user_type", 3);
         var7 = var21;
      } else {
         var7 = null;
      }

      return var7;
   }

   public static FacebookNotification parseJson(JsonParser var0) throws JsonParseException, IOException, JMException {
      return (FacebookNotification)JMParser.parseObjectJson(var0, FacebookNotification.class);
   }

   public long getAppId() {
      return this.mAppId;
   }

   public String getBody() {
      return this.mBody;
   }

   public long getCreatedTime() {
      return this.mCreatedTime;
   }

   public String getHRef() {
      return this.mHref;
   }

   public long getNotificationId() {
      return this.mNotificationId;
   }

   public String getObjectId() {
      return this.mObjectId;
   }

   public String getObjectType() {
      return this.mObjectType;
   }

   public long getSenderId() {
      return this.mSenderId;
   }

   public String getTitle() {
      return this.mTitle;
   }

   public boolean isRead() {
      boolean var1;
      if(!this.mIsUnread) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
