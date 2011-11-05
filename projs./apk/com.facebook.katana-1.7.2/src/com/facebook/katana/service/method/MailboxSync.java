package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookMailboxThread;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.BaseApiMethodListener;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.service.method.MailboxGetThreads;
import com.facebook.katana.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MailboxSync extends ApiMethod {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   private static boolean DEBUG;
   private final int mFolder;
   private final int mLimit;
   private final long mMyUserId;
   private final String mSessionKey;
   private final int mStart;
   private final boolean mSync;


   static {
      byte var0;
      if(!MailboxSync.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      DEBUG = (boolean)1;
   }

   public MailboxSync(Context var1, Intent var2, String var3, int var4, int var5, int var6, boolean var7, long var8, ApiMethodListener var10) {
      String var11 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", (String)null, var11, var10);
      this.mSessionKey = var3;
      this.mFolder = var4;
      this.mStart = var5;
      this.mLimit = var6;
      this.mSync = var7;
      this.mMyUserId = var8;
   }

   private static void addParticipantIdsToList(List<FacebookMailboxThread> var0, Set<Long> var1) {
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         List var3 = ((FacebookMailboxThread)var2.next()).getParticipants();
         var1.addAll(var3);
      }

   }

   private void buildThreadChangeSets(List<FacebookMailboxThread> var1, List<FacebookMailboxThread> var2, List<FacebookMailboxThread> var3, List<Long> var4, Set<Long> var5) {
      boolean var6;
      if(var4 != null) {
         var6 = true;
      } else {
         var6 = false;
      }

      HashMap var7 = new HashMap();
      StringBuffer var8;
      if(var6) {
         var8 = null;
      } else {
         var8 = new StringBuffer;
         short var21 = 256;
         var8.<init>(var21);
         String var23 = "tid";
         StringBuffer var24 = var8.append(var23).append(" IN(");
      }

      boolean var9 = true;
      Iterator var10 = var1.iterator();

      while(var10.hasNext()) {
         FacebookMailboxThread var11 = (FacebookMailboxThread)var10.next();
         Long var12 = Long.valueOf(var11.getThreadId());
         var7.put(var12, var11);
         if(var9) {
            var9 = false;
         } else if(!var6) {
            String var26 = ",";
            var8.append(var26);
         }

         if(!var6) {
            long var14 = var11.getThreadId();
            StringBuffer var19 = var8.append(var14);
         }
      }

      if(!var6) {
         String var29 = ")";
         var8.append(var29);
      }

      String var31;
      if(var6) {
         var31 = null;
      } else {
         var31 = var8.toString();
      }

      ContentResolver var32 = this.mContext.getContentResolver();
      Uri var33 = MailboxProvider.getThreadsFolderUri(this.mFolder);
      String[] var34 = MailboxSync.ThreadsQuery.THREADS_PROJECTION;
      Cursor var35 = var32.query(var33, var34, var31, (String[])null, (String)null);
      if(var35.moveToFirst()) {
         do {
            long var36 = var35.getLong(0);
            Long var38 = Long.valueOf(var36);
            FacebookMailboxThread var39 = (FacebookMailboxThread)var7.get(var38);
            if(var39 != null) {
               long var40 = var39.getLastUpdate();
               long var42 = var35.getLong(1);
               if(var40 != var42) {
                  boolean var46 = var3.add(var39);
               }

               Long var47 = Long.valueOf(var36);
               var7.remove(var47);
            } else if(var6) {
               Long var61 = Long.valueOf(var36);
               boolean var64 = var4.add(var61);
            } else if(!$assertionsDisabled) {
               throw new AssertionError();
            }
         } while(var35.moveToNext());
      }

      var35.close();
      Collection var49 = var7.values();
      boolean var52 = var2.addAll(var49);
      if(var2.size() > 0) {
         StringBuilder var53 = (new StringBuilder()).append("found ");
         int var54 = var2.size();
         log(var53.append(var54).append(" messages to add").toString());
      }

      if(var3.size() > 0) {
         StringBuilder var55 = (new StringBuilder()).append("found ");
         int var56 = var3.size();
         log(var55.append(var56).append(" messages to change").toString());
      }

      if(var4 != null && var4.size() > 0) {
         StringBuilder var57 = (new StringBuilder()).append("found ");
         int var58 = var4.size();
         log(var57.append(var58).append(" messages to delete").toString());
      }

      addParticipantIdsToList(var1, var5);
   }

   private void commitChanges(List<FacebookMailboxThread> var1, List<FacebookMailboxThread> var2, List<Long> var3, Map<Long, FacebookProfile> var4) {
      ContentResolver var5 = this.mContext.getContentResolver();
      Iterator var9;
      if(var1.size() > 0) {
         int var6 = 0;
         ContentValues[] var7 = new ContentValues[var1.size()];
         String var8 = this.mContext.getString(2131361982);

         for(var9 = var1.iterator(); var9.hasNext(); ++var6) {
            FacebookMailboxThread var10 = (FacebookMailboxThread)var9.next();
            int var11 = this.mFolder;
            long var12 = this.mMyUserId;
            ContentValues var15 = var10.getContentValues(var11, var12, var4, var8);
            var7[var6] = var15;
         }

         if(var1.size() > 0) {
            StringBuilder var16 = (new StringBuilder()).append("adding ");
            int var17 = var1.size();
            log(var16.append(var17).append(" messages").toString());
         }

         Uri var18 = MailboxProvider.THREADS_CONTENT_URI;
         int var22 = var5.bulkInsert(var18, var7);
      }

      boolean var86;
      if(var2.size() > 0) {
         Uri var23 = MailboxProvider.getThreadsTidFolderUri(this.mFolder);
         Uri var24 = MailboxProvider.getMessagesFolderUri(this.mFolder);
         ContentValues var25 = new ContentValues();
         Iterator var26 = var2.iterator();

         while(var26.hasNext()) {
            FacebookMailboxThread var27 = (FacebookMailboxThread)var26.next();
            var25.clear();
            String var28 = var27.getSubject();
            String var30 = "subject";
            var25.put(var30, var28);
            String var32 = var27.getSnippet();
            String var34 = "snippet";
            var25.put(var34, var32);
            Long var36 = Long.valueOf(var27.getOtherPartyUserId());
            String var38 = "other_party";
            var25.put(var38, var36);
            Integer var40 = Integer.valueOf(var27.getMsgCount());
            String var42 = "msg_count";
            var25.put(var42, var40);
            Integer var44 = Integer.valueOf(var27.getUnreadCount());
            String var46 = "unread_count";
            var25.put(var46, var44);
            Long var48 = Long.valueOf(var27.getLastUpdate());
            String var50 = "last_update";
            var25.put(var50, var48);
            String var52 = this.mContext.getString(2131361982);
            Long var53 = Long.valueOf(this.mMyUserId);
            String var58 = var27.getParticipantsString(var4, var52, var53);
            String var60 = "participants";
            var25.put(var60, var58);
            if(var2.size() > 0) {
               StringBuilder var62 = (new StringBuilder()).append("updating ");
               int var63 = var2.size();
               log(var62.append(var63).append(" messages").toString());
            }

            StringBuilder var64 = (new StringBuilder()).append("");
            long var65 = var27.getThreadId();
            String var67 = var64.append(var65).toString();
            Uri var70 = Uri.withAppendedPath(var23, var67);
            Object var74 = null;
            Object var75 = null;
            var5.update(var70, var25, (String)var74, (String[])var75);
         }

         StringBuffer var77 = new StringBuffer;
         short var79 = 256;
         var77.<init>(var79);
         String var81 = "tid";
         var77.append(var81);
         String var84 = " IN(";
         var77.append(var84);
         var86 = true;

         StringBuffer var93;
         long var88;
         for(var9 = var2.iterator(); var9.hasNext(); var93 = var77.append(var88)) {
            FacebookMailboxThread var87 = (FacebookMailboxThread)var9.next();
            if(var86) {
               var86 = false;
            } else {
               String var95 = ",";
               var77.append(var95);
            }

            var88 = var87.getThreadId();
         }

         String var98 = ")";
         var77.append(var98);
         String var100 = var77.toString();
         Object var104 = null;
         var5.delete(var24, var100, (String[])var104);
      }

      if(var3 != null) {
         if(var3.size() > 0) {
            StringBuffer var106 = new StringBuffer(128);
            StringBuffer var107 = var106.append("tid");
            StringBuffer var108 = var106.append(" IN(");
            boolean var109 = true;

            Long var111;
            StringBuffer var114;
            for(Iterator var110 = var3.iterator(); var110.hasNext(); var114 = var106.append(var111)) {
               var111 = (Long)var110.next();
               if(var109) {
                  var109 = false;
               } else {
                  StringBuffer var115 = var106.append(",");
               }
            }

            StringBuffer var116 = var106.append(")");
            Uri var117 = MailboxProvider.getThreadsFolderUri(this.mFolder);
            String var118 = var106.toString();
            Object var122 = null;
            var5.delete(var117, var118, (String[])var122);
            StringBuffer var124 = new StringBuffer(128);
            StringBuffer var125 = var124.append("tid");
            StringBuffer var126 = var124.append(" IN(");
            var86 = true;

            StringBuffer var130;
            Long var127;
            for(var9 = var3.iterator(); var9.hasNext(); var130 = var124.append(var127)) {
               var127 = (Long)var9.next();
               if(!var86) {
                  StringBuffer var131 = var124.append(",");
               }
            }

            StringBuffer var132 = var124.append(")");
            StringBuilder var133 = (new StringBuilder()).append("deleting ");
            int var134 = var3.size();
            log(var133.append(var134).append(" messages").toString());
            Uri var135 = MailboxProvider.getMessagesFolderUri(this.mFolder);
            String var136 = var124.toString();
            Object var140 = null;
            var5.delete(var135, var136, (String[])var140);
            Uri var142 = MailboxProvider.PROFILES_PRUNE_CONTENT_URI;
            var5.delete(var142, (String)null, (String[])null);
         }
      }
   }

   private void findMissingUserIds(Set<Long> var1, Set<Long> var2, Map<Long, FacebookProfile> var3) {
      ContentResolver var4 = this.mContext.getContentResolver();
      boolean var7 = var2.addAll(var1);
      StringBuilder var8 = (new StringBuilder()).append("looking for ");
      int var9 = var2.size();
      log(var8.append(var9).append(" users").toString());
      StringBuilder var10 = (new StringBuilder()).append("have ");
      int var11 = var3.size();
      log(var10.append(var11).append(" cached users").toString());
      Set var12 = var3.keySet();
      boolean var15 = var2.removeAll(var12);
      int var16 = var2.size();
      if(var16 > 0) {
         String var18 = "id";
         String var20 = this.whereForPropertyWithValues(var18, var1);
         Uri var21 = MailboxProvider.PROFILES_CONTENT_URI;
         String[] var22 = MailboxSync.ProfilesQuery.PROFILES_PROJECTION;
         Cursor var23 = var4.query(var21, var22, var20, (String[])null, (String)null);
         if(var23.moveToFirst()) {
            do {
               byte var25 = 0;
               Long var26 = Long.valueOf(var23.getLong(var25));
               boolean var29 = var2.remove(var26);
               long var30 = var26.longValue();
               byte var33 = 1;
               String var34 = var23.getString(var33);
               byte var36 = 2;
               String var37 = var23.getString(var36);
               FacebookProfile var38 = new FacebookProfile(var30, var34, var37, 0);
               Object var42 = var3.put(var26, var38);
            } while(var23.moveToNext());
         }

         var23.close();
         StringBuilder var43 = (new StringBuilder()).append("found ");
         int var44 = var2.size();
         int var45 = var16 - var44;
         log(var43.append(var45).append(" users in the users table").toString());
         var16 = var2.size();
         if(var16 > 0) {
            ArrayList var46 = new ArrayList();
            String var48 = "user_id";
            String var50 = this.whereForPropertyWithValues(var48, var2);
            Uri var51 = ConnectionsProvider.FRIENDS_CONTENT_URI;
            String[] var52 = MailboxSync.FriendsQuery.PROJECTION;
            Cursor var55 = var4.query(var51, var52, var50, (String[])null, (String)null);
            if(var55.moveToFirst()) {
               do {
                  Long var56 = Long.valueOf(var55.getLong(0));
                  long var57 = var56.longValue();
                  String var59 = var55.getString(1);
                  String var60 = var55.getString(2);
                  FacebookProfile var61 = new FacebookProfile(var57, var59, var60, 0);
                  Object var65 = var3.put(var56, var61);
                  var46.add(var61);
                  boolean var69 = var2.remove(var56);
               } while(var55.moveToNext());
            }

            var55.close();
            if(!$assertionsDisabled) {
               int var70 = var46.size();
               int var71 = var2.size() - var16;
               if(var70 != var71) {
                  throw new AssertionError();
               }
            }

            StringBuilder var72 = (new StringBuilder()).append("found ");
            int var73 = var46.size();
            log(var72.append(var73).append(" users in the friends table").toString());
            this.insertUsers(var46);
         }
      }
   }

   private Collection<Long> handleFetchResults(List<FacebookMailboxThread> param1, Map<Long, FacebookProfile> param2, Map<Long, FacebookProfile> param3, boolean param4) {
      // $FF: Couldn't be decompiled
   }

   private void insertUsers(Collection<FacebookProfile> var1) {
      int var2 = var1.size();
      if(var2 > 0) {
         ContentResolver var3 = this.mContext.getContentResolver();
         ContentValues[] var4 = new ContentValues[var2];
         int var5 = 0;
         Iterator var6 = var1.iterator();

         while(var6.hasNext()) {
            FacebookProfile var7 = (FacebookProfile)var6.next();
            ContentValues var8 = new ContentValues();
            var4[var5] = var8;
            ++var5;
            Long var9 = Long.valueOf(var7.mId);
            var8.put("id", var9);
            String var10 = var7.mDisplayName;
            var8.put("display_name", var10);
            String var11 = var7.mImageUrl;
            var8.put("profile_image_url", var11);
            Integer var12 = Integer.valueOf(var7.mType);
            var8.put("type", var12);
         }

         log("adding " + var2 + " users to the users table");
         Uri var13 = MailboxProvider.PROFILES_CONTENT_URI;
         var3.bulkInsert(var13, var4);
      }
   }

   private static void log(String var0) {
      if(DEBUG) {
         Log.e("MailboxSync", var0);
      }
   }

   private void saveNewUsers(Map<Long, FacebookProfile> var1) {
      ContentResolver var2 = this.mContext.getContentResolver();
      Set var3 = var1.keySet();
      String var4 = this.whereForPropertyWithValues("id", var3);
      Uri var5 = MailboxProvider.PROFILES_CONTENT_URI;
      String[] var6 = MailboxSync.ProfilesQuery.PROFILES_PROJECTION;
      Object var7 = null;
      Cursor var8 = var2.query(var5, var6, var4, (String[])null, (String)var7);
      HashMap var9 = new HashMap(var1);
      if(var8.moveToFirst()) {
         do {
            Long var10 = Long.valueOf(var8.getLong(0));
            var9.remove(var10);
         } while(var8.moveToNext());
      }

      Collection var12 = var9.values();
      this.insertUsers(var12);
   }

   private String whereForPropertyWithValues(String var1, Collection<Long> var2) {
      StringBuffer var3 = new StringBuffer(256);
      StringBuffer var4 = var3.append(var1).append(" IN(");
      boolean var5 = true;

      Long var7;
      for(Iterator var6 = var2.iterator(); var6.hasNext(); var3.append(var7)) {
         var7 = (Long)var6.next();
         if(var5) {
            var5 = false;
         } else {
            StringBuffer var9 = var3.append(",");
         }
      }

      StringBuffer var10 = var3.append(")");
      return var3.toString();
   }

   public void start() {
      MailboxSync.1 var1 = new MailboxSync.1();
      Context var2 = this.mContext;
      Intent var3 = this.mReqIntent;
      String var4 = this.mSessionKey;
      int var5 = this.mFolder;
      int var6 = this.mStart;
      int var7 = this.mLimit;
      (new MailboxGetThreads(var2, var3, var4, var5, var6, var7, var1)).start();
   }

   private interface ThreadsQuery {

      int INDEX_LAST_UPDATE = 1;
      int INDEX_THREAD_ID;
      String[] THREADS_PROJECTION;


      static {
         String[] var0 = new String[]{"tid", "last_update"};
         THREADS_PROJECTION = var0;
      }
   }

   private interface ProfilesQuery {

      int INDEX_PROFILE_DISPLAY_NAME = 1;
      int INDEX_PROFILE_ID = 0;
      int INDEX_PROFILE_IMAGE_URL = 2;
      String[] PROFILES_PROJECTION;


      static {
         String[] var0 = new String[]{"id", "display_name", "profile_image_url"};
         PROFILES_PROJECTION = var0;
      }
   }

   private interface FriendsQuery {

      int INDEX_USER_DISPLAY_NAME = 1;
      int INDEX_USER_ID = 0;
      int INDEX_USER_IMAGE_URL = 2;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"user_id", "display_name", "user_image_url"};
         PROJECTION = var0;
      }
   }

   class 1 extends BaseApiMethodListener {

      private final Map<Long, FacebookProfile> mCachedUsers;
      private Collection<Long> mMissingUserIds;
      private List<FacebookMailboxThread> mThreads;


      1() {
         HashMap var2 = new HashMap();
         this.mCachedUsers = var2;
      }

      public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
         if(this.mMissingUserIds != null && this.mMissingUserIds.size() > 0) {
            MailboxSync.1.1 var5 = new MailboxSync.1.1();
            Intent var6 = new Intent();
            String var7 = MailboxSync.this.getIntent().getStringExtra("ApiMethod.secret");
            var6.putExtra("ApiMethod.secret", var7);
            HashMap var9 = new HashMap();
            Iterator var10 = this.mMissingUserIds.iterator();

            while(var10.hasNext()) {
               Long var11 = (Long)var10.next();
               var9.put(var11, (Object)null);
            }

            Context var13 = MailboxSync.this.mContext;
            String var14 = MailboxSync.this.mSessionKey;
            (new FqlGetProfile(var13, var6, var14, var5, var9)).start();
         } else {
            ApiMethodListener var15 = MailboxSync.this.mListener;
            MailboxSync var16 = MailboxSync.this;
            var15.onOperationComplete(var16, var2, var3, var4);
         }
      }

      public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {
         List var5 = ((MailboxGetThreads)var1).getMailboxThreads();
         this.mThreads = var5;
         if(this.mThreads != null && var4 == null) {
            MailboxSync var6 = MailboxSync.this;
            List var7 = this.mThreads;
            Map var8 = this.mCachedUsers;
            Collection var9 = var6.handleFetchResults(var7, (Map)null, var8, (boolean)1);
            this.mMissingUserIds = var9;
         } else {
            String var10 = "failed to download mailbox threads: " + var4;
            Log.e("MailboxSync", var10);
         }
      }

      class 1 extends BaseApiMethodListener {

         1() {}

         public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
            ApiMethodListener var5 = MailboxSync.this.mListener;
            MailboxSync var6 = MailboxSync.this;
            var5.onOperationComplete(var6, var2, var3, var4);
         }

         public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {
            Map var5 = ((FqlGetProfile)var1).getProfiles();
            if(var5 != null && var4 == null) {
               1.this.mCachedUsers.putAll(var5);
               MailboxSync var6 = MailboxSync.this;
               List var7 = 1.this.mThreads;
               Map var8 = 1.this.mCachedUsers;
               var6.handleFetchResults(var7, var5, var8, (boolean)0);
            } else {
               String var10 = "failed to download thread participants: " + var4;
               Log.e("MailboxSync", var10);
            }
         }
      }
   }
}
