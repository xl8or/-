package com.google.android.vending.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.Collections;
import java.util.List;

public class Asset implements Parcelable {

   public static Creator<Asset> CREATOR = new Asset.1();
   private final VendingProtos.ExternalAssetProto mProto;


   public Asset(VendingProtos.ExternalAssetProto var1) {
      this.mProto = var1;
   }

   public int describeContents() {
      return 0;
   }

   public double getAverageRating() {
      return Double.parseDouble(this.mProto.getAverageRating());
   }

   public String getDevName() {
      return this.mProto.getOwner();
   }

   public String getId() {
      return this.mProto.getId();
   }

   public String getPackageName() {
      return this.mProto.getPackageName();
   }

   public List<String> getPermissions() {
      return Collections.unmodifiableList(this.mProto.getExtendedInfo().getApplicationPermissionIdList());
   }

   public long getRatingCount() {
      return this.mProto.getNumRatings();
   }

   public String getTitle() {
      return this.mProto.getTitle();
   }

   public long getVersionCode() {
      return (long)this.mProto.getVersionCode();
   }

   public boolean hasRating() {
      return this.mProto.hasAverageRating();
   }

   public void writeToParcel(Parcel var1, int var2) {
      ParcelableProto var3 = ParcelableProto.forProto(this.mProto);
      var1.writeParcelable(var3, var2);
   }

   static class 1 implements Creator<Asset> {

      1() {}

      public Asset createFromParcel(Parcel var1) {
         ClassLoader var2 = ParcelableProto.class.getClassLoader();
         VendingProtos.ExternalAssetProto var3 = (VendingProtos.ExternalAssetProto)ParcelableProto.getProtoFromParcel(var1, var2);
         return new Asset(var3);
      }

      public Asset[] newArray(int var1) {
         return new Asset[var1];
      }
   }
}
