// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VideoUpload.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.*;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, HttpOperation, ApiMethodListener

public class VideoUpload extends ApiMethod
    implements ApiMethodCallback
{
    protected class VideoUploadListener extends ApiMethod.ApiHttpListener
    {

        public void onHttpOperationProgress(HttpOperation httpoperation, final long position, final long length)
        {
            if(mListener != null)
                ApiMethod.mHandler.post(new Runnable() {

                    public void run()
                    {
                        if(mHttpOp != null)
                            ServiceNotificationManager.updateProgressNotification(mContext, Integer.parseInt(VideoUpload.mReqId), (int)((100L * position) / length));
                    }

                    final VideoUploadListener this$1;
                    final long val$length;
                    final long val$position;

                
                {
                    this$1 = VideoUploadListener.this;
                    position = l;
                    length = l1;
                    super();
                }
                }
);
        }

        final VideoUpload this$0;

        protected VideoUploadListener()
        {
            this$0 = VideoUpload.this;
            super(VideoUpload.this);
        }
    }


    public VideoUpload(Context context, Intent intent, String s, String s1, String s2, String s3, long l, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "POST", "video.upload", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener, null);
        mHttpListener = new VideoUploadListener();
        mVideoUri = s3;
        mParams.put("method", mFacebookMethod);
        mParams.put("v", "1.0");
        mParams.put("api_key", "882a8490361da98702bf97a021ddc14d");
        mParams.put("format", "JSON");
        mParams.put("session_key", s);
        mParams.put("call_id", String.valueOf(System.currentTimeMillis()));
        if(l != -1L)
            mParams.put("id", String.valueOf(l));
        if(s1 != null)
            mParams.put("title", s1);
        if(s2 != null)
            mParams.put("description", s2);
    }

    public static String RequestVideoUpload(Context context, String s, String s1, String s2, long l)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        mReqId = appsession.postToService(context, new VideoUpload(context, null, appsession.getSessionInfo().sessionKey, s, s1, s2, l, null), 1001, 1020, null);
        ServiceNotificationManager.beginVideoUploadProgressNotification(context, Integer.parseInt(mReqId), s, s1, s2);
        return mReqId;
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        if(!ServiceNotificationManager.endVideoUploadProgressNotification(context, Integer.parseInt(mReqId), i, mVideoUri))
            (new File(mVideoUri)).delete();
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onVideoUploadComplete(appsession, s, i, s1, exception, mVideoResponse, mVideoUri));
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

    public String getRealPathFromURI(Uri uri)
    {
        String as[] = new String[1];
        as[0] = "_data";
        Cursor cursor = mContext.getContentResolver().query(uri, as, null, null, null);
        int i = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        return cursor.getString(i);
    }

    public FacebookVideoUploadResponse getVideoUploadResponse()
    {
        return mVideoResponse;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        mVideoResponse = FacebookVideoUploadResponse.parseJson(jsonparser);
    }

    public void start()
    {
        HashMap hashmap;
        String s2;
        hashmap = new HashMap();
        String s = getRealPathFromURI(Uri.parse(mVideoUri));
        String s1 = ".3gp";
        int i = s.lastIndexOf(".");
        if(i != -1)
            s1 = s.substring(i, s.length());
        s2 = (new StringBuilder()).append(StringUtils.randomString(8)).append(s1).toString();
        if(!mVideoUri.startsWith("content:")) goto _L2; else goto _L1
_L1:
        Long long2;
        Object obj;
        Uri uri = Uri.parse(mVideoUri);
        obj = mContext.getContentResolver().openInputStream(uri);
        long2 = Long.valueOf(getFileSizeFromURI(uri));
_L4:
        InputStreamBody inputstreambody = new InputStreamBody(((java.io.InputStream) (obj)), s2);
        hashmap.put("media", inputstreambody);
        hashmap.put("method", new StringBody(mFacebookMethod));
        hashmap.put("v", new StringBody("1.0"));
        hashmap.put("api_key", new StringBody("882a8490361da98702bf97a021ddc14d"));
        hashmap.put("format", new StringBody((String)mParams.get("format")));
        hashmap.put("session_key", new StringBody((String)mParams.get("session_key")));
        hashmap.put("call_id", new StringBody((String)mParams.get("call_id")));
        hashmap.put("sig", new StringBody(buildSignature()));
        if(mParams.get("id") != null)
            hashmap.put("id", new StringBody((String)mParams.get("id")));
        if(mParams.get("title") != null)
            hashmap.put("title", new StringBody((String)mParams.get("title"), Charset.forName("UTF-8")));
        if(mParams.get("description") != null)
            hashmap.put("description", new StringBody((String)mParams.get("description"), Charset.forName("UTF-8")));
        mHttpOp = new HttpOperation(mContext, com.facebook.katana.Constants.URL.getApiVideoUrl(mContext), hashmap, long2, new ByteArrayOutputStream(16384), mHttpListener, true);
        mHttpOp.start();
          goto _L3
_L2:
        FileInputStream fileinputstream = new FileInputStream(mVideoUri);
        Long long1 = Long.valueOf((new File(mVideoUri)).length());
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

    private static final String ID_PARAM = "id";
    private static String mReqId;
    private FacebookVideoUploadResponse mVideoResponse;
    private final String mVideoUri;

}
