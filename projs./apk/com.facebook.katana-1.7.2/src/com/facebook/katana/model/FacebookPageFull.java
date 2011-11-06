// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPageFull.java

package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;

// Referenced classes of package com.facebook.katana.model:
//            FacebookPage

public class FacebookPageFull extends FacebookPage
    implements Parcelable
{

    public FacebookPageFull()
    {
        mWebsite = null;
        mFounded = null;
        mCompanyOverview = null;
        mMission = null;
        mProducts = null;
        mAttire = null;
        mCulinaryTeam = null;
        mPriceRange = null;
        mReleaseDate = null;
        mGenre = null;
        mStarring = null;
        mScreenplayBy = null;
        mDirectedBy = null;
        mProducedBy = null;
        mStudio = null;
        mAwards = null;
        mPlotOutline = null;
        mNetwork = null;
        mSeason = null;
        mSchedule = null;
        mBandMembers = null;
        mHomeTown = null;
        mCurrentLocation = null;
        mRecordLabel = null;
        mBookingAgent = null;
        mPressContact = null;
        mArtistWeLike = null;
        mInfluences = null;
        mBandInterests = null;
        mBio = null;
        mBirthday = null;
        mPersonalInfo = null;
        mPersonalInterests = null;
        mMembers = null;
        mBuilt = null;
        mFeatures = null;
        mMpg = null;
        mGeneralInfo = null;
        mPhone = null;
    }

    protected FacebookPageFull(Parcel parcel)
    {
        super(parcel);
        mWebsite = parcel.readString();
        mFounded = parcel.readString();
        mCompanyOverview = parcel.readString();
        mMission = parcel.readString();
        mProducts = parcel.readString();
        mAttire = parcel.readString();
        mCulinaryTeam = parcel.readString();
        mPriceRange = parcel.readString();
        mReleaseDate = parcel.readString();
        mGenre = parcel.readString();
        mStarring = parcel.readString();
        mScreenplayBy = parcel.readString();
        mDirectedBy = parcel.readString();
        mProducedBy = parcel.readString();
        mStudio = parcel.readString();
        mAwards = parcel.readString();
        mPlotOutline = parcel.readString();
        mNetwork = parcel.readString();
        mSeason = parcel.readString();
        mSchedule = parcel.readString();
        mBandMembers = parcel.readString();
        mHomeTown = parcel.readString();
        mCurrentLocation = parcel.readString();
        mRecordLabel = parcel.readString();
        mBookingAgent = parcel.readString();
        mPressContact = parcel.readString();
        mArtistWeLike = parcel.readString();
        mInfluences = parcel.readString();
        mBandInterests = parcel.readString();
        mBio = parcel.readString();
        mBirthday = parcel.readString();
        mPersonalInfo = parcel.readString();
        mPersonalInterests = parcel.readString();
        mMembers = parcel.readString();
        mBuilt = parcel.readString();
        mFeatures = parcel.readString();
        mMpg = parcel.readString();
        mGeneralInfo = parcel.readString();
        mPhone = parcel.readString();
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        super.writeToParcel(parcel, i);
        parcel.writeString(mWebsite);
        parcel.writeString(mFounded);
        parcel.writeString(mCompanyOverview);
        parcel.writeString(mMission);
        parcel.writeString(mProducts);
        parcel.writeString(mAttire);
        parcel.writeString(mCulinaryTeam);
        parcel.writeString(mPriceRange);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mGenre);
        parcel.writeString(mStarring);
        parcel.writeString(mScreenplayBy);
        parcel.writeString(mDirectedBy);
        parcel.writeString(mProducedBy);
        parcel.writeString(mStudio);
        parcel.writeString(mAwards);
        parcel.writeString(mPlotOutline);
        parcel.writeString(mNetwork);
        parcel.writeString(mSeason);
        parcel.writeString(mSchedule);
        parcel.writeString(mBandMembers);
        parcel.writeString(mHomeTown);
        parcel.writeString(mCurrentLocation);
        parcel.writeString(mRecordLabel);
        parcel.writeString(mBookingAgent);
        parcel.writeString(mPressContact);
        parcel.writeString(mArtistWeLike);
        parcel.writeString(mInfluences);
        parcel.writeString(mBandInterests);
        parcel.writeString(mBio);
        parcel.writeString(mBirthday);
        parcel.writeString(mPersonalInfo);
        parcel.writeString(mPersonalInterests);
        parcel.writeString(mMembers);
        parcel.writeString(mBuilt);
        parcel.writeString(mFeatures);
        parcel.writeString(mMpg);
        parcel.writeString(mGeneralInfo);
        parcel.writeString(mPhone);
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
    public final String mArtistWeLike;
    public final String mAttire;
    public final String mAwards;
    public final String mBandInterests;
    public final String mBandMembers;
    public final String mBio;
    public final String mBirthday;
    public final String mBookingAgent;
    public final String mBuilt;
    public final String mCompanyOverview;
    public final String mCulinaryTeam;
    public final String mCurrentLocation;
    public final String mDirectedBy;
    public final String mFeatures;
    public final String mFounded;
    public final String mGeneralInfo;
    public final String mGenre;
    public final String mHomeTown;
    public final String mInfluences;
    public final String mMembers;
    public final String mMission;
    public final String mMpg;
    public final String mNetwork;
    public final String mPersonalInfo;
    public final String mPersonalInterests;
    public final String mPhone;
    public final String mPlotOutline;
    public final String mPressContact;
    public final String mPriceRange;
    public final String mProducedBy;
    public final String mProducts;
    public final String mRecordLabel;
    public final String mReleaseDate;
    public final String mSchedule;
    public final String mScreenplayBy;
    public final String mSeason;
    public final String mStarring;
    public final String mStudio;
    public final String mWebsite;

}
