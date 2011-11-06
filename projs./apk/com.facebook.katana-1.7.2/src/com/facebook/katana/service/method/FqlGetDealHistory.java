// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetDealHistory.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookDealHistory;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodListener

public class FqlGetDealHistory extends FqlGeneratedQuery
{

    public FqlGetDealHistory(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
    {
        super(context, intent, s, apimethodlistener, "checkin_promotion_claim_history", s1, com/facebook/katana/model/FacebookDealHistory);
    }

    public Map getDealHistories()
    {
        return Collections.unmodifiableMap(mDealHistories);
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        List list = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FacebookDealHistory);
        mDealHistories = new LinkedHashMap();
        FacebookDealHistory facebookdealhistory;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); mDealHistories.put(Long.valueOf(facebookdealhistory.mDealId), facebookdealhistory))
            facebookdealhistory = (FacebookDealHistory)iterator.next();

    }

    private static final String TAG = "FqlGetDealHistory";
    protected Map mDealHistories;
}
