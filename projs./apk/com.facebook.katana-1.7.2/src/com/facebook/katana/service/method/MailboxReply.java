// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxReply.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, Utils, FBJsonFactory, ApiMethodListener

public class MailboxReply extends ApiMethod
{
    private static interface ThreadQuery
    {

        public static final int INDEX_ID = 0;
        public static final int INDEX_MSG_COUNT = 1;
        public static final String THREADS_PROJECTION[] = as;

        
        {
            String as[] = new String[2];
            as[0] = "_id";
            as[1] = "msg_count";
        }
    }


    public MailboxReply(Context context, Intent intent, String s, FacebookUser facebookuser, long l, String s1, 
            ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "mailbox.reply", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("tid", (new StringBuilder()).append("").append(l).toString());
        mParams.put("body", s1);
        mSender = facebookuser;
    }

    private void updateContentProvider(long l)
    {
        ContentResolver contentresolver = mContext.getContentResolver();
        long l1 = System.currentTimeMillis() / 1000L;
        String s = (new StringBuilder()).append("").append(l).toString();
        ContentValues contentvalues = new ContentValues();
        Cursor cursor = contentresolver.query(Uri.withAppendedPath(MailboxProvider.OUTBOX_THREADS_TID_CONTENT_URI, s), ThreadQuery.THREADS_PROJECTION, null, null, null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                contentvalues.clear();
                contentvalues.put("snippet", Utils.buildSnippet((String)mParams.get("body")));
                contentvalues.put("other_party", Long.valueOf(mSender.mUserId));
                contentvalues.put("last_update", Long.valueOf(l1));
                contentvalues.put("msg_count", Integer.valueOf(1 + cursor.getInt(1)));
                contentresolver.update(Uri.withAppendedPath(MailboxProvider.THREADS_CONTENT_URI, (new StringBuilder()).append("").append(cursor.getInt(0)).toString()), contentvalues, null, null);
                contentvalues.clear();
                contentvalues.put("folder", Integer.valueOf(1));
                contentvalues.put("tid", Long.valueOf(l));
                contentvalues.put("mid", Integer.valueOf(cursor.getInt(1)));
                contentvalues.put("author_id", Long.valueOf(mSender.mUserId));
                contentvalues.put("sent", Long.valueOf(l1));
                contentvalues.put("body", (String)mParams.get("body"));
                contentresolver.insert(MailboxProvider.MESSAGES_CONTENT_URI, contentvalues);
            }
            cursor.close();
        }
        Cursor cursor1 = contentresolver.query(Uri.withAppendedPath(MailboxProvider.INBOX_THREADS_TID_CONTENT_URI, s), ThreadQuery.THREADS_PROJECTION, null, null, null);
        if(cursor1 != null)
        {
            if(cursor1.moveToFirst())
            {
                contentvalues.clear();
                contentvalues.put("last_update", Long.valueOf(l1));
                contentvalues.put("msg_count", Integer.valueOf(1 + cursor1.getInt(1)));
                contentresolver.update(Uri.withAppendedPath(MailboxProvider.THREADS_CONTENT_URI, (new StringBuilder()).append("").append(cursor1.getInt(0)).toString()), contentvalues, null, null);
                contentvalues.clear();
                contentvalues.put("folder", Integer.valueOf(0));
                contentvalues.put("tid", Long.valueOf(l));
                contentvalues.put("mid", Integer.valueOf(cursor1.getInt(1)));
                contentvalues.put("author_id", Long.valueOf(mSender.mUserId));
                contentvalues.put("sent", Long.valueOf(l1));
                contentvalues.put("body", (String)mParams.get("body"));
                contentresolver.insert(MailboxProvider.MESSAGES_CONTENT_URI, contentvalues);
            }
            cursor1.close();
        }
        Cursor cursor2 = contentresolver.query(Uri.withAppendedPath(MailboxProvider.PROFILES_ID_CONTENT_URI, (new StringBuilder()).append("").append(mSender.mUserId).toString()), USERS_PROJECTION, null, null, null);
        if(cursor2 != null)
        {
            if(cursor2.getCount() == 0)
            {
                contentvalues.clear();
                contentvalues.put("id", Long.valueOf(mSender.mUserId));
                contentvalues.put("display_name", mSender.getDisplayName());
                contentvalues.put("profile_image_url", mSender.mImageUrl);
                contentvalues.put("type", Integer.valueOf(0));
                contentresolver.insert(MailboxProvider.PROFILES_CONTENT_URI, contentvalues);
            }
            cursor2.close();
        }
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
            updateContentProvider(Long.parseLong(s));
            return;
        }
    }

    private static final String USERS_PROJECTION[];
    private final FacebookUser mSender;

    static 
    {
        String as[] = new String[1];
        as[0] = "_id";
        USERS_PROJECTION = as;
    }
}
