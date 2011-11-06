// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookDeal.java

package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.facebook.katana.model:
//            FacebookStatus, FacebookDealStatus, FacebookDealHistory

public class FacebookDeal extends JMCachingDictDestination
    implements Parcelable
{

    private FacebookDeal()
    {
        mDealId = -1L;
        mPageId = -1L;
        mDisplayData = null;
        mEndTime = 0L;
        mNumClaimed = 0;
        mNumOffered = 0;
        mMinCheckin = 0;
        mMinTagging = 0;
        mDealStatus = null;
        mDealHistory = null;
    }

    protected FacebookDeal(Parcel parcel)
    {
        mDealId = parcel.readLong();
        mPageId = parcel.readLong();
        mDisplayData = new HashMap();
        parcel.readMap(mDisplayData, java/util/Map.getClassLoader());
        mEndTime = parcel.readLong();
        mNumClaimed = parcel.readInt();
        mNumOffered = parcel.readInt();
        mMinCheckin = parcel.readInt();
        mMinTagging = parcel.readInt();
        mDealStatus = (FacebookDealStatus)parcel.readParcelable(com/facebook/katana/model/FacebookStatus.getClassLoader());
        mDealHistory = (FacebookDealHistory)parcel.readParcelable(com/facebook/katana/model/FacebookDealHistory.getClassLoader());
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mDealId);
        parcel.writeLong(mPageId);
        parcel.writeMap(mDisplayData);
        parcel.writeLong(mEndTime);
        parcel.writeInt(mNumClaimed);
        parcel.writeInt(mNumOffered);
        parcel.writeInt(mMinCheckin);
        parcel.writeInt(mMinTagging);
        parcel.writeParcelable(mDealStatus, 0);
        parcel.writeParcelable(mDealHistory, 0);
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public FacebookDeal createFromParcel(Parcel parcel)
        {
            return new FacebookDeal(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public FacebookDeal[] newArray(int i)
        {
            return new FacebookDeal[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    public static final long INVALID_ID = -1L;
    public FacebookDealHistory mDealHistory;
    public final long mDealId;
    public FacebookDealStatus mDealStatus;
    public final Map mDisplayData;
    public final long mEndTime;
    public final int mMinCheckin;
    public final int mMinTagging;
    public final int mNumClaimed;
    public final int mNumOffered;
    public final long mPageId;

}
