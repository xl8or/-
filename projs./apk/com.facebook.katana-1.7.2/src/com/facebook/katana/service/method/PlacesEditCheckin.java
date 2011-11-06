// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesEditCheckin.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, ApiMethodListener

public class PlacesEditCheckin extends ApiMethod
    implements ApiMethodCallback
{

    public PlacesEditCheckin(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, String s1, 
            Set set)
        throws JSONException
    {
        super(context, intent, "GET", "places.editCheckin", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", Long.toString(System.currentTimeMillis()));
        mParams.put("session_key", s);
        mParams.put("checkin_id", Long.toString(l));
        JSONObject jsonobject = new JSONObject();
        if(s1 != null)
            jsonobject.put("message", s1);
        if(set != null)
            jsonobject.put("tagged_uids", set);
        mParams.put("checkin_data", jsonobject.toString());
        mCheckinId = l;
        mMessage = s1;
        mTaggedUids = set;
    }

    public static String EditCheckin(AppSession appsession, Context context, long l, String s, Set set)
        throws JSONException
    {
        return appsession.postToService(context, new PlacesEditCheckin(context, null, appsession.getSessionInfo().sessionKey, null, l, s, set), 1001, 1020, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        if(i == 200)
        {
            String s2 = UserValuesManager.getValue(context, "places:last_checkin", null);
            if(s2 != null)
            {
                FacebookCheckin facebookcheckin = (FacebookCheckin)JMCachingDictDestination.jsonDecode(s2, com/facebook/katana/model/FacebookCheckin);
                if(facebookcheckin.mCheckinId == mCheckinId)
                {
                    facebookcheckin.getDetails().mTaggedUids = new ArrayList(mTaggedUids);
                    String s3 = facebookcheckin.jsonEncode();
                    if(s3 != null)
                        UserValuesManager.setValue(context, "places:last_checkin", s3);
                }
            }
        }
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onPlacesEditCheckinComplete(appsession, s, i, s1, exception, mCheckinId, mMessage, mTaggedUids));
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        if(jsonparser.getCurrentToken() != JsonToken.VALUE_NULL)
            throw new FacebookApiException(jsonparser);
        else
            return;
    }

    protected long mCheckinId;
    public String mMessage;
    public Set mTaggedUids;
}
