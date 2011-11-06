// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookVideo.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.model:
//            FacebookApiException

public class FacebookVideo extends JMCachingDictDestination
{
    public static final class VideoSource extends Enum
    {

        public static VideoSource valueOf(String s)
        {
            return (VideoSource)Enum.valueOf(com/facebook/katana/model/FacebookVideo$VideoSource, s);
        }

        public static VideoSource[] values()
        {
            return (VideoSource[])$VALUES.clone();
        }

        private static final VideoSource $VALUES[];
        public static final VideoSource SOURCE_HTML;
        public static final VideoSource SOURCE_RAW;

        static 
        {
            SOURCE_HTML = new VideoSource("SOURCE_HTML", 0);
            SOURCE_RAW = new VideoSource("SOURCE_RAW", 1);
            VideoSource avideosource[] = new VideoSource[2];
            avideosource[0] = SOURCE_HTML;
            avideosource[1] = SOURCE_RAW;
            $VALUES = avideosource;
        }

        private VideoSource(String s, int i)
        {
            super(s, i);
        }
    }


    public FacebookVideo()
    {
    }

    public static FacebookVideo parseJson(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        return (FacebookVideo)JMParser.parseObjectJson(jsonparser, com/facebook/katana/model/FacebookVideo);
    }

    public String getDisplayUrl()
    {
        return mDisplayUrl;
    }

    public VideoSource getSourceType()
    {
        return mSourceType;
    }

    public String getSourceUrl()
    {
        return mSourceUrl;
    }

    public void setString(String s, String s1)
        throws JMException
    {
        if(s.equals("mSourceType_internal"))
        {
            if(s1.equals("raw"))
                setObject("mSourceType", VideoSource.SOURCE_RAW);
            else
                setObject("mSourceType", VideoSource.SOURCE_HTML);
        } else
        {
            super.setString(s, s1);
        }
    }

    private String mDisplayUrl;
    private VideoSource mSourceType;
    private String mSourceType_internal;
    private String mSourceUrl;
}
