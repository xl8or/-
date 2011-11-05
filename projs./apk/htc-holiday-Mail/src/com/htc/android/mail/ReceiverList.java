package com.htc.android.mail;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View;

public class ReceiverList implements Parcelable {

   public static final Creator<ReceiverList> CREATOR = new ReceiverList.1();
   String addr;
   long contactId;
   int group;
   boolean haveDisplayName;
   long id;
   long methodId;
   String name;
   View v;
   int width;


   public ReceiverList() {}

   private ReceiverList(Parcel var1) {
      this.readFromParcel(var1);
   }

   // $FF: synthetic method
   ReceiverList(Parcel var1, ReceiverList.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void readFromParcel(Parcel var1) {
      long var2 = var1.readLong();
      this.id = var2;
      long var4 = var1.readLong();
      this.contactId = var4;
      long var6 = var1.readLong();
      this.methodId = var6;
      String var8 = var1.readString();
      this.addr = var8;
      String var9 = var1.readString();
      this.name = var9;
      int var10 = var1.readInt();
      this.group = var10;
      int var11 = var1.readInt();
      this.width = var11;
      byte var12;
      if(var1.readByte() == 1) {
         var12 = 1;
      } else {
         var12 = 0;
      }

      this.haveDisplayName = (boolean)var12;
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.id;
      var1.writeLong(var3);
      long var5 = this.contactId;
      var1.writeLong(var5);
      long var7 = this.methodId;
      var1.writeLong(var7);
      String var9 = this.addr;
      var1.writeString(var9);
      String var10 = this.name;
      var1.writeString(var10);
      int var11 = this.group;
      var1.writeInt(var11);
      int var12 = this.width;
      var1.writeInt(var12);
      byte var13;
      if(this.haveDisplayName) {
         var13 = 1;
      } else {
         var13 = 0;
      }

      var1.writeByte(var13);
   }

   static class 1 implements Creator<ReceiverList> {

      1() {}

      public ReceiverList createFromParcel(Parcel var1) {
         return new ReceiverList(var1, (ReceiverList.1)null);
      }

      public ReceiverList[] newArray(int var1) {
         return new ReceiverList[var1];
      }
   }
}
