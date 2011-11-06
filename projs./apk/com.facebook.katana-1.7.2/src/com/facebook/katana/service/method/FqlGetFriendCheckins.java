// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetFriendCheckins.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.*;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlMultiQuery, FqlGetUsersProfile, FqlGetPlaces, FqlGetPages, 
//            FqlGetAppsProfile, FqlGetDeals, FqlGetDealStatus, FqlGetDealHistory, 
//            ApiMethodListener, FqlGeneratedQuery

public class FqlGetFriendCheckins extends FqlMultiQuery
{
    static class FqlGetCheckinDetails extends FqlGeneratedQuery
    {

        protected void parseJSON(JsonParser jsonparser)
            throws FacebookApiException, JsonParseException, IOException, JMException
        {
            List list = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FacebookCheckinDetails);
            mDetails = new HashMap();
            FacebookCheckinDetails facebookcheckindetails;
            for(Iterator iterator = list.iterator(); iterator.hasNext(); mDetails.put(Long.valueOf(facebookcheckindetails.mCheckinId), facebookcheckindetails))
                facebookcheckindetails = (FacebookCheckinDetails)iterator.next();

        }

        private static final String TAG = "FqlGetCheckinDetails";
        Map mDetails;

        public FqlGetCheckinDetails(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
        {
            super(context, intent, s, apimethodlistener, "checkin", s1, com/facebook/katana/model/FacebookCheckinDetails);
        }
    }

    static class FqlGetCheckins extends FqlGeneratedQuery
    {

        protected void parseJSON(JsonParser jsonparser)
            throws FacebookApiException, JsonParseException, IOException, JMException
        {
            mCheckins = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FacebookCheckin);
        }

        private static final String TAG = "FqlGetCheckins";
        List mCheckins;

        public FqlGetCheckins(Context context, Intent intent, String s, ApiMethodListener apimethodlistener)
        {
            super(context, intent, s, apimethodlistener, "checkin_activity", "filter='friend_activity'", com/facebook/katana/model/FacebookCheckin);
        }
    }


    public FqlGetFriendCheckins(Context context, Intent intent, String s, ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, buildQueries(context, intent, s), apimethodlistener);
    }

    protected static LinkedHashMap buildQueries(Context context, Intent intent, String s)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        linkedhashmap.put("checkins", new FqlGetCheckins(context, intent, s, null));
        linkedhashmap.put("details", new FqlGetCheckinDetails(context, intent, s, null, "checkin_id IN (SELECT checkin_id FROM #checkins)"));
        linkedhashmap.put("users", new FqlGetUsersProfile(context, intent, s, null, "uid IN (SELECT actor_uid FROM #checkins)", com/facebook/katana/model/FacebookUser));
        linkedhashmap.put("places", new FqlGetPlaces(context, intent, s, null, "page_id IN (SELECT page_id FROM #details)"));
        linkedhashmap.put("pages", new FqlGetPages(context, intent, s, null, "page_id IN (SELECT page_id FROM #details)", com/facebook/katana/model/FacebookPage));
        linkedhashmap.put("apps", new FqlGetAppsProfile(context, intent, s, null, "app_id IN (SELECT app_id FROM #details) AND is_facebook_app=0"));
        linkedhashmap.put("deals", new FqlGetDeals(context, intent, s, null, "creator_id IN (SELECT page_id FROM #places)"));
        linkedhashmap.put("deal_status", new FqlGetDealStatus(context, intent, s, null, "promotion_id IN (SELECT promotion_id FROM #deals)"));
        linkedhashmap.put("deal_history", new FqlGetDealHistory(context, intent, s, null, "promotion_id IN (SELECT promotion_id FROM #deals)"));
        return linkedhashmap;
    }

    public List getCheckins()
    {
        return Collections.unmodifiableList(mCheckins);
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        super.parseJSON(jsonparser, s);
        List list = ((FqlGetCheckins)getQueryByName("checkins")).mCheckins;
        Map map = ((FqlGetCheckinDetails)getQueryByName("details")).mDetails;
        Map map1 = ((FqlGetUsersProfile)getQueryByName("users")).getUsers();
        Map map2 = ((FqlGetAppsProfile)getQueryByName("apps")).getApps();
        Map map3 = ((FqlGetPlaces)getQueryByName("places")).getPlaces();
        Map map4 = ((FqlGetPages)getQueryByName("pages")).getPages();
        Map map5 = ((FqlGetDeals)getQueryByName("deals")).getDeals();
        Map map6 = ((FqlGetDealStatus)getQueryByName("deal_status")).getDealStatuses();
        Map map7 = ((FqlGetDealHistory)getQueryByName("deal_history")).getDealHistories();
        mCheckins = new ArrayList();
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FacebookCheckin facebookcheckin = (FacebookCheckin)iterator.next();
            FacebookUser facebookuser = (FacebookUser)map1.get(Long.valueOf(facebookcheckin.mActorId));
            if(facebookuser != null)
            {
                facebookcheckin.setActor(facebookuser);
                FacebookCheckinDetails facebookcheckindetails = (FacebookCheckinDetails)map.get(Long.valueOf(facebookcheckin.mCheckinId));
                if(facebookcheckindetails != null)
                {
                    facebookcheckin.setDetails(facebookcheckindetails);
                    facebookcheckindetails.setAppInfo((FacebookApp)map2.get(Long.valueOf(facebookcheckindetails.mAppId)));
                    FacebookPlace facebookplace = (FacebookPlace)map3.get(Long.valueOf(facebookcheckindetails.mPageId));
                    if(facebookplace != null)
                    {
                        facebookcheckindetails.setPlaceInfo(facebookplace);
                        facebookplace.setPageInfo((FacebookPage)map4.get(Long.valueOf(facebookcheckindetails.mPageId)));
                        FacebookDeal facebookdeal = (FacebookDeal)map5.get(Long.valueOf(facebookcheckindetails.mPageId));
                        if(facebookdeal != null)
                        {
                            FacebookDealStatus facebookdealstatus = (FacebookDealStatus)map6.get(Long.valueOf(facebookdeal.mDealId));
                            facebookdeal.mDealHistory = (FacebookDealHistory)map7.get(Long.valueOf(facebookdeal.mDealId));
                            facebookdeal.mDealStatus = facebookdealstatus;
                        }
                        facebookplace.setDealInfo(facebookdeal);
                        mCheckins.add(facebookcheckin);
                    }
                }
            }
        } while(true);
        Collections.sort(mCheckins, FacebookCheckin.checkinsByTimeComparator);
    }

    private static final String TAG = "FqlGetFriendCheckins";
    protected List mCheckins;
}
