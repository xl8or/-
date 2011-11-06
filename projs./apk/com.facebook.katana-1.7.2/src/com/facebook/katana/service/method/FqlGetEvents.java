// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetEvents.java

package com.facebook.katana.service.method;

import android.content.*;
import android.text.format.Time;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.EventsProvider;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlMultiQuery, FqlGetProfile, ApiMethodListener, FqlQuery

public class FqlGetEvents extends FqlMultiQuery
{
    static class FqlGetEventRsvpStatus extends FqlQuery
    {

        protected static String buildQuery(long l, String s)
        {
            StringBuilder stringbuilder = new StringBuilder("SELECT eid, rsvp_status FROM event_member WHERE uid=");
            stringbuilder.append(String.valueOf(l));
            stringbuilder.append(" AND eid IN (SELECT eid FROM #");
            stringbuilder.append(s);
            stringbuilder.append(")");
            return stringbuilder.toString();
        }

        public com.facebook.katana.model.FacebookEvent.RsvpStatus getEventRsvpStatus(long l)
        {
            return (com.facebook.katana.model.FacebookEvent.RsvpStatus)mRsvpStatus.get(new Long(l));
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
        private static final String TAG = "FqlGetEventRsvpStatus";
        private final Map mRsvpStatus = new HashMap();

        static 
        {
            boolean flag;
            if(!com/facebook/katana/service/method/FqlGetEvents.desiredAssertionStatus())
                flag = true;
            else
                flag = false;
            $assertionsDisabled = flag;
        }

        public FqlGetEventRsvpStatus(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, String s1)
        {
            super(context, intent, s, buildQuery(l, s1), apimethodlistener);
        }
    }

    static class FqlGetEventInfo extends FqlQuery
    {

        protected static String buildQuery(long l)
        {
            Time time = new Time();
            time.setToNow();
            long l1 = time.toMillis(false) / 1000L;
            StringBuilder stringbuilder = new StringBuilder("SELECT eid, name, tagline, pic_small, pic, host, description, event_type, event_subtype, start_time, end_time, creator, location, venue, hide_guest_list FROM event WHERE end_time > ");
            stringbuilder.append(String.valueOf(l1));
            stringbuilder.append(" AND eid IN (SELECT eid FROM event_member where uid=");
            stringbuilder.append(String.valueOf(l));
            stringbuilder.append(") ORDER BY start_time");
            return stringbuilder.toString();
        }

        public List getEvents()
        {
            return Collections.unmodifiableList(mEvents);
        }

        protected void parseJSON(JsonParser jsonparser)
            throws FacebookApiException, JsonParseException, IOException, JMException
        {
            JsonToken jsontoken = jsonparser.getCurrentToken();
            if(jsontoken == JsonToken.START_ARRAY)
                for(; jsontoken != JsonToken.END_ARRAY; jsontoken = jsonparser.nextToken())
                    if(jsontoken == JsonToken.START_OBJECT)
                    {
                        FacebookEvent facebookevent = FacebookEvent.parseFromJSON(jsonparser);
                        mEvents.add(facebookevent);
                    }

        }

        private static final String TAG = "FqlGetEventInfo";
        private final List mEvents = new ArrayList();

        public FqlGetEventInfo(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l)
        {
            super(context, intent, s, buildQuery(l), apimethodlistener);
        }
    }


    public FqlGetEvents(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l)
    {
        super(context, intent, s, buildQueries(context, intent, s, l), apimethodlistener);
    }

    protected static LinkedHashMap buildQueries(Context context, Intent intent, String s, long l)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        linkedhashmap.put("event_info", new FqlGetEventInfo(context, intent, s, null, l));
        linkedhashmap.put("rsvp_status", new FqlGetEventRsvpStatus(context, intent, s, null, l, "event_info"));
        linkedhashmap.put("creator_info", new FqlGetProfile(context, intent, s, null, "id IN (SELECT creator FROM #event_info)"));
        return linkedhashmap;
    }

    /**
     * @deprecated Method saveSearchResults is deprecated
     */

    private void saveSearchResults()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        ContentResolver contentresolver;
        int i;
        contentresolver = mContext.getContentResolver();
        contentresolver.delete(EventsProvider.EVENTS_CONTENT_URI, null, null);
        i = mEvents.size();
        if(i != 0) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        int j = 0;
        ContentValues acontentvalues[];
        acontentvalues = new ContentValues[mEvents.size()];
        FacebookEvent facebookevent;
        ContentValues contentvalues;
        for(Iterator iterator = mEvents.iterator(); iterator.hasNext(); facebookevent.writeContentValues(contentvalues))
        {
            facebookevent = (FacebookEvent)iterator.next();
            contentvalues = new ContentValues();
            acontentvalues[j] = contentvalues;
            j++;
        }

        break MISSING_BLOCK_LABEL_123;
        Exception exception;
        exception;
        throw exception;
        contentresolver.bulkInsert(EventsProvider.EVENTS_CONTENT_URI, acontentvalues);
          goto _L1
    }

    public List getEvents()
    {
        return mEvents;
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        super.parseJSON(jsonparser, s);
        FqlGetEventInfo fqlgeteventinfo = (FqlGetEventInfo)getQueryByName("event_info");
        FqlGetEventRsvpStatus fqlgeteventrsvpstatus = (FqlGetEventRsvpStatus)getQueryByName("rsvp_status");
        FqlGetProfile fqlgetprofile = (FqlGetProfile)getQueryByName("creator_info");
        mEvents = fqlgeteventinfo.getEvents();
        HashMap hashmap = new HashMap();
        FacebookEvent facebookevent;
        Object obj;
        for(Iterator iterator = mEvents.iterator(); iterator.hasNext(); ((List) (obj)).add(facebookevent))
        {
            facebookevent = (FacebookEvent)iterator.next();
            facebookevent.setRsvpStatus(fqlgeteventrsvpstatus.getEventRsvpStatus(facebookevent.getEventId()));
            Long long1 = new Long(facebookevent.getCreatorId());
            obj = (List)hashmap.get(long1);
            if(obj == null)
            {
                obj = new ArrayList();
                hashmap.put(long1, obj);
            }
        }

        Iterator iterator1 = fqlgetprofile.getProfiles().entrySet().iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator1.next();
            List list = (List)hashmap.get((Long)entry.getKey());
            if(list != null)
            {
                Iterator iterator2 = list.iterator();
                while(iterator2.hasNext()) 
                    ((FacebookEvent)iterator2.next()).setCreator((FacebookProfile)entry.getValue());
            }
        } while(true);
        saveSearchResults();
    }

    private static final String TAG = "FqlGetEvents";
    protected List mEvents;
}
