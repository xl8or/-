// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPage.java

package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.HashMap;
import java.util.Map;

public class FacebookPage extends JMCachingDictDestination
    implements Parcelable
{

    protected FacebookPage()
    {
        mPageId = -1L;
        mDisplayName = null;
        mCanPost = false;
        mCommunityPage = false;
        mPicSmall = null;
        mPicMedium = null;
        mPicBig = null;
        mUrl = null;
        mLocation = new HashMap();
        mFanCount = -1;
    }

    protected FacebookPage(Parcel parcel)
    {
        mPageId = parcel.readLong();
        mDisplayName = parcel.readString();
        boolean flag;
        boolean flag1;
        if(parcel.readByte() != 0)
            flag = true;
        else
            flag = false;
        mCanPost = flag;
        if(parcel.readByte() != 0)
            flag1 = true;
        else
            flag1 = false;
        mCommunityPage = flag1;
        mPicSmall = parcel.readString();
        mPicMedium = parcel.readString();
        mPicBig = parcel.readString();
        mUrl = parcel.readString();
        mLocation = new HashMap();
        parcel.readMap(mLocation, java/util/Map.getClassLoader());
        mFanCount = parcel.readInt();
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(mPageId);
        parcel.writeString(mDisplayName);
        int j;
        int k;
        if(mCanPost)
            j = 1;
        else
            j = 0;
        parcel.writeByte((byte)j);
        if(mCommunityPage)
            k = 1;
        else
            k = 0;
        parcel.writeByte((byte)k);
        parcel.writeString(mPicSmall);
        parcel.writeString(mPicMedium);
        parcel.writeString(mPicBig);
        parcel.writeString(mUrl);
        parcel.writeMap(mLocation);
        parcel.writeInt(mFanCount);
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public FacebookPage createFromParcel(Parcel parcel)
        {
            return new FacebookPage(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public FacebookPage[] newArray(int i)
        {
            return new FacebookPage[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    public static final int INVALID_FAN_COUNT = -1;
    public static final long INVALID_ID = -1L;
    public final boolean mCanPost;
    public final boolean mCommunityPage;
    public final String mDisplayName;
    public final int mFanCount;
    public final Map mLocation;
    public final long mPageId;
    public final String mPicBig;
    public final String mPicMedium;
    public final String mPicSmall;
    public final String mUrl;

}
