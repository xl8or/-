// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AuthLogin.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.UserTask;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.provider.FacebookDatabaseHelper;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class AuthLogin extends ApiMethod
{
    protected static class FacebookApiSessionInfo extends FacebookSessionInfo
    {

        public final int mErrorCode = -1;
        public final String mErrorMsg = null;

        protected FacebookApiSessionInfo()
        {
        }
    }

    private class ClearAccountDataTask extends UserTask
    {

        protected void doInBackground()
        {
            boolean flag = true;
            boolean flag1;
            String s = UserValuesManager.loadActiveSessionInfo(mContext);
            if(s == null)
                break MISSING_BLOCK_LABEL_49;
            String s1 = FacebookSessionInfo.parseFromJson(s).username;
            if(s1 == null)
                break MISSING_BLOCK_LABEL_49;
            flag1 = s1.equals(mUsername);
            if(!flag1)
                flag = true;
            else
                flag = false;
_L2:
            if(flag)
            {
                FacebookDatabaseHelper.clearPrivateData(mContext);
                if(PlatformUtils.platformStorageSupported(mContext))
                    FacebookAuthenticationService.removeSessionInfo(mContext, mUsername);
            }
            return;
            Throwable throwable;
            throwable;
            if(true) goto _L2; else goto _L1
_L1:
        }

        protected void onPostExecute()
        {
            mUserListener.onOperationComplete(AuthLogin.this, mErrorCode, mReasonPhrase, mEx);
        }

        private final int mErrorCode;
        private final Exception mEx;
        private final String mReasonPhrase;
        private final String mUsername;
        final AuthLogin this$0;

        public ClearAccountDataTask(String s, int i, String s1, Exception exception)
        {
            this$0 = AuthLogin.this;
            super(ApiMethod.mHandler);
            mUsername = s;
            mErrorCode = i;
            mReasonPhrase = s1;
            mEx = exception;
        }
    }

    private class LoginListener
        implements ApiMethodListener
    {

        public void onOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
        {
            (new ClearAccountDataTask((String)mParams.get("email"), i, s, exception)).execute();
        }

        public void onOperationProgress(ApiMethod apimethod, long l, long l1)
        {
        }

        public void onProcessComplete(ApiMethod apimethod, int i, String s, Exception exception)
        {
        }

        final AuthLogin this$0;

        private LoginListener()
        {
            this$0 = AuthLogin.this;
            super();
        }

    }


    public AuthLogin(Context context, Intent intent, String s, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "auth.login", com.facebook.katana.Constants.URL.getApiUrl(context), null);
        mParams.put("email", s);
        mParams.put("password", s1);
        mListener = new LoginListener();
        mUserListener = apimethodlistener;
    }

    public FacebookSessionInfo getSessionInfo()
    {
        return mSessionInfo;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        FacebookApiSessionInfo facebookapisessioninfo = (FacebookApiSessionInfo)JMParser.parseObjectJson(jsonparser, com/facebook/katana/service/method/AuthLogin$FacebookApiSessionInfo);
        if(facebookapisessioninfo.mErrorCode != -1)
            throw new FacebookApiException(facebookapisessioninfo.mErrorCode, facebookapisessioninfo.mErrorMsg);
        if(facebookapisessioninfo.sessionKey != null && facebookapisessioninfo.userId != -1L)
        {
            mSessionInfo = facebookapisessioninfo;
            mSessionInfo.setString("username", (String)mParams.get("email"));
            return;
        } else
        {
            throw new IOException("Session info not found");
        }
    }

    protected String signatureKey()
    {
        return "62f8ce9f74b12f84c123cc23437a4a32";
    }

    protected boolean simulateSessionKeyError()
    {
        return false;
    }

    protected static final int INVALID_ERROR_CODE = -1;
    private FacebookSessionInfo mSessionInfo;
    private final ApiMethodListener mUserListener;

}
