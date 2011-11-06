// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceNotificationManager.java

package com.facebook.katana.binding;

import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.widget.RemoteViews;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.activity.events.EventDetailsActivity;
import com.facebook.katana.activity.media.UploadPhotoActivity;
import com.facebook.katana.activity.media.UploadVideoActivity;
import com.facebook.katana.model.FacebookNotifications;
import com.facebook.katana.provider.EventsProvider;
import com.facebook.katana.service.FacebookService;
import java.io.File;
import java.util.*;

public class ServiceNotificationManager
{
    private static class MyNotification
    {

        private String getCaption()
        {
            return mCaption;
        }

        private int getType()
        {
            return mType;
        }

        private void update(Context context)
        {
            mType;
            JVM INSTR tableswitch 0 2: default 32
        //                       0 53
        //                       1 338
        //                       2 214;
               goto _L1 _L2 _L3 _L4
_L1:
            ((NotificationManager)context.getSystemService("notification")).notify(mId, mNotif);
            return;
_L2:
            RemoteViews remoteviews = new RemoteViews(context.getPackageName(), 0x7f03006a);
            remoteviews.setImageViewResource(0x7f0e010c, 0x7f02012a);
            if(mCaption != null)
            {
                Object aobj5[] = new Object[1];
                aobj5[0] = mCaption;
                remoteviews.setTextViewText(0x7f0e0017, context.getString(0x7f0a020d, aobj5));
            } else
            {
                Object aobj4[] = new Object[2];
                aobj4[0] = Integer.valueOf(mPosition);
                aobj4[1] = Integer.valueOf(mCount);
                remoteviews.setTextViewText(0x7f0e0017, context.getString(0x7f0a020e, aobj4));
            }
            remoteviews.setTextViewText(0x7f0e010d, (new StringBuilder()).append(mProgress).append("%").toString());
            remoteviews.setProgressBar(0x7f0e010e, 100, mProgress, false);
            mNotif.contentView = remoteviews;
            continue; /* Loop/switch isn't completed */
_L4:
            if(mCaption != null)
            {
                Notification notification3 = mNotif;
                Object aobj3[] = new Object[1];
                aobj3[0] = mCaption;
                notification3.tickerText = context.getString(0x7f0a020a, aobj3);
            } else
            {
                Notification notification2 = mNotif;
                Object aobj2[] = new Object[2];
                aobj2[0] = Integer.valueOf(mPosition);
                aobj2[1] = Integer.valueOf(mCount);
                notification2.tickerText = context.getString(0x7f0a020b, aobj2);
            }
            mNotif.setLatestEventInfo(context, context.getResources().getString(0x7f0a020c), mNotif.tickerText, mNotif.contentIntent);
            continue; /* Loop/switch isn't completed */
_L3:
            if(mCaption != null)
            {
                Notification notification1 = mNotif;
                Object aobj1[] = new Object[1];
                aobj1[0] = mCaption;
                notification1.tickerText = context.getString(0x7f0a0208, aobj1);
            } else
            {
                Notification notification = mNotif;
                Object aobj[] = new Object[2];
                aobj[0] = Integer.valueOf(mPosition);
                aobj[1] = Integer.valueOf(mCount);
                notification.tickerText = context.getString(0x7f0a0209, aobj);
            }
            mNotif.setLatestEventInfo(context, context.getResources().getString(0x7f0a020c), mNotif.tickerText, mNotif.contentIntent);
            if(true) goto _L1; else goto _L5
_L5:
        }

        public void updateCount(Context context, int i)
        {
            mCount = i;
            update(context);
        }

        public void updateNotification(Context context, Notification notification, int i)
        {
            mNotif = notification;
            mType = i;
            update(context);
        }

        public void updateProgress(Context context, int i)
        {
            mProgress = i;
            update(context);
        }

        public static final int TYPE_ERROR = 1;
        public static final int TYPE_PROGRESS = 0;
        public static final int TYPE_SUCCESS = 2;
        private final String mCaption;
        private int mCount;
        public final String mFilename;
        private final int mId;
        public Notification mNotif;
        private final int mPosition;
        public int mProgress;
        private int mType;





        public MyNotification(int i, Notification notification, int j, String s, String s1, int k, int l, 
                int i1)
        {
            mId = i;
            mType = j;
            mCaption = s;
            mNotif = notification;
            mFilename = s1;
            mProgress = k;
            mPosition = l;
            mCount = i1;
        }
    }


    private ServiceNotificationManager()
    {
    }

    protected static void beginPhotoUploadProgressNotification(Context context, int i, String s, String s1, String s2)
    {
        Intent intent = new Intent(context, com/facebook/katana/activity/media/UploadPhotoActivity);
        intent.setAction((new StringBuilder()).append("com.facebook.katana.upload.notification.pending.").append(i).toString());
        intent.putExtra("android.intent.extra.STREAM", Uri.parse(s2));
        intent.putExtra("extra_photo_is_scaled", true);
        if(s != null)
            intent.putExtra("extra_album_id", s);
        intent.setFlags(0x14000000);
        postBeginProgressNotification(context, i, s1, s2, intent);
    }

    public static void beginVideoUploadProgressNotification(Context context, int i, String s, String s1, String s2)
    {
        Intent intent = new Intent(context, com/facebook/katana/activity/media/UploadVideoActivity);
        intent.setAction((new StringBuilder()).append("com.facebook.katana.upload.notification.pending.").append(i).toString());
        intent.putExtra("android.intent.extra.STREAM", Uri.parse(s2));
        intent.setFlags(0x14000000);
        postBeginProgressNotification(context, i, s, s2, intent);
    }

    protected static void cancelUploadNotification(Context context, int i)
    {
        MyNotification mynotification = (MyNotification)mProgressNotificationMap.remove(Integer.valueOf(i));
        if(mynotification != null)
            ((NotificationManager)context.getSystemService("notification")).cancel(mynotification.mId);
    }

    protected static boolean endPhotoUploadProgressNotification(Context context, int i, int j, String s, String s1, String s2)
    {
        MyNotification mynotification = (MyNotification)mProgressNotificationMap.get(Integer.valueOf(i));
        boolean flag;
        if(mynotification != null)
        {
            ((NotificationManager)context.getSystemService("notification")).cancel(mynotification.mId);
            Notification notification = new Notification();
            Intent intent;
            Intent intent1;
            byte byte0;
            if(j == 200)
                notification.icon = 0x7f020127;
            else
                notification.icon = 0x1080078;
            intent = new Intent(context, com/facebook/katana/service/FacebookService);
            intent.setAction("com.facebook.katana.clear_notification");
            intent.putExtra("type", 300);
            intent.putExtra("android.intent.extra.SUBJECT", i);
            notification.deleteIntent = PendingIntent.getService(context, 0, intent, 0);
            intent1 = new Intent(context, com/facebook/katana/activity/media/UploadPhotoActivity);
            intent1.putExtra("extra_album_id", s1);
            if(j == 200)
            {
                intent1.setAction("com.facebook.katana.upload.notification.ok");
                intent1.putExtra("extra_photo_id", s2);
            } else
            {
                intent1.setAction("com.facebook.katana.upload.notification.error");
            }
            intent1.putExtra("android.intent.extra.STREAM", Uri.parse(s));
            intent1.putExtra("extra_photo_is_scaled", true);
            intent1.putExtra("android.intent.extra.SUBJECT", i);
            intent1.setFlags(0x14000000);
            if(mynotification.getCaption() != null)
                intent1.putExtra("android.intent.extra.TITLE", mynotification.getCaption());
            notification.contentIntent = PendingIntent.getActivity(context, 0, intent1, 0);
            if(j == 200)
                byte0 = 2;
            else
                byte0 = 1;
            mynotification.updateNotification(context, notification, byte0);
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    public static boolean endVideoUploadProgressNotification(Context context, int i, int j, String s)
    {
        MyNotification mynotification = (MyNotification)mProgressNotificationMap.get(Integer.valueOf(i));
        boolean flag;
        if(mynotification != null)
        {
            ((NotificationManager)context.getSystemService("notification")).cancel(mynotification.mId);
            Notification notification = new Notification();
            Intent intent;
            Intent intent1;
            byte byte0;
            if(j == 200)
                notification.icon = 0x7f020127;
            else
                notification.icon = 0x1080078;
            intent = new Intent(context, com/facebook/katana/service/FacebookService);
            intent.setAction("com.facebook.katana.clear_notification");
            intent.putExtra("type", 300);
            intent.putExtra("android.intent.extra.SUBJECT", i);
            notification.deleteIntent = PendingIntent.getService(context, 0, intent, 0);
            intent1 = new Intent(context, com/facebook/katana/activity/media/UploadVideoActivity);
            if(j == 200)
                intent1.setAction("com.facebook.katana.upload.notification.ok");
            else
                intent1.setAction("com.facebook.katana.upload.notification.error");
            intent1.putExtra("android.intent.extra.STREAM", Uri.parse(s));
            intent1.putExtra("android.intent.extra.SUBJECT", i);
            intent1.setFlags(0x14000000);
            if(mynotification.getCaption() != null)
                intent1.putExtra("android.intent.extra.TITLE", mynotification.getCaption());
            notification.contentIntent = PendingIntent.getActivity(context, 0, intent1, 0);
            if(j == 200)
                byte0 = 2;
            else
                byte0 = 1;
            mynotification.updateNotification(context, notification, byte0);
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    protected static void handleClearNotifications(Context context)
    {
        Iterator iterator = mProgressNotificationMap.values().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            MyNotification mynotification = (MyNotification)iterator.next();
            if(mynotification.getType() == 1 || mynotification.getType() == 2)
            {
                (new File(mynotification.mFilename)).delete();
                ((NotificationManager)context.getSystemService("notification")).cancel(mynotification.mId);
                iterator.remove();
            }
        } while(true);
    }

    private static void postBeginProgressNotification(Context context, int i, String s, String s1, Intent intent)
    {
        Notification notification = new Notification(0x7f02012a, null, System.currentTimeMillis());
        notification.flags = 2 | notification.flags;
        notification.setLatestEventInfo(context, context.getResources().getString(0x7f0a001e), null, PendingIntent.getActivity(context, 0, intent, 0));
        int j = 0;
        Iterator iterator = mProgressNotificationMap.values().iterator();
        if(iterator.hasNext())
            j = ((MyNotification)iterator.next()).mCount;
        int k = j + 1;
        mProgressNotificationMap.put(Integer.valueOf(i), new MyNotification(i + 100, notification, 0, s, s1, 0, k, k));
        for(Iterator iterator1 = mProgressNotificationMap.values().iterator(); iterator1.hasNext(); ((MyNotification)iterator1.next()).updateCount(context, k));
    }

    public static void realShowNotification(Context context, int i, CharSequence charsequence, int j, int k, Intent intent, boolean flag)
    {
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Notification notification = new Notification(i, charsequence, System.currentTimeMillis());
        if(sharedpreferences.getBoolean("use_led", false))
        {
            notification.ledOnMS = 500;
            notification.ledOffMS = 2000;
            notification.ledARGB = 0xff0000ff;
            notification.flags = 1 | notification.flags;
        }
        notification.flags = 0x10 | notification.flags;
        notification.setLatestEventInfo(context, context.getResources().getString(0x7f0a001e), charsequence, PendingIntent.getActivity(context, 0, intent, 0x10000000));
        if(!flag)
        {
            if(((TelephonyManager)context.getSystemService("phone")).getCallState() == 0)
            {
                String s = sharedpreferences.getString("ringtone", null);
                if(s != null && s.length() > 0)
                {
                    notification.audioStreamType = 2;
                    notification.sound = Uri.parse(s);
                }
            }
            if(sharedpreferences.getBoolean("vibrate", false))
                notification.defaults = 2 | notification.defaults;
        }
        ((NotificationManager)context.getSystemService("notification")).notify(k, notification);
    }

    protected static void release(Context context)
    {
        ((NotificationManager)context.getSystemService("notification")).cancelAll();
        mProgressNotificationMap.clear();
    }

    protected static void showNotification(Context context, long l, FacebookNotifications facebooknotifications, String s, String s1)
    {
        SharedPreferences sharedpreferences;
        boolean flag;
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        flag = false;
        if(sharedpreferences.getBoolean("notif", true)) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(facebooknotifications.getUnreadMessages() <= 0 || !sharedpreferences.getBoolean("notif_messages", false)) goto _L4; else goto _L3
_L3:
        android.content.pm.PackageInfo packageinfo = context.getPackageManager().getPackageInfo("com.facebook.orca", 0);
        boolean flag1;
        if(packageinfo != null)
            flag1 = true;
        else
            flag1 = false;
_L5:
        if(!flag1)
        {
            StringBuilder stringbuilder;
            StringBuilder stringbuilder1;
            Uri uri;
            StringBuilder stringbuilder2;
            StringBuilder stringbuilder3;
            StringBuilder stringbuilder4;
            Intent intent1;
            android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
            String s2;
            Intent intent2;
            if(facebooknotifications.getUnreadMessages() == 1)
            {
                s2 = context.getString(0x7f0a0235);
            } else
            {
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(facebooknotifications.getUnreadMessages());
                s2 = context.getString(0x7f0a0236, aobj);
            }
            intent2 = IntentUriHandler.getIntentForUri(context, "fb://messaging");
            realShowNotification(context, 0x7f02012b, s2, facebooknotifications.getUnreadMessages(), 1, intent2, flag);
            flag = true;
        }
_L4:
        if(facebooknotifications.getFriendRequests().size() > 0 && sharedpreferences.getBoolean("notif_friend_requests", false))
        {
            stringbuilder2 = new StringBuilder(context.getString(0x7f0a0234));
            stringbuilder3 = stringbuilder2.append(" ").append(facebooknotifications.getFriendRequests().size()).append(" ");
            int j;
            if(facebooknotifications.getFriendRequests().size() == 1)
                j = 0x7f0a00cf;
            else
                j = 0x7f0a00d0;
            stringbuilder4 = stringbuilder3.append(context.getString(j));
            intent1 = IntentUriHandler.getIntentForUri(context, "fb://requests");
            realShowNotification(context, 0x7f020129, stringbuilder4.toString(), facebooknotifications.getFriendRequests().size(), 2, intent1, flag);
            flag = true;
        }
        if(facebooknotifications.getEventInvites().size() > 0 && sharedpreferences.getBoolean("notif_event_invites", false))
        {
            stringbuilder = (new StringBuilder(context.getString(0x7f0a0234))).append(" ").append(facebooknotifications.getEventInvites().size()).append(" ");
            int i;
            Intent intent;
            if(facebooknotifications.getEventInvites().size() == 1)
                i = 0x7f0a00cd;
            else
                i = 0x7f0a00ce;
            stringbuilder1 = stringbuilder.append(context.getString(i));
            if(facebooknotifications.getEventInvites().size() == 1)
            {
                intent = new Intent(context, com/facebook/katana/activity/events/EventDetailsActivity);
                uri = Uri.withAppendedPath(EventsProvider.EVENT_EID_CONTENT_URI, ((Long)facebooknotifications.getEventInvites().get(0)).toString());
                intent.setData(uri);
            } else
            {
                intent = IntentUriHandler.getIntentForUri(context, "fb://events");
            }
            realShowNotification(context, 0x7f020128, stringbuilder1.toString(), facebooknotifications.getEventInvites().size(), 3, intent, flag);
        }
          goto _L1
        namenotfoundexception;
        flag1 = false;
          goto _L5
    }

    public static boolean updateProgressNotification(Context context, int i, int j)
    {
        MyNotification mynotification = (MyNotification)mProgressNotificationMap.get(Integer.valueOf(i));
        boolean flag;
        if(mynotification != null)
        {
            mynotification.updateProgress(context, j);
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    public static final int NOTIFICATION_TYPE_UPLOAD_PROGRESS_BASE = 100;
    public static final int TYPE_CHAT_MESSAGES = 4;
    public static final int TYPE_DEFAULT = 5;
    public static final int TYPE_EVENT_INVITES = 3;
    public static final int TYPE_FRIEND_REQUESTS = 2;
    public static final int TYPE_MESSAGES = 1;
    private static Map mProgressNotificationMap = new LinkedHashMap();

}
