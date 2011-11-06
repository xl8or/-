// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetStream.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.*;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlMultiQuery, FqlGetProfile, FqlGetPages, FqlGetPlaces, 
//            FqlGetDeals, FqlGetDealStatus, FqlGetDealHistory, ApiMethodListener, 
//            FqlGeneratedQuery

public class FqlGetStream extends FqlMultiQuery
{
    static class FqlGetPosts extends FqlGeneratedQuery
    {

        private static String buildWhereClause(long l, long l1, String s, int i)
        {
            StringBuilder stringbuilder = new StringBuilder();
            boolean flag = false;
            if(l > 0L)
            {
                stringbuilder.append(" updated_time >= ").append(l);
                flag = true;
            }
            if(l1 > 0L)
            {
                if(flag)
                    stringbuilder.append(" AND ");
                stringbuilder.append(" updated_time <= ").append(l1);
                flag = true;
            }
            if(s != null)
            {
                if(flag)
                    stringbuilder.append(" AND ");
                stringbuilder.append(s);
            }
            if(i > 0)
                stringbuilder.append(" LIMIT ").append(i);
            return stringbuilder.toString();
        }

        private static String buildWhereClause(long al[], long l, String s, String as[], FacebookStreamType facebookstreamtype)
        {
            mType = facebookstreamtype;
            StringBuilder stringbuilder = new StringBuilder();
            boolean flag;
            if(al != null)
            {
                stringbuilder.append("source_id IN (");
                boolean flag1 = true;
                int i = al.length;
                int j = 0;
                while(j < i) 
                {
                    long l2 = al[j];
                    if(flag1)
                        flag1 = false;
                    else
                        stringbuilder.append(",");
                    stringbuilder.append(l2);
                    j++;
                }
                if(mType == FacebookStreamType.PAGE_WALL_STREAM)
                {
                    stringbuilder.append(")");
                    stringbuilder.append("AND actor_id IN (");
                    boolean flag2 = true;
                    int k = al.length;
                    int i1 = 0;
                    while(i1 < k) 
                    {
                        long l1 = al[i1];
                        if(flag2)
                            flag2 = false;
                        else
                            stringbuilder.append(",");
                        stringbuilder.append(l1);
                        i1++;
                    }
                } else
                {
                    boolean _tmp = flag1;
                    int _tmp1 = j;
                }
                stringbuilder.append(")");
                flag = true;
            } else
            {
                flag = false;
            }
            if(s != null)
            {
                if(flag)
                    stringbuilder.append(" AND ");
                stringbuilder.append(" filter_key=");
                StringUtils.appendEscapedFQLString(stringbuilder, s);
                flag = true;
            }
            if(mType != FacebookStreamType.PAGE_WALL_STREAM && as != null)
            {
                if(flag)
                    stringbuilder.append(" AND ");
                stringbuilder.append(" post_id IN (");
                StringUtils.join(stringbuilder, ", ", StringUtils.FQLEscaper, (Object[])as);
                stringbuilder.append(")");
                flag = true;
            }
            if(!flag)
                stringbuilder.append("source_id IN (SELECT target_id FROM connection WHERE source_id=").append(l).append(" AND is_following=1)");
            return stringbuilder.toString();
        }

        protected void parseJSON(JsonParser jsonparser)
            throws FacebookApiException, JsonParseException, IOException, JMException
        {
            mPosts = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FacebookPost);
        }

        private static final String TAG = "FqlGetPosts";
        private static FacebookStreamType mType;
        private List mPosts;


        protected FqlGetPosts(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, long l1, String s1, int i)
        {
            super(context, intent, s, apimethodlistener, "stream", buildWhereClause(l, l1, s1, i), com/facebook/katana/model/FacebookPost);
        }

        public FqlGetPosts(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, long l1, long al[], long l2, String s1, String as[], int i, 
                FacebookStreamType facebookstreamtype)
        {
            this(context, intent, s, apimethodlistener, l, l1, buildWhereClause(al, l2, s1, as, facebookstreamtype), i);
        }
    }


    public FqlGetStream(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, long l1, long al[], long l2, String s1, int i, FacebookStreamType facebookstreamtype)
    {
        super(context, intent, s, buildQueries(context, intent, s, al, l, l1, l2, s1, null, i, facebookstreamtype), apimethodlistener);
    }

    public FqlGetStream(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, String as[])
    {
        super(context, intent, s, buildQueries(context, intent, s, null, 0L, -1L, l, null, as, as.length, mType), apimethodlistener);
    }

    protected FqlGetStream(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, LinkedHashMap linkedhashmap)
    {
        super(context, intent, s, linkedhashmap, apimethodlistener);
    }

    protected static LinkedHashMap buildQueries(Context context, Intent intent, String s, long al[], long l, long l1, 
            long l2, String s1, String as[], int i, FacebookStreamType facebookstreamtype)
    {
        mType = facebookstreamtype;
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        linkedhashmap.put("posts", new FqlGetPosts(context, intent, s, null, l, l1, al, l2, s1, as, i, mType));
        linkedhashmap.put("profiles", new FqlGetProfile(context, intent, s, null, profileWhereClause));
        linkedhashmap.put("checkin_pages", new FqlGetPages(context, intent, s, null, checkinPagesWhereClause, com/facebook/katana/model/FacebookPage));
        linkedhashmap.put("places", new FqlGetPlaces(context, intent, s, null, placesWhereClause));
        linkedhashmap.put("deals", new FqlGetDeals(context, intent, s, null, "creator_id IN (SELECT page_id FROM #places)"));
        linkedhashmap.put("deal_status", new FqlGetDealStatus(context, intent, s, null, "promotion_id IN (SELECT promotion_id FROM #deals)"));
        linkedhashmap.put("deal_history", new FqlGetDealHistory(context, intent, s, null, "promotion_id IN (SELECT promotion_id FROM #deals)"));
        return linkedhashmap;
    }

    public List getPosts()
    {
        return new ArrayList(mPosts);
    }

    protected FacebookProfile getProfile(long l, Map map)
    {
        FacebookProfile facebookprofile1;
        if(l < 0L)
        {
            facebookprofile1 = null;
        } else
        {
            FacebookProfile facebookprofile = (FacebookProfile)map.get(Long.valueOf(l));
            if(facebookprofile != null)
                facebookprofile1 = facebookprofile;
            else
                facebookprofile1 = new FacebookProfile(l, mContext.getString(0x7f0a0067), null, 0);
        }
        return facebookprofile1;
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        super.parseJSON(jsonparser, s);
        List list = ((FqlGetPosts)getQueryByName("posts")).mPosts;
        Map map = ((FqlGetProfile)getQueryByName("profiles")).getProfiles();
        boolean flag = false;
        Map map1 = null;
        FqlGetPages fqlgetpages = (FqlGetPages)getQueryByName("checkin_pages");
        FqlGetPlaces fqlgetplaces = (FqlGetPlaces)getQueryByName("places");
        if(fqlgetpages != null && fqlgetplaces != null)
        {
            Map map2 = fqlgetpages.getPages();
            map1 = fqlgetplaces.getPlaces();
            Map map3 = ((FqlGetDeals)getQueryByName("deals")).getDeals();
            Map map4 = ((FqlGetDealStatus)getQueryByName("deal_status")).getDealStatuses();
            Map map5 = ((FqlGetDealHistory)getQueryByName("deal_history")).getDealHistories();
            FacebookPlace facebookplace;
            FacebookDeal facebookdeal;
            for(Iterator iterator2 = map1.entrySet().iterator(); iterator2.hasNext(); facebookplace.setDealInfo(facebookdeal))
            {
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator2.next();
                Long long2 = (Long)entry.getKey();
                facebookplace = (FacebookPlace)entry.getValue();
                facebookplace.setPageInfo((FacebookPage)map2.get(long2));
                facebookdeal = (FacebookDeal)map3.get(long2);
                if(facebookdeal != null)
                {
                    FacebookDealStatus facebookdealstatus = (FacebookDealStatus)map4.get(Long.valueOf(facebookdeal.mDealId));
                    facebookdeal.mDealHistory = (FacebookDealHistory)map5.get(Long.valueOf(facebookdeal.mDealId));
                    facebookdeal.mDealStatus = facebookdealstatus;
                }
            }

            flag = true;
        }
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FacebookPost facebookpost = (FacebookPost)iterator.next();
            facebookpost.setProfile(getProfile(facebookpost.actorId, map));
            facebookpost.setTargetProfile(getProfile(facebookpost.targetId, map));
            facebookpost.buildTaggedProfiles(map);
            com.facebook.katana.model.FacebookPost.Comment comment;
            for(Iterator iterator1 = facebookpost.getComments().getComments().iterator(); iterator1.hasNext(); comment.setProfile(getProfile(comment.fromId, map)))
                comment = (com.facebook.katana.model.FacebookPost.Comment)iterator1.next();

            if(flag)
            {
                com.facebook.katana.model.FacebookPost.Attachment attachment = facebookpost.getAttachment();
                if(attachment != null && attachment.mCheckinDetails != null)
                {
                    long l = attachment.mCheckinDetails.mPageId;
                    FacebookCheckinDetails facebookcheckindetails = attachment.mCheckinDetails;
                    Long long1 = Long.valueOf(l);
                    facebookcheckindetails.setPlaceInfo((FacebookPlace)map1.get(long1));
                }
            }
        } while(true);
        mPosts = list;
    }

    private static final String TAG = "FqlGetStream";
    private static String checkinPagesWhereClause = "page_id IN (SELECT page_id FROM checkin WHERE post_id IN (SELECT post_id FROM #posts))";
    private static FacebookStreamType mType;
    private static String placesWhereClause = "page_id IN (SELECT page_id FROM #checkin_pages)";
    protected static String profileWhereClause = "id IN (SELECT actor_id FROM #posts) OR id IN (SELECT target_id FROM #posts) OR id IN (SELECT comments.comment_list.fromid FROM #posts) OR id in (SELECT tagged_ids FROM #posts)";
    private List mPosts;

}
