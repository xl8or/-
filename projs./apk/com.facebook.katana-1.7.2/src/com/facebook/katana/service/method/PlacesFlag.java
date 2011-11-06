// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesFlag.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.*;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;
import org.json.JSONException;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, ApiMethodListener

public class PlacesFlag extends ApiMethod
    implements ApiMethodCallback
{

    public PlacesFlag(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, FacebookPlace facebookplace, String s1)
        throws JSONException
    {
        super(context, intent, "GET", "places.setFlag", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mPlace = facebookplace;
        mFlagType = s1;
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("page_id", Long.toString(mPlace.mPageId));
        mParams.put("flag", mFlagType);
        mParams.put("value", "1");
        if(!$assertionsDisabled && facebookplace.getPageInfo() == null)
        {
            throw new AssertionError();
        } else
        {
            mFlagId = -1L;
            return;
        }
    }

    public static String FlagPlace(AppSession appsession, Context context, FacebookPlace facebookplace, String s)
        throws JSONException
    {
        return appsession.postToService(context, new PlacesFlag(context, null, appsession.getSessionInfo().sessionKey, null, facebookplace, s), 1001, 505, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        intent.getIntExtra("extended_type", -1);
        JVM INSTR tableswitch 505 505: default 28
    //                   505 29;
           goto _L1 _L2
_L1:
        return;
_L2:
        Iterator iterator = appsession.getListeners().iterator();
        while(iterator.hasNext()) 
            ((AppSessionListener)iterator.next()).onFlagPlaceComplete(appsession, s, i, s1, exception);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public long getFlagId()
    {
        return mFlagId;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        if(jsonparser.getCurrentToken() == JsonToken.START_OBJECT)
            throw new FacebookApiException(jsonparser);
        Object obj = JMParser.parseJsonResponse(jsonparser, JMBase.LONG);
        if(obj != null && (obj instanceof Long))
        {
            mFlagId = ((Long)obj).longValue();
            return;
        } else
        {
            throw new FacebookApiException(-1, "unexpected value in response");
        }
    }

    static final boolean $assertionsDisabled = false;
    public static String CLOSED = "closed";
    public static String DUPLICATE = "duplicate";
    public static String INFO_INCORRECT = "info_incorrect";
    public static final long INVALID_ID = -1L;
    public static String OFFENSIVE = "offensive";
    protected long mFlagId;
    protected String mFlagType;
    public FacebookPlace mPlace;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/PlacesFlag.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
