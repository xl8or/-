// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPhotoComment.java

package com.facebook.katana.model;

import java.io.IOException;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.model:
//            FacebookProfile

public class FacebookPhotoComment
{

    public FacebookPhotoComment(String s, long l, String s1, long l1, long l2)
    {
        mPhotoId = s;
        mFromProfileId = l;
        mBody = s1;
        mTime = l1;
        mId = l2;
    }

    public FacebookPhotoComment(JsonParser jsonparser)
        throws JsonParseException, IOException
    {
        String s = null;
        long l = -1L;
        String s1 = null;
        long l1 = -1L;
        long l2 = -1L;
        JsonToken jsontoken = jsonparser.nextToken();
        while(jsontoken != JsonToken.END_OBJECT) 
        {
            if(jsontoken == JsonToken.VALUE_STRING)
            {
                String s3 = jsonparser.getCurrentName();
                if(s3.equals("pid"))
                    s = jsonparser.getText();
                else
                if(s3.equals("body"))
                    s1 = jsonparser.getText();
            } else
            if(jsontoken == JsonToken.VALUE_NUMBER_INT)
            {
                String s2 = jsonparser.getCurrentName();
                if(s2.equals("from"))
                    l = jsonparser.getLongValue();
                else
                if(s2.equals("time"))
                    l1 = jsonparser.getLongValue();
                else
                if(s2.equals("pcid"))
                    l2 = jsonparser.getLongValue();
            }
            jsontoken = jsonparser.nextToken();
        }
        mPhotoId = s;
        mFromProfileId = l;
        mBody = s1;
        mTime = l1;
        mId = l2;
    }

    public String getBody()
    {
        return mBody;
    }

    public FacebookProfile getFromProfile()
    {
        return mFromProfile;
    }

    public long getFromProfileId()
    {
        return mFromProfileId;
    }

    public long getId()
    {
        return mId;
    }

    public String getPhotoId()
    {
        return mPhotoId;
    }

    public long getTime()
    {
        return mTime;
    }

    public void setFromProfile(FacebookProfile facebookprofile)
    {
        mFromProfileId = facebookprofile.mId;
        mFromProfile = facebookprofile;
    }

    private final String mBody;
    private FacebookProfile mFromProfile;
    private long mFromProfileId;
    private final long mId;
    private final String mPhotoId;
    private final long mTime;
}
