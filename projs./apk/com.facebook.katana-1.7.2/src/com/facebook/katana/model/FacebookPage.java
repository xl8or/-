package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FacebookPage extends JMCachingDictDestination implements Parcelable {

   public static final Creator<FacebookPage> CREATOR = new FacebookPage.1();
   public static final int INVALID_FAN_COUNT = 255;
   public static final long INVALID_ID = 255L;
   @JMAutogen.InferredType(
      jsonFieldName = "can_post"
   )
   public final boolean mCanPost;
   @JMAutogen.InferredType(
      jsonFieldName = "is_community_page"
   )
   public final boolean mCommunityPage;
   @JMAutogen.InferredType(
      jsonFieldName = "name"
   )
   public final String mDisplayName;
   @JMAutogen.InferredType(
      jsonFieldName = "fan_count"
   )
   public final int mFanCount;
   @JMAutogen.InferredType(
      jsonFieldName = "location"
   )
   public final Map<String, Serializable> mLocation;
   @JMAutogen.InferredType(
      jsonFieldName = "page_id"
   )
   public final long mPageId;
   @JMAutogen.InferredType(
      jsonFieldName = "pic_big"
   )
   public final String mPicBig;
   @JMAutogen.InferredType(
      jsonFieldName = "pic"
   )
   public final String mPicMedium;
   @JMAutogen.InferredType(
      jsonFieldName = "pic_small"
   )
   public final String mPicSmall;
   @JMAutogen.InferredType(
      jsonFieldName = "page_url"
   )
   public final String mUrl;


   protected FacebookPage() {
      this.mPageId = 65535L;
      this.mDisplayName = null;
      this.mCanPost = (boolean)0;
      this.mCommunityPage = (boolean)0;
      this.mPicSmall = null;
      this.mPicMedium = null;
      this.mPicBig = null;
      this.mUrl = null;
      HashMap var1 = new HashMap();
      this.mLocation = var1;
      this.mFanCount = -1;
   }

   protected FacebookPage(Parcel var1) {
      long var2 = var1.readLong();
      this.mPageId = var2;
      String var4 = var1.readString();
      this.mDisplayName = var4;
      byte var5;
      if(var1.readByte() != 0) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      this.mCanPost = (boolean)var5;
      byte var6;
      if(var1.readByte() != 0) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      this.mCommunityPage = (boolean)var6;
      String var7 = var1.readString();
      this.mPicSmall = var7;
      String var8 = var1.readString();
      this.mPicMedium = var8;
      String var9 = var1.readString();
      this.mPicBig = var9;
      String var10 = var1.readString();
      this.mUrl = var10;
      HashMap var11 = new HashMap();
      this.mLocation = var11;
      Map var12 = this.mLocation;
      ClassLoader var13 = Map.class.getClassLoader();
      var1.readMap(var12, var13);
      int var14 = var1.readInt();
      this.mFanCount = var14;
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.mPageId;
      var1.writeLong(var3);
      String var5 = this.mDisplayName;
      var1.writeString(var5);
      byte var6;
      if(this.mCanPost) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      byte var7 = (byte)var6;
      var1.writeByte(var7);
      byte var8;
      if(this.mCommunityPage) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      byte var9 = (byte)var8;
      var1.writeByte(var9);
      String var10 = this.mPicSmall;
      var1.writeString(var10);
      String var11 = this.mPicMedium;
      var1.writeString(var11);
      String var12 = this.mPicBig;
      var1.writeString(var12);
      String var13 = this.mUrl;
      var1.writeString(var13);
      Map var14 = this.mLocation;
      var1.writeMap(var14);
      int var15 = this.mFanCount;
      var1.writeInt(var15);
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
