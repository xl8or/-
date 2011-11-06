// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosUpload.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPhoto;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, HttpOperation, ApiMethodListener

public class PhotosUpload extends ApiMethod
{
    private static interface AlbumQuery
    {

        public static final String ALBUM_PROJECTION[] = as;
        public static final int INDEX_SIZE;

        
        {
            String as[] = new String[1];
            as[0] = "size";
        }
    }

    protected class PhotosUploadHttpListener extends ApiMethod.ApiHttpListener
    {

        public void onHttpOperationProgress(HttpOperation httpoperation, final long position, final long length)
        {
            if(mListener != null)
                ApiMethod.mHandler.post(new Runnable() {

                    public void run()
                    {
                        if(mHttpOp != null)
                            mListener.onOperationProgress(_fld0, position, length);
                    }

                    final PhotosUploadHttpListener this$1;
                    final long val$length;
                    final long val$position;

                
                {
                    this$1 = PhotosUploadHttpListener.this;
                    position = l;
                    length = l1;
                    super();
                }
                }
);
        }

        final PhotosUpload this$0;

        protected PhotosUploadHttpListener()
        {
            this$0 = PhotosUpload.this;
            super(PhotosUpload.this);
        }
    }


    public PhotosUpload(Context context, Intent intent, String s, String s1, String s2, ApiMethodListener apimethodlistener, String s3, 
            long l, long l1, boolean flag, long l2, 
            String s4, long l3, String s5)
    {
        super(context, intent, "POST", "photos.upload", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener, null);
        mHttpListener = new PhotosUploadHttpListener();
        mPhotoUri = s2;
        mParams.put("method", mFacebookMethod);
        mParams.put("v", "1.0");
        mParams.put("api_key", "882a8490361da98702bf97a021ddc14d");
        mParams.put("format", "JSON");
        mParams.put("session_key", s);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        if(s1 != null)
            mParams.put("caption", s1);
        if(s3 != null)
            mParams.put("aid", s3);
        if(l != -1L)
            mParams.put("checkin_id", Long.toString(l));
        if(l1 != -1L)
            mParams.put("profile_id", Long.toString(l1));
        mParams.put("published", Boolean.toString(flag));
        if(l2 != -1L)
            mParams.put("place", Long.toString(l2));
        if(s4 != null && s4.length() > 0)
            mParams.put("tags", s4);
        if(l3 != -1L)
            mParams.put("target_id", Long.toString(l3));
        if(s5 != null && s5.length() > 0)
            mParams.put("privacy", s5);
    }

    private void adjustAlbum()
    {
        ContentResolver contentresolver = mContext.getContentResolver();
        Uri uri = Uri.withAppendedPath(PhotosProvider.ALBUMS_AID_CONTENT_URI, mPhoto.getAlbumId());
        Cursor cursor = contentresolver.query(uri, AlbumQuery.ALBUM_PROJECTION, null, null, null);
        if(cursor.moveToFirst())
        {
            ContentValues contentvalues = new ContentValues();
            contentvalues.put("size", Integer.valueOf(1 + cursor.getInt(0)));
            contentresolver.update(uri, contentvalues, null, null);
        }
        cursor.close();
    }

    private void insertPhoto()
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("pid", mPhoto.getPhotoId());
        contentvalues.put("owner", Long.valueOf(mPhoto.getOwnerId()));
        contentvalues.put("src", mPhoto.getSrc());
        contentvalues.put("src_big", mPhoto.getSrcBig());
        contentvalues.put("src_small", mPhoto.getSrcSmall());
        contentvalues.put("caption", mPhoto.getCaption());
        contentvalues.put("created", Long.valueOf(mPhoto.getCreated()));
        Uri uri = Uri.withAppendedPath(PhotosProvider.PHOTOS_AID_CONTENT_URI, mPhoto.getAlbumId());
        mContext.getContentResolver().insert(uri, contentvalues);
    }

    public long getFileSizeFromURI(Uri uri)
    {
        String as[] = new String[1];
        as[0] = "_size";
        Cursor cursor = mContext.getContentResolver().query(uri, as, null, null, null);
        int i = cursor.getColumnIndexOrThrow("_size");
        cursor.moveToFirst();
        return cursor.getLong(i);
    }

    public FacebookPhoto getPhoto()
    {
        return mPhoto;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        mPhoto = FacebookPhoto.parseJson(jsonparser);
        insertPhoto();
        adjustAlbum();
    }

    public void start()
    {
        HashMap hashmap;
        String s;
        hashmap = new HashMap();
        s = (new StringBuilder()).append(StringUtils.randomString(8)).append(".jpg").toString();
        if(!mPhotoUri.startsWith("content:")) goto _L2; else goto _L1
_L1:
        Long long2;
        Object obj;
        Uri uri = Uri.parse(mPhotoUri);
        obj = mContext.getContentResolver().openInputStream(uri);
        long2 = Long.valueOf(getFileSizeFromURI(uri));
_L4:
        hashmap.put("media", new InputStreamBody(((java.io.InputStream) (obj)), s));
        hashmap.put("method", new StringBody(mFacebookMethod));
        hashmap.put("v", new StringBody("1.0"));
        hashmap.put("api_key", new StringBody("882a8490361da98702bf97a021ddc14d"));
        hashmap.put("format", new StringBody((String)mParams.get("format")));
        hashmap.put("session_key", new StringBody((String)mParams.get("session_key")));
        hashmap.put("call_id", new StringBody((String)mParams.get("call_id")));
        hashmap.put("sig", new StringBody(buildSignature()));
        if(mParams.get("caption") != null)
            hashmap.put("caption", new StringBody((String)mParams.get("caption"), Charset.forName("UTF-8")));
        if(mParams.get("aid") != null)
            hashmap.put("aid", new StringBody((String)mParams.get("aid")));
        if(mParams.get("profile_id") != null)
            hashmap.put("profile_id", new StringBody((String)mParams.get("profile_id")));
        if(mParams.get("checkin_id") != null)
            hashmap.put("checkin_id", new StringBody((String)mParams.get("checkin_id")));
        hashmap.put("published", new StringBody((String)mParams.get("published")));
        if(mParams.get("place") != null)
            hashmap.put("place", new StringBody((String)mParams.get("place")));
        if(mParams.get("tags") != null)
            hashmap.put("tags", new StringBody((String)mParams.get("tags")));
        if(mParams.get("target_id") != null)
            hashmap.put("target_id", new StringBody((String)mParams.get("target_id")));
        if(mParams.get("privacy") != null)
            hashmap.put("privacy", new StringBody((String)mParams.get("privacy")));
        mHttpOp = new HttpOperation(mContext, com.facebook.katana.Constants.URL.getApiUrl(mContext), hashmap, long2, new ByteArrayOutputStream(16384), mHttpListener, true);
        mHttpOp.start();
          goto _L3
_L2:
        FileInputStream fileinputstream = new FileInputStream(mPhotoUri);
        Long long1 = Long.valueOf((new File(mPhotoUri)).length());
        long2 = long1;
        obj = fileinputstream;
          goto _L4
        Exception exception;
        exception;
        Exception exception1 = exception;
_L5:
        if(mListener != null)
            mListener.onOperationComplete(this, 0, null, exception1);
        break; /* Loop/switch isn't completed */
        Exception exception2;
        exception2;
        exception1 = exception2;
        if(true) goto _L5; else goto _L3
_L3:
    }

    public static final String AT_PLACE = "place";
    public static final String PHOTO_PUBLISH_PARAM = "published";
    public static final String PRIVACY = "privacy";
    public static final String PROFILE_ID_PARAM = "profile_id";
    public static final String TAGS = "tags";
    public static final String TARGET_ID = "target_id";
    private FacebookPhoto mPhoto;
    private final String mPhotoUri;
}
