// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesCheckin.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.*;
import org.json.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class PlacesCheckin extends ApiMethod
{

    public PlacesCheckin(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, FacebookPlace facebookplace, Location location, String s1, 
            Set set, Long long1, String s2)
        throws JSONException
    {
        super(context, intent, "GET", "places.checkin", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("page_id", Long.toString(facebookplace.mPageId));
        mParams.put("coords", jsonEncode(location));
        if(s1 != null)
            mParams.put("message", s1);
        if(set != null && set.size() != 0)
        {
            Map map1 = mParams;
            JSONArray jsonarray = new JSONArray(set);
            map1.put("tagged_uids", jsonarray.toString());
        }
        if(long1 != null && long1.longValue() != -1L)
            mParams.put("group_id", String.valueOf(long1));
        if(s2 != null)
        {
            Map map = mParams;
            JSONObject jsonobject = new JSONObject(s2);
            map.put("privacy", jsonobject.toString());
        }
        mPlace = facebookplace;
        if(!$assertionsDisabled && facebookplace.getPageInfo() == null)
        {
            throw new AssertionError();
        } else
        {
            mLocation = location;
            mMessage = s1;
            mTaggedUids = set;
            mCheckinId = -1L;
            return;
        }
    }

    public long getCheckinId()
    {
        return mCheckinId;
    }

    protected String jsonEncode(Location location)
        throws JSONException
    {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("latitude", location.getLatitude());
        jsonobject.put("longitude", location.getLongitude());
        if(location.hasAccuracy())
            jsonobject.put("accuracy", location.getAccuracy());
        if(location.hasAltitude())
            jsonobject.put("altitude", location.getAltitude());
        if(location.hasBearing())
            jsonobject.put("heading", location.getBearing());
        if(location.hasSpeed())
            jsonobject.put("speed", location.getSpeed());
        return jsonobject.toString();
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        if(jsonparser.getCurrentToken() == JsonToken.START_OBJECT)
            throw new FacebookApiException(jsonparser);
        Object obj = JMParser.parseJsonResponse(jsonparser, JMBase.LONG);
        if(obj != null && (obj instanceof Long))
        {
            mCheckinId = ((Long)obj).longValue();
            return;
        } else
        {
            throw new FacebookApiException(-1, "unexpected value in response");
        }
    }

    static final boolean $assertionsDisabled;
    protected long mCheckinId;
    public Location mLocation;
    public String mMessage;
    public FacebookPlace mPlace;
    public Set mTaggedUids;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/PlacesCheckin.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
