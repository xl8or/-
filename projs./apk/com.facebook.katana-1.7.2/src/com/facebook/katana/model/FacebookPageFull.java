package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.util.jsonmirror.JMAutogen;

public class FacebookPageFull extends FacebookPage implements Parcelable {

   public static final Creator<FacebookPage> CREATOR = new FacebookPageFull.1();
   @JMAutogen.InferredType(
      jsonFieldName = "artists_we_like"
   )
   public final String mArtistWeLike;
   @JMAutogen.InferredType(
      jsonFieldName = "attire"
   )
   public final String mAttire;
   @JMAutogen.InferredType(
      jsonFieldName = "awards"
   )
   public final String mAwards;
   @JMAutogen.InferredType(
      jsonFieldName = "band_interests"
   )
   public final String mBandInterests;
   @JMAutogen.InferredType(
      jsonFieldName = "band_members"
   )
   public final String mBandMembers;
   @JMAutogen.InferredType(
      jsonFieldName = "bio"
   )
   public final String mBio;
   @JMAutogen.InferredType(
      jsonFieldName = "birthday"
   )
   public final String mBirthday;
   @JMAutogen.InferredType(
      jsonFieldName = "booking_agent"
   )
   public final String mBookingAgent;
   @JMAutogen.InferredType(
      jsonFieldName = "built"
   )
   public final String mBuilt;
   @JMAutogen.InferredType(
      jsonFieldName = "company_overview"
   )
   public final String mCompanyOverview;
   @JMAutogen.InferredType(
      jsonFieldName = "culinary_team"
   )
   public final String mCulinaryTeam;
   @JMAutogen.InferredType(
      jsonFieldName = "current_location"
   )
   public final String mCurrentLocation;
   @JMAutogen.InferredType(
      jsonFieldName = "directed_by"
   )
   public final String mDirectedBy;
   @JMAutogen.InferredType(
      jsonFieldName = "features"
   )
   public final String mFeatures;
   @JMAutogen.InferredType(
      jsonFieldName = "founded"
   )
   public final String mFounded;
   @JMAutogen.InferredType(
      jsonFieldName = "general_info"
   )
   public final String mGeneralInfo;
   @JMAutogen.InferredType(
      jsonFieldName = "genre"
   )
   public final String mGenre;
   @JMAutogen.InferredType(
      jsonFieldName = "hometown"
   )
   public final String mHomeTown;
   @JMAutogen.InferredType(
      jsonFieldName = "influences"
   )
   public final String mInfluences;
   @JMAutogen.InferredType(
      jsonFieldName = "members"
   )
   public final String mMembers;
   @JMAutogen.InferredType(
      jsonFieldName = "mission"
   )
   public final String mMission;
   @JMAutogen.InferredType(
      jsonFieldName = "mpg"
   )
   public final String mMpg;
   @JMAutogen.InferredType(
      jsonFieldName = "network"
   )
   public final String mNetwork;
   @JMAutogen.InferredType(
      jsonFieldName = "personal_info"
   )
   public final String mPersonalInfo;
   @JMAutogen.InferredType(
      jsonFieldName = "personal_interests"
   )
   public final String mPersonalInterests;
   @JMAutogen.InferredType(
      jsonFieldName = "phone"
   )
   public final String mPhone;
   @JMAutogen.InferredType(
      jsonFieldName = "plot_outline"
   )
   public final String mPlotOutline;
   @JMAutogen.InferredType(
      jsonFieldName = "press_contact"
   )
   public final String mPressContact;
   @JMAutogen.InferredType(
      jsonFieldName = "price_range"
   )
   public final String mPriceRange;
   @JMAutogen.InferredType(
      jsonFieldName = "produced_by"
   )
   public final String mProducedBy;
   @JMAutogen.InferredType(
      jsonFieldName = "products"
   )
   public final String mProducts;
   @JMAutogen.InferredType(
      jsonFieldName = "record_label"
   )
   public final String mRecordLabel;
   @JMAutogen.InferredType(
      jsonFieldName = "release_date"
   )
   public final String mReleaseDate;
   @JMAutogen.InferredType(
      jsonFieldName = "schedule"
   )
   public final String mSchedule;
   @JMAutogen.InferredType(
      jsonFieldName = "screenplay_by"
   )
   public final String mScreenplayBy;
   @JMAutogen.InferredType(
      jsonFieldName = "season"
   )
   public final String mSeason;
   @JMAutogen.InferredType(
      jsonFieldName = "starring"
   )
   public final String mStarring;
   @JMAutogen.InferredType(
      jsonFieldName = "studio"
   )
   public final String mStudio;
   @JMAutogen.InferredType(
      jsonFieldName = "website"
   )
   public final String mWebsite;


   public FacebookPageFull() {
      this.mWebsite = null;
      this.mFounded = null;
      this.mCompanyOverview = null;
      this.mMission = null;
      this.mProducts = null;
      this.mAttire = null;
      this.mCulinaryTeam = null;
      this.mPriceRange = null;
      this.mReleaseDate = null;
      this.mGenre = null;
      this.mStarring = null;
      this.mScreenplayBy = null;
      this.mDirectedBy = null;
      this.mProducedBy = null;
      this.mStudio = null;
      this.mAwards = null;
      this.mPlotOutline = null;
      this.mNetwork = null;
      this.mSeason = null;
      this.mSchedule = null;
      this.mBandMembers = null;
      this.mHomeTown = null;
      this.mCurrentLocation = null;
      this.mRecordLabel = null;
      this.mBookingAgent = null;
      this.mPressContact = null;
      this.mArtistWeLike = null;
      this.mInfluences = null;
      this.mBandInterests = null;
      this.mBio = null;
      this.mBirthday = null;
      this.mPersonalInfo = null;
      this.mPersonalInterests = null;
      this.mMembers = null;
      this.mBuilt = null;
      this.mFeatures = null;
      this.mMpg = null;
      this.mGeneralInfo = null;
      this.mPhone = null;
   }

   protected FacebookPageFull(Parcel var1) {
      super(var1);
      String var2 = var1.readString();
      this.mWebsite = var2;
      String var3 = var1.readString();
      this.mFounded = var3;
      String var4 = var1.readString();
      this.mCompanyOverview = var4;
      String var5 = var1.readString();
      this.mMission = var5;
      String var6 = var1.readString();
      this.mProducts = var6;
      String var7 = var1.readString();
      this.mAttire = var7;
      String var8 = var1.readString();
      this.mCulinaryTeam = var8;
      String var9 = var1.readString();
      this.mPriceRange = var9;
      String var10 = var1.readString();
      this.mReleaseDate = var10;
      String var11 = var1.readString();
      this.mGenre = var11;
      String var12 = var1.readString();
      this.mStarring = var12;
      String var13 = var1.readString();
      this.mScreenplayBy = var13;
      String var14 = var1.readString();
      this.mDirectedBy = var14;
      String var15 = var1.readString();
      this.mProducedBy = var15;
      String var16 = var1.readString();
      this.mStudio = var16;
      String var17 = var1.readString();
      this.mAwards = var17;
      String var18 = var1.readString();
      this.mPlotOutline = var18;
      String var19 = var1.readString();
      this.mNetwork = var19;
      String var20 = var1.readString();
      this.mSeason = var20;
      String var21 = var1.readString();
      this.mSchedule = var21;
      String var22 = var1.readString();
      this.mBandMembers = var22;
      String var23 = var1.readString();
      this.mHomeTown = var23;
      String var24 = var1.readString();
      this.mCurrentLocation = var24;
      String var25 = var1.readString();
      this.mRecordLabel = var25;
      String var26 = var1.readString();
      this.mBookingAgent = var26;
      String var27 = var1.readString();
      this.mPressContact = var27;
      String var28 = var1.readString();
      this.mArtistWeLike = var28;
      String var29 = var1.readString();
      this.mInfluences = var29;
      String var30 = var1.readString();
      this.mBandInterests = var30;
      String var31 = var1.readString();
      this.mBio = var31;
      String var32 = var1.readString();
      this.mBirthday = var32;
      String var33 = var1.readString();
      this.mPersonalInfo = var33;
      String var34 = var1.readString();
      this.mPersonalInterests = var34;
      String var35 = var1.readString();
      this.mMembers = var35;
      String var36 = var1.readString();
      this.mBuilt = var36;
      String var37 = var1.readString();
      this.mFeatures = var37;
      String var38 = var1.readString();
      this.mMpg = var38;
      String var39 = var1.readString();
      this.mGeneralInfo = var39;
      String var40 = var1.readString();
      this.mPhone = var40;
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      super.writeToParcel(var1, var2);
      String var3 = this.mWebsite;
      var1.writeString(var3);
      String var4 = this.mFounded;
      var1.writeString(var4);
      String var5 = this.mCompanyOverview;
      var1.writeString(var5);
      String var6 = this.mMission;
      var1.writeString(var6);
      String var7 = this.mProducts;
      var1.writeString(var7);
      String var8 = this.mAttire;
      var1.writeString(var8);
      String var9 = this.mCulinaryTeam;
      var1.writeString(var9);
      String var10 = this.mPriceRange;
      var1.writeString(var10);
      String var11 = this.mReleaseDate;
      var1.writeString(var11);
      String var12 = this.mGenre;
      var1.writeString(var12);
      String var13 = this.mStarring;
      var1.writeString(var13);
      String var14 = this.mScreenplayBy;
      var1.writeString(var14);
      String var15 = this.mDirectedBy;
      var1.writeString(var15);
      String var16 = this.mProducedBy;
      var1.writeString(var16);
      String var17 = this.mStudio;
      var1.writeString(var17);
      String var18 = this.mAwards;
      var1.writeString(var18);
      String var19 = this.mPlotOutline;
      var1.writeString(var19);
      String var20 = this.mNetwork;
      var1.writeString(var20);
      String var21 = this.mSeason;
      var1.writeString(var21);
      String var22 = this.mSchedule;
      var1.writeString(var22);
      String var23 = this.mBandMembers;
      var1.writeString(var23);
      String var24 = this.mHomeTown;
      var1.writeString(var24);
      String var25 = this.mCurrentLocation;
      var1.writeString(var25);
      String var26 = this.mRecordLabel;
      var1.writeString(var26);
      String var27 = this.mBookingAgent;
      var1.writeString(var27);
      String var28 = this.mPressContact;
      var1.writeString(var28);
      String var29 = this.mArtistWeLike;
      var1.writeString(var29);
      String var30 = this.mInfluences;
      var1.writeString(var30);
      String var31 = this.mBandInterests;
      var1.writeString(var31);
      String var32 = this.mBio;
      var1.writeString(var32);
      String var33 = this.mBirthday;
      var1.writeString(var33);
      String var34 = this.mPersonalInfo;
      var1.writeString(var34);
      String var35 = this.mPersonalInterests;
      var1.writeString(var35);
      String var36 = this.mMembers;
      var1.writeString(var36);
      String var37 = this.mBuilt;
      var1.writeString(var37);
      String var38 = this.mFeatures;
      var1.writeString(var38);
      String var39 = this.mMpg;
      var1.writeString(var39);
      String var40 = this.mGeneralInfo;
      var1.writeString(var40);
      String var41 = this.mPhone;
      var1.writeString(var41);
   }

   static class 1 implements Creator<FacebookPage> {

      1() {}

      public FacebookPage createFromParcel(Parcel var1) {
         return new FacebookPage(var1);
      }

      public FacebookPage[] newArray(int var1) {
         return new FacebookPage[var1];
      }
   }
}
