// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetAlbums.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlMultiQuery, PhotosGetAlbums, PhotosGetPhotos, AlbumSyncModel, 
//            ApiMethodListener

public class FqlGetAlbums extends FqlMultiQuery
{

    public FqlGetAlbums(Context context, Intent intent, String s, long l, String as[], ApiMethodListener apimethodlistener, 
            long l1, long l2)
    {
        super(context, intent, s, buildQueries(context, intent, s, l, as, l1, l2), apimethodlistener);
    }

    protected static LinkedHashMap buildQueries(Context context, Intent intent, String s, long l, String as[], long l1, 
            long l2)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        String s1;
        if(as != null && as.length > 0)
        {
            StringBuilder stringbuilder = new StringBuilder();
            StringUtils.join(stringbuilder, ",", StringUtils.FQLEscaper, (Object[])as);
            s1 = stringbuilder.toString();
        } else
        {
            s1 = null;
        }
        linkedhashmap.put("album_info", new PhotosGetAlbums(context, intent, s, l, s1, null, l1, l2));
        linkedhashmap.put("album_cover", new PhotosGetPhotos(context, intent, s, null, "#album_info", "cover_pid"));
        return linkedhashmap;
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        super.parseJSON(jsonparser, s);
        PhotosGetAlbums photosgetalbums = (PhotosGetAlbums)getQueryByName("album_info");
        PhotosGetPhotos photosgetphotos = (PhotosGetPhotos)getQueryByName("album_cover");
        mAlbums = photosgetalbums.getAlbums();
        AlbumSyncModel.assignCoversToAlbums(photosgetphotos.getPhotos(), mAlbums);
    }

    protected List mAlbums;
}
