package com.seven.Z7.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Z7AccountStateInfo implements Parcelable {

   public final Creator<Z7AccountStateInfo> CREATOR;
   byte mAccountStatus;
   long mConnectionDate;
   byte mConnectionStatus;
   byte mEndpointStatus;
   boolean mIsPushOn;
   List<Z7AccountStateInfo.Service> mServices;


   public Z7AccountStateInfo(byte var1, byte var2, boolean var3, byte var4, long var5) {
      Z7AccountStateInfo.1 var7 = new Z7AccountStateInfo.1();
      this.CREATOR = var7;
      this.mAccountStatus = var1;
      this.mEndpointStatus = var2;
      this.mIsPushOn = var3;
      this.mConnectionDate = var5;
      this.mConnectionStatus = var4;
   }

   public Z7AccountStateInfo(byte var1, byte var2, boolean var3, byte var4, long var5, Z7AccountStateInfo.Service[] var7) {
      this(var1, var2, var3, var4, var5);
      List var8 = Arrays.asList(var7);
      this.mServices = var8;
   }

   public void addServiceInfo(short var1, String var2, byte var3, byte var4) {
      if(this.mServices == null) {
         ArrayList var5 = new ArrayList();
         this.mServices = var5;
      }

      List var6 = this.mServices;
      Z7AccountStateInfo.Service var12 = new Z7AccountStateInfo.Service(var1, var2, var3, var4);
      var6.add(var12);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      byte var3 = this.mAccountStatus;
      var1.writeByte(var3);
      byte var4 = this.mEndpointStatus;
      var1.writeByte(var4);
      byte var5;
      if(this.mIsPushOn) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      byte var6 = (byte)var5;
      var1.writeByte(var6);
      byte var7 = this.mConnectionStatus;
      var1.writeByte(var7);
      long var8 = this.mConnectionDate;
      var1.writeLong(var8);
      List var10 = this.mServices;
      Z7AccountStateInfo.Service[] var11 = new Z7AccountStateInfo.Service[0];
      Parcelable[] var12 = (Parcelable[])var10.toArray(var11);
      var1.writeParcelableArray(var12, 0);
   }

   class 1 implements Creator<Z7AccountStateInfo> {

      1() {}

      public Z7AccountStateInfo createFromParcel(Parcel var1) {
         Z7AccountStateInfo var2 = new Z7AccountStateInfo;
         byte var3 = var1.readByte();
         byte var4 = var1.readByte();
         byte var5;
         if(var1.readByte() == 1) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         byte var6 = var1.readByte();
         long var7 = var1.readLong();
         ClassLoader var9 = this.getClass().getClassLoader();
         Z7AccountStateInfo.Service[] var10 = (Z7AccountStateInfo.Service[])((Z7AccountStateInfo.Service[])var1.readParcelableArray(var9));
         var2.<init>(var3, var4, (boolean)var5, var6, var7, var10);
         return var2;
      }

      public Z7AccountStateInfo[] newArray(int var1) {
         return new Z7AccountStateInfo[var1];
      }
   }

   class Service implements Parcelable {

      public final Creator<Z7AccountStateInfo.Service> CREATOR;
      byte mPercentage;
      String mServiceDesc;
      short mServiceId;
      byte mServiceState;


      public Service(short var2, String var3, byte var4, byte var5) {
         Z7AccountStateInfo.Service.1 var6 = new Z7AccountStateInfo.Service.1();
         this.CREATOR = var6;
         this.mServiceDesc = var3;
         this.mServiceId = var2;
         this.mServiceState = var4;
         this.mPercentage = var5;
      }

      public int describeContents() {
         return 0;
      }

      public void writeToParcel(Parcel var1, int var2) {
         short var3 = this.mServiceId;
         var1.writeInt(var3);
         String var4 = this.mServiceDesc;
         var1.writeString(var4);
         byte var5 = this.mServiceState;
         var1.writeByte(var5);
         byte var6 = this.mPercentage;
         var1.writeByte(var6);
      }

      class 1 implements Creator<Z7AccountStateInfo.Service> {

         1() {}

         public Z7AccountStateInfo.Service createFromParcel(Parcel var1) {
            Z7AccountStateInfo var2 = Z7AccountStateInfo.this;
            short var3 = (short)var1.readInt();
            String var4 = var1.readString();
            byte var5 = var1.readByte();
            byte var6 = var1.readByte();
            return var2.new Service(var3, var4, var5, var6);
         }

         public Z7AccountStateInfo.Service[] newArray(int var1) {
            return new Z7AccountStateInfo.Service[var1];
         }
      }
   }
}
