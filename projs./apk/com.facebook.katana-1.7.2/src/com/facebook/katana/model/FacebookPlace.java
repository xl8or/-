// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPlace.java

package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

// Referenced classes of package com.facebook.katana.model:
//            FacebookPage, FacebookDeal

public class FacebookPlace extends JMCachingDictDestination
    implements Parcelable
{

    private FacebookPlace()
    {
        mPageId = -1L;
        mName = null;
        mDescription = null;
        mDisplaySubtext = null;
        mLatitude = 0D;
        mLongitude = 0D;
        mCheckinCount = 0;
    }

    protected FacebookPlace(Parcel parcel)
    {
        mPageId = parcel.readLong();
        mName = parcel.readString();
        mDescription = parcel.readString();
        mLatitude = parcel.readDouble();
        mLongitude = parcel.readDouble();
        mCheckinCount = parcel.readInt();
        mDisplaySubtext = parcel.readString();
        mPageInfo = (FacebookPage)parcel.readParcelable(com/facebook/katana/model/FacebookPage.getClassLoader());
        mDeal = (FacebookDeal)parcel.readParcelable(com/facebook/katana/model/FacebookDeal.getClassLoader());
    }

    public int describeContents()
    {
        return 0;
    }

    public FacebookDeal getDealInfo()
    {
        return mDeal;
    }

    public FacebookPage getPageInfo()
    {
        return mPageInfo;
    }

    public void setDealInfo(FacebookDeal facebookdeal)
    {
        mDeal = facebookdeal;
    }

    public void setPageInfo(FacebookPage facebookpage)
    {
        mPageInfo = facebookpage;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mPageId);
        parcel.writeString(mName);
        parcel.writeString(mDescription);
        parcel.writeDouble(mLatitude);
        parcel.writeDouble(mLongitude);
        parcel.writeInt(mCheckinCount);
        parcel.writeString(mDisplaySubtext);
        parcel.writeParcelable(mPageInfo, 0);
        parcel.writeParcelable(mDeal, 0);
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public FacebookPlace createFromParcel(Parcel parcel)
        {
            return new FacebookPlace(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public FacebookPlace[] newArray(int i)
        {
            return new FacebookPlace[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    public final int mCheckinCount;
    protected FacebookDeal mDeal;
    public final String mDescription;
    public final String mDisplaySubtext;
    public final double mLatitude;
    public final double mLongitude;
    public final String mName;
    public final long mPageId;
    protected FacebookPage mPageInfo;

}
