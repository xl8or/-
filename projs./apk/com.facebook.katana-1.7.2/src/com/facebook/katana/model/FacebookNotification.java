// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookNotification.java

package com.facebook.katana.model;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.ProfileTabHostActivity;
import com.facebook.katana.activity.events.EventDetailsActivity;
import com.facebook.katana.activity.feedback.FeedbackActivity;
import com.facebook.katana.activity.media.PhotoFeedbackActivity;
import com.facebook.katana.provider.EventsProvider;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FacebookNotification extends JMCachingDictDestination
{

    private FacebookNotification()
    {
    }

    public static Intent getIntentForNotification(String s, String s1, long l, String s2, Activity activity)
    {
        Intent intent1;
        if(s == null)
        {
            Log.v("Notifications", (new StringBuilder()).append("Null object type for: ").append(s2).toString());
            intent1 = null;
        } else
        if(s.equals("stream"))
        {
            Intent intent = new Intent(activity, com/facebook/katana/activity/feedback/FeedbackActivity);
            intent.putExtra("extra_post_id", s1);
            intent.putExtra("extra_uid", l);
            intent1 = intent;
        } else
        if(s.equals("photo"))
        {
            Intent intent2 = new Intent(activity, com/facebook/katana/activity/media/PhotoFeedbackActivity);
            intent2.setData(Uri.withAppendedPath(PhotosProvider.PHOTOS_PID_CONTENT_URI, s1));
            intent1 = intent2;
        } else
        if(s.equals("friend"))
            intent1 = ProfileTabHostActivity.intentForProfile(activity, Long.parseLong(s1));
        else
        if(s.equals("event"))
        {
            Intent intent3 = new Intent(activity, com/facebook/katana/activity/events/EventDetailsActivity);
            intent3.setData(Uri.withAppendedPath(EventsProvider.EVENT_EID_CONTENT_URI, s1));
            intent1 = intent3;
        } else
        if(s.equals("group"))
        {
            Intent intent4 = ProfileTabHostActivity.intentForProfile(activity, Long.parseLong(s1));
            intent4.putExtra("extra_user_type", 3);
            intent1 = intent4;
        } else
        {
            intent1 = null;
        }
        return intent1;
    }

    public static FacebookNotification parseJson(JsonParser jsonparser)
        throws JsonParseException, IOException, JMException
    {
        return (FacebookNotification)JMParser.parseObjectJson(jsonparser, com/facebook/katana/model/FacebookNotification);
    }

    public long getAppId()
    {
        return mAppId;
    }

    public String getBody()
    {
        return mBody;
    }

    public long getCreatedTime()
    {
        return mCreatedTime;
    }

    public String getHRef()
    {
        return mHref;
    }

    public long getNotificationId()
    {
        return mNotificationId;
    }

    public String getObjectId()
    {
        return mObjectId;
    }

    public String getObjectType()
    {
        return mObjectType;
    }

    public long getSenderId()
    {
        return mSenderId;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public boolean isRead()
    {
        boolean flag;
        if(!mIsUnread)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static final String EVENT_TYPE = "event";
    public static final String GROUP_TYPE = "group";
    public static final String PHOTO_TYPE = "photo";
    public static final String PROFILE_TYPE = "friend";
    public static final String STREAM_TYPE = "stream";
    private final long mAppId = 0L;
    private final String mBody = null;
    private final long mCreatedTime = 0L;
    private final String mHref = null;
    private final boolean mIsUnread = false;
    private final long mNotificationId = 0L;
    private final String mObjectId = null;
    private final String mObjectType = null;
    private final long mSenderId = -1L;
    private final String mTitle = null;
}
