// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxMarkThread.java

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

public class MailboxMarkThread extends ApiMethod
{

    public MailboxMarkThread(Context context, Intent intent, String s, long l, boolean flag, ApiMethodListener apimethodlistener)
    {
        String s1;
        if(flag)
            s1 = "mailbox.markRead";
        else
            s1 = "mailbox.markUnread";
        super(context, intent, "GET", s1, com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mFolder = intent.getIntExtra("folder", 0);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("tid", (new StringBuilder()).append("").append(l).toString());
        mRead = flag;
    }

    protected void parseResponse(String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        printJson(s);
        if(s.startsWith("{"))
            throw new FacebookApiException(mJsonFactory.createJsonParser(s));
        ContentValues contentvalues = new ContentValues();
        int i;
        String s1;
        ContentResolver contentresolver;
        boolean flag;
        int j;
        if(mRead)
            i = 0;
        else
            i = 1;
        contentvalues.put("unread_count", Integer.valueOf(i));
        s1 = (String)mParams.get("tid");
        contentresolver = mContext.getContentResolver();
        contentresolver.update(Uri.withAppendedPath(MailboxProvider.getThreadsTidFolderUri(mFolder), s1), contentvalues, null, null);
        if(mFolder == 0)
        {
            flag = true;
            j = 1;
        } else
        if(1 == mFolder)
        {
            flag = true;
            j = 0;
        } else
        {
            flag = false;
            j = 0;
        }
        if(flag)
            contentresolver.update(Uri.withAppendedPath(MailboxProvider.getThreadsTidFolderUri(j), s1), contentvalues, null, null);
    }

    private final int mFolder;
    private final boolean mRead;
}
