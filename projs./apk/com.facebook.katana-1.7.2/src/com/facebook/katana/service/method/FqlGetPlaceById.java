// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetPlaceById.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.*;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlMultiQuery, ApiMethodCallback, FqlGetPlaces, FqlGetPages, 
//            FqlGetDeals, FqlGetDealStatus, FqlGetDealHistory

public class FqlGetPlaceById extends FqlMultiQuery
    implements ApiMethodCallback
{

    public FqlGetPlaceById(Context context, Intent intent, String s, long l, com.facebook.katana.binding.AppSessionListener.GetObjectListener getobjectlistener)
    {
        super(context, intent, s, buildQueries(context, intent, s, l), null);
        mId = l;
        mCallback = getobjectlistener;
    }

    protected static LinkedHashMap buildQueries(Context context, Intent intent, String s, long l)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        Object aobj[] = new Object[1];
        aobj[0] = Long.valueOf(l);
        String s1 = String.format("page_id IN ( %d )", aobj);
        linkedhashmap.put("places", new FqlGetPlaces(context, intent, s, null, s1));
        linkedhashmap.put("pages", new FqlGetPages(context, intent, s, null, s1, com/facebook/katana/model/FacebookPage));
        linkedhashmap.put("deals", new FqlGetDeals(context, intent, s, null, "creator_id IN (SELECT page_id FROM #places)"));
        linkedhashmap.put("deal_status", new FqlGetDealStatus(context, intent, s, null, "promotion_id IN (SELECT promotion_id FROM #deals)"));
        linkedhashmap.put("deal_history", new FqlGetDealHistory(context, intent, s, null, "promotion_id IN (SELECT promotion_id FROM #deals)"));
        return linkedhashmap;
    }

    public static String loadPlaceById(Context context, long l, com.facebook.katana.binding.AppSessionListener.GetObjectListener getobjectlistener)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new FqlGetPlaceById(context, null, appsession.getSessionInfo().sessionKey, l, getobjectlistener), 1001, 1001, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        if(i == 200)
        {
            mCallback.onObjectLoaded(mPlace);
        } else
        {
            if(!$assertionsDisabled && exception == null)
                throw new AssertionError();
            mCallback.onLoadError(exception);
        }
    }

    public FacebookPlace getPlace()
    {
        return mPlace;
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        super.parseJSON(jsonparser, s);
        Map map = ((FqlGetPlaces)getQueryByName("places")).getPlaces();
        Map map1 = ((FqlGetPages)getQueryByName("pages")).getPages();
        Map map2 = ((FqlGetDeals)getQueryByName("deals")).getDeals();
        Map map3 = ((FqlGetDealStatus)getQueryByName("deal_status")).getDealStatuses();
        Map map4 = ((FqlGetDealHistory)getQueryByName("deal_history")).getDealHistories();
        mPlace = (FacebookPlace)map.get(Long.valueOf(mId));
        mPlace.setPageInfo((FacebookPage)map1.get(Long.valueOf(mId)));
        if(!$assertionsDisabled && mPlace.getPageInfo() == null)
            throw new AssertionError();
        FacebookDeal facebookdeal = (FacebookDeal)map2.get(Long.valueOf(mId));
        if(facebookdeal != null)
        {
            FacebookDealStatus facebookdealstatus = (FacebookDealStatus)map3.get(Long.valueOf(facebookdeal.mDealId));
            facebookdeal.mDealHistory = (FacebookDealHistory)map4.get(Long.valueOf(facebookdeal.mDealId));
            facebookdeal.mDealStatus = facebookdealstatus;
        }
        mPlace.setDealInfo(facebookdeal);
    }

    static final boolean $assertionsDisabled = false;
    private static final String TAG = "FqlGetPlaceById";
    private com.facebook.katana.binding.AppSessionListener.GetObjectListener mCallback;
    private long mId;
    protected FacebookPlace mPlace;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/FqlGetPlaceById.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
