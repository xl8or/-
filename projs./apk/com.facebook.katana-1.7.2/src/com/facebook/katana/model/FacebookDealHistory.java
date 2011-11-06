// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookDealHistory.java

package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class FacebookDealHistory extends JMCachingDictDestination
    implements Parcelable
{

    private FacebookDealHistory()
    {
        mDealId = -1L;
        mClaimTime = 0L;
        mExpirationTime = 0L;
        mClaimId = 0;
    }

    protected FacebookDealHistory(Parcel parcel)
    {
        mDealId = parcel.readLong();
        mClaimTime = parcel.readLong();
        mExpirationTime = parcel.readLong();
        mClaimId = parcel.readInt();
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mDealId);
        parcel.writeLong(mClaimTime);
        parcel.writeLong(mExpirationTime);
        parcel.writeInt(mClaimId);
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public FacebookDealHistory createFromParcel(Parcel parcel)
        {
            return new FacebookDealHistory(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public FacebookDealHistory[] newArray(int i)
        {
            return new FacebookDealHistory[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    public static final long INVALID_ID = -1L;
    public final int mClaimId;
    public final long mClaimTime;
    public final long mDealId;
    public final long mExpirationTime;

}
