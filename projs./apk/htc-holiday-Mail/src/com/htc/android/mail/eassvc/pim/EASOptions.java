package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EASOptions implements Parcelable {

   public static final Creator<EASOptions> CREATOR = new EASOptions.1();
   public static final double PROTOCOL_UNKNOWN = -1.0D;
   public int calFilterType;
   public int conflictResolving;
   public int mailAttachmentOpt;
   public int mailBodyType;
   public int mailFilterType;
   public int mailTruncationSize;
   public int peakDays;
   public int peakTimeEnd;
   public int peakTimeStart;
   public int syncSchedule;
   public int syncScheduleOffPeak;
   public int syncSchedulePeak;
   public boolean syncWhileRoaming;


   public EASOptions(double var1) {
      this.syncSchedule = 4;
      this.syncSchedulePeak = 4;
      this.syncScheduleOffPeak = 6;
      this.peakDays = 31;
      this.syncWhileRoaming = (boolean)0;
      this.conflictResolving = 1;
      this.mailFilterType = 2;
      this.mailTruncationSize = 4;
      this.mailAttachmentOpt = 0;
      this.calFilterType = 4;
      this.peakTimeStart = 480;
      this.peakTimeEnd = 1200;
      if(var1 != -1.0D && var1 <= 2.5D) {
         this.mailBodyType = 1;
      } else {
         this.mailBodyType = 2;
      }
   }

   private EASOptions(Parcel var1) {
      int var2 = var1.readInt();
      this.syncSchedule = var2;
      int var3 = var1.readInt();
      this.syncSchedulePeak = var3;
      int var4 = var1.readInt();
      this.syncScheduleOffPeak = var4;
      int var5 = var1.readInt();
      this.peakDays = var5;
      byte var6;
      if(var1.readInt() == 1) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      this.syncWhileRoaming = (boolean)var6;
      int var7 = var1.readInt();
      this.conflictResolving = var7;
      int var8 = var1.readInt();
      this.mailFilterType = var8;
      int var9 = var1.readInt();
      this.mailTruncationSize = var9;
      int var10 = var1.readInt();
      this.mailBodyType = var10;
      int var11 = var1.readInt();
      this.mailAttachmentOpt = var11;
      int var12 = var1.readInt();
      this.calFilterType = var12;
      int var13 = var1.readInt();
      this.peakTimeStart = var13;
      int var14 = var1.readInt();
      this.peakTimeEnd = var14;
   }

   // $FF: synthetic method
   EASOptions(Parcel var1, EASOptions.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.syncSchedule;
      var1.writeInt(var3);
      int var4 = this.syncSchedulePeak;
      var1.writeInt(var4);
      int var5 = this.syncScheduleOffPeak;
      var1.writeInt(var5);
      int var6 = this.peakDays;
      var1.writeInt(var6);
      byte var7;
      if(this.syncWhileRoaming == 1) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      var1.writeInt(var7);
      int var8 = this.conflictResolving;
      var1.writeInt(var8);
      int var9 = this.mailFilterType;
      var1.writeInt(var9);
      int var10 = this.mailTruncationSize;
      var1.writeInt(var10);
      int var11 = this.mailBodyType;
      var1.writeInt(var11);
      int var12 = this.mailAttachmentOpt;
      var1.writeInt(var12);
      int var13 = this.calFilterType;
      var1.writeInt(var13);
      int var14 = this.peakTimeStart;
      var1.writeInt(var14);
      int var15 = this.peakTimeEnd;
      var1.writeInt(var15);
   }

   static class 1 implements Creator<EASOptions> {

      1() {}

      public EASOptions createFromParcel(Parcel var1) {
         return new EASOptions(var1, (EASOptions.1)null);
      }

      public EASOptions[] newArray(int var1) {
         return new EASOptions[var1];
      }
   }
}
