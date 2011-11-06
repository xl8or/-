// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosGetAlbums.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlQuery, ApiMethodListener

public class PhotosGetAlbums extends FqlQuery
{

    public PhotosGetAlbums(Context context, Intent intent, String s, long l, String s1, ApiMethodListener apimethodlistener, 
            long l1, long l2)
    {
        super(context, intent, s, buildQuery(l, s1, l1, l2), apimethodlistener);
    }

    private static String buildQuery(long l, String s, long l1, long l2)
    {
        String s1;
        String s2;
        String s3;
        if(s != null)
            s1 = (new StringBuilder()).append("SELECT aid,owner,cover_pid,name,created,modified,description,location,size,link,visible,type,object_id FROM album WHERE ").append("aid IN(").append(s).append(")").toString();
        else
            s1 = (new StringBuilder()).append("SELECT aid,owner,cover_pid,name,created,modified,description,location,size,link,visible,type,object_id FROM album WHERE ").append("owner=").append(l).toString();
        s2 = (new StringBuilder()).append(s1).append(" ORDER BY created DESC").toString();
        if(l2 > 0L)
            s3 = (new StringBuilder()).append(s2).append(" LIMIT ").append(l1).append(",").append(l2).toString();
        else
            s3 = (new StringBuilder()).append(s2).append(" LIMIT ").append(l1).append(",").append(0xf4240L).toString();
        return s3;
    }

    public List getAlbums()
    {
        return mAlbums;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_OBJECT)
        {
            FacebookApiException facebookapiexception = new FacebookApiException(jsonparser);
            if(facebookapiexception.getErrorCode() != -1)
                throw facebookapiexception;
        } else
        if(jsontoken == JsonToken.START_ARRAY)
            for(; jsontoken != JsonToken.END_ARRAY; jsontoken = jsonparser.nextToken())
                if(jsontoken == JsonToken.START_OBJECT)
                    mAlbums.add(new FacebookAlbum(jsonparser));

        else
            throw new IOException("Malformed JSON");
    }

    private static final long MAX_ALBUMS = 0xf4240L;
    private final List mAlbums = new ArrayList();
}
