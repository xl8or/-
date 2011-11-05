package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ExchangeAccount implements Parcelable, Cloneable {

   public static final Creator<ExchangeAccount> CREATOR = new ExchangeAccount.1();
   public long accountId;
   public String accountName;
   public final String accountType;
   public String calendarCollId;
   public String contactCollId;
   public int deleted;
   public String deviceID;
   public String deviceType;
   public boolean directpushEnabled;
   public String displayName;
   public String domainName;
   public String emailAddress;
   public int heartBeatInterval;
   public String[] mailCollId;
   public String password;
   public String protocolVer;
   public double protocolVerDouble;
   public boolean requireSSL;
   public String safeUserName;
   public String serverName;
   public boolean syncWhileRoaming;
   public String userName;


   public ExchangeAccount() {
      this.accountId = 65535L;
      this.accountType = "com.htc.android.mail.eas";
      this.accountName = "";
      this.displayName = "";
      this.emailAddress = "";
      this.serverName = "";
      this.domainName = "";
      this.userName = "";
      this.password = "";
      this.requireSSL = (boolean)0;
      this.protocolVer = "Unknown";
      this.protocolVerDouble = -1.0D;
      this.heartBeatInterval = -1;
      this.deviceID = "";
      this.deviceType = "";
      this.safeUserName = "";
      this.deleted = -1;
      this.contactCollId = "";
      this.calendarCollId = "";
      this.mailCollId = null;
      this.syncWhileRoaming = (boolean)0;
      this.directpushEnabled = (boolean)0;
   }

   private ExchangeAccount(Parcel var1) {
      long var2 = var1.readLong();
      this.accountId = var2;
      String var4 = var1.readString();
      this.accountType = var4;
      String var5 = var1.readString();
      this.accountName = var5;
      String var6 = var1.readString();
      this.displayName = var6;
      String var7 = var1.readString();
      this.emailAddress = var7;
      String var8 = var1.readString();
      this.serverName = var8;
      String var9 = var1.readString();
      this.domainName = var9;
      String var10 = var1.readString();
      this.userName = var10;
      String var11 = var1.readString();
      this.password = var11;
      byte var12;
      if(var1.readInt() != 0) {
         var12 = 1;
      } else {
         var12 = 0;
      }

      this.requireSSL = (boolean)var12;
      String var13 = var1.readString();
      this.protocolVer = var13;
      double var14 = var1.readDouble();
      this.protocolVerDouble = var14;
      int var16 = var1.readInt();
      this.heartBeatInterval = var16;
      String var17 = var1.readString();
      this.deviceID = var17;
      String var18 = var1.readString();
      this.deviceType = var18;
      String var19 = var1.readString();
      this.safeUserName = var19;
      int var20 = var1.readInt();
      this.deleted = var20;
      String var21 = var1.readString();
      this.contactCollId = var21;
      String var22 = var1.readString();
      this.calendarCollId = var22;
      String[] var23 = var1.readStringArray();
      this.mailCollId = var23;
      byte var24;
      if(var1.readInt() != 0) {
         var24 = 1;
      } else {
         var24 = 0;
      }

      this.syncWhileRoaming = (boolean)var24;
      byte var25;
      if(var1.readInt() != 0) {
         var25 = 1;
      } else {
         var25 = 0;
      }

      this.directpushEnabled = (boolean)var25;
   }

   // $FF: synthetic method
   ExchangeAccount(Parcel var1, ExchangeAccount.1 var2) {
      this(var1);
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.accountId;
      var1.writeLong(var3);
      String var5 = this.accountType;
      var1.writeString(var5);
      String var6 = this.accountName;
      var1.writeString(var6);
      String var7 = this.displayName;
      var1.writeString(var7);
      String var8 = this.emailAddress;
      var1.writeString(var8);
      String var9 = this.serverName;
      var1.writeString(var9);
      String var10 = this.domainName;
      var1.writeString(var10);
      String var11 = this.userName;
      var1.writeString(var11);
      String var12 = this.password;
      var1.writeString(var12);
      byte var13;
      if(this.requireSSL == 1) {
         var13 = 1;
      } else {
         var13 = 0;
      }

      var1.writeInt(var13);
      String var14 = this.protocolVer;
      var1.writeString(var14);
      double var15 = this.protocolVerDouble;
      var1.writeDouble(var15);
      int var17 = this.heartBeatInterval;
      var1.writeInt(var17);
      String var18 = this.deviceID;
      var1.writeString(var18);
      String var19 = this.deviceType;
      var1.writeString(var19);
      String var20 = this.safeUserName;
      var1.writeString(var20);
      int var21 = this.deleted;
      var1.writeInt(var21);
      String var22 = this.contactCollId;
      var1.writeString(var22);
      String var23 = this.calendarCollId;
      var1.writeString(var23);
      String[] var24 = this.mailCollId;
      var1.writeStringArray(var24);
      byte var25;
      if(this.syncWhileRoaming == 1) {
         var25 = 1;
      } else {
         var25 = 0;
      }

      var1.writeInt(var25);
      byte var26;
      if(this.directpushEnabled == 1) {
         var26 = 1;
      } else {
         var26 = 0;
      }

      var1.writeInt(var26);
   }

   static class 1 implements Creator<ExchangeAccount> {

      1() {}

      public ExchangeAccount createFromParcel(Parcel var1) {
         return new ExchangeAccount(var1, (ExchangeAccount.1)null);
      }

      public ExchangeAccount[] newArray(int var1) {
         return new ExchangeAccount[var1];
      }
   }
}
