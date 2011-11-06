// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookContactInfo.java

package com.facebook.katana.model;

import java.io.IOException;
import org.codehaus.jackson.*;

public class FacebookContactInfo
{

    public FacebookContactInfo(JsonParser jsonparser)
        throws JsonParseException, IOException
    {
        long l = -1L;
        String s = null;
        String s1 = null;
        String s2 = null;
        JsonToken jsontoken = jsonparser.nextToken();
        while(jsontoken != JsonToken.END_OBJECT) 
        {
            if(jsontoken == JsonToken.VALUE_STRING)
            {
                String s6 = jsonparser.getCurrentName();
                if(s6.equals("cell"))
                    s1 = jsonparser.getText();
                else
                if(s6.equals("phone"))
                    s2 = jsonparser.getText();
                else
                if(s6.equals("email"))
                    s = jsonparser.getText();
            } else
            if(jsontoken == JsonToken.VALUE_NUMBER_INT && jsonparser.getCurrentName().equals("uid"))
                l = jsonparser.getLongValue();
            jsontoken = jsonparser.nextToken();
        }
        mUserId = l;
        String s3;
        String s4;
        String s5;
        if(s != null)
        {
            if(s.length() > 0)
                s3 = s;
            else
                s3 = null;
        } else
        {
            s3 = null;
        }
        mEmail = s3;
        if(s1 != null)
        {
            if(s1.length() > 0)
                s4 = s1;
            else
                s4 = null;
        } else
        {
            s4 = null;
        }
        mCellPhone = s4;
        if(s2 != null)
        {
            if(s2.length() > 0)
                s5 = s2;
            else
                s5 = null;
        } else
        {
            s5 = null;
        }
        mOtherPhone = s5;
    }

    public String getCellPhone()
    {
        return mCellPhone;
    }

    public String getEmail()
    {
        return mEmail;
    }

    public String getOtherPhone()
    {
        return mOtherPhone;
    }

    public long getUserId()
    {
        return mUserId;
    }

    private final String mCellPhone;
    private final String mEmail;
    private final String mOtherPhone;
    private final long mUserId;
}
