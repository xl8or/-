package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EASLastSyncInfo implements Parcelable {

   public static final Creator<EASLastSyncInfo> CREATOR = new EASLastSyncInfo.1();
   public int lastSyncErrorCode;
   public int lastSyncResult;
   public long lastSyncTime;
   public int syncSrcType;


   public EASLastSyncInfo() {
      this.syncSrcType = -1;
      this.lastSyncErrorCode = 0;
   }

   private EASLastSyncInfo(Parcel var1) {
      int var2 = var1.readInt();
      this.syncSrcType = var2;
      int var3 = var1.readInt();
      this.lastSyncResult = var3;
      long var4 = var1.readLong();
      this.lastSyncTime = var4;
      int var6 = var1.readInt();
      this.lastSyncErrorCode = var6;
   }

   // $FF: synthetic method
   EASLastSyncInfo(Parcel var1, EASLastSyncInfo.1 var2) {
      this(var1);
   }

   public void clean() {
      this.lastSyncResult = 0;
      this.lastSyncTime = 0L;
      this.lastSyncErrorCode = 0;
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.syncSrcType;
      var1.writeInt(var3);
      int var4 = this.lastSyncResult;
      var1.writeInt(var4);
      long var5 = this.lastSyncTime;
      var1.writeLong(var5);
      int var7 = this.lastSyncErrorCode;
      var1.writeInt(var7);
   }

   static class 1 implements Creator<EASLastSyncInfo> {

      1() {}

      public EASLastSyncInfo createFromParcel(Parcel var1) {
         return new EASLastSyncInfo(var1, (EASLastSyncInfo.1)null);
      }

      public EASLastSyncInfo[] newArray(int var1) {
         return new EASLastSyncInfo[var1];
      }
   }
}
