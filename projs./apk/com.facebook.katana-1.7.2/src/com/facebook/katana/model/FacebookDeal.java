package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.katana.model.FacebookDealHistory;
import com.facebook.katana.model.FacebookDealStatus;
import com.facebook.katana.model.FacebookStatus;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.HashMap;
import java.util.Map;

public class FacebookDeal extends JMCachingDictDestination implements Parcelable {

   public static final Creator<FacebookDeal> CREATOR = new FacebookDeal.1();
   public static final long INVALID_ID = 255L;
   @JMCachingDictDestination.SerializableJsonObject(
      jsonFieldName = "deal_history",
      type = FacebookDealHistory.class
   )
   public FacebookDealHistory mDealHistory;
   @JMAutogen.InferredType(
      jsonFieldName = "promotion_id"
   )
   public final long mDealId;
   @JMCachingDictDestination.SerializableJsonObject(
      jsonFieldName = "deal_status",
      type = FacebookDealStatus.class
   )
   public FacebookDealStatus mDealStatus;
   @JMAutogen.InferredType(
      jsonFieldName = "display_data"
   )
   public final Map<String, String> mDisplayData;
   @JMAutogen.InferredType(
      jsonFieldName = "end_time"
   )
   public final long mEndTime;
   @JMAutogen.InferredType(
      jsonFieldName = "min_checkin"
   )
   public final int mMinCheckin;
   @JMAutogen.InferredType(
      jsonFieldName = "min_tagging"
   )
   public final int mMinTagging;
   @JMAutogen.InferredType(
      jsonFieldName = "num_claimed"
   )
   public final int mNumClaimed;
   @JMAutogen.InferredType(
      jsonFieldName = "num_offered"
   )
   public final int mNumOffered;
   @JMAutogen.InferredType(
      jsonFieldName = "creator_id"
   )
   public final long mPageId;


   private FacebookDeal() {
      this.mDealId = 65535L;
      this.mPageId = 65535L;
      this.mDisplayData = null;
      this.mEndTime = 0L;
      this.mNumClaimed = 0;
      this.mNumOffered = 0;
      this.mMinCheckin = 0;
      this.mMinTagging = 0;
      this.mDealStatus = null;
      this.mDealHistory = null;
   }

   protected FacebookDeal(Parcel var1) {
      long var2 = var1.readLong();
      this.mDealId = var2;
      long var4 = var1.readLong();
      this.mPageId = var4;
      HashMap var6 = new HashMap();
      this.mDisplayData = var6;
      Map var7 = this.mDisplayData;
      ClassLoader var8 = Map.class.getClassLoader();
      var1.readMap(var7, var8);
      long var9 = var1.readLong();
      this.mEndTime = var9;
      int var11 = var1.readInt();
      this.mNumClaimed = var11;
      int var12 = var1.readInt();
      this.mNumOffered = var12;
      int var13 = var1.readInt();
      this.mMinCheckin = var13;
      int var14 = var1.readInt();
      this.mMinTagging = var14;
      ClassLoader var15 = FacebookStatus.class.getClassLoader();
      FacebookDealStatus var16 = (FacebookDealStatus)var1.readParcelable(var15);
      this.mDealStatus = var16;
      ClassLoader var17 = FacebookDealHistory.class.getClassLoader();
      FacebookDealHistory var18 = (FacebookDealHistory)var1.readParcelable(var17);
      this.mDealHistory = var18;
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.mDealId;
      var1.writeLong(var3);
      long var5 = this.mPageId;
      var1.writeLong(var5);
      Map var7 = this.mDisplayData;
      var1.writeMap(var7);
      long var8 = this.mEndTime;
      var1.writeLong(var8);
      int var10 = this.mNumClaimed;
      var1.writeInt(var10);
      int var11 = this.mNumOffered;
      var1.writeInt(var11);
      int var12 = this.mMinCheckin;
      var1.writeInt(var12);
      int var13 = this.mMinTagging;
      var1.writeInt(var13);
      FacebookDealStatus var14 = this.mDealStatus;
      var1.writeParcelable(var14, 0);
      FacebookDealHistory var15 = this.mDealHistory;
      var1.writeParcelable(var15, 0);
   }

   static class 1 implements Creator<FacebookDeal> {

      1() {}

      public FacebookDeal createFromParcel(Parcel var1) {
         return new FacebookDeal(var1);
      }

      public FacebookDeal[] newArray(int var1) {
         return new FacebookDeal[var1];
      }
   }
}
