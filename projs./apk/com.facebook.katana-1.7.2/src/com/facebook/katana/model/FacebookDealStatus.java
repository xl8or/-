package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class FacebookDealStatus extends JMCachingDictDestination implements Parcelable {

   public static final Creator<FacebookDealStatus> CREATOR = new FacebookDealStatus.1();
   public static final long INVALID_ID = 255L;
   @JMAutogen.InferredType(
      jsonFieldName = "promotion_id"
   )
   public final long mDealId;
   @JMAutogen.InferredType(
      jsonFieldName = "status_code"
   )
   public final int mStatusCode;
   @JMAutogen.InferredType(
      jsonFieldName = "status_data"
   )
   public final int mStatusData;


   private FacebookDealStatus() {
      this.mDealId = 65535L;
      this.mStatusCode = 0;
      this.mStatusData = 0;
   }

   protected FacebookDealStatus(Parcel var1) {
      long var2 = var1.readLong();
      this.mDealId = var2;
      int var4 = var1.readInt();
      this.mStatusCode = var4;
      int var5 = var1.readInt();
      this.mStatusData = var5;
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.mDealId;
      var1.writeLong(var3);
      int var5 = this.mStatusCode;
      var1.writeInt(var5);
      int var6 = this.mStatusData;
      var1.writeInt(var6);
   }

   static class 1 implements Creator<FacebookDealStatus> {

      1() {}

      public FacebookDealStatus createFromParcel(Parcel var1) {
         return new FacebookDealStatus(var1);
      }

      public FacebookDealStatus[] newArray(int var1) {
         return new FacebookDealStatus[var1];
      }
   }
}
