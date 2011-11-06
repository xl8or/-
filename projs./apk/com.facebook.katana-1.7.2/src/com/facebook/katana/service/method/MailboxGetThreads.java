// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxGetThreads.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookMailboxThread;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlQuery, ApiMethodListener

public class MailboxGetThreads extends FqlQuery
{

    public MailboxGetThreads(Context context, Intent intent, String s, int i, int j, int k, ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, buildQuery(i, j, k), apimethodlistener);
        mResult = new ArrayList();
    }

    private static String buildQuery(int i, int j, int k)
    {
        if(j == -1)
            j = 0;
        if(k == -1)
            k = 20;
        return (new StringBuilder()).append("SELECT thread_id,subject,recipients,updated_time,message_count,snippet,snippet_author,unread,object_id FROM thread WHERE (folder_id=").append(i).append(") LIMIT ").append(j).append(",").append(k).toString();
    }

    List getMailboxThreads()
    {
        return mResult;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_OBJECT)
        {
            FacebookApiException facebookapiexception = new FacebookApiException(jsonparser);
            if(facebookapiexception.getErrorCode() != -1)
                throw facebookapiexception;
        } else
        if(jsontoken == JsonToken.START_ARRAY)
            for(; jsontoken != JsonToken.END_ARRAY; jsontoken = jsonparser.nextToken())
                if(jsontoken == JsonToken.START_OBJECT)
                    mResult.add(new FacebookMailboxThread(jsonparser));

        else
            throw new IOException("Malformed JSON");
    }

    List mResult;
}
