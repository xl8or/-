// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxGetThreadMessages.java

package com.facebook.katana.service.method;

import android.content.*;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookMailboxThreadMessage;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlQuery, ApiMethodListener

public class MailboxGetThreadMessages extends FqlQuery
{

    public MailboxGetThreadMessages(Context context, Intent intent, String s, int i, long l, int j, 
            int k, ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, buildQuery(i, l, j, k), apimethodlistener);
        mFolder = i;
        mThreadId = l;
    }

    private static String buildQuery(int i, long l, int j, int k)
    {
        if(j == -1)
            j = 0;
        if(k == -1)
            k = 100;
        return (new StringBuilder()).append("SELECT message_id,author_id,created_time,body,attachment FROM message WHERE (thread_id=").append(l).append(")").append(" ORDER BY created_time DESC").append(" LIMIT ").append(j).append(",").append(k).toString();
    }

    private void saveMessages()
    {
        ContentValues acontentvalues[] = new ContentValues[mMessages.size()];
        int i = 0;
        FacebookMailboxThreadMessage facebookmailboxthreadmessage;
        ContentValues contentvalues;
        for(Iterator iterator = mMessages.iterator(); iterator.hasNext(); contentvalues.put("body", facebookmailboxthreadmessage.getBody()))
        {
            facebookmailboxthreadmessage = (FacebookMailboxThreadMessage)iterator.next();
            acontentvalues[i] = new ContentValues();
            contentvalues = acontentvalues[i];
            i++;
            contentvalues.put("folder", Integer.valueOf(mFolder));
            contentvalues.put("tid", Long.valueOf(facebookmailboxthreadmessage.getThreadId()));
            contentvalues.put("mid", Long.valueOf(facebookmailboxthreadmessage.getMessageId()));
            contentvalues.put("author_id", Long.valueOf(facebookmailboxthreadmessage.getAuthorId()));
            contentvalues.put("sent", Long.valueOf(facebookmailboxthreadmessage.getTimeSent()));
        }

        mContext.getContentResolver().bulkInsert(MailboxProvider.MESSAGES_CONTENT_URI, acontentvalues);
    }

    public List getMessages()
    {
        return mMessages;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_OBJECT)
            throw new FacebookApiException(jsonparser);
        if(jsontoken == JsonToken.START_ARRAY)
        {
            for(; jsontoken != JsonToken.END_ARRAY; jsontoken = jsonparser.nextToken())
                if(jsontoken == JsonToken.START_OBJECT)
                    mMessages.add(new FacebookMailboxThreadMessage(jsonparser, mThreadId));

            saveMessages();
            return;
        } else
        {
            throw new IOException("Malformed JSON");
        }
    }

    private final int mFolder;
    private final List mMessages = new ArrayList();
    private final long mThreadId;
}
