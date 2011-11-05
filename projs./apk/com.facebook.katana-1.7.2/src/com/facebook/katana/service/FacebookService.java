package com.facebook.katana.service;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ChatSession;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.StreamPhoto;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.model.FacebookNotifications;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.model.FacebookPhotoComment;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookStreamType;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.model.FacebookUserFull;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.service.method.ApiLogging;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.AuthLogin;
import com.facebook.katana.service.method.AuthLogout;
import com.facebook.katana.service.method.BatchRun;
import com.facebook.katana.service.method.ConfigFetcher;
import com.facebook.katana.service.method.ConnectionsSync;
import com.facebook.katana.service.method.EventRsvp;
import com.facebook.katana.service.method.FqlGetEventMembers;
import com.facebook.katana.service.method.FqlGetFacebookAffiliation;
import com.facebook.katana.service.method.FqlGetMutualFriends;
import com.facebook.katana.service.method.FqlGetStream;
import com.facebook.katana.service.method.FqlGetUsersFriendStatus;
import com.facebook.katana.service.method.FqlGetUsersProfile;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlStatusQuery;
import com.facebook.katana.service.method.FqlSyncNotifications;
import com.facebook.katana.service.method.FriendRequestRespond;
import com.facebook.katana.service.method.MailboxDeleteThread;
import com.facebook.katana.service.method.MailboxGetThreadMessages;
import com.facebook.katana.service.method.MailboxMarkThread;
import com.facebook.katana.service.method.MailboxReply;
import com.facebook.katana.service.method.MailboxSend;
import com.facebook.katana.service.method.MailboxSync;
import com.facebook.katana.service.method.NotificationsGet;
import com.facebook.katana.service.method.NotificationsMarkRead;
import com.facebook.katana.service.method.PhotoDownload;
import com.facebook.katana.service.method.PhotosAddComment;
import com.facebook.katana.service.method.PhotosAddTag;
import com.facebook.katana.service.method.PhotosCreateAlbum;
import com.facebook.katana.service.method.PhotosDeleteAlbum;
import com.facebook.katana.service.method.PhotosDeletePhoto;
import com.facebook.katana.service.method.PhotosEditAlbum;
import com.facebook.katana.service.method.PhotosEditPhoto;
import com.facebook.katana.service.method.PhotosGetPhotos;
import com.facebook.katana.service.method.PhotosGetTags;
import com.facebook.katana.service.method.PhotosUpload;
import com.facebook.katana.service.method.ProfileImageDownload;
import com.facebook.katana.service.method.StreamAddComment;
import com.facebook.katana.service.method.StreamAddLike;
import com.facebook.katana.service.method.StreamGetComments;
import com.facebook.katana.service.method.StreamGetFilters;
import com.facebook.katana.service.method.StreamRemove;
import com.facebook.katana.service.method.StreamRemoveComment;
import com.facebook.katana.service.method.StreamRemoveLike;
import com.facebook.katana.service.method.SyncAlbums;
import com.facebook.katana.service.method.SyncPhotoComments;
import com.facebook.katana.service.method.UsersSearch;
import com.facebook.katana.util.FileUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FacebookService extends Service implements ApiMethodListener {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final String EXTRA_ORIGINAL_INTENT = "FacebookService.originalIntent";
   private static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";
   private static final int MAX_CONCURRENT_DOWNLOAD = 3;
   private static final long MAX_DIFF_TIME_MS = 100L;
   public static Map<String, ApiMethod> apiMethodReceiver;
   public static boolean isBackgroundMode;
   private static Intent nextIntent;
   private Context mContext;
   private final List<ApiMethod> mHighPriorityDownloadQueue;
   private long mLastBatchTime;
   private final List<ApiMethod> mLowPriorityDownloadQueue;
   private final List<ApiMethod> mMediumPriorityDownloadQueue;
   private final Map<String, ApiMethod> mPendingOps;


   static {
      byte var0;
      if(!FacebookService.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      nextIntent = null;
      apiMethodReceiver = new HashMap();
      isBackgroundMode = (boolean)1;
   }

   public FacebookService() {
      HashMap var1 = new HashMap();
      this.mPendingOps = var1;
      LinkedList var2 = new LinkedList();
      this.mHighPriorityDownloadQueue = var2;
      LinkedList var3 = new LinkedList();
      this.mMediumPriorityDownloadQueue = var3;
      LinkedList var4 = new LinkedList();
      this.mLowPriorityDownloadQueue = var4;
   }

   private void addToBestPlaceInDownloadQueue(ApiMethod var1) {
      if(var1 instanceof ProfileImageDownload) {
         this.mLowPriorityDownloadQueue.add(var1);
      } else {
         long var3 = System.currentTimeMillis();
         long var5 = this.mLastBatchTime + 100L;
         if(var3 > var5) {
            List var7 = this.mMediumPriorityDownloadQueue;
            List var8 = this.mHighPriorityDownloadQueue;
            var7.addAll(var8);
            this.mHighPriorityDownloadQueue.clear();
         }

         this.mHighPriorityDownloadQueue.add(var1);
         this.mLastBatchTime = var3;
      }
   }

   private void downloadNext(ApiMethod var1) {
      boolean var2;
      if(true && this.mHighPriorityDownloadQueue.remove(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      boolean var3;
      if(!var2 && this.mMediumPriorityDownloadQueue.remove(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(!var3 && this.mLowPriorityDownloadQueue.remove(var1)) {
         boolean var4 = true;
      } else {
         boolean var11 = false;
      }

      boolean var6;
      label39: {
         if(true) {
            List var5 = this.mHighPriorityDownloadQueue;
            if(this.startImageDownloadFromQueue(var5)) {
               var6 = true;
               break label39;
            }
         }

         var6 = false;
      }

      boolean var8;
      label34: {
         if(!var6) {
            List var7 = this.mMediumPriorityDownloadQueue;
            if(this.startImageDownloadFromQueue(var7)) {
               var8 = true;
               break label34;
            }
         }

         var8 = false;
      }

      if(!var8) {
         List var9 = this.mLowPriorityDownloadQueue;
         if(this.startImageDownloadFromQueue(var9)) {
            boolean var10 = true;
            return;
         }
      }

      boolean var12 = false;
   }

   private String getRequestId(ApiMethod var1) {
      return var1.getIntent().getStringExtra("rid");
   }

   public static void pause(boolean var0, boolean var1, Activity var2) {
      if(!var0 && var1 && (nextIntent == null || processActivityChange(nextIntent))) {
         isBackgroundMode = (boolean)1;
         ApiLogging.flush(var2);
         ChatSession.shutdown((boolean)1);
      }

      if(!var0) {
         nextIntent = null;
      }
   }

   public static boolean processActivityChange(Intent var0) {
      nextIntent = var0;
      ComponentName var1 = var0.getComponent();
      boolean var2;
      if(var1 == null) {
         var2 = true;
      } else if(var1.getPackageName().equals("com.facebook.katana")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public static boolean resume(boolean var0, Activity var1) {
      nextIntent = null;
      boolean var2;
      if(var0) {
         var2 = false;
      } else {
         if(isBackgroundMode) {
            isBackgroundMode = (boolean)0;
            ApiLogging.load(var1);
            ChatSession var3 = ChatSession.getActiveChatSession(var1);
            if(var3 != null) {
               var3.connect((boolean)1, (String)null);
            }
         }

         var2 = true;
      }

      return var2;
   }

   private boolean startImageDownloadFromQueue(List<ApiMethod> var1) {
      boolean var5;
      if(var1.size() > 0) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            ApiMethod var3 = (ApiMethod)var2.next();
            String var4 = this.getRequestId(var3);
            if(!this.mPendingOps.containsKey(var4)) {
               this.startOp(var4, var3);
               var5 = true;
               return var5;
            }
         }
      }

      var5 = false;
      return var5;
   }

   private void startOp(String var1, ApiMethod var2) {
      this.mPendingOps.put(var1, var2);
      var2.start();
   }

   public IBinder onBind(Intent var1) {
      return null;
   }

   public void onCreate() {
      super.onCreate();
      Context var1 = this.getApplicationContext();
      this.mContext = var1;
   }

   public void onDestroy() {}

   public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {
      Intent var5 = var1.getIntent();
      String var7 = "rid";
      String var8 = var5.getStringExtra(var7);
      Map var9 = this.mPendingOps;
      var9.remove(var8);
      String var13 = "type";
      byte var14 = 0;
      byte var267;
      long var265;
      Iterator var205;
      FqlMultiQuery var197;
      Object var198;
      switch(var5.getIntExtra(var13, var14)) {
      case 1:
      case 301:
         short var21 = 200;
         if(var2 == var21) {
            String var23 = "rid";
            String var24 = var5.getStringExtra(var23);
            FacebookSessionInfo var25 = ((AuthLogin)var1).getSessionInfo();
            ArrayList var26 = new ArrayList();
            Intent var27 = new Intent();
            Intent var28 = var27.putExtra("type", 3);
            String var30 = "FacebookService.originalIntent";
            var27.putExtra(var30, var5);
            String var33 = var25.sessionSecret;
            var27.putExtra("ApiMethod.secret", var33);
            HashMap var35 = new HashMap();
            Long var36 = Long.valueOf(var25.userId);
            var35.put(var36, (Object)null);
            Context var38 = this.mContext;
            String var39 = var25.sessionKey;
            FqlGetUsersProfile var40 = new FqlGetUsersProfile(var38, var27, var39, (ApiMethodListener)null, var35, var25);
            var26.add(var40);
            Context var42 = this.mContext;
            String var43 = var25.sessionKey;
            StreamGetFilters var44 = new StreamGetFilters(var42, var27, var43, (ApiMethodListener)null);
            var26.add(var44);
            Context var46 = this.mContext;
            String var47 = var25.sessionKey;
            BatchRun var50 = new BatchRun(var46, var27, var47, var26, this);
            this.startOp(var24, var50);
            return;
         }

         Context var54 = this.mContext;
         AppSession.onServiceOperationComplete(var54, var5, var2, var3, var4, (Object)null, (Object)null);
         return;
      case 3:
         Object var58 = null;
         short var60 = 200;
         if(var2 == var60) {
            FqlGetUsersProfile var61 = (FqlGetUsersProfile)((BatchRun)var1).getMethods().get(0);
            FacebookSessionInfo var62 = (FacebookSessionInfo)var61.getOpaque();
            if(var62 == null) {
               FacebookSessionInfo var63 = AppSession.getActiveSession(this.mContext, (boolean)0).getSessionInfo();
            }

            Map var64 = var61.getUsers();
            Long var65 = Long.valueOf(var62.userId);
            FacebookUser var66 = (FacebookUser)var64.get(var65);
            var62.setProfile(var66);
            String var67 = ((StreamGetFilters)((BatchRun)var1).getMethods().get(1)).getNewsFeedFilter();
            var62.setFilterKey(var67);
            String var68 = var62.toJSONObject().toString();
            Context var69 = this.mContext;
            UserValuesManager.saveActiveSessionInfo(var69, var68);
         }

         String var72 = "FacebookService.originalIntent";
         Intent var73 = (Intent)var5.getParcelableExtra(var72);
         if(var73 == null) {
            return;
         }

         Context var74 = this.mContext;
         AppSession.onServiceOperationComplete(var74, var73, var2, var3, var4, var58, (Object)null);
         return;
      case 30:
         List var79 = null;
         short var81 = 200;
         if(var2 == var81) {
            var79 = ((FqlGetStream)var1).getPosts();
         }

         Context var82 = this.mContext;
         AppSession.onServiceOperationComplete(var82, var5, var2, var3, var4, var79, (Object)null);
         return;
      case 31:
         List var87 = null;
         short var89 = 200;
         if(var2 == var89) {
            var87 = ((StreamGetComments)var1).getComments();
         }

         Context var90 = this.mContext;
         AppSession.onServiceOperationComplete(var90, var5, var2, var3, var4, var87, (Object)null);
         return;
      case 33:
         Context var95 = this.mContext;
         String var96 = ((StreamAddComment)var1).getCommentId();
         AppSession.onServiceOperationComplete(var95, var5, var2, var3, var4, var96, (Object)null);
         return;
      case 50:
         Object var255 = null;
         short var257 = 200;
         if(var2 == var257) {
            FacebookNotifications var258 = ((NotificationsGet)var1).getNotifications();
         }

         Context var259 = this.mContext;
         AppSession.onServiceOperationComplete(var259, var5, var2, var3, var4, var255, (Object)null);
         return;
      case 61:
         PhotosCreateAlbum var101 = (PhotosCreateAlbum)var1;
         Context var102 = this.mContext;
         FacebookAlbum var103 = var101.getAlbum();
         Uri var104 = var101.getAlbumUri();
         AppSession.onServiceOperationComplete(var102, var5, var2, var3, var4, var103, var104);
         return;
      case 65:
         String var110 = "session_user_id";
         long var111 = 65535L;
         long var113 = var5.getLongExtra(var110, var111);
         short var116 = 200;
         FacebookPhoto var117;
         if(var2 == var116) {
            var117 = ((PhotosUpload)var1).getPhoto();
         } else {
            String var125 = "checkin_id";
            long var126 = 65535L;
            long var128 = var5.getLongExtra(var125, var126);
            var117 = null;
            if(var128 == 65535L) {
               String var131 = "aid";
               String var132 = var5.getStringExtra(var131);
               String var134 = "subject";
               String var135 = var5.getStringExtra(var134);
               var117 = new FacebookPhoto((String)null, var132, var113, var135, (String)null, (String)null, (String)null, 0L, (byte[])null, 65535L, 65535L);
            }
         }

         Context var118 = this.mContext;
         AppSession.onServiceOperationComplete(var118, var5, var2, var3, var4, var117, (Object)null);
         return;
      case 67:
         Context var136 = this.mContext;
         Boolean var137 = Boolean.valueOf(((PhotosDeletePhoto)var1).hasAlbumCoverChanged());
         AppSession.onServiceOperationComplete(var136, var5, var2, var3, var4, var137, (Object)null);
         return;
      case 68:
         Context var142 = this.mContext;
         List var143 = ((PhotosAddTag)var1).getTags();
         AppSession.onServiceOperationComplete(var142, var5, var2, var3, var4, var143, (Object)null);
         return;
      case 69:
         Context var148 = this.mContext;
         List var149 = ((PhotosGetTags)var1).getTags();
         AppSession.onServiceOperationComplete(var148, var5, var2, var3, var4, var149, (Object)null);
         return;
      case 70:
         SyncPhotoComments var154 = (SyncPhotoComments)var1;
         Context var155 = this.mContext;
         List var156 = var154.getComments();
         Boolean var157 = Boolean.valueOf(var154.getCanComment());
         AppSession.onServiceOperationComplete(var155, var5, var2, var3, var4, var156, var157);
         return;
      case 71:
         PhotosAddComment var162 = (PhotosAddComment)var1;
         Context var163 = this.mContext;
         FacebookPhotoComment var164 = var162.getComment();
         AppSession.onServiceOperationComplete(var163, var5, var2, var3, var4, var164, (Object)null);
         return;
      case 72:
      case 73:
         Context var175 = this.mContext;
         StreamPhoto var176 = ((PhotoDownload)var1).getPhoto();
         AppSession.onServiceOperationComplete(var175, var5, var2, var3, var4, var176, (Object)null);
         this.downloadNext(var1);
         return;
      case 74:
      case 76:
         Context var181 = this.mContext;
         AppSession.onServiceOperationComplete(var181, var5, var2, var3, var4, (Object)null, (Object)null);
         this.downloadNext(var1);
         return;
      case 75:
         Context var186 = this.mContext;
         AppSession.onServiceOperationComplete(var186, var5, var2, var3, var4, (Object)null, (Object)null);
         this.downloadNext(var1);
         return;
      case 77:
         Context var169 = this.mContext;
         Bitmap var170 = ((PhotoDownload)var1).getBitmap();
         AppSession.onServiceOperationComplete(var169, var5, var2, var3, var4, var170, (Object)null);
         this.downloadNext(var1);
         return;
      case 80:
         Map var233 = null;
         short var235 = 200;
         if(var2 == var235) {
            var233 = ((ConnectionsSync)var1).getUsersWithoutImageMap();
         }

         Context var236 = this.mContext;
         AppSession.onServiceOperationComplete(var236, var5, var2, var3, var4, var233, (Object)null);
         return;
      case 81:
         Context var249 = this.mContext;
         List var250 = ((FqlStatusQuery)var1).getStatusList();
         AppSession.onServiceOperationComplete(var249, var5, var2, var3, var4, var250, (Object)null);
         return;
      case 82:
         var197 = (FqlMultiQuery)var1;
         var198 = null;
         byte var199 = 1;
         short var201 = 200;
         if(var2 == var201) {
            String var203 = "user";
            FqlGetUsersProfile var204 = (FqlGetUsersProfile)var197.getQueryByName(var203);
            if(!$assertionsDisabled && var204.getUsers().size() != 1) {
               throw new AssertionError();
            }

            var205 = var204.getUsers().values().iterator();
            if(var205.hasNext()) {
               FacebookUserFull var206 = (FacebookUserFull)((FacebookUser)var205.next());
            }

            String var208 = "significant_other";
            FqlGetUsersProfile var209 = (FqlGetUsersProfile)var197.getQueryByName(var208);
            if(var209.getUsers().size() == 1) {
               var205 = var209.getUsers().values().iterator();

               while(var205.hasNext()) {
                  FacebookUser var210 = (FacebookUser)var205.next();
                  ((FacebookUserFull)var198).setSignificantOther(var210);
               }
            }

            String var214 = "arefriends";
            FqlGetUsersFriendStatus var215 = (FqlGetUsersFriendStatus)var197.getQueryByName(var214);
            if(var215 != null && !var215.areFriends()) {
               var199 = 0;
            }
         }

         Context var216 = this.mContext;
         Boolean var217 = Boolean.valueOf((boolean)var199);
         AppSession.onServiceOperationComplete(var216, var5, var2, var3, var4, var198, var217);
         return;
      case 83:
         var197 = (FqlMultiQuery)var1;
         var198 = null;
         short var223 = 200;
         if(var2 == var223) {
            String var225 = "user";
            FqlGetUsersProfile var226 = (FqlGetUsersProfile)var197.getQueryByName(var225);
            if(!$assertionsDisabled && var226.getUsers().size() != 1) {
               throw new AssertionError();
            }

            var205 = var226.getUsers().values().iterator();
            if(var205.hasNext()) {
               FacebookUser var227 = (FacebookUser)var205.next();
            }
         }

         Context var228 = this.mContext;
         AppSession.onServiceOperationComplete(var228, var5, var2, var3, var4, var198, (Object)null);
         return;
      case 100:
         Context var191 = this.mContext;
         ProfileImage var192 = ((ProfileImageDownload)var1).getProfileImage();
         AppSession.onServiceOperationComplete(var191, var5, var2, var3, var4, var192, (Object)null);
         this.downloadNext(var1);
         return;
      case 121:
         EventRsvp var332 = (EventRsvp)var1;
         var265 = var332.getEventId();
         var267 = 0;
         short var269 = 200;
         if(var2 == var269) {
            var267 = var332.getSuccess();
         }

         Context var270 = this.mContext;
         Long var271 = Long.valueOf(var265);
         Boolean var272 = Boolean.valueOf((boolean)var267);
         AppSession.onServiceOperationComplete(var270, var5, var2, var3, var4, var271, var272);
         return;
      case 122:
         FqlGetEventMembers var333 = (FqlGetEventMembers)var1;
         var265 = var333.getEventId();
         Object var277 = null;
         short var279 = 200;
         if(var2 == var279) {
            Map var280 = var333.getEventMembersByStatus();
         }

         Context var281 = this.mContext;
         Long var282 = Long.valueOf(var265);
         AppSession.onServiceOperationComplete(var281, var5, var2, var3, var4, var282, var277);
         return;
      case 131:
         FqlGetUsersProfile var334 = (FqlGetUsersProfile)var1;
         Map var287 = null;
         short var289 = 200;
         if(var2 == var289) {
            var287 = var334.getUsers();
         }

         Context var290 = this.mContext;
         AppSession.onServiceOperationComplete(var290, var5, var2, var3, var4, var287, (Object)null);
         return;
      case 132:
         FriendRequestRespond var335 = (FriendRequestRespond)var1;
         long var295 = var335.getRequesterUserId();
         var267 = 0;
         short var298 = 200;
         if(var2 == var298) {
            boolean var299 = var335.getSuccess();
         }

         Context var300 = this.mContext;
         Long var301 = new Long(var295);
         Boolean var305 = new Boolean((boolean)var267);
         AppSession.onServiceOperationComplete(var300, var5, var2, var3, var4, var301, var305);
         return;
      case 133:
         FqlGetMutualFriends var264 = (FqlGetMutualFriends)var1;
         Object var312 = null;
         short var314 = 200;
         if(var2 == var314) {
            Map var315 = var264.getMutualFriends();
         }

         Context var316 = this.mContext;
         AppSession.onServiceOperationComplete(var316, var5, var2, var3, var4, var312, (Object)null);
         return;
      case 140:
         FacebookAffiliation.requestCompleted();
         Context var321 = this.mContext;
         AppSession.onServiceOperationComplete(var321, var5, var2, var3, var4, (Object)null, (Object)null);
         return;
      case 211:
         UsersSearch var241 = (UsersSearch)var1;
         Context var242 = this.mContext;
         Integer var243 = Integer.valueOf(var241.getStartResults());
         Integer var244 = Integer.valueOf(var241.getTotalResults());
         AppSession.onServiceOperationComplete(var242, var5, var2, var3, var4, var243, var244);
         return;
      case 1000:
      case 1001:
         Context var326 = this.mContext;
         AppSession.onServiceOperationComplete(var326, var5, var2, var3, var4, var1, (Object)null);
         return;
      default:
         Context var15 = this.mContext;
         AppSession.onServiceOperationComplete(var15, var5, var2, var3, var4, (Object)null, (Object)null);
      }
   }

   public void onOperationProgress(ApiMethod var1, long var2, long var4) {
      Intent var6 = var1.getIntent();
      switch(var6.getIntExtra("type", 0)) {
      case 65:
         Context var7 = this.mContext;
         Integer var8 = Integer.valueOf((int)(100L * var2 / var4));
         AppSession.onServiceOperationProgress(var7, var6, var8, (Object)null);
         return;
      default:
      }
   }

   public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {}

   public void onStart(Intent var1, int var2) {
      super.onStart(var1, var2);
      if(var1 != null) {
         int var6;
         Object var7;
         String var4 = "type";
         byte var5 = 0;
         var6 = var1.getIntExtra(var4, var5);
         var7 = null;
         String var80;
         ArrayList var751;
         long[] var739;
         label120:
         switch(var6) {
         case 1:
         case 301:
            Context var14 = this.getApplicationContext();
            String var16 = "un";
            String var17 = var1.getStringExtra(var16);
            String var19 = "pwd";
            String var20 = var1.getStringExtra(var19);
            var7 = new AuthLogin(var14, var1, var17, var20, this);
            break;
         case 2:
            Iterator var57 = this.mPendingOps.values().iterator();

            while(var57.hasNext()) {
               ApiMethod var58 = (ApiMethod)var57.next();
               byte var59 = 0;
               var58.cancel((boolean)var59);
            }

            this.mPendingOps.clear();
            this.mHighPriorityDownloadQueue.clear();
            this.mMediumPriorityDownloadQueue.clear();
            this.mLowPriorityDownloadQueue.clear();
            var7 = new AuthLogout;
            Context var60 = this.getApplicationContext();
            String var62 = "session_key";
            String var63 = var1.getStringExtra(var62);
            var7.<init>(var60, var1, var63, this);
            break;
         case 3:
            ArrayList var23 = new ArrayList();
            HashMap var24 = new HashMap();
            String var26 = "uid";
            long var27 = 65535L;
            long var29 = var1.getLongExtra(var26, var27);
            String var32 = "session_key";
            String var33 = var1.getStringExtra(var32);
            Long var34 = Long.valueOf(var29);
            var24.put(var34, (Object)null);
            Context var36 = this.mContext;
            FqlGetUsersProfile var38 = new FqlGetUsersProfile(var36, var1, var33, (ApiMethodListener)null, var24, FacebookUser.class);
            boolean var41 = var23.add(var38);
            StreamGetFilters var42 = new StreamGetFilters;
            Context var43 = this.mContext;
            Object var48 = null;
            var42.<init>(var43, var1, var33, (ApiMethodListener)var48);
            boolean var51 = var23.add(var42);
            var7 = new BatchRun;
            Context var52 = this.mContext;
            var7.<init>(var52, var1, var33, var23, this);
            break;
         case 30:
            String var79 = "session_key";
            var80 = var1.getStringExtra(var79);
            String var82 = "subject";
            long[] var83 = var1.getLongArrayExtra(var82);
            String var85 = "stream_type";
            String var86 = var1.getStringExtra(var85);
            FacebookStreamType var87 = FacebookStreamType.NEWSFEED_STREAM;
            if(var86 != null) {
               var87 = FacebookStreamType.valueOf(var86);
            }

            var7 = new FqlGetStream;
            Context var88 = this.mContext;
            String var90 = "start";
            long var91 = 65535L;
            long var93 = var1.getLongExtra(var90, var91);
            String var96 = "end";
            long var97 = 65535L;
            long var99 = var1.getLongExtra(var96, var97);
            String var102 = "uid";
            long var103 = 65535L;
            long var105 = var1.getLongExtra(var102, var103);
            String var109;
            if(var83 == null) {
               String var108 = "session_filter_key";
               var109 = var1.getStringExtra(var108);
            } else {
               var109 = null;
            }

            String var111 = "limit";
            byte var112 = -1;
            int var113 = var1.getIntExtra(var111, var112);
            var7.<init>(var88, var1, var80, this, var93, var99, var83, var105, var109, var113, var87);
            break;
         case 31:
            var7 = new StreamGetComments;
            Context var128 = this.getApplicationContext();
            String var130 = "session_key";
            String var131 = var1.getStringExtra(var130);
            String var133 = "post_id";
            String var134 = var1.getStringExtra(var133);
            var7.<init>(var128, var1, var131, var134, this);
            break;
         case 33:
            var7 = new StreamAddComment;
            Context var138 = this.mContext;
            String var140 = "session_key";
            String var141 = var1.getStringExtra(var140);
            String var143 = "session_user_id";
            long var144 = 65535L;
            long var146 = var1.getLongExtra(var143, var144);
            String var149 = "post_id";
            String var150 = var1.getStringExtra(var149);
            String var152 = "status";
            String var153 = var1.getStringExtra(var152);
            var7.<init>(var138, var1, var141, var146, var150, var153, this);
            break;
         case 34:
            var7 = new StreamRemove;
            Context var118 = this.mContext;
            String var120 = "session_key";
            String var121 = var1.getStringExtra(var120);
            String var123 = "post_id";
            String var124 = var1.getStringExtra(var123);
            var7.<init>(var118, var1, var121, var124, this);
            break;
         case 35:
            var7 = new StreamRemoveComment;
            Context var157 = this.mContext;
            String var159 = "session_key";
            String var160 = var1.getStringExtra(var159);
            String var162 = "item_id";
            String var163 = var1.getStringExtra(var162);
            var7.<init>(var157, var1, var160, var163, this);
            break;
         case 36:
            var7 = new StreamAddLike;
            Context var167 = this.mContext;
            String var169 = "session_key";
            String var170 = var1.getStringExtra(var169);
            String var172 = "session_user_id";
            long var173 = 65535L;
            long var175 = var1.getLongExtra(var172, var173);
            String var178 = "post_id";
            String var179 = var1.getStringExtra(var178);
            var7.<init>(var167, var1, var170, var175, var179, this);
            break;
         case 37:
            var7 = new StreamRemoveLike;
            Context var183 = this.mContext;
            String var185 = "session_key";
            String var186 = var1.getStringExtra(var185);
            String var188 = "post_id";
            String var189 = var1.getStringExtra(var188);
            var7.<init>(var183, var1, var186, var189, this);
            break;
         case 50:
            var7 = new NotificationsGet;
            Context var308 = this.mContext;
            String var310 = "session_key";
            String var311 = var1.getStringExtra(var310);
            var7.<init>(var308, var1, var311, this);
            break;
         case 51:
            var7 = new FqlSyncNotifications;
            Context var317 = this.mContext;
            String var319 = "session_key";
            String var320 = var1.getStringExtra(var319);
            String var322 = "session_user_id";
            long var323 = 65535L;
            long var325 = var1.getLongExtra(var322, var323);
            var7.<init>(var317, var1, var320, var325, this);
            break;
         case 52:
            var7 = new NotificationsMarkRead;
            Context var330 = this.mContext;
            String var332 = "session_key";
            String var333 = var1.getStringExtra(var332);
            String var335 = "item_id";
            long[] var336 = var1.getLongArrayExtra(var335);
            var7.<init>(var330, var1, var333, var336, this);
            break;
         case 60:
            var7 = new SyncAlbums;
            Context var340 = this.mContext;
            String var342 = "session_key";
            String var343 = var1.getStringExtra(var342);
            String var345 = "uid";
            long var346 = 65535L;
            long var348 = var1.getLongExtra(var345, var346);
            String var351 = "aid";
            String[] var352 = var1.getStringArrayExtra(var351);
            var7.<init>(var340, var1, var343, var348, var352, this);
            break;
         case 61:
            var7 = new PhotosCreateAlbum;
            Context var356 = this.mContext;
            String var358 = "session_key";
            String var359 = var1.getStringExtra(var358);
            String var361 = "name";
            String var362 = var1.getStringExtra(var361);
            String var364 = "location";
            String var365 = var1.getStringExtra(var364);
            String var367 = "description";
            String var368 = var1.getStringExtra(var367);
            String var370 = "visibility";
            String var371 = var1.getStringExtra(var370);
            var7.<init>(var356, var1, var359, var362, var365, var368, var371, this);
            break;
         case 62:
            var7 = new PhotosEditAlbum;
            Context var375 = this.mContext;
            String var377 = "session_key";
            String var378 = var1.getStringExtra(var377);
            String var380 = "aid";
            String var381 = var1.getStringExtra(var380);
            String var383 = "name";
            String var384 = var1.getStringExtra(var383);
            String var386 = "location";
            String var387 = var1.getStringExtra(var386);
            String var389 = "description";
            String var390 = var1.getStringExtra(var389);
            String var392 = "visibility";
            String var393 = var1.getStringExtra(var392);
            var7.<init>(var375, var1, var378, var381, var384, var387, var390, var393, this);
            break;
         case 63:
            var7 = new PhotosDeleteAlbum;
            Context var397 = this.mContext;
            String var399 = "session_key";
            String var400 = var1.getStringExtra(var399);
            String var402 = "uid";
            long var403 = 65535L;
            long var405 = var1.getLongExtra(var402, var403);
            String var408 = "aid";
            String var409 = var1.getStringExtra(var408);
            var7.<init>(var397, var1, var400, var405, var409, this);
            break;
         case 64:
            var7 = new PhotosGetPhotos;
            Context var413 = this.mContext;
            String var415 = "session_key";
            String var416 = var1.getStringExtra(var415);
            String var418 = "aid";
            String var419 = var1.getStringExtra(var418);
            String var421 = "pid";
            String[] var422 = var1.getStringArrayExtra(var421);
            String var424 = "uid";
            long var425 = 65535L;
            long var427 = var1.getLongExtra(var424, var425);
            String var430 = "start";
            byte var431 = 0;
            int var432 = var1.getIntExtra(var430, var431);
            String var434 = "limit";
            byte var435 = -1;
            int var436 = var1.getIntExtra(var434, var435);
            var7.<init>(var413, var1, var416, var419, var422, var427, var432, var436, this);
            break;
         case 65:
            var7 = new PhotosUpload;
            Context var440 = this.mContext;
            String var442 = "session_key";
            String var443 = var1.getStringExtra(var442);
            String var445 = "caption";
            String var446 = var1.getStringExtra(var445);
            String var448 = "uri";
            String var449 = var1.getStringExtra(var448);
            String var451 = "aid";
            String var452 = var1.getStringExtra(var451);
            String var454 = "checkin_id";
            long var455 = 65535L;
            long var457 = var1.getLongExtra(var454, var455);
            String var460 = "profile_id";
            long var461 = 65535L;
            long var463 = var1.getLongExtra(var460, var461);
            String var466 = "extra_photo_publish";
            byte var467 = 0;
            boolean var468 = var1.getBooleanExtra(var466, (boolean)var467);
            String var470 = "extra_place";
            long var471 = 65535L;
            long var473 = var1.getLongExtra(var470, var471);
            String var476 = "tags";
            String var477 = var1.getStringExtra(var476);
            String var479 = "extra_status_target_id";
            long var480 = 65535L;
            long var482 = var1.getLongExtra(var479, var480);
            String var485 = "extra_status_privacy";
            String var486 = var1.getStringExtra(var485);
            var7.<init>(var440, var1, var443, var446, var449, this, var452, var457, var463, var468, var473, var477, var482, var486);
            break;
         case 66:
            var7 = new PhotosEditPhoto;
            Context var490 = this.mContext;
            String var492 = "session_key";
            String var493 = var1.getStringExtra(var492);
            String var495 = "pid";
            String var496 = var1.getStringExtra(var495);
            String var498 = "caption";
            String var499 = var1.getStringExtra(var498);
            var7.<init>(var490, var1, var493, var496, var499, this);
            break;
         case 67:
            var7 = new PhotosDeletePhoto;
            Context var503 = this.mContext;
            String var505 = "session_key";
            String var506 = var1.getStringExtra(var505);
            String var508 = "aid";
            String var509 = var1.getStringExtra(var508);
            String var511 = "pid";
            String var512 = var1.getStringExtra(var511);
            var7.<init>(var503, var1, var506, var509, var512, this);
            break;
         case 68:
            var7 = new PhotosAddTag;
            Context var516 = this.mContext;
            String var518 = "session_key";
            String var519 = var1.getStringExtra(var518);
            String var521 = "pid";
            String var522 = var1.getStringExtra(var521);
            String var524 = "tags";
            String var525 = var1.getStringExtra(var524);
            var7.<init>(var516, var1, var519, var522, var525, this);
            break;
         case 69:
            var7 = new PhotosGetTags;
            Context var529 = this.mContext;
            String var531 = "session_key";
            String var532 = var1.getStringExtra(var531);
            String var534 = "pid";
            String var535 = var1.getStringExtra(var534);
            var7.<init>(var529, var1, var532, var535, this);
            break;
         case 70:
            var7 = new SyncPhotoComments;
            Context var539 = this.getApplicationContext();
            String var541 = "session_key";
            String var542 = var1.getStringExtra(var541);
            String var544 = "pid";
            String var545 = var1.getStringExtra(var544);
            var7.<init>(var539, var1, var542, var545, this);
            break;
         case 71:
            var7 = new PhotosAddComment;
            Context var549 = this.mContext;
            String var551 = "session_key";
            String var552 = var1.getStringExtra(var551);
            String var554 = "uid";
            long var555 = 65535L;
            long var557 = var1.getLongExtra(var554, var555);
            String var560 = "pid";
            String var561 = var1.getStringExtra(var560);
            String var563 = "status";
            String var564 = var1.getStringExtra(var563);
            var7.<init>(var549, var1, var552, var557, var561, var564, this);
            break;
         case 72:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
            var7 = new PhotoDownload;
            Context var568 = this.mContext;
            String var570 = "uid";
            long var571 = 65535L;
            long var573 = var1.getLongExtra(var570, var571);
            String var576 = "aid";
            String var577 = var1.getStringExtra(var576);
            String var579 = "pid";
            String var580 = var1.getStringExtra(var579);
            String var582 = "uri";
            String var583 = var1.getStringExtra(var582);
            String var585 = "type";
            byte var586 = -1;
            int var587 = var1.getIntExtra(var585, var586);
            var7.<init>(var568, var1, var573, var577, var580, var583, var587, this);
            this.addToBestPlaceInDownloadQueue((ApiMethod)var7);
            int var593 = this.mHighPriorityDownloadQueue.size();
            int var594 = this.mMediumPriorityDownloadQueue.size();
            int var595 = var593 + var594;
            int var596 = this.mLowPriorityDownloadQueue.size();
            if(var595 + var596 > 3) {
               return;
            }
            break;
         case 80:
            var7 = new ConnectionsSync;
            Context var275 = this.mContext;
            String var277 = "session_key";
            String var278 = var1.getStringExtra(var277);
            String var280 = "session_user_id";
            long var281 = 65535L;
            long var283 = var1.getLongExtra(var280, var281);
            String var286 = "un";
            String var287 = var1.getStringExtra(var286);
            var7.<init>(var275, var1, var278, var283, var287, this);
            break;
         case 81:
            var7 = new FqlStatusQuery;
            Context var291 = this.mContext;
            String var293 = "session_key";
            String var294 = var1.getStringExtra(var293);
            Object[] var295 = new Object[1];
            String var297 = "session_user_id";
            long var298 = 65535L;
            Long var300 = Long.valueOf(var1.getLongExtra(var297, var298));
            var295[0] = var300;
            String var301 = String.format("SELECT uid,first_name,last_name,name,status,pic_square FROM user WHERE ((uid IN (SELECT target_id FROM connection WHERE source_id=%1$d AND target_type=\'user\' AND is_following=1)) AND (status.message != \'\')) ORDER BY status.time DESC LIMIT 25", var295);
            String var303 = "un";
            String var304 = var1.getStringExtra(var303);
            var7.<init>(var291, var1, var294, var301, var304, this);
            break;
         case 82:
            LinkedHashMap var193 = new LinkedHashMap();
            String var195 = "session_key";
            var80 = var1.getStringExtra(var195);
            String var197 = "uid";
            long var198 = 65535L;
            long var200 = var1.getLongExtra(var197, var198);
            String var203 = "session_user_id";
            long var204 = 65535L;
            long var206 = var1.getLongExtra(var203, var204);
            HashMap var208 = new HashMap();
            Long var209 = new Long(var200);
            var208.put(var209, (Object)null);
            Context var214 = this.mContext;
            FqlGetUsersProfile var216 = new FqlGetUsersProfile(var214, var1, var80, (ApiMethodListener)null, var208, FacebookUserFull.class);
            String var218 = "user";
            var193.put(var218, var216);
            Context var221 = this.mContext;
            FqlGetUsersProfile var224 = new FqlGetUsersProfile(var221, var1, var80, (ApiMethodListener)null, "uid IN (SELECT significant_other_id FROM #user)", FacebookUser.class);
            String var226 = "significant_other";
            var193.put(var226, var224);
            if(var200 != var206) {
               Context var229 = this.mContext;
               FqlGetUsersFriendStatus var236 = new FqlGetUsersFriendStatus(var229, var1, var80, var206, var200, (ApiMethodListener)null);
               String var238 = "arefriends";
               var193.put(var238, var236);
            }

            var7 = new FqlMultiQuery;
            Context var241 = this.mContext;
            var7.<init>(var241, var1, var80, var193, this);
            break;
         case 83:
            LinkedHashMap var247 = new LinkedHashMap();
            String var249 = "session_key";
            String var250 = var1.getStringExtra(var249);
            String var252 = "uid";
            long var253 = 65535L;
            long var255 = var1.getLongExtra(var252, var253);
            HashMap var257 = new HashMap();
            Long var258 = new Long(var255);
            var257.put(var258, (Object)null);
            Context var263 = this.mContext;
            FqlGetUsersProfile var265 = new FqlGetUsersProfile(var263, var1, var250, (ApiMethodListener)null, var257, FacebookUser.class);
            String var267 = "user";
            var247.put(var267, var265);
            var7 = new FqlMultiQuery;
            Context var270 = this.mContext;
            var7.<init>(var270, var1, var250, var247, this);
            break;
         case 90:
         case 91:
         case 92:
         case 201:
         case 202:
         case 203:
         case 300:
            Context var826 = this.mContext;
            AppSession.onServiceOperationComplete(var826, var1, 200, "Ok", (Exception)null, (Object)null, (Object)null);
            break;
         case 100:
            var7 = new ProfileImageDownload;
            Context var597 = this.mContext;
            String var599 = "uid";
            long var600 = 65535L;
            long var602 = var1.getLongExtra(var599, var600);
            String var605 = "uri";
            String var606 = var1.getStringExtra(var605);
            String var607 = FileUtils.buildFilename(this.mContext);
            var7.<init>(var597, var1, var602, var606, var607, this);
            this.addToBestPlaceInDownloadQueue((ApiMethod)var7);
            int var613 = this.mHighPriorityDownloadQueue.size();
            int var614 = this.mMediumPriorityDownloadQueue.size();
            int var615 = var613 + var614;
            int var616 = this.mLowPriorityDownloadQueue.size();
            if(var615 + var616 > 1) {
               return;
            }
            break;
         case 110:
            var7 = new MailboxSync;
            Context var617 = this.mContext;
            String var619 = "session_key";
            String var620 = var1.getStringExtra(var619);
            String var622 = "folder";
            byte var623 = 0;
            int var624 = var1.getIntExtra(var622, var623);
            String var626 = "start";
            byte var627 = 0;
            int var628 = var1.getIntExtra(var626, var627);
            String var630 = "limit";
            byte var631 = 20;
            int var632 = var1.getIntExtra(var630, var631);
            String var634 = "sync";
            byte var635 = 0;
            boolean var636 = var1.getBooleanExtra(var634, (boolean)var635);
            String var638 = "uid";
            long var639 = 65535L;
            long var641 = var1.getLongExtra(var638, var639);
            var7.<init>(var617, var1, var620, var624, var628, var632, var636, var641, this);
            break;
         case 111:
            String var664 = "profile_uid";
            long var665 = 65535L;
            long var667 = var1.getLongExtra(var664, var665);
            String var670 = "profile_first_name";
            String var671 = var1.getStringExtra(var670);
            String var673 = "profile_last_name";
            String var674 = var1.getStringExtra(var673);
            String var676 = "profile_display_name";
            String var677 = var1.getStringExtra(var676);
            String var679 = "profile_url";
            String var680 = var1.getStringExtra(var679);
            FacebookUser var681 = new FacebookUser(var667, var671, var674, var677, var680);
            String var683 = "uid";
            ArrayList var684 = (ArrayList)var1.getParcelableArrayListExtra(var683);
            var7 = new MailboxSend;
            Context var685 = this.mContext;
            String var687 = "session_key";
            String var688 = var1.getStringExtra(var687);
            String var690 = "subject";
            String var691 = var1.getStringExtra(var690);
            String var693 = "status";
            String var694 = var1.getStringExtra(var693);
            var7.<init>(var685, var1, var688, var681, var684, var691, var694, this);
            break;
         case 112:
            FacebookUser var699 = new FacebookUser;
            String var701 = "profile_uid";
            long var702 = 65535L;
            long var704 = var1.getLongExtra(var701, var702);
            String var707 = "profile_first_name";
            String var708 = var1.getStringExtra(var707);
            String var710 = "profile_last_name";
            String var711 = var1.getStringExtra(var710);
            String var713 = "profile_display_name";
            String var714 = var1.getStringExtra(var713);
            String var716 = "profile_url";
            String var717 = var1.getStringExtra(var716);
            var699.<init>(var704, var708, var711, var714, var717);
            var7 = new MailboxReply;
            Context var718 = this.mContext;
            String var720 = "session_key";
            String var721 = var1.getStringExtra(var720);
            String var723 = "tid";
            long var724 = 65535L;
            long var726 = var1.getLongExtra(var723, var724);
            String var729 = "status";
            String var730 = var1.getStringExtra(var729);
            var7.<init>(var718, var1, var721, var699, var726, var730, this);
            break;
         case 113:
            String var736 = "session_key";
            var80 = var1.getStringExtra(var736);
            String var738 = "tid";
            var739 = var1.getLongArrayExtra(var738);
            String var741 = "read";
            byte var742 = 1;
            boolean var743 = var1.getBooleanExtra(var741, (boolean)var742);
            if(var739.length == 1) {
               var7 = new MailboxMarkThread;
               Context var744 = this.mContext;
               long var745 = var739[0];
               var7.<init>(var744, var1, var80, var745, var743, this);
               break;
            } else {
               var751 = new ArrayList();
               int var962 = 0;

               while(true) {
                  int var753 = var739.length;
                  if(var962 >= var753) {
                     var7 = new BatchRun;
                     Context var766 = this.mContext;
                     var7.<init>(var766, var1, var80, var751, this);
                     break label120;
                  }

                  Context var756 = this.mContext;
                  long var757 = var739[var962];
                  MailboxMarkThread var762 = new MailboxMarkThread(var756, var1, var80, var757, var743, this);
                  boolean var765 = var751.add(var762);
                  ++var962;
               }
            }
         case 114:
            String var772 = "session_key";
            var80 = var1.getStringExtra(var772);
            String var774 = "tid";
            var739 = var1.getLongArrayExtra(var774);
            String var776 = "folder";
            byte var777 = 0;
            int var778 = var1.getIntExtra(var776, var777);
            if(var739.length == 1) {
               var7 = new MailboxDeleteThread;
               Context var779 = this.mContext;
               long var780 = var739[0];
               var7.<init>(var779, var1, var80, var780, var778, this);
               break;
            } else {
               var751 = new ArrayList();
               byte var752 = 0;

               while(true) {
                  int var786 = var739.length;
                  if(var752 >= var786) {
                     var7 = new BatchRun;
                     Context var800 = this.mContext;
                     var7.<init>(var800, var1, var80, var751, this);
                     break label120;
                  }

                  Context var789 = this.mContext;
                  long var790 = var739[var752];
                  MailboxDeleteThread var795 = new MailboxDeleteThread(var789, var1, var80, var790, var778, this);
                  boolean var798 = var751.add(var795);
                  int var799 = var752 + 1;
               }
            }
         case 115:
            var7 = new MailboxGetThreadMessages;
            Context var646 = this.mContext;
            String var648 = "session_key";
            String var649 = var1.getStringExtra(var648);
            String var651 = "folder";
            byte var652 = 0;
            int var653 = var1.getIntExtra(var651, var652);
            String var655 = "tid";
            long var656 = 65535L;
            long var658 = var1.getLongExtra(var655, var656);
            var7.<init>(var646, var1, var649, var653, var658, 0, '\uffff', this);
            break;
         case 121:
            String var829 = "session_key";
            String var830 = var1.getStringExtra(var829);
            String var832 = "eid";
            long var833 = 65535L;
            long var835 = var1.getLongExtra(var832, var833);
            String var838 = "rsvp_status";
            FacebookEvent.RsvpStatus var839 = FacebookEvent.getRsvpStatus(var1.getStringExtra(var838));
            var7 = new EventRsvp;
            Context var840 = this.mContext;
            var7.<init>(var840, var1, var830, this, var835, var839);
            break;
         case 122:
            String var846 = "session_key";
            String var847 = var1.getStringExtra(var846);
            String var849 = "eid";
            long var850 = 65535L;
            long var852 = var1.getLongExtra(var849, var850);
            var7 = new FqlGetEventMembers;
            Context var854 = this.mContext;
            var7.<init>(var854, var1, var847, this, var852);
            break;
         case 131:
            String var860 = "session_key";
            String var861 = var1.getStringExtra(var860);
            String var863 = "uid";
            long var864 = 65535L;
            long var866 = var1.getLongExtra(var863, var864);
            StringBuilder var868 = new StringBuilder;
            String var870 = "uid IN (SELECT uid_from FROM friend_request WHERE uid_to=";
            var868.<init>(var870);
            String var871 = String.valueOf(var866);
            StringBuilder var874 = var868.append(var871);
            String var876 = ")";
            var868.append(var876);
            var7 = new FqlGetUsersProfile;
            Context var878 = this.mContext;
            String var879 = var868.toString();
            var7.<init>(var878, var1, var861, this, var879, FacebookUser.class);
            break;
         case 132:
            String var885 = "session_key";
            String var886 = var1.getStringExtra(var885);
            String var888 = "uid";
            long var889 = 65535L;
            long var891 = var1.getLongExtra(var888, var889);
            String var894 = "confirm";
            byte var895 = 0;
            boolean var896 = var1.getBooleanExtra(var894, (boolean)var895);
            var7 = new FriendRequestRespond;
            Context var897 = this.mContext;
            var7.<init>(var897, var1, var886, this, var891, var896);
            break;
         case 133:
            String var903 = "session_key";
            String var904 = var1.getStringExtra(var903);
            String var906 = "uid";
            long var907 = 65535L;
            long var909 = var1.getLongExtra(var906, var907);
            StringBuilder var911 = new StringBuilder;
            String var913 = "uid2 IN (SELECT uid_from FROM friend_request WHERE uid_to=";
            var911.<init>(var913);
            String var914 = String.valueOf(var909);
            StringBuilder var917 = var911.append(var914);
            String var919 = ")";
            var911.append(var919);
            var7 = new FqlGetMutualFriends;
            Context var921 = this.mContext;
            String var922 = var911.toString();
            var7.<init>(var921, var1, var904, this, var909, var922);
            break;
         case 140:
            String var930 = "session_key";
            String var931 = var1.getStringExtra(var930);
            String var933 = "uid";
            long var934 = 65535L;
            long var936 = var1.getLongExtra(var933, var934);
            var7 = new FqlGetFacebookAffiliation;
            Context var938 = this.mContext;
            var7.<init>(var938, var1, var931, this, var936);
            break;
         case 211:
            var7 = new UsersSearch;
            Context var805 = this.mContext;
            String var807 = "rid";
            int var808 = Integer.parseInt(var1.getStringExtra(var807));
            String var810 = "session_key";
            String var811 = var1.getStringExtra(var810);
            String var813 = "subject";
            String var814 = var1.getStringExtra(var813);
            String var816 = "start";
            byte var817 = 0;
            int var818 = var1.getIntExtra(var816, var817);
            String var820 = "limit";
            byte var821 = 0;
            int var822 = var1.getIntExtra(var820, var821);
            var7.<init>(var805, var1, var808, var811, var814, var818, var822, this);
            break;
         case 400:
            var7 = new ConfigFetcher;
            Context var69 = this.mContext;
            String var71 = "session_key";
            String var72 = var1.getStringExtra(var71);
            var7.<init>(var69, var1, var72, this);
            break;
         case 1000:
         case 1001:
            String var944 = "rid";
            String var945 = var1.getStringExtra(var944);
            Map var946 = apiMethodReceiver;
            var7 = (ApiMethod)var946.remove(var945);
            if(var7 != null) {
               ((ApiMethod)var7).addIntentAndListener(var1, this);
            }
         }

         if(var7 != null) {
            String var9 = "rid";
            String var10 = var1.getStringExtra(var9);
            this.startOp(var10, (ApiMethod)var7);
         } else {
            short var952 = 2000;
            if(var6 == var952) {
               String var954 = "rid";
               String var955 = var1.getStringExtra(var954);
               Map var956 = this.mPendingOps;
               if(var956.containsKey(var955)) {
                  Map var958 = this.mPendingOps;
                  ApiMethod var960 = (ApiMethod)var958.get(var955);
                  byte var961 = 1;
                  var960.cancel((boolean)var961);
               }
            }
         }
      }
   }
}
