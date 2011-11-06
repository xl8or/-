// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosGetPhotos.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlQuery, ApiMethodListener, PhotoSyncModel, ApiMethod

public class PhotosGetPhotos extends FqlQuery
{

    public PhotosGetPhotos(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1, String s2)
    {
        super(context, intent, s, buildQueryFromSourceQuery(s1, s2), apimethodlistener);
        mPhotos = new ArrayList();
        mIsLastQuery = true;
        mAlbumId = null;
    }

    public PhotosGetPhotos(Context context, Intent intent, String s, String s1, String as[], long l, 
            int i, int j, ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, buildQuery(s1, as, l, i, j), apimethodlistener);
        mAlbumId = s1;
        mPhotos = new ArrayList();
        boolean flag;
        if(j < 0)
            flag = true;
        else
            flag = false;
        mIsLastQuery = flag;
        if(i == 0 && mAlbumId != null)
        {
            mTempPhotos = new ArrayList();
            mTempAlbumId = mAlbumId;
        }
    }

    private static String buildQuery(String s, String as[], long l, int i, int j)
    {
        String s1;
        String s2;
        String s3;
        if(as != null)
            s1 = (new StringBuilder()).append("SELECT pid,aid,owner,src_small,src_big,src,caption,created,position FROM photo WHERE ").append("pid IN(").append(photoIdsParameterString(as)).append(")").toString();
        else
            s1 = (new StringBuilder()).append("SELECT pid,aid,owner,src_small,src_big,src,caption,created,position FROM photo WHERE ").append("aid='").append(s).append("'").toString();
        if(-1L != l)
            s1 = (new StringBuilder()).append(s1).append(" AND owner = ").append(l).toString();
        s2 = (new StringBuilder()).append(s1).append(" ORDER BY position ASC").toString();
        if(j >= 0)
            s3 = (new StringBuilder()).append(s2).append(" LIMIT ").append(i).append(",").append(j).toString();
        else
            s3 = (new StringBuilder()).append(s2).append(" LIMIT ").append(i).append(",").append(0xf4240).toString();
        return s3;
    }

    private static String buildQueryFromSourceQuery(String s, String s1)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("SELECT pid,aid,owner,src_small,src_big,src,caption,created,position").append(" FROM photo WHERE ").append("pid IN (SELECT ").append(s1).append(" FROM ").append(s).append(") ORDER BY position ASC");
        return stringbuilder.toString();
    }

    private static String listToCommaString(List list, boolean flag)
    {
        StringBuffer stringbuffer = new StringBuffer(64);
        boolean flag1 = true;
        for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            Object obj = iterator.next();
            if(!flag1)
                stringbuffer.append(",");
            else
                flag1 = false;
            if(flag)
                stringbuffer.append("'").append(obj).append("'");
            else
                stringbuffer.append(obj);
        }

        return stringbuffer.toString();
    }

    private static String photoIdsParameterString(String as[])
    {
        return listToCommaString(Arrays.asList(as), true);
    }

    protected void dispatchOnOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
    {
        mListener.onOperationComplete(apimethod, i, s, exception);
    }

    public List getPhotos()
    {
        return mPhotos;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
label0:
        {
            JsonToken jsontoken = jsonparser.getCurrentToken();
            if(jsontoken == JsonToken.START_OBJECT)
            {
                FacebookApiException facebookapiexception = new FacebookApiException(jsonparser);
                if(facebookapiexception.getErrorCode() != -1)
                    throw facebookapiexception;
            } else
            {
                if(jsontoken != JsonToken.START_ARRAY)
                    break label0;
                for(; jsontoken != JsonToken.END_ARRAY; jsontoken = jsonparser.nextToken())
                    if(jsontoken == JsonToken.START_OBJECT)
                        mPhotos.add(FacebookPhoto.parseJson(jsonparser));

                if(mAlbumId != null && mAlbumId.equals(mTempAlbumId))
                    mTempPhotos.addAll(mPhotos);
                boolean flag;
                boolean flag1;
                com.facebook.katana.util.Factory factory;
                if(mAlbumId != null)
                    flag = true;
                else
                    flag = false;
                if(flag && mIsLastQuery)
                    flag1 = true;
                else
                    flag1 = false;
                if(flag)
                    factory = PhotoSyncModel.cursorFactoryForPhotosForAlbum(mContext, mAlbumId);
                else
                    factory = PhotoSyncModel.cursorFactoryForPhotos(mContext, mPhotos);
                PhotoSyncModel.doSync(mContext, mPhotos, factory, true, true, false, mAlbumId);
                if(flag1)
                    PhotoSyncModel.doSync(mContext, mTempPhotos, factory, true, true, true, mAlbumId);
            }
            return;
        }
        throw new IOException("Malformed JSON");
    }

    private static final int MAX_PHOTOS = 0xf4240;
    private static final boolean SIMULATE_FAILURE;
    private static String mTempAlbumId;
    private static ArrayList mTempPhotos;
    private final String mAlbumId;
    private final boolean mIsLastQuery;
    private final List mPhotos;
}
