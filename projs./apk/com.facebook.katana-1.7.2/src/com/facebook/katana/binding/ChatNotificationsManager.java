// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChatNotificationsManager.java

package com.facebook.katana.binding;

import android.app.*;
import android.content.*;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import com.facebook.katana.activity.chat.BuddyListActivity;
import com.facebook.katana.activity.chat.ChatConversationActivity;
import com.facebook.katana.provider.ConnectionsProvider;
import java.util.*;

// Referenced classes of package com.facebook.katana.binding:
//            AppSession, ProfileImagesCache, AppSessionListener, ProfileImage

public class ChatNotificationsManager
{

    public ChatNotificationsManager(Context context)
    {
        mContext = context;
        mRunningConversationId = -1L;
        mNotificationManager = (NotificationManager)mContext.getSystemService("notification");
    }

    private void displayNotificationPendingPicture(final Notification note, final long UID, final String message)
    {
        final AppSession appSession = AppSession.getActiveSession(mContext, false);
        ProfileImagesCache profileimagescache = appSession.getUserImagesCache();
        final Timer timer = new Timer();
        final AppSessionListener asl = new AppSessionListener() {

            public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache1)
            {
                if(i == 200 && profileimage.id == UID)
                {
                    timer.cancel();
                    timer.purge();
                    appSession.removeListener(this);
                    displayWithUserPic(note, profileimage.getBitmap(), UID, message);
                }
            }

            public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
            {
                timer.cancel();
                timer.purge();
                appSession.removeListener(this);
                displayWithUserPic(note, profileimage.getBitmap(), UID, message);
            }

            final ChatNotificationsManager this$0;
            final long val$UID;
            final AppSession val$appSession;
            final String val$message;
            final Notification val$note;
            final Timer val$timer;

            
            {
                this$0 = ChatNotificationsManager.this;
                UID = l;
                timer = timer1;
                appSession = appsession;
                note = notification;
                message = s;
                super();
            }
        }
;
        appSession.addListener(asl);
        timer.schedule(new TimerTask() {

            public void run()
            {
                appSession.removeListener(asl);
                displayWithUserPic(note, null, UID, message);
            }

            final ChatNotificationsManager this$0;
            final long val$UID;
            final AppSession val$appSession;
            final AppSessionListener val$asl;
            final String val$message;
            final Notification val$note;

            
            {
                this$0 = ChatNotificationsManager.this;
                appSession = appsession;
                asl = appsessionlistener;
                note = notification;
                UID = l;
                message = s;
                super();
            }
        }
, 2500L);
        Bitmap bitmap = profileimagescache.getWithoutURL(mContext, UID);
        if(bitmap != null)
        {
            appSession.removeListener(asl);
            timer.cancel();
            timer.purge();
            displayWithUserPic(note, bitmap, UID, message);
        }
    }

    private void displayWithUserPic(Notification notification, Bitmap bitmap, long l, String s)
    {
        if(PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("notif_messages", false))
        {
            if(bitmap != null)
            {
                RemoteViews remoteviews = new RemoteViews(mContext.getPackageName(), 0x7f030066);
                remoteviews.setImageViewBitmap(0x7f0e002d, bitmap);
                remoteviews.setTextViewText(0x7f0e0017, getDisplayName(l));
                remoteviews.setTextViewText(0x7f0e0107, s);
                notification.contentView = remoteviews;
            }
            mNotificationManager.notify(4, notification);
        }
    }

    private String getDisplayName(long l)
    {
        String s;
        if(mDisplayName.containsKey(Long.valueOf(l)))
        {
            s = (String)mDisplayName.get(Long.valueOf(l));
        } else
        {
            String as[] = new String[1];
            as[0] = "display_name";
            Cursor cursor = mContext.getContentResolver().query(ConnectionsProvider.FRIENDS_CONTENT_URI, as, (new StringBuilder()).append("user_id = ").append(l).toString(), null, null);
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                mDisplayName.put(Long.valueOf(l), cursor.getString(0));
                cursor.close();
                s = (String)mDisplayName.get(Long.valueOf(l));
            } else
            {
                cursor.close();
                s = mContext.getString(0x7f0a0067);
            }
        }
        return s;
    }

    private Notification getDisplayNotification(String s, PendingIntent pendingintent)
    {
        Notification notification = new Notification(0x7f02012b, s, System.currentTimeMillis());
        notification.flags = 0x10 | notification.flags;
        notification.setLatestEventInfo(mContext, mContext.getString(0x7f0a001e), getExpandedText(), pendingintent);
        notification.number = mCount;
        return notification;
    }

    private String getExpandedText()
    {
        String s;
        if(mCount > 1)
        {
            Context context = mContext;
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(mCount);
            s = context.getString(0x7f0a002b, aobj);
        } else
        {
            s = mContext.getString(0x7f0a002a);
        }
        return s;
    }

    public void clear()
    {
        mCount = 0;
        mNotificationCount.clear();
        mNotificationManager.cancel(4);
    }

    public void displayNotification(long l, String s, String s1)
    {
        if(mRunningConversationId != l) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Intent intent;
        if(!mNotificationCount.containsKey(Long.valueOf(l)))
            mNotificationCount.put(Long.valueOf(l), Integer.valueOf(0));
        mNotificationCount.put(Long.valueOf(l), Integer.valueOf(1 + ((Integer)mNotificationCount.get(Long.valueOf(l))).intValue()));
        mCount = 1 + mCount;
        if(mNotificationCount.size() != 1)
            break; /* Loop/switch isn't completed */
        intent = new Intent(mContext, com/facebook/katana/activity/chat/ChatConversationActivity);
        intent.putExtra("buddyId", l);
        intent.putExtra("displayName", getDisplayName(l));
        if(s1 != null)
            intent.putExtra("token", s1);
_L4:
        PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0, intent, 0x10000000);
        displayNotificationPendingPicture(getDisplayNotification((new StringBuilder()).append(getDisplayName(l)).append(": ").append(s).toString(), pendingintent), l, s);
        if(true) goto _L1; else goto _L3
_L3:
        intent = new Intent(mContext, com/facebook/katana/activity/chat/BuddyListActivity);
        intent.addFlags(0x4000000);
        if(s1 != null)
            intent.putExtra("token", s1);
          goto _L4
        if(true) goto _L1; else goto _L5
_L5:
    }

    public void markConversationAsRunning(long l)
    {
        mRunningConversationId = l;
        if(mNotificationCount.containsKey(Long.valueOf(l)))
        {
            mCount = mCount - ((Integer)mNotificationCount.get(Long.valueOf(l))).intValue();
            mNotificationCount.remove(Long.valueOf(l));
            if(mCount == 0)
            {
                clear();
            } else
            {
                Intent intent = new Intent(mContext, com/facebook/katana/activity/chat/BuddyListActivity);
                intent.addFlags(0x4000000);
                PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0, intent, 0x10000000);
                Notification notification = getDisplayNotification(mContext.getString(0x7f0a002a), pendingintent);
                mNotificationManager.notify(4, notification);
            }
        }
    }

    public void unMarkConversationAsRunning(long l)
    {
        mRunningConversationId = -1L;
    }

    private static final int PROFILE_PIC_WAIT_TIMEOUT_MS = 2500;
    private final Context mContext;
    private int mCount;
    private final Map mDisplayName = new HashMap();
    private final Map mNotificationCount = new HashMap();
    private final NotificationManager mNotificationManager;
    private long mRunningConversationId;

}
