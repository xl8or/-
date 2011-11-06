// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosPublish.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.*;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

// Referenced classes of package com.facebook.katana.service.method:
//            GraphBatchRequest, ApiMethodCallback, GraphApiMethod

public class PhotosPublish extends GraphBatchRequest
    implements ApiMethodCallback
{

    private PhotosPublish(Context context, String s, List list, String s1, JSONObject jsonobject, List list1, String s2, 
            Long long1)
    {
        super(context, s, com.facebook.katana.Constants.URL.getGraphUrl(context), genRequests(context, list, s1, jsonobject, list1, s2, long1));
        mPhotoIds = list;
        mNumFail = 0;
        mNumSuccess = 0;
    }

    public static String Publish(Context context, List list, String s, JSONObject jsonobject, List list1, String s1, Long long1)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new PhotosPublish(context, appsession.getSessionInfo().oAuthToken, list, s, jsonobject, list1, s1, long1), 1001, 1020, null);
    }

    private static List genRequests(Context context, List list, String s, JSONObject jsonobject, List list1, String s1, Long long1)
    {
        HashMap hashmap = new HashMap();
        hashmap.put("published", "1");
        if(long1 != null && long1.longValue() != -1L)
            hashmap.put("target", String.valueOf(long1));
        hashmap.put("name", s);
        if(jsonobject != null)
            hashmap.put("privacy", jsonobject.toString());
        if(s1 != null)
            hashmap.put("place", s1);
        if(list1 != null && list1.size() > 0)
        {
            JSONArray jsonarray = new JSONArray();
            for(Iterator iterator1 = list1.iterator(); iterator1.hasNext(); jsonarray.put((Long)iterator1.next()));
            hashmap.put("tags", jsonarray.toString());
        }
        ArrayList arraylist = new ArrayList();
        GraphApiMethod graphapimethod;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); arraylist.add(graphapimethod))
        {
            graphapimethod = new GraphApiMethod(context, "POST", (String)iterator.next(), com.facebook.katana.Constants.URL.getGraphUrl(context));
            graphapimethod.mParams.putAll(hashmap);
        }

        return arraylist;
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onPhotosPublishComplete(appsession, s, i, s1, exception, mPhotoIds, mNumSuccess, mNumFail));
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        parseResponse(jsonparser);
        for(Iterator iterator = responses.iterator(); iterator.hasNext();)
        {
            GraphRequestResponse graphrequestresponse = (GraphRequestResponse)iterator.next();
            if(graphrequestresponse.code != 200 || !graphrequestresponse.body.equals("true"))
                mNumFail = 1 + mNumFail;
            else
                mNumSuccess = 1 + mNumSuccess;
        }

    }

    private int mNumFail;
    private int mNumSuccess;
    protected final List mPhotoIds;
}
