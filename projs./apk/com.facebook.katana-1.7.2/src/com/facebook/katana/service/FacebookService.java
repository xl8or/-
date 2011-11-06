// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookService.java

package com.facebook.katana.service;

import android.app.Activity;
import android.app.Service;
import android.content.*;
import android.os.IBinder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ChatSession;
import com.facebook.katana.model.*;
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
import java.util.*;
import org.json.JSONObject;

public class FacebookService extends Service
    implements ApiMethodListener
{

    public FacebookService()
    {
    }

    private void addToBestPlaceInDownloadQueue(ApiMethod apimethod)
    {
        if(apimethod instanceof ProfileImageDownload)
        {
            mLowPriorityDownloadQueue.add(apimethod);
        } else
        {
            long l = System.currentTimeMillis();
            if(l > 100L + mLastBatchTime)
            {
                mMediumPriorityDownloadQueue.addAll(mHighPriorityDownloadQueue);
                mHighPriorityDownloadQueue.clear();
            }
            mHighPriorityDownloadQueue.add(apimethod);
            mLastBatchTime = l;
        }
    }

    private void downloadNext(ApiMethod apimethod)
    {
        boolean flag;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        if(true && mHighPriorityDownloadQueue.remove(apimethod))
            flag = true;
        else
            flag = false;
        if(!flag && mMediumPriorityDownloadQueue.remove(apimethod))
            flag1 = true;
        else
            flag1 = false;
        if(flag1 || !mLowPriorityDownloadQueue.remove(apimethod));
        if(true && startImageDownloadFromQueue(mHighPriorityDownloadQueue))
            flag2 = true;
        else
            flag2 = false;
        if(!flag2 && startImageDownloadFromQueue(mMediumPriorityDownloadQueue))
            flag3 = true;
        else
            flag3 = false;
        if(flag3 || !startImageDownloadFromQueue(mLowPriorityDownloadQueue));
    }

    private String getRequestId(ApiMethod apimethod)
    {
        return apimethod.getIntent().getStringExtra("rid");
    }

    public static void pause(boolean flag, boolean flag1, Activity activity)
    {
        if(!flag && flag1 && (nextIntent == null || processActivityChange(nextIntent)))
        {
            isBackgroundMode = true;
            ApiLogging.flush(activity);
            ChatSession.shutdown(true);
        }
        if(!flag)
            nextIntent = null;
    }

    public static boolean processActivityChange(Intent intent)
    {
        nextIntent = intent;
        ComponentName componentname = intent.getComponent();
        boolean flag;
        if(componentname == null)
            flag = true;
        else
        if(componentname.getPackageName().equals("com.facebook.katana"))
            flag = false;
        else
            flag = true;
        return flag;
    }

    public static boolean resume(boolean flag, Activity activity)
    {
        nextIntent = null;
        boolean flag1;
        if(flag)
        {
            flag1 = false;
        } else
        {
            if(isBackgroundMode)
            {
                isBackgroundMode = false;
                ApiLogging.load(activity);
                ChatSession chatsession = ChatSession.getActiveChatSession(activity);
                if(chatsession != null)
                    chatsession.connect(true, null);
            }
            flag1 = true;
        }
        return flag1;
    }

    private boolean startImageDownloadFromQueue(List list)
    {
        if(list.size() <= 0) goto _L2; else goto _L1
_L1:
        Iterator iterator = list.iterator();
_L5:
        if(!iterator.hasNext()) goto _L2; else goto _L3
_L3:
        ApiMethod apimethod;
        String s;
        apimethod = (ApiMethod)iterator.next();
        s = getRequestId(apimethod);
        if(mPendingOps.containsKey(s)) goto _L5; else goto _L4
_L4:
        boolean flag;
        startOp(s, apimethod);
        flag = true;
_L7:
        return flag;
_L2:
        flag = false;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void startOp(String s, ApiMethod apimethod)
    {
        mPendingOps.put(s, apimethod);
        apimethod.start();
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public void onDestroy()
    {
    }

    public void onOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
    {
        Intent intent;
        intent = apimethod.getIntent();
        String s1 = intent.getStringExtra("rid");
        mPendingOps.remove(s1);
        intent.getIntExtra("type", 0);
        JVM INSTR lookupswitch 34: default 316
    //                   1: 332
    //                   3: 539
    //                   30: 700
    //                   31: 738
    //                   33: 776
    //                   50: 1665
    //                   61: 800
    //                   65: 832
    //                   67: 949
    //                   68: 976
    //                   69: 1000
    //                   70: 1024
    //                   71: 1059
    //                   72: 1116
    //                   73: 1116
    //                   74: 1145
    //                   75: 1168
    //                   76: 1145
    //                   77: 1087
    //                   80: 1565
    //                   81: 1641
    //                   82: 1220
    //                   83: 1450
    //                   100: 1191
    //                   121: 1703
    //                   122: 1759
    //                   131: 1812
    //                   132: 1854
    //                   133: 1930
    //                   140: 1972
    //                   211: 1603
    //                   301: 332
    //                   1000: 1993
    //                   1001: 1993;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L15 _L15 _L16 _L17 _L16 _L18 _L19 _L20 _L21 _L22 _L23 _L24 _L25 _L26 _L27 _L28 _L29 _L30 _L2 _L31 _L31
_L1:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, null, null);
_L36:
        return;
_L2:
        if(i == 200)
        {
            String s3 = intent.getStringExtra("rid");
            FacebookSessionInfo facebooksessioninfo1 = ((AuthLogin)apimethod).getSessionInfo();
            ArrayList arraylist = new ArrayList();
            Intent intent2 = new Intent();
            intent2.putExtra("type", 3);
            intent2.putExtra("FacebookService.originalIntent", intent);
            intent2.putExtra("ApiMethod.secret", facebooksessioninfo1.sessionSecret);
            HashMap hashmap = new HashMap();
            hashmap.put(Long.valueOf(facebooksessioninfo1.userId), null);
            arraylist.add(new FqlGetUsersProfile(mContext, intent2, facebooksessioninfo1.sessionKey, null, hashmap, facebooksessioninfo1));
            arraylist.add(new StreamGetFilters(mContext, intent2, facebooksessioninfo1.sessionKey, null));
            startOp(s3, new BatchRun(mContext, intent2, facebooksessioninfo1.sessionKey, arraylist, this));
        } else
        {
            AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, null, null);
        }
        continue; /* Loop/switch isn't completed */
_L3:
        FacebookSessionInfo facebooksessioninfo = null;
        if(i == 200)
        {
            FqlGetUsersProfile fqlgetusersprofile4 = (FqlGetUsersProfile)((BatchRun)apimethod).getMethods().get(0);
            facebooksessioninfo = (FacebookSessionInfo)fqlgetusersprofile4.getOpaque();
            if(facebooksessioninfo == null)
                facebooksessioninfo = AppSession.getActiveSession(mContext, false).getSessionInfo();
            facebooksessioninfo.setProfile((FacebookUser)fqlgetusersprofile4.getUsers().get(Long.valueOf(facebooksessioninfo.userId)));
            facebooksessioninfo.setFilterKey(((StreamGetFilters)((BatchRun)apimethod).getMethods().get(1)).getNewsFeedFilter());
            String s2 = facebooksessioninfo.toJSONObject().toString();
            UserValuesManager.saveActiveSessionInfo(mContext, s2);
        }
        Intent intent1 = (Intent)intent.getParcelableExtra("FacebookService.originalIntent");
        if(intent1 != null)
            AppSession.onServiceOperationComplete(mContext, intent1, i, s, exception, facebooksessioninfo, null);
        continue; /* Loop/switch isn't completed */
_L4:
        List list1 = null;
        if(i == 200)
            list1 = ((FqlGetStream)apimethod).getPosts();
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, list1, null);
        continue; /* Loop/switch isn't completed */
_L5:
        List list = null;
        if(i == 200)
            list = ((StreamGetComments)apimethod).getComments();
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, list, null);
        continue; /* Loop/switch isn't completed */
_L6:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, ((StreamAddComment)apimethod).getCommentId(), null);
        continue; /* Loop/switch isn't completed */
_L8:
        PhotosCreateAlbum photoscreatealbum = (PhotosCreateAlbum)apimethod;
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, photoscreatealbum.getAlbum(), photoscreatealbum.getAlbumUri());
        continue; /* Loop/switch isn't completed */
_L9:
        long l3 = intent.getLongExtra("session_user_id", -1L);
        if(i != 200) goto _L33; else goto _L32
_L32:
        FacebookPhoto facebookphoto = ((PhotosUpload)apimethod).getPhoto();
_L34:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, facebookphoto, null);
        continue; /* Loop/switch isn't completed */
_L33:
        long l4 = intent.getLongExtra("checkin_id", -1L);
        facebookphoto = null;
        if(l4 == -1L)
            facebookphoto = new FacebookPhoto(null, intent.getStringExtra("aid"), l3, intent.getStringExtra("subject"), null, null, null, 0L, null, -1L, -1L);
        if(true) goto _L34; else goto _L10
_L10:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, Boolean.valueOf(((PhotosDeletePhoto)apimethod).hasAlbumCoverChanged()), null);
        continue; /* Loop/switch isn't completed */
_L11:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, ((PhotosAddTag)apimethod).getTags(), null);
        continue; /* Loop/switch isn't completed */
_L12:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, ((PhotosGetTags)apimethod).getTags(), null);
        continue; /* Loop/switch isn't completed */
_L13:
        SyncPhotoComments syncphotocomments = (SyncPhotoComments)apimethod;
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, syncphotocomments.getComments(), Boolean.valueOf(syncphotocomments.getCanComment()));
        continue; /* Loop/switch isn't completed */
_L14:
        PhotosAddComment photosaddcomment = (PhotosAddComment)apimethod;
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, photosaddcomment.getComment(), null);
        continue; /* Loop/switch isn't completed */
_L18:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, ((PhotoDownload)apimethod).getBitmap(), null);
        downloadNext(apimethod);
        continue; /* Loop/switch isn't completed */
_L15:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, ((PhotoDownload)apimethod).getPhoto(), null);
        downloadNext(apimethod);
        continue; /* Loop/switch isn't completed */
_L16:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, null, null);
        downloadNext(apimethod);
        continue; /* Loop/switch isn't completed */
_L17:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, null, null);
        downloadNext(apimethod);
        continue; /* Loop/switch isn't completed */
_L23:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, ((ProfileImageDownload)apimethod).getProfileImage(), null);
        downloadNext(apimethod);
        continue; /* Loop/switch isn't completed */
_L21:
        FqlMultiQuery fqlmultiquery1 = (FqlMultiQuery)apimethod;
        FacebookUserFull facebookuserfull = null;
        boolean flag2 = true;
        if(i == 200)
        {
            FqlGetUsersProfile fqlgetusersprofile2 = (FqlGetUsersProfile)fqlmultiquery1.getQueryByName("user");
            if(!$assertionsDisabled && fqlgetusersprofile2.getUsers().size() != 1)
                throw new AssertionError();
            Iterator iterator1 = fqlgetusersprofile2.getUsers().values().iterator();
            if(iterator1.hasNext())
                facebookuserfull = (FacebookUserFull)(FacebookUser)iterator1.next();
            FqlGetUsersProfile fqlgetusersprofile3 = (FqlGetUsersProfile)fqlmultiquery1.getQueryByName("significant_other");
            if(fqlgetusersprofile3.getUsers().size() == 1)
            {
                FacebookUser facebookuser1;
                for(Iterator iterator2 = fqlgetusersprofile3.getUsers().values().iterator(); iterator2.hasNext(); facebookuserfull.setSignificantOther(facebookuser1))
                    facebookuser1 = (FacebookUser)iterator2.next();

            }
            FqlGetUsersFriendStatus fqlgetusersfriendstatus = (FqlGetUsersFriendStatus)fqlmultiquery1.getQueryByName("arefriends");
            if(fqlgetusersfriendstatus != null && !fqlgetusersfriendstatus.areFriends())
                flag2 = false;
        }
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, facebookuserfull, Boolean.valueOf(flag2));
        continue; /* Loop/switch isn't completed */
_L22:
        FqlMultiQuery fqlmultiquery = (FqlMultiQuery)apimethod;
        FacebookUser facebookuser = null;
        if(i == 200)
        {
            FqlGetUsersProfile fqlgetusersprofile1 = (FqlGetUsersProfile)fqlmultiquery.getQueryByName("user");
            if(!$assertionsDisabled && fqlgetusersprofile1.getUsers().size() != 1)
                throw new AssertionError();
            Iterator iterator = fqlgetusersprofile1.getUsers().values().iterator();
            if(iterator.hasNext())
                facebookuser = (FacebookUser)iterator.next();
        }
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, facebookuser, null);
        continue; /* Loop/switch isn't completed */
_L19:
        Map map3 = null;
        if(i == 200)
            map3 = ((ConnectionsSync)apimethod).getUsersWithoutImageMap();
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, map3, null);
        continue; /* Loop/switch isn't completed */
_L30:
        UsersSearch userssearch = (UsersSearch)apimethod;
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, Integer.valueOf(userssearch.getStartResults()), Integer.valueOf(userssearch.getTotalResults()));
        continue; /* Loop/switch isn't completed */
_L20:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, ((FqlStatusQuery)apimethod).getStatusList(), null);
        continue; /* Loop/switch isn't completed */
_L7:
        com.facebook.katana.model.FacebookNotifications facebooknotifications = null;
        if(i == 200)
            facebooknotifications = ((NotificationsGet)apimethod).getNotifications();
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, facebooknotifications, null);
        continue; /* Loop/switch isn't completed */
_L24:
        EventRsvp eventrsvp = (EventRsvp)apimethod;
        long l2 = eventrsvp.getEventId();
        boolean flag1 = false;
        if(i == 200)
            flag1 = eventrsvp.getSuccess();
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, Long.valueOf(l2), Boolean.valueOf(flag1));
        continue; /* Loop/switch isn't completed */
_L25:
        FqlGetEventMembers fqlgeteventmembers = (FqlGetEventMembers)apimethod;
        long l1 = fqlgeteventmembers.getEventId();
        Map map2 = null;
        if(i == 200)
            map2 = fqlgeteventmembers.getEventMembersByStatus();
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, Long.valueOf(l1), map2);
        continue; /* Loop/switch isn't completed */
_L26:
        FqlGetUsersProfile fqlgetusersprofile = (FqlGetUsersProfile)apimethod;
        Map map1 = null;
        if(i == 200)
            map1 = fqlgetusersprofile.getUsers();
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, map1, null);
        continue; /* Loop/switch isn't completed */
_L27:
        FriendRequestRespond friendrequestrespond = (FriendRequestRespond)apimethod;
        long l = friendrequestrespond.getRequesterUserId();
        boolean flag = false;
        if(i == 200)
            flag = friendrequestrespond.getSuccess();
        Context context = mContext;
        Long long1 = new Long(l);
        Boolean boolean1 = new Boolean(flag);
        AppSession.onServiceOperationComplete(context, intent, i, s, exception, long1, boolean1);
        continue; /* Loop/switch isn't completed */
_L28:
        FqlGetMutualFriends fqlgetmutualfriends = (FqlGetMutualFriends)apimethod;
        Map map = null;
        if(i == 200)
            map = fqlgetmutualfriends.getMutualFriends();
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, map, null);
        continue; /* Loop/switch isn't completed */
_L29:
        FacebookAffiliation.requestCompleted();
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, null, null);
        continue; /* Loop/switch isn't completed */
_L31:
        AppSession.onServiceOperationComplete(mContext, intent, i, s, exception, apimethod, null);
        if(true) goto _L36; else goto _L35
_L35:
    }

    public void onOperationProgress(ApiMethod apimethod, long l, long l1)
    {
        Intent intent = apimethod.getIntent();
        intent.getIntExtra("type", 0);
        JVM INSTR tableswitch 65 65: default 32
    //                   65 33;
           goto _L1 _L2
_L1:
        return;
_L2:
        AppSession.onServiceOperationProgress(mContext, intent, Integer.valueOf((int)((100L * l) / l1)), null);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onProcessComplete(ApiMethod apimethod, int i, String s, Exception exception)
    {
    }

    public void onStart(Intent intent, int i)
    {
        super.onStart(intent, i);
        if(intent != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int j;
        Object obj;
        j = intent.getIntExtra("type", 0);
        obj = null;
        j;
        JVM INSTR lookupswitch 60: default 512
    //                   1: 532
    //                   2: 732
    //                   3: 564
    //                   30: 891
    //                   31: 1084
    //                   33: 1128
    //                   34: 1040
    //                   35: 1197
    //                   36: 1241
    //                   37: 1299
    //                   50: 1790
    //                   51: 1823
    //                   52: 1870
    //                   60: 1914
    //                   61: 1972
    //                   62: 2049
    //                   63: 2137
    //                   64: 2195
    //                   65: 2289
    //                   66: 2445
    //                   67: 2500
    //                   68: 2555
    //                   69: 2610
    //                   70: 2654
    //                   71: 2698
    //                   72: 2767
    //                   73: 2767
    //                   74: 2767
    //                   75: 2767
    //                   76: 2767
    //                   77: 2767
    //                   80: 1653
    //                   81: 1711
    //                   82: 1343
    //                   83: 1537
    //                   90: 3758
    //                   91: 3758
    //                   92: 3758
    //                   100: 2887
    //                   110: 2984
    //                   111: 3142
    //                   112: 3260
    //                   113: 3367
    //                   114: 3522
    //                   115: 3080
    //                   121: 3778
    //                   122: 3839
    //                   131: 3886
    //                   132: 3975
    //                   133: 4034
    //                   140: 4122
    //                   201: 3758
    //                   202: 3758
    //                   203: 3758
    //                   211: 3677
    //                   300: 3758
    //                   301: 532
    //                   400: 858
    //                   1000: 4169
    //                   1001: 4169;
           goto _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L17 _L18 _L19 _L20 _L21 _L22 _L23 _L24 _L25 _L26 _L27 _L28 _L29 _L29 _L29 _L29 _L29 _L29 _L30 _L31 _L32 _L33 _L34 _L34 _L34 _L35 _L36 _L37 _L38 _L39 _L40 _L41 _L42 _L43 _L44 _L45 _L46 _L47 _L34 _L34 _L34 _L48 _L34 _L4 _L49 _L50 _L50
_L3:
        break; /* Loop/switch isn't completed */
_L50:
        break MISSING_BLOCK_LABEL_4169;
_L51:
        String s;
        String s2;
        long l;
        Context context;
        String s3;
        long l1;
        StringBuilder stringbuilder;
        Context context1;
        String s4;
        String s5;
        long l2;
        boolean flag;
        Context context2;
        String s6;
        long l3;
        StringBuilder stringbuilder1;
        Context context3;
        String s7;
        String s8;
        long l4;
        Context context4;
        String s9;
        long l5;
        com.facebook.katana.model.FacebookEvent.RsvpStatus rsvpstatus;
        Context context5;
        Context context6;
        int k;
        String s10;
        String s11;
        int i1;
        int j1;
        String s12;
        long al[];
        int k1;
        ArrayList arraylist;
        int i2;
        int j2;
        Context context7;
        Context context8;
        long l6;
        String s13;
        long al1[];
        boolean flag1;
        ArrayList arraylist1;
        int k2;
        int i3;
        Context context9;
        Context context10;
        long l7;
        FacebookUser facebookuser;
        Context context11;
        String s14;
        long l8;
        String s15;
        FacebookUser facebookuser1;
        ArrayList arraylist2;
        Context context12;
        String s16;
        String s17;
        String s18;
        Context context13;
        String s19;
        int j3;
        long l9;
        Context context14;
        String s20;
        int k3;
        int i4;
        int j4;
        boolean flag2;
        long l10;
        Context context15;
        long l11;
        String s21;
        String s22;
        Context context16;
        long l12;
        String s23;
        String s24;
        String s25;
        int k4;
        Context context17;
        String s26;
        long l13;
        String s27;
        String s28;
        Context context18;
        String s29;
        String s30;
        Context context19;
        String s31;
        String s32;
        Context context20;
        String s33;
        String s34;
        String s35;
        Context context21;
        String s36;
        String s37;
        String s38;
        Context context22;
        String s39;
        String s40;
        String s41;
        Context context23;
        String s42;
        String s43;
        String s44;
        String s45;
        long l14;
        long l15;
        boolean flag3;
        long l16;
        String s46;
        long l17;
        String s47;
        Context context24;
        String s48;
        String s49;
        String as[];
        long l18;
        int i5;
        int j5;
        Context context25;
        String s50;
        long l19;
        String s51;
        Context context26;
        String s52;
        String s53;
        String s54;
        String s55;
        String s56;
        String s57;
        Context context27;
        String s58;
        String s59;
        String s60;
        String s61;
        String s62;
        Context context28;
        String s63;
        long l20;
        String as1[];
        Context context29;
        String s64;
        long al2[];
        Context context30;
        String s65;
        long l21;
        Context context31;
        String s66;
        Context context32;
        String s67;
        Object aobj[];
        String s68;
        String s69;
        Context context33;
        String s70;
        long l22;
        String s71;
        LinkedHashMap linkedhashmap;
        String s72;
        long l23;
        HashMap hashmap;
        Long long1;
        Context context34;
        LinkedHashMap linkedhashmap1;
        String s73;
        long l24;
        long l25;
        HashMap hashmap1;
        Long long2;
        Context context35;
        Context context36;
        String s74;
        String s75;
        Context context37;
        String s76;
        long l26;
        String s77;
        Context context38;
        String s78;
        String s79;
        Context context39;
        String s80;
        long l27;
        String s81;
        String s82;
        Context context40;
        String s83;
        String s84;
        Context context41;
        String s85;
        String s86;
        String s87;
        long al3[];
        String s88;
        FacebookStreamType facebookstreamtype;
        Context context42;
        long l28;
        long l29;
        long l30;
        String s89;
        int k5;
        Context context43;
        String s90;
        Iterator iterator;
        Context context44;
        String s91;
        ArrayList arraylist3;
        HashMap hashmap2;
        long l31;
        String s92;
        StreamGetFilters streamgetfilters;
        Context context45;
        if(obj != null)
            startOp(intent.getStringExtra("rid"), ((ApiMethod) (obj)));
        else
        if(j == 2000)
        {
            String s1 = intent.getStringExtra("rid");
            if(mPendingOps.containsKey(s1))
                ((ApiMethod)mPendingOps.get(s1)).cancel(true);
        }
        continue; /* Loop/switch isn't completed */
_L4:
        obj = new AuthLogin(getApplicationContext(), intent, intent.getStringExtra("un"), intent.getStringExtra("pwd"), this);
          goto _L51
_L6:
        arraylist3 = new ArrayList();
        hashmap2 = new HashMap();
        l31 = intent.getLongExtra("uid", -1L);
        s92 = intent.getStringExtra("session_key");
        hashmap2.put(Long.valueOf(l31), null);
        arraylist3.add(new FqlGetUsersProfile(mContext, intent, s92, null, hashmap2, com/facebook/katana/model/FacebookUser));
        streamgetfilters = new StreamGetFilters(mContext, intent, s92, null);
        arraylist3.add(streamgetfilters);
        context45 = mContext;
        obj = new BatchRun(context45, intent, s92, arraylist3, this);
          goto _L51
_L5:
        for(iterator = mPendingOps.values().iterator(); iterator.hasNext(); ((ApiMethod)iterator.next()).cancel(false));
        mPendingOps.clear();
        mHighPriorityDownloadQueue.clear();
        mMediumPriorityDownloadQueue.clear();
        mLowPriorityDownloadQueue.clear();
        context44 = getApplicationContext();
        s91 = intent.getStringExtra("session_key");
        obj = new AuthLogout(context44, intent, s91, this);
          goto _L51
_L49:
        context43 = mContext;
        s90 = intent.getStringExtra("session_key");
        obj = new ConfigFetcher(context43, intent, s90, this);
          goto _L51
_L7:
        s87 = intent.getStringExtra("session_key");
        al3 = intent.getLongArrayExtra("subject");
        s88 = intent.getStringExtra("stream_type");
        facebookstreamtype = FacebookStreamType.NEWSFEED_STREAM;
        if(s88 != null)
            facebookstreamtype = FacebookStreamType.valueOf(s88);
        context42 = mContext;
        l28 = intent.getLongExtra("start", -1L);
        l29 = intent.getLongExtra("end", -1L);
        l30 = intent.getLongExtra("uid", -1L);
        if(al3 == null)
            s89 = intent.getStringExtra("session_filter_key");
        else
            s89 = null;
        k5 = intent.getIntExtra("limit", -1);
        obj = new FqlGetStream(context42, intent, s87, this, l28, l29, al3, l30, s89, k5, facebookstreamtype);
          goto _L51
_L10:
        context41 = mContext;
        s85 = intent.getStringExtra("session_key");
        s86 = intent.getStringExtra("post_id");
        obj = new StreamRemove(context41, intent, s85, s86, this);
          goto _L51
_L8:
        context40 = getApplicationContext();
        s83 = intent.getStringExtra("session_key");
        s84 = intent.getStringExtra("post_id");
        obj = new StreamGetComments(context40, intent, s83, s84, this);
          goto _L51
_L9:
        context39 = mContext;
        s80 = intent.getStringExtra("session_key");
        l27 = intent.getLongExtra("session_user_id", -1L);
        s81 = intent.getStringExtra("post_id");
        s82 = intent.getStringExtra("status");
        obj = new StreamAddComment(context39, intent, s80, l27, s81, s82, this);
          goto _L51
_L11:
        context38 = mContext;
        s78 = intent.getStringExtra("session_key");
        s79 = intent.getStringExtra("item_id");
        obj = new StreamRemoveComment(context38, intent, s78, s79, this);
          goto _L51
_L12:
        context37 = mContext;
        s76 = intent.getStringExtra("session_key");
        l26 = intent.getLongExtra("session_user_id", -1L);
        s77 = intent.getStringExtra("post_id");
        obj = new StreamAddLike(context37, intent, s76, l26, s77, this);
          goto _L51
_L13:
        context36 = mContext;
        s74 = intent.getStringExtra("session_key");
        s75 = intent.getStringExtra("post_id");
        obj = new StreamRemoveLike(context36, intent, s74, s75, this);
          goto _L51
_L32:
        linkedhashmap1 = new LinkedHashMap();
        s73 = intent.getStringExtra("session_key");
        l24 = intent.getLongExtra("uid", -1L);
        l25 = intent.getLongExtra("session_user_id", -1L);
        hashmap1 = new HashMap();
        long2 = new Long(l24);
        hashmap1.put(long2, null);
        linkedhashmap1.put("user", new FqlGetUsersProfile(mContext, intent, s73, null, hashmap1, com/facebook/katana/model/FacebookUserFull));
        linkedhashmap1.put("significant_other", new FqlGetUsersProfile(mContext, intent, s73, null, "uid IN (SELECT significant_other_id FROM #user)", com/facebook/katana/model/FacebookUser));
        if(l24 != l25)
            linkedhashmap1.put("arefriends", new FqlGetUsersFriendStatus(mContext, intent, s73, l25, l24, null));
        context35 = mContext;
        obj = new FqlMultiQuery(context35, intent, s73, linkedhashmap1, this);
          goto _L51
_L33:
        linkedhashmap = new LinkedHashMap();
        s72 = intent.getStringExtra("session_key");
        l23 = intent.getLongExtra("uid", -1L);
        hashmap = new HashMap();
        long1 = new Long(l23);
        hashmap.put(long1, null);
        linkedhashmap.put("user", new FqlGetUsersProfile(mContext, intent, s72, null, hashmap, com/facebook/katana/model/FacebookUser));
        context34 = mContext;
        obj = new FqlMultiQuery(context34, intent, s72, linkedhashmap, this);
          goto _L51
_L30:
        context33 = mContext;
        s70 = intent.getStringExtra("session_key");
        l22 = intent.getLongExtra("session_user_id", -1L);
        s71 = intent.getStringExtra("un");
        obj = new ConnectionsSync(context33, intent, s70, l22, s71, this);
          goto _L51
_L31:
        context32 = mContext;
        s67 = intent.getStringExtra("session_key");
        aobj = new Object[1];
        aobj[0] = Long.valueOf(intent.getLongExtra("session_user_id", -1L));
        s68 = String.format("SELECT uid,first_name,last_name,name,status,pic_square FROM user WHERE ((uid IN (SELECT target_id FROM connection WHERE source_id=%1$d AND target_type='user' AND is_following=1)) AND (status.message != '')) ORDER BY status.time DESC LIMIT 25", aobj);
        s69 = intent.getStringExtra("un");
        obj = new FqlStatusQuery(context32, intent, s67, s68, s69, this);
          goto _L51
_L14:
        context31 = mContext;
        s66 = intent.getStringExtra("session_key");
        obj = new NotificationsGet(context31, intent, s66, this);
          goto _L51
_L15:
        context30 = mContext;
        s65 = intent.getStringExtra("session_key");
        l21 = intent.getLongExtra("session_user_id", -1L);
        obj = new FqlSyncNotifications(context30, intent, s65, l21, this);
          goto _L51
_L16:
        context29 = mContext;
        s64 = intent.getStringExtra("session_key");
        al2 = intent.getLongArrayExtra("item_id");
        obj = new NotificationsMarkRead(context29, intent, s64, al2, this);
          goto _L51
_L17:
        context28 = mContext;
        s63 = intent.getStringExtra("session_key");
        l20 = intent.getLongExtra("uid", -1L);
        as1 = intent.getStringArrayExtra("aid");
        obj = new SyncAlbums(context28, intent, s63, l20, as1, this);
          goto _L51
_L18:
        context27 = mContext;
        s58 = intent.getStringExtra("session_key");
        s59 = intent.getStringExtra("name");
        s60 = intent.getStringExtra("location");
        s61 = intent.getStringExtra("description");
        s62 = intent.getStringExtra("visibility");
        obj = new PhotosCreateAlbum(context27, intent, s58, s59, s60, s61, s62, this);
          goto _L51
_L19:
        context26 = mContext;
        s52 = intent.getStringExtra("session_key");
        s53 = intent.getStringExtra("aid");
        s54 = intent.getStringExtra("name");
        s55 = intent.getStringExtra("location");
        s56 = intent.getStringExtra("description");
        s57 = intent.getStringExtra("visibility");
        obj = new PhotosEditAlbum(context26, intent, s52, s53, s54, s55, s56, s57, this);
          goto _L51
_L20:
        context25 = mContext;
        s50 = intent.getStringExtra("session_key");
        l19 = intent.getLongExtra("uid", -1L);
        s51 = intent.getStringExtra("aid");
        obj = new PhotosDeleteAlbum(context25, intent, s50, l19, s51, this);
          goto _L51
_L21:
        context24 = mContext;
        s48 = intent.getStringExtra("session_key");
        s49 = intent.getStringExtra("aid");
        as = intent.getStringArrayExtra("pid");
        l18 = intent.getLongExtra("uid", -1L);
        i5 = intent.getIntExtra("start", 0);
        j5 = intent.getIntExtra("limit", -1);
        obj = new PhotosGetPhotos(context24, intent, s48, s49, as, l18, i5, j5, this);
          goto _L51
_L22:
        context23 = mContext;
        s42 = intent.getStringExtra("session_key");
        s43 = intent.getStringExtra("caption");
        s44 = intent.getStringExtra("uri");
        s45 = intent.getStringExtra("aid");
        l14 = intent.getLongExtra("checkin_id", -1L);
        l15 = intent.getLongExtra("profile_id", -1L);
        flag3 = intent.getBooleanExtra("extra_photo_publish", false);
        l16 = intent.getLongExtra("extra_place", -1L);
        s46 = intent.getStringExtra("tags");
        l17 = intent.getLongExtra("extra_status_target_id", -1L);
        s47 = intent.getStringExtra("extra_status_privacy");
        obj = new PhotosUpload(context23, intent, s42, s43, s44, this, s45, l14, l15, flag3, l16, s46, l17, s47);
          goto _L51
_L23:
        context22 = mContext;
        s39 = intent.getStringExtra("session_key");
        s40 = intent.getStringExtra("pid");
        s41 = intent.getStringExtra("caption");
        obj = new PhotosEditPhoto(context22, intent, s39, s40, s41, this);
          goto _L51
_L24:
        context21 = mContext;
        s36 = intent.getStringExtra("session_key");
        s37 = intent.getStringExtra("aid");
        s38 = intent.getStringExtra("pid");
        obj = new PhotosDeletePhoto(context21, intent, s36, s37, s38, this);
          goto _L51
_L25:
        context20 = mContext;
        s33 = intent.getStringExtra("session_key");
        s34 = intent.getStringExtra("pid");
        s35 = intent.getStringExtra("tags");
        obj = new PhotosAddTag(context20, intent, s33, s34, s35, this);
          goto _L51
_L26:
        context19 = mContext;
        s31 = intent.getStringExtra("session_key");
        s32 = intent.getStringExtra("pid");
        obj = new PhotosGetTags(context19, intent, s31, s32, this);
          goto _L51
_L27:
        context18 = getApplicationContext();
        s29 = intent.getStringExtra("session_key");
        s30 = intent.getStringExtra("pid");
        obj = new SyncPhotoComments(context18, intent, s29, s30, this);
          goto _L51
_L28:
        context17 = mContext;
        s26 = intent.getStringExtra("session_key");
        l13 = intent.getLongExtra("uid", -1L);
        s27 = intent.getStringExtra("pid");
        s28 = intent.getStringExtra("status");
        obj = new PhotosAddComment(context17, intent, s26, l13, s27, s28, this);
          goto _L51
_L29:
        context16 = mContext;
        l12 = intent.getLongExtra("uid", -1L);
        s23 = intent.getStringExtra("aid");
        s24 = intent.getStringExtra("pid");
        s25 = intent.getStringExtra("uri");
        k4 = intent.getIntExtra("type", -1);
        obj = new PhotoDownload(context16, intent, l12, s23, s24, s25, k4, this);
        addToBestPlaceInDownloadQueue(((ApiMethod) (obj)));
        if(mHighPriorityDownloadQueue.size() + mMediumPriorityDownloadQueue.size() + mLowPriorityDownloadQueue.size() > 3)
            continue; /* Loop/switch isn't completed */
          goto _L51
_L35:
        context15 = mContext;
        l11 = intent.getLongExtra("uid", -1L);
        s21 = intent.getStringExtra("uri");
        s22 = FileUtils.buildFilename(mContext);
        obj = new ProfileImageDownload(context15, intent, l11, s21, s22, this);
        addToBestPlaceInDownloadQueue(((ApiMethod) (obj)));
        if(mHighPriorityDownloadQueue.size() + mMediumPriorityDownloadQueue.size() + mLowPriorityDownloadQueue.size() > 1)
            continue; /* Loop/switch isn't completed */
          goto _L51
_L36:
        context14 = mContext;
        s20 = intent.getStringExtra("session_key");
        k3 = intent.getIntExtra("folder", 0);
        i4 = intent.getIntExtra("start", 0);
        j4 = intent.getIntExtra("limit", 20);
        flag2 = intent.getBooleanExtra("sync", false);
        l10 = intent.getLongExtra("uid", -1L);
        obj = new MailboxSync(context14, intent, s20, k3, i4, j4, flag2, l10, this);
          goto _L51
_L41:
        context13 = mContext;
        s19 = intent.getStringExtra("session_key");
        j3 = intent.getIntExtra("folder", 0);
        l9 = intent.getLongExtra("tid", -1L);
        obj = new MailboxGetThreadMessages(context13, intent, s19, j3, l9, 0, -1, this);
          goto _L51
_L37:
        facebookuser1 = new FacebookUser(intent.getLongExtra("profile_uid", -1L), intent.getStringExtra("profile_first_name"), intent.getStringExtra("profile_last_name"), intent.getStringExtra("profile_display_name"), intent.getStringExtra("profile_url"));
        arraylist2 = (ArrayList)intent.getParcelableArrayListExtra("uid");
        context12 = mContext;
        s16 = intent.getStringExtra("session_key");
        s17 = intent.getStringExtra("subject");
        s18 = intent.getStringExtra("status");
        obj = new MailboxSend(context12, intent, s16, facebookuser1, arraylist2, s17, s18, this);
          goto _L51
_L38:
        facebookuser = new FacebookUser(intent.getLongExtra("profile_uid", -1L), intent.getStringExtra("profile_first_name"), intent.getStringExtra("profile_last_name"), intent.getStringExtra("profile_display_name"), intent.getStringExtra("profile_url"));
        context11 = mContext;
        s14 = intent.getStringExtra("session_key");
        l8 = intent.getLongExtra("tid", -1L);
        s15 = intent.getStringExtra("status");
        obj = new MailboxReply(context11, intent, s14, facebookuser, l8, s15, this);
          goto _L51
_L39:
        s13 = intent.getStringExtra("session_key");
        al1 = intent.getLongArrayExtra("tid");
        flag1 = intent.getBooleanExtra("read", true);
        if(al1.length == 1)
        {
            context10 = mContext;
            l7 = al1[0];
            obj = new MailboxMarkThread(context10, intent, s13, l7, flag1, this);
        } else
        {
            arraylist1 = new ArrayList();
            k2 = 0;
            do
            {
                i3 = al1.length;
                if(k2 >= i3)
                    break;
                arraylist1.add(new MailboxMarkThread(mContext, intent, s13, al1[k2], flag1, this));
                k2++;
            } while(true);
            context9 = mContext;
            obj = new BatchRun(context9, intent, s13, arraylist1, this);
        }
          goto _L51
_L40:
        s12 = intent.getStringExtra("session_key");
        al = intent.getLongArrayExtra("tid");
        k1 = intent.getIntExtra("folder", 0);
        if(al.length == 1)
        {
            context8 = mContext;
            l6 = al[0];
            obj = new MailboxDeleteThread(context8, intent, s12, l6, k1, this);
        } else
        {
            arraylist = new ArrayList();
            i2 = 0;
            do
            {
                j2 = al.length;
                if(i2 >= j2)
                    break;
                arraylist.add(new MailboxDeleteThread(mContext, intent, s12, al[i2], k1, this));
                i2++;
            } while(true);
            context7 = mContext;
            obj = new BatchRun(context7, intent, s12, arraylist, this);
        }
          goto _L51
_L48:
        context6 = mContext;
        k = Integer.parseInt(intent.getStringExtra("rid"));
        s10 = intent.getStringExtra("session_key");
        s11 = intent.getStringExtra("subject");
        i1 = intent.getIntExtra("start", 0);
        j1 = intent.getIntExtra("limit", 0);
        obj = new UsersSearch(context6, intent, k, s10, s11, i1, j1, this);
          goto _L51
_L34:
        AppSession.onServiceOperationComplete(mContext, intent, 200, "Ok", null, null, null);
          goto _L51
_L42:
        s9 = intent.getStringExtra("session_key");
        l5 = intent.getLongExtra("eid", -1L);
        rsvpstatus = FacebookEvent.getRsvpStatus(intent.getStringExtra("rsvp_status"));
        context5 = mContext;
        obj = new EventRsvp(context5, intent, s9, this, l5, rsvpstatus);
          goto _L51
_L43:
        s8 = intent.getStringExtra("session_key");
        l4 = intent.getLongExtra("eid", -1L);
        context4 = mContext;
        obj = new FqlGetEventMembers(context4, intent, s8, this, l4);
          goto _L51
_L44:
        s6 = intent.getStringExtra("session_key");
        l3 = intent.getLongExtra("uid", -1L);
        stringbuilder1 = new StringBuilder("uid IN (SELECT uid_from FROM friend_request WHERE uid_to=");
        stringbuilder1.append(String.valueOf(l3));
        stringbuilder1.append(")");
        context3 = mContext;
        s7 = stringbuilder1.toString();
        obj = new FqlGetUsersProfile(context3, intent, s6, this, s7, com/facebook/katana/model/FacebookUser);
          goto _L51
_L45:
        s5 = intent.getStringExtra("session_key");
        l2 = intent.getLongExtra("uid", -1L);
        flag = intent.getBooleanExtra("confirm", false);
        context2 = mContext;
        obj = new FriendRequestRespond(context2, intent, s5, this, l2, flag);
          goto _L51
_L46:
        s3 = intent.getStringExtra("session_key");
        l1 = intent.getLongExtra("uid", -1L);
        stringbuilder = new StringBuilder("uid2 IN (SELECT uid_from FROM friend_request WHERE uid_to=");
        stringbuilder.append(String.valueOf(l1));
        stringbuilder.append(")");
        context1 = mContext;
        s4 = stringbuilder.toString();
        obj = new FqlGetMutualFriends(context1, intent, s3, this, l1, s4);
          goto _L51
_L47:
        s2 = intent.getStringExtra("session_key");
        l = intent.getLongExtra("uid", -1L);
        context = mContext;
        obj = new FqlGetFacebookAffiliation(context, intent, s2, this, l);
          goto _L51
        s = intent.getStringExtra("rid");
        obj = (ApiMethod)apiMethodReceiver.remove(s);
        if(obj != null)
            ((ApiMethod) (obj)).addIntentAndListener(intent, this);
          goto _L51
        if(true) goto _L1; else goto _L52
_L52:
    }

    static final boolean $assertionsDisabled = false;
    private static final String EXTRA_ORIGINAL_INTENT = "FacebookService.originalIntent";
    private static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";
    private static final int MAX_CONCURRENT_DOWNLOAD = 3;
    private static final long MAX_DIFF_TIME_MS = 100L;
    public static Map apiMethodReceiver = new HashMap();
    public static boolean isBackgroundMode = true;
    private static Intent nextIntent = null;
    private Context mContext;
    private final List mHighPriorityDownloadQueue = new LinkedList();
    private long mLastBatchTime;
    private final List mLowPriorityDownloadQueue = new LinkedList();
    private final List mMediumPriorityDownloadQueue = new LinkedList();
    private final Map mPendingOps = new HashMap();

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/FacebookService.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
