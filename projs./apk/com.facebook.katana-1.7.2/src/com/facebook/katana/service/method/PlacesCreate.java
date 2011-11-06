// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesCreate.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;
import org.json.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, PlacesCreateException, ApiMethodListener

public class PlacesCreate extends ApiMethod
    implements ApiMethodCallback
{

    public PlacesCreate(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1, String s2, Location location, 
            List list)
    {
        super(context, intent, "POST", "places.create", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", Long.toString(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mParams.put("name", s1);
        mParams.put("coords", jsonEncode(location));
        mParams.put("description", s2);
        mParams.put("override_ids", jsonEncode(list));
        mName = s1;
        mDescription = s2;
        mLocation = location;
        mOverrideIds = list;
        mPlaceId = -1L;
    }

    public static String placesCreate(Context context, String s, String s1, Location location, List list)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new PlacesCreate(context, null, appsession.getSessionInfo().sessionKey, null, s, s1, location, list), 1001, 0, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        long l = getCreatedPlace();
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onPlacesCreateComplete(appsession, s, i, s1, exception, l));
    }

    public long getCreatedPlace()
    {
        return mPlaceId;
    }

    protected String jsonEncode(Location location)
    {
        String s1;
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
        s1 = jsonobject.toString();
        String s = s1;
_L2:
        return s;
        JSONException jsonexception;
        jsonexception;
        Object aobj[] = new Object[1];
        aobj[0] = jsonexception.getMessage();
        Log.e("service.method.PlacesCreate", String.format("How do we get a JSONException when *encoding* data? %s", aobj));
        s = "";
        if(true) goto _L2; else goto _L1
_L1:
    }

    protected String jsonEncode(List list)
    {
        JSONArray jsonarray = new JSONArray();
        for(Iterator iterator = list.iterator(); iterator.hasNext(); jsonarray.put(((Long)iterator.next()).longValue()));
        return jsonarray.toString();
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        if(jsonparser.getCurrentToken() == JsonToken.START_OBJECT)
            throw new PlacesCreateException(jsonparser);
        Object obj = JMParser.parseJsonResponse(jsonparser, JMBase.LONG);
        if(obj != null && (obj instanceof Long))
        {
            mPlaceId = ((Long)obj).longValue();
            return;
        } else
        {
            throw new FacebookApiException(-1, "unexpected value in response");
        }
    }

    static final String TAG = "service.method.PlacesCreate";
    public String mDescription;
    public Location mLocation;
    public String mName;
    public List mOverrideIds;
    protected long mPlaceId;
}
