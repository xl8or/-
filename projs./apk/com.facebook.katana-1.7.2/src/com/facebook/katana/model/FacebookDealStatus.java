// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookDealStatus.java

package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class FacebookDealStatus extends JMCachingDictDestination
    implements Parcelable
{

    private FacebookDealStatus()
    {
        mDealId = -1L;
        mStatusCode = 0;
        mStatusData = 0;
    }

    protected FacebookDealStatus(Parcel parcel)
    {
        mDealId = parcel.readLong();
        mStatusCode = parcel.readInt();
        mStatusData = parcel.readInt();
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mDealId);
        parcel.writeInt(mStatusCode);
        parcel.writeInt(mStatusData);
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public FacebookDealStatus createFromParcel(Parcel parcel)
        {
            return new FacebookDealStatus(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public FacebookDealStatus[] newArray(int i)
        {
            return new FacebookDealStatus[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    public static final long INVALID_ID = -1L;
    public final long mDealId;
    public final int mStatusCode;
    public final int mStatusData;

}
