// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetPageFanStatus.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodCallback, ApiMethodListener

public class FqlGetPageFanStatus extends FqlGeneratedQuery
    implements ApiMethodCallback
{
    private static class UserPageRelationship extends JMCachingDictDestination
    {

        public final long pageId = -1L;
        public final long uid = -1L;

        private UserPageRelationship()
        {
        }
    }


    public FqlGetPageFanStatus(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, long l1)
    {
        super(context, intent, s, apimethodlistener, "page_fan", buildWhereClause(l, l1), com/facebook/katana/service/method/FqlGetPageFanStatus$UserPageRelationship);
        mPageId = l1;
    }

    public static String RequestPageFanStatus(Context context, long l)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new FqlGetPageFanStatus(context, null, appsession.getSessionInfo().sessionKey, null, appsession.getSessionInfo().userId, l), 1001, 1020, null);
    }

    private static String buildWhereClause(long l, long l1)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("uid=").append(l).append(" and ").append("page_id=").append(l1);
        return stringbuilder.toString();
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onGetPageFanStatusComplete(appsession, s, i, s1, exception, mPageId, mFan));
    }

    public boolean isFan()
    {
        return mFan;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        mFan = false;
        List list = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/service/method/FqlGetPageFanStatus$UserPageRelationship);
        if(list != null && list.size() > 0)
            mFan = true;
    }

    private boolean mFan;
    private final long mPageId;
}
