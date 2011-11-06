// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesCreateException.java

package com.facebook.katana.service.method;

import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class PlacesCreateException extends FacebookApiException
{
    protected static class ErrorData extends JMCachingDictDestination
    {

        List mSimilarPlaces;

        protected ErrorData()
        {
        }
    }

    protected static class PlacesServerExceptionData extends com.facebook.katana.model.FacebookApiException.ServerExceptionData
    {

        ErrorData mErrorData;

        protected PlacesServerExceptionData()
        {
        }
    }

    public static class SimilarPlace extends JMCachingDictDestination
    {

        public final long mId = -1L;
        public final String mName = null;

        public SimilarPlace()
        {
        }
    }


    public PlacesCreateException(JsonParser jsonparser)
        throws JsonParseException, IOException, JMException
    {
        mData = (com.facebook.katana.model.FacebookApiException.ServerExceptionData)JMParser.parseObjectJson(jsonparser, com/facebook/katana/service/method/PlacesCreateException$PlacesServerExceptionData);
        if(!$assertionsDisabled && mData == null)
            throw new AssertionError();
        else
            return;
    }

    public List getSimilarPlaces()
    {
        return ((PlacesServerExceptionData)mData).mErrorData.mSimilarPlaces;
    }

    static final boolean $assertionsDisabled;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/PlacesCreateException.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
