// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhonebookLookup.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.*;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, ApiMethodListener

public class PhonebookLookup extends ApiMethod
    implements ApiMethodCallback
{

    public PhonebookLookup(Context context, Intent intent, String s, List list, boolean flag, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "POST", "phonebook.lookup", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", String.valueOf(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mParams.put("entries", FacebookPhonebookContact.jsonEncode(list));
        Map map = mParams;
        String s2;
        if(flag)
            s2 = "1";
        else
            s2 = "0";
        map.put("include_non_fb", s2);
        mParams.put("country_code", s1);
        mParams.put("not_for_sync", "1");
        mContacts = new ArrayList();
    }

    public static String lookup(AppSession appsession, Context context, List list, boolean flag, String s)
    {
        return appsession.postToService(context, new PhonebookLookup(context, null, appsession.getSessionInfo().sessionKey, list, flag, s, null), 1001, 1020, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onPhonebookLookupComplete(appsession, s, i, s1, exception, getContacts()));
    }

    public List getContacts()
    {
        return mContacts;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        mContacts = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FacebookPhonebookContact);
    }

    protected static final String ENTRIES_PARAM = "entries";
    protected static final String INCLUDE_NON_FB_PARAM = "include_non_fb";
    protected static final String NOT_FOR_SYNC = "not_for_sync";
    private List mContacts;
}
