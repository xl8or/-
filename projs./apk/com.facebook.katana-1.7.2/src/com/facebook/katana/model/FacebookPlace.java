package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.katana.model.FacebookDeal;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class FacebookPlace extends JMCachingDictDestination implements Parcelable {

   public static final Creator<FacebookPlace> CREATOR = new FacebookPlace.1();
   @JMAutogen.InferredType(
      jsonFieldName = "checkin_count"
   )
   public final int mCheckinCount;
   @JMCachingDictDestination.SerializableJsonObject(
      jsonFieldName = "deal",
      type = FacebookDeal.class
   )
   protected FacebookDeal mDeal;
   @JMAutogen.InferredType(
      jsonFieldName = "description"
   )
   public final String mDescription;
   @JMAutogen.InferredType(
      jsonFieldName = "display_subtext"
   )
   public final String mDisplaySubtext;
   @JMAutogen.InferredType(
      jsonFieldName = "latitude"
   )
   public final double mLatitude;
   @JMAutogen.InferredType(
      jsonFieldName = "longitude"
   )
   public final double mLongitude;
   @JMAutogen.InferredType(
      jsonFieldName = "name"
   )
   public final String mName;
   @JMAutogen.InferredType(
      jsonFieldName = "page_id"
   )
   public final long mPageId;
   @JMCachingDictDestination.SerializableJsonObject(
      jsonFieldName = "page_info",
      type = FacebookPage.class
   )
   protected FacebookPage mPageInfo;


   private FacebookPlace() {
      this.mPageId = 65535L;
      this.mName = null;
      this.mDescription = null;
      this.mDisplaySubtext = null;
      this.mLatitude = 0.0D;
      this.mLongitude = 0.0D;
      this.mCheckinCount = 0;
   }

   protected FacebookPlace(Parcel var1) {
      long var2 = var1.readLong();
      this.mPageId = var2;
      String var4 = var1.readString();
      this.mName = var4;
      String var5 = var1.readString();
      this.mDescription = var5;
      double var6 = var1.readDouble();
      this.mLatitude = var6;
      double var8 = var1.readDouble();
      this.mLongitude = var8;
      int var10 = var1.readInt();
      this.mCheckinCount = var10;
      String var11 = var1.readString();
      this.mDisplaySubtext = var11;
      ClassLoader var12 = FacebookPage.class.getClassLoader();
      FacebookPage var13 = (FacebookPage)var1.readParcelable(var12);
      this.mPageInfo = var13;
      ClassLoader var14 = FacebookDeal.class.getClassLoader();
      FacebookDeal var15 = (FacebookDeal)var1.readParcelable(var14);
      this.mDeal = var15;
   }

   public int describeContents() {
      return 0;
   }

   public FacebookDeal getDealInfo() {
      return this.mDeal;
   }

   public FacebookPage getPageInfo() {
      return this.mPageInfo;
   }

   public void setDealInfo(FacebookDeal var1) {
      this.mDeal = var1;
   }

   public void setPageInfo(FacebookPage var1) {
      this.mPageInfo = var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.mPageId;
      var1.writeLong(var3);
      String var5 = this.mName;
      var1.writeString(var5);
      String var6 = this.mDescription;
      var1.writeString(var6);
      double var7 = this.mLatitude;
      var1.writeDouble(var7);
      double var9 = this.mLongitude;
      var1.writeDouble(var9);
      int var11 = this.mCheckinCount;
      var1.writeInt(var11);
      String var12 = this.mDisplaySubtext;
      var1.writeString(var12);
      FacebookPage var13 = this.mPageInfo;
      var1.writeParcelable(var13, 0);
      FacebookDeal var14 = this.mDeal;
      var1.writeParcelable(var14, 0);
   }

   static class 1 implements Creator<FacebookPlace> {

      1() {}

      public FacebookPlace createFromParcel(Parcel var1) {
         return new FacebookPlace(var1);
      }

      public FacebookPlace[] newArray(int var1) {
         return new FacebookPlace[var1];
      }
   }
}
