package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class FacebookDealHistory extends JMCachingDictDestination implements Parcelable {

   public static final Creator<FacebookDealHistory> CREATOR = new FacebookDealHistory.1();
   public static final long INVALID_ID = 255L;
   @JMAutogen.InferredType(
      jsonFieldName = "claim_id"
   )
   public final int mClaimId;
   @JMAutogen.InferredType(
      jsonFieldName = "claim_time"
   )
   public final long mClaimTime;
   @JMAutogen.InferredType(
      jsonFieldName = "promotion_id"
   )
   public final long mDealId;
   @JMAutogen.InferredType(
      jsonFieldName = "expiration_time"
   )
   public final long mExpirationTime;


   private FacebookDealHistory() {
      this.mDealId = 65535L;
      this.mClaimTime = 0L;
      this.mExpirationTime = 0L;
      this.mClaimId = 0;
   }

   protected FacebookDealHistory(Parcel var1) {
      long var2 = var1.readLong();
      this.mDealId = var2;
      long var4 = var1.readLong();
      this.mClaimTime = var4;
      long var6 = var1.readLong();
      this.mExpirationTime = var6;
      int var8 = var1.readInt();
      this.mClaimId = var8;
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.mDealId;
      var1.writeLong(var3);
      long var5 = this.mClaimTime;
      var1.writeLong(var5);
      long var7 = this.mExpirationTime;
      var1.writeLong(var7);
      int var9 = this.mClaimId;
      var1.writeInt(var9);
   }

   static class 1 implements Creator<FacebookDealHistory> {

      1() {}

      public FacebookDealHistory createFromParcel(Parcel var1) {
         return new FacebookDealHistory(var1);
      }

      public FacebookDealHistory[] newArray(int var1) {
         return new FacebookDealHistory[var1];
      }
   }
}
