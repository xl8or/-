package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookFriendInfo;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.platform.PlatformStorage;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetProfileGeneric;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.service.method.FqlSyncUsersQuery;
import com.facebook.katana.util.PlatformUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ConnectionsSync extends ApiMethod implements ApiMethodListener {

   private final String mAccount;
   private boolean mCanceled;
   private final List<Long> mDeletedFriendIds;
   private final long mMyUserId;
   private final String mMyUsername;
   private final Map<Long, String> mNeedUserImageMap;
   private final List<FacebookFriendInfo> mNewFriends;
   private ConnectionsSync.FqlGetFriendsAndPages mOp;
   private final String mSessionKey;
   private final List<FacebookFriendInfo> mUpdatedFriends;


   public ConnectionsSync(Context var1, Intent var2, String var3, long var4, String var6, ApiMethodListener var7) {
      String var8 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", (String)null, var8, var7);
      this.mSessionKey = var3;
      this.mMyUserId = var4;
      this.mMyUsername = var6;
      HashMap var13 = new HashMap();
      this.mNeedUserImageMap = var13;
      ArrayList var14 = new ArrayList();
      this.mNewFriends = var14;
      ArrayList var15 = new ArrayList();
      this.mUpdatedFriends = var15;
      ArrayList var16 = new ArrayList();
      this.mDeletedFriendIds = var16;
      String var17 = var2.getStringExtra("un");
      this.mAccount = var17;
   }

   private boolean detectFriendsChanges(List<FacebookFriendInfo> var1) {
      HashMap var2 = new HashMap();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         FacebookFriendInfo var4 = (FacebookFriendInfo)var3.next();
         Long var5 = Long.valueOf(var4.mUserId);
         var2.put(var5, var4);
      }

      ContentResolver var7 = this.mContext.getContentResolver();
      Uri var8 = ConnectionsProvider.FRIENDS_CONTENT_URI;
      String[] var9 = ConnectionsSync.HashQuery.PROJECTION;
      Object var10 = null;
      Object var11 = null;
      Cursor var12 = var7.query(var8, var9, (String)null, (String[])var10, (String)var11);

      while(var12.moveToNext()) {
         Long var13 = Long.valueOf(var12.getLong(0));
         FacebookFriendInfo var14 = (FacebookFriendInfo)var2.get(var13);
         if(var14 == null) {
            this.mDeletedFriendIds.add(var13);
            var2.remove(var13);
         } else {
            long var17 = var14.computeHash();
            long var19 = var12.getLong(1);
            if(var17 != var19) {
               this.mUpdatedFriends.add(var14);
               var2.remove(var13);
            } else {
               var2.remove(var13);
            }
         }
      }

      var12.close();
      Iterator var24 = var2.values().iterator();

      while(var24.hasNext()) {
         FacebookFriendInfo var25 = (FacebookFriendInfo)var24.next();
         if(var25.mDisplayName != null) {
            this.mNewFriends.add(var25);
         }
      }

      boolean var27;
      if(this.mNewFriends.size() <= 0 && this.mUpdatedFriends.size() <= 0 && this.mDeletedFriendIds.size() <= 0) {
         var27 = false;
      } else {
         var27 = true;
      }

      return var27;
   }

   private boolean detectPagesChanges(Map<Long, ConnectionsSync.FacebookPageProfile> var1, List<ConnectionsSync.FacebookPageProfile> var2, List<ConnectionsSync.FacebookPageProfile> var3, List<Long> var4) {
      ContentResolver var5 = this.mContext.getContentResolver();
      Uri var6 = ConnectionsProvider.PAGES_CONTENT_URI;
      String[] var7 = ConnectionsSync.HashQuery.PROJECTION;
      Cursor var8 = var5.query(var6, var7, (String)null, (String[])null, (String)null);

      while(var8.moveToNext()) {
         long var9 = var8.getLong(0);
         Long var11 = Long.valueOf(var9);
         ConnectionsSync.FacebookPageProfile var14 = (ConnectionsSync.FacebookPageProfile)var1.get(var11);
         if(var14 == null) {
            Long var15 = Long.valueOf(var9);
            boolean var18 = var4.add(var15);
         } else {
            long var19 = var14.computeHash();
            long var21 = var8.getLong(1);
            if(var19 != var21) {
               boolean var25 = var3.add(var14);
               Long var26 = Long.valueOf(var9);
               Object var29 = var1.remove(var26);
            } else {
               Long var30 = Long.valueOf(var9);
               Object var33 = var1.remove(var30);
            }
         }
      }

      var8.close();
      Iterator var34 = var1.values().iterator();

      while(var34.hasNext()) {
         ConnectionsSync.FacebookPageProfile var35 = (ConnectionsSync.FacebookPageProfile)var34.next();
         if(var35.mDisplayName != null) {
            boolean var38 = var2.add(var35);
         }
      }

      boolean var39;
      if(var2.size() <= 0 && var3.size() <= 0 && var4.size() <= 0) {
         var39 = false;
      } else {
         var39 = true;
      }

      return var39;
   }

   private void syncFriends(List<FacebookFriendInfo> var1) {
      ContentResolver var2 = this.mContext.getContentResolver();
      if(PlatformUtils.platformStorageSupported(this.mContext)) {
         Context var3 = this.mContext;
         String var4 = this.mMyUsername;
         if(FacebookAuthenticationService.isSyncEnabled(var3, var4)) {
            Context var5 = this.mContext;
            String var6 = this.mAccount;
            Map var7 = this.mNeedUserImageMap;
            PlatformStorage.syncContacts(var5, var6, var1, var7);
         }
      }

      if(!this.mCanceled) {
         if(this.detectFriendsChanges(var1)) {
            if(!this.mCanceled) {
               Iterator var14;
               if(this.mNewFriends.size() > 0) {
                  int var12 = 0;
                  ContentValues[] var13 = new ContentValues[this.mNewFriends.size()];
                  var14 = this.mNewFriends.iterator();

                  while(var14.hasNext()) {
                     FacebookFriendInfo var15 = (FacebookFriendInfo)var14.next();
                     long var16 = var15.mUserId;
                     ContentValues var18 = new ContentValues();
                     var13[var12] = var18;
                     ++var12;
                     Long var19 = Long.valueOf(var16);
                     String var21 = "user_id";
                     var18.put(var21, var19);
                     String var23 = var15.mCellPhone;
                     String var25 = "cell";
                     var18.put(var25, var23);
                     String var27 = var15.mOtherPhone;
                     String var29 = "other";
                     var18.put(var29, var27);
                     String var31 = var15.mContactEmail;
                     String var33 = "email";
                     var18.put(var33, var31);
                     String var35 = var15.mFirstName;
                     String var37 = "first_name";
                     var18.put(var37, var35);
                     String var39 = var15.mLastName;
                     String var41 = "last_name";
                     var18.put(var41, var39);
                     String var43 = var15.mDisplayName;
                     String var45 = "display_name";
                     var18.put(var45, var43);
                     String var47 = var15.mImageUrl;
                     String var49 = "user_image_url";
                     var18.put(var49, var47);
                     Integer var51 = Integer.valueOf(var15.mBirthdayMonth);
                     String var53 = "birthday_month";
                     var18.put(var53, var51);
                     Integer var55 = Integer.valueOf(var15.mBirthdayDay);
                     String var57 = "birthday_day";
                     var18.put(var57, var55);
                     Integer var59 = Integer.valueOf(var15.mBirthdayYear);
                     String var61 = "birthday_year";
                     var18.put(var61, var59);
                     Long var63 = Long.valueOf(var15.computeHash());
                     String var65 = "hash";
                     var18.put(var65, var63);
                     if(var15.mImageUrl != null) {
                        Map var67 = this.mNeedUserImageMap;
                        Long var68 = Long.valueOf(var16);
                        String var69 = var15.mImageUrl;
                        var67.put(var68, var69);
                     }
                  }

                  Uri var71 = ConnectionsProvider.FRIENDS_CONTENT_URI;
                  int var75 = var2.bulkInsert(var71, var13);
               }

               if(this.mUpdatedFriends.size() > 0) {
                  ContentValues var76 = new ContentValues();
                  var14 = this.mUpdatedFriends.iterator();

                  while(var14.hasNext()) {
                     FacebookFriendInfo var77 = (FacebookFriendInfo)var14.next();
                     if(var77.mDisplayName != null) {
                        long var78 = var77.mUserId;
                        var76.clear();
                        String var80 = var77.mFirstName;
                        String var82 = "first_name";
                        var76.put(var82, var80);
                        String var84 = var77.mLastName;
                        String var86 = "last_name";
                        var76.put(var86, var84);
                        String var88 = var77.mDisplayName;
                        String var90 = "display_name";
                        var76.put(var90, var88);
                        String var92 = var77.mImageUrl;
                        String var94 = "user_image_url";
                        var76.put(var94, var92);
                        Integer var96 = Integer.valueOf(var77.mBirthdayMonth);
                        String var98 = "birthday_month";
                        var76.put(var98, var96);
                        Integer var100 = Integer.valueOf(var77.mBirthdayDay);
                        String var102 = "birthday_day";
                        var76.put(var102, var100);
                        Integer var104 = Integer.valueOf(var77.mBirthdayYear);
                        String var106 = "birthday_year";
                        var76.put(var106, var104);
                        Long var108 = Long.valueOf(var77.computeHash());
                        String var110 = "hash";
                        var76.put(var110, var108);
                        String var112 = var77.mCellPhone;
                        String var114 = "cell";
                        var76.put(var114, var112);
                        String var116 = var77.mOtherPhone;
                        String var118 = "other";
                        var76.put(var118, var116);
                        String var120 = var77.mContactEmail;
                        String var122 = "email";
                        var76.put(var122, var120);
                        Uri var124 = ConnectionsProvider.FRIEND_UID_CONTENT_URI;
                        StringBuilder var125 = (new StringBuilder()).append("");
                        String var128 = var125.append(var78).toString();
                        Uri var129 = Uri.withAppendedPath(var124, var128);
                        Object var133 = null;
                        Object var134 = null;
                        var2.update(var129, var76, (String)var133, (String[])var134);
                        if(var77.mImageUrl != null) {
                           Map var136 = this.mNeedUserImageMap;
                           Long var137 = Long.valueOf(var77.mUserId);
                           String var138 = var77.mImageUrl;
                           var136.put(var137, var138);
                        }
                     }
                  }
               }

               if(this.mDeletedFriendIds.size() > 0) {
                  StringBuffer var140 = new StringBuffer;
                  short var142 = 128;
                  var140.<init>(var142);
                  String var144 = "user_id";
                  StringBuffer var145 = var140.append(var144).append(" IN(");
                  boolean var146 = true;

                  Long var147;
                  for(var14 = this.mDeletedFriendIds.iterator(); var14.hasNext(); var140.append(var147)) {
                     var147 = (Long)var14.next();
                     if(var146) {
                        var146 = false;
                     } else {
                        String var150 = ",";
                        var140.append(var150);
                     }
                  }

                  String var153 = ")";
                  var140.append(var153);
                  Uri var155 = ConnectionsProvider.CONNECTIONS_CONTENT_URI;
                  String var156 = var140.toString();
                  Object var160 = null;
                  var2.delete(var155, var156, (String[])var160);
               }
            }
         }
      }
   }

   private void syncPages(Map<Long, ConnectionsSync.FacebookPageProfile> var1, Map<Long, ConnectionsSync.FacebookPageProfile> var2) {
      ContentResolver var3 = this.mContext.getContentResolver();
      HashMap var4 = new HashMap(var2);

      Iterator var7;
      Object var9;
      Object var10;
      Object var14;
      for(var7 = var1.entrySet().iterator(); var7.hasNext(); var14 = var4.put(var9, var10)) {
         Entry var8 = (Entry)var7.next();
         var9 = var8.getKey();
         var10 = var8.getValue();
      }

      if(!this.mCanceled) {
         ArrayList var15 = new ArrayList();
         ArrayList var16 = new ArrayList();
         ArrayList var17 = new ArrayList();
         if(this.detectPagesChanges(var4, var15, var16, var17)) {
            if(!this.mCanceled) {
               if(var15.size() > 0) {
                  int var23 = 0;
                  ContentValues[] var24 = new ContentValues[var15.size()];
                  var7 = var15.iterator();

                  while(var7.hasNext()) {
                     ConnectionsSync.FacebookPageProfile var25 = (ConnectionsSync.FacebookPageProfile)var7.next();
                     long var26 = var25.mId;
                     ContentValues var28 = new ContentValues();
                     var24[var23] = var28;
                     ++var23;
                     Long var29 = Long.valueOf(var26);
                     String var31 = "user_id";
                     var28.put(var31, var29);
                     String var33 = var25.mDisplayName;
                     String var35 = "display_name";
                     var28.put(var35, var33);
                     String var37 = var25.mImageUrl;
                     String var39 = "user_image_url";
                     var28.put(var39, var37);
                     Integer var41 = Integer.valueOf(var25.getConnectionType().ordinal());
                     String var43 = "connection_type";
                     var28.put(var43, var41);
                     Long var45 = Long.valueOf(var25.computeHash());
                     String var47 = "hash";
                     var28.put(var47, var45);
                     if(var25.mImageUrl != null) {
                        Map var49 = this.mNeedUserImageMap;
                        Long var50 = Long.valueOf(var26);
                        String var51 = var25.mImageUrl;
                        var49.put(var50, var51);
                     }
                  }

                  Uri var53 = ConnectionsProvider.PAGES_CONTENT_URI;
                  int var57 = var3.bulkInsert(var53, var24);
               }

               if(var16.size() > 0) {
                  ContentValues var58 = new ContentValues();
                  var7 = var16.iterator();

                  while(var7.hasNext()) {
                     ConnectionsSync.FacebookPageProfile var59 = (ConnectionsSync.FacebookPageProfile)var7.next();
                     if(var59.mDisplayName != null) {
                        long var60 = var59.mId;
                        var58.clear();
                        String var62 = var59.mDisplayName;
                        String var64 = "display_name";
                        var58.put(var64, var62);
                        String var66 = var59.mImageUrl;
                        String var68 = "user_image_url";
                        var58.put(var68, var66);
                        Integer var70 = Integer.valueOf(var59.getConnectionType().ordinal());
                        String var72 = "connection_type";
                        var58.put(var72, var70);
                        Long var74 = Long.valueOf(var59.computeHash());
                        String var76 = "hash";
                        var58.put(var76, var74);
                        Uri var78 = ConnectionsProvider.PAGE_ID_CONTENT_URI;
                        String var79 = String.valueOf(var60);
                        Uri var80 = Uri.withAppendedPath(var78, var79);
                        Object var84 = null;
                        Object var85 = null;
                        var3.update(var80, var58, (String)var84, (String[])var85);
                        if(var59.mImageUrl != null) {
                           Map var87 = this.mNeedUserImageMap;
                           Long var88 = Long.valueOf(var59.mId);
                           String var89 = var59.mImageUrl;
                           var87.put(var88, var89);
                        }
                     }
                  }
               }

               if(var17.size() > 0) {
                  StringBuffer var91 = new StringBuffer;
                  short var93 = 128;
                  var91.<init>(var93);
                  String var95 = "user_id";
                  StringBuffer var96 = var91.append(var95).append(" IN(");
                  boolean var97 = true;

                  StringBuffer var101;
                  Long var98;
                  for(var7 = var17.iterator(); var7.hasNext(); var101 = var91.append(var98)) {
                     var98 = (Long)var7.next();
                     if(var97) {
                        var97 = false;
                     } else {
                        String var103 = ",";
                        var91.append(var103);
                     }
                  }

                  String var106 = ")";
                  var91.append(var106);
                  Uri var108 = ConnectionsProvider.CONNECTIONS_CONTENT_URI;
                  String var109 = var91.toString();
                  Object var113 = null;
                  var3.delete(var108, var109, (String[])var113);
               }
            }
         }
      }
   }

   public void cancel(boolean var1) {
      if(this.mOp != null) {
         this.mOp.cancel((boolean)0);
         this.mOp = null;
      }

      this.mCanceled = (boolean)1;
   }

   public Map<Long, String> getUsersWithoutImageMap() {
      return this.mNeedUserImageMap;
   }

   public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
      Object var5 = var4;
      if(this.simulateSessionKeyError()) {
         var5 = new FacebookApiException(102, "Invalid credentials");
      }

      if(this.mCanceled) {
         this.mListener.onOperationComplete(this, 400, "Canceled", (Exception)var5);
      } else {
         this.mListener.onOperationComplete(this, var2, var3, (Exception)var5);
      }
   }

   public void onOperationProgress(ApiMethod var1, long var2, long var4) {}

   public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {
      if(!this.mCanceled) {
         if(var2 == 200) {
            ConnectionsSync.FqlGetFriendsAndPages var5 = (ConnectionsSync.FqlGetFriendsAndPages)var1;
            List var6 = var5.getFriends();
            this.syncFriends(var6);
            Map var7 = var5.getAdminPages();
            Map var8 = var5.getFanPages();
            this.syncPages(var7, var8);
         }
      }
   }

   protected boolean simulateSessionKeyError() {
      return false;
   }

   public void start() {
      Context var1 = this.mContext;
      Intent var2 = this.mReqIntent;
      String var3 = this.mSessionKey;
      long var4 = this.mMyUserId;
      ConnectionsSync.FqlGetFriendsAndPages var7 = new ConnectionsSync.FqlGetFriendsAndPages(var1, var2, var3, this, var4);
      this.mOp = var7;
      this.mOp.start();
   }

   private static class FacebookAdminPageProfile extends ConnectionsSync.FacebookPageProfile {

      FacebookAdminPageProfile() {
         super((ConnectionsSync.1)null);
         ConnectionsProvider.ConnectionType var1 = ConnectionsProvider.ConnectionType.PAGE_ADMIN;
         this.connectionType = var1;
      }
   }

   private static class FacebookPageProfile extends FacebookProfile {

      protected ConnectionsProvider.ConnectionType connectionType;


      private FacebookPageProfile() {}

      // $FF: synthetic method
      FacebookPageProfile(ConnectionsSync.1 var1) {
         this();
      }

      long computeHash() {
         Object[] var1 = new Object[4];
         Long var2 = Long.valueOf(this.mId);
         var1[0] = var2;
         String var3 = this.mDisplayName;
         var1[1] = var3;
         String var4 = this.mImageUrl;
         var1[2] = var4;
         Integer var5 = Integer.valueOf(this.connectionType.ordinal());
         var1[3] = var5;
         return com.facebook.katana.util.Utils.hashItemsLong(var1);
      }

      ConnectionsProvider.ConnectionType getConnectionType() {
         return this.connectionType;
      }
   }

   private static class FqlGetFriendsAndPages extends FqlMultiQuery {

      FqlGetFriendsAndPages(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5) {
         LinkedHashMap var7 = buildQueries(var1, var2, var3, var5);
         super(var1, var2, var3, var7, var4);
      }

      private static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2, long var3) {
         LinkedHashMap var5 = new LinkedHashMap();
         FqlSyncUsersQuery var11 = new FqlSyncUsersQuery(var0, var1, var2, var3, (ApiMethodListener)null);
         var5.put("friends", var11);
         String var13 = "id IN (SELECT page_id FROM page_admin WHERE uid=" + var3 + ")";
         FqlGetProfileGeneric var17 = new FqlGetProfileGeneric(var0, var1, var2, (ApiMethodListener)null, var13, ConnectionsSync.FacebookAdminPageProfile.class);
         var5.put("admin", var17);
         String var19 = "id IN (SELECT page_id FROM page_fan WHERE uid=" + var3 + ")";
         FqlGetProfileGeneric var23 = new FqlGetProfileGeneric(var0, var1, var2, (ApiMethodListener)null, var19, ConnectionsSync.FacebookFanPageProfile.class);
         var5.put("fan", var23);
         return var5;
      }

      Map<Long, ConnectionsSync.FacebookPageProfile> getAdminPages() {
         return ((FqlGetProfileGeneric)this.getQueryByName("admin")).getProfiles();
      }

      Map<Long, ConnectionsSync.FacebookPageProfile> getFanPages() {
         return ((FqlGetProfileGeneric)this.getQueryByName("fan")).getProfiles();
      }

      List<FacebookFriendInfo> getFriends() {
         return ((FqlSyncUsersQuery)this.getQueryByName("friends")).getFriends();
      }
   }

   private static class FacebookFanPageProfile extends ConnectionsSync.FacebookPageProfile {

      FacebookFanPageProfile() {
         super((ConnectionsSync.1)null);
         ConnectionsProvider.ConnectionType var1 = ConnectionsProvider.ConnectionType.PAGE_FAN;
         this.connectionType = var1;
      }
   }

   private interface HashQuery {

      int INDEX_HASH = 1;
      int INDEX_USER_ID;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"user_id", "hash"};
         PROJECTION = var0;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
