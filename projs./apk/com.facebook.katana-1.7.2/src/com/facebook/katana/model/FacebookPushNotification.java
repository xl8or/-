// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPushNotification.java

package com.facebook.katana.model;

import android.content.*;
import android.preference.PreferenceManager;
import com.facebook.katana.*;
import com.facebook.katana.activity.events.EventsActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.Map;

// Referenced classes of package com.facebook.katana.model:
//            FacebookSessionInfo

public class FacebookPushNotification extends JMCachingDictDestination
{
    private static final class NotificationType extends Enum
    {

        public static NotificationType valueOf(String s)
        {
            return (NotificationType)Enum.valueOf(com/facebook/katana/model/FacebookPushNotification$NotificationType, s);
        }

        public static NotificationType[] values()
        {
            return (NotificationType[])$VALUES.clone();
        }

        private static final NotificationType $VALUES[];
        public static final NotificationType CHAT;
        public static final NotificationType DEFAULT;
        public static final NotificationType EVENT;
        public static final NotificationType FRIEND;
        public static final NotificationType MSG;
        public static final NotificationType UNKNOWN;
        public static final NotificationType WALL;

        static 
        {
            UNKNOWN = new NotificationType("UNKNOWN", 0);
            MSG = new NotificationType("MSG", 1);
            CHAT = new NotificationType("CHAT", 2);
            WALL = new NotificationType("WALL", 3);
            EVENT = new NotificationType("EVENT", 4);
            FRIEND = new NotificationType("FRIEND", 5);
            DEFAULT = new NotificationType("DEFAULT", 6);
            NotificationType anotificationtype[] = new NotificationType[7];
            anotificationtype[0] = UNKNOWN;
            anotificationtype[1] = MSG;
            anotificationtype[2] = CHAT;
            anotificationtype[3] = WALL;
            anotificationtype[4] = EVENT;
            anotificationtype[5] = FRIEND;
            anotificationtype[6] = DEFAULT;
            $VALUES = anotificationtype;
        }

        private NotificationType(String s, int i)
        {
            super(s, i);
        }
    }


    private FacebookPushNotification()
    {
        mType = null;
        mTimestamp = 0L;
        mMessage = null;
        mUnreadCount = 0;
        mParams = null;
    }

    public FacebookPushNotification(String s, long l, String s1, int i, Map map)
    {
        mType = s;
        mTimestamp = l;
        mMessage = s1;
        mUnreadCount = i;
        mParams = map;
    }

    private NotificationType mapType(String s)
    {
        NotificationType notificationtype;
        if(s.equals("msg"))
            notificationtype = NotificationType.MSG;
        else
        if(s.equals("chat"))
            notificationtype = NotificationType.CHAT;
        else
        if(s.equals("wall"))
            notificationtype = NotificationType.WALL;
        else
        if(s.equals("event_invite"))
            notificationtype = NotificationType.EVENT;
        else
        if(s.equals("friend"))
            notificationtype = NotificationType.FRIEND;
        else
            notificationtype = NotificationType.DEFAULT;
        return notificationtype;
    }

    public void showNotification(Context context)
    {
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(sharedpreferences.getBoolean("notif", true)) goto _L2; else goto _L1
_L1:
        return;
_L2:
        String s;
        int i;
        int j;
        byte byte0;
        Intent intent;
        if(mapType(mType) == NotificationType.CHAT)
        {
            (new ChatNotificationsManager(context)).displayNotification(((Long)mParams.get("from")).longValue(), mMessage, (String)mParams.get("token"));
            continue; /* Loop/switch isn't completed */
        }
        s = mMessage;
        i = mUnreadCount;
        j = -1;
        byte0 = -1;
        intent = null;
        class _cls1
        {

            static final int $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType[];

            static 
            {
                $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType = new int[NotificationType.values().length];
                NoSuchFieldError nosuchfielderror3;
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType[NotificationType.MSG.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType[NotificationType.WALL.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType[NotificationType.FRIEND.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                $SwitchMap$com$facebook$katana$model$FacebookPushNotification$NotificationType[NotificationType.EVENT.ordinal()] = 4;
_L2:
                return;
                nosuchfielderror3;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls1..SwitchMap.com.facebook.katana.model.FacebookPushNotification.NotificationType[mapType(mType).ordinal()];
        JVM INSTR tableswitch 1 4: default 148
    //                   1 207
    //                   2 237
    //                   3 273
    //                   4 305;
           goto _L3 _L4 _L5 _L6 _L7
_L7:
        break MISSING_BLOCK_LABEL_305;
_L4:
        break; /* Loop/switch isn't completed */
_L3:
        j = 0x7f0200c8;
        byte0 = 5;
        intent = new Intent(context, com/facebook/katana/HomeActivity);
        intent.putExtra(HomeActivity.EXTRA_SHOW_NOTIFICATIONS, true);
_L9:
        if(intent != null)
        {
            intent.addFlags(0x4000000);
            ServiceNotificationManager.realShowNotification(context, j, s, i, byte0, intent, false);
        }
        if(true) goto _L1; else goto _L8
_L8:
        if(sharedpreferences.getBoolean("notif_messages", false))
        {
            j = 0x7f02012b;
            byte0 = 1;
            intent = IntentUriHandler.getIntentForUri(context, "fb://messaging");
        }
          goto _L9
_L5:
        AppSession appsession;
        j = 0x7f0200c8;
        byte0 = 5;
        appsession = AppSession.getActiveSession(context, false);
        if(appsession == null) goto _L1; else goto _L10
_L10:
        intent = ProfileTabHostActivity.intentForProfile(context, appsession.getSessionInfo().userId);
          goto _L9
_L6:
        j = 0x7f020129;
        byte0 = 2;
        intent = new Intent(context, com/facebook/katana/UsersTabHostActivity);
        intent.putExtra("com.facebook.katana.DefaultTab", "requests");
          goto _L9
        j = 0x7f020128;
        byte0 = 3;
        intent = new Intent(context, com/facebook/katana/activity/events/EventsActivity);
          goto _L9
    }

    public final String mMessage;
    public final Map mParams;
    public final long mTimestamp;
    public final String mType;
    public final int mUnreadCount;
}
