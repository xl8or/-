// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetPlacesNearby.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.model.*;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlMultiQuery, FqlGetPlaces, FqlGetPages, FqlGetDeals, 
//            FqlGetDealStatus, FqlGetDealHistory, FqlGetNearbyRegions, ApiMethodListener

public class FqlGetPlacesNearby extends FqlMultiQuery
{

    public FqlGetPlacesNearby(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, Location location1, double d, 
            String s1, int i, NetworkRequestCallback networkrequestcallback)
    {
        super(context, intent, s, buildQueries(context, intent, s, location1.getLatitude(), location1.getLongitude(), d, location1.getAccuracy(), s1, i), apimethodlistener);
        location = location1;
        maxDistance = d;
        filter = s1;
        resultLimit = i;
        callback = networkrequestcallback;
    }

    private static String buildDistanceFunction(double d, double d1, double d2)
    {
        Locale locale = (Locale)null;
        Object aobj[] = new Object[3];
        aobj[0] = Double.valueOf(d);
        aobj[1] = Double.valueOf(d1);
        aobj[2] = Double.valueOf(d2);
        return String.format(locale, "distance(latitude, longitude, \"%f\", \"%f\", \"%f\")", aobj);
    }

    protected static LinkedHashMap buildQueries(Context context, Intent intent, String s, double d, double d1, double d2, double d3, String s1, int i)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        linkedhashmap.put("places", new FqlGetPlaces(context, intent, s, null, buildWhereClause(d, d1, d2, d3, s1, i)));
        linkedhashmap.put("pages", new FqlGetPages(context, intent, s, null, "page_id IN (SELECT page_id FROM #places)", com/facebook/katana/model/FacebookPage));
        linkedhashmap.put("deals", new FqlGetDeals(context, intent, s, null, "creator_id IN (SELECT page_id FROM #places)"));
        linkedhashmap.put("deal_status", new FqlGetDealStatus(context, intent, s, null, "promotion_id IN (SELECT promotion_id FROM #deals)"));
        linkedhashmap.put("deal_history", new FqlGetDealHistory(context, intent, s, null, "promotion_id IN (SELECT promotion_id FROM #deals)"));
        Locale locale = (Locale)null;
        Object aobj[] = new Object[4];
        aobj[0] = Double.valueOf(d);
        aobj[1] = Double.valueOf(d1);
        aobj[2] = com.facebook.katana.model.GeoRegion.Type.city;
        aobj[3] = com.facebook.katana.model.GeoRegion.Type.state;
        linkedhashmap.put("nearby_regions", new FqlGetNearbyRegions(context, intent, s, null, String.format(locale, "latitude='%f' and longitude='%f' and type in ('%s','%s')", aobj)));
        return linkedhashmap;
    }

    private static String buildWhereClause(double d, double d1, double d2, double d3, 
            String s, int i)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(buildDistanceFunction(d, d1, d3));
        stringbuilder.append("< \"");
        stringbuilder.append(d2);
        stringbuilder.append('"');
        if(s != null && s.length() > 0)
        {
            stringbuilder.append(" AND CONTAINS (");
            StringUtils.appendEscapedFQLString(stringbuilder, s);
            stringbuilder.append(")");
        }
        stringbuilder.append(" LIMIT ");
        stringbuilder.append(i);
        return stringbuilder.toString();
    }

    public List getPlaces()
    {
        List list;
        if(mPlaces != null)
            list = Collections.unmodifiableList(mPlaces);
        else
            list = null;
        return list;
    }

    public List getRegions()
    {
        List list;
        if(mPlaces != null)
            list = Collections.unmodifiableList(mRegions);
        else
            list = null;
        return list;
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
        mRegions = ((FqlGetNearbyRegions)getQueryByName("nearby_regions")).regions;
        mPlaces = new ArrayList();
        java.util.Map.Entry entry;
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); mPlaces.add(entry.getValue()))
        {
            entry = (java.util.Map.Entry)iterator.next();
            FacebookPage facebookpage = (FacebookPage)map1.get(entry.getKey());
            FacebookDeal facebookdeal = (FacebookDeal)map2.get(entry.getKey());
            if(facebookdeal != null)
            {
                FacebookDealStatus facebookdealstatus = (FacebookDealStatus)map3.get(Long.valueOf(facebookdeal.mDealId));
                facebookdeal.mDealHistory = (FacebookDealHistory)map4.get(Long.valueOf(facebookdeal.mDealId));
                facebookdeal.mDealStatus = facebookdealstatus;
            }
            ((FacebookPlace)entry.getValue()).setPageInfo(facebookpage);
            ((FacebookPlace)entry.getValue()).setDealInfo(facebookdeal);
        }

    }

    private static final String TAG = "FqlGetPlacesNearby";
    public NetworkRequestCallback callback;
    public String filter;
    public Location location;
    protected List mPlaces;
    protected List mRegions;
    public double maxDistance;
    public int resultLimit;
}
