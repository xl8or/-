// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppSession.java

package com.facebook.katana.binding;

import android.app.*;
import android.appwidget.AppWidgetManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.RemoteViews;
import com.facebook.katana.*;
import com.facebook.katana.activity.media.ViewPhotoActivity;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.features.composer.ComposerUserSettings;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.*;
import com.facebook.katana.service.FacebookService;
import com.facebook.katana.service.UploadManager;
import com.facebook.katana.service.method.*;
import com.facebook.katana.util.*;
import com.facebook.katana.version.SDK5;
import com.facebook.katana.webview.MRoot;
import java.io.File;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana.binding:
//            ProfileImagesCache, StreamPhotosCache, WorkerThread, AppSessionListener, 
//            ChatSession, FacebookStreamContainer, ManagedDataStore, ServiceNotificationManager, 
//            StreamPhoto, ProfileImage, ExtendedReq, NetworkRequestCallback

public class AppSession
{
    public static class ContactData
    {

        final String mCell;
        final String mEmail;
        final String mName;

        public ContactData(String s, String s1, String s2)
        {
            mName = s;
            mEmail = s1;
            mCell = s2;
        }
    }

    private static interface StatusesQuery
    {

        public static final int INDEX_MESSAGE = 6;
        public static final int INDEX_TIMESTAMP = 5;
        public static final int INDEX_USER_DISPLAY_NAME = 3;
        public static final int INDEX_USER_FIRST_NAME = 1;
        public static final int INDEX_USER_ID = 0;
        public static final int INDEX_USER_LAST_NAME = 2;
        public static final int INDEX_USER_PIC = 4;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[7];
            as[0] = "user_id";
            as[1] = "first_name";
            as[2] = "last_name";
            as[3] = "display_name";
            as[4] = "user_pic";
            as[5] = "timestamp";
            as[6] = "message";
        }
    }

    public static final class LoginStatus extends Enum
    {

        public static LoginStatus valueOf(String s)
        {
            return (LoginStatus)Enum.valueOf(com/facebook/katana/binding/AppSession$LoginStatus, s);
        }

        public static LoginStatus[] values()
        {
            return (LoginStatus[])$VALUES.clone();
        }

        private static final LoginStatus $VALUES[];
        public static final LoginStatus STATUS_LOGGED_IN;
        public static final LoginStatus STATUS_LOGGED_OUT;
        public static final LoginStatus STATUS_LOGGING_IN;
        public static final LoginStatus STATUS_LOGGING_OUT;

        static 
        {
            STATUS_LOGGED_OUT = new LoginStatus("STATUS_LOGGED_OUT", 0);
            STATUS_LOGGING_IN = new LoginStatus("STATUS_LOGGING_IN", 1);
            STATUS_LOGGED_IN = new LoginStatus("STATUS_LOGGED_IN", 2);
            STATUS_LOGGING_OUT = new LoginStatus("STATUS_LOGGING_OUT", 3);
            LoginStatus aloginstatus[] = new LoginStatus[4];
            aloginstatus[0] = STATUS_LOGGED_OUT;
            aloginstatus[1] = STATUS_LOGGING_IN;
            aloginstatus[2] = STATUS_LOGGED_IN;
            aloginstatus[3] = STATUS_LOGGING_OUT;
            $VALUES = aloginstatus;
        }

        private LoginStatus(String s, int i)
        {
            super(s, i);
        }
    }


    public AppSession()
    {
        sessionInvalidHandled = false;
        mSessionMap.put(mSessionId, this);
        mActiveSessionId = mSessionId;
        mLoginStatus = LoginStatus.STATUS_LOGGED_OUT;
        mLatestPostTime = -1L;
        initializeSettingsState();
    }

    private void acquireWakeLock(Context context)
    {
        if(mWakeLock == null)
        {
            mWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(1, "FacebookService");
            mWakeLock.acquire();
        }
    }

    private static void clearCookies(Context context)
    {
        CookieSyncManager.createInstance(context);
        CookieManager.getInstance().removeAllCookie();
    }

    private static void clearPhotoUploads(Context context)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/UploadManager);
        intent.putExtra("type", 2);
        context.startService(intent);
    }

    public static void clearWidget(Context context, String s, String s1)
    {
        RemoteViews remoteviews = new RemoteViews(context.getPackageName(), 0x7f030090);
        remoteviews.setTextViewText(0x7f0e00d2, s);
        remoteviews.setTextViewText(0x7f0e0163, s1);
        Intent intent = IntentUriHandler.getIntentForUri(context, "fb://feed");
        intent.setFlags(0x14000000);
        remoteviews.setOnClickPendingIntent(0x7f0e0162, PendingIntent.getActivity(context, 0, intent, 0x10000000));
        remoteviews.setOnClickPendingIntent(0x7f0e0161, PendingIntent.getActivity(context, 0, new Intent(context, com/facebook/katana/LoginActivity), 0x10000000));
        ComponentName componentname = new ComponentName(context, com/facebook/katana/FacebookWidgetProvider);
        AppWidgetManager.getInstance(context).updateAppWidget(componentname, remoteviews);
    }

    private String downloadPhoto(Context context, int i, long l, String s, String s1, String s2)
    {
        String s3 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", i);
        intent.putExtra("rid", s3);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("aid", s);
        intent.putExtra("pid", s1);
        intent.putExtra("uri", s2);
        postToService(context, s3, intent);
        return s3;
    }

    private String downloadUserImage(Context context, long l, String s)
    {
        String s1 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 100);
        intent.putExtra("rid", s1);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("uri", s);
        postToService(context, s1, intent);
        return s1;
    }

    public static AppSession getActiveSession(Context context, boolean flag)
    {
        AppSession appsession;
        AppSession appsession1;
        Boolean boolean1;
        if(mActiveSessionId != null)
        {
            appsession = (AppSession)mSessionMap.get(mActiveSessionId);
        } else
        {
            String s = UserValuesManager.loadActiveSessionInfo(context);
            if(s != null)
            {
                AppSession appsession2;
                try
                {
                    FacebookSessionInfo facebooksessioninfo = FacebookSessionInfo.parseFromJson(s);
                    if(facebooksessioninfo.sessionSecret != null)
                    {
                        AppSession appsession3 = new AppSession();
                        Intent intent1 = new Intent(context, com/facebook/katana/service/FacebookService);
                        intent1.putExtra("type", 1);
                        intent1.putExtra("rid", nextRequestId());
                        intent1.putExtra("sid", appsession3.mSessionId);
                        onServiceOperationComplete(context, intent1, 200, "Ok", null, facebooksessioninfo, null);
                    }
                }
                catch(Exception exception)
                {
                    mSessionMap.remove(mActiveSessionId);
                    mActiveSessionId = null;
                }
                if(mActiveSessionId != null)
                    appsession2 = (AppSession)mSessionMap.get(mActiveSessionId);
                else
                    appsession2 = null;
                if(appsession2 != null)
                {
                    if(appsession2.mSessionInfo.getProfile().getDisplayName().length() == 0)
                    {
                        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
                        String s1 = nextRequestId();
                        intent.putExtra("type", 3);
                        intent.putExtra("rid", s1);
                        intent.putExtra("sid", appsession2.mSessionId);
                        intent.putExtra("session_key", appsession2.mSessionInfo.sessionKey);
                        intent.putExtra("uid", appsession2.mSessionInfo.userId);
                        appsession2.postToService(context, s1, intent);
                    }
                    if(appsession2.mSessionInfo.oAuthToken == null)
                        GraphApiExchangeSession.RequestOAuthToken(context);
                    appsession = appsession2;
                } else
                {
                    appsession = appsession2;
                }
            } else
            {
                appsession = null;
            }
        }
        if(!flag || appsession == null || appsession.mSessionInfo == null || !Constants.isBetaBuild()) goto _L2; else goto _L1
_L1:
        boolean1 = Gatekeeper.get(context, "android_beta");
        if(boolean1 == null || boolean1.booleanValue()) goto _L2; else goto _L3
_L3:
        appsession1 = null;
        FacebookAffiliation.maybeToast(context);
_L5:
        if(appsession1 == null)
            clearPhotoUploads(context);
        return appsession1;
_L2:
        appsession1 = appsession;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public static String getUsernameHint(Context context)
    {
        String s1 = KeyValueManager.getValue(context, "last_username", null);
        String s = s1;
_L2:
        return s;
        Throwable throwable;
        throwable;
        s = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void handleLogin(Context context, FacebookSessionInfo facebooksessioninfo)
    {
        if(SIMULATE_102 && got102)
            fixed102 = true;
        mLoginStatus = LoginStatus.STATUS_LOGGED_IN;
        UserValuesManager.saveActiveSessionInfo(context, facebooksessioninfo.toJSONObject().toString());
        mSessionInfo = facebooksessioninfo;
        sessionInvalidHandled = false;
        mWorkerThread = new WorkerThread();
        mUserImageCache.open(context, mWorkerThread);
        mPhotosCache.open(context, mWorkerThread);
        mStatusesList.addAll(loadUserStatuses(context));
        if(mStatusesList.size() == 0)
        {
            clearWidget(context, context.getString(0x7f0a0231), "");
        } else
        {
            mCurrentStatusIndex = 0;
            initiateWidgetUpdate(context);
        }
        scheduleUsersPollingAlarm(context, 1000, 0);
        scheduleNotificationsPollingAlarm(context, 10000, 0);
        FacebookNotifications.load(context);
        resumePhotoUploads(context);
    }

    private void handleLoginResult(Context context, String s, int i, String s1, Exception exception, Object obj, boolean flag)
    {
_L4:
        Intent intent;
        for(Iterator iterator1 = mRequestsToHandleAfterLogin.iterator(); iterator1.hasNext(); postToService(context, intent.getStringExtra("rid"), intent))
        {
            intent = (Intent)iterator1.next();
            int j = intent.getIntExtra("type", 0);
            if(j != 1000 && j != 1001)
                continue;
            String s2 = intent.getStringExtra("rid");
            ApiMethod apimethod = (ApiMethod)FacebookService.apiMethodReceiver.get(s2);
            if(!$assertionsDisabled && apimethod == null)
                throw new AssertionError();
            apimethod.addAuthenticationData(mSessionInfo);
        }

        mRequestsToHandleAfterLogin.clear();
_L2:
        if(mLoginStatus == LoginStatus.STATUS_LOGGED_IN)
        {
            FqlGetGatekeeperSettings.SyncAll(context);
            ComposerUserSettings.get(context, "composer_tour_completed");
            if(!FacebookAffiliation.synced())
                FacebookAffiliation.syncStatus(context);
            if(FacebookAffiliation.shouldInitiateRequest())
                getFacebookAffiliationStatus(context, mSessionInfo.userId);
        }
        for(Iterator iterator = mListeners.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onLoginComplete(this, s, i, s1, exception));
        break; /* Loop/switch isn't completed */
        if(mActiveSessionId.equals(mSessionId))
            if(i == 200)
            {
                handleLogin(context, (FacebookSessionInfo)obj);
                if(flag)
                    Toaster.toast(context, 0x7f0a009e);
                FacebookAffiliation.invalidateEmployeeBit(context);
                break MISSING_BLOCK_LABEL_46;
            } else
            {
                mLoginStatus = LoginStatus.STATUS_LOGGED_OUT;
                if(flag)
                {
                    postLoginRequiredNotification(context);
                    if(mRequestsToHandleAfterLogin.isEmpty())
                        Toaster.toast(context, 0x7f0a0095);
                } else
                {
                    mLoginStatus = LoginStatus.STATUS_LOGGED_OUT;
                    mSessionMap.remove(mSessionId);
                }
                continue; /* Loop/switch isn't completed */
            }
        if(!flag)
            mSessionMap.remove(mSessionId);
        if(true) goto _L2; else goto _L1
_L1:
        return;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void handleLogout(Context context)
    {
        clearCookies(context);
        KeyValueManager.setValue(context, "last_username", mSessionInfo.username);
        C2DMReceiver.logout(context);
        if(Integer.parseInt(android.os.Build.VERSION.SDK) >= 5)
            SDK5.clearWebStorage();
        MRoot.reset(context);
        KeyValueManager.delete(context, "key=\"C2DMKey\"", null);
        mLoginStatus = LoginStatus.STATUS_LOGGED_OUT;
        ChatSession.shutdown(false);
        mPendingServiceRequestsMap.clear();
        releaseStreamContainers();
        if(mHomeStreamContainer != null)
            mHomeStreamContainer.clear();
        mListeners.clear();
        mUserImageCache.close();
        if(mWorkerThread != null)
        {
            mWorkerThread.quit();
            mWorkerThread = null;
        }
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
        mPhotosCache.close();
        mStatusesList.clear();
        clearWidget(context, context.getString(0x7f0a0230), context.getString(0x7f0a022f));
        clearPhotoUploads(context);
        AlarmManager alarmmanager = (AlarmManager)context.getSystemService("alarm");
        if(mPollingUsersAlarmIntent != null)
        {
            alarmmanager.cancel(mPollingUsersAlarmIntent);
            mPollingUsersAlarmIntent.cancel();
            mPollingUsersAlarmIntent = null;
        }
        if(mPollingStatusAlarmIntent != null)
        {
            alarmmanager.cancel(mPollingStatusAlarmIntent);
            mPollingStatusAlarmIntent.cancel();
            mPollingStatusAlarmIntent = null;
        }
        if(mPollingStreamAlarmIntent != null)
        {
            alarmmanager.cancel(mPollingStreamAlarmIntent);
            mPollingStreamAlarmIntent.cancel();
            mPollingStreamAlarmIntent = null;
        }
        if(mPollingNotificationsAlarmIntent != null)
        {
            alarmmanager.cancel(mPollingNotificationsAlarmIntent);
            mPollingNotificationsAlarmIntent.cancel();
            mPollingNotificationsAlarmIntent = null;
        }
        initializeSettingsState();
        FacebookAffiliation.invalidateEmployeeBit(context);
        ManagedDataStore.invalidateAllManagedDataStores();
        mSessionMap.remove(mSessionId);
        if(mActiveSessionId.equals(mSessionId))
            ServiceNotificationManager.release(context);
        if(mSessionMap.size() == 0)
        {
            releaseWakeLock();
            context.stopService(new Intent(context, com/facebook/katana/service/FacebookService));
            FacebookService.apiMethodReceiver.clear();
        }
        Utils.updateErrorReportingUserId(context, true);
    }

    private void initiateWidgetUpdate(Context context)
    {
        FacebookStatus facebookstatus = (FacebookStatus)mStatusesList.get(mCurrentStatusIndex);
        FacebookUser facebookuser = facebookstatus.getUser();
        Bitmap bitmap = null;
        if(facebookuser.mImageUrl != null)
            bitmap = mUserImageCache.get(context, facebookuser.mUserId, facebookuser.mImageUrl);
        updateWidget(context, facebookstatus, mCurrentStatusIndex, bitmap);
    }

    private static boolean isLoginRequest(Intent intent)
    {
        int i = intent.getIntExtra("type", -1);
        boolean flag;
        if(1 == i || 301 == i)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean isWidgetEnabled(Context context)
    {
        boolean flag;
        if(AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, com/facebook/katana/FacebookWidgetProvider)).length == 0)
            flag = false;
        else
            flag = true;
        return flag;
    }

    private static String listToCommaString(List list, boolean flag)
    {
        StringBuffer stringbuffer = new StringBuffer(64);
        boolean flag1 = true;
        for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            Object obj = iterator.next();
            if(!flag1)
                stringbuffer.append(",");
            else
                flag1 = false;
            if(flag)
                stringbuffer.append("'").append(obj).append("'");
            else
                stringbuffer.append(obj);
        }

        return stringbuffer.toString();
    }

    private static List loadUserStatuses(Context context)
    {
        ArrayList arraylist = new ArrayList();
        Cursor cursor = context.getContentResolver().query(UserStatusesProvider.CONTENT_URI, StatusesQuery.PROJECTION, null, null, null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
                do
                    arraylist.add(new FacebookStatus(new FacebookUser(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)), cursor.getString(6), cursor.getLong(5)));
                while(cursor.moveToNext());
            cursor.close();
        }
        return arraylist;
    }

    private static String nextRequestId()
    {
        StringBuilder stringbuilder = (new StringBuilder()).append("");
        int i = mUniqueId;
        mUniqueId = i + 1;
        return stringbuilder.append(i).toString();
    }

    private void onOperationComplete(Context context, Intent intent, int i, String s, Exception exception, Object obj, Object obj1)
    {
        int j;
        String s1;
        j = intent.getIntExtra("type", -1);
        if(ApiMethod.isSessionKeyError(i, exception))
        {
            if(SIMULATE_102)
                got102 = true;
            if(getSessionInfo() != null && StringUtils.saneStringEquals(intent.getStringExtra("session_key"), getSessionInfo().sessionKey) && !sessionInvalidHandled)
            {
                postLoginRequiredNotification(context);
                sessionInvalidHandled = true;
            }
        }
        s1 = intent.getStringExtra("rid");
        if(s1 != null)
            mPendingServiceRequestsMap.remove(s1);
        j;
        JVM INSTR lookupswitch 56: default 556
    //                   1: 601
    //                   2: 637
    //                   30: 822
    //                   31: 1204
    //                   33: 1295
    //                   34: 1140
    //                   35: 1481
    //                   36: 1569
    //                   37: 1652
    //                   50: 2188
    //                   51: 2289
    //                   60: 2337
    //                   61: 2408
    //                   62: 2461
    //                   63: 2515
    //                   64: 2569
    //                   65: 2650
    //                   66: 2864
    //                   67: 2924
    //                   68: 3029
    //                   69: 3088
    //                   70: 3141
    //                   71: 3208
    //                   72: 3304
    //                   73: 3304
    //                   74: 3407
    //                   75: 3461
    //                   76: 3521
    //                   77: 3581
    //                   80: 1876
    //                   81: 2057
    //                   82: 1735
    //                   83: 1809
    //                   90: 4465
    //                   91: 4531
    //                   92: 4575
    //                   100: 3638
    //                   110: 3896
    //                   111: 4069
    //                   112: 4117
    //                   113: 4178
    //                   114: 4239
    //                   115: 3955
    //                   121: 4612
    //                   122: 4684
    //                   131: 4753
    //                   132: 4810
    //                   133: 4882
    //                   201: 4365
    //                   202: 4386
    //                   203: 4436
    //                   211: 4293
    //                   300: 4605
    //                   301: 619
    //                   1000: 4939
    //                   1001: 4958;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L17 _L18 _L19 _L20 _L21 _L22 _L23 _L24 _L25 _L25 _L26 _L27 _L28 _L29 _L30 _L31 _L32 _L33 _L34 _L35 _L36 _L37 _L38 _L39 _L40 _L41 _L42 _L43 _L44 _L45 _L46 _L47 _L48 _L49 _L50 _L51 _L52 _L53 _L54 _L55 _L56
_L1:
        if(mPendingServiceRequestsMap.size() == 0)
        {
            releaseWakeLock();
            Intent intent1 = new Intent(context, com/facebook/katana/service/FacebookService);
            context.stopService(intent1);
            FacebookService.apiMethodReceiver.clear();
        }
        return;
_L2:
        handleLoginResult(context, s1, i, s, exception, obj, false);
        continue; /* Loop/switch isn't completed */
_L54:
        handleLoginResult(context, s1, i, s, exception, obj, true);
        continue; /* Loop/switch isn't completed */
_L3:
        if(mActiveSessionId.equals(mSessionId))
            UserValuesManager.saveActiveSessionInfo(context, null);
        Iterator iterator41 = mPendingServiceRequestsMap.values().iterator();
        do
        {
            if(!iterator41.hasNext())
                break;
            Intent intent2 = (Intent)iterator41.next();
            if(intent2.getIntExtra("type", -1) == 80)
            {
                String s12 = intent2.getStringExtra("rid");
                Iterator iterator43 = mListeners.getListeners().iterator();
                while(iterator43.hasNext()) 
                    ((AppSessionListener)iterator43.next()).onFriendsSyncComplete(this, s12, 400, "Canceled", null);
            }
        } while(true);
        for(Iterator iterator42 = mListeners.getListeners().iterator(); iterator42.hasNext(); ((AppSessionListener)iterator42.next()).onLogoutComplete(this, s1, i, s, exception));
        handleLogout(context);
        continue; /* Loop/switch isn't completed */
_L4:
        List list1 = (List)obj;
        FacebookStreamContainer facebookstreamcontainer = null;
        long al1[] = intent.getLongArrayExtra("subject");
        if(i == 200)
        {
            if(list1.size() > 1 && al1 == null && ((FacebookPost)list1.get(0)).createdTime > mLatestPostTime)
                mLatestPostTime = ((FacebookPost)list1.get(0)).createdTime;
            Iterator iterator40;
            if(al1 == null)
            {
                if(mHomeStreamContainer == null)
                    mHomeStreamContainer = new FacebookStreamContainer();
                mHomeStreamContainer.addPosts(list1, intent.getIntExtra("limit", 20), intent.getIntExtra("app_value", 0));
                facebookstreamcontainer = mHomeStreamContainer;
            } else
            {
                facebookstreamcontainer = (FacebookStreamContainer)mWallContainerMap.get(Long.valueOf(al1[0]));
                if(facebookstreamcontainer == null)
                {
                    facebookstreamcontainer = new FacebookStreamContainer();
                    mWallContainerMap.put(Long.valueOf(al1[0]), facebookstreamcontainer);
                }
                int i3 = intent.getIntExtra("limit", 20);
                int j3 = intent.getIntExtra("app_value", 0);
                facebookstreamcontainer.addPosts(list1, i3, j3);
            }
        }
        iterator40 = mListeners.getListeners().iterator();
        while(iterator40.hasNext()) 
            ((AppSessionListener)iterator40.next()).onStreamGetComplete(this, s1, i, s, exception, intent.getLongExtra("uid", -1L), al1, intent.getLongExtra("start", -1L), intent.getLongExtra("end", -1L), intent.getIntExtra("limit", 30), intent.getIntExtra("app_value", -1), list1, facebookstreamcontainer);
        continue; /* Loop/switch isn't completed */
_L7:
        if(i == 200)
            FacebookStreamContainer.remove(intent.getStringExtra("post_id"));
        Iterator iterator39 = mListeners.getListeners().iterator();
        while(iterator39.hasNext()) 
            ((AppSessionListener)iterator39.next()).onStreamRemovePostComplete(this, s1, i, s, exception);
        continue; /* Loop/switch isn't completed */
_L5:
        String s11 = intent.getStringExtra("post_id");
        if(i == 200)
        {
            List list = (List)obj;
            FacebookPost facebookpost4 = FacebookStreamContainer.get(s11);
            if(facebookpost4 != null)
                facebookpost4.updateComments(list);
        }
        Iterator iterator38 = mListeners.getListeners().iterator();
        while(iterator38.hasNext()) 
            ((AppSessionListener)iterator38.next()).onStreamGetCommentsComplete(this, s1, i, s, exception, s11);
        continue; /* Loop/switch isn't completed */
_L6:
        com.facebook.katana.model.FacebookPost.Comment comment = new com.facebook.katana.model.FacebookPost.Comment((String)obj, mSessionInfo.userId, System.currentTimeMillis() / 1000L, intent.getStringExtra("status"));
        String s10;
        Iterator iterator37;
        if(intent.hasExtra("actor"))
            comment.setProfile((FacebookProfile)intent.getParcelableExtra("actor"));
        else
            comment.setProfile(new FacebookProfile(mSessionInfo.userId, mSessionInfo.getProfile().mDisplayName, mSessionInfo.getProfile().mImageUrl, 0));
        s10 = intent.getStringExtra("post_id");
        if(i == 200)
        {
            FacebookPost facebookpost3 = FacebookStreamContainer.get(s10);
            if(facebookpost3 != null)
                facebookpost3.addComment(comment);
        }
        iterator37 = mListeners.getListeners().iterator();
        while(iterator37.hasNext()) 
            ((AppSessionListener)iterator37.next()).onStreamAddCommentComplete(this, s1, i, s, exception, s10, comment);
        continue; /* Loop/switch isn't completed */
_L8:
        String s9 = intent.getStringExtra("post_id");
        if(i == 200)
        {
            FacebookPost facebookpost2 = FacebookStreamContainer.get(s9);
            if(facebookpost2 != null)
                facebookpost2.deleteComment(intent.getStringExtra("item_id"));
        }
        Iterator iterator36 = mListeners.getListeners().iterator();
        while(iterator36.hasNext()) 
            ((AppSessionListener)iterator36.next()).onStreamRemoveCommentComplete(this, s1, i, s, exception, s9);
        continue; /* Loop/switch isn't completed */
_L9:
        String s8 = intent.getStringExtra("post_id");
        if(i == 200)
        {
            FacebookPost facebookpost1 = FacebookStreamContainer.get(s8);
            if(facebookpost1 != null)
                facebookpost1.setUserLikes(true);
        }
        Iterator iterator35 = mListeners.getListeners().iterator();
        while(iterator35.hasNext()) 
            ((AppSessionListener)iterator35.next()).onStreamAddLikeComplete(this, s1, i, s, exception, s8);
        continue; /* Loop/switch isn't completed */
_L10:
        String s7 = intent.getStringExtra("post_id");
        if(i == 200)
        {
            FacebookPost facebookpost = FacebookStreamContainer.get(s7);
            if(facebookpost != null)
                facebookpost.setUserLikes(false);
        }
        Iterator iterator34 = mListeners.getListeners().iterator();
        while(iterator34.hasNext()) 
            ((AppSessionListener)iterator34.next()).onStreamRemoveLikeComplete(this, s1, i, s, exception, s7);
        continue; /* Loop/switch isn't completed */
_L32:
        FacebookUserFull facebookuserfull = (FacebookUserFull)obj;
        Iterator iterator33 = mListeners.getListeners().iterator();
        while(iterator33.hasNext()) 
            ((AppSessionListener)iterator33.next()).onUsersGetInfoComplete(this, s1, i, s, exception, intent.getLongExtra("uid", -1L), facebookuserfull, ((Boolean)obj1).booleanValue());
        continue; /* Loop/switch isn't completed */
_L33:
        FacebookUser facebookuser2 = (FacebookUser)obj;
        Iterator iterator32 = mListeners.getListeners().iterator();
        while(iterator32.hasNext()) 
            ((AppSessionListener)iterator32.next()).onUsersGetInfoComplete(this, s1, i, s, exception, intent.getLongExtra("uid", -1L), facebookuser2, false);
        continue; /* Loop/switch isn't completed */
_L30:
        Iterator iterator31;
        if(i == 200)
        {
            UserValuesManager.setLastContactsSync(context);
            Map map3 = (Map)obj;
            if(mSessionInfo != null && mSessionInfo.getProfile() != null)
            {
                FacebookUser facebookuser1 = mSessionInfo.getProfile();
                map3.put(Long.valueOf(facebookuser1.mUserId), facebookuser1.mImageUrl);
            }
            mUserImageCache.get(context, map3);
            scheduleUsersPollingAlarm(context, -1, 0);
        } else
        {
            int k2 = 1 + intent.getIntExtra("extra_attempt", 0);
            if(k2 < 3)
                scheduleUsersPollingAlarm(context, 0x493e0, k2);
            else
                scheduleUsersPollingAlarm(context, -1, 0);
        }
        scheduleStatusPollingAlarm(context, 1000, 0);
        iterator31 = mListeners.getListeners().iterator();
        while(iterator31.hasNext()) 
            ((AppSessionListener)iterator31.next()).onFriendsSyncComplete(this, s1, i, s, exception);
        continue; /* Loop/switch isn't completed */
_L31:
        if(i == 200)
        {
            mStatusesList.clear();
            mStatusesList.addAll((List)obj);
            mCurrentStatusIndex = 0;
            if(mStatusesList.size() > 0)
                initiateWidgetUpdate(context);
            scheduleStatusPollingAlarm(context, -1, 0);
        } else
        {
            int j2 = 1 + intent.getIntExtra("extra_attempt", 0);
            if(j2 < 3)
            {
                scheduleStatusPollingAlarm(context, 30000, j2);
            } else
            {
                if(mStatusesList.size() == 0)
                    clearWidget(context, context.getString(0x7f0a022e), "");
                scheduleStatusPollingAlarm(context, -1, 0);
            }
        }
        continue; /* Loop/switch isn't completed */
_L11:
        if(i == 200)
        {
            FacebookNotifications facebooknotifications = (FacebookNotifications)obj;
            if(facebooknotifications.hasNewNotifications())
                ServiceNotificationManager.showNotification(context, mSessionInfo.userId, facebooknotifications, mSessionInfo.sessionKey, mSessionInfo.sessionSecret);
            scheduleNotificationsPollingAlarm(context, -1, 0);
        } else
        {
            int i2 = 1 + intent.getIntExtra("extra_attempt", 0);
            if(i2 < 3)
                scheduleNotificationsPollingAlarm(context, 60000, i2);
            else
                scheduleNotificationsPollingAlarm(context, -1, 0);
        }
        continue; /* Loop/switch isn't completed */
_L12:
        Iterator iterator30 = mListeners.getListeners().iterator();
        while(iterator30.hasNext()) 
            ((AppSessionListener)iterator30.next()).onGetNotificationHistoryComplete(this, s1, i, s, exception);
        continue; /* Loop/switch isn't completed */
_L13:
        long l5 = intent.getLongExtra("uid", -1L);
        String as1[] = intent.getStringArrayExtra("aid");
        Iterator iterator29 = mListeners.getListeners().iterator();
        while(iterator29.hasNext()) 
            ((AppSessionListener)iterator29.next()).onPhotoGetAlbumsComplete(this, s1, i, s, exception, as1, l5);
        continue; /* Loop/switch isn't completed */
_L14:
        Iterator iterator28 = mListeners.getListeners().iterator();
        while(iterator28.hasNext()) 
            ((AppSessionListener)iterator28.next()).onPhotoCreateAlbumComplete(this, s1, i, s, exception, (FacebookAlbum)obj);
        continue; /* Loop/switch isn't completed */
_L15:
        Iterator iterator27 = mListeners.getListeners().iterator();
        while(iterator27.hasNext()) 
            ((AppSessionListener)iterator27.next()).onPhotoEditAlbumComplete(this, s1, i, s, exception, intent.getStringExtra("aid"));
        continue; /* Loop/switch isn't completed */
_L16:
        Iterator iterator26 = mListeners.getListeners().iterator();
        while(iterator26.hasNext()) 
            ((AppSessionListener)iterator26.next()).onPhotoDeleteAlbumComplete(this, s1, i, s, exception, intent.getStringExtra("aid"));
        continue; /* Loop/switch isn't completed */
_L17:
        String s6 = intent.getStringExtra("aid");
        String as[] = intent.getStringArrayExtra("pid");
        long l4 = intent.getLongExtra("uid", -1L);
        Iterator iterator25 = mListeners.getListeners().iterator();
        while(iterator25.hasNext()) 
            ((AppSessionListener)iterator25.next()).onPhotoGetPhotosComplete(this, s1, i, s, exception, s6, as, l4);
        continue; /* Loop/switch isn't completed */
_L18:
        FacebookPhoto facebookphoto = (FacebookPhoto)obj;
        String s3;
        String s4;
        String s5;
        Iterator iterator24;
        if(facebookphoto != null)
        {
            s3 = facebookphoto.getAlbumId();
            s4 = facebookphoto.getPhotoId();
        } else
        {
            s3 = null;
            s4 = null;
        }
        if(i == 200 && s3 != null)
        {
            ArrayList arraylist1 = new ArrayList(1);
            arraylist1.add(s3);
            photoGetAlbums(context, -1L, arraylist1);
        }
        s5 = intent.getStringExtra("uri");
        if(!ServiceNotificationManager.endPhotoUploadProgressNotification(context, Integer.parseInt(s1), i, s5, s3, s4))
        {
            File file = new File(s5);
            file.delete();
        }
        iterator24 = mListeners.getListeners().iterator();
        while(iterator24.hasNext()) 
            ((AppSessionListener)iterator24.next()).onPhotoUploadComplete(this, s1, i, s, exception, intent.getIntExtra("upload_id", -1), facebookphoto, intent.getStringExtra("uri"), intent.getLongExtra("checkin_id", -1L), intent.getLongExtra("profile_id", -1L), intent.getLongExtra("upload_manager_extras_local_id", -1L));
        continue; /* Loop/switch isn't completed */
_L19:
        Iterator iterator23 = mListeners.getListeners().iterator();
        while(iterator23.hasNext()) 
            ((AppSessionListener)iterator23.next()).onPhotoEditPhotoComplete(this, s1, i, s, exception, intent.getStringExtra("aid"), intent.getStringExtra("pid"));
        continue; /* Loop/switch isn't completed */
_L20:
        if(((Boolean)obj).booleanValue())
        {
            ArrayList arraylist = new ArrayList();
            arraylist.add(intent.getStringExtra("aid"));
            photoGetAlbums(context, -1L, arraylist);
        }
        Iterator iterator22 = mListeners.getListeners().iterator();
        while(iterator22.hasNext()) 
            ((AppSessionListener)iterator22.next()).onPhotoDeletePhotoComplete(this, s1, i, s, exception, intent.getStringExtra("aid"), intent.getStringExtra("pid"));
        continue; /* Loop/switch isn't completed */
_L21:
        Iterator iterator21 = mListeners.getListeners().iterator();
        while(iterator21.hasNext()) 
            ((AppSessionListener)iterator21.next()).onPhotoAddTagsComplete(this, s1, i, s, exception, intent.getStringExtra("pid"), (List)obj);
        continue; /* Loop/switch isn't completed */
_L22:
        Iterator iterator20 = mListeners.getListeners().iterator();
        while(iterator20.hasNext()) 
            ((AppSessionListener)iterator20.next()).onPhotoGetTagsComplete(this, s1, i, s, exception, (List)obj);
        continue; /* Loop/switch isn't completed */
_L23:
        Iterator iterator19 = mListeners.getListeners().iterator();
        while(iterator19.hasNext()) 
            ((AppSessionListener)iterator19.next()).onPhotoGetCommentsComplete(this, s1, i, s, exception, intent.getStringExtra("pid"), (List)obj, ((Boolean)obj1).booleanValue());
        continue; /* Loop/switch isn't completed */
_L24:
        FacebookPhotoComment facebookphotocomment = null;
        if(i == 200)
        {
            facebookphotocomment = (FacebookPhotoComment)obj;
            FacebookProfile facebookprofile = new FacebookProfile(mSessionInfo.getProfile());
            facebookphotocomment.setFromProfile(facebookprofile);
        }
        Iterator iterator18 = mListeners.getListeners().iterator();
        while(iterator18.hasNext()) 
            ((AppSessionListener)iterator18.next()).onPhotoAddCommentComplete(this, s1, i, s, exception, intent.getStringExtra("pid"), facebookphotocomment);
        continue; /* Loop/switch isn't completed */
_L25:
        String s2 = intent.getStringExtra("uri");
        StreamPhoto streamphoto = (StreamPhoto)obj;
        mPhotosCache.onDownloadComplete(context, i, s2, streamphoto);
        Iterator iterator17 = mListeners.getListeners().iterator();
        while(iterator17.hasNext()) 
        {
            AppSessionListener appsessionlistener = (AppSessionListener)iterator17.next();
            Bitmap bitmap1;
            if(streamphoto != null)
                bitmap1 = streamphoto.getBitmap();
            else
                bitmap1 = null;
            appsessionlistener.onDownloadStreamPhotoComplete(this, s1, i, s, exception, s2, bitmap1);
        }
        continue; /* Loop/switch isn't completed */
_L26:
        Iterator iterator16 = mListeners.getListeners().iterator();
        while(iterator16.hasNext()) 
            ((AppSessionListener)iterator16.next()).onDownloadAlbumThumbnailComplete(this, s1, i, s, exception, intent.getStringExtra("aid"));
        continue; /* Loop/switch isn't completed */
_L27:
        Iterator iterator15 = mListeners.getListeners().iterator();
        while(iterator15.hasNext()) 
            ((AppSessionListener)iterator15.next()).onDownloadPhotoThumbnailComplete(this, s1, i, s, exception, intent.getStringExtra("aid"), intent.getStringExtra("pid"));
        continue; /* Loop/switch isn't completed */
_L28:
        Iterator iterator14 = mListeners.getListeners().iterator();
        while(iterator14.hasNext()) 
            ((AppSessionListener)iterator14.next()).onDownloadPhotoFullComplete(this, s1, i, s, exception, intent.getStringExtra("aid"), intent.getStringExtra("pid"));
        continue; /* Loop/switch isn't completed */
_L29:
        Bitmap bitmap = (Bitmap)obj;
        Iterator iterator13 = mListeners.getListeners().iterator();
        while(iterator13.hasNext()) 
            ((AppSessionListener)iterator13.next()).onDownloadPhotoRawComplete(this, s1, i, s, exception, bitmap);
        continue; /* Loop/switch isn't completed */
_L37:
        long l3 = intent.getLongExtra("uid", -1L);
        ProfileImage profileimage = (ProfileImage)obj;
        mUserImageCache.onDownloadComplete(context, i, l3, profileimage);
        if(i == 200)
        {
            if(mCurrentStatusIndex < mStatusesList.size() && ((FacebookStatus)mStatusesList.get(mCurrentStatusIndex)).getUser().mUserId == l3)
                updateWidget(context, (FacebookStatus)mStatusesList.get(mCurrentStatusIndex), mCurrentStatusIndex, profileimage.getBitmap());
            if(mSessionInfo.userId == l3)
            {
                FacebookUser facebookuser = mSessionInfo.getProfile();
                if(!profileimage.url.equals(facebookuser.mImageUrl))
                {
                    mSessionInfo.setProfile(new FacebookUser(facebookuser.mUserId, facebookuser.mFirstName, facebookuser.mLastName, facebookuser.mDisplayName, profileimage.url));
                    UserValuesManager.saveActiveSessionInfo(context, mSessionInfo.toJSONObject().toString());
                }
            }
            Iterator iterator12 = mListeners.getListeners().iterator();
            while(iterator12.hasNext()) 
                ((AppSessionListener)iterator12.next()).onProfileImageDownloaded(this, s1, i, s, exception, profileimage, mUserImageCache);
        }
        continue; /* Loop/switch isn't completed */
_L38:
        int k1 = intent.getIntExtra("folder", 0);
        Iterator iterator11 = mListeners.getListeners().iterator();
        while(iterator11.hasNext()) 
            ((AppSessionListener)iterator11.next()).onMailboxSyncComplete(this, s1, i, s, exception, k1);
        continue; /* Loop/switch isn't completed */
_L43:
        int j1 = intent.getIntExtra("folder", 0);
        long l2 = intent.getLongExtra("tid", -1L);
        for(Iterator iterator10 = mListeners.getListeners().iterator(); iterator10.hasNext(); ((AppSessionListener)iterator10.next()).onMailboxGetThreadMessagesComplete(this, s1, i, s, exception, j1, l2));
        if(i == 200 && intent.getBooleanExtra("read", true))
        {
            long al[] = new long[1];
            al[0] = l2;
            mailboxMarkThread(context, j1, al, true);
        }
        continue; /* Loop/switch isn't completed */
_L39:
        Iterator iterator9 = mListeners.getListeners().iterator();
        while(iterator9.hasNext()) 
            ((AppSessionListener)iterator9.next()).onMailboxSendComplete(this, s1, i, s, exception);
        continue; /* Loop/switch isn't completed */
_L40:
        long l1 = intent.getLongExtra("tid", -1L);
        Iterator iterator8 = mListeners.getListeners().iterator();
        while(iterator8.hasNext()) 
            ((AppSessionListener)iterator8.next()).onMailboxReplyComplete(this, s1, i, s, exception, l1);
        continue; /* Loop/switch isn't completed */
_L41:
        Iterator iterator7 = mListeners.getListeners().iterator();
        while(iterator7.hasNext()) 
            ((AppSessionListener)iterator7.next()).onMailboxMarkThreadComplete(this, s1, i, s, exception, intent.getLongArrayExtra("tid"), intent.getBooleanExtra("read", false));
        continue; /* Loop/switch isn't completed */
_L42:
        Iterator iterator6 = mListeners.getListeners().iterator();
        while(iterator6.hasNext()) 
            ((AppSessionListener)iterator6.next()).onMailboxDeleteThreadComplete(this, s1, i, s, exception, intent.getLongArrayExtra("tid"));
        continue; /* Loop/switch isn't completed */
_L52:
        int l = ((Integer)obj).intValue();
        int i1 = ((Integer)obj1).intValue();
        Iterator iterator5 = mListeners.getListeners().iterator();
        while(iterator5.hasNext()) 
            ((AppSessionListener)iterator5.next()).onUsersSearchComplete(this, s1, i, s, exception, l, i1);
        continue; /* Loop/switch isn't completed */
_L49:
        acquireWakeLock(context);
        pollNotifications(context, intent.getIntExtra("extra_attempt", 0));
        continue; /* Loop/switch isn't completed */
_L50:
        if(((ConnectivityManager)context.getSystemService("connectivity")).getBackgroundDataSetting())
        {
            if(syncFriends(context, intent.getIntExtra("extra_attempt", 0)) != null)
                acquireWakeLock(context);
        } else
        {
            scheduleUsersPollingAlarm(context, -1, 0);
        }
        continue; /* Loop/switch isn't completed */
_L51:
        if(isWidgetEnabled(context))
        {
            acquireWakeLock(context);
            usersPollStatuses(context, intent.getIntExtra("extra_attempt", 0));
        }
        continue; /* Loop/switch isn't completed */
_L34:
        int k = mStatusesList.size();
        if(k != 0)
            if(mCurrentStatusIndex >= 0 && mCurrentStatusIndex < k - 1)
            {
                mCurrentStatusIndex = 1 + mCurrentStatusIndex;
                initiateWidgetUpdate(context);
            } else
            if(mCurrentStatusIndex != k - 1);
        continue; /* Loop/switch isn't completed */
_L35:
        if(mStatusesList.size() != 0 && mCurrentStatusIndex != 0 && mCurrentStatusIndex > 0)
        {
            mCurrentStatusIndex = mCurrentStatusIndex - 1;
            initiateWidgetUpdate(context);
        }
        continue; /* Loop/switch isn't completed */
_L36:
        StreamPublish.Publish(context, mSessionInfo.userId, intent.getStringExtra("status"), intent.getStringExtra("status"), null, true, null);
        continue; /* Loop/switch isn't completed */
_L53:
        ServiceNotificationManager.handleClearNotifications(context);
        continue; /* Loop/switch isn't completed */
_L44:
        Long long3 = (Long)obj;
        Boolean boolean2 = (Boolean)obj1;
        Iterator iterator4 = mListeners.getListeners().iterator();
        while(iterator4.hasNext()) 
            ((AppSessionListener)iterator4.next()).onEventRsvpComplete(this, s1, i, s, exception, long3.longValue(), boolean2.booleanValue());
        continue; /* Loop/switch isn't completed */
_L45:
        Long long2 = (Long)obj;
        Map map2 = (Map)obj1;
        Iterator iterator3 = mListeners.getListeners().iterator();
        while(iterator3.hasNext()) 
            ((AppSessionListener)iterator3.next()).onEventGetMembersComplete(this, s1, i, s, exception, long2.longValue(), map2);
        continue; /* Loop/switch isn't completed */
_L46:
        Map map1 = (Map)obj;
        Iterator iterator2 = mListeners.getListeners().iterator();
        while(iterator2.hasNext()) 
            ((AppSessionListener)iterator2.next()).onUserGetFriendRequestsComplete(this, s1, i, s, exception, map1);
        continue; /* Loop/switch isn't completed */
_L47:
        Long long1 = (Long)obj;
        Boolean boolean1 = (Boolean)obj1;
        Iterator iterator1 = mListeners.getListeners().iterator();
        while(iterator1.hasNext()) 
            ((AppSessionListener)iterator1.next()).onFriendRequestRespondComplete(this, s1, i, s, exception, long1.longValue(), boolean1.booleanValue());
        continue; /* Loop/switch isn't completed */
_L48:
        Map map = (Map)obj;
        Iterator iterator = mListeners.getListeners().iterator();
        while(iterator.hasNext()) 
            ((AppSessionListener)iterator.next()).onFriendRequestsMutualFriendsComplete(this, s1, i, s, exception, map);
        continue; /* Loop/switch isn't completed */
_L55:
        ExtendedReq.onExtendedOperationComplete(this, context, intent, i, s, exception, (ApiMethod)obj);
        continue; /* Loop/switch isn't completed */
_L56:
        ((ApiMethodCallback)obj).executeCallbacks(this, context, intent, s1, i, s, exception);
        if(true) goto _L1; else goto _L57
_L57:
    }

    private void onOperationProgress(Context context, Intent intent, Object obj, Object obj1)
    {
        intent.getIntExtra("type", -1);
        JVM INSTR tableswitch 65 65: default 28
    //                   65 29;
           goto _L1 _L2
_L1:
        return;
_L2:
        ServiceNotificationManager.updateProgressNotification(context, Integer.parseInt(intent.getStringExtra("rid")), ((Integer)obj).intValue());
        if(true) goto _L1; else goto _L3
_L3:
    }

    public static void onServiceOperationComplete(Context context, Intent intent, int i, String s, Exception exception, Object obj, Object obj1)
    {
        AppSession appsession = (AppSession)mSessionMap.get(intent.getStringExtra("sid"));
        if(appsession == null) goto _L2; else goto _L1
_L1:
        appsession.onOperationComplete(context, intent, i, s, exception, obj, obj1);
_L4:
        return;
_L2:
        int j = intent.getIntExtra("type", -1);
        switch(j)
        {
        default:
            if(j == 65)
            {
                StringBuilder stringbuilder = new StringBuilder();
                stringbuilder.append(" reqIntent: ").append(intent.toString());
                Bundle bundle = intent.getExtras();
                StringBuilder stringbuilder1 = stringbuilder.append(" reqIntent.extras: ");
                AppSession appsession1;
                String s1;
                Object aobj[];
                if(bundle != null)
                    s1 = bundle.toString();
                else
                    s1 = "";
                stringbuilder1.append(s1);
                stringbuilder.append(" mSessionMap: ");
                aobj = new Object[1];
                aobj[0] = mSessionMap.keySet();
                StringUtils.join(stringbuilder, ", ", null, aobj);
                Utils.reportSoftError("photoupload_stuck", stringbuilder.toString());
            }
            Log.e("onServiceOperationComplete", (new StringBuilder()).append("No session: ").append(j).toString());
            break;

        case 90: // 'Z'
        case 91: // '['
        case 92: // '\\'
        case 201: 
        case 202: 
        case 203: 
        case 300: 
            appsession1 = getActiveSession(context, false);
            if(appsession1 != null)
                appsession1.onOperationComplete(context, intent, i, s, exception, obj, obj1);
            else
                Log.e("onServiceOperationComplete", (new StringBuilder()).append("No session: ").append(intent.getIntExtra("type", -1)).toString());
            break;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static void onServiceOperationProgress(Context context, Intent intent, Object obj, Object obj1)
    {
        AppSession appsession = (AppSession)mSessionMap.get(intent.getStringExtra("sid"));
        if(appsession != null)
            appsession.onOperationProgress(context, intent, obj, obj1);
    }

    private String pollNotifications(Context context, int i)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s = nextRequestId();
        intent.putExtra("type", 50);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("extra_attempt", i);
        postToService(context, s, intent);
        return s;
    }

    private void postLoginRequiredNotification(Context context)
    {
        if(mLoginStatus != LoginStatus.STATUS_LOGGING_OUT && mLoginStatus != LoginStatus.STATUS_LOGGED_OUT)
            postLoginRequiredNotification(context, mSessionInfo.username);
    }

    public static void postLoginRequiredNotification(Context context, String s)
    {
        Utils.updateErrorReportingUserId(context, true);
        (new Notification(0x108008a, context.getString(0x7f0a0094), System.currentTimeMillis())).flags = 16;
        Intent intent = new Intent(context, com/facebook/katana/LoginNotificationActivity);
        intent.putExtra("un", s);
        intent.addFlags(0x10000000);
        context.startActivity(intent);
    }

    private void postToService(Context context, String s, Intent intent)
    {
        FacebookSessionInfo facebooksessioninfo = getSessionInfo();
        if(facebooksessioninfo != null)
        {
            String s1 = facebooksessioninfo.sessionSecret;
            if(s1 != null)
                intent.putExtra("ApiMethod.secret", s1);
        }
        mPendingServiceRequestsMap.put(s, intent);
        if(LoginStatus.STATUS_LOGGING_IN != mLoginStatus || isLoginRequest(intent))
            context.startService(intent);
        else
            mRequestsToHandleAfterLogin.add(intent);
    }

    private void releaseWakeLock()
    {
        if(mWakeLock != null)
        {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    private static void resumePhotoUploads(Context context)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/UploadManager);
        intent.putExtra("type", 1);
        context.startService(intent);
    }

    private void scheduleNotificationsPollingAlarm(Context context, int i, int j)
    {
        AlarmManager alarmmanager;
        alarmmanager = (AlarmManager)context.getSystemService("alarm");
        if(mPollingNotificationsAlarmIntent != null)
        {
            alarmmanager.cancel(mPollingNotificationsAlarmIntent);
            mPollingNotificationsAlarmIntent.cancel();
        }
        if(i != -1) goto _L2; else goto _L1
_L1:
        int k = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString("polling_interval", "60"));
        if(k <= 120) goto _L4; else goto _L3
_L3:
        k = 120;
_L6:
        i = k * 60000;
_L2:
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.setAction((new StringBuilder()).append("com.facebook.katana.service.").append(nextRequestId()).toString());
        intent.putExtra("type", 201);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("extra_attempt", j);
        mPollingNotificationsAlarmIntent = PendingIntent.getService(context, 0, intent, 0);
        alarmmanager.set(0, System.currentTimeMillis() + (long)i, mPollingNotificationsAlarmIntent);
        return;
_L4:
        if(k == 0)
            k = 30;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private void scheduleUsersPollingAlarm(Context context, int i, int j)
    {
        if(i != -1 || !PlatformUtils.platformStorageSupported(context))
        {
            AlarmManager alarmmanager = (AlarmManager)context.getSystemService("alarm");
            if(mPollingUsersAlarmIntent != null)
            {
                alarmmanager.cancel(mPollingUsersAlarmIntent);
                mPollingUsersAlarmIntent.cancel();
            }
            Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
            intent.setAction((new StringBuilder()).append("com.facebook.katana.service.").append(nextRequestId()).toString());
            intent.putExtra("type", 202);
            intent.putExtra("sid", mSessionId);
            intent.putExtra("extra_attempt", j);
            mPollingUsersAlarmIntent = PendingIntent.getService(context, 0, intent, 0);
            if(i == -1)
                i = 0xa4cb800;
            alarmmanager.set(0, System.currentTimeMillis() + (long)i, mPollingUsersAlarmIntent);
        }
    }

    private String[] stringCollectionToSortedArray(Collection collection)
    {
        ArrayList arraylist = new ArrayList(collection);
        Collections.sort(arraylist);
        return (String[])arraylist.toArray(new String[0]);
    }

    private String syncFriends(Context context, int i)
    {
        String s1;
        if(isFriendsSyncPending())
        {
            s1 = null;
        } else
        {
            Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
            String s = nextRequestId();
            intent.putExtra("type", 80);
            intent.putExtra("rid", s);
            intent.putExtra("sid", mSessionId);
            intent.putExtra("session_key", mSessionInfo.sessionKey);
            intent.putExtra("session_user_id", mSessionInfo.userId);
            intent.putExtra("extra_attempt", i);
            intent.putExtra("un", mSessionInfo.username);
            postToService(context, s, intent);
            s1 = s;
        }
        return s1;
    }

    private void updateWidget(Context context, FacebookStatus facebookstatus, int i, Bitmap bitmap)
    {
        AppWidgetManager appwidgetmanager = AppWidgetManager.getInstance(context);
        ComponentName componentname = new ComponentName(context, com/facebook/katana/FacebookWidgetProvider);
        if(appwidgetmanager.getAppWidgetIds(componentname).length != 0)
        {
            RemoteViews remoteviews = new RemoteViews(context.getPackageName(), 0x7f030091);
            Intent intent = IntentUriHandler.getIntentForUri(context, "fb://feed");
            intent.setFlags(0x14000000);
            remoteviews.setOnClickPendingIntent(0x7f0e0162, PendingIntent.getActivity(context, 0, intent, 0x10000000));
            Intent intent1 = new Intent(context, com/facebook/katana/WidgetActivity);
            intent1.setAction("com.facebook.katana.widget.sharebutton");
            intent1.setFlags(0x10000000);
            remoteviews.setOnClickPendingIntent(0x7f0e0165, PendingIntent.getActivity(context, 0, intent1, 0x10000000));
            Intent intent2 = new Intent(context, com/facebook/katana/WidgetActivity);
            intent2.setAction("com.facebook.katana.widget.edit");
            intent2.setFlags(0x10000000);
            remoteviews.setOnClickPendingIntent(0x7f0e0164, PendingIntent.getActivity(context, 0, intent2, 0x10000000));
            String s = facebookstatus.getUser().mDisplayName;
            String s1 = facebookstatus.getMessage();
            SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder(s);
            if(s1 != null)
                spannablestringbuilder.append(" ").append(s1);
            if(mNameSpan == null)
            {
                Resources resources = context.getResources();
                mNameSpan = new TextAppearanceSpan(null, 1, (int)(14F * resources.getDisplayMetrics().density), ColorStateList.valueOf(resources.getColor(0x7f070007)), null);
            }
            spannablestringbuilder.setSpan(mNameSpan, 0, s.length(), 33);
            remoteviews.setTextViewText(0x7f0e0168, spannablestringbuilder);
            remoteviews.setTextViewText(0x7f0e0169, StringUtils.getTimeAsString(context, com.facebook.katana.util.StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE, 1000L * facebookstatus.getTime()));
            Intent intent3;
            Intent intent4;
            Intent intent5;
            if(bitmap != null)
                remoteviews.setImageViewBitmap(0x7f0e0167, bitmap);
            else
                remoteviews.setImageViewResource(0x7f0e0167, 0x7f0200f3);
            intent3 = ProfileTabHostActivity.intentForProfile(context, facebookstatus.getUser().mUserId);
            intent3.setFlags(0x14000000);
            remoteviews.setOnClickPendingIntent(0x7f0e0166, PendingIntent.getActivity(context, 0, intent3, 0x10000000));
            if(i == 0)
            {
                intent4 = null;
                remoteviews.setImageViewResource(0x7f0e016a, 0x7f0200d0);
            } else
            {
                intent4 = new Intent(context, com/facebook/katana/service/FacebookService);
                String s2 = (new StringBuilder()).append("com.facebook.katana.service.").append(nextRequestId()).toString();
                intent4.setAction(s2);
                intent4.putExtra("type", 91);
                String s3 = mActiveSessionId;
                intent4.putExtra("sid", s3);
                remoteviews.setImageViewResource(0x7f0e016a, 0x7f020136);
            }
            remoteviews.setOnClickPendingIntent(0x7f0e016a, PendingIntent.getService(context, 0, intent4, 0x10000000));
            if(i >= mStatusesList.size() - 1)
            {
                intent5 = null;
                remoteviews.setImageViewResource(0x7f0e016b, 0x7f0200c5);
            } else
            {
                intent5 = new Intent(context, com/facebook/katana/service/FacebookService);
                String s4 = (new StringBuilder()).append("com.facebook.katana.service.").append(nextRequestId()).toString();
                intent5.setAction(s4);
                intent5.putExtra("type", 90);
                String s5 = mActiveSessionId;
                intent5.putExtra("sid", s5);
                remoteviews.setImageViewResource(0x7f0e016b, 0x7f020135);
            }
            remoteviews.setOnClickPendingIntent(0x7f0e016b, PendingIntent.getService(context, 0, intent5, 0x10000000));
            appwidgetmanager.updateAppWidget(componentname, remoteviews);
        }
    }

    private String usersPollStatuses(Context context, int i)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s = nextRequestId();
        intent.putExtra("type", 81);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        intent.putExtra("extra_attempt", i);
        intent.putExtra("un", mSessionInfo.username);
        postToService(context, s, intent);
        return s;
    }

    public void addListener(AppSessionListener appsessionlistener)
    {
        mListeners.addListener(appsessionlistener);
    }

    public String authLogin(Context context, String s, String s1, boolean flag)
    {
        String s2 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        char c;
        if(flag)
            c = '\u012D';
        else
            c = '\001';
        intent.putExtra("type", c);
        intent.putExtra("rid", s2);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("un", s);
        intent.putExtra("pwd", s1);
        if(!flag)
            clearCookies(context);
        postToService(context, s2, intent);
        mLoginStatus = LoginStatus.STATUS_LOGGING_IN;
        return s2;
    }

    public String authLogout(Context context)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 2);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        if(mSessionInfo != null)
            intent.putExtra("session_key", mSessionInfo.sessionKey);
        mLoginStatus = LoginStatus.STATUS_LOGGING_OUT;
        postToService(context, s, intent);
        return s;
    }

    public void cancelServiceOp(Context context, String s)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 2000);
        intent.putExtra("rid", s);
        if(mPendingServiceRequestsMap.containsKey(s))
        {
            mPendingServiceRequestsMap.remove(s);
            context.startService(intent);
        }
    }

    public void cancelUploadNotification(Context context, String s)
    {
        ServiceNotificationManager.cancelUploadNotification(context, Integer.parseInt(s));
    }

    public String checkin(Context context, FacebookPlace facebookplace, Location location, String s, Set set, Long long1, String s1)
        throws JSONException
    {
        return postToService(context, new PlacesCheckin(context, null, mSessionInfo.sessionKey, null, facebookplace, location, s, set, long1, s1), 503, null);
    }

    public String downloadAlbumThumbail(Context context, long l, String s, String s1, String s2)
    {
        return downloadPhoto(context, 74, l, s, s1, s2);
    }

    public String downloadFullPhoto(Context context, String s, String s1, String s2)
    {
        return downloadPhoto(context, 76, -1L, s, s1, s2);
    }

    public String downloadPhotoRaw(Context context, String s)
    {
        return downloadPhoto(context, 77, -1L, null, null, s);
    }

    public String downloadPhotoThumbail(Context context, String s, String s1, String s2)
    {
        return downloadPhoto(context, 75, -1L, s, s1, s2);
    }

    public String eventGetMembers(Context context, long l)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 122);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("eid", l);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public String eventRsvp(Context context, long l, com.facebook.katana.model.FacebookEvent.RsvpStatus rsvpstatus)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 121);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("eid", l);
        intent.putExtra("rsvp_status", FacebookEvent.getRsvpStatusString(rsvpstatus));
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public String getConfig(Context context)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 400);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public String getEvents(Context context, long l)
    {
        return postToService(context, new FqlGetEvents(context, null, mSessionInfo.sessionKey, null, l), 120, null);
    }

    public String getFacebookAffiliationStatus(Context context, long l)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 140);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public String getFriendCheckins(Context context)
    {
        return postToService(context, new FqlGetFriendCheckins(context, null, mSessionInfo.sessionKey, null), 500, null);
    }

    public String getFriendRequests(Context context, long l)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 131);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public String getFriendRequestsMutualFriends(Context context, long l)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 133);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public Set getListeners()
    {
        return mListeners.getListeners();
    }

    public StreamPhotosCache getPhotosCache()
    {
        return mPhotosCache;
    }

    public String getPlacesNearby(Context context, Location location, double d, String s, int i, NetworkRequestCallback networkrequestcallback)
    {
        return postToService(context, new FqlGetPlacesNearby(context, null, mSessionInfo.sessionKey, null, location, d, s, i, networkrequestcallback), 501, null);
    }

    public FacebookSessionInfo getSessionInfo()
    {
        return mSessionInfo;
    }

    public LoginStatus getStatus()
    {
        return mLoginStatus;
    }

    public FacebookStreamContainer getStreamContainer(long l, FacebookStreamType facebookstreamtype)
    {
        class _cls3
        {

            static final int $SwitchMap$com$facebook$katana$model$FacebookStreamType[];

            static 
            {
                $SwitchMap$com$facebook$katana$model$FacebookStreamType = new int[FacebookStreamType.values().length];
                NoSuchFieldError nosuchfielderror4;
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookStreamType[FacebookStreamType.NEWSFEED_STREAM.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookStreamType[FacebookStreamType.PROFILE_WALL_STREAM.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookStreamType[FacebookStreamType.GROUP_WALL_STREAM.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookStreamType[FacebookStreamType.PAGE_WALL_STREAM.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                $SwitchMap$com$facebook$katana$model$FacebookStreamType[FacebookStreamType.PLACE_ACTIVITY_STREAM.ordinal()] = 5;
_L2:
                return;
                nosuchfielderror4;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls3..SwitchMap.com.facebook.katana.model.FacebookStreamType[facebookstreamtype.ordinal()];
        JVM INSTR tableswitch 1 5: default 44
    //                   1 50
    //                   2 59
    //                   3 59
    //                   4 59
    //                   5 80;
           goto _L1 _L2 _L3 _L3 _L3 _L4
_L1:
        FacebookStreamContainer facebookstreamcontainer = null;
_L6:
        return facebookstreamcontainer;
_L2:
        facebookstreamcontainer = mHomeStreamContainer;
        continue; /* Loop/switch isn't completed */
_L3:
        facebookstreamcontainer = (FacebookStreamContainer)mWallContainerMap.get(Long.valueOf(l));
        continue; /* Loop/switch isn't completed */
_L4:
        facebookstreamcontainer = (FacebookStreamContainer)mPlacesActivityContainerMap.get(Long.valueOf(l));
        if(true) goto _L6; else goto _L5
_L5:
    }

    public ProfileImagesCache getUserImagesCache()
    {
        return mUserImageCache;
    }

    public void handlePasswordEntry(Context context, String s)
    {
        authLogin(context, mSessionInfo.username, s, true);
    }

    protected void initializeSettingsState()
    {
        Gatekeeper.reset();
        mUserServerSettings = new HashMap();
        mUserServerSettingsSync = new HashMap();
    }

    public boolean isAlbumGetThumbnailsPending(String s)
    {
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Intent intent = (Intent)iterator.next();
        if(intent.getIntExtra("type", -1) != 75 || !s.equals(intent.getStringExtra("aid"))) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean isAlbumsGetPending(long l)
    {
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Intent intent = (Intent)iterator.next();
        if(intent.getIntExtra("type", -1) != 60 || intent.getLongExtra("uid", -1L) != l) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean isAlbumsGetPending(long l, String s)
    {
        boolean flag = false;
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Intent intent = (Intent)iterator.next();
            if(intent.getIntExtra("type", -1) == 60 && intent.getLongExtra("uid", -1L) == l)
            {
                String s1 = intent.getStringExtra("aid");
                if(s1 != null && s.equals(s1))
                    flag = true;
            }
        } while(true);
        return flag;
    }

    public boolean isFriendsSyncPending()
    {
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        if(((Intent)iterator.next()).getIntExtra("type", -1) != 80) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean isMailboxGetMessagesPending(int i, long l)
    {
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Intent intent = (Intent)iterator.next();
        if(intent.getIntExtra("type", -1) != 115 || intent.getIntExtra("folder", 0) != i || intent.getLongExtra("tid", -1L) != l) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean isMailboxSyncPending(int i)
    {
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Intent intent = (Intent)iterator.next();
        if(intent.getIntExtra("type", -1) != 110 || intent.getIntExtra("folder", 0) != i) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean isNotificationsSyncPending()
    {
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        if(((Intent)iterator.next()).getIntExtra("type", -1) != 51) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean isPhotoGetCommentPending(String s)
    {
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Intent intent = (Intent)iterator.next();
        if(intent.getIntExtra("type", -1) != 70 || !s.equals(intent.getStringExtra("pid"))) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean isPhotosGetPending(String s, long l)
    {
        boolean flag = false;
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Intent intent = (Intent)iterator.next();
            if(intent.getIntExtra("type", -1) == 64 && s.equals(intent.getStringExtra("aid")) && l == intent.getLongExtra("uid", -1L))
                flag = true;
        } while(true);
        return flag;
    }

    public boolean isPhotosGetPending(Collection collection, long l)
    {
        boolean flag = false;
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Intent intent = (Intent)iterator.next();
            if(intent.getIntExtra("type", -1) == 64 && l == intent.getLongExtra("uid", -1L) && Arrays.equals(stringCollectionToSortedArray(collection), intent.getStringArrayExtra("pid")))
                flag = true;
        } while(true);
        return flag;
    }

    public boolean isRequestPending(int i)
    {
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
_L2:
        boolean flag;
        Intent intent;
        int j;
        if(iterator.hasNext())
        {
            intent = (Intent)iterator.next();
            j = intent.getIntExtra("type", -1);
            if(j != i)
                continue; /* Loop/switch isn't completed */
            flag = true;
        } else
        {
            flag = false;
        }
_L3:
        return flag;
        if(j != 1001 || intent.getIntExtra("extended_type", -1) != i) goto _L2; else goto _L1
_L1:
        flag = true;
          goto _L3
    }

    public boolean isRequestPending(String s)
    {
        return mPendingServiceRequestsMap.containsKey(s);
    }

    public boolean isStreamGetCommentsPending(long l, String s)
    {
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Intent intent = (Intent)iterator.next();
        if(intent.getIntExtra("type", -1) != 31 || intent.getLongExtra("uid", -1L) != l || !intent.getStringExtra("post_id").equals(s)) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean isStreamGetPending(long l, FacebookStreamType facebookstreamtype)
    {
        Iterator iterator = mPendingServiceRequestsMap.values().iterator();
_L2:
        boolean flag;
        Intent intent;
        int i;
        long al[];
        do
        {
            if(!iterator.hasNext())
                break MISSING_BLOCK_LABEL_183;
            intent = (Intent)iterator.next();
            i = intent.getIntExtra("type", -1);
            if(i != 30)
                continue; /* Loop/switch isn't completed */
            al = intent.getLongArrayExtra("subject");
            if(al != null)
                continue; /* Loop/switch isn't completed */
        } while(l != mSessionInfo.userId || facebookstreamtype != FacebookStreamType.NEWSFEED_STREAM);
        flag = true;
_L3:
        return flag;
        if(al[0] != l || facebookstreamtype != FacebookStreamType.PROFILE_WALL_STREAM && facebookstreamtype != FacebookStreamType.PAGE_WALL_STREAM) goto _L2; else goto _L1
_L1:
        flag = true;
          goto _L3
        if(i != 1000 && i != 1001 || intent.getIntExtra("extended_type", -1) != 502 || intent.getLongExtra("subject", -1L) != l || facebookstreamtype != FacebookStreamType.PLACE_ACTIVITY_STREAM) goto _L2; else goto _L4
_L4:
        flag = true;
          goto _L3
        flag = false;
          goto _L3
    }

    public String mailboxDeleteThread(Context context, long al[], int i)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 114);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("tid", al);
        intent.putExtra("folder", i);
        postToService(context, s, intent);
        return s;
    }

    public String mailboxGetThreadMessages(Context context, int i, long l, boolean flag)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 115);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("folder", i);
        intent.putExtra("tid", l);
        intent.putExtra("read", flag);
        postToService(context, s, intent);
        return s;
    }

    public String mailboxMarkThread(Context context, int i, long al[], boolean flag)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 113);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("tid", al);
        intent.putExtra("folder", i);
        intent.putExtra("read", flag);
        postToService(context, s, intent);
        return s;
    }

    public String mailboxReply(Context context, long l, String s)
    {
        String s1 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 112);
        intent.putExtra("rid", s1);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("tid", l);
        intent.putExtra("status", s);
        intent.putExtra("profile_uid", mSessionInfo.getProfile().mUserId);
        intent.putExtra("profile_first_name", mSessionInfo.getProfile().mFirstName);
        intent.putExtra("profile_last_name", mSessionInfo.getProfile().mLastName);
        intent.putExtra("profile_display_name", mSessionInfo.getProfile().mDisplayName);
        intent.putExtra("profile_url", mSessionInfo.getProfile().mImageUrl);
        postToService(context, s1, intent);
        return s1;
    }

    public String mailboxSend(Context context, List list, String s, String s1)
    {
        ArrayList arraylist = new ArrayList(list);
        String s2 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 111);
        intent.putExtra("rid", s2);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putParcelableArrayListExtra("uid", arraylist);
        intent.putExtra("subject", s);
        intent.putExtra("status", s1);
        intent.putExtra("profile_uid", mSessionInfo.getProfile().mUserId);
        intent.putExtra("profile_first_name", mSessionInfo.getProfile().mFirstName);
        intent.putExtra("profile_last_name", mSessionInfo.getProfile().mLastName);
        intent.putExtra("profile_display_name", mSessionInfo.getProfile().mDisplayName);
        intent.putExtra("profile_url", mSessionInfo.getProfile().mImageUrl);
        postToService(context, s2, intent);
        return s2;
    }

    public String mailboxSync(Context context, int i, int j, int k, boolean flag)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 110);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("folder", i);
        intent.putExtra("start", j);
        intent.putExtra("limit", k);
        intent.putExtra("sync", flag);
        intent.putExtra("uid", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public String notificationsMarkAsRead(Context context, long al[])
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s = nextRequestId();
        intent.putExtra("type", 52);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("item_id", al);
        postToService(context, s, intent);
        return s;
    }

    public void openMediaItem(Context context, com.facebook.katana.model.FacebookPost.Attachment.MediaItem mediaitem)
    {
        boolean flag = false;
        if(!mediaitem.type.equals("video")) goto _L2; else goto _L1
_L1:
        FacebookVideo facebookvideo = mediaitem.getVideo();
        if(facebookvideo != null)
        {
            if(facebookvideo.getSourceType() == com.facebook.katana.model.FacebookVideo.VideoSource.SOURCE_RAW && facebookvideo.getSourceUrl() != null)
            {
                Intent intent1 = new Intent("android.intent.action.VIEW");
                intent1.setDataAndType(Uri.parse(facebookvideo.getSourceUrl()), "video/*");
                if(context.getPackageManager().queryIntentActivities(intent1, 0).size() > 0)
                {
                    context.startActivity(intent1);
                    flag = true;
                }
            }
            if(!flag && facebookvideo.getDisplayUrl() != null)
            {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(facebookvideo.getDisplayUrl()));
                if(context.getPackageManager().queryIntentActivities(intent, 0).size() > 0)
                {
                    context.startActivity(intent);
                    flag = true;
                }
            }
        }
_L4:
        if(!flag)
            openURL(context, mediaitem.href);
        return;
_L2:
        if(mediaitem.type.equals("photo"))
        {
            FacebookPhoto facebookphoto = mediaitem.getPhoto();
            if(facebookphoto != null)
            {
                String s = facebookphoto.getAlbumId();
                String s1 = facebookphoto.getPhotoId();
                if(s1 != null || s != null)
                {
                    context.startActivity(ViewPhotoActivity.photoIntent(context, facebookphoto.getOwnerId(), s, s1, "android.intent.action.VIEW"));
                    flag = true;
                }
            }
            if(!flag)
            {
                openURL(context, mediaitem.href.replaceFirst("www.", "m."));
                flag = true;
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public String openURL(Context context, String s)
    {
        if(s.startsWith("/"))
            s = (new StringBuilder()).append("http://www.facebook.com").append(s).toString();
        Uri uri = Uri.parse(s);
        String s1;
        if(uri.getHost().toLowerCase().endsWith(".facebook.com"))
        {
            long l = System.currentTimeMillis() / 1000L;
            long l1 = getSessionInfo().userId;
            String s2 = mSessionInfo.sessionKey;
            String s3 = mSessionInfo.sessionSecret;
            AuthDeepLinkMethod authdeeplinkmethod = new AuthDeepLinkMethod(context, l, l1, s, null, s2, s3);
            authdeeplinkmethod.start();
            s1 = authdeeplinkmethod.getUrl();
            uri = Uri.parse(s1);
        } else
        {
            s1 = s;
        }
        if(context != null)
            context.startActivity(new Intent("android.intent.action.VIEW", uri));
        return s1;
    }

    public String photoAddComment(Context context, String s, String s1)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s2 = nextRequestId();
        intent.putExtra("type", 71);
        intent.putExtra("rid", s2);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("pid", s);
        intent.putExtra("status", s1);
        intent.putExtra("uid", mSessionInfo.userId);
        postToService(context, s2, intent);
        return s2;
    }

    public String photoAddTags(Context context, String s, String s1)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s2 = nextRequestId();
        intent.putExtra("type", 68);
        intent.putExtra("rid", s2);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("pid", s);
        intent.putExtra("tags", s1);
        postToService(context, s2, intent);
        return s2;
    }

    public String photoAddTags(Context context, String s, List list)
    {
        return photoAddTags(context, s, FacebookPhotoTag.encode(list));
    }

    public String photoCreateAlbum(Context context, String s, String s1, String s2, String s3)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s4 = nextRequestId();
        intent.putExtra("type", 61);
        intent.putExtra("rid", s4);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("name", s);
        if(s1 != null)
            intent.putExtra("location", s1);
        if(s2 != null)
            intent.putExtra("description", s2);
        if(s3 != null)
            intent.putExtra("visibility", s3);
        postToService(context, s4, intent);
        return s4;
    }

    public String photoDeleteAlbum(Context context, String s)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s1 = nextRequestId();
        intent.putExtra("type", 63);
        intent.putExtra("rid", s1);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("uid", mSessionInfo.userId);
        intent.putExtra("aid", s);
        postToService(context, s1, intent);
        return s1;
    }

    public String photoDeletePhoto(Context context, String s, String s1)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s2 = nextRequestId();
        intent.putExtra("type", 67);
        intent.putExtra("rid", s2);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("aid", s);
        intent.putExtra("pid", s1);
        postToService(context, s2, intent);
        return s2;
    }

    public String photoEditAlbum(Context context, String s, String s1, String s2, String s3, String s4)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s5 = nextRequestId();
        intent.putExtra("type", 62);
        intent.putExtra("rid", s5);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("aid", s);
        intent.putExtra("name", s1);
        if(s2 != null)
            intent.putExtra("location", s2);
        if(s3 != null)
            intent.putExtra("description", s3);
        if(s4 != null)
            intent.putExtra("visibility", s4);
        postToService(context, s5, intent);
        return s5;
    }

    public String photoEditPhoto(Context context, String s, String s1, String s2)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s3 = nextRequestId();
        intent.putExtra("type", 66);
        intent.putExtra("rid", s3);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("aid", s);
        intent.putExtra("pid", s1);
        if(s2 != null)
            intent.putExtra("caption", s2);
        postToService(context, s3, intent);
        return s3;
    }

    public String photoGetAlbums(Context context, long l, List list)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s = nextRequestId();
        intent.putExtra("type", 60);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        if(list != null)
            intent.putExtra("aid", stringCollectionToSortedArray(list));
        else
            intent.putExtra("uid", l);
        postToService(context, s, intent);
        return s;
    }

    public String photoGetComments(Context context, String s)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s1 = nextRequestId();
        intent.putExtra("type", 70);
        intent.putExtra("rid", s1);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("pid", s);
        postToService(context, s1, intent);
        return s1;
    }

    public String photoGetPhotos(Context context, String s, Collection collection, long l)
    {
        return photoGetPhotos(context, s, collection, l, 0, -1);
    }

    public String photoGetPhotos(Context context, String s, Collection collection, long l, int i, int j)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s1 = nextRequestId();
        intent.putExtra("type", 64);
        intent.putExtra("rid", s1);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("aid", s);
        intent.putExtra("start", i);
        intent.putExtra("limit", j);
        if(collection != null)
            intent.putExtra("pid", stringCollectionToSortedArray(collection));
        intent.putExtra("uid", l);
        postToService(context, s1, intent);
        return s1;
    }

    public String photoGetTags(Context context, List list)
    {
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s = nextRequestId();
        intent.putExtra("type", 69);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("pid", listToCommaString(list, true));
        postToService(context, s, intent);
        return s;
    }

    public String photoUpload(Context context, int i, String s, String s1, String s2, long l, 
            long l1, boolean flag, long l2, String s3, long l3, String s4, long l4)
    {
        String s5 = nextRequestId();
        ServiceNotificationManager.beginPhotoUploadProgressNotification(context, Integer.parseInt(s5), s, s1, s2);
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 65);
        intent.putExtra("rid", s5);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("upload_id", i);
        if(s != null)
            intent.putExtra("aid", s);
        if(s1 != null)
            intent.putExtra("caption", s1);
        intent.putExtra("profile_id", l);
        intent.putExtra("checkin_id", l1);
        intent.putExtra("uri", s2);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        intent.putExtra("extra_photo_publish", flag);
        intent.putExtra("extra_place", l2);
        intent.putExtra("tags", s3);
        intent.putExtra("extra_status_target_id", l3);
        intent.putExtra("extra_status_privacy", s4);
        intent.putExtra("upload_manager_extras_local_id", l4);
        postToService(context, s5, intent);
        return s5;
    }

    public String postToService(Context context, ApiMethod apimethod, int i, int j, Bundle bundle)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", i);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("extended_type", j);
        if(bundle != null)
            intent.putExtras(bundle);
        FacebookSessionInfo facebooksessioninfo = getSessionInfo();
        if(facebooksessioninfo != null)
        {
            String s1 = facebooksessioninfo.sessionSecret;
            if(s1 != null)
                intent.putExtra("ApiMethod.secret", s1);
        }
        mPendingServiceRequestsMap.put(s, intent);
        FacebookService.apiMethodReceiver.put(s, apimethod);
        if(LoginStatus.STATUS_LOGGING_IN != mLoginStatus || isLoginRequest(intent))
            context.startService(intent);
        else
            mRequestsToHandleAfterLogin.add(intent);
        return s;
    }

    protected String postToService(Context context, ApiMethod apimethod, int i, Bundle bundle)
    {
        return postToService(context, apimethod, 1000, i, bundle);
    }

    public String registerForPush(Context context, String s)
    {
        return postToService(context, new UserRegisterPushCallback(context, null, mSessionInfo.sessionKey, null, s), 700, null);
    }

    public void releaseStreamContainers()
    {
        FacebookStreamContainer facebookstreamcontainer1;
        for(Iterator iterator = mWallContainerMap.values().iterator(); iterator.hasNext(); FacebookStreamContainer.deregister(facebookstreamcontainer1))
        {
            facebookstreamcontainer1 = (FacebookStreamContainer)iterator.next();
            facebookstreamcontainer1.clear();
        }

        FacebookStreamContainer facebookstreamcontainer;
        for(Iterator iterator1 = mPlacesActivityContainerMap.values().iterator(); iterator1.hasNext(); FacebookStreamContainer.deregister(facebookstreamcontainer))
        {
            facebookstreamcontainer = (FacebookStreamContainer)iterator1.next();
            facebookstreamcontainer.clear();
        }

        mWallContainerMap.clear();
        mPlacesActivityContainerMap.clear();
    }

    public void removeListener(AppSessionListener appsessionlistener)
    {
        mListeners.removeListener(appsessionlistener);
    }

    public String respondToFriendRequest(Context context, long l, boolean flag)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 132);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("confirm", flag);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public void scheduleStatusPollingAlarm(Context context, int i, int j)
    {
        AlarmManager alarmmanager;
        alarmmanager = (AlarmManager)context.getSystemService("alarm");
        if(mPollingStatusAlarmIntent != null)
        {
            alarmmanager.cancel(mPollingStatusAlarmIntent);
            mPollingStatusAlarmIntent.cancel();
        }
        if(i != -1) goto _L2; else goto _L1
_L1:
        int k = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString("polling_interval", "60"));
        if(k <= 120) goto _L4; else goto _L3
_L3:
        k = 120;
_L6:
        i = k * 60000;
_L2:
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.setAction((new StringBuilder()).append("com.facebook.katana.service.").append(nextRequestId()).toString());
        intent.putExtra("type", 203);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("extra_attempt", j);
        mPollingStatusAlarmIntent = PendingIntent.getService(context, 0, intent, 0);
        alarmmanager.set(0, System.currentTimeMillis() + (long)i, mPollingStatusAlarmIntent);
        return;
_L4:
        if(k == 0)
            k = 30;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void setIsEmployee(Context context, boolean flag)
    {
        FacebookAffiliation.setIsEmployee(context, flag);
    }

    public void settingsChanged(Context context)
    {
        scheduleNotificationsPollingAlarm(context, -1, 0);
        scheduleUsersPollingAlarm(context, -1, 0);
        scheduleStatusPollingAlarm(context, -1, 0);
    }

    public String streamAddComment(Context context, long l, String s, String s1, FacebookProfile facebookprofile)
    {
        String s2 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 33);
        intent.putExtra("rid", s2);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("post_id", s);
        intent.putExtra("status", s1);
        if(facebookprofile != null)
            intent.putExtra("actor", facebookprofile);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s2, intent);
        return s2;
    }

    public String streamAddLike(Context context, long l, String s)
    {
        String s1 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 36);
        intent.putExtra("rid", s1);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("post_id", s);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s1, intent);
        return s1;
    }

    public String streamGet(Context context, long l, long al[], long l1, long l2, int i, int j, FacebookStreamType facebookstreamtype)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 30);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("stream_type", facebookstreamtype.toString());
        if(al != null)
            intent.putExtra("subject", al);
        else
            intent.putExtra("session_filter_key", mSessionInfo.getFilterKey());
        if(l1 > 0L)
            intent.putExtra("start", l1);
        if(l2 > 0L)
            intent.putExtra("end", l2);
        if(i > 0)
            intent.putExtra("limit", i);
        intent.putExtra("app_value", j);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        postToService(context, s, intent);
        return s;
    }

    public String streamGetComments(Context context, long l, String s)
    {
        String s1 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 31);
        intent.putExtra("rid", s1);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("post_id", s);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        postToService(context, s1, intent);
        return s1;
    }

    public String streamRemoveComment(Context context, long l, String s, String s1)
    {
        String s2 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 35);
        intent.putExtra("rid", s2);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("post_id", s);
        intent.putExtra("item_id", s1);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        postToService(context, s2, intent);
        return s2;
    }

    public String streamRemoveLike(Context context, long l, String s)
    {
        String s1 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 37);
        intent.putExtra("rid", s1);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("post_id", s);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        postToService(context, s1, intent);
        return s1;
    }

    public String streamRemovePost(Context context, long l, String s)
    {
        String s1 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 34);
        intent.putExtra("rid", s1);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("post_id", s);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        postToService(context, s1, intent);
        return s1;
    }

    public String syncFriends(Context context)
    {
        return syncFriends(context, 3);
    }

    public String syncNotifications(Context context)
    {
        pollNotifications(context, 0);
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        String s = nextRequestId();
        intent.putExtra("type", 51);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public String unregisterForPush(Context context)
    {
        return postToService(context, new UserUnregisterPushCallback(context, null, mSessionInfo.sessionKey, null), 701, null);
    }

    public void updateSessionInfo(Context context, FacebookSessionInfo facebooksessioninfo)
    {
        mSessionInfo = facebooksessioninfo;
        UserValuesManager.saveActiveSessionInfo(context, mSessionInfo.toJSONObject().toString());
    }

    public String usersGetBriefInfo(Context context, long l)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 83);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public String usersGetInfo(Context context, long l)
    {
        String s = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 82);
        intent.putExtra("rid", s);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("uid", l);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("session_user_id", mSessionInfo.userId);
        postToService(context, s, intent);
        return s;
    }

    public String usersSearch(Context context, String s, int i, int j)
    {
        String s1 = nextRequestId();
        Intent intent = new Intent(context, com/facebook/katana/service/FacebookService);
        intent.putExtra("type", 211);
        intent.putExtra("rid", s1);
        intent.putExtra("sid", mSessionId);
        intent.putExtra("session_key", mSessionInfo.sessionKey);
        intent.putExtra("subject", s);
        intent.putExtra("start", i);
        intent.putExtra("limit", j);
        postToService(context, s1, intent);
        return s1;
    }

    public void widgetUpdate(Context context)
    {
        if(mStatusesList.size() > 0)
            initiateWidgetUpdate(context);
    }

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
    private static boolean SIMULATE_102 = false;
    private static final String TAG = "fbandroid";
    public static final String VISIBILITY_CUSTOM = "custom";
    public static final String VISIBILITY_EVERYONE = "everyone";
    public static final String VISIBILITY_FRIENDS = "friends";
    public static final String VISIBILITY_FRIENDS_OF_FRIENDS = "friends-of-friends";
    public static final String VISIBILITY_NETWORKS = "networks";
    public static boolean fixed102 = false;
    private static boolean got102 = false;
    private static String mActiveSessionId;
    private static TextAppearanceSpan mNameSpan;
    private static final Map mSessionMap = new HashMap();
    private static int mUniqueId;
    private int mCurrentStatusIndex;
    FacebookStreamContainer mHomeStreamContainer;
    private long mLatestPostTime;
    final ReentrantCallback mListeners = new ReentrantCallback();
    private LoginStatus mLoginStatus;
    private final Map mPendingServiceRequestsMap = new HashMap();
    private final StreamPhotosCache mPhotosCache = new StreamPhotosCache(new StreamPhotosCache.PhotosContainerListener() {

        public void onPhotoDecoded(Bitmap bitmap, String s)
        {
            for(Iterator iterator = mListeners.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onPhotoDecodeComplete(AppSession.this, bitmap, s));
        }

        public void onPhotoRequested(Context context, String s, int i)
        {
            i;
            JVM INSTR tableswitch 1 2: default 24
        //                       1 25
        //                       2 45;
               goto _L1 _L2 _L3
_L1:
            return;
_L2:
            downloadPhoto(context, 73, -1L, null, null, s);
            continue; /* Loop/switch isn't completed */
_L3:
            downloadPhoto(context, 72, -1L, null, null, s);
            if(true) goto _L1; else goto _L4
_L4:
        }

        final AppSession this$0;

            
            {
                this$0 = AppSession.this;
                super();
            }
    }
);
    public final Map mPlacesActivityContainerMap = new HashMap();
    private PendingIntent mPollingNotificationsAlarmIntent;
    private PendingIntent mPollingStatusAlarmIntent;
    private PendingIntent mPollingStreamAlarmIntent;
    private PendingIntent mPollingUsersAlarmIntent;
    private final Collection mRequestsToHandleAfterLogin = new HashSet();
    private final String mSessionId = (new StringBuilder()).append("").append(nextRequestId()).toString();
    private FacebookSessionInfo mSessionInfo;
    private final List mStatusesList = new ArrayList();
    private final ProfileImagesCache mUserImageCache = new ProfileImagesCache(new ProfileImagesCache.ProfileImagesContainerListener() {

        public void onProfileImageDownload(Context context, long l, String s)
        {
            downloadUserImage(context, l, s);
        }

        public void onProfileImageLoaded(Context context, ProfileImage profileimage)
        {
            if(mCurrentStatusIndex < mStatusesList.size() && ((FacebookStatus)mStatusesList.get(mCurrentStatusIndex)).getUser().mUserId == profileimage.id)
                updateWidget(context, (FacebookStatus)mStatusesList.get(mCurrentStatusIndex), mCurrentStatusIndex, profileimage.getBitmap());
            for(Iterator iterator = mListeners.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onProfileImageLoaded(AppSession.this, profileimage));
        }

        final AppSession this$0;

            
            {
                this$0 = AppSession.this;
                super();
            }
    }
);
    public Map mUserServerSettings;
    public Map mUserServerSettingsSync;
    private android.os.PowerManager.WakeLock mWakeLock;
    final Map mWallContainerMap = new HashMap();
    private WorkerThread mWorkerThread;
    private boolean sessionInvalidHandled;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/binding/AppSession.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
        SIMULATE_102 = false;
    }





}
