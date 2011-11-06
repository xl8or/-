// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractContentBody.java

package org.apache.http.entity.mime.content;


// Referenced classes of package org.apache.http.entity.mime.content:
//            ContentBody

public abstract class AbstractContentBody
    implements ContentBody
{

    public AbstractContentBody(String s)
    {
        if(s == null)
            throw new IllegalArgumentException("MIME type may not be null");
        mimeType = s;
        int i = s.indexOf('/');
        if(i != -1)
        {
            mediaType = s.substring(0, i);
            subType = s.substring(i + 1);
        } else
        {
            mediaType = s;
            subType = null;
        }
    }

    public String getMediaType()
    {
        return mediaType;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public String getSubType()
    {
        return subType;
    }

    private final String mediaType;
    private final String mimeType;
    private final String subType;
}
