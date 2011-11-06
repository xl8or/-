// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApiMethod.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.*;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FBJsonFactory, HttpOperation, ApiMethodListener, ApiLogging

public class ApiMethod
{
    protected class ApiHttpListener
        implements HttpOperation.HttpOperationListener
    {

        public void onHttpOperationComplete(HttpOperation httpoperation, int i, String s, OutputStream outputstream, Exception exception)
        {
            int j = 0;
            if(i == 200 && exception == null)
            {
                j = ((ByteArrayOutputStream)outputstream).size();
                try
                {
                    String s1 = new String(((ByteArrayOutputStream)outputstream).toByteArray());
                    parseResponse(s1);
                }
                catch(FacebookApiException facebookapiexception)
                {
                    Log.e("ApiMethod.onHttpOperationComplete", (new StringBuilder()).append("FacebookApiException: ").append(facebookapiexception.getErrorCode()).append("/").append(facebookapiexception.getErrorMsg()).toString());
                    i = 0;
                    s = null;
                    exception = facebookapiexception;
                }
                catch(Exception exception1)
                {
                    exception1.printStackTrace();
                    i = 0;
                    s = null;
                    exception = exception1;
                }
                catch(OutOfMemoryError outofmemoryerror)
                {
                    ((ByteArrayOutputStream)outputstream).reset();
                    Utils.reportSoftError("ApiMethod OutOfMemoryError", buildApiCallInfo());
                }
            }
            onHttpComplete(i, s, exception);
            if(ApiLogging.reportAndCheckApi(exception))
                ApiLogging.logApiResponse(mContext, generateLogParams(), httpoperation.calculateTimeElapsed(), j, exception);
        }

        public void onHttpOperationProgress(HttpOperation httpoperation, long l, long l1)
        {
        }

        final ApiMethod this$0;

        protected ApiHttpListener()
        {
            this$0 = ApiMethod.this;
            super();
        }
    }


    protected ApiMethod(Context context, Intent intent, String s, String s1, String s2, ApiMethodListener apimethodlistener)
    {
        mParams = new TreeMap();
        mContext = context;
        mReqIntent = intent;
        mHttpMethod = s;
        mFacebookMethod = s1;
        mBaseUrl = s2;
        mListener = apimethodlistener;
        mHttpListener = new ApiHttpListener();
    }

    protected ApiMethod(Context context, Intent intent, String s, String s1, String s2, ApiMethodListener apimethodlistener, HttpOperation.HttpOperationListener httpoperationlistener)
    {
        mParams = new TreeMap();
        mContext = context;
        mReqIntent = intent;
        mHttpMethod = s;
        mFacebookMethod = s1;
        mBaseUrl = s2;
        mListener = apimethodlistener;
        mHttpListener = httpoperationlistener;
    }

    private String buildApiCallInfo()
    {
        return generateLogParams().substring(1);
    }

    public static boolean isSessionKeyError(int i, Exception exception)
    {
        boolean flag;
        if(exception != null && (exception instanceof FacebookApiException) && ((FacebookApiException)exception).getErrorCode() == 102)
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected static void printJson(String s)
    {
    }

    protected static String removeChar(String s, char c)
    {
        StringBuffer stringbuffer = new StringBuffer(128);
        for(int i = 0; i < s.length(); i++)
            if(s.charAt(i) != c)
                stringbuffer.append(s.charAt(i));

        return stringbuffer.toString();
    }

    public void addAuthenticationData(FacebookSessionInfo facebooksessioninfo)
    {
        if(!$assertionsDisabled && facebooksessioninfo == null)
            throw new AssertionError();
        if(!$assertionsDisabled && facebooksessioninfo.sessionKey == null)
        {
            throw new AssertionError();
        } else
        {
            mParams.put("session_key", facebooksessioninfo.sessionKey);
            return;
        }
    }

    protected void addCommonParameters()
    {
        mParams.put("api_key", "882a8490361da98702bf97a021ddc14d");
        mParams.put("format", "JSON");
        mParams.put("method", mFacebookMethod);
        mParams.put("v", "1.0");
        mParams.put("migrations_override", "{'empty_json': true}");
        mParams.put("return_ssl_resources", "0");
        mParams.put("locale", "user");
    }

    public void addIntentAndListener(Intent intent, ApiMethodListener apimethodlistener)
    {
        if(!$assertionsDisabled && (mReqIntent != null || mListener != null))
            throw new AssertionError();
        if(!$assertionsDisabled && (intent == null || apimethodlistener == null))
        {
            throw new AssertionError();
        } else
        {
            mReqIntent = intent;
            mListener = apimethodlistener;
            return;
        }
    }

    protected void addSignature()
        throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        mParams.put("sig", buildSignature());
    }

    protected String buildGETUrl(String s)
        throws UnsupportedEncodingException
    {
        StringBuilder stringbuilder = new StringBuilder(s);
        StringBuilder stringbuilder1 = buildQueryString();
        String s1;
        if(stringbuilder1.length() == 0)
        {
            String s2 = getClass().getName();
            String s3;
            if("ApiMethod" != s2)
            {
                StringBuffer stringbuffer = new StringBuffer("ApiMethod");
                stringbuffer.append("(");
                stringbuffer.append(s2);
                stringbuffer.append(")");
                s3 = stringbuffer.toString();
            } else
            {
                s3 = "ApiMethod";
            }
            Log.e(s3, "We always should have something in the query (e.g., the signature)");
            s1 = s;
        } else
        {
            stringbuilder.append("?");
            stringbuilder.append(stringbuilder1);
            s1 = stringbuilder.toString();
        }
        return s1;
    }

    protected StringBuilder buildQueryString()
    {
        return URLQueryBuilder.buildQueryString(mParams);
    }

    protected String buildSignature()
        throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        return URLQueryBuilder.buildSignature(signatureString());
    }

    public void cancel(boolean flag)
    {
        if(mHttpOp != null)
        {
            mHttpOp.cancel();
            if(flag)
                dispatchOnOperationComplete(this, 0, null, null);
            mHttpOp = null;
        }
    }

    protected void dispatchOnOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
    {
        Object obj = exception;
        if(simulateSessionKeyError())
            obj = new FacebookApiException(102, "Invalid credentials");
        mListener.onOperationComplete(apimethod, i, s, ((Exception) (obj)));
    }

    protected String generateLogParams()
    {
        StringBuilder stringbuilder = new StringBuilder(500);
        stringbuilder.append(",\"method\":\"");
        stringbuilder.append(mFacebookMethod);
        return stringbuilder.toString();
    }

    public Intent getIntent()
    {
        return mReqIntent;
    }

    protected void onComplete(final int errorCode, final String reasonPhrase, final Exception ex)
    {
        if(mListener != null)
        {
            mListener.onProcessComplete(this, errorCode, reasonPhrase, ex);
            mHandler.post(new Runnable() {

                public void run()
                {
                    int i = errorCode;
                    Object obj = ex;
                    if(simulateSessionKeyError())
                        obj = new FacebookApiException(102, "Invalid credentials");
                    mListener.onOperationComplete(ApiMethod.this, i, reasonPhrase, ((Exception) (obj)));
                }

                final ApiMethod this$0;
                final int val$errorCode;
                final Exception val$ex;
                final String val$reasonPhrase;

            
            {
                this$0 = ApiMethod.this;
                errorCode = i;
                ex = exception;
                reasonPhrase = s;
                super();
            }
            }
);
        }
    }

    protected void onHttpComplete(final int errorCode, final String reasonPhrase, final Exception ex)
    {
        if(mListener != null)
        {
            mListener.onProcessComplete(this, errorCode, reasonPhrase, ex);
            mHandler.post(new Runnable() {

                public void run()
                {
                    if(mHttpOp != null)
                    {
                        mHttpOp = null;
                        dispatchOnOperationComplete(ApiMethod.this, errorCode, reasonPhrase, ex);
                    }
                }

                final ApiMethod this$0;
                final int val$errorCode;
                final Exception val$ex;
                final String val$reasonPhrase;

            
            {
                this$0 = ApiMethod.this;
                errorCode = i;
                reasonPhrase = s;
                ex = exception;
                super();
            }
            }
);
        }
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
    }

    protected void parseResponse(String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        printJson(s);
        JsonParser jsonparser = mJsonFactory.createJsonParser(s);
        jsonparser.nextToken();
        parseJSON(jsonparser);
    }

    protected String signatureKey()
    {
        return mReqIntent.getStringExtra("ApiMethod.secret");
    }

    protected String signatureString()
    {
        return URLQueryBuilder.signatureString(mParams, signatureKey());
    }

    protected boolean simulateSessionKeyError()
    {
        return false;
    }

    public void start()
    {
        addCommonParameters();
        addSignature();
        if(!mHttpMethod.equals("GET")) goto _L2; else goto _L1
_L1:
        mHttpOp = new HttpOperation(mContext, mHttpMethod, buildGETUrl(mBaseUrl), new ByteArrayOutputStream(8192), mHttpListener, true);
_L4:
        mHttpOp.start();
        break; /* Loop/switch isn't completed */
_L2:
        if(mHttpMethod.equals("POST"))
        {
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(buildQueryString().toString().getBytes("UTF-8"));
            mHttpOp = new HttpOperation(mContext, mBaseUrl, bytearrayinputstream, new ByteArrayOutputStream(8192), "application/x-www-form-urlencoded", mHttpListener, true);
        }
        if(true) goto _L4; else goto _L3
        Exception exception;
        exception;
        exception.printStackTrace();
        if(mListener != null)
            mListener.onOperationComplete(this, 0, null, exception);
_L3:
    }

    static final boolean $assertionsDisabled = false;
    protected static final String ALBUM_ID_PARAM = "aid";
    public static final int API_EC_PARAM_SESSION_KEY = 102;
    public static final String API_KEY_PARAM = "api_key";
    public static final String API_VERSION = "1.0";
    public static final String APPLICATION_API_KEY = "882a8490361da98702bf97a021ddc14d";
    protected static final String APP_SECRET = "62f8ce9f74b12f84c123cc23437a4a32";
    protected static final String BODY_PARAM = "body";
    public static final String CALL_ID_PARAM = "call_id";
    protected static final String CAPTION_PARAM = "caption";
    protected static final String CHECKIN_ID_PARAM = "checkin_id";
    protected static final String CONFIRM_PARAM = "confirm";
    protected static final String COUNTRY_CODE_PARAM = "country_code";
    protected static final String DESCRIPTION_PARAM = "description";
    protected static final String EID_PARAM = "eid";
    protected static final String EMAIL_PARAM = "email";
    protected static final String ERROR_CODE = "error_code";
    protected static final String ERROR_MSG = "error_msg";
    public static final String EXTRA_SESSION_SECRET = "ApiMethod.secret";
    public static final long FACEBOOK_APP_ID = 0xa67c8e50L;
    protected static final String FOLDER_PARAM = "folder";
    protected static final String FORMAT_PARAM = "format";
    protected static final String JSON_FORMAT = "JSON";
    protected static final String LIMIT_PARAM = "limit";
    protected static final String LOCATION_PARAM = "location";
    protected static final String MESSAGE_PARAM = "message";
    public static final String METHOD_PARAM = "method";
    protected static final String MIGRATIONS_OVERRIDE_PARAM = "migrations_override";
    protected static final String NAME_PARAM = "name";
    protected static final String PAGE_ID_PARAM = "page_id";
    protected static final String PASSWORD_PARAM = "password";
    protected static final String PHOTO_IDS_PARAM = "pids";
    protected static final String PHOTO_ID_PARAM = "pid";
    protected static final String POST_ID_PARAM = "post_id";
    protected static final String PUSH_PROTOCOL_PARAMS = "protocol_params";
    protected static final String PUSH_SETTINGS_PARAM = "settings";
    protected static final String QUERIES_PARAM = "queries";
    protected static final String QUERY_PARAM = "query";
    protected static final String RSVP_STATUS_PARAM = "rsvp_status";
    public static final String SESSION_KEY_PARAM = "session_key";
    public static final String SIG_PARAM = "sig";
    protected static final String SOURCE_IDS_PARAM = "source_ids";
    protected static final String SSL_RESOURCES_PARAM = "return_ssl_resources";
    protected static final String START_PARAM = "start";
    protected static final String SUBJECT_PARAM = "subject";
    private static final String TAG = "ApiMethod";
    protected static final String TAGGED_UIDS_PARAM = "tagged_uids";
    protected static final String TAGS_PARAM = "tags";
    protected static final String THREAD_ID_PARAM = "tid";
    protected static final String TITLE_PARAM = "title";
    protected static final String UIDS_PARAM = "uids";
    public static final String UID_PARAM = "uid";
    public static final String VERSION_PARAM = "v";
    protected static final String VIEWER_ID_PARAM = "viewer_id";
    protected static final String VISIBILITY_PARAM = "visible";
    protected static final Handler mHandler = new Handler();
    protected static final FBJsonFactory mJsonFactory = new FBJsonFactory();
    protected final String mBaseUrl;
    protected final Context mContext;
    protected final String mFacebookMethod;
    protected HttpOperation.HttpOperationListener mHttpListener;
    protected final String mHttpMethod;
    protected HttpOperation mHttpOp;
    protected ApiMethodListener mListener;
    protected final Map mParams;
    protected Intent mReqIntent;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/ApiMethod.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }

}
