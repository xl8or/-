// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AuthLogout.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.UserTask;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.provider.FacebookDatabaseHelper;
import com.facebook.katana.util.PlatformUtils;
import java.util.Map;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class AuthLogout extends ApiMethod
{
    private class ClearAccountDataTask extends UserTask
    {

        protected void doInBackground()
        {
            FacebookDatabaseHelper.clearPrivateData(mContext);
            if(PlatformUtils.platformStorageSupported(mContext))
                FacebookAuthenticationService.removeSessionInfo(mContext, null);
        }

        protected void onPostExecute()
        {
            mUserListener.onOperationComplete(AuthLogout.this, mErrorCode, mReasonPhrase, mEx);
        }

        private final int mErrorCode;
        private final Exception mEx;
        private final String mReasonPhrase;
        final AuthLogout this$0;

        public ClearAccountDataTask(int i, String s, Exception exception)
        {
            this$0 = AuthLogout.this;
            super(ApiMethod.mHandler);
            mErrorCode = i;
            mReasonPhrase = s;
            mEx = exception;
        }
    }

    private class LogoutListener
        implements ApiMethodListener
    {

        public void onOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
        {
            (new ClearAccountDataTask(i, s, exception)).execute();
        }

        public void onOperationProgress(ApiMethod apimethod, long l, long l1)
        {
        }

        public void onProcessComplete(ApiMethod apimethod, int i, String s, Exception exception)
        {
        }

        final AuthLogout this$0;

        private LogoutListener()
        {
            this$0 = AuthLogout.this;
            super();
        }

    }


    public AuthLogout(Context context, Intent intent, String s, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "auth.logout", com.facebook.katana.Constants.URL.getApiUrl(context), null);
        mListener = new LogoutListener();
        mUserListener = apimethodlistener;
        mParams.put("call_id", Long.toString(System.currentTimeMillis()));
        mParams.put("session_key", s);
    }

    public void start()
    {
        if(mParams.get("session_key") != null)
            super.start();
        else
            (new ClearAccountDataTask(200, "Ok", null)).execute();
    }

    private final ApiMethodListener mUserListener;

}
