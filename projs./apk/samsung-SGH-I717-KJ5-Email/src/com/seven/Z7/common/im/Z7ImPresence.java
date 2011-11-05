package com.seven.Z7.common.im;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Z7ImPresence implements Parcelable {

   public static final Creator<Z7ImPresence> CREATOR = new Z7ImPresence.1();
   private int inactiveStatus;
   private String inactiveStatusText;
   private boolean isLogin;
   private int mStatus;
   private String mStatusText;


   public Z7ImPresence(int var1) {
      this.inactiveStatus = 0;
      this.mStatus = var1;
   }

   public Z7ImPresence(int var1, String var2) {
      this(var1);
      this.mStatusText = var2;
   }

   public Z7ImPresence(int var1, String var2, int var3, String var4) {
      this(var1, var2);
      this.inactiveStatus = var3;
      this.inactiveStatusText = var4;
   }

   public Z7ImPresence(int var1, String var2, int var3, String var4, boolean var5) {
      this(var1, var2);
      this.inactiveStatus = var3;
      this.inactiveStatusText = var4;
      this.isLogin = var5;
   }

   public Z7ImPresence(Parcel var1) {
      byte var2 = 1;
      int var3 = var1.readInt();
      String var4 = var1.readString();
      int var5 = var1.readInt();
      String var6 = var1.readString();
      if(var1.readByte() != var2) {
         var2 = 0;
      }

      this(var3, var4, var5, var6, (boolean)var2);
   }

   public int describeContents() {
      return 0;
   }

   public int getInactiveStatus() {
      return this.inactiveStatus;
   }

   public String getInactiveStatusText() {
      return this.inactiveStatusText;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public int getStatus(boolean var1) {
      int var2;
      if(var1) {
         var2 = this.mStatus;
      } else {
         var2 = this.inactiveStatus;
      }

      return var2;
   }

   public String getStatusText() {
      return this.mStatusText;
   }

   public String getStatusText(boolean var1) {
      String var2;
      if(var1) {
         var2 = this.mStatusText;
      } else {
         var2 = this.inactiveStatusText;
      }

      return var2;
   }

   public boolean isLogin() {
      return this.isLogin;
   }

   public void setInactiveStatus(int var1) {
      this.inactiveStatus = var1;
   }

   public void setInactiveStatusText(String var1) {
      this.inactiveStatusText = var1;
   }

   public void setLogin(boolean var1) {
      this.isLogin = var1;
   }

   public void setStatus(int var1) {
      this.mStatus = var1;
   }

   public void setStatusText(String var1) {
      this.mStatusText = var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.mStatus;
      var1.writeInt(var3);
      String var4 = this.mStatusText;
      var1.writeString(var4);
      int var5 = this.inactiveStatus;
      var1.writeInt(var5);
      String var6 = this.inactiveStatusText;
      var1.writeString(var6);
      byte var7;
      if(this.isLogin) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      var1.writeByte(var7);
   }

   static class 1 implements Creator<Z7ImPresence> {

      1() {}

      public Z7ImPresence createFromParcel(Parcel var1) {
         return new Z7ImPresence(var1);
      }

      public Z7ImPresence[] newArray(int var1) {
         return new Z7ImPresence[var1];
      }
   }
}
