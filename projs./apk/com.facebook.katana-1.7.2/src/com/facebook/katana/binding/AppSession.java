package com.facebook.katana.binding;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Build.VERSION;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.RemoteViews;
import com.facebook.katana.C2DMReceiver;
import com.facebook.katana.Constants;
import com.facebook.katana.FacebookWidgetProvider;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.LoginNotificationActivity;
import com.facebook.katana.ProfileTabHostActivity;
import com.facebook.katana.WidgetActivity;
import com.facebook.katana.activity.media.ViewPhotoActivity;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ChatSession;
import com.facebook.katana.binding.ExtendedReq;
import com.facebook.katana.binding.FacebookStreamContainer;
import com.facebook.katana.binding.ManagedDataStore;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.binding.ServiceNotificationManager;
import com.facebook.katana.binding.StreamPhoto;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.binding.WorkerThread;
import com.facebook.katana.binding.AppSession.1;
import com.facebook.katana.binding.AppSession.2;
import com.facebook.katana.binding.AppSession.3;
import com.facebook.katana.binding.AppSession.LoginStatus;
import com.facebook.katana.binding.AppSession.StatusesQuery;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.features.composer.ComposerUserSettings;
import com.facebook.katana.features.places.PlacesNearby.PlacesNearbyArgType;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.model.FacebookNotifications;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.model.FacebookPhotoComment;
import com.facebook.katana.model.FacebookPhotoTag;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookStatus;
import com.facebook.katana.model.FacebookStreamType;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.model.FacebookUserFull;
import com.facebook.katana.model.FacebookVideo;
import com.facebook.katana.model.FacebookEvent.RsvpStatus;
import com.facebook.katana.model.FacebookPost.Comment;
import com.facebook.katana.model.FacebookPost.Attachment.MediaItem;
import com.facebook.katana.model.FacebookVideo.VideoSource;
import com.facebook.katana.provider.KeyValueManager;
import com.facebook.katana.provider.UserStatusesProvider;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.service.FacebookService;
import com.facebook.katana.service.UploadManager;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.AuthDeepLinkMethod;
import com.facebook.katana.service.method.FqlGetEvents;
import com.facebook.katana.service.method.FqlGetFriendCheckins;
import com.facebook.katana.service.method.FqlGetGatekeeperSettings;
import com.facebook.katana.service.method.FqlGetPlacesNearby;
import com.facebook.katana.service.method.GraphApiExchangeSession;
import com.facebook.katana.service.method.PlacesCheckin;
import com.facebook.katana.service.method.StreamPublish;
import com.facebook.katana.service.method.UserRegisterPushCallback;
import com.facebook.katana.service.method.UserUnregisterPushCallback;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.ReentrantCallback;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import com.facebook.katana.util.Tuple;
import com.facebook.katana.util.Utils;
import com.facebook.katana.util.StringUtils.StringProcessor;
import com.facebook.katana.util.StringUtils.TimeFormatStyle;
import com.facebook.katana.version.SDK5;
import com.facebook.katana.webview.MRoot;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;

public class AppSession {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final String ACTION_SERVICE = "com.facebook.katana.service.";
   private static final String ACTION_WIDGET_BUTTON_SHARE = "com.facebook.katana.widget.sharebutton";
   private static final String ACTION_WIDGET_EDIT_SHARE = "com.facebook.katana.widget.edit";
   public static final int GET_ALL = 0;
   public static final int GET_NEWER = 1;
   public static final int GET_OLDER = 2;
   private static final int MAX_POLL_ATTEMPTS = 3;
   public static final String PARAM_ACTOR = "actor";
   public static final String PARAM_AID = "aid";
   private static final String PARAM_ATTEMPT_COUNT = "extra_attempt";
   public static final String PARAM_CAPTION = "caption";
   public static final String PARAM_CHECKIN_ID = "checkin_id";
   public static final String PARAM_CONFIRM = "confirm";
   public static final String PARAM_DESCRIPTION = "description";
   public static final String PARAM_EID = "eid";
   public static final String PARAM_EMAIL = "email";
   public static final String PARAM_END = "end";
   public static final String PARAM_EXTENDED_TYPE = "extended_type";
   public static final String PARAM_FOLDER = "folder";
   public static final String PARAM_GET_TYPE = "app_value";
   public static final String PARAM_ITEM_ID = "item_id";
   public static final String PARAM_LIMIT = "limit";
   public static final String PARAM_LOCATION = "location";
   public static final String PARAM_NAME = "name";
   public static final String PARAM_PASSWORD = "pwd";
   public static final String PARAM_PID = "pid";
   public static final String PARAM_POST_ID = "post_id";
   public static final String PARAM_PROFILE_DISPLAY_NAME = "profile_display_name";
   public static final String PARAM_PROFILE_FIRST_NAME = "profile_first_name";
   public static final String PARAM_PROFILE_ID = "profile_id";
   public static final String PARAM_PROFILE_LAST_NAME = "profile_last_name";
   public static final String PARAM_PROFILE_PIC_URL = "profile_url";
   public static final String PARAM_PROFILE_UID = "profile_uid";
   public static final String PARAM_READ = "read";
   public static final String PARAM_REQUEST_ID = "rid";
   public static final String PARAM_RSVP_STATUS = "rsvp_status";
   public static final String PARAM_SESSION_FILTER_KEY = "session_filter_key";
   public static final String PARAM_SESSION_ID = "sid";
   public static final String PARAM_SESSION_KEY = "session_key";
   public static final String PARAM_SESSION_USER_ID = "session_user_id";
   public static final String PARAM_START = "start";
   public static final String PARAM_STREAM_TYPE = "stream_type";
   public static final String PARAM_SUBJECT = "subject";
   public static final String PARAM_SYNC = "sync";
   public static final String PARAM_TAGS = "tags";
   public static final String PARAM_TEXT = "status";
   public static final String PARAM_THREAD_ID = "tid";
   public static final String PARAM_TYPE = "type";
   public static final String PARAM_UID = "uid";
   public static final String PARAM_UPLOAD_ID = "upload_id";
   public static final String PARAM_URI = "uri";
   public static final String PARAM_USERNAME = "un";
   public static final String PARAM_VISIBILITY = "visibility";
   public static final String PUSH_PROTOCOL_PARAMS = "protocol_params";
   public static final int REQ_ALARM_POLL_CONTACT_INFO = 202;
   public static final int REQ_ALARM_POLL_NOTIFICATIONS = 201;
   public static final int REQ_ALARM_POLL_STATUS = 203;
   public static final int REQ_CANCEL_OP = 2000;
   public static final int REQ_CHECKIN = 503;
   public static final int REQ_DOWNLOAD_USER_IMAGE = 100;
   public static final int REQ_EVENT_GET_MEMBERS = 122;
   public static final int REQ_EVENT_RSVP = 121;
   public static final int REQ_EXTENDED = 1000;
   public static final int REQ_EXTENDED_V2 = 1001;
   public static final int REQ_EXTENDED_V2_NO_SUBTYPE = 1020;
   public static final int REQ_FRIEND_GET_REQUESTS = 131;
   public static final int REQ_FRIEND_REQUEST_GET_MUTUAL_FRIENDS = 133;
   public static final int REQ_FRIEND_REQUEST_RESPOND = 132;
   public static final int REQ_GET_ALL_GATEKEEPER_SETTINGS = 402;
   public static final int REQ_GET_CONFIG = 400;
   public static final int REQ_GET_EVENTS = 120;
   public static final int REQ_GET_FACEBOOK_AFFILIATION_STATUS = 140;
   public static final int REQ_GET_FRIEND_CHECKINS = 500;
   public static final int REQ_GET_GATEKEEPER_SETTING = 401;
   public static final int REQ_GET_GROUPS = 600;
   public static final int REQ_GET_GROUP_MEMBERS = 601;
   public static final int REQ_GET_PLACES_NEARBY = 501;
   public static final int REQ_GET_PLACE_CHECKINS = 502;
   public static final int REQ_GET_PROFILES = 84;
   public static final int REQ_GET_USER_SERVER_SETTINGS = 403;
   public static final int REQ_LOGIN = 1;
   public static final int REQ_LOGIN_MY_PROFILE = 3;
   public static final int REQ_LOGOUT = 2;
   public static final int REQ_MAILBOX_DELETE_THREAD = 114;
   public static final int REQ_MAILBOX_GET_THREAD_MESSAGES = 115;
   public static final int REQ_MAILBOX_MARK_THREAD = 113;
   public static final int REQ_MAILBOX_REPLY = 112;
   public static final int REQ_MAILBOX_SEND = 111;
   public static final int REQ_MAILBOX_SYNC = 110;
   public static final int REQ_NOTIFICATIONS_CLEAR = 300;
   public static final int REQ_NOTIFICATIONS_HISTORY = 51;
   public static final int REQ_NOTIFICATIONS_MARK_READ = 52;
   public static final int REQ_PHOTO_ADD_COMMENT = 71;
   public static final int REQ_PHOTO_ADD_TAGS = 68;
   public static final int REQ_PHOTO_CREATE_ALBUM = 61;
   public static final int REQ_PHOTO_DELETE_ALBUM = 63;
   public static final int REQ_PHOTO_DELETE_PHOTO = 67;
   public static final int REQ_PHOTO_DOWNLOAD_ALBUM_THUMBNAIL = 74;
   public static final int REQ_PHOTO_DOWNLOAD_FULL = 76;
   public static final int REQ_PHOTO_DOWNLOAD_PHOTO_THUMBNAIL = 75;
   public static final int REQ_PHOTO_DOWNLOAD_PROFILE_PHOTO = 72;
   public static final int REQ_PHOTO_DOWNLOAD_RAW = 77;
   public static final int REQ_PHOTO_DOWNLOAD_STREAM_PHOTO = 73;
   public static final int REQ_PHOTO_EDIT_ALBUM = 62;
   public static final int REQ_PHOTO_EDIT_PHOTO = 66;
   public static final int REQ_PHOTO_GET_ALBUMS = 60;
   public static final int REQ_PHOTO_GET_COMMENTS = 70;
   public static final int REQ_PHOTO_GET_PHOTOS = 64;
   public static final int REQ_PHOTO_GET_TAGS = 69;
   public static final int REQ_PHOTO_UPLOAD = 65;
   public static final int REQ_PLACES_FLAG = 505;
   public static final int REQ_PLACES_SETTINGS = 506;
   public static final int REQ_POLL_NOTIFICATIONS = 50;
   public static final int REQ_REGISTER_PUSH = 700;
   public static final int REQ_RELOGIN = 301;
   public static final int REQ_STREAM_ADD_COMMENT = 33;
   public static final int REQ_STREAM_ADD_LIKE = 36;
   public static final int REQ_STREAM_GET = 30;
   public static final int REQ_STREAM_GET_COMMENTS = 31;
   public static final int REQ_STREAM_REMOVE_COMMENT = 35;
   public static final int REQ_STREAM_REMOVE_LIKE = 37;
   public static final int REQ_STREAM_REMOVE_POST = 34;
   public static final int REQ_UNREGISTER_PUSH = 701;
   public static final int REQ_USERS_GET_BRIEF_INFO = 83;
   public static final int REQ_USERS_GET_INFO = 82;
   public static final int REQ_USERS_POLL_STATUSES = 81;
   public static final int REQ_USERS_SEARCH = 211;
   public static final int REQ_USERS_SYNC = 80;
   public static final int REQ_WIDGET_NEXT_STATUS = 90;
   public static final int REQ_WIDGET_PREV_STATUS = 91;
   public static final int REQ_WIDGET_SHARE = 92;
   private static boolean SIMULATE_102;
   private static final String TAG = "fbandroid";
   public static final String VISIBILITY_CUSTOM = "custom";
   public static final String VISIBILITY_EVERYONE = "everyone";
   public static final String VISIBILITY_FRIENDS = "friends";
   public static final String VISIBILITY_FRIENDS_OF_FRIENDS = "friends-of-friends";
   public static final String VISIBILITY_NETWORKS = "networks";
   public static boolean fixed102;
   private static boolean got102;
   private static String mActiveSessionId;
   private static TextAppearanceSpan mNameSpan;
   private static final Map<String, AppSession> mSessionMap;
   private static int mUniqueId;
   private int mCurrentStatusIndex;
   FacebookStreamContainer mHomeStreamContainer;
   private long mLatestPostTime;
   final ReentrantCallback<AppSessionListener> mListeners;
   private LoginStatus mLoginStatus;
   private final Map<String, Intent> mPendingServiceRequestsMap;
   private final StreamPhotosCache mPhotosCache;
   public final Map<Long, FacebookStreamContainer> mPlacesActivityContainerMap;
   private PendingIntent mPollingNotificationsAlarmIntent;
   private PendingIntent mPollingStatusAlarmIntent;
   private PendingIntent mPollingStreamAlarmIntent;
   private PendingIntent mPollingUsersAlarmIntent;
   private final Collection<Intent> mRequestsToHandleAfterLogin;
   private final String mSessionId;
   private FacebookSessionInfo mSessionInfo;
   private final List<FacebookStatus> mStatusesList;
   private final ProfileImagesCache mUserImageCache;
   public Map<Tuple<String, String>, String> mUserServerSettings;
   public Map<String, Long> mUserServerSettingsSync;
   private WakeLock mWakeLock;
   final Map<Long, FacebookStreamContainer> mWallContainerMap;
   private WorkerThread mWorkerThread;
   private boolean sessionInvalidHandled = 0;


   static {
      byte var0;
      if(!AppSession.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
      SIMULATE_102 = (boolean)0;
      got102 = (boolean)0;
      fixed102 = (boolean)0;
      mSessionMap = new HashMap();
   }

   public AppSession() {
      StringBuilder var1 = (new StringBuilder()).append("");
      String var2 = nextRequestId();
      String var3 = var1.append(var2).toString();
      this.mSessionId = var3;
      Map var4 = mSessionMap;
      String var5 = this.mSessionId;
      var4.put(var5, this);
      mActiveSessionId = this.mSessionId;
      HashMap var7 = new HashMap();
      this.mPendingServiceRequestsMap = var7;
      HashSet var8 = new HashSet();
      this.mRequestsToHandleAfterLogin = var8;
      ReentrantCallback var9 = new ReentrantCallback();
      this.mListeners = var9;
      LoginStatus var10 = LoginStatus.STATUS_LOGGED_OUT;
      this.mLoginStatus = var10;
      HashMap var11 = new HashMap();
      this.mWallContainerMap = var11;
      HashMap var12 = new HashMap();
      this.mPlacesActivityContainerMap = var12;
      1 var13 = new 1(this);
      ProfileImagesCache var14 = new ProfileImagesCache(var13);
      this.mUserImageCache = var14;
      2 var15 = new 2(this);
      StreamPhotosCache var16 = new StreamPhotosCache(var15);
      this.mPhotosCache = var16;
      ArrayList var17 = new ArrayList();
      this.mStatusesList = var17;
      this.mLatestPostTime = 65535L;
      this.initializeSettingsState();
   }

   // $FF: synthetic method
   static String access$000(AppSession var0, Context var1, long var2, String var4) {
      return var0.downloadUserImage(var1, var2, var4);
   }

   // $FF: synthetic method
   static int access$100(AppSession var0) {
      return var0.mCurrentStatusIndex;
   }

   // $FF: synthetic method
   static List access$200(AppSession var0) {
      return var0.mStatusesList;
   }

   // $FF: synthetic method
   static void access$300(AppSession var0, Context var1, FacebookStatus var2, int var3, Bitmap var4) {
      var0.updateWidget(var1, var2, var3, var4);
   }

   // $FF: synthetic method
   static String access$400(AppSession var0, Context var1, int var2, long var3, String var5, String var6, String var7) {
      return var0.downloadPhoto(var1, var2, var3, var5, var6, var7);
   }

   private void acquireWakeLock(Context var1) {
      if(this.mWakeLock == null) {
         WakeLock var2 = ((PowerManager)var1.getSystemService("power")).newWakeLock(1, "FacebookService");
         this.mWakeLock = var2;
         this.mWakeLock.acquire();
      }
   }

   private static void clearCookies(Context var0) {
      CookieSyncManager var1 = CookieSyncManager.createInstance(var0);
      CookieManager.getInstance().removeAllCookie();
   }

   private static void clearPhotoUploads(Context var0) {
      Intent var1 = new Intent(var0, UploadManager.class);
      Intent var2 = var1.putExtra("type", 2);
      var0.startService(var1);
   }

   public static void clearWidget(Context var0, String var1, String var2) {
      String var3 = var0.getPackageName();
      RemoteViews var4 = new RemoteViews(var3, 2130903184);
      var4.setTextViewText(2131624146, var1);
      var4.setTextViewText(2131624291, var2);
      Intent var5 = IntentUriHandler.getIntentForUri(var0, "fb://feed");
      Intent var6 = var5.setFlags(335544320);
      PendingIntent var7 = PendingIntent.getActivity(var0, 0, var5, 268435456);
      var4.setOnClickPendingIntent(2131624290, var7);
      Intent var8 = new Intent(var0, LoginActivity.class);
      PendingIntent var9 = PendingIntent.getActivity(var0, 0, var8, 268435456);
      var4.setOnClickPendingIntent(2131624289, var9);
      ComponentName var10 = new ComponentName(var0, FacebookWidgetProvider.class);
      AppWidgetManager.getInstance(var0).updateAppWidget(var10, var4);
   }

   private String downloadPhoto(Context var1, int var2, long var3, String var5, String var6, String var7) {
      String var8 = nextRequestId();
      Intent var9 = new Intent(var1, FacebookService.class);
      var9.putExtra("type", var2);
      var9.putExtra("rid", var8);
      String var12 = this.mSessionId;
      var9.putExtra("sid", var12);
      var9.putExtra("uid", var3);
      var9.putExtra("aid", var5);
      var9.putExtra("pid", var6);
      var9.putExtra("uri", var7);
      this.postToService(var1, var8, var9);
      return var8;
   }

   private String downloadUserImage(Context var1, long var2, String var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      Intent var7 = var6.putExtra("type", 100);
      var6.putExtra("rid", var5);
      String var9 = this.mSessionId;
      var6.putExtra("sid", var9);
      var6.putExtra("uid", var2);
      var6.putExtra("uri", var4);
      this.postToService(var1, var5, var6);
      return var5;
   }

   public static AppSession getActiveSession(Context var0, boolean var1) {
      AppSession var4;
      if(mActiveSessionId != null) {
         Map var2 = mSessionMap;
         String var3 = mActiveSessionId;
         var4 = (AppSession)var2.get(var3);
      } else {
         String var5 = UserValuesManager.loadActiveSessionInfo(var0);
         if(var5 != null) {
            try {
               FacebookSessionInfo var6 = FacebookSessionInfo.parseFromJson(var5);
               if(var6.sessionSecret != null) {
                  AppSession var7 = new AppSession();
                  Intent var8 = new Intent(var0, FacebookService.class);
                  Intent var9 = var8.putExtra("type", 1);
                  String var10 = nextRequestId();
                  var8.putExtra("rid", var10);
                  String var12 = var7.mSessionId;
                  var8.putExtra("sid", var12);
                  onServiceOperationComplete(var0, var8, 200, "Ok", (Exception)null, var6, (Object)null);
               }
            } catch (Exception var33) {
               Map var30 = mSessionMap;
               String var31 = mActiveSessionId;
               var30.remove(var31);
               mActiveSessionId = null;
            }

            AppSession var16;
            if(mActiveSessionId != null) {
               Map var14 = mSessionMap;
               String var15 = mActiveSessionId;
               var16 = (AppSession)var14.get(var15);
            } else {
               var16 = null;
            }

            if(var16 != null) {
               if(var16.mSessionInfo.getProfile().getDisplayName().length() == 0) {
                  Intent var17 = new Intent(var0, FacebookService.class);
                  String var18 = nextRequestId();
                  Intent var19 = var17.putExtra("type", 3);
                  var17.putExtra("rid", var18);
                  String var21 = var16.mSessionId;
                  var17.putExtra("sid", var21);
                  String var23 = var16.mSessionInfo.sessionKey;
                  var17.putExtra("session_key", var23);
                  long var25 = var16.mSessionInfo.userId;
                  var17.putExtra("uid", var25);
                  var16.postToService(var0, var18, var17);
               }

               if(var16.mSessionInfo.oAuthToken == null) {
                  String var28 = GraphApiExchangeSession.RequestOAuthToken(var0);
               }

               var4 = var16;
            } else {
               var4 = var16;
            }
         } else {
            var4 = null;
         }
      }

      AppSession var35;
      label55: {
         if(var1 && var4 != null && var4.mSessionInfo != null && Constants.isBetaBuild()) {
            Boolean var34 = Gatekeeper.get(var0, "android_beta");
            if(var34 != null && !var34.booleanValue()) {
               var35 = null;
               FacebookAffiliation.maybeToast(var0);
               break label55;
            }
         }

         var35 = var4;
      }

      if(var35 == null) {
         clearPhotoUploads(var0);
      }

      return var35;
   }

   public static String getUsernameHint(Context var0) {
      String var1;
      String var2;
      try {
         var1 = KeyValueManager.getValue(var0, "last_username", (String)null);
      } catch (Throwable var4) {
         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   private void handleLogin(Context var1, FacebookSessionInfo var2) {
      if(SIMULATE_102 && got102) {
         fixed102 = (boolean)1;
      }

      LoginStatus var3 = LoginStatus.STATUS_LOGGED_IN;
      this.mLoginStatus = var3;
      String var4 = var2.toJSONObject().toString();
      UserValuesManager.saveActiveSessionInfo(var1, var4);
      this.mSessionInfo = var2;
      this.sessionInvalidHandled = (boolean)0;
      WorkerThread var5 = new WorkerThread();
      this.mWorkerThread = var5;
      ProfileImagesCache var6 = this.mUserImageCache;
      WorkerThread var7 = this.mWorkerThread;
      var6.open(var1, var7);
      StreamPhotosCache var8 = this.mPhotosCache;
      WorkerThread var9 = this.mWorkerThread;
      var8.open(var1, var9);
      List var10 = this.mStatusesList;
      List var11 = loadUserStatuses(var1);
      var10.addAll(var11);
      if(this.mStatusesList.size() == 0) {
         String var13 = var1.getString(2131362353);
         clearWidget(var1, var13, "");
      } else {
         this.mCurrentStatusIndex = 0;
         this.initiateWidgetUpdate(var1);
      }

      this.scheduleUsersPollingAlarm(var1, 1000, 0);
      this.scheduleNotificationsPollingAlarm(var1, 10000, 0);
      FacebookNotifications.load(var1);
      resumePhotoUploads(var1);
   }

   private void handleLoginResult(Context var1, String var2, int var3, String var4, Exception var5, Object var6, boolean var7) {
      String var8 = mActiveSessionId;
      String var9 = this.mSessionId;
      if(var8.equals(var9)) {
         short var11 = 200;
         if(var3 == var11) {
            FacebookSessionInfo var12 = (FacebookSessionInfo)var6;
            this.handleLogin(var1, var12);
            if(var7) {
               Toaster.toast(var1, 2131361950);
            }

            FacebookAffiliation.invalidateEmployeeBit(var1);
         } else {
            LoginStatus var21 = LoginStatus.STATUS_LOGGED_OUT;
            this.mLoginStatus = var21;
            if(var7) {
               this.postLoginRequiredNotification(var1);
               if(this.mRequestsToHandleAfterLogin.isEmpty()) {
                  Toaster.toast(var1, 2131361941);
               }
            } else {
               LoginStatus var22 = LoginStatus.STATUS_LOGGED_OUT;
               this.mLoginStatus = var22;
               Map var23 = mSessionMap;
               String var24 = this.mSessionId;
               var23.remove(var24);
            }
         }

         Iterator var16 = this.mRequestsToHandleAfterLogin.iterator();

         while(var16.hasNext()) {
            Intent var17 = (Intent)var16.next();
            int var18 = var17.getIntExtra("type", 0);
            if(var18 == 1000 || var18 == 1001) {
               String var19 = var17.getStringExtra("rid");
               ApiMethod var20 = (ApiMethod)FacebookService.apiMethodReceiver.get(var19);
               if(!$assertionsDisabled && var20 == null) {
                  throw new AssertionError();
               }

               FacebookSessionInfo var26 = this.mSessionInfo;
               var20.addAuthenticationData(var26);
            }

            String var27 = var17.getStringExtra("rid");
            this.postToService(var1, var27, var17);
         }

         this.mRequestsToHandleAfterLogin.clear();
      } else if(!var7) {
         Map var42 = mSessionMap;
         String var43 = this.mSessionId;
         var42.remove(var43);
      }

      LoginStatus var28 = this.mLoginStatus;
      LoginStatus var29 = LoginStatus.STATUS_LOGGED_IN;
      if(var28 == var29) {
         String var30 = FqlGetGatekeeperSettings.SyncAll(var1);
         String var31 = ComposerUserSettings.get(var1, "composer_tour_completed");
         if(!FacebookAffiliation.synced()) {
            FacebookAffiliation.syncStatus(var1);
         }

         if(FacebookAffiliation.shouldInitiateRequest()) {
            long var32 = this.mSessionInfo.userId;
            this.getFacebookAffiliationStatus(var1, var32);
         }
      }

      Iterator var35 = this.mListeners.getListeners().iterator();

      while(var35.hasNext()) {
         AppSessionListener var36 = (AppSessionListener)var35.next();
         var36.onLoginComplete(this, var2, var3, var4, var5);
      }

   }

   private void handleLogout(Context var1) {
      clearCookies(var1);
      String var2 = this.mSessionInfo.username;
      KeyValueManager.setValue(var1, "last_username", var2);
      C2DMReceiver.logout(var1);
      if(Integer.parseInt(VERSION.SDK) >= 5) {
         SDK5.clearWebStorage();
      }

      MRoot.reset(var1);
      KeyValueManager.delete(var1, "key=\"C2DMKey\"", (String[])null);
      LoginStatus var3 = LoginStatus.STATUS_LOGGED_OUT;
      this.mLoginStatus = var3;
      ChatSession.shutdown((boolean)0);
      this.mPendingServiceRequestsMap.clear();
      this.releaseStreamContainers();
      if(this.mHomeStreamContainer != null) {
         this.mHomeStreamContainer.clear();
      }

      this.mListeners.clear();
      this.mUserImageCache.close();
      if(this.mWorkerThread != null) {
         this.mWorkerThread.quit();
         this.mWorkerThread = null;
      }

      boolean var4 = PreferenceManager.getDefaultSharedPreferences(var1).edit().clear().commit();
      this.mPhotosCache.close();
      this.mStatusesList.clear();
      String var5 = var1.getString(2131362352);
      String var6 = var1.getString(2131362351);
      clearWidget(var1, var5, var6);
      clearPhotoUploads(var1);
      AlarmManager var7 = (AlarmManager)var1.getSystemService("alarm");
      if(this.mPollingUsersAlarmIntent != null) {
         PendingIntent var8 = this.mPollingUsersAlarmIntent;
         var7.cancel(var8);
         this.mPollingUsersAlarmIntent.cancel();
         this.mPollingUsersAlarmIntent = null;
      }

      if(this.mPollingStatusAlarmIntent != null) {
         PendingIntent var9 = this.mPollingStatusAlarmIntent;
         var7.cancel(var9);
         this.mPollingStatusAlarmIntent.cancel();
         this.mPollingStatusAlarmIntent = null;
      }

      if(this.mPollingStreamAlarmIntent != null) {
         PendingIntent var10 = this.mPollingStreamAlarmIntent;
         var7.cancel(var10);
         this.mPollingStreamAlarmIntent.cancel();
         this.mPollingStreamAlarmIntent = null;
      }

      if(this.mPollingNotificationsAlarmIntent != null) {
         PendingIntent var11 = this.mPollingNotificationsAlarmIntent;
         var7.cancel(var11);
         this.mPollingNotificationsAlarmIntent.cancel();
         this.mPollingNotificationsAlarmIntent = null;
      }

      this.initializeSettingsState();
      FacebookAffiliation.invalidateEmployeeBit(var1);
      ManagedDataStore.invalidateAllManagedDataStores();
      Map var12 = mSessionMap;
      String var13 = this.mSessionId;
      var12.remove(var13);
      String var15 = mActiveSessionId;
      String var16 = this.mSessionId;
      if(var15.equals(var16)) {
         ServiceNotificationManager.release(var1);
      }

      if(mSessionMap.size() == 0) {
         this.releaseWakeLock();
         Intent var17 = new Intent(var1, FacebookService.class);
         var1.stopService(var17);
         FacebookService.apiMethodReceiver.clear();
      }

      Utils.updateErrorReportingUserId(var1, (boolean)1);
   }

   private void initiateWidgetUpdate(Context var1) {
      List var2 = this.mStatusesList;
      int var3 = this.mCurrentStatusIndex;
      FacebookStatus var4 = (FacebookStatus)var2.get(var3);
      FacebookUser var5 = var4.getUser();
      Bitmap var6 = null;
      if(var5.mImageUrl != null) {
         ProfileImagesCache var7 = this.mUserImageCache;
         long var8 = var5.mUserId;
         String var10 = var5.mImageUrl;
         var6 = var7.get(var1, var8, var10);
      }

      int var11 = this.mCurrentStatusIndex;
      this.updateWidget(var1, var4, var11, var6);
   }

   private static boolean isLoginRequest(Intent var0) {
      int var1 = var0.getIntExtra("type", -1);
      boolean var2;
      if(1 != var1 && 301 != var1) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private boolean isWidgetEnabled(Context var1) {
      AppWidgetManager var2 = AppWidgetManager.getInstance(var1);
      ComponentName var3 = new ComponentName(var1, FacebookWidgetProvider.class);
      boolean var4;
      if(var2.getAppWidgetIds(var3).length == 0) {
         var4 = false;
      } else {
         var4 = true;
      }

      return var4;
   }

   private static String listToCommaString(List<?> var0, boolean var1) {
      StringBuffer var2 = new StringBuffer(64);
      boolean var3 = true;
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         if(!var3) {
            StringBuffer var6 = var2.append(",");
         } else {
            var3 = false;
         }

         if(var1) {
            StringBuffer var7 = var2.append("\'").append(var5).append("\'");
         } else {
            var2.append(var5);
         }
      }

      return var2.toString();
   }

   private static List<FacebookStatus> loadUserStatuses(Context var0) {
      ArrayList var1 = new ArrayList();
      ContentResolver var2 = var0.getContentResolver();
      Uri var3 = UserStatusesProvider.CONTENT_URI;
      String[] var4 = StatusesQuery.PROJECTION;
      Object var5 = null;
      Object var6 = null;
      Cursor var7 = var2.query(var3, var4, (String)null, (String[])var5, (String)var6);
      if(var7 != null) {
         if(var7.moveToFirst()) {
            do {
               long var8 = var7.getLong(0);
               String var10 = var7.getString(1);
               String var11 = var7.getString(2);
               String var12 = var7.getString(3);
               String var13 = var7.getString(4);
               FacebookUser var14 = new FacebookUser(var8, var10, var11, var12, var13);
               String var15 = var7.getString(6);
               long var16 = var7.getLong(5);
               FacebookStatus var18 = new FacebookStatus(var14, var15, var16);
               var1.add(var18);
            } while(var7.moveToNext());
         }

         var7.close();
      }

      return var1;
   }

   private static String nextRequestId() {
      StringBuilder var0 = (new StringBuilder()).append("");
      int var1 = mUniqueId;
      mUniqueId = var1 + 1;
      return var0.append(var1).toString();
   }

   private void onOperationComplete(Context var1, Intent var2, int var3, String var4, Exception var5, Object var6, Object var7) {
      String var9 = "type";
      byte var10 = -1;
      int var11 = var2.getIntExtra(var9, var10);
      if(ApiMethod.isSessionKeyError(var3, var5)) {
         if(SIMULATE_102) {
            got102 = (boolean)1;
         }

         if(this.getSessionInfo() != null) {
            String var15 = "session_key";
            String var16 = var2.getStringExtra(var15);
            String var17 = this.getSessionInfo().sessionKey;
            if(StringUtils.saneStringEquals(var16, var17) && !this.sessionInvalidHandled) {
               this.postLoginRequiredNotification(var1);
               byte var18 = 1;
               this.sessionInvalidHandled = (boolean)var18;
            }
         }
      }

      String var20 = "rid";
      String var21 = var2.getStringExtra(var20);
      if(var21 != null) {
         this.mPendingServiceRequestsMap.remove(var21);
      }

      Iterator var59;
      String var162;
      FacebookPost var166;
      Comment var183;
      int var334;
      String var426;
      long var719;
      int var705;
      Long var834;
      Boolean var835;
      label570:
      switch(var11) {
      case 1:
         this.handleLoginResult(var1, var21, var3, var4, var5, var6, (boolean)0);
         break;
      case 2:
         String var42 = mActiveSessionId;
         String var43 = this.mSessionId;
         if(var42.equals(var43)) {
            Object var45 = null;
            UserValuesManager.saveActiveSessionInfo(var1, (String)var45);
         }

         Iterator var46 = this.mPendingServiceRequestsMap.values().iterator();

         while(var46.hasNext()) {
            Intent var47 = (Intent)var46.next();
            String var49 = "type";
            byte var50 = -1;
            int var51 = var47.getIntExtra(var49, var50);
            byte var52 = 80;
            if(var51 == var52) {
               String var54 = "rid";
               String var55 = var47.getStringExtra(var54);
               Iterator var56 = this.mListeners.getListeners().iterator();

               while(var56.hasNext()) {
                  AppSessionListener var57 = (AppSessionListener)var56.next();
                  var57.onFriendsSyncComplete(this, var55, 400, "Canceled", (Exception)null);
               }
            }
         }

         var59 = this.mListeners.getListeners().iterator();

         while(var59.hasNext()) {
            AppSessionListener var60 = (AppSessionListener)var59.next();
            var60.onLogoutComplete(this, var21, var3, var4, var5);
         }

         this.handleLogout(var1);
         break;
      case 30:
         List var66 = (List)var6;
         FacebookStreamContainer var67 = null;
         String var69 = "subject";
         long[] var70 = var2.getLongArrayExtra(var69);
         short var72 = 200;
         if(var3 == var72) {
            if(var66.size() > 1 && var70 == null) {
               byte var74 = 0;
               long var75 = ((FacebookPost)var66.get(var74)).createdTime;
               long var77 = this.mLatestPostTime;
               if(var75 > var77) {
                  byte var80 = 0;
                  long var81 = ((FacebookPost)var66.get(var80)).createdTime;
                  this.mLatestPostTime = var81;
               }
            }

            if(var70 == null) {
               if(this.mHomeStreamContainer == null) {
                  FacebookStreamContainer var83 = new FacebookStreamContainer();
                  this.mHomeStreamContainer = var83;
               }

               FacebookStreamContainer var84 = this.mHomeStreamContainer;
               String var86 = "limit";
               byte var87 = 20;
               int var88 = var2.getIntExtra(var86, var87);
               String var90 = "app_value";
               byte var91 = 0;
               int var92 = var2.getIntExtra(var90, var91);
               var84.addPosts(var66, var88, var92);
               var67 = this.mHomeStreamContainer;
            } else {
               Map var129 = this.mWallContainerMap;
               Long var130 = Long.valueOf(var70[0]);
               var67 = (FacebookStreamContainer)var129.get(var130);
               if(var67 == null) {
                  FacebookStreamContainer var131 = new FacebookStreamContainer();
                  Map var132 = this.mWallContainerMap;
                  Long var133 = Long.valueOf(var70[0]);
                  Object var137 = var132.put(var133, var131);
               }

               String var139 = "limit";
               byte var140 = 20;
               int var141 = var2.getIntExtra(var139, var140);
               String var143 = "app_value";
               byte var144 = 0;
               int var145 = var2.getIntExtra(var143, var144);
               var67.addPosts(var66, var141, var145);
            }
         }

         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var97 = (AppSessionListener)var59.next();
            String var99 = "uid";
            long var100 = 65535L;
            long var102 = var2.getLongExtra(var99, var100);
            String var105 = "start";
            long var106 = 65535L;
            long var108 = var2.getLongExtra(var105, var106);
            String var111 = "end";
            long var112 = 65535L;
            long var114 = var2.getLongExtra(var111, var112);
            String var117 = "limit";
            byte var118 = 30;
            int var119 = var2.getIntExtra(var117, var118);
            String var121 = "app_value";
            byte var122 = -1;
            int var123 = var2.getIntExtra(var121, var122);
            var97.onStreamGetComplete(this, var21, var3, var4, var5, var102, var70, var108, var114, var119, var123, var66, var67);
         }
      case 31:
         String var161 = "post_id";
         var162 = var2.getStringExtra(var161);
         short var164 = 200;
         if(var3 == var164) {
            List var165 = (List)var6;
            var166 = FacebookStreamContainer.get(var162);
            if(var166 != null) {
               var166.updateComments(var165);
            }
         }

         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var169 = (AppSessionListener)var59.next();
            var169.onStreamGetCommentsComplete(this, var21, var3, var4, var5, var162);
         }
      case 33:
         String var175 = (String)var6;
         long var176 = this.mSessionInfo.userId;
         long var178 = System.currentTimeMillis() / 1000L;
         String var181 = "status";
         String var182 = var2.getStringExtra(var181);
         var183 = new Comment(var175, var176, var178, var182);
         String var185 = "actor";
         if(var2.hasExtra(var185)) {
            String var187 = "actor";
            FacebookProfile var188 = (FacebookProfile)var2.getParcelableExtra(var187);
            var183.setProfile(var188);
         } else {
            long var204 = this.mSessionInfo.userId;
            String var206 = this.mSessionInfo.getProfile().mDisplayName;
            String var207 = this.mSessionInfo.getProfile().mImageUrl;
            FacebookProfile var208 = new FacebookProfile(var204, var206, var207, 0);
            var183.setProfile(var208);
         }

         String var192 = "post_id";
         var162 = var2.getStringExtra(var192);
         short var194 = 200;
         if(var3 == var194) {
            var166 = FacebookStreamContainer.get(var162);
            if(var166 != null) {
               var166.addComment(var183);
            }
         }

         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var197 = (AppSessionListener)var59.next();
            var197.onStreamAddCommentComplete(this, var21, var3, var4, var5, var162, var183);
         }
      case 34:
         short var151 = 200;
         if(var3 == var151) {
            String var153 = "post_id";
            FacebookStreamContainer.remove(var2.getStringExtra(var153));
         }

         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var154 = (AppSessionListener)var59.next();
            var154.onStreamRemovePostComplete(this, var21, var3, var4, var5);
         }
      case 35:
         String var212 = "post_id";
         var162 = var2.getStringExtra(var212);
         short var214 = 200;
         if(var3 == var214) {
            var166 = FacebookStreamContainer.get(var162);
            if(var166 != null) {
               String var216 = "item_id";
               String var217 = var2.getStringExtra(var216);
               var166.deleteComment(var217);
            }
         }

         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var220 = (AppSessionListener)var59.next();
            var220.onStreamRemoveCommentComplete(this, var21, var3, var4, var5, var162);
         }
      case 36:
         String var227 = "post_id";
         var162 = var2.getStringExtra(var227);
         short var229 = 200;
         if(var3 == var229) {
            var166 = FacebookStreamContainer.get(var162);
            if(var166 != null) {
               byte var231 = 1;
               var166.setUserLikes((boolean)var231);
            }
         }

         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var232 = (AppSessionListener)var59.next();
            var232.onStreamAddLikeComplete(this, var21, var3, var4, var5, var162);
         }
      case 37:
         String var239 = "post_id";
         var162 = var2.getStringExtra(var239);
         short var241 = 200;
         if(var3 == var241) {
            var166 = FacebookStreamContainer.get(var162);
            if(var166 != null) {
               byte var243 = 0;
               var166.setUserLikes((boolean)var243);
            }
         }

         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var244 = (AppSessionListener)var59.next();
            var244.onStreamRemoveLikeComplete(this, var21, var3, var4, var5, var162);
         }
      case 50:
         short var352 = 200;
         if(var3 == var352) {
            FacebookNotifications var353 = (FacebookNotifications)var6;
            if(var353.hasNewNotifications()) {
               long var354 = this.mSessionInfo.userId;
               String var356 = this.mSessionInfo.sessionKey;
               String var357 = this.mSessionInfo.sessionSecret;
               ServiceNotificationManager.showNotification(var1, var354, var353, var356, var357);
            }

            byte var360 = -1;
            byte var361 = 0;
            this.scheduleNotificationsPollingAlarm(var1, var360, var361);
         } else {
            String var363 = "extra_attempt";
            byte var364 = 0;
            var334 = var2.getIntExtra(var363, var364) + 1;
            byte var366 = 3;
            if(var334 < var366) {
               char var369 = '\uea60';
               this.scheduleNotificationsPollingAlarm(var1, var369, var334);
            } else {
               byte var373 = -1;
               byte var374 = 0;
               this.scheduleNotificationsPollingAlarm(var1, var373, var374);
            }
         }
         break;
      case 51:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var375 = (AppSessionListener)var59.next();
            var375.onGetNotificationHistoryComplete(this, var21, var3, var4, var5);
         }
      case 60:
         String var382 = "uid";
         long var383 = 65535L;
         long var385 = var2.getLongExtra(var382, var383);
         String var388 = "aid";
         String[] var389 = var2.getStringArrayExtra(var388);
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var390 = (AppSessionListener)var59.next();
            var390.onPhotoGetAlbumsComplete(this, var21, var3, var4, var5, var389, var385);
         }
      case 61:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var396 = (AppSessionListener)var59.next();
            FacebookAlbum var397 = (FacebookAlbum)var6;
            var396.onPhotoCreateAlbumComplete(this, var21, var3, var4, var5, var397);
         }
      case 62:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var404 = (AppSessionListener)var59.next();
            String var406 = "aid";
            String var407 = var2.getStringExtra(var406);
            var404.onPhotoEditAlbumComplete(this, var21, var3, var4, var5, var407);
         }
      case 63:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var414 = (AppSessionListener)var59.next();
            String var416 = "aid";
            String var417 = var2.getStringExtra(var416);
            var414.onPhotoDeleteAlbumComplete(this, var21, var3, var4, var5, var417);
         }
      case 64:
         String var425 = "aid";
         var426 = var2.getStringExtra(var425);
         String var428 = "pid";
         String[] var429 = var2.getStringArrayExtra(var428);
         String var431 = "uid";
         long var432 = 65535L;
         long var434 = var2.getLongExtra(var431, var432);
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var436 = (AppSessionListener)var59.next();
            var436.onPhotoGetPhotosComplete(this, var21, var3, var4, var5, var426, var429, var434);
         }
      case 65:
         FacebookPhoto var442 = (FacebookPhoto)var6;
         String var443;
         if(var442 != null) {
            var426 = var442.getAlbumId();
            var443 = var442.getPhotoId();
         } else {
            var426 = null;
            var443 = null;
         }

         short var445 = 200;
         if(var3 == var445 && var426 != null) {
            ArrayList var446 = new ArrayList;
            byte var448 = 1;
            var446.<init>(var448);
            boolean var451 = var446.add(var426);
            long var454 = 65535L;
            this.photoGetAlbums(var1, var454, var446);
         }

         String var459 = "uri";
         String var460 = var2.getStringExtra(var459);
         int var461 = Integer.parseInt(var21);
         if(!ServiceNotificationManager.endPhotoUploadProgressNotification(var1, var461, var3, var460, var426, var443)) {
            File var465 = new File(var460);
            boolean var468 = var465.delete();
         }

         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var469 = (AppSessionListener)var59.next();
            String var471 = "upload_id";
            byte var472 = -1;
            int var473 = var2.getIntExtra(var471, var472);
            String var475 = "uri";
            String var476 = var2.getStringExtra(var475);
            String var478 = "checkin_id";
            long var479 = 65535L;
            long var481 = var2.getLongExtra(var478, var479);
            String var484 = "profile_id";
            long var485 = 65535L;
            long var487 = var2.getLongExtra(var484, var485);
            String var490 = "upload_manager_extras_local_id";
            long var491 = 65535L;
            long var493 = var2.getLongExtra(var490, var491);
            var469.onPhotoUploadComplete(this, var21, var3, var4, var5, var473, var442, var476, var481, var487, var493);
         }
      case 66:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var501 = (AppSessionListener)var59.next();
            String var503 = "aid";
            String var504 = var2.getStringExtra(var503);
            String var506 = "pid";
            String var507 = var2.getStringExtra(var506);
            var501.onPhotoEditPhotoComplete(this, var21, var3, var4, var5, var504, var507);
         }
      case 67:
         if(((Boolean)var6).booleanValue()) {
            ArrayList var514 = new ArrayList();
            String var516 = "aid";
            String var517 = var2.getStringExtra(var516);
            boolean var520 = var514.add(var517);
            long var523 = 65535L;
            this.photoGetAlbums(var1, var523, var514);
         }

         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var527 = (AppSessionListener)var59.next();
            String var529 = "aid";
            String var530 = var2.getStringExtra(var529);
            String var532 = "pid";
            String var533 = var2.getStringExtra(var532);
            var527.onPhotoDeletePhotoComplete(this, var21, var3, var4, var5, var530, var533);
         }
      case 68:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var540 = (AppSessionListener)var59.next();
            String var542 = "pid";
            String var543 = var2.getStringExtra(var542);
            List var544 = (List)var6;
            var540.onPhotoAddTagsComplete(this, var21, var3, var4, var5, var543, var544);
         }
      case 69:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var551 = (AppSessionListener)var59.next();
            List var552 = (List)var6;
            var551.onPhotoGetTagsComplete(this, var21, var3, var4, var5, var552);
         }
      case 70:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var559 = (AppSessionListener)var59.next();
            String var561 = "pid";
            String var562 = var2.getStringExtra(var561);
            List var563 = (List)var6;
            boolean var564 = ((Boolean)var7).booleanValue();
            var559.onPhotoGetCommentsComplete(this, var21, var3, var4, var5, var562, var563, var564);
         }
      case 71:
         var183 = null;
         short var572 = 200;
         if(var3 == var572) {
            FacebookPhotoComment var573 = (FacebookPhotoComment)var6;
            FacebookUser var574 = this.mSessionInfo.getProfile();
            FacebookProfile var575 = new FacebookProfile(var574);
            var573.setFromProfile(var575);
         }

         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var578 = (AppSessionListener)var59.next();
            String var580 = "pid";
            String var581 = var2.getStringExtra(var580);
            var578.onPhotoAddCommentComplete(this, var21, var3, var4, var5, var581, var183);
         }
      case 72:
      case 73:
         String var590 = "uri";
         String var591 = var2.getStringExtra(var590);
         StreamPhoto var592 = (StreamPhoto)var6;
         StreamPhotosCache var593 = this.mPhotosCache;
         var593.onDownloadComplete(var1, var3, var591, var592);
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var599 = (AppSessionListener)var59.next();
            Bitmap var600;
            if(var592 != null) {
               var600 = var592.getBitmap();
            } else {
               var600 = null;
            }

            var599.onDownloadStreamPhotoComplete(this, var21, var3, var4, var5, var591, var600);
         }
      case 74:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var607 = (AppSessionListener)var59.next();
            String var609 = "aid";
            String var610 = var2.getStringExtra(var609);
            var607.onDownloadAlbumThumbnailComplete(this, var21, var3, var4, var5, var610);
         }
      case 75:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var617 = (AppSessionListener)var59.next();
            String var619 = "aid";
            String var620 = var2.getStringExtra(var619);
            String var622 = "pid";
            String var623 = var2.getStringExtra(var622);
            var617.onDownloadPhotoThumbnailComplete(this, var21, var3, var4, var5, var620, var623);
         }
      case 76:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var630 = (AppSessionListener)var59.next();
            String var632 = "aid";
            String var633 = var2.getStringExtra(var632);
            String var635 = "pid";
            String var636 = var2.getStringExtra(var635);
            var630.onDownloadPhotoFullComplete(this, var21, var3, var4, var5, var633, var636);
         }
      case 77:
         Bitmap var643 = (Bitmap)var6;
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var644 = (AppSessionListener)var59.next();
            var644.onDownloadPhotoRawComplete(this, var21, var3, var4, var5, var643);
         }
      case 80:
         short var279 = 200;
         if(var3 == var279) {
            UserValuesManager.setLastContactsSync(var1);
            Map var280 = (Map)var6;
            if(this.mSessionInfo != null && this.mSessionInfo.getProfile() != null) {
               FacebookUser var281 = this.mSessionInfo.getProfile();
               Long var282 = Long.valueOf(var281.mUserId);
               String var283 = var281.mImageUrl;
               Object var287 = var280.put(var282, var283);
            }

            ProfileImagesCache var288 = this.mUserImageCache;
            var288.get(var1, var280);
            byte var293 = -1;
            byte var294 = 0;
            this.scheduleUsersPollingAlarm(var1, var293, var294);
         } else {
            String var306 = "extra_attempt";
            byte var307 = 0;
            int var308 = var2.getIntExtra(var306, var307) + 1;
            byte var310 = 3;
            if(var308 < var310) {
               int var313 = 300000;
               this.scheduleUsersPollingAlarm(var1, var313, var308);
            } else {
               byte var317 = -1;
               byte var318 = 0;
               this.scheduleUsersPollingAlarm(var1, var317, var318);
            }
         }

         short var297 = 1000;
         byte var298 = 0;
         this.scheduleStatusPollingAlarm(var1, var297, var298);
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var299 = (AppSessionListener)var59.next();
            var299.onFriendsSyncComplete(this, var21, var3, var4, var5);
         }
      case 81:
         short var320 = 200;
         if(var3 == var320) {
            this.mStatusesList.clear();
            List var321 = this.mStatusesList;
            List var322 = (List)var6;
            boolean var325 = var321.addAll(var322);
            byte var326 = 0;
            this.mCurrentStatusIndex = var326;
            if(this.mStatusesList.size() > 0) {
               this.initiateWidgetUpdate(var1);
            }

            byte var329 = -1;
            byte var330 = 0;
            this.scheduleStatusPollingAlarm(var1, var329, var330);
         } else {
            String var332 = "extra_attempt";
            byte var333 = 0;
            var334 = var2.getIntExtra(var332, var333) + 1;
            byte var336 = 3;
            if(var334 < var336) {
               short var339 = 30000;
               this.scheduleStatusPollingAlarm(var1, var339, var334);
            } else {
               if(this.mStatusesList.size() == 0) {
                  int var342 = 2131362350;
                  String var343 = var1.getString(var342);
                  String var346 = "";
                  clearWidget(var1, var343, var346);
               }

               byte var349 = -1;
               byte var350 = 0;
               this.scheduleStatusPollingAlarm(var1, var349, var350);
            }
         }
         break;
      case 82:
         FacebookUserFull var896 = (FacebookUserFull)var6;
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var251 = (AppSessionListener)var59.next();
            String var253 = "uid";
            long var254 = 65535L;
            long var256 = var2.getLongExtra(var253, var254);
            boolean var258 = ((Boolean)var7).booleanValue();
            var251.onUsersGetInfoComplete(this, var21, var3, var4, var5, var256, var896, var258);
         }
      case 83:
         FacebookUser var250 = (FacebookUser)var6;
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var265 = (AppSessionListener)var59.next();
            String var267 = "uid";
            long var268 = 65535L;
            long var270 = var2.getLongExtra(var267, var268);
            var265.onUsersGetInfoComplete(this, var21, var3, var4, var5, var270, var250, (boolean)0);
         }
      case 90:
         int var818 = this.mStatusesList.size();
         if(var818 != 0) {
            label374: {
               if(this.mCurrentStatusIndex >= 0) {
                  int var819 = this.mCurrentStatusIndex;
                  int var820 = var818 - 1;
                  if(var819 < var820) {
                     int var821 = this.mCurrentStatusIndex + 1;
                     this.mCurrentStatusIndex = var821;
                     this.initiateWidgetUpdate(var1);
                     break label374;
                  }
               }

               int var822 = this.mCurrentStatusIndex;
               int var823 = var818 - 1;
               if(var822 == var823) {
                  ;
               }
            }
         }
         break;
      case 91:
         if(this.mStatusesList.size() != 0 && this.mCurrentStatusIndex != 0 && this.mCurrentStatusIndex > 0) {
            int var824 = this.mCurrentStatusIndex - 1;
            this.mCurrentStatusIndex = var824;
            this.initiateWidgetUpdate(var1);
         }
         break;
      case 92:
         long var825 = this.mSessionInfo.userId;
         String var828 = "status";
         String var829 = var2.getStringExtra(var828);
         String var831 = "status";
         String var832 = var2.getStringExtra(var831);
         StreamPublish.Publish(var1, var825, var829, var832, (Set)null, (boolean)1, (FacebookProfile)null);
         break;
      case 100:
         String var651 = "uid";
         long var652 = 65535L;
         long var654 = var2.getLongExtra(var651, var652);
         ProfileImage var656 = (ProfileImage)var6;
         ProfileImagesCache var657 = this.mUserImageCache;
         var657.onDownloadComplete(var1, var3, var654, var656);
         short var662 = 200;
         if(var3 == var662) {
            int var663 = this.mCurrentStatusIndex;
            int var664 = this.mStatusesList.size();
            if(var663 < var664) {
               List var665 = this.mStatusesList;
               int var666 = this.mCurrentStatusIndex;
               if(((FacebookStatus)var665.get(var666)).getUser().mUserId == var654) {
                  List var667 = this.mStatusesList;
                  int var668 = this.mCurrentStatusIndex;
                  FacebookStatus var669 = (FacebookStatus)var667.get(var668);
                  int var670 = this.mCurrentStatusIndex;
                  Bitmap var671 = var656.getBitmap();
                  this.updateWidget(var1, var669, var670, var671);
               }
            }

            if(this.mSessionInfo.userId == var654) {
               FacebookUser var677 = this.mSessionInfo.getProfile();
               String var678 = var656.url;
               String var679 = var677.mImageUrl;
               if(!var678.equals(var679)) {
                  FacebookSessionInfo var680 = this.mSessionInfo;
                  long var681 = var677.mUserId;
                  String var683 = var677.mFirstName;
                  String var684 = var677.mLastName;
                  String var685 = var677.mDisplayName;
                  String var686 = var656.url;
                  FacebookUser var687 = new FacebookUser(var681, var683, var684, var685, var686);
                  var680.setProfile(var687);
                  String var690 = this.mSessionInfo.toJSONObject().toString();
                  UserValuesManager.saveActiveSessionInfo(var1, var690);
               }
            }

            var59 = this.mListeners.getListeners().iterator();

            while(var59.hasNext()) {
               AppSessionListener var693 = (AppSessionListener)var59.next();
               ProfileImagesCache var694 = this.mUserImageCache;
               var693.onProfileImageDownloaded(this, var21, var3, var4, var5, var656, var694);
            }
         }
         break;
      case 110:
         String var703 = "folder";
         byte var704 = 0;
         var705 = var2.getIntExtra(var703, var704);
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var706 = (AppSessionListener)var59.next();
            var706.onMailboxSyncComplete(this, var21, var3, var4, var5, var705);
         }
      case 111:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var739 = (AppSessionListener)var59.next();
            var739.onMailboxSendComplete(this, var21, var3, var4, var5);
         }
      case 112:
         String var746 = "tid";
         long var747 = 65535L;
         var719 = var2.getLongExtra(var746, var747);
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var749 = (AppSessionListener)var59.next();
            var749.onMailboxReplyComplete(this, var21, var3, var4, var5, var719);
         }
      case 113:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var757 = (AppSessionListener)var59.next();
            String var759 = "tid";
            long[] var760 = var2.getLongArrayExtra(var759);
            String var762 = "read";
            byte var763 = 0;
            boolean var764 = var2.getBooleanExtra(var762, (boolean)var763);
            var757.onMailboxMarkThreadComplete(this, var21, var3, var4, var5, var760, var764);
         }
      case 114:
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var771 = (AppSessionListener)var59.next();
            String var773 = "tid";
            long[] var774 = var2.getLongArrayExtra(var773);
            var771.onMailboxDeleteThreadComplete(this, var21, var3, var4, var5, var774);
         }
      case 115:
         String var713 = "folder";
         byte var714 = 0;
         var705 = var2.getIntExtra(var713, var714);
         String var716 = "tid";
         long var717 = 65535L;
         var719 = var2.getLongExtra(var716, var717);
         var59 = this.mListeners.getListeners().iterator();

         while(var59.hasNext()) {
            AppSessionListener var721 = (AppSessionListener)var59.next();
            var721.onMailboxGetThreadMessagesComplete(this, var21, var3, var4, var5, var705, var719);
         }

         short var728 = 200;
         if(var3 == var728) {
            String var730 = "read";
            byte var731 = 1;
            if(var2.getBooleanExtra(var730, (boolean)var731)) {
               Object var732 = null;
               ((Object[])var732)[0] = var719;
               byte var737 = 1;
               this.mailboxMarkThread(var1, var705, (long[])var732, (boolean)var737);
            }
         }
         break;
      case 121:
         var834 = (Long)var6;
         var835 = (Boolean)var7;
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var836 = (AppSessionListener)var59.next();
            long var837 = var834.longValue();
            boolean var839 = var835.booleanValue();
            var836.onEventRsvpComplete(this, var21, var3, var4, var5, var837, var839);
         }
      case 122:
         var834 = (Long)var6;
         Map var846 = (Map)var7;
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var847 = (AppSessionListener)var59.next();
            long var848 = var834.longValue();
            var847.onEventGetMembersComplete(this, var21, var3, var4, var5, var848, var846);
         }
      case 131:
         Map var856 = (Map)var6;
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var857 = (AppSessionListener)var59.next();
            var857.onUserGetFriendRequestsComplete(this, var21, var3, var4, var5, var856);
         }
      case 132:
         Long var863 = (Long)var6;
         var835 = (Boolean)var7;
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var864 = (AppSessionListener)var59.next();
            long var865 = var863.longValue();
            boolean var867 = var835.booleanValue();
            var864.onFriendRequestRespondComplete(this, var21, var3, var4, var5, var865, var867);
         }
      case 133:
         Map var874 = (Map)var6;
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var875 = (AppSessionListener)var59.next();
            var875.onFriendRequestsMutualFriendsComplete(this, var21, var3, var4, var5, var874);
         }
      case 201:
         this.acquireWakeLock(var1);
         String var790 = "extra_attempt";
         byte var791 = 0;
         int var792 = var2.getIntExtra(var790, var791);
         String var796 = this.pollNotifications(var1, var792);
         break;
      case 202:
         String var798 = "connectivity";
         if(((ConnectivityManager)var1.getSystemService(var798)).getBackgroundDataSetting()) {
            String var800 = "extra_attempt";
            byte var801 = 0;
            int var802 = var2.getIntExtra(var800, var801);
            if(this.syncFriends(var1, var802) != null) {
               this.acquireWakeLock(var1);
            }
         } else {
            byte var808 = -1;
            byte var809 = 0;
            this.scheduleUsersPollingAlarm(var1, var808, var809);
         }
         break;
      case 203:
         if(this.isWidgetEnabled(var1)) {
            this.acquireWakeLock(var1);
            String var811 = "extra_attempt";
            byte var812 = 0;
            int var813 = var2.getIntExtra(var811, var812);
            String var817 = this.usersPollStatuses(var1, var813);
         }
         break;
      case 211:
         int var781 = ((Integer)var6).intValue();
         int var782 = ((Integer)var7).intValue();
         var59 = this.mListeners.getListeners().iterator();

         while(true) {
            if(!var59.hasNext()) {
               break label570;
            }

            AppSessionListener var783 = (AppSessionListener)var59.next();
            var783.onUsersSearchComplete(this, var21, var3, var4, var5, var781, var782);
         }
      case 300:
         ServiceNotificationManager.handleClearNotifications(var1);
         break;
      case 301:
         this.handleLoginResult(var1, var21, var3, var4, var5, var6, (boolean)1);
         break;
      case 1000:
         ApiMethod var881 = (ApiMethod)var6;
         ExtendedReq.onExtendedOperationComplete(this, var1, var2, var3, var4, var5, var881);
         break;
      case 1001:
         ApiMethodCallback var888 = (ApiMethodCallback)var6;
         var888.executeCallbacks(this, var1, var2, var21, var3, var4, var5);
      }

      if(this.mPendingServiceRequestsMap.size() == 0) {
         this.releaseWakeLock();
         Intent var23 = new Intent;
         Class var26 = FacebookService.class;
         var23.<init>(var1, var26);
         boolean var29 = var1.stopService(var23);
         FacebookService.apiMethodReceiver.clear();
      }
   }

   private void onOperationProgress(Context var1, Intent var2, Object var3, Object var4) {
      switch(var2.getIntExtra("type", -1)) {
      case 65:
         int var5 = Integer.parseInt(var2.getStringExtra("rid"));
         int var6 = ((Integer)var3).intValue();
         ServiceNotificationManager.updateProgressNotification(var1, var5, var6);
         return;
      default:
      }
   }

   public static void onServiceOperationComplete(Context var0, Intent var1, int var2, String var3, Exception var4, Object var5, Object var6) {
      Map var7 = mSessionMap;
      String var8 = var1.getStringExtra("sid");
      AppSession var9 = (AppSession)var7.get(var8);
      if(var9 != null) {
         var9.onOperationComplete(var0, var1, var2, var3, var4, var5, var6);
      } else {
         int var17 = var1.getIntExtra("type", -1);
         switch(var17) {
         case 90:
         case 91:
         case 92:
         case 201:
         case 202:
         case 203:
         case 300:
            var9 = getActiveSession(var0, (boolean)0);
            if(var9 != null) {
               var9.onOperationComplete(var0, var1, var2, var3, var4, var5, var6);
               return;
            }

            StringBuilder var37 = (new StringBuilder()).append("No session: ");
            int var38 = var1.getIntExtra("type", -1);
            String var39 = var37.append(var38).toString();
            Log.e("onServiceOperationComplete", var39);
            return;
         default:
            if(var17 == 65) {
               StringBuilder var40 = new StringBuilder();
               StringBuilder var18 = var40.append(" reqIntent: ");
               String var19 = var1.toString();
               var18.append(var19);
               Bundle var21 = var1.getExtras();
               StringBuilder var22 = var40.append(" reqIntent.extras: ");
               String var23;
               if(var21 != null) {
                  var23 = var21.toString();
               } else {
                  var23 = "";
               }

               var22.append(var23);
               StringBuilder var25 = var40.append(" mSessionMap: ");
               Object[] var26 = new Object[1];
               Set var27 = mSessionMap.keySet();
               var26[0] = var27;
               StringUtils.join(var40, ", ", (StringProcessor)null, var26);
               String var28 = var40.toString();
               Utils.reportSoftError("photoupload_stuck", var28);
            }

            String var29 = "No session: " + var17;
            Log.e("onServiceOperationComplete", var29);
         }
      }
   }

   public static void onServiceOperationProgress(Context var0, Intent var1, Object var2, Object var3) {
      Map var4 = mSessionMap;
      String var5 = var1.getStringExtra("sid");
      AppSession var6 = (AppSession)var4.get(var5);
      if(var6 != null) {
         var6.onOperationProgress(var0, var1, var2, var3);
      }
   }

   private String pollNotifications(Context var1, int var2) {
      Intent var3 = new Intent(var1, FacebookService.class);
      String var4 = nextRequestId();
      Intent var5 = var3.putExtra("type", 50);
      var3.putExtra("rid", var4);
      String var7 = this.mSessionId;
      var3.putExtra("sid", var7);
      String var9 = this.mSessionInfo.sessionKey;
      var3.putExtra("session_key", var9);
      var3.putExtra("extra_attempt", var2);
      this.postToService(var1, var4, var3);
      return var4;
   }

   private void postLoginRequiredNotification(Context var1) {
      LoginStatus var2 = this.mLoginStatus;
      LoginStatus var3 = LoginStatus.STATUS_LOGGING_OUT;
      if(var2 != var3) {
         LoginStatus var4 = this.mLoginStatus;
         LoginStatus var5 = LoginStatus.STATUS_LOGGED_OUT;
         if(var4 != var5) {
            String var6 = this.mSessionInfo.username;
            postLoginRequiredNotification(var1, var6);
         }
      }
   }

   public static void postLoginRequiredNotification(Context var0, String var1) {
      Utils.updateErrorReportingUserId(var0, (boolean)1);
      String var2 = var0.getString(2131361940);
      long var3 = System.currentTimeMillis();
      (new Notification(17301642, var2, var3)).flags = 16;
      Intent var5 = new Intent(var0, LoginNotificationActivity.class);
      var5.putExtra("un", var1);
      Intent var7 = var5.addFlags(268435456);
      var0.startActivity(var5);
   }

   private void postToService(Context var1, String var2, Intent var3) {
      FacebookSessionInfo var4 = this.getSessionInfo();
      if(var4 != null) {
         String var5 = var4.sessionSecret;
         if(var5 != null) {
            var3.putExtra("ApiMethod.secret", var5);
         }
      }

      this.mPendingServiceRequestsMap.put(var2, var3);
      LoginStatus var8 = LoginStatus.STATUS_LOGGING_IN;
      LoginStatus var9 = this.mLoginStatus;
      if(var8 == var9 && !isLoginRequest(var3)) {
         this.mRequestsToHandleAfterLogin.add(var3);
      } else {
         var1.startService(var3);
      }
   }

   private void releaseWakeLock() {
      if(this.mWakeLock != null) {
         this.mWakeLock.release();
         this.mWakeLock = null;
      }
   }

   private static void resumePhotoUploads(Context var0) {
      Intent var1 = new Intent(var0, UploadManager.class);
      Intent var2 = var1.putExtra("type", 1);
      var0.startService(var1);
   }

   private void scheduleNotificationsPollingAlarm(Context var1, int var2, int var3) {
      AlarmManager var4 = (AlarmManager)var1.getSystemService("alarm");
      if(this.mPollingNotificationsAlarmIntent != null) {
         PendingIntent var5 = this.mPollingNotificationsAlarmIntent;
         var4.cancel(var5);
         this.mPollingNotificationsAlarmIntent.cancel();
      }

      if(var2 == -1) {
         int var6 = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(var1).getString("polling_interval", "60"));
         if(var6 > 120) {
            var6 = 120;
         } else if(var6 == 0) {
            var6 = 30;
         }

         var2 = var6 * '\uea60';
      }

      Intent var7 = new Intent(var1, FacebookService.class);
      StringBuilder var8 = (new StringBuilder()).append("com.facebook.katana.service.");
      String var9 = nextRequestId();
      String var10 = var8.append(var9).toString();
      var7.setAction(var10);
      Intent var12 = var7.putExtra("type", 201);
      String var13 = this.mSessionId;
      var7.putExtra("sid", var13);
      var7.putExtra("extra_attempt", var3);
      PendingIntent var16 = PendingIntent.getService(var1, 0, var7, 0);
      this.mPollingNotificationsAlarmIntent = var16;
      long var17 = System.currentTimeMillis();
      long var19 = (long)var2;
      long var21 = var17 + var19;
      PendingIntent var23 = this.mPollingNotificationsAlarmIntent;
      var4.set(0, var21, var23);
   }

   private void scheduleUsersPollingAlarm(Context var1, int var2, int var3) {
      if(var2 != -1 || !PlatformUtils.platformStorageSupported(var1)) {
         AlarmManager var4 = (AlarmManager)var1.getSystemService("alarm");
         if(this.mPollingUsersAlarmIntent != null) {
            PendingIntent var5 = this.mPollingUsersAlarmIntent;
            var4.cancel(var5);
            this.mPollingUsersAlarmIntent.cancel();
         }

         Intent var6 = new Intent(var1, FacebookService.class);
         StringBuilder var7 = (new StringBuilder()).append("com.facebook.katana.service.");
         String var8 = nextRequestId();
         String var9 = var7.append(var8).toString();
         var6.setAction(var9);
         Intent var11 = var6.putExtra("type", 202);
         String var12 = this.mSessionId;
         var6.putExtra("sid", var12);
         var6.putExtra("extra_attempt", var3);
         PendingIntent var15 = PendingIntent.getService(var1, 0, var6, 0);
         this.mPollingUsersAlarmIntent = var15;
         if(var2 == -1) {
            var2 = 172800000;
         }

         long var16 = System.currentTimeMillis();
         long var18 = (long)var2;
         long var20 = var16 + var18;
         PendingIntent var22 = this.mPollingUsersAlarmIntent;
         var4.set(0, var20, var22);
      }
   }

   private String[] stringCollectionToSortedArray(Collection<String> var1) {
      ArrayList var2 = new ArrayList(var1);
      Collections.sort(var2);
      String[] var3 = new String[0];
      return (String[])var2.toArray(var3);
   }

   private String syncFriends(Context var1, int var2) {
      String var3;
      if(this.isFriendsSyncPending()) {
         var3 = null;
      } else {
         Intent var4 = new Intent(var1, FacebookService.class);
         String var5 = nextRequestId();
         Intent var6 = var4.putExtra("type", 80);
         var4.putExtra("rid", var5);
         String var8 = this.mSessionId;
         var4.putExtra("sid", var8);
         String var10 = this.mSessionInfo.sessionKey;
         var4.putExtra("session_key", var10);
         long var12 = this.mSessionInfo.userId;
         var4.putExtra("session_user_id", var12);
         var4.putExtra("extra_attempt", var2);
         String var16 = this.mSessionInfo.username;
         var4.putExtra("un", var16);
         this.postToService(var1, var5, var4);
         var3 = var5;
      }

      return var3;
   }

   private void updateWidget(Context var1, FacebookStatus var2, int var3, Bitmap var4) {
      AppWidgetManager var5 = AppWidgetManager.getInstance(var1);
      ComponentName var6 = new ComponentName;
      Class var9 = FacebookWidgetProvider.class;
      var6.<init>(var1, var9);
      if(var5.getAppWidgetIds(var6).length != 0) {
         String var12 = var1.getPackageName();
         RemoteViews var13 = new RemoteViews(var12, 2130903185);
         String var15 = "fb://feed";
         Intent var16 = IntentUriHandler.getIntentForUri(var1, var15);
         Intent var17 = var16.setFlags(335544320);
         byte var19 = 0;
         int var21 = 268435456;
         PendingIntent var22 = PendingIntent.getActivity(var1, var19, var16, var21);
         var13.setOnClickPendingIntent(2131624290, var22);
         Intent var23 = new Intent;
         Class var26 = WidgetActivity.class;
         var23.<init>(var1, var26);
         Intent var27 = var23.setAction("com.facebook.katana.widget.sharebutton");
         Intent var28 = var23.setFlags(268435456);
         byte var30 = 0;
         int var32 = 268435456;
         PendingIntent var33 = PendingIntent.getActivity(var1, var30, var23, var32);
         var13.setOnClickPendingIntent(2131624293, var33);
         Intent var34 = new Intent;
         Class var37 = WidgetActivity.class;
         var34.<init>(var1, var37);
         String var39 = "com.facebook.katana.widget.edit";
         var34.setAction(var39);
         int var42 = 268435456;
         var34.setFlags(var42);
         byte var45 = 0;
         int var47 = 268435456;
         PendingIntent var48 = PendingIntent.getActivity(var1, var45, var34, var47);
         var13.setOnClickPendingIntent(2131624292, var48);
         String var49 = var2.getUser().mDisplayName;
         String var50 = var2.getMessage();
         SpannableStringBuilder var51 = new SpannableStringBuilder(var49);
         if(var50 != null) {
            String var55 = " ";
            SpannableStringBuilder var56 = var51.append(var55).append(var50);
         }

         if(mNameSpan == null) {
            Resources var57 = var1.getResources();
            float var58 = var57.getDisplayMetrics().density;
            int var59 = (int)(14.0F * var58);
            int var61 = 2131165191;
            ColorStateList var62 = ColorStateList.valueOf(var57.getColor(var61));
            mNameSpan = new TextAppearanceSpan((String)null, 1, var59, var62, (ColorStateList)null);
         }

         TextAppearanceSpan var63 = mNameSpan;
         int var64 = var49.length();
         byte var67 = 0;
         byte var69 = 33;
         var51.setSpan(var63, var67, var64, var69);
         int var71 = 2131624296;
         var13.setTextViewText(var71, var51);
         TimeFormatStyle var73 = TimeFormatStyle.STREAM_RELATIVE_STYLE;
         long var74 = var2.getTime() * 1000L;
         String var80 = StringUtils.getTimeAsString(var1, var73, var74);
         var13.setTextViewText(2131624297, var80);
         if(var4 != null) {
            int var82 = 2131624295;
            var13.setImageViewBitmap(var82, var4);
         } else {
            var13.setImageViewResource(2131624295, 2130837747);
         }

         long var84 = var2.getUser().mUserId;
         Intent var89 = ProfileTabHostActivity.intentForProfile(var1, var84);
         int var91 = 335544320;
         var89.setFlags(var91);
         byte var94 = 0;
         int var96 = 268435456;
         PendingIntent var97 = PendingIntent.getActivity(var1, var94, var89, var96);
         var13.setOnClickPendingIntent(2131624294, var97);
         Intent var98;
         if(var3 == 0) {
            var98 = null;
            var13.setImageViewResource(2131624298, 2130837712);
         } else {
            var98 = new Intent;
            Class var118 = FacebookService.class;
            var98.<init>(var1, var118);
            StringBuilder var119 = (new StringBuilder()).append("com.facebook.katana.service.");
            String var120 = nextRequestId();
            String var121 = var119.append(var120).toString();
            Intent var124 = var98.setAction(var121);
            String var126 = "type";
            byte var127 = 91;
            var98.putExtra(var126, var127);
            String var129 = mActiveSessionId;
            String var131 = "sid";
            var98.putExtra(var131, var129);
            var13.setImageViewResource(2131624298, 2130837814);
         }

         byte var100 = 0;
         int var102 = 268435456;
         PendingIntent var103 = PendingIntent.getService(var1, var100, var98, var102);
         var13.setOnClickPendingIntent(2131624298, var103);
         int var104 = this.mStatusesList.size() - 1;
         Intent var107;
         if(var3 >= var104) {
            var107 = null;
            var13.setImageViewResource(2131624299, 2130837701);
         } else {
            var107 = new Intent;
            Class var136 = FacebookService.class;
            var107.<init>(var1, var136);
            StringBuilder var137 = (new StringBuilder()).append("com.facebook.katana.service.");
            String var138 = nextRequestId();
            String var139 = var137.append(var138).toString();
            Intent var142 = var107.setAction(var139);
            String var144 = "type";
            byte var145 = 90;
            var107.putExtra(var144, var145);
            String var147 = mActiveSessionId;
            String var149 = "sid";
            var107.putExtra(var149, var147);
            var13.setImageViewResource(2131624299, 2130837813);
         }

         byte var109 = 0;
         int var111 = 268435456;
         PendingIntent var112 = PendingIntent.getService(var1, var109, var107, var111);
         var13.setOnClickPendingIntent(2131624299, var112);
         var5.updateAppWidget(var6, var13);
      }
   }

   private String usersPollStatuses(Context var1, int var2) {
      Intent var3 = new Intent(var1, FacebookService.class);
      String var4 = nextRequestId();
      Intent var5 = var3.putExtra("type", 81);
      var3.putExtra("rid", var4);
      String var7 = this.mSessionId;
      var3.putExtra("sid", var7);
      String var9 = this.mSessionInfo.sessionKey;
      var3.putExtra("session_key", var9);
      long var11 = this.mSessionInfo.userId;
      var3.putExtra("session_user_id", var11);
      var3.putExtra("extra_attempt", var2);
      String var15 = this.mSessionInfo.username;
      var3.putExtra("un", var15);
      this.postToService(var1, var4, var3);
      return var4;
   }

   public void addListener(AppSessionListener var1) {
      this.mListeners.addListener(var1);
   }

   public String authLogin(Context var1, String var2, String var3, boolean var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      short var7;
      if(var4) {
         var7 = 301;
      } else {
         var7 = 1;
      }

      var6.putExtra("type", var7);
      var6.putExtra("rid", var5);
      String var10 = this.mSessionId;
      var6.putExtra("sid", var10);
      var6.putExtra("un", var2);
      var6.putExtra("pwd", var3);
      if(!var4) {
         clearCookies(var1);
      }

      this.postToService(var1, var5, var6);
      LoginStatus var14 = LoginStatus.STATUS_LOGGING_IN;
      this.mLoginStatus = var14;
      return var5;
   }

   public String authLogout(Context var1) {
      String var2 = nextRequestId();
      Intent var3 = new Intent(var1, FacebookService.class);
      Intent var4 = var3.putExtra("type", 2);
      var3.putExtra("rid", var2);
      String var6 = this.mSessionId;
      var3.putExtra("sid", var6);
      if(this.mSessionInfo != null) {
         String var8 = this.mSessionInfo.sessionKey;
         var3.putExtra("session_key", var8);
      }

      LoginStatus var10 = LoginStatus.STATUS_LOGGING_OUT;
      this.mLoginStatus = var10;
      this.postToService(var1, var2, var3);
      return var2;
   }

   public void cancelServiceOp(Context var1, String var2) {
      Intent var3 = new Intent(var1, FacebookService.class);
      Intent var4 = var3.putExtra("type", 2000);
      var3.putExtra("rid", var2);
      if(this.mPendingServiceRequestsMap.containsKey(var2)) {
         this.mPendingServiceRequestsMap.remove(var2);
         var1.startService(var3);
      }
   }

   public void cancelUploadNotification(Context var1, String var2) {
      int var3 = Integer.parseInt(var2);
      ServiceNotificationManager.cancelUploadNotification(var1, var3);
   }

   public String checkin(Context var1, FacebookPlace var2, Location var3, String var4, Set<Long> var5, Long var6, String var7) throws JSONException {
      String var8 = this.mSessionInfo.sessionKey;
      PlacesCheckin var16 = new PlacesCheckin(var1, (Intent)null, var8, (ApiMethodListener)null, var2, var3, var4, var5, var6, var7);
      return this.postToService(var1, var16, 503, (Bundle)null);
   }

   public String downloadAlbumThumbail(Context var1, long var2, String var4, String var5, String var6) {
      return this.downloadPhoto(var1, 74, var2, var4, var5, var6);
   }

   public String downloadFullPhoto(Context var1, String var2, String var3, String var4) {
      return this.downloadPhoto(var1, 76, 65535L, var2, var3, var4);
   }

   public String downloadPhotoRaw(Context var1, String var2) {
      Object var5 = null;
      return this.downloadPhoto(var1, 77, 65535L, (String)null, (String)var5, var2);
   }

   public String downloadPhotoThumbail(Context var1, String var2, String var3, String var4) {
      return this.downloadPhoto(var1, 75, 65535L, var2, var3, var4);
   }

   public String eventGetMembers(Context var1, long var2) {
      String var4 = nextRequestId();
      Intent var5 = new Intent(var1, FacebookService.class);
      Intent var6 = var5.putExtra("type", 122);
      var5.putExtra("rid", var4);
      String var8 = this.mSessionId;
      var5.putExtra("sid", var8);
      var5.putExtra("eid", var2);
      String var11 = this.mSessionInfo.sessionKey;
      var5.putExtra("session_key", var11);
      long var13 = this.mSessionInfo.userId;
      var5.putExtra("session_user_id", var13);
      this.postToService(var1, var4, var5);
      return var4;
   }

   public String eventRsvp(Context var1, long var2, RsvpStatus var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      Intent var7 = var6.putExtra("type", 121);
      var6.putExtra("rid", var5);
      String var9 = this.mSessionId;
      var6.putExtra("sid", var9);
      var6.putExtra("eid", var2);
      String var12 = FacebookEvent.getRsvpStatusString(var4);
      var6.putExtra("rsvp_status", var12);
      String var14 = this.mSessionInfo.sessionKey;
      var6.putExtra("session_key", var14);
      long var16 = this.mSessionInfo.userId;
      var6.putExtra("session_user_id", var16);
      this.postToService(var1, var5, var6);
      return var5;
   }

   public String getConfig(Context var1) {
      String var2 = nextRequestId();
      Intent var3 = new Intent(var1, FacebookService.class);
      Intent var4 = var3.putExtra("type", 400);
      var3.putExtra("rid", var2);
      String var6 = this.mSessionId;
      var3.putExtra("sid", var6);
      String var8 = this.mSessionInfo.sessionKey;
      var3.putExtra("session_key", var8);
      long var10 = this.mSessionInfo.userId;
      var3.putExtra("session_user_id", var10);
      this.postToService(var1, var2, var3);
      return var2;
   }

   public String getEvents(Context var1, long var2) {
      String var4 = this.mSessionInfo.sessionKey;
      Object var6 = null;
      FqlGetEvents var9 = new FqlGetEvents(var1, (Intent)null, var4, (ApiMethodListener)var6, var2);
      return this.postToService(var1, var9, 120, (Bundle)null);
   }

   public String getFacebookAffiliationStatus(Context var1, long var2) {
      String var4 = nextRequestId();
      Intent var5 = new Intent(var1, FacebookService.class);
      Intent var6 = var5.putExtra("type", 140);
      var5.putExtra("rid", var4);
      String var8 = this.mSessionId;
      var5.putExtra("sid", var8);
      var5.putExtra("uid", var2);
      String var11 = this.mSessionInfo.sessionKey;
      var5.putExtra("session_key", var11);
      long var13 = this.mSessionInfo.userId;
      var5.putExtra("session_user_id", var13);
      this.postToService(var1, var4, var5);
      return var4;
   }

   public String getFriendCheckins(Context var1) {
      String var2 = this.mSessionInfo.sessionKey;
      FqlGetFriendCheckins var3 = new FqlGetFriendCheckins(var1, (Intent)null, var2, (ApiMethodListener)null);
      return this.postToService(var1, var3, 500, (Bundle)null);
   }

   public String getFriendRequests(Context var1, long var2) {
      String var4 = nextRequestId();
      Intent var5 = new Intent(var1, FacebookService.class);
      Intent var6 = var5.putExtra("type", 131);
      var5.putExtra("rid", var4);
      String var8 = this.mSessionId;
      var5.putExtra("sid", var8);
      var5.putExtra("uid", var2);
      String var11 = this.mSessionInfo.sessionKey;
      var5.putExtra("session_key", var11);
      long var13 = this.mSessionInfo.userId;
      var5.putExtra("session_user_id", var13);
      this.postToService(var1, var4, var5);
      return var4;
   }

   public String getFriendRequestsMutualFriends(Context var1, long var2) {
      String var4 = nextRequestId();
      Intent var5 = new Intent(var1, FacebookService.class);
      Intent var6 = var5.putExtra("type", 133);
      var5.putExtra("rid", var4);
      String var8 = this.mSessionId;
      var5.putExtra("sid", var8);
      var5.putExtra("uid", var2);
      String var11 = this.mSessionInfo.sessionKey;
      var5.putExtra("session_key", var11);
      long var13 = this.mSessionInfo.userId;
      var5.putExtra("session_user_id", var13);
      this.postToService(var1, var4, var5);
      return var4;
   }

   public Set<AppSessionListener> getListeners() {
      return this.mListeners.getListeners();
   }

   public StreamPhotosCache getPhotosCache() {
      return this.mPhotosCache;
   }

   public String getPlacesNearby(Context var1, Location var2, double var3, String var5, int var6, NetworkRequestCallback<PlacesNearbyArgType, FqlGetPlacesNearby, Object> var7) {
      String var8 = this.mSessionInfo.sessionKey;
      FqlGetPlacesNearby var16 = new FqlGetPlacesNearby(var1, (Intent)null, var8, (ApiMethodListener)null, var2, var3, var5, var6, var7);
      return this.postToService(var1, var16, 501, (Bundle)null);
   }

   public FacebookSessionInfo getSessionInfo() {
      return this.mSessionInfo;
   }

   public LoginStatus getStatus() {
      return this.mLoginStatus;
   }

   public FacebookStreamContainer getStreamContainer(long var1, FacebookStreamType var3) {
      int[] var4 = 3.$SwitchMap$com$facebook$katana$model$FacebookStreamType;
      int var5 = var3.ordinal();
      FacebookStreamContainer var6;
      switch(var4[var5]) {
      case 1:
         var6 = this.mHomeStreamContainer;
         break;
      case 2:
      case 3:
      case 4:
         Map var7 = this.mWallContainerMap;
         Long var8 = Long.valueOf(var1);
         var6 = (FacebookStreamContainer)var7.get(var8);
         break;
      case 5:
         Map var9 = this.mPlacesActivityContainerMap;
         Long var10 = Long.valueOf(var1);
         var6 = (FacebookStreamContainer)var9.get(var10);
         break;
      default:
         var6 = null;
      }

      return var6;
   }

   public ProfileImagesCache getUserImagesCache() {
      return this.mUserImageCache;
   }

   public void handlePasswordEntry(Context var1, String var2) {
      String var3 = this.mSessionInfo.username;
      this.authLogin(var1, var3, var2, (boolean)1);
   }

   protected void initializeSettingsState() {
      Gatekeeper.reset();
      HashMap var1 = new HashMap();
      this.mUserServerSettings = var1;
      HashMap var2 = new HashMap();
      this.mUserServerSettingsSync = var2;
   }

   public boolean isAlbumGetThumbnailsPending(String var1) {
      Iterator var2 = this.mPendingServiceRequestsMap.values().iterator();

      boolean var5;
      while(true) {
         if(var2.hasNext()) {
            Intent var3 = (Intent)var2.next();
            if(var3.getIntExtra("type", -1) != 75) {
               continue;
            }

            String var4 = var3.getStringExtra("aid");
            if(!var1.equals(var4)) {
               continue;
            }

            var5 = true;
            break;
         }

         var5 = false;
         break;
      }

      return var5;
   }

   public boolean isAlbumsGetPending(long var1) {
      Iterator var3 = this.mPendingServiceRequestsMap.values().iterator();

      boolean var5;
      while(true) {
         if(var3.hasNext()) {
            Intent var4 = (Intent)var3.next();
            if(var4.getIntExtra("type", -1) != 60 || var4.getLongExtra("uid", 65535L) != var1) {
               continue;
            }

            var5 = true;
            break;
         }

         var5 = false;
         break;
      }

      return var5;
   }

   public boolean isAlbumsGetPending(long var1, String var3) {
      boolean var4 = false;
      Iterator var5 = this.mPendingServiceRequestsMap.values().iterator();

      while(var5.hasNext()) {
         Intent var6 = (Intent)var5.next();
         if(var6.getIntExtra("type", -1) == 60 && var6.getLongExtra("uid", 65535L) == var1) {
            String var7 = var6.getStringExtra("aid");
            if(var7 != null && var3.equals(var7)) {
               var4 = true;
            }
         }
      }

      return var4;
   }

   public boolean isFriendsSyncPending() {
      Iterator var1 = this.mPendingServiceRequestsMap.values().iterator();

      boolean var2;
      while(true) {
         if(var1.hasNext()) {
            if(((Intent)var1.next()).getIntExtra("type", -1) != 80) {
               continue;
            }

            var2 = true;
            break;
         }

         var2 = false;
         break;
      }

      return var2;
   }

   public boolean isMailboxGetMessagesPending(int var1, long var2) {
      Iterator var4 = this.mPendingServiceRequestsMap.values().iterator();

      boolean var6;
      while(true) {
         if(var4.hasNext()) {
            Intent var5 = (Intent)var4.next();
            if(var5.getIntExtra("type", -1) != 115 || var5.getIntExtra("folder", 0) != var1 || var5.getLongExtra("tid", 65535L) != var2) {
               continue;
            }

            var6 = true;
            break;
         }

         var6 = false;
         break;
      }

      return var6;
   }

   public boolean isMailboxSyncPending(int var1) {
      Iterator var2 = this.mPendingServiceRequestsMap.values().iterator();

      boolean var4;
      while(true) {
         if(var2.hasNext()) {
            Intent var3 = (Intent)var2.next();
            if(var3.getIntExtra("type", -1) != 110 || var3.getIntExtra("folder", 0) != var1) {
               continue;
            }

            var4 = true;
            break;
         }

         var4 = false;
         break;
      }

      return var4;
   }

   public boolean isNotificationsSyncPending() {
      Iterator var1 = this.mPendingServiceRequestsMap.values().iterator();

      boolean var2;
      while(true) {
         if(var1.hasNext()) {
            if(((Intent)var1.next()).getIntExtra("type", -1) != 51) {
               continue;
            }

            var2 = true;
            break;
         }

         var2 = false;
         break;
      }

      return var2;
   }

   public boolean isPhotoGetCommentPending(String var1) {
      Iterator var2 = this.mPendingServiceRequestsMap.values().iterator();

      boolean var5;
      while(true) {
         if(var2.hasNext()) {
            Intent var3 = (Intent)var2.next();
            if(var3.getIntExtra("type", -1) != 70) {
               continue;
            }

            String var4 = var3.getStringExtra("pid");
            if(!var1.equals(var4)) {
               continue;
            }

            var5 = true;
            break;
         }

         var5 = false;
         break;
      }

      return var5;
   }

   public boolean isPhotosGetPending(String var1, long var2) {
      boolean var4 = false;
      Iterator var5 = this.mPendingServiceRequestsMap.values().iterator();

      while(var5.hasNext()) {
         Intent var6 = (Intent)var5.next();
         if(var6.getIntExtra("type", -1) == 64) {
            String var7 = var6.getStringExtra("aid");
            if(var1.equals(var7)) {
               long var8 = var6.getLongExtra("uid", 65535L);
               if(var2 == var8) {
                  var4 = true;
               }
            }
         }
      }

      return var4;
   }

   public boolean isPhotosGetPending(Collection<String> var1, long var2) {
      boolean var4 = false;
      Iterator var5 = this.mPendingServiceRequestsMap.values().iterator();

      while(var5.hasNext()) {
         Intent var6 = (Intent)var5.next();
         if(var6.getIntExtra("type", -1) == 64) {
            long var7 = var6.getLongExtra("uid", 65535L);
            if(var2 == var7) {
               String[] var9 = this.stringCollectionToSortedArray(var1);
               String[] var10 = var6.getStringArrayExtra("pid");
               if(Arrays.equals(var9, var10)) {
                  var4 = true;
               }
            }
         }
      }

      return var4;
   }

   public boolean isRequestPending(int var1) {
      Iterator var2 = this.mPendingServiceRequestsMap.values().iterator();

      boolean var5;
      while(true) {
         if(var2.hasNext()) {
            Intent var3 = (Intent)var2.next();
            int var4 = var3.getIntExtra("type", -1);
            if(var4 == var1) {
               var5 = true;
               break;
            }

            if(var4 != 1001 || var3.getIntExtra("extended_type", -1) != var1) {
               continue;
            }

            var5 = true;
            break;
         }

         var5 = false;
         break;
      }

      return var5;
   }

   public boolean isRequestPending(String var1) {
      return this.mPendingServiceRequestsMap.containsKey(var1);
   }

   public boolean isStreamGetCommentsPending(long var1, String var3) {
      Iterator var4 = this.mPendingServiceRequestsMap.values().iterator();

      boolean var6;
      while(true) {
         if(var4.hasNext()) {
            Intent var5 = (Intent)var4.next();
            if(var5.getIntExtra("type", -1) != 31 || var5.getLongExtra("uid", 65535L) != var1 || !var5.getStringExtra("post_id").equals(var3)) {
               continue;
            }

            var6 = true;
            break;
         }

         var6 = false;
         break;
      }

      return var6;
   }

   public boolean isStreamGetPending(long var1, FacebookStreamType var3) {
      Iterator var4 = this.mPendingServiceRequestsMap.values().iterator();

      boolean var11;
      while(true) {
         if(var4.hasNext()) {
            Intent var5 = (Intent)var4.next();
            int var6 = var5.getIntExtra("type", -1);
            if(var6 == 30) {
               long[] var7 = var5.getLongArrayExtra("subject");
               if(var7 == null) {
                  long var8 = this.mSessionInfo.userId;
                  if(var1 != var8) {
                     continue;
                  }

                  FacebookStreamType var10 = FacebookStreamType.NEWSFEED_STREAM;
                  if(var3 != var10) {
                     continue;
                  }

                  var11 = true;
                  break;
               }

               if(var7[0] != var1) {
                  continue;
               }

               FacebookStreamType var12 = FacebookStreamType.PROFILE_WALL_STREAM;
               if(var3 != var12) {
                  FacebookStreamType var13 = FacebookStreamType.PAGE_WALL_STREAM;
                  if(var3 != var13) {
                     continue;
                  }
               }

               var11 = true;
               break;
            }

            if(var6 != 1000 && var6 != 1001 || var5.getIntExtra("extended_type", -1) != 502 || var5.getLongExtra("subject", 65535L) != var1) {
               continue;
            }

            FacebookStreamType var14 = FacebookStreamType.PLACE_ACTIVITY_STREAM;
            if(var3 != var14) {
               continue;
            }

            var11 = true;
            break;
         }

         var11 = false;
         break;
      }

      return var11;
   }

   public String mailboxDeleteThread(Context var1, long[] var2, int var3) {
      String var4 = nextRequestId();
      Intent var5 = new Intent(var1, FacebookService.class);
      Intent var6 = var5.putExtra("type", 114);
      var5.putExtra("rid", var4);
      String var8 = this.mSessionId;
      var5.putExtra("sid", var8);
      String var10 = this.mSessionInfo.sessionKey;
      var5.putExtra("session_key", var10);
      var5.putExtra("tid", var2);
      var5.putExtra("folder", var3);
      this.postToService(var1, var4, var5);
      return var4;
   }

   public String mailboxGetThreadMessages(Context var1, int var2, long var3, boolean var5) {
      String var6 = nextRequestId();
      Intent var7 = new Intent(var1, FacebookService.class);
      Intent var8 = var7.putExtra("type", 115);
      var7.putExtra("rid", var6);
      String var10 = this.mSessionId;
      var7.putExtra("sid", var10);
      String var12 = this.mSessionInfo.sessionKey;
      var7.putExtra("session_key", var12);
      var7.putExtra("folder", var2);
      var7.putExtra("tid", var3);
      var7.putExtra("read", var5);
      this.postToService(var1, var6, var7);
      return var6;
   }

   public String mailboxMarkThread(Context var1, int var2, long[] var3, boolean var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      Intent var7 = var6.putExtra("type", 113);
      var6.putExtra("rid", var5);
      String var9 = this.mSessionId;
      var6.putExtra("sid", var9);
      String var11 = this.mSessionInfo.sessionKey;
      var6.putExtra("session_key", var11);
      var6.putExtra("tid", var3);
      var6.putExtra("folder", var2);
      var6.putExtra("read", var4);
      this.postToService(var1, var5, var6);
      return var5;
   }

   public String mailboxReply(Context var1, long var2, String var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      Intent var7 = var6.putExtra("type", 112);
      var6.putExtra("rid", var5);
      String var9 = this.mSessionId;
      var6.putExtra("sid", var9);
      String var11 = this.mSessionInfo.sessionKey;
      var6.putExtra("session_key", var11);
      var6.putExtra("tid", var2);
      var6.putExtra("status", var4);
      long var15 = this.mSessionInfo.getProfile().mUserId;
      var6.putExtra("profile_uid", var15);
      String var18 = this.mSessionInfo.getProfile().mFirstName;
      var6.putExtra("profile_first_name", var18);
      String var20 = this.mSessionInfo.getProfile().mLastName;
      var6.putExtra("profile_last_name", var20);
      String var22 = this.mSessionInfo.getProfile().mDisplayName;
      var6.putExtra("profile_display_name", var22);
      String var24 = this.mSessionInfo.getProfile().mImageUrl;
      var6.putExtra("profile_url", var24);
      this.postToService(var1, var5, var6);
      return var5;
   }

   public String mailboxSend(Context var1, List<FacebookProfile> var2, String var3, String var4) {
      ArrayList var5 = new ArrayList(var2);
      String var6 = nextRequestId();
      Intent var7 = new Intent(var1, FacebookService.class);
      Intent var8 = var7.putExtra("type", 111);
      var7.putExtra("rid", var6);
      String var10 = this.mSessionId;
      var7.putExtra("sid", var10);
      String var12 = this.mSessionInfo.sessionKey;
      var7.putExtra("session_key", var12);
      var7.putParcelableArrayListExtra("uid", var5);
      var7.putExtra("subject", var3);
      var7.putExtra("status", var4);
      long var17 = this.mSessionInfo.getProfile().mUserId;
      var7.putExtra("profile_uid", var17);
      String var20 = this.mSessionInfo.getProfile().mFirstName;
      var7.putExtra("profile_first_name", var20);
      String var22 = this.mSessionInfo.getProfile().mLastName;
      var7.putExtra("profile_last_name", var22);
      String var24 = this.mSessionInfo.getProfile().mDisplayName;
      var7.putExtra("profile_display_name", var24);
      String var26 = this.mSessionInfo.getProfile().mImageUrl;
      var7.putExtra("profile_url", var26);
      this.postToService(var1, var6, var7);
      return var6;
   }

   public String mailboxSync(Context var1, int var2, int var3, int var4, boolean var5) {
      String var6 = nextRequestId();
      Intent var7 = new Intent(var1, FacebookService.class);
      Intent var8 = var7.putExtra("type", 110);
      var7.putExtra("rid", var6);
      String var10 = this.mSessionId;
      var7.putExtra("sid", var10);
      String var12 = this.mSessionInfo.sessionKey;
      var7.putExtra("session_key", var12);
      var7.putExtra("folder", var2);
      var7.putExtra("start", var3);
      var7.putExtra("limit", var4);
      var7.putExtra("sync", var5);
      long var18 = this.mSessionInfo.userId;
      var7.putExtra("uid", var18);
      this.postToService(var1, var6, var7);
      return var6;
   }

   public String notificationsMarkAsRead(Context var1, long[] var2) {
      Intent var3 = new Intent(var1, FacebookService.class);
      String var4 = nextRequestId();
      Intent var5 = var3.putExtra("type", 52);
      var3.putExtra("rid", var4);
      String var7 = this.mSessionId;
      var3.putExtra("sid", var7);
      String var9 = this.mSessionInfo.sessionKey;
      var3.putExtra("session_key", var9);
      var3.putExtra("item_id", var2);
      this.postToService(var1, var4, var3);
      return var4;
   }

   public void openMediaItem(Context var1, MediaItem var2) {
      boolean var3 = false;
      if(var2.type.equals("video")) {
         FacebookVideo var4 = var2.getVideo();
         if(var4 != null) {
            VideoSource var5 = var4.getSourceType();
            VideoSource var6 = VideoSource.SOURCE_RAW;
            Intent var7;
            if(var5 == var6 && var4.getSourceUrl() != null) {
               var7 = new Intent("android.intent.action.VIEW");
               Uri var8 = Uri.parse(var4.getSourceUrl());
               var7.setDataAndType(var8, "video/*");
               if(var1.getPackageManager().queryIntentActivities(var7, 0).size() > 0) {
                  var1.startActivity(var7);
                  var3 = true;
               }
            }

            if(!var3 && var4.getDisplayUrl() != null) {
               Uri var10 = Uri.parse(var4.getDisplayUrl());
               var7 = new Intent("android.intent.action.VIEW", var10);
               if(var1.getPackageManager().queryIntentActivities(var7, 0).size() > 0) {
                  var1.startActivity(var7);
                  var3 = true;
               }
            }
         }
      } else if(var2.type.equals("photo")) {
         FacebookPhoto var13 = var2.getPhoto();
         if(var13 != null) {
            String var14 = var13.getAlbumId();
            String var15 = var13.getPhotoId();
            if(var15 != null || var14 != null) {
               long var16 = var13.getOwnerId();
               Intent var18 = ViewPhotoActivity.photoIntent(var1, var16, var14, var15, "android.intent.action.VIEW");
               var1.startActivity(var18);
               var3 = true;
            }
         }

         if(!var3) {
            String var19 = var2.href.replaceFirst("www.", "m.");
            this.openURL(var1, var19);
            var3 = true;
         }
      }

      if(!var3) {
         String var11 = var2.href;
         this.openURL(var1, var11);
      }
   }

   public String openURL(Context var1, String var2) {
      if(var2.startsWith("/")) {
         var2 = "http://www.facebook.com" + var2;
      }

      Uri var3 = Uri.parse(var2);
      String var13;
      if(var3.getHost().toLowerCase().endsWith(".facebook.com")) {
         long var4 = System.currentTimeMillis() / 1000L;
         long var6 = this.getSessionInfo().userId;
         String var8 = this.mSessionInfo.sessionKey;
         String var9 = this.mSessionInfo.sessionSecret;
         AuthDeepLinkMethod var12 = new AuthDeepLinkMethod(var1, var4, var6, var2, (ApiMethodListener)null, var8, var9);
         var12.start();
         var13 = var12.getUrl();
         var3 = Uri.parse(var13);
      } else {
         var13 = var2;
      }

      if(var1 != null) {
         Intent var14 = new Intent("android.intent.action.VIEW", var3);
         var1.startActivity(var14);
      }

      return var13;
   }

   public String photoAddComment(Context var1, String var2, String var3) {
      Intent var4 = new Intent(var1, FacebookService.class);
      String var5 = nextRequestId();
      Intent var6 = var4.putExtra("type", 71);
      var4.putExtra("rid", var5);
      String var8 = this.mSessionId;
      var4.putExtra("sid", var8);
      String var10 = this.mSessionInfo.sessionKey;
      var4.putExtra("session_key", var10);
      var4.putExtra("pid", var2);
      var4.putExtra("status", var3);
      long var14 = this.mSessionInfo.userId;
      var4.putExtra("uid", var14);
      this.postToService(var1, var5, var4);
      return var5;
   }

   public String photoAddTags(Context var1, String var2, String var3) {
      Intent var4 = new Intent(var1, FacebookService.class);
      String var5 = nextRequestId();
      Intent var6 = var4.putExtra("type", 68);
      var4.putExtra("rid", var5);
      String var8 = this.mSessionId;
      var4.putExtra("sid", var8);
      String var10 = this.mSessionInfo.sessionKey;
      var4.putExtra("session_key", var10);
      var4.putExtra("pid", var2);
      var4.putExtra("tags", var3);
      this.postToService(var1, var5, var4);
      return var5;
   }

   public String photoAddTags(Context var1, String var2, List<FacebookPhotoTag> var3) {
      String var4 = FacebookPhotoTag.encode(var3);
      return this.photoAddTags(var1, var2, var4);
   }

   public String photoCreateAlbum(Context var1, String var2, String var3, String var4, String var5) {
      Intent var6 = new Intent(var1, FacebookService.class);
      String var7 = nextRequestId();
      Intent var8 = var6.putExtra("type", 61);
      var6.putExtra("rid", var7);
      String var10 = this.mSessionId;
      var6.putExtra("sid", var10);
      String var12 = this.mSessionInfo.sessionKey;
      var6.putExtra("session_key", var12);
      var6.putExtra("name", var2);
      if(var3 != null) {
         var6.putExtra("location", var3);
      }

      if(var4 != null) {
         var6.putExtra("description", var4);
      }

      if(var5 != null) {
         var6.putExtra("visibility", var5);
      }

      this.postToService(var1, var7, var6);
      return var7;
   }

   public String photoDeleteAlbum(Context var1, String var2) {
      Intent var3 = new Intent(var1, FacebookService.class);
      String var4 = nextRequestId();
      Intent var5 = var3.putExtra("type", 63);
      var3.putExtra("rid", var4);
      String var7 = this.mSessionId;
      var3.putExtra("sid", var7);
      String var9 = this.mSessionInfo.sessionKey;
      var3.putExtra("session_key", var9);
      long var11 = this.mSessionInfo.userId;
      var3.putExtra("uid", var11);
      var3.putExtra("aid", var2);
      this.postToService(var1, var4, var3);
      return var4;
   }

   public String photoDeletePhoto(Context var1, String var2, String var3) {
      Intent var4 = new Intent(var1, FacebookService.class);
      String var5 = nextRequestId();
      Intent var6 = var4.putExtra("type", 67);
      var4.putExtra("rid", var5);
      String var8 = this.mSessionId;
      var4.putExtra("sid", var8);
      String var10 = this.mSessionInfo.sessionKey;
      var4.putExtra("session_key", var10);
      var4.putExtra("aid", var2);
      var4.putExtra("pid", var3);
      this.postToService(var1, var5, var4);
      return var5;
   }

   public String photoEditAlbum(Context var1, String var2, String var3, String var4, String var5, String var6) {
      Intent var7 = new Intent(var1, FacebookService.class);
      String var8 = nextRequestId();
      Intent var9 = var7.putExtra("type", 62);
      var7.putExtra("rid", var8);
      String var11 = this.mSessionId;
      var7.putExtra("sid", var11);
      String var13 = this.mSessionInfo.sessionKey;
      var7.putExtra("session_key", var13);
      var7.putExtra("aid", var2);
      var7.putExtra("name", var3);
      if(var4 != null) {
         var7.putExtra("location", var4);
      }

      if(var5 != null) {
         var7.putExtra("description", var5);
      }

      if(var6 != null) {
         var7.putExtra("visibility", var6);
      }

      this.postToService(var1, var8, var7);
      return var8;
   }

   public String photoEditPhoto(Context var1, String var2, String var3, String var4) {
      Intent var5 = new Intent(var1, FacebookService.class);
      String var6 = nextRequestId();
      Intent var7 = var5.putExtra("type", 66);
      var5.putExtra("rid", var6);
      String var9 = this.mSessionId;
      var5.putExtra("sid", var9);
      String var11 = this.mSessionInfo.sessionKey;
      var5.putExtra("session_key", var11);
      var5.putExtra("aid", var2);
      var5.putExtra("pid", var3);
      if(var4 != null) {
         var5.putExtra("caption", var4);
      }

      this.postToService(var1, var6, var5);
      return var6;
   }

   public String photoGetAlbums(Context var1, long var2, List<String> var4) {
      Intent var5 = new Intent(var1, FacebookService.class);
      String var6 = nextRequestId();
      Intent var7 = var5.putExtra("type", 60);
      var5.putExtra("rid", var6);
      String var9 = this.mSessionId;
      var5.putExtra("sid", var9);
      String var11 = this.mSessionInfo.sessionKey;
      var5.putExtra("session_key", var11);
      if(var4 != null) {
         String[] var13 = this.stringCollectionToSortedArray(var4);
         var5.putExtra("aid", var13);
      } else {
         var5.putExtra("uid", var2);
      }

      this.postToService(var1, var6, var5);
      return var6;
   }

   public String photoGetComments(Context var1, String var2) {
      Intent var3 = new Intent(var1, FacebookService.class);
      String var4 = nextRequestId();
      Intent var5 = var3.putExtra("type", 70);
      var3.putExtra("rid", var4);
      String var7 = this.mSessionId;
      var3.putExtra("sid", var7);
      String var9 = this.mSessionInfo.sessionKey;
      var3.putExtra("session_key", var9);
      var3.putExtra("pid", var2);
      this.postToService(var1, var4, var3);
      return var4;
   }

   public String photoGetPhotos(Context var1, String var2, Collection<String> var3, long var4) {
      return this.photoGetPhotos(var1, var2, var3, var4, 0, -1);
   }

   public String photoGetPhotos(Context var1, String var2, Collection<String> var3, long var4, int var6, int var7) {
      Intent var8 = new Intent(var1, FacebookService.class);
      String var9 = nextRequestId();
      Intent var10 = var8.putExtra("type", 64);
      var8.putExtra("rid", var9);
      String var12 = this.mSessionId;
      var8.putExtra("sid", var12);
      String var14 = this.mSessionInfo.sessionKey;
      var8.putExtra("session_key", var14);
      var8.putExtra("aid", var2);
      var8.putExtra("start", var6);
      var8.putExtra("limit", var7);
      if(var3 != null) {
         String[] var19 = this.stringCollectionToSortedArray(var3);
         var8.putExtra("pid", var19);
      }

      var8.putExtra("uid", var4);
      this.postToService(var1, var9, var8);
      return var9;
   }

   public String photoGetTags(Context var1, List<String> var2) {
      Intent var3 = new Intent(var1, FacebookService.class);
      String var4 = nextRequestId();
      Intent var5 = var3.putExtra("type", 69);
      var3.putExtra("rid", var4);
      String var7 = this.mSessionId;
      var3.putExtra("sid", var7);
      String var9 = this.mSessionInfo.sessionKey;
      var3.putExtra("session_key", var9);
      String var11 = listToCommaString(var2, (boolean)1);
      var3.putExtra("pid", var11);
      this.postToService(var1, var4, var3);
      return var4;
   }

   public String photoUpload(Context var1, int var2, String var3, String var4, String var5, long var6, long var8, boolean var10, long var11, String var13, long var14, String var16, long var17) {
      String var19 = nextRequestId();
      int var20 = Integer.parseInt(var19);
      ServiceNotificationManager.beginPhotoUploadProgressNotification(var1, var20, var3, var4, var5);
      Intent var21 = new Intent(var1, FacebookService.class);
      Intent var22 = var21.putExtra("type", 65);
      var21.putExtra("rid", var19);
      String var24 = this.mSessionId;
      var21.putExtra("sid", var24);
      var21.putExtra("upload_id", var2);
      if(var3 != null) {
         var21.putExtra("aid", var3);
      }

      if(var4 != null) {
         var21.putExtra("caption", var4);
      }

      String var30 = "profile_id";
      var21.putExtra(var30, var6);
      String var35 = "checkin_id";
      var21.putExtra(var35, var8);
      var21.putExtra("uri", var5);
      String var40 = this.mSessionInfo.sessionKey;
      var21.putExtra("session_key", var40);
      long var42 = this.mSessionInfo.userId;
      var21.putExtra("session_user_id", var42);
      String var46 = "extra_photo_publish";
      var21.putExtra(var46, var10);
      String var51 = "extra_place";
      var21.putExtra(var51, var11);
      String var56 = "tags";
      var21.putExtra(var56, var13);
      String var60 = "extra_status_target_id";
      var21.putExtra(var60, var14);
      String var65 = "extra_status_privacy";
      var21.putExtra(var65, var16);
      String var69 = "upload_manager_extras_local_id";
      var21.putExtra(var69, var17);
      this.postToService(var1, var19, var21);
      return var19;
   }

   public String postToService(Context var1, ApiMethod var2, int var3, int var4, Bundle var5) {
      String var6 = nextRequestId();
      Intent var7 = new Intent(var1, FacebookService.class);
      var7.putExtra("type", var3);
      var7.putExtra("rid", var6);
      String var10 = this.mSessionId;
      var7.putExtra("sid", var10);
      var7.putExtra("extended_type", var4);
      if(var5 != null) {
         var7.putExtras(var5);
      }

      FacebookSessionInfo var14 = this.getSessionInfo();
      if(var14 != null) {
         String var15 = var14.sessionSecret;
         if(var15 != null) {
            var7.putExtra("ApiMethod.secret", var15);
         }
      }

      this.mPendingServiceRequestsMap.put(var6, var7);
      FacebookService.apiMethodReceiver.put(var6, var2);
      LoginStatus var19 = LoginStatus.STATUS_LOGGING_IN;
      LoginStatus var20 = this.mLoginStatus;
      if(var19 == var20 && !isLoginRequest(var7)) {
         this.mRequestsToHandleAfterLogin.add(var7);
      } else {
         var1.startService(var7);
      }

      return var6;
   }

   protected String postToService(Context var1, ApiMethod var2, int var3, Bundle var4) {
      return this.postToService(var1, var2, 1000, var3, var4);
   }

   public String registerForPush(Context var1, String var2) {
      String var3 = this.mSessionInfo.sessionKey;
      Object var5 = null;
      UserRegisterPushCallback var7 = new UserRegisterPushCallback(var1, (Intent)null, var3, (ApiMethodListener)var5, var2);
      return this.postToService(var1, var7, 700, (Bundle)null);
   }

   public void releaseStreamContainers() {
      Iterator var1 = this.mWallContainerMap.values().iterator();

      while(var1.hasNext()) {
         FacebookStreamContainer var2 = (FacebookStreamContainer)var1.next();
         var2.clear();
         FacebookStreamContainer.deregister(var2);
      }

      Iterator var3 = this.mPlacesActivityContainerMap.values().iterator();

      while(var3.hasNext()) {
         FacebookStreamContainer var4 = (FacebookStreamContainer)var3.next();
         var4.clear();
         FacebookStreamContainer.deregister(var4);
      }

      this.mWallContainerMap.clear();
      this.mPlacesActivityContainerMap.clear();
   }

   public void removeListener(AppSessionListener var1) {
      this.mListeners.removeListener(var1);
   }

   public String respondToFriendRequest(Context var1, long var2, boolean var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      Intent var7 = var6.putExtra("type", 132);
      var6.putExtra("rid", var5);
      String var9 = this.mSessionId;
      var6.putExtra("sid", var9);
      var6.putExtra("uid", var2);
      var6.putExtra("confirm", var4);
      String var13 = this.mSessionInfo.sessionKey;
      var6.putExtra("session_key", var13);
      long var15 = this.mSessionInfo.userId;
      var6.putExtra("session_user_id", var15);
      this.postToService(var1, var5, var6);
      return var5;
   }

   public void scheduleStatusPollingAlarm(Context var1, int var2, int var3) {
      AlarmManager var4 = (AlarmManager)var1.getSystemService("alarm");
      if(this.mPollingStatusAlarmIntent != null) {
         PendingIntent var5 = this.mPollingStatusAlarmIntent;
         var4.cancel(var5);
         this.mPollingStatusAlarmIntent.cancel();
      }

      if(var2 == -1) {
         int var6 = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(var1).getString("polling_interval", "60"));
         if(var6 > 120) {
            var6 = 120;
         } else if(var6 == 0) {
            var6 = 30;
         }

         var2 = var6 * '\uea60';
      }

      Intent var7 = new Intent(var1, FacebookService.class);
      StringBuilder var8 = (new StringBuilder()).append("com.facebook.katana.service.");
      String var9 = nextRequestId();
      String var10 = var8.append(var9).toString();
      var7.setAction(var10);
      Intent var12 = var7.putExtra("type", 203);
      String var13 = this.mSessionId;
      var7.putExtra("sid", var13);
      var7.putExtra("extra_attempt", var3);
      PendingIntent var16 = PendingIntent.getService(var1, 0, var7, 0);
      this.mPollingStatusAlarmIntent = var16;
      long var17 = System.currentTimeMillis();
      long var19 = (long)var2;
      long var21 = var17 + var19;
      PendingIntent var23 = this.mPollingStatusAlarmIntent;
      var4.set(0, var21, var23);
   }

   public void setIsEmployee(Context var1, boolean var2) {
      FacebookAffiliation.setIsEmployee(var1, var2);
   }

   public void settingsChanged(Context var1) {
      this.scheduleNotificationsPollingAlarm(var1, -1, 0);
      this.scheduleUsersPollingAlarm(var1, -1, 0);
      this.scheduleStatusPollingAlarm(var1, -1, 0);
   }

   public String streamAddComment(Context var1, long var2, String var4, String var5, FacebookProfile var6) {
      String var7 = nextRequestId();
      Intent var8 = new Intent(var1, FacebookService.class);
      Intent var9 = var8.putExtra("type", 33);
      var8.putExtra("rid", var7);
      String var11 = this.mSessionId;
      var8.putExtra("sid", var11);
      var8.putExtra("uid", var2);
      var8.putExtra("post_id", var4);
      var8.putExtra("status", var5);
      if(var6 != null) {
         var8.putExtra("actor", var6);
      }

      String var17 = this.mSessionInfo.sessionKey;
      var8.putExtra("session_key", var17);
      long var19 = this.mSessionInfo.userId;
      var8.putExtra("session_user_id", var19);
      this.postToService(var1, var7, var8);
      return var7;
   }

   public String streamAddLike(Context var1, long var2, String var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      Intent var7 = var6.putExtra("type", 36);
      var6.putExtra("rid", var5);
      String var9 = this.mSessionId;
      var6.putExtra("sid", var9);
      var6.putExtra("uid", var2);
      var6.putExtra("post_id", var4);
      String var13 = this.mSessionInfo.sessionKey;
      var6.putExtra("session_key", var13);
      long var15 = this.mSessionInfo.userId;
      var6.putExtra("session_user_id", var15);
      this.postToService(var1, var5, var6);
      return var5;
   }

   public String streamGet(Context param1, long param2, long[] param4, long param5, long param7, int param9, int param10, FacebookStreamType param11) {
      // $FF: Couldn't be decompiled
   }

   public String streamGetComments(Context var1, long var2, String var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      Intent var7 = var6.putExtra("type", 31);
      var6.putExtra("rid", var5);
      String var9 = this.mSessionId;
      var6.putExtra("sid", var9);
      var6.putExtra("uid", var2);
      var6.putExtra("post_id", var4);
      String var13 = this.mSessionInfo.sessionKey;
      var6.putExtra("session_key", var13);
      this.postToService(var1, var5, var6);
      return var5;
   }

   public String streamRemoveComment(Context var1, long var2, String var4, String var5) {
      String var6 = nextRequestId();
      Intent var7 = new Intent(var1, FacebookService.class);
      Intent var8 = var7.putExtra("type", 35);
      var7.putExtra("rid", var6);
      String var10 = this.mSessionId;
      var7.putExtra("sid", var10);
      var7.putExtra("uid", var2);
      var7.putExtra("post_id", var4);
      var7.putExtra("item_id", var5);
      String var15 = this.mSessionInfo.sessionKey;
      var7.putExtra("session_key", var15);
      this.postToService(var1, var6, var7);
      return var6;
   }

   public String streamRemoveLike(Context var1, long var2, String var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      Intent var7 = var6.putExtra("type", 37);
      var6.putExtra("rid", var5);
      String var9 = this.mSessionId;
      var6.putExtra("sid", var9);
      var6.putExtra("uid", var2);
      var6.putExtra("post_id", var4);
      String var13 = this.mSessionInfo.sessionKey;
      var6.putExtra("session_key", var13);
      this.postToService(var1, var5, var6);
      return var5;
   }

   public String streamRemovePost(Context var1, long var2, String var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      Intent var7 = var6.putExtra("type", 34);
      var6.putExtra("rid", var5);
      String var9 = this.mSessionId;
      var6.putExtra("sid", var9);
      var6.putExtra("uid", var2);
      var6.putExtra("post_id", var4);
      String var13 = this.mSessionInfo.sessionKey;
      var6.putExtra("session_key", var13);
      this.postToService(var1, var5, var6);
      return var5;
   }

   public String syncFriends(Context var1) {
      return this.syncFriends(var1, 3);
   }

   public String syncNotifications(Context var1) {
      this.pollNotifications(var1, 0);
      Intent var3 = new Intent(var1, FacebookService.class);
      String var4 = nextRequestId();
      Intent var5 = var3.putExtra("type", 51);
      var3.putExtra("rid", var4);
      String var7 = this.mSessionId;
      var3.putExtra("sid", var7);
      String var9 = this.mSessionInfo.sessionKey;
      var3.putExtra("session_key", var9);
      long var11 = this.mSessionInfo.userId;
      var3.putExtra("session_user_id", var11);
      this.postToService(var1, var4, var3);
      return var4;
   }

   public String unregisterForPush(Context var1) {
      String var2 = this.mSessionInfo.sessionKey;
      UserUnregisterPushCallback var3 = new UserUnregisterPushCallback(var1, (Intent)null, var2, (ApiMethodListener)null);
      return this.postToService(var1, var3, 701, (Bundle)null);
   }

   public void updateSessionInfo(Context var1, FacebookSessionInfo var2) {
      this.mSessionInfo = var2;
      String var3 = this.mSessionInfo.toJSONObject().toString();
      UserValuesManager.saveActiveSessionInfo(var1, var3);
   }

   public String usersGetBriefInfo(Context var1, long var2) {
      String var4 = nextRequestId();
      Intent var5 = new Intent(var1, FacebookService.class);
      Intent var6 = var5.putExtra("type", 83);
      var5.putExtra("rid", var4);
      String var8 = this.mSessionId;
      var5.putExtra("sid", var8);
      var5.putExtra("uid", var2);
      String var11 = this.mSessionInfo.sessionKey;
      var5.putExtra("session_key", var11);
      long var13 = this.mSessionInfo.userId;
      var5.putExtra("session_user_id", var13);
      this.postToService(var1, var4, var5);
      return var4;
   }

   public String usersGetInfo(Context var1, long var2) {
      String var4 = nextRequestId();
      Intent var5 = new Intent(var1, FacebookService.class);
      Intent var6 = var5.putExtra("type", 82);
      var5.putExtra("rid", var4);
      String var8 = this.mSessionId;
      var5.putExtra("sid", var8);
      var5.putExtra("uid", var2);
      String var11 = this.mSessionInfo.sessionKey;
      var5.putExtra("session_key", var11);
      long var13 = this.mSessionInfo.userId;
      var5.putExtra("session_user_id", var13);
      this.postToService(var1, var4, var5);
      return var4;
   }

   public String usersSearch(Context var1, String var2, int var3, int var4) {
      String var5 = nextRequestId();
      Intent var6 = new Intent(var1, FacebookService.class);
      Intent var7 = var6.putExtra("type", 211);
      var6.putExtra("rid", var5);
      String var9 = this.mSessionId;
      var6.putExtra("sid", var9);
      String var11 = this.mSessionInfo.sessionKey;
      var6.putExtra("session_key", var11);
      var6.putExtra("subject", var2);
      var6.putExtra("start", var3);
      var6.putExtra("limit", var4);
      this.postToService(var1, var5, var6);
      return var5;
   }

   public void widgetUpdate(Context var1) {
      if(this.mStatusesList.size() > 0) {
         this.initiateWidgetUpdate(var1);
      }
   }
}
