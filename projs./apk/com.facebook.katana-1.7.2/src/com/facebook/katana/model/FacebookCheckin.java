// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookCheckin.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.Comparator;

// Referenced classes of package com.facebook.katana.model:
//            FacebookUser, FacebookCheckinDetails

public class FacebookCheckin extends JMCachingDictDestination
{

    private FacebookCheckin()
    {
        mActorId = -1L;
        mCheckinId = -1L;
    }

    public FacebookCheckin(long l, long l1)
    {
        mActorId = l;
        mCheckinId = l1;
    }

    public FacebookUser getActor()
    {
        return mActor;
    }

    public FacebookCheckinDetails getDetails()
    {
        return mCheckinDetails;
    }

    public void setActor(FacebookUser facebookuser)
    {
        mActor = facebookuser;
    }

    public void setDetails(FacebookCheckinDetails facebookcheckindetails)
    {
        mCheckinDetails = facebookcheckindetails;
    }

    public static final long INVALID_ID = -1L;
    public static final Comparator checkinsByTimeComparator = new Comparator() {

        public int compare(FacebookCheckin facebookcheckin, FacebookCheckin facebookcheckin1)
        {
            return (int)(facebookcheckin1.mCheckinDetails.mTimestamp - facebookcheckin.mCheckinDetails.mTimestamp);
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((FacebookCheckin)obj, (FacebookCheckin)obj1);
        }

    }
;
    protected FacebookUser mActor;
    public final long mActorId;
    protected FacebookCheckinDetails mCheckinDetails;
    public final long mCheckinId;

}
