// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookMailboxThreadMessage.java

package com.facebook.katana.model;

import java.io.IOException;
import org.codehaus.jackson.*;

public class FacebookMailboxThreadMessage
{

    public FacebookMailboxThreadMessage(JsonParser jsonparser)
        throws JsonParseException, IOException
    {
        this(jsonparser, -1L);
    }

    public FacebookMailboxThreadMessage(JsonParser jsonparser, long l)
        throws JsonParseException, IOException
    {
        long l1;
        long l2;
        long l3;
        String s;
        JsonToken jsontoken;
        l1 = -1L;
        l2 = -1L;
        l3 = -1L;
        s = null;
        jsontoken = jsonparser.nextToken();
_L1:
        if(jsontoken == JsonToken.END_OBJECT)
            break MISSING_BLOCK_LABEL_254;
        if(jsontoken == JsonToken.VALUE_STRING)
        {
            String s2 = jsonparser.getCurrentName();
            if(s2.equals("body"))
                s = jsonparser.getText();
            else
            if(s2.equals("message_id"))
            {
                String s3 = jsonparser.getText();
                l1 = Long.parseLong(s3.substring(1 + s3.indexOf('_')));
            } else
            if(s2.equals("thread_id"))
                l = Long.parseLong(jsonparser.getText());
        } else
        {
            if(jsontoken != JsonToken.VALUE_NUMBER_INT)
                continue; /* Loop/switch isn't completed */
            String s1 = jsonparser.getCurrentName();
            if(s1.equals("thread_id"))
                l = jsonparser.getLongValue();
            else
            if(s1.equals("author_id"))
                l2 = jsonparser.getLongValue();
            else
            if(s1.equals("created_time"))
                l3 = jsonparser.getLongValue();
        }
_L3:
        jsontoken = jsonparser.nextToken();
          goto _L1
        if(jsontoken != JsonToken.START_OBJECT) goto _L3; else goto _L2
_L2:
        int i = 1;
        while(i > 0) 
        {
            JsonToken jsontoken1 = jsonparser.nextToken();
            if(jsontoken1 == JsonToken.START_OBJECT)
                i++;
            else
            if(jsontoken1 == JsonToken.END_OBJECT)
                i--;
        }
          goto _L3
        mThreadId = l;
        mMessageId = l1;
        mAuthorUserId = l2;
        mTimeSent = l3;
        mBody = s;
        return;
    }

    public long getAuthorId()
    {
        return mAuthorUserId;
    }

    public String getBody()
    {
        return mBody;
    }

    public long getMessageId()
    {
        return mMessageId;
    }

    public long getThreadId()
    {
        return mThreadId;
    }

    public long getTimeSent()
    {
        return mTimeSent;
    }

    private final long mAuthorUserId;
    private final String mBody;
    private final long mMessageId;
    private final long mThreadId;
    private final long mTimeSent;
}
