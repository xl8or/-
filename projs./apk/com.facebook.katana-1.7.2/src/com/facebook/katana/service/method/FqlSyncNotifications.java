package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookApp;
import com.facebook.katana.model.FacebookNotification;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.NotificationsProvider;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetAppsProfile;
import com.facebook.katana.service.method.FqlGetNotifications;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlSyncNotifications extends FqlMultiQuery {

   private Map<Long, FacebookProfile> mAllProfiles;
   private Map<Long, FacebookApp> mApps;
   private final List<Long> mDeletedNotificationIds;
   private final List<FacebookNotification> mNewNotifications;
   private List<FacebookNotification> mNotifications;


   public FqlSyncNotifications(Context var1, Intent var2, String var3, long var4, ApiMethodListener var6) {
      LinkedHashMap var7 = buildQueries(var1, var2, var3, var4);
      super(var1, var2, var3, var7, var6);
      ArrayList var13 = new ArrayList();
      this.mNewNotifications = var13;
      ArrayList var14 = new ArrayList();
      this.mDeletedNotificationIds = var14;
   }

   public static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2, long var3) {
      LinkedHashMap var5 = new LinkedHashMap();
      ApiMethodListener var6 = (ApiMethodListener)false;
      FqlGetNotifications var12 = new FqlGetNotifications(var0, var1, var2, var3, var6);
      var5.put("notifications", var12);
      Object var17 = null;
      FqlGetAppsProfile var18 = new FqlGetAppsProfile(var0, var1, var2, (ApiMethodListener)var17, "app_id IN (select app_id FROM #notifications)");
      var5.put("apps", var18);
      Object var23 = null;
      FqlGetProfile var24 = new FqlGetProfile(var0, var1, var2, (ApiMethodListener)var23, "id IN (select sender_id FROM #notifications)");
      var5.put("profiles", var24);
      return var5;
   }

   private boolean detectSyncChanges() {
      HashMap var1 = new HashMap();
      Iterator var2 = this.mNotifications.iterator();

      while(var2.hasNext()) {
         FacebookNotification var3 = (FacebookNotification)var2.next();
         Long var4 = Long.valueOf(var3.getNotificationId());
         var1.put(var4, var3);
      }

      ContentResolver var6 = this.mContext.getContentResolver();
      Uri var7 = NotificationsProvider.CONTENT_URI;
      String[] var8 = FqlSyncNotifications.NotificationsQuery.PROJECTION;
      Cursor var9 = var6.query(var7, var8, (String)null, (String[])null, (String)null);
      if(var9.moveToFirst()) {
         do {
            Long var10 = Long.valueOf(var9.getLong(0));
            String var11 = var9.getString(2);
            String var12 = var9.getString(1);
            String var13 = var9.getString(3);
            FacebookNotification var14 = (FacebookNotification)var1.get(var10);
            if(var14 == null) {
               this.mDeletedNotificationIds.add(var10);
            } else {
               String var19 = var14.getObjectType();
               boolean var22 = isStringUpdated(var11, var19);
               String var23 = var14.getObjectId();
               boolean var24 = isStringUpdated(var12, var23);
               String var25 = var14.getTitle();
               boolean var28 = isStringUpdated(var13, var25);
               if(!var22 && !var24 && !var28) {
                  var1.remove(var10);
               } else {
                  this.mDeletedNotificationIds.add(var10);
               }
            }
         } while(var9.moveToNext());
      }

      var9.close();
      Iterator var16 = var1.values().iterator();

      while(var16.hasNext()) {
         FacebookNotification var17 = (FacebookNotification)var16.next();
         this.mNewNotifications.add(var17);
      }

      boolean var31;
      if(this.mNewNotifications.size() <= 0 && this.mDeletedNotificationIds.size() <= 0) {
         var31 = false;
      } else {
         var31 = true;
      }

      return var31;
   }

   private static boolean isStringUpdated(String var0, String var1) {
      boolean var2;
      if(var1 != null && (var0 == null || !var0.equals(var1))) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void updateContentProviders() {
      ContentResolver var1 = this.mContext.getContentResolver();
      if(this.mDeletedNotificationIds.size() > 0) {
         StringBuilder var2 = new StringBuilder(256);
         StringBuilder var3 = var2.append("notif_id").append(" IN(");
         Object[] var4 = new Object[1];
         List var5 = this.mDeletedNotificationIds;
         var4[0] = var5;
         StringUtils.join(var2, ",", (StringUtils.StringProcessor)null, var4);
         StringBuilder var6 = var2.append(')');
         Uri var7 = NotificationsProvider.CONTENT_URI;
         String var8 = var2.toString();
         var1.delete(var7, var8, (String[])null);
      }

      if(this.mNewNotifications.size() > 0) {
         int var10 = 0;
         ContentValues[] var11 = new ContentValues[this.mNewNotifications.size()];
         Iterator var12 = this.mNewNotifications.iterator();

         while(var12.hasNext()) {
            FacebookNotification var13 = (FacebookNotification)var12.next();
            ContentValues var14 = new ContentValues();
            var11[var10] = var14;
            ++var10;
            String var15 = var13.getTitle();
            var14.put("title", var15);
            String var16 = var13.getBody();
            var14.put("body", var16);
            Long var17 = Long.valueOf(var13.getCreatedTime());
            var14.put("created", var17);
            String var18 = var13.getHRef();
            var14.put("href", var18);
            Boolean var19 = Boolean.valueOf(var13.isRead());
            var14.put("is_read", var19);
            Long var20 = Long.valueOf(var13.getNotificationId());
            var14.put("notif_id", var20);
            Long var21 = Long.valueOf(var13.getSenderId());
            var14.put("sender_id", var21);
            Long var22 = Long.valueOf(var13.getAppId());
            var14.put("app_id", var22);
            String var23 = var13.getObjectId();
            var14.put("object_id", var23);
            String var24 = var13.getObjectType();
            var14.put("object_type", var24);
            Map var25 = this.mApps;
            Long var26 = Long.valueOf(var13.getAppId());
            FacebookApp var27 = (FacebookApp)var25.get(var26);
            if(var27 != null && var27.mImageUrl != null) {
               String var28 = var27.mImageUrl;
               var14.put("app_image", var28);
            }

            Long var29 = Long.valueOf(var13.getAppId());
            var14.put("app_id", var29);
            Map var30 = this.mAllProfiles;
            Long var31 = Long.valueOf(var13.getSenderId());
            FacebookProfile var32 = (FacebookProfile)var30.get(var31);
            if(var32 != null) {
               String var33 = var32.mDisplayName;
               var14.put("sender_name", var33);
               String var34 = var32.mImageUrl;
               var14.put("sender_url", var34);
            }
         }

         Uri var35 = NotificationsProvider.CONTENT_URI;
         var1.bulkInsert(var35, var11);
      }
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      FqlGetNotifications var3 = (FqlGetNotifications)this.getQueryByName("notifications");
      FqlGetProfile var4 = (FqlGetProfile)this.getQueryByName("profiles");
      FqlGetAppsProfile var5 = (FqlGetAppsProfile)this.getQueryByName("apps");
      List var6 = var3.getNotifications();
      this.mNotifications = var6;
      Map var7 = var4.getProfiles();
      this.mAllProfiles = var7;
      Map var8 = var5.getApps();
      this.mApps = var8;
      if(this.detectSyncChanges()) {
         this.updateContentProviders();
      }
   }

   private interface NotificationsQuery {

      int INDEX_NOTIFICATION_ID = 0;
      int INDEX_OBJECT_ID = 1;
      int INDEX_OBJECT_TYPE = 2;
      int INDEX_TITLE = 3;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"notif_id", "object_id", "object_type", "title"};
         PROJECTION = var0;
      }
   }
}
