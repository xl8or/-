// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxDeleteThread.java

package com.facebook.katana.service.method;

import android.content.*;
import android.net.Uri;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, FBJsonFactory, ApiMethodListener

public class MailboxDeleteThread extends ApiMethod
{

    public MailboxDeleteThread(Context context, Intent intent, String s, long l, int i, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "mailbox.deleteThread", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("tid", (new StringBuilder()).append("").append(l).toString());
        mParams.put("folder", (new StringBuilder()).append("").append(i).toString());
        mFolder = i;
    }

    protected void parseResponse(String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        printJson(s);
        if(s.startsWith("{"))
        {
            throw new FacebookApiException(mJsonFactory.createJsonParser(s));
        } else
        {
            ContentResolver contentresolver = mContext.getContentResolver();
            String s1 = (String)mParams.get("tid");
            contentresolver.delete(Uri.withAppendedPath(MailboxProvider.getThreadsTidFolderUri(mFolder), s1), null, null);
            contentresolver.delete(Uri.withAppendedPath(MailboxProvider.getMessagesTidFolderUri(mFolder), s1), null, null);
            contentresolver.delete(MailboxProvider.PROFILES_PRUNE_CONTENT_URI, null, null);
            return;
        }
    }

    private final int mFolder;
}
