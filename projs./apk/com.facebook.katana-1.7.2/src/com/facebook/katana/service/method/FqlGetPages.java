// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetPages.java

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
//            FqlGeneratedQuery, ApiMethodCallback, ApiMethodListener

public class FqlGetPages extends FqlGeneratedQuery
    implements ApiMethodCallback
{

    public FqlGetPages(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1, Class class1)
    {
        super(context, intent, s, apimethodlistener, "page", s1, class1);
        mCls = class1;
    }

    public static String RequestPageInfo(Context context, String s, Class class1)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new FqlGetPages(context, null, appsession.getSessionInfo().sessionKey, null, s, class1), 1001, 1020, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        FacebookPage facebookpage = null;
        if(i == 200)
        {
            Iterator iterator1 = mPages.values().iterator();
            if(iterator1.hasNext())
                facebookpage = (FacebookPage)iterator1.next();
        }
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext();)
        {
            AppSessionListener appsessionlistener = (AppSessionListener)iterator.next();
            if(facebookpage != null)
                appsessionlistener.onPagesGetInfoComplete(appsession, s, i, s1, exception, ((FacebookPage)facebookpage).mPageId, facebookpage);
            else
                appsessionlistener.onPagesGetInfoComplete(appsession, s, i, s1, exception, -1L, null);
        }

    }

    public Map getPages()
    {
        return Collections.unmodifiableMap(mPages);
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        List list = JMParser.parseObjectListJson(jsonparser, mCls);
        if(list != null)
        {
            mPages = new HashMap();
            FacebookPage facebookpage;
            for(Iterator iterator = list.iterator(); iterator.hasNext(); mPages.put(Long.valueOf(facebookpage.mPageId), facebookpage))
                facebookpage = (FacebookPage)iterator.next();

        }
    }

    private static final String TAG = "FqlGetPages";
    private Class mCls;
    private Map mPages;
}
