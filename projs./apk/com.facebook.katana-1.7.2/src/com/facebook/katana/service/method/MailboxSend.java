// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxSend.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, Utils, FBJsonFactory, ApiMethodListener

public class MailboxSend extends ApiMethod
{

    public MailboxSend(Context context, Intent intent, String s, FacebookUser facebookuser, List list, String s1, String s2, 
            ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "mailbox.send", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("to", commaSeparatedUserIds(list));
        mParams.put("subject", s1);
        mParams.put("body", s2);
        mSender = facebookuser;
        mRecipients = list;
    }

    private static String commaSeparatedUserIds(List list)
    {
        StringBuffer stringbuffer = new StringBuffer(64);
        boolean flag = true;
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) 
        {
            FacebookProfile facebookprofile = (FacebookProfile)iterator.next();
            if(!flag)
                stringbuffer.append(",");
            else
                flag = false;
            stringbuffer.append(facebookprofile.mId);
        }
        return stringbuffer.toString();
    }

    private void updateContentProviders(long l)
    {
        long l1 = System.currentTimeMillis() / 1000L;
        ContentResolver contentresolver = mContext.getContentResolver();
        String s = Utils.buildSnippet((String)mParams.get("body"));
        List list = userIdList(mRecipients);
        Map map = usersMap(mRecipients);
        ContentValues contentvalues = (new FacebookMailboxThread(l, (String)mParams.get("subject"), s, -1L, 1, 0, l1, 0L, list)).getContentValues(1, mSender.mUserId, map, mContext.getString(0x7f0a00be));
        contentresolver.insert(MailboxProvider.THREADS_CONTENT_URI, contentvalues);
        contentvalues.clear();
        contentvalues.put("folder", Integer.valueOf(1));
        contentvalues.put("tid", Long.valueOf(l));
        contentvalues.put("mid", Integer.valueOf(0));
        contentvalues.put("author_id", Long.valueOf(mSender.mUserId));
        contentvalues.put("sent", Long.valueOf(l1));
        contentvalues.put("body", (String)mParams.get("body"));
        contentresolver.insert(MailboxProvider.MESSAGES_CONTENT_URI, contentvalues);
        String s1 = (new StringBuilder()).append("").append(mSender.mUserId).toString();
        Cursor cursor = contentresolver.query(Uri.withAppendedPath(MailboxProvider.PROFILES_ID_CONTENT_URI, s1), USERS_PROJECTION, null, null, null);
        if(cursor != null)
        {
            if(cursor.getCount() == 0)
            {
                contentvalues.clear();
                contentvalues.put("id", Long.valueOf(mSender.mUserId));
                contentvalues.put("display_name", mSender.getDisplayName());
                contentvalues.put("profile_image_url", mSender.mImageUrl);
                contentvalues.put("type", Integer.valueOf(0));
                contentresolver.insert(MailboxProvider.PROFILES_CONTENT_URI, contentvalues);
            }
            cursor.close();
        }
    }

    private static List userIdList(List list)
    {
        ArrayList arraylist = new ArrayList();
        for(Iterator iterator = list.iterator(); iterator.hasNext(); arraylist.add(Long.valueOf(((FacebookProfile)iterator.next()).mId)));
        return arraylist;
    }

    private static Map usersMap(List list)
    {
        HashMap hashmap = new HashMap();
        FacebookProfile facebookprofile;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); hashmap.put(Long.valueOf(facebookprofile.mId), facebookprofile))
            facebookprofile = (FacebookProfile)iterator.next();

        return hashmap;
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
            updateContentProviders(Long.parseLong(s));
            return;
        }
    }

    private static final String USERS_PROJECTION[];
    private final List mRecipients;
    private final FacebookUser mSender;

    static 
    {
        String as[] = new String[1];
        as[0] = "_id";
        USERS_PROJECTION = as;
    }
}
