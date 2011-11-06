// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookCheckinDetails.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.Collections;
import java.util.List;

// Referenced classes of package com.facebook.katana.model:
//            FacebookApp, FacebookPlace

public class FacebookCheckinDetails extends JMCachingDictDestination
{

    private FacebookCheckinDetails()
    {
        mCheckinId = -1L;
        mAuthorId = -1L;
        mPageId = -1L;
        mTimestamp = 0L;
        mTaggedUids = Collections.EMPTY_LIST;
        mAppId = 0L;
    }

    public FacebookCheckinDetails(long l, long l1, long l2, long l3, List list, long l4)
    {
        mCheckinId = l;
        mAuthorId = l1;
        mPageId = l2;
        mTimestamp = l3;
        mTaggedUids = list;
        mAppId = l4;
    }

    public FacebookApp getAppInfo()
    {
        return mAppInfo;
    }

    public FacebookPlace getPlaceInfo()
    {
        return mPlaceInfo;
    }

    public void setAppInfo(FacebookApp facebookapp)
    {
        mAppInfo = facebookapp;
    }

    public void setPlaceInfo(FacebookPlace facebookplace)
    {
        mPlaceInfo = facebookplace;
    }

    public final long mAppId;
    protected FacebookApp mAppInfo;
    public final long mAuthorId;
    public final long mCheckinId;
    public final long mPageId;
    protected FacebookPlace mPlaceInfo;
    public List mTaggedUids;
    public final long mTimestamp;
}
