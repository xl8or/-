// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventRsvp.java

package com.facebook.katana.service.method;

import android.content.*;
import android.net.Uri;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.provider.EventsProvider;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class EventRsvp extends ApiMethod
{

    public EventRsvp(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, com.facebook.katana.model.FacebookEvent.RsvpStatus rsvpstatus)
    {
        super(context, intent, "POST", "events.rsvp", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", Long.toString(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mParams.put("eid", Long.toString(l));
        mParams.put("rsvp_status", FacebookEvent.getRsvpStatusString(rsvpstatus));
        mEventId = l;
        mNewStatus = rsvpstatus;
        mSuccess = false;
    }

    public long getEventId()
    {
        return mEventId;
    }

    public boolean getSuccess()
    {
        return mSuccess;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        if(jsonparser.getCurrentToken() == JsonToken.VALUE_TRUE)
        {
            mSuccess = true;
            ContentValues contentvalues = new ContentValues();
            contentvalues.put("rsvp_status", Integer.valueOf(mNewStatus.status.ordinal()));
            Uri uri = Uri.withAppendedPath(EventsProvider.EVENT_EID_CONTENT_URI, Long.toString(mEventId));
            mContext.getContentResolver().update(uri, contentvalues, null, null);
        }
    }

    private long mEventId;
    private com.facebook.katana.model.FacebookEvent.RsvpStatus mNewStatus;
    private boolean mSuccess;
}
