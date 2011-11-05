package com.facebook.katana;

import android.content.Context;
import com.facebook.katana.ProfileInfoAdapter;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookPageFull;
import java.util.List;

public class PageInfoAdapter extends ProfileInfoAdapter {

   public PageInfoAdapter(Context var1, StreamPhotosCache var2, boolean var3) {
      super(var1, var2, var3);
   }

   public int getItemViewType(int var1) {
      byte var2;
      switch(((ProfileInfoAdapter.Item)this.mItems.get(var1)).mType) {
      case 0:
         var2 = 0;
         break;
      case 1:
      default:
         var2 = 2;
         break;
      case 2:
         var2 = 1;
      }

      return var2;
   }

   public int getViewTypeCount() {
      return 3;
   }

   public void setPageInfo(FacebookPageFull var1) {
      this.mItems.clear();
      if(this.mShowProfilePhoto) {
         List var2 = this.mItems;
         String var3 = var1.mDisplayName;
         String var4 = var1.mPicBig;
         ProfileInfoAdapter.Item var5 = new ProfileInfoAdapter.Item(0, var3, "", var4);
         var2.add(var5);
      }

      if(var1.mFounded != null && var1.mFounded.length() != 0) {
         List var7 = this.mItems;
         String var8 = this.mContext.getString(2131362031);
         String var9 = var1.mFounded;
         ProfileInfoAdapter.Item var10 = new ProfileInfoAdapter.Item(1, var8, var9);
         var7.add(var10);
      }

      if(var1.mCompanyOverview != null && var1.mCompanyOverview.length() != 0) {
         List var12 = this.mItems;
         String var13 = this.mContext.getString(2131362025);
         String var14 = var1.mCompanyOverview;
         ProfileInfoAdapter.Item var15 = new ProfileInfoAdapter.Item(1, var13, var14);
         var12.add(var15);
      }

      if(var1.mMission != null && var1.mMission.length() != 0) {
         List var17 = this.mItems;
         String var18 = this.mContext.getString(2131362042);
         String var19 = var1.mMission;
         ProfileInfoAdapter.Item var20 = new ProfileInfoAdapter.Item(1, var18, var19);
         var17.add(var20);
      }

      if(var1.mWebsite != null && var1.mWebsite.length() != 0) {
         List var22 = this.mItems;
         String var23 = this.mContext.getString(2131362067);
         String var24 = var1.mWebsite;
         ProfileInfoAdapter.Item var25 = new ProfileInfoAdapter.Item(5, var23, var24);
         var22.add(var25);
      }

      if(var1.mUrl != null && var1.mUrl.length() != 0) {
         List var27 = this.mItems;
         String var28 = this.mContext.getString(2131362037);
         String var29 = var1.mUrl;
         ProfileInfoAdapter.Item var30 = new ProfileInfoAdapter.Item(5, var28, var29);
         var27.add(var30);
      }

      if(var1.mFanCount > 0) {
         List var32 = this.mItems;
         String var33 = this.mContext.getString(2131362029);
         String var34 = String.valueOf(var1.mFanCount);
         ProfileInfoAdapter.Item var35 = new ProfileInfoAdapter.Item(1, var33, var34);
         var32.add(var35);
      }

      if(var1.mProducts != null && var1.mProducts.length() != 0) {
         List var37 = this.mItems;
         String var38 = this.mContext.getString(2131362056);
         String var39 = var1.mProducts;
         ProfileInfoAdapter.Item var40 = new ProfileInfoAdapter.Item(1, var38, var39);
         var37.add(var40);
      }

      if(var1.mAttire != null && var1.mAttire.length() != 0) {
         List var42 = this.mItems;
         String var43 = this.mContext.getString(2131362017);
         String var44 = var1.mAttire;
         ProfileInfoAdapter.Item var45 = new ProfileInfoAdapter.Item(1, var43, var44);
         var42.add(var45);
      }

      if(var1.mCulinaryTeam != null && var1.mCulinaryTeam.length() != 0) {
         List var47 = this.mItems;
         String var48 = this.mContext.getString(2131362026);
         String var49 = var1.mCulinaryTeam;
         ProfileInfoAdapter.Item var50 = new ProfileInfoAdapter.Item(1, var48, var49);
         var47.add(var50);
      }

      if(var1.mPriceRange != null && var1.mPriceRange.length() != 0) {
         List var52 = this.mItems;
         String var53 = this.mContext.getString(2131362054);
         String var54 = var1.mPriceRange;
         ProfileInfoAdapter.Item var55 = new ProfileInfoAdapter.Item(1, var53, var54);
         var52.add(var55);
      }

      if(var1.mReleaseDate != null && var1.mReleaseDate.length() != 0) {
         List var57 = this.mItems;
         String var58 = this.mContext.getString(2131362059);
         String var59 = var1.mReleaseDate;
         ProfileInfoAdapter.Item var60 = new ProfileInfoAdapter.Item(1, var58, var59);
         var57.add(var60);
      }

      if(var1.mGenre != null && var1.mGenre.length() != 0) {
         List var62 = this.mItems;
         String var63 = this.mContext.getString(2131362033);
         String var64 = var1.mGenre;
         ProfileInfoAdapter.Item var65 = new ProfileInfoAdapter.Item(1, var63, var64);
         var62.add(var65);
      }

      if(var1.mStarring != null && var1.mStarring.length() != 0) {
         List var67 = this.mItems;
         String var68 = this.mContext.getString(2131362065);
         String var69 = var1.mStarring;
         ProfileInfoAdapter.Item var70 = new ProfileInfoAdapter.Item(1, var68, var69);
         var67.add(var70);
      }

      if(var1.mScreenplayBy != null && var1.mScreenplayBy.length() != 0) {
         List var72 = this.mItems;
         String var73 = this.mContext.getString(2131362063);
         String var74 = var1.mScreenplayBy;
         ProfileInfoAdapter.Item var75 = new ProfileInfoAdapter.Item(1, var73, var74);
         var72.add(var75);
      }

      if(var1.mDirectedBy != null && var1.mDirectedBy.length() != 0) {
         List var77 = this.mItems;
         String var78 = this.mContext.getString(2131362028);
         String var79 = var1.mDirectedBy;
         ProfileInfoAdapter.Item var80 = new ProfileInfoAdapter.Item(1, var78, var79);
         var77.add(var80);
      }

      if(var1.mProducedBy != null && var1.mProducedBy.length() != 0) {
         List var82 = this.mItems;
         String var83 = this.mContext.getString(2131362055);
         String var84 = var1.mProducedBy;
         ProfileInfoAdapter.Item var85 = new ProfileInfoAdapter.Item(1, var83, var84);
         var82.add(var85);
      }

      if(var1.mStudio != null && var1.mStudio.length() != 0) {
         List var87 = this.mItems;
         String var88 = this.mContext.getString(2131362066);
         String var89 = var1.mStudio;
         ProfileInfoAdapter.Item var90 = new ProfileInfoAdapter.Item(1, var88, var89);
         var87.add(var90);
      }

      if(var1.mAwards != null && var1.mAwards.length() != 0) {
         List var92 = this.mItems;
         String var93 = this.mContext.getString(2131362018);
         String var94 = var1.mAwards;
         ProfileInfoAdapter.Item var95 = new ProfileInfoAdapter.Item(1, var93, var94);
         var92.add(var95);
      }

      if(var1.mPlotOutline != null && var1.mPlotOutline.length() != 0) {
         List var97 = this.mItems;
         String var98 = this.mContext.getString(2131362052);
         String var99 = var1.mPlotOutline;
         ProfileInfoAdapter.Item var100 = new ProfileInfoAdapter.Item(1, var98, var99);
         var97.add(var100);
      }

      if(var1.mNetwork != null && var1.mNetwork.length() != 0) {
         List var102 = this.mItems;
         String var103 = this.mContext.getString(2131362046);
         String var104 = var1.mNetwork;
         ProfileInfoAdapter.Item var105 = new ProfileInfoAdapter.Item(1, var103, var104);
         var102.add(var105);
      }

      if(var1.mSeason != null && var1.mSeason.length() != 0) {
         List var107 = this.mItems;
         String var108 = this.mContext.getString(2131362064);
         String var109 = var1.mSeason;
         ProfileInfoAdapter.Item var110 = new ProfileInfoAdapter.Item(1, var108, var109);
         var107.add(var110);
      }

      if(var1.mSchedule != null && var1.mSchedule.length() != 0) {
         List var112 = this.mItems;
         String var113 = this.mContext.getString(2131362062);
         String var114 = var1.mSchedule;
         ProfileInfoAdapter.Item var115 = new ProfileInfoAdapter.Item(1, var113, var114);
         var112.add(var115);
      }

      if(var1.mBandMembers != null && var1.mBandMembers.length() != 0) {
         List var117 = this.mItems;
         String var118 = this.mContext.getString(2131362020);
         String var119 = var1.mBandMembers;
         ProfileInfoAdapter.Item var120 = new ProfileInfoAdapter.Item(1, var118, var119);
         var117.add(var120);
      }

      if(var1.mHomeTown != null && var1.mHomeTown.length() != 0) {
         List var122 = this.mItems;
         String var123 = this.mContext.getString(2131362034);
         String var124 = var1.mHomeTown;
         ProfileInfoAdapter.Item var125 = new ProfileInfoAdapter.Item(1, var123, var124);
         var122.add(var125);
      }

      if(var1.mCurrentLocation != null && var1.mCurrentLocation.length() != 0) {
         List var127 = this.mItems;
         String var128 = this.mContext.getString(2131362027);
         String var129 = var1.mCurrentLocation;
         ProfileInfoAdapter.Item var130 = new ProfileInfoAdapter.Item(1, var128, var129);
         var127.add(var130);
      }

      if(var1.mRecordLabel != null && var1.mRecordLabel.length() != 0) {
         List var132 = this.mItems;
         String var133 = this.mContext.getString(2131362058);
         String var134 = var1.mRecordLabel;
         ProfileInfoAdapter.Item var135 = new ProfileInfoAdapter.Item(1, var133, var134);
         var132.add(var135);
      }

      if(var1.mBookingAgent != null && var1.mBookingAgent.length() != 0) {
         List var137 = this.mItems;
         String var138 = this.mContext.getString(2131362023);
         String var139 = var1.mBookingAgent;
         ProfileInfoAdapter.Item var140 = new ProfileInfoAdapter.Item(1, var138, var139);
         var137.add(var140);
      }

      if(var1.mPressContact != null && var1.mPressContact.length() != 0) {
         List var142 = this.mItems;
         String var143 = this.mContext.getString(2131362053);
         String var144 = var1.mPressContact;
         ProfileInfoAdapter.Item var145 = new ProfileInfoAdapter.Item(1, var143, var144);
         var142.add(var145);
      }

      if(var1.mArtistWeLike != null && var1.mArtistWeLike.length() != 0) {
         List var147 = this.mItems;
         String var148 = this.mContext.getString(2131362016);
         String var149 = var1.mArtistWeLike;
         ProfileInfoAdapter.Item var150 = new ProfileInfoAdapter.Item(1, var148, var149);
         var147.add(var150);
      }

      if(var1.mInfluences != null && var1.mInfluences.length() != 0) {
         List var152 = this.mItems;
         String var153 = this.mContext.getString(2131362036);
         String var154 = var1.mInfluences;
         ProfileInfoAdapter.Item var155 = new ProfileInfoAdapter.Item(1, var153, var154);
         var152.add(var155);
      }

      if(var1.mBandInterests != null && var1.mBandInterests.length() != 0) {
         List var157 = this.mItems;
         String var158 = this.mContext.getString(2131362019);
         String var159 = var1.mBandInterests;
         ProfileInfoAdapter.Item var160 = new ProfileInfoAdapter.Item(1, var158, var159);
         var157.add(var160);
      }

      if(var1.mBio != null && var1.mBio.length() != 0) {
         List var162 = this.mItems;
         String var163 = this.mContext.getString(2131362021);
         String var164 = var1.mBio;
         ProfileInfoAdapter.Item var165 = new ProfileInfoAdapter.Item(1, var163, var164);
         var162.add(var165);
      }

      if(var1.mBirthday != null && var1.mBirthday.length() != 0) {
         List var167 = this.mItems;
         String var168 = this.mContext.getString(2131362022);
         String var169 = var1.mBirthday;
         ProfileInfoAdapter.Item var170 = new ProfileInfoAdapter.Item(1, var168, var169);
         var167.add(var170);
      }

      if(var1.mPersonalInfo != null && var1.mPersonalInfo.length() != 0) {
         List var172 = this.mItems;
         String var173 = this.mContext.getString(2131362049);
         String var174 = var1.mPersonalInfo;
         ProfileInfoAdapter.Item var175 = new ProfileInfoAdapter.Item(1, var173, var174);
         var172.add(var175);
      }

      if(var1.mPersonalInterests != null && var1.mPersonalInterests.length() != 0) {
         List var177 = this.mItems;
         String var178 = this.mContext.getString(2131362050);
         String var179 = var1.mPersonalInterests;
         ProfileInfoAdapter.Item var180 = new ProfileInfoAdapter.Item(1, var178, var179);
         var177.add(var180);
      }

      if(var1.mMembers != null && var1.mMembers.length() != 0) {
         List var182 = this.mItems;
         String var183 = this.mContext.getString(2131362041);
         String var184 = var1.mMembers;
         ProfileInfoAdapter.Item var185 = new ProfileInfoAdapter.Item(1, var183, var184);
         var182.add(var185);
      }

      if(var1.mBuilt != null && var1.mBuilt.length() != 0) {
         List var187 = this.mItems;
         String var188 = this.mContext.getString(2131362024);
         String var189 = var1.mBuilt;
         ProfileInfoAdapter.Item var190 = new ProfileInfoAdapter.Item(1, var188, var189);
         var187.add(var190);
      }

      if(var1.mFeatures != null && var1.mFeatures.length() != 0) {
         List var192 = this.mItems;
         String var193 = this.mContext.getString(2131362030);
         String var194 = var1.mFeatures;
         ProfileInfoAdapter.Item var195 = new ProfileInfoAdapter.Item(1, var193, var194);
         var192.add(var195);
      }

      if(var1.mMpg != null && var1.mMpg.length() != 0) {
         List var197 = this.mItems;
         String var198 = this.mContext.getString(2131362043);
         String var199 = var1.mMpg;
         ProfileInfoAdapter.Item var200 = new ProfileInfoAdapter.Item(1, var198, var199);
         var197.add(var200);
      }

      if(var1.mGeneralInfo != null && var1.mGeneralInfo.length() != 0) {
         List var202 = this.mItems;
         String var203 = this.mContext.getString(2131362032);
         String var204 = var1.mGeneralInfo;
         ProfileInfoAdapter.Item var205 = new ProfileInfoAdapter.Item(1, var203, var204);
         var202.add(var205);
      }

      if(var1.mPhone != null && var1.mPhone.length() != 0) {
         List var207 = this.mItems;
         String var208 = this.mContext.getString(2131362051);
         String var209 = var1.mPhone;
         ProfileInfoAdapter.Item var210 = new ProfileInfoAdapter.Item(2, var208, var209);
         var207.add(var210);
      }

      this.notifyDataSetChanged();
   }
}
