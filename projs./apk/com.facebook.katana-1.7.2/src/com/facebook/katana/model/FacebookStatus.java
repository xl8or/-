// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookStatus.java

package com.facebook.katana.model;

import java.io.IOException;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.model:
//            FacebookUser

public class FacebookStatus
{

    public FacebookStatus(FacebookUser facebookuser, String s, long l)
    {
        mUser = facebookuser;
        mMessage = s;
        mTime = l;
    }

    public FacebookStatus(JsonParser jsonparser)
        throws JsonParseException, IOException
    {
        String s = null;
        long l = -1L;
        String s1 = null;
        String s2 = null;
        String s3 = null;
        String s4 = null;
        long l1 = 0L;
        String s5 = null;
        JsonToken jsontoken = jsonparser.nextToken();
        while(jsontoken != JsonToken.END_OBJECT) 
        {
            if(jsontoken == JsonToken.VALUE_STRING)
            {
                String s6 = jsonparser.getCurrentName();
                if(s6.equals("first_name"))
                    s1 = jsonparser.getText();
                else
                if(s6.equals("last_name"))
                    s2 = jsonparser.getText();
                else
                if(s6.equals("name"))
                    s3 = jsonparser.getText();
                else
                if(s6.equals("pic_square"))
                {
                    s4 = jsonparser.getText();
                    if(s4.length() == 0)
                        s4 = null;
                }
            } else
            if(jsontoken == JsonToken.VALUE_NUMBER_INT)
            {
                if(jsonparser.getCurrentName().equals("uid"))
                    l = jsonparser.getLongValue();
            } else
            if(jsontoken == JsonToken.START_OBJECT)
            {
                if(s != null)
                    if(s.equals("status"))
                        while(jsontoken != JsonToken.END_OBJECT) 
                        {
                            if(jsontoken == JsonToken.VALUE_STRING)
                            {
                                if(jsonparser.getCurrentName().equals("message"))
                                    s5 = jsonparser.getText();
                            } else
                            if(jsontoken == JsonToken.VALUE_NUMBER_INT && jsonparser.getCurrentName().equals("time"))
                                l1 = jsonparser.getLongValue();
                            jsontoken = jsonparser.nextToken();
                        }
                    else
                        jsonparser.skipChildren();
            } else
            if(jsontoken == JsonToken.FIELD_NAME)
                s = jsonparser.getText();
            jsontoken = jsonparser.nextToken();
        }
        if(s1 != null && s1.equals("null"))
            s1 = null;
        if(s2 != null && s2.equals("null"))
            s2 = null;
        mUser = new FacebookUser(l, s1, s2, s3, s4);
        if(s5 != null && s5.length() > 0)
            mMessage = s5;
        else
            mMessage = null;
        mTime = l1;
    }

    public String getMessage()
    {
        return mMessage;
    }

    public long getTime()
    {
        return mTime;
    }

    public FacebookUser getUser()
    {
        return mUser;
    }

    public void setUser(FacebookUser facebookuser)
    {
        mUser = facebookuser;
    }

    private final String mMessage;
    private final long mTime;
    private FacebookUser mUser;
}
