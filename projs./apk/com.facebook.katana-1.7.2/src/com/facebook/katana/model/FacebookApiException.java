// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookApiException.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FacebookApiException extends Exception
{
    protected static class ServerExceptionData extends JMCachingDictDestination
    {

        final int mErrorCode;
        final String mErrorMsg;

        protected ServerExceptionData()
        {
            mErrorCode = -1;
            mErrorMsg = null;
        }

        public ServerExceptionData(int i, String s)
        {
            mErrorCode = i;
            mErrorMsg = s;
        }
    }


    protected FacebookApiException()
    {
    }

    public FacebookApiException(int i, String s)
    {
        mData = new ServerExceptionData(i, s);
    }

    public FacebookApiException(JsonParser jsonparser)
        throws JsonParseException, IOException, JMException
    {
        mData = (ServerExceptionData)JMParser.parseObjectJson(jsonparser, com/facebook/katana/model/FacebookApiException$ServerExceptionData);
    }

    public int getErrorCode()
    {
        return mData.mErrorCode;
    }

    public String getErrorMsg()
    {
        return mData.mErrorMsg;
    }

    protected ServerExceptionData mData;
}
