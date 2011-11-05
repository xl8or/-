package com.android.exchange;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.android.email.SecurityPolicy;

public class SyncScheduleData implements Parcelable {

   public static final Creator<SyncScheduleData> CREATOR = new SyncScheduleData.1();
   private int mEndMinute;
   private int mOffPeakSchedule;
   private int mPeakDays;
   private int mPeakSchedule;
   private int mRoamingSchedule;
   private int mStartMinute;


   public SyncScheduleData(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.mStartMinute = var1;
      this.mEndMinute = var2;
      this.mPeakDays = var3;
      this.mPeakSchedule = var4;
      this.mOffPeakSchedule = var5;
      this.mRoamingSchedule = var6;
   }

   public SyncScheduleData(Parcel var1) {
      int var2 = var1.readInt();
      this.mStartMinute = var2;
      int var3 = var1.readInt();
      this.mEndMinute = var3;
      int var4 = var1.readInt();
      this.mPeakDays = var4;
      int var5 = var1.readInt();
      this.mPeakSchedule = var5;
      int var6 = var1.readInt();
      this.mOffPeakSchedule = var6;
      int var7 = var1.readInt();
      this.mRoamingSchedule = var7;
   }

   public int describeContents() {
      return 0;
   }

   public int getEndMinute() {
      return this.mEndMinute;
   }

   public int getOffPeakSchedule() {
      return this.mOffPeakSchedule;
   }

   public int getPeakDay() {
      return this.mPeakDays;
   }

   public int getPeakSchedule() {
      return this.mPeakSchedule;
   }

   public int getRoamingSchedule() {
      int var4;
      label16: {
         boolean var3;
         try {
            DevicePolicyManager var1 = (DevicePolicyManager)SecurityPolicy.getContext().getSystemService("device_policy");
            ComponentName var2 = SecurityPolicy.getInstance(SecurityPolicy.getContext()).getAdminComponent();
            var3 = var1.getRequireManualSyncRoaming(var2);
         } catch (Exception var6) {
            break label16;
         }

         if(var3) {
            var4 = 0;
            return var4;
         }
      }

      var4 = this.mRoamingSchedule;
      return var4;
   }

   public int getStartMinute() {
      return this.mStartMinute;
   }

   public void setEndMinute(int var1) {
      this.mEndMinute = var1;
   }

   public void setOffPeakSchedule(int var1) {
      this.mOffPeakSchedule = var1;
   }

   public void setPeakDay(int var1) {
      this.mPeakDays = var1;
   }

   public void setPeakSchedule(int var1) {
      this.mPeakSchedule = var1;
   }

   public void setRoamingSchedule(int var1) {
      this.mRoamingSchedule = var1;
   }

   public void setStartMinute(int var1) {
      this.mStartMinute = var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.mStartMinute;
      var1.writeInt(var3);
      int var4 = this.mEndMinute;
      var1.writeInt(var4);
      int var5 = this.mPeakDays;
      var1.writeInt(var5);
      int var6 = this.mPeakSchedule;
      var1.writeInt(var6);
      int var7 = this.mOffPeakSchedule;
      var1.writeInt(var7);
      int var8 = this.mRoamingSchedule;
      var1.writeInt(var8);
   }

   static class 1 implements Creator<SyncScheduleData> {

      1() {}

      public SyncScheduleData createFromParcel(Parcel var1) {
         return new SyncScheduleData(var1);
      }

      public SyncScheduleData[] newArray(int var1) {
         return new SyncScheduleData[var1];
      }
   }
}
