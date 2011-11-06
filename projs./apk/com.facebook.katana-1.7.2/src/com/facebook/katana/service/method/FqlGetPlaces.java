// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetPlaces.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodListener

class FqlGetPlaces extends FqlGeneratedQuery
{

    public FqlGetPlaces(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
    {
        super(context, intent, s, apimethodlistener, "place", s1, com/facebook/katana/model/FacebookPlace);
    }

    public Map getPlaces()
    {
        return Collections.unmodifiableMap(mPlaces);
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        List list = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FacebookPlace);
        mPlaces = new LinkedHashMap();
        FacebookPlace facebookplace;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); mPlaces.put(Long.valueOf(facebookplace.mPageId), facebookplace))
            facebookplace = (FacebookPlace)iterator.next();

    }

    private static final String TAG = "FqlGetPlaces";
    private Map mPlaces;
}
