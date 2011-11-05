package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EASMailbox implements Parcelable {

   public static final Creator<EASMailbox> CREATOR = new EASMailbox.1();
   private static final long serialVersionUID = 1L;
   public String DisplayName;
   public String HierarchyName;
   public String ParentID;
   public String ServerID;
   public String SyncKey;
   public String Type;
   public boolean defaultSync;
   public boolean enableSyncDown;
   public boolean enableSyncUp;


   public EASMailbox() {
      String var1 = String.valueOf(18);
      this.Type = var1;
      this.enableSyncDown = (boolean)0;
      this.enableSyncUp = (boolean)0;
      this.defaultSync = (boolean)0;
   }

   private EASMailbox(Parcel var1) {
      String var2 = String.valueOf(18);
      this.Type = var2;
      this.enableSyncDown = (boolean)0;
      this.enableSyncUp = (boolean)0;
      this.defaultSync = (boolean)0;
      String var3 = var1.readString();
      this.ServerID = var3;
      String var4 = var1.readString();
      this.ParentID = var4;
      String var5 = var1.readString();
      this.DisplayName = var5;
      String var6 = var1.readString();
      this.HierarchyName = var6;
      String var7 = var1.readString();
      this.Type = var7;
      String var8 = var1.readString();
      this.SyncKey = var8;
      byte var9;
      if(var1.readInt() == 1) {
         var9 = 1;
      } else {
         var9 = 0;
      }

      this.enableSyncDown = (boolean)var9;
      byte var10;
      if(var1.readInt() == 1) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      this.enableSyncUp = (boolean)var10;
      byte var11;
      if(var1.readInt() == 1) {
         var11 = 1;
      } else {
         var11 = 0;
      }

      this.defaultSync = (boolean)var11;
   }

   // $FF: synthetic method
   EASMailbox(Parcel var1, EASMailbox.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.ServerID;
      var1.writeString(var3);
      String var4 = this.ParentID;
      var1.writeString(var4);
      String var5 = this.DisplayName;
      var1.writeString(var5);
      String var6 = this.HierarchyName;
      var1.writeString(var6);
      String var7 = this.Type;
      var1.writeString(var7);
      String var8 = this.SyncKey;
      var1.writeString(var8);
      byte var9;
      if(this.enableSyncDown) {
         var9 = 1;
      } else {
         var9 = 0;
      }

      var1.writeInt(var9);
      byte var10;
      if(this.enableSyncUp) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      var1.writeInt(var10);
      byte var11;
      if(this.defaultSync) {
         var11 = 1;
      } else {
         var11 = 0;
      }

      var1.writeInt(var11);
   }

   static class 1 implements Creator<EASMailbox> {

      1() {}

      public EASMailbox createFromParcel(Parcel var1) {
         return new EASMailbox(var1, (EASMailbox.1)null);
      }

      public EASMailbox[] newArray(int var1) {
         return new EASMailbox[var1];
      }
   }
}
