// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileImageDownload.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.provider.ConnectionsProvider;
import java.io.*;
import java.net.URI;
import org.apache.http.client.methods.HttpRequestBase;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiLogging, HttpOperation, ApiMethodListener

public class ProfileImageDownload extends ApiMethod
    implements HttpOperation.HttpOperationListener
{

    public ProfileImageDownload(Context context, Intent intent, long l, String s, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", null, s, apimethodlistener);
        id = l;
        filename = s1;
    }

    public ProfileImage getProfileImage()
    {
        return mProfileImage;
    }

    public void onHttpOperationComplete(HttpOperation httpoperation, int i, String s, OutputStream outputstream, Exception exception)
    {
        File file;
        long l;
        final Exception fex;
        final int fErrorCode;
        final String fReasonPhrase;
        if(i == 200)
            try
            {
                mProfileImage = ConnectionsProvider.updateImage(mContext, id, mBaseUrl, filename);
            }
            catch(IOException ioexception)
            {
                exception = ioexception;
                i = 0;
                s = null;
            }
        file = new File(filename);
        l = file.length();
        file.delete();
        fex = exception;
        fErrorCode = i;
        fReasonPhrase = s;
        if(ApiLogging.reportAndCheckTrx(i))
            ApiLogging.logTransferResponse(mContext, httpoperation.mHttpMethod.getURI().toString(), httpoperation.calculateTimeElapsed(), l, fErrorCode);
        mHandler.post(new Runnable() {

            public void run()
            {
                if(mHttpOp != null)
                {
                    mHttpOp = null;
                    mListener.onOperationComplete(ProfileImageDownload.this, fErrorCode, fReasonPhrase, fex);
                }
            }

            final ProfileImageDownload this$0;
            final int val$fErrorCode;
            final String val$fReasonPhrase;
            final Exception val$fex;

            
            {
                this$0 = ProfileImageDownload.this;
                fErrorCode = i;
                fReasonPhrase = s;
                fex = exception;
                super();
            }
        }
);
    }

    public void onHttpOperationProgress(HttpOperation httpoperation, long l, long l1)
    {
    }

    public void start()
    {
        mHttpOp = new HttpOperation(mContext, mHttpMethod, mBaseUrl, new FileOutputStream(filename), this, false);
        mHttpOp.start();
_L1:
        return;
        Exception exception;
        exception;
        exception.printStackTrace();
        mListener.onOperationComplete(this, 0, null, exception);
          goto _L1
    }

    public final String filename;
    public final long id;
    private ProfileImage mProfileImage;
}
