package com.android.exchange;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Date;
import java.util.TimeZone;

public class OoOData implements Parcelable {

   public static final Creator<OoOData> CREATOR = new OoOData.1();
   private static final int OOO_CONST_BASE = 0;
   public static final int OOO_STATE_DISABLE = 0;
   public static final int OOO_STATE_GLOBAL = 1;
   public static final int OOO_STATE_TIME_BASED = 2;
   public static final int OOO_TYPE_EXTERNAL_KNOWN = 5;
   public static final int OOO_TYPE_EXTERNAL_UNKNOWN = 6;
   public static final int OOO_TYPE_GENERIC = 3;
   public static final int OOO_TYPE_INTERNAL = 4;
   private static final String TAG = "OoOData";
   public int enabled;
   public Date end;
   public String msg;
   public Date start;
   public int state;
   public int type;


   public OoOData() {}

   public OoOData(int var1, int var2, int var3, String var4) {
      this.type = var1;
      this.state = var2;
      this.enabled = var3;
      this.msg = var4;
      this.start = null;
      this.end = null;
   }

   public OoOData(int var1, int var2, int var3, String var4, long var5, long var7) {
      this.type = var1;
      this.state = var2;
      this.enabled = var3;
      Date var9 = new Date(var5);
      this.start = var9;
      Date var10 = new Date(var7);
      this.end = var10;
      this.msg = var4;
   }

   public OoOData(int var1, int var2, int var3, String var4, Date var5, Date var6) {
      this.type = var1;
      this.state = var2;
      this.enabled = var3;
      this.start = var5;
      this.end = var6;
      this.msg = var4;
   }

   private OoOData(Parcel var1) {
      this.readFromParcel(var1);
   }

   // $FF: synthetic method
   OoOData(Parcel var1, OoOData.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public long getEnd() {
      long var1;
      if(this.end != null) {
         var1 = this.end.getTime();
      } else {
         var1 = 0L;
      }

      return var1;
   }

   public long getStart() {
      long var1;
      if(this.start != null) {
         var1 = this.start.getTime();
      } else {
         var1 = 0L;
      }

      return var1;
   }

   public boolean isEndDST() {
      boolean var3;
      if(this.end != null) {
         TimeZone var1 = TimeZone.getDefault();
         Date var2 = this.end;
         var3 = var1.inDaylightTime(var2);
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isStartDST() {
      boolean var3;
      if(this.start != null) {
         TimeZone var1 = TimeZone.getDefault();
         Date var2 = this.start;
         var3 = var1.inDaylightTime(var2);
      } else {
         var3 = false;
      }

      return var3;
   }

   public void readFromParcel(Parcel var1) {
      int var2 = var1.readInt();
      this.type = var2;
      int var3 = var1.readInt();
      this.state = var3;
      int var4 = var1.readInt();
      this.enabled = var4;
      String var5 = var1.readString();
      this.msg = var5;
      long var6 = var1.readLong();
      if(var6 != 0L) {
         Date var8 = new Date(var6);
         this.start = var8;
      } else {
         this.start = null;
      }

      long var9 = var1.readLong();
      if(var9 != 0L) {
         Date var11 = new Date(var9);
         this.end = var11;
      } else {
         this.end = null;
      }
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.type;
      var1.writeInt(var3);
      int var4 = this.state;
      var1.writeInt(var4);
      int var5 = this.enabled;
      var1.writeInt(var5);
      String var6 = this.msg;
      var1.writeString(var6);
      if(this.start != null) {
         long var7 = this.start.getTime();
         var1.writeLong(var7);
      } else {
         var1.writeLong(0L);
      }

      if(this.end != null) {
         long var9 = this.end.getTime();
         var1.writeLong(var9);
      } else {
         var1.writeLong(0L);
      }
   }

   static class 1 implements Creator<OoOData> {

      1() {}

      public OoOData createFromParcel(Parcel var1) {
         return new OoOData(var1, (OoOData.1)null);
      }

      public OoOData[] newArray(int var1) {
         return new OoOData[var1];
      }
   }
}
