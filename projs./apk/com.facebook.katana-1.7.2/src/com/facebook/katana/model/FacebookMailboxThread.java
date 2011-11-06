// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookMailboxThread.java

package com.facebook.katana.model;

import android.content.ContentValues;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.model:
//            FacebookProfile

public class FacebookMailboxThread
{

    public FacebookMailboxThread(long l, String s, String s1, long l1, int i, 
            int j, long l2, long l3, List list)
    {
        mParticipants = new LinkedHashSet(list);
        if(0L != l3)
            mParticipants.add(Long.valueOf(l3));
        mThreadId = l;
        mSubject = s;
        mSnippet = s1;
        mOtherPartyUserId = l1;
        mMsgCount = i;
        mUnreadCount = j;
        mLastUpdate = l2;
        mObjectId = l3;
    }

    public FacebookMailboxThread(JsonParser jsonparser)
        throws JsonParseException, IOException
    {
        long l;
        String s;
        String s1;
        long l1;
        int i;
        int j;
        long l2;
        String s2;
        long l3;
        JsonToken jsontoken;
        l = -1L;
        s = null;
        s1 = null;
        l1 = -1L;
        i = 0;
        j = 0;
        l2 = -1L;
        s2 = null;
        l3 = 0L;
        mParticipants = new LinkedHashSet();
        jsontoken = jsonparser.nextToken();
_L2:
        JsonToken jsontoken1 = JsonToken.END_OBJECT;
        if(jsontoken == jsontoken1)
            break MISSING_BLOCK_LABEL_445;
        JsonToken jsontoken2 = JsonToken.VALUE_STRING;
        if(jsontoken != jsontoken2)
            break; /* Loop/switch isn't completed */
        String s4 = jsonparser.getCurrentName();
        if(s4.equals("thread_id"))
            l = Long.parseLong(jsonparser.getText());
        else
        if(s4.equals("subject"))
            s = jsonparser.getText();
        else
        if(s4.equals("snippet"))
            s1 = jsonparser.getText();
        else
        if(s4.equals("snippet_author"))
            l1 = Long.parseLong(jsonparser.getText());
_L3:
        jsontoken = jsonparser.nextToken();
        if(true) goto _L2; else goto _L1
_L1:
        JsonToken jsontoken3 = JsonToken.VALUE_NUMBER_INT;
        if(jsontoken == jsontoken3)
        {
            String s3 = jsonparser.getCurrentName();
            if(s3.equals("thread_id"))
                l = jsonparser.getLongValue();
            else
            if(s3.equals("message_count"))
                i = jsonparser.getIntValue();
            else
            if(s3.equals("unread"))
                j = jsonparser.getIntValue();
            else
            if(s3.equals("updated_time"))
                l2 = jsonparser.getLongValue();
            else
            if(s3.equals("snippet_author"))
                l1 = jsonparser.getLongValue();
            else
            if(s3.equals("object_id"))
                l3 = jsonparser.getLongValue();
        } else
        {
label0:
            {
                JsonToken jsontoken4 = JsonToken.START_OBJECT;
                if(jsontoken != jsontoken4)
                    break label0;
                while(jsonparser.nextToken() != JsonToken.END_OBJECT) ;
            }
        }
          goto _L3
        JsonToken jsontoken5 = JsonToken.START_ARRAY;
        if(jsontoken != jsontoken5)
            break MISSING_BLOCK_LABEL_424;
        if(s2 == null) goto _L3; else goto _L4
_L4:
        JsonToken jsontoken7;
        if(!s2.equals("recipients"))
            break MISSING_BLOCK_LABEL_411;
        jsontoken7 = jsonparser.nextToken();
_L6:
        JsonToken jsontoken8 = JsonToken.END_ARRAY;
        if(jsontoken7 == jsontoken8) goto _L3; else goto _L5
_L5:
        JsonToken jsontoken9 = JsonToken.VALUE_NUMBER_INT;
        if(jsontoken7 == jsontoken9)
            mParticipants.add(Long.valueOf(jsonparser.getLongValue()));
        jsontoken7 = jsonparser.nextToken();
          goto _L6
        while(jsonparser.nextToken() != JsonToken.END_ARRAY) ;
          goto _L3
        JsonToken jsontoken6 = JsonToken.FIELD_NAME;
        if(jsontoken == jsontoken6)
            s2 = jsonparser.getText();
          goto _L3
        if(0L != l3)
            mParticipants.add(Long.valueOf(l3));
        mThreadId = l;
        mSubject = s;
        mSnippet = s1;
        mOtherPartyUserId = l1;
        mMsgCount = i;
        mUnreadCount = j;
        mLastUpdate = l2;
        mObjectId = l3;
        return;
    }

    public ContentValues getContentValues(int i, long l, Map map, String s)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("folder", Integer.valueOf(i));
        contentvalues.put("tid", Long.valueOf(getThreadId()));
        contentvalues.put("subject", getSubject());
        contentvalues.put("snippet", getSnippet());
        contentvalues.put("other_party", Long.valueOf(getOtherPartyUserId(l)));
        contentvalues.put("msg_count", Integer.valueOf(getMsgCount()));
        contentvalues.put("unread_count", Integer.valueOf(getUnreadCount()));
        contentvalues.put("last_update", Long.valueOf(getLastUpdate()));
        contentvalues.put("object_id", Long.valueOf(getObjectId()));
        contentvalues.put("participants", getParticipantsString(map, s, Long.valueOf(l)));
        return contentvalues;
    }

    public long getLastUpdate()
    {
        return mLastUpdate;
    }

    public int getMsgCount()
    {
        return mMsgCount;
    }

    public long getObjectId()
    {
        return mObjectId;
    }

    public long getOtherPartyUserId()
    {
        return mOtherPartyUserId;
    }

    public long getOtherPartyUserId(long l)
    {
        long l1 = -1L;
        if(mObjectId != 0L)
            l1 = mObjectId;
        else
        if(mParticipants.size() > 0)
        {
            Iterator iterator = mParticipants.iterator();
            while(iterator.hasNext()) 
            {
                long l2 = ((Long)iterator.next()).longValue();
                if(l2 != l)
                    l1 = l2;
            }
        }
        return l1;
    }

    public List getParticipants()
    {
        return new ArrayList(mParticipants);
    }

    public String getParticipantsString(Map map, String s, Long long1)
    {
        String s1;
        if(mObjectId != 0L)
        {
            FacebookProfile facebookprofile1 = (FacebookProfile)map.get(Long.valueOf(mObjectId));
            if(facebookprofile1 != null)
                s1 = facebookprofile1.mDisplayName;
            else
                s1 = "";
        } else
        if(mParticipants.size() > 0)
        {
            StringBuffer stringbuffer = new StringBuffer();
            Iterator iterator = mParticipants.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                Long long2 = (Long)iterator.next();
                if(!long2.equals(long1))
                {
                    if(stringbuffer.length() > 0)
                        stringbuffer.append(s);
                    FacebookProfile facebookprofile = (FacebookProfile)map.get(long2);
                    if(facebookprofile != null)
                    {
                        String s2 = facebookprofile.mDisplayName;
                        if(s2 != null)
                            stringbuffer.append(s2);
                    }
                }
            } while(true);
            s1 = stringbuffer.toString();
        } else
        {
            s1 = "";
        }
        return s1;
    }

    public String getSnippet()
    {
        return mSnippet;
    }

    public String getSubject()
    {
        return mSubject;
    }

    public long getThreadId()
    {
        return mThreadId;
    }

    public int getUnreadCount()
    {
        return mUnreadCount;
    }

    public void setUnreadCount(int i)
    {
        mUnreadCount = i;
    }

    public static final long INVALID_OBJECT_ID;
    private final long mLastUpdate;
    private final int mMsgCount;
    private final long mObjectId;
    private final long mOtherPartyUserId;
    private final Set mParticipants;
    private final String mSnippet;
    private final String mSubject;
    private final long mThreadId;
    private int mUnreadCount;
}
