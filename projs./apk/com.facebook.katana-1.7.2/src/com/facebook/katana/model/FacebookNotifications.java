// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookNotifications.java

package com.facebook.katana.model;

import android.content.Context;
import com.facebook.katana.provider.UserValuesManager;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.model:
//            FacebookApiException

public class FacebookNotifications
{

    public FacebookNotifications(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        String s;
        int i;
        String s1;
        long l;
        int j;
        long l1;
        int k;
        JsonToken jsontoken;
        s = null;
        i = -1;
        s1 = null;
        l = -1L;
        j = 0;
        l1 = -1L;
        k = 0;
        mFriendRequests = new ArrayList();
        mEventInvites = new ArrayList();
        jsontoken = jsonparser.nextToken();
_L2:
        JsonToken jsontoken1 = JsonToken.END_OBJECT;
        if(jsontoken == jsontoken1)
            break MISSING_BLOCK_LABEL_489;
        JsonToken jsontoken2 = JsonToken.START_OBJECT;
        if(jsontoken != jsontoken2)
            break; /* Loop/switch isn't completed */
        if(s != null)
            if(s.equals("messages"))
                do
                {
                    JsonToken jsontoken13 = JsonToken.END_OBJECT;
                    if(jsontoken == jsontoken13)
                        break;
                    JsonToken jsontoken14 = JsonToken.VALUE_NUMBER_INT;
                    if(jsontoken == jsontoken14)
                    {
                        String s3 = jsonparser.getCurrentName();
                        if(s3.equals("most_recent"))
                            l = jsonparser.getLongValue();
                        else
                        if(s3.equals("unread"))
                            j = jsonparser.getIntValue();
                    }
                    jsontoken = jsonparser.nextToken();
                } while(true);
            else
            if(s.equals("pokes"))
                do
                {
                    JsonToken jsontoken11 = JsonToken.END_OBJECT;
                    if(jsontoken == jsontoken11)
                        break;
                    JsonToken jsontoken12 = JsonToken.VALUE_NUMBER_INT;
                    if(jsontoken == jsontoken12)
                    {
                        String s2 = jsonparser.getCurrentName();
                        if(s2.equals("most_recent"))
                            l1 = jsonparser.getLongValue();
                        else
                        if(s2.equals("unread"))
                            k = jsonparser.getIntValue();
                    }
                    jsontoken = jsonparser.nextToken();
                } while(true);
            else
                jsonparser.skipChildren();
_L4:
        jsontoken = jsonparser.nextToken();
        if(true) goto _L2; else goto _L1
_L1:
        JsonToken jsontoken3 = JsonToken.START_ARRAY;
        if(jsontoken != jsontoken3)
            break MISSING_BLOCK_LABEL_404;
        if(s == null) goto _L4; else goto _L3
_L3:
        if(!s.equals("friend_requests"))
            break MISSING_BLOCK_LABEL_338;
_L6:
        JsonToken jsontoken9 = JsonToken.END_ARRAY;
        if(jsontoken == jsontoken9) goto _L4; else goto _L5
_L5:
        JsonToken jsontoken10 = JsonToken.VALUE_NUMBER_INT;
        if(jsontoken == jsontoken10)
            mFriendRequests.add(Long.valueOf(jsonparser.getLongValue()));
        jsontoken = jsonparser.nextToken();
          goto _L6
        if(!s.equals("event_invites"))
            break MISSING_BLOCK_LABEL_397;
_L8:
        JsonToken jsontoken7 = JsonToken.END_ARRAY;
        if(jsontoken == jsontoken7) goto _L4; else goto _L7
_L7:
        JsonToken jsontoken8 = JsonToken.VALUE_NUMBER_INT;
        if(jsontoken == jsontoken8)
            mEventInvites.add(Long.valueOf(jsonparser.getLongValue()));
        jsontoken = jsonparser.nextToken();
          goto _L8
        jsonparser.skipChildren();
          goto _L4
        JsonToken jsontoken4 = JsonToken.VALUE_NUMBER_INT;
        if(jsontoken == jsontoken4)
        {
            if(jsonparser.getCurrentName().equals("error_code"))
                i = jsonparser.getIntValue();
        } else
        {
            JsonToken jsontoken5 = JsonToken.VALUE_STRING;
            if(jsontoken == jsontoken5)
            {
                if(jsonparser.getCurrentName().equals("error_msg"))
                    s1 = jsonparser.getText();
            } else
            {
                JsonToken jsontoken6 = JsonToken.FIELD_NAME;
                if(jsontoken == jsontoken6)
                    s = jsonparser.getText();
            }
        }
          goto _L4
        if(i > 0)
        {
            FacebookApiException facebookapiexception = new FacebookApiException(i, s1);
            throw facebookapiexception;
        }
        boolean flag;
        Iterator iterator;
        if(l != mMostRecentMessageId)
        {
            mMostRecentMessageId = l;
            mUnreadMessages = j;
        } else
        {
            mUnreadMessages = 0;
        }
        if(l1 != mMostRecentPokeId)
        {
            mMostRecentPokeId = l1;
            mUnreadPokes = k;
        } else
        {
            mUnreadPokes = 0;
        }
        flag = false;
        iterator = mFriendRequests.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            long l3 = ((Long)iterator.next()).longValue();
            if(!flag)
            {
                if(l3 == mMostRecentFriendRequestId)
                {
                    flag = true;
                    iterator.remove();
                }
            } else
            {
                iterator.remove();
            }
        } while(true);
        if(mFriendRequests.size() > 0)
            mMostRecentFriendRequestId = ((Long)mFriendRequests.get(0)).longValue();
        boolean flag1 = false;
        Iterator iterator1 = mEventInvites.iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            long l2 = ((Long)iterator1.next()).longValue();
            if(!flag1)
            {
                if(l2 == mMostRecentEventInviteId)
                {
                    flag1 = true;
                    iterator1.remove();
                }
            } else
            {
                iterator1.remove();
            }
        } while(true);
        if(mEventInvites.size() > 0)
            mMostRecentEventInviteId = ((Long)mEventInvites.get(0)).longValue();
        return;
    }

    public static void load(Context context)
    {
        mMostRecentMessageId = UserValuesManager.getLastSeenId(context, "message");
        mMostRecentPokeId = UserValuesManager.getLastSeenId(context, "poke");
        mMostRecentFriendRequestId = UserValuesManager.getLastSeenId(context, "friend_request");
        mMostRecentEventInviteId = UserValuesManager.getLastSeenId(context, "event_invite");
    }

    public static void save(Context context)
    {
        UserValuesManager.setLastSeenId(context, "message", Long.valueOf(mMostRecentMessageId));
        UserValuesManager.setLastSeenId(context, "poke", Long.valueOf(mMostRecentPokeId));
        UserValuesManager.setLastSeenId(context, "friend_request", Long.valueOf(mMostRecentFriendRequestId));
        UserValuesManager.setLastSeenId(context, "event_invite", Long.valueOf(mMostRecentEventInviteId));
    }

    public List getEventInvites()
    {
        return mEventInvites;
    }

    public List getFriendRequests()
    {
        return mFriendRequests;
    }

    public int getUnreadMessages()
    {
        return mUnreadMessages;
    }

    public int getUnreadPokes()
    {
        return mUnreadPokes;
    }

    public boolean hasNewNotifications()
    {
        boolean flag;
        if(mUnreadMessages != 0 || mUnreadPokes != 0 || mFriendRequests.size() != 0 || mEventInvites.size() != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static final String EVENT_INVITE_KEY = "event_invite";
    private static final String FRIEND_REQUEST_KEY = "friend_request";
    private static final String MESSAGE_KEY = "message";
    private static final String POKE_KEY = "poke";
    private static long mMostRecentEventInviteId;
    private static long mMostRecentFriendRequestId;
    private static long mMostRecentMessageId;
    private static long mMostRecentPokeId;
    private final List mEventInvites;
    private final List mFriendRequests;
    private final int mUnreadMessages;
    private final int mUnreadPokes;
}
