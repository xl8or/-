// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PageInfoAdapter.java

package com.facebook.katana;

import android.content.Context;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookPageFull;
import java.util.List;

// Referenced classes of package com.facebook.katana:
//            ProfileInfoAdapter

public class PageInfoAdapter extends ProfileInfoAdapter
{

    public PageInfoAdapter(Context context, StreamPhotosCache streamphotoscache, boolean flag)
    {
        super(context, streamphotoscache, flag);
    }

    public int getItemViewType(int i)
    {
        ((ProfileInfoAdapter.Item)mItems.get(i)).mType;
        JVM INSTR tableswitch 0 2: default 44
    //                   0 48
    //                   1 44
    //                   2 53;
           goto _L1 _L2 _L1 _L3
_L1:
        byte byte0 = 2;
_L5:
        return byte0;
_L2:
        byte0 = 0;
        continue; /* Loop/switch isn't completed */
_L3:
        byte0 = 1;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public int getViewTypeCount()
    {
        return 3;
    }

    public void setPageInfo(FacebookPageFull facebookpagefull)
    {
        mItems.clear();
        if(mShowProfilePhoto)
            mItems.add(new ProfileInfoAdapter.Item(0, facebookpagefull.mDisplayName, "", facebookpagefull.mPicBig));
        if(facebookpagefull.mFounded != null && facebookpagefull.mFounded.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00ef), facebookpagefull.mFounded));
        if(facebookpagefull.mCompanyOverview != null && facebookpagefull.mCompanyOverview.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00e9), facebookpagefull.mCompanyOverview));
        if(facebookpagefull.mMission != null && facebookpagefull.mMission.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00fa), facebookpagefull.mMission));
        if(facebookpagefull.mWebsite != null && facebookpagefull.mWebsite.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(5, mContext.getString(0x7f0a0113), facebookpagefull.mWebsite));
        if(facebookpagefull.mUrl != null && facebookpagefull.mUrl.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(5, mContext.getString(0x7f0a00f5), facebookpagefull.mUrl));
        if(facebookpagefull.mFanCount > 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00ed), String.valueOf(facebookpagefull.mFanCount)));
        if(facebookpagefull.mProducts != null && facebookpagefull.mProducts.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0108), facebookpagefull.mProducts));
        if(facebookpagefull.mAttire != null && facebookpagefull.mAttire.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00e1), facebookpagefull.mAttire));
        if(facebookpagefull.mCulinaryTeam != null && facebookpagefull.mCulinaryTeam.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00ea), facebookpagefull.mCulinaryTeam));
        if(facebookpagefull.mPriceRange != null && facebookpagefull.mPriceRange.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0106), facebookpagefull.mPriceRange));
        if(facebookpagefull.mReleaseDate != null && facebookpagefull.mReleaseDate.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a010b), facebookpagefull.mReleaseDate));
        if(facebookpagefull.mGenre != null && facebookpagefull.mGenre.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00f1), facebookpagefull.mGenre));
        if(facebookpagefull.mStarring != null && facebookpagefull.mStarring.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0111), facebookpagefull.mStarring));
        if(facebookpagefull.mScreenplayBy != null && facebookpagefull.mScreenplayBy.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a010f), facebookpagefull.mScreenplayBy));
        if(facebookpagefull.mDirectedBy != null && facebookpagefull.mDirectedBy.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00ec), facebookpagefull.mDirectedBy));
        if(facebookpagefull.mProducedBy != null && facebookpagefull.mProducedBy.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0107), facebookpagefull.mProducedBy));
        if(facebookpagefull.mStudio != null && facebookpagefull.mStudio.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0112), facebookpagefull.mStudio));
        if(facebookpagefull.mAwards != null && facebookpagefull.mAwards.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00e2), facebookpagefull.mAwards));
        if(facebookpagefull.mPlotOutline != null && facebookpagefull.mPlotOutline.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0104), facebookpagefull.mPlotOutline));
        if(facebookpagefull.mNetwork != null && facebookpagefull.mNetwork.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00fe), facebookpagefull.mNetwork));
        if(facebookpagefull.mSeason != null && facebookpagefull.mSeason.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0110), facebookpagefull.mSeason));
        if(facebookpagefull.mSchedule != null && facebookpagefull.mSchedule.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a010e), facebookpagefull.mSchedule));
        if(facebookpagefull.mBandMembers != null && facebookpagefull.mBandMembers.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00e4), facebookpagefull.mBandMembers));
        if(facebookpagefull.mHomeTown != null && facebookpagefull.mHomeTown.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00f2), facebookpagefull.mHomeTown));
        if(facebookpagefull.mCurrentLocation != null && facebookpagefull.mCurrentLocation.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00eb), facebookpagefull.mCurrentLocation));
        if(facebookpagefull.mRecordLabel != null && facebookpagefull.mRecordLabel.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a010a), facebookpagefull.mRecordLabel));
        if(facebookpagefull.mBookingAgent != null && facebookpagefull.mBookingAgent.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00e7), facebookpagefull.mBookingAgent));
        if(facebookpagefull.mPressContact != null && facebookpagefull.mPressContact.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0105), facebookpagefull.mPressContact));
        if(facebookpagefull.mArtistWeLike != null && facebookpagefull.mArtistWeLike.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00e0), facebookpagefull.mArtistWeLike));
        if(facebookpagefull.mInfluences != null && facebookpagefull.mInfluences.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00f4), facebookpagefull.mInfluences));
        if(facebookpagefull.mBandInterests != null && facebookpagefull.mBandInterests.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00e3), facebookpagefull.mBandInterests));
        if(facebookpagefull.mBio != null && facebookpagefull.mBio.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00e5), facebookpagefull.mBio));
        if(facebookpagefull.mBirthday != null && facebookpagefull.mBirthday.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00e6), facebookpagefull.mBirthday));
        if(facebookpagefull.mPersonalInfo != null && facebookpagefull.mPersonalInfo.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0101), facebookpagefull.mPersonalInfo));
        if(facebookpagefull.mPersonalInterests != null && facebookpagefull.mPersonalInterests.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a0102), facebookpagefull.mPersonalInterests));
        if(facebookpagefull.mMembers != null && facebookpagefull.mMembers.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00f9), facebookpagefull.mMembers));
        if(facebookpagefull.mBuilt != null && facebookpagefull.mBuilt.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00e8), facebookpagefull.mBuilt));
        if(facebookpagefull.mFeatures != null && facebookpagefull.mFeatures.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00ee), facebookpagefull.mFeatures));
        if(facebookpagefull.mMpg != null && facebookpagefull.mMpg.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00fb), facebookpagefull.mMpg));
        if(facebookpagefull.mGeneralInfo != null && facebookpagefull.mGeneralInfo.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(1, mContext.getString(0x7f0a00f0), facebookpagefull.mGeneralInfo));
        if(facebookpagefull.mPhone != null && facebookpagefull.mPhone.length() != 0)
            mItems.add(new ProfileInfoAdapter.Item(2, mContext.getString(0x7f0a0103), facebookpagefull.mPhone));
        notifyDataSetChanged();
    }
}
