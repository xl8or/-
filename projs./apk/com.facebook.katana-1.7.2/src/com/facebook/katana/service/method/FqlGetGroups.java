// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetGroups.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.*;
import com.facebook.katana.util.jsonmirror.*;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlMultiQuery, ApiMethodCallback, ApiMethodListener, FqlGeneratedQuery

public class FqlGetGroups extends FqlMultiQuery
    implements ApiMethodCallback
{
    static class Info extends FqlGeneratedQuery
    {

        protected static String buildWhereClause(String s)
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("gid IN (SELECT gid FROM ").append(s).append(") AND version>0 ORDER BY update_time DESC");
            return stringbuilder.toString();
        }

        protected void parseJSON(JsonParser jsonparser)
            throws FacebookApiException, JsonParseException, IOException, JMException
        {
            mGroups = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FacebookGroup);
        }

        List mGroups;

        protected Info(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
        {
            super(context, intent, s, apimethodlistener, "group", buildWhereClause(s1), com/facebook/katana/model/FacebookGroup);
        }
    }

    static class UserRelationship extends FqlGeneratedQuery
    {
        static class GroupUserRelationship extends JMCachingDictDestination
        {

            public final long mGid = -1L;
            public final int mUnread = 0;

            private GroupUserRelationship()
            {
            }
        }


        protected static String buildWhereClause(long l)
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("uid=").append(l);
            return stringbuilder.toString();
        }

        protected void parseJSON(JsonParser jsonparser)
            throws FacebookApiException, JsonParseException, IOException, JMException
        {
            List list = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/service/method/FqlGetGroups$UserRelationship$GroupUserRelationship);
            if(list != null)
            {
                Iterator iterator = list.iterator();
                while(iterator.hasNext()) 
                {
                    GroupUserRelationship groupuserrelationship = (GroupUserRelationship)iterator.next();
                    mGroups.put(Long.valueOf(groupuserrelationship.mGid), groupuserrelationship);
                }
            }
        }

        Map mGroups;

        protected UserRelationship(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l)
        {
            this(context, intent, s, apimethodlistener, buildWhereClause(l));
        }

        protected UserRelationship(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
        {
            super(context, intent, s, apimethodlistener, "group_member", s1, com/facebook/katana/service/method/FqlGetGroups$UserRelationship$GroupUserRelationship);
            mGroups = new HashMap();
        }
    }


    public FqlGetGroups(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l)
    {
        super(context, intent, s, buildQueries(context, intent, s, l), apimethodlistener);
    }

    public static String RequestGroups(Context context)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new FqlGetGroups(context, null, appsession.getSessionInfo().sessionKey, null, appsession.getSessionInfo().userId), 1001, 600, null);
    }

    protected static LinkedHashMap buildQueries(Context context, Intent intent, String s, long l)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        linkedhashmap.put("grouprel", new UserRelationship(context, intent, s, null, l));
        linkedhashmap.put("groupinfo", new Info(context, intent, s, null, "#grouprel"));
        return linkedhashmap;
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        intent.getIntExtra("extended_type", -1);
        JVM INSTR tableswitch 600 600: default 28
    //                   600 29;
           goto _L1 _L2
_L1:
        return;
_L2:
        Iterator iterator = appsession.getListeners().iterator();
        while(iterator.hasNext()) 
            ((AppSessionListener)iterator.next()).onGetGroupsComplete(appsession, s, i, s1, exception, mGroups);
        if(true) goto _L1; else goto _L3
_L3:
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        super.parseJSON(jsonparser, s);
        UserRelationship userrelationship = (UserRelationship)getQueryByName("grouprel");
        mGroups = ((Info)getQueryByName("groupinfo")).mGroups;
        Iterator iterator = mGroups.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FacebookGroup facebookgroup = (FacebookGroup)iterator.next();
            UserRelationship.GroupUserRelationship groupuserrelationship = (UserRelationship.GroupUserRelationship)userrelationship.mGroups.get(Long.valueOf(facebookgroup.mId));
            if(groupuserrelationship != null)
                facebookgroup.setUnreadCount(groupuserrelationship.mUnread);
        } while(true);
    }

    private List mGroups;
}
