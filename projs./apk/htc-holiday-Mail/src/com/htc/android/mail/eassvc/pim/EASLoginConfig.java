package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EASLoginConfig implements Parcelable, Cloneable {

   public static final Creator<EASLoginConfig> CREATOR = new EASLoginConfig.1();
   public String deviceID;
   public String deviceType;
   public String domainName;
   public String emailAddress;
   public int heartBeatInterval;
   public String password;
   public String protocolVer;
   public boolean requireSSL;
   public String safeUserName;
   public String serverName;
   public String userName;


   public EASLoginConfig() {
      this.emailAddress = "";
      this.serverName = "";
      this.domainName = "";
      this.userName = "";
      this.password = "";
      this.requireSSL = (boolean)0;
      this.protocolVer = "Unknown";
      this.heartBeatInterval = -1;
      this.deviceID = "";
      this.deviceType = "";
      this.safeUserName = "";
   }

   private EASLoginConfig(Parcel var1) {
      String var2 = var1.readString();
      this.emailAddress = var2;
      String var3 = var1.readString();
      this.serverName = var3;
      String var4 = var1.readString();
      this.domainName = var4;
      String var5 = var1.readString();
      this.userName = var5;
      String var6 = var1.readString();
      this.password = var6;
      byte var7;
      if(var1.readInt() != 0) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      this.requireSSL = (boolean)var7;
      String var8 = var1.readString();
      this.protocolVer = var8;
      int var9 = var1.readInt();
      this.heartBeatInterval = var9;
      String var10 = var1.readString();
      this.deviceID = var10;
      String var11 = var1.readString();
      this.deviceType = var11;
      String var12 = var1.readString();
      this.safeUserName = var12;
   }

   // $FF: synthetic method
   EASLoginConfig(Parcel var1, EASLoginConfig.1 var2) {
      this(var1);
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.emailAddress;
      var1.writeString(var3);
      String var4 = this.serverName;
      var1.writeString(var4);
      String var5 = this.domainName;
      var1.writeString(var5);
      String var6 = this.userName;
      var1.writeString(var6);
      String var7 = this.password;
      var1.writeString(var7);
      byte var8;
      if(this.requireSSL == 1) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      var1.writeInt(var8);
      String var9 = this.protocolVer;
      var1.writeString(var9);
      int var10 = this.heartBeatInterval;
      var1.writeInt(var10);
      String var11 = this.deviceID;
      var1.writeString(var11);
      String var12 = this.deviceType;
      var1.writeString(var12);
      String var13 = this.safeUserName;
      var1.writeString(var13);
   }

   static class 1 implements Creator<EASLoginConfig> {

      1() {}

      public EASLoginConfig createFromParcel(Parcel var1) {
         return new EASLoginConfig(var1, (EASLoginConfig.1)null);
      }

      public EASLoginConfig[] newArray(int var1) {
         return new EASLoginConfig[var1];
      }
   }
}
