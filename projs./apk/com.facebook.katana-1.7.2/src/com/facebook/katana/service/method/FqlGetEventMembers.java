// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetEventMembers.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.*;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlMultiQuery, FqlGetUsersProfile, ApiMethodListener, FqlQuery

public class FqlGetEventMembers extends FqlMultiQuery
{
    static class FqlGetRsvpStatusByEvent extends FqlQuery
    {

        protected static String buildQuery(long l)
        {
            StringBuilder stringbuilder = new StringBuilder("SELECT uid, rsvp_status FROM event_member WHERE eid=");
            stringbuilder.append(String.valueOf(l));
            return stringbuilder.toString();
        }

        public Map getEventRsvpStatus()
        {
            return Collections.unmodifiableMap(mRsvpStatus);
        }

        protected void parseJSON(JsonParser jsonparser)
            throws FacebookApiException, JsonParseException, IOException
        {
            JsonToken jsontoken = jsonparser.getCurrentToken();
            if(!$assertionsDisabled && jsontoken != JsonToken.START_ARRAY)
                throw new AssertionError();
            JsonToken jsontoken1 = jsonparser.nextToken();
            while(jsontoken1 != JsonToken.END_ARRAY) 
            {
                if(jsontoken1 == JsonToken.START_OBJECT)
                    FacebookEvent.parseRsvpStatus(jsonparser, mRsvpStatus);
                else
                if(jsontoken1 == JsonToken.START_ARRAY)
                    jsonparser.skipChildren();
                jsontoken1 = jsonparser.nextToken();
            }
        }

        static final boolean $assertionsDisabled = false;
        private static final String TAG = "FqlGetRsvpStatusByEvent";
        private final Map mRsvpStatus = new HashMap();

        static 
        {
            boolean flag;
            if(!com/facebook/katana/service/method/FqlGetEventMembers.desiredAssertionStatus())
                flag = true;
            else
                flag = false;
            $assertionsDisabled = flag;
        }

        public FqlGetRsvpStatusByEvent(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l)
        {
            super(context, intent, s, buildQuery(l), apimethodlistener);
        }
    }


    public FqlGetEventMembers(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l)
    {
        super(context, intent, s, buildQueries(context, intent, s, l), apimethodlistener);
        mEventId = l;
    }

    protected static LinkedHashMap buildQueries(Context context, Intent intent, String s, long l)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        linkedhashmap.put("rsvp_status", new FqlGetRsvpStatusByEvent(context, intent, s, null, l));
        linkedhashmap.put("user_info", new FqlGetUsersProfile(context, intent, s, null, "uid IN (SELECT uid FROM #rsvp_status)", com/facebook/katana/model/FacebookUser));
        return linkedhashmap;
    }

    public long getEventId()
    {
        return mEventId;
    }

    public Map getEventMembersByStatus()
    {
        return Collections.unmodifiableMap(mMembersByRsvpStatus);
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        super.parseJSON(jsonparser, s);
        FqlGetRsvpStatusByEvent fqlgetrsvpstatusbyevent = (FqlGetRsvpStatusByEvent)getQueryByName("rsvp_status");
        FqlGetUsersProfile fqlgetusersprofile = (FqlGetUsersProfile)getQueryByName("user_info");
        mMembersByRsvpStatus = new HashMap();
        Map map = fqlgetrsvpstatusbyevent.getEventRsvpStatus();
        Map map1 = fqlgetusersprofile.getUsers();
        Long long1;
        com.facebook.katana.model.FacebookEvent.RsvpStatus rsvpstatus;
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ((List)mMembersByRsvpStatus.get(rsvpstatus)).add(map1.get(long1)))
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            long1 = (Long)entry.getKey();
            rsvpstatus = (com.facebook.katana.model.FacebookEvent.RsvpStatus)entry.getValue();
            if(!mMembersByRsvpStatus.containsKey(rsvpstatus))
                mMembersByRsvpStatus.put(rsvpstatus, new ArrayList());
        }

    }

    private static final String TAG = "FqlGetEventMembers";
    protected long mEventId;
    protected Map mMembersByRsvpStatus;
}
