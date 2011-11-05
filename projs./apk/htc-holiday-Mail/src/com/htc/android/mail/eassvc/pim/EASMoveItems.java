package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class EASMoveItems implements Parcelable {

   public static final Creator<EASMoveItems> CREATOR = new EASMoveItems.1();
   public ArrayList<EASMoveItems.EASMoveItem> moveItemList;


   public EASMoveItems() {
      ArrayList var1 = new ArrayList();
      this.moveItemList = var1;
      this.moveItemList.clear();
   }

   private EASMoveItems(Parcel var1) {
      ArrayList var2 = new ArrayList();
      this.moveItemList = var2;
      ArrayList var3 = this.moveItemList;
      Creator var4 = EASMoveItems.EASMoveItem.CREATOR;
      var1.readTypedList(var3, var4);
   }

   // $FF: synthetic method
   EASMoveItems(Parcel var1, EASMoveItems.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      ArrayList var3 = this.moveItemList;
      var1.writeTypedList(var3);
   }

   static class 1 implements Creator<EASMoveItems> {

      1() {}

      public EASMoveItems createFromParcel(Parcel var1) {
         return new EASMoveItems(var1, (EASMoveItems.1)null);
      }

      public EASMoveItems[] newArray(int var1) {
         return new EASMoveItems[var1];
      }
   }

   public static class EASMoveItem implements Parcelable {

      public static final Creator<EASMoveItems.EASMoveItem> CREATOR = new EASMoveItems.EASMoveItem.1();
      public static final int FROM_LOCAL = 2;
      public static final int FROM_SERVER = 1;
      public String dstFldName;
      public String dstFldServerId;
      public int fromSvrFlag;
      public long messageId;
      public String srcFldName;
      public String srcFldServerId;
      public String srcMsgServerId;


      public EASMoveItem() {
         this.messageId = 65535L;
         this.fromSvrFlag = 1;
      }

      private EASMoveItem(Parcel var1) {
         long var2 = var1.readLong();
         this.messageId = var2;
         int var4 = var1.readInt();
         this.fromSvrFlag = var4;
         String var5 = var1.readString();
         this.srcMsgServerId = var5;
         String var6 = var1.readString();
         this.srcFldServerId = var6;
         String var7 = var1.readString();
         this.srcFldName = var7;
         String var8 = var1.readString();
         this.dstFldServerId = var8;
         String var9 = var1.readString();
         this.dstFldName = var9;
      }

      // $FF: synthetic method
      EASMoveItem(Parcel var1, EASMoveItems.1 var2) {
         this(var1);
      }

      public int describeContents() {
         return 0;
      }

      public void writeToParcel(Parcel var1, int var2) {
         long var3 = this.messageId;
         var1.writeLong(var3);
         int var5 = this.fromSvrFlag;
         var1.writeInt(var5);
         String var6 = this.srcMsgServerId;
         var1.writeString(var6);
         String var7 = this.srcFldServerId;
         var1.writeString(var7);
         String var8 = this.srcFldName;
         var1.writeString(var8);
         String var9 = this.dstFldServerId;
         var1.writeString(var9);
         String var10 = this.dstFldName;
         var1.writeString(var10);
      }

      static class 1 implements Creator<EASMoveItems.EASMoveItem> {

         1() {}

         public EASMoveItems.EASMoveItem createFromParcel(Parcel var1) {
            return new EASMoveItems.EASMoveItem(var1, (EASMoveItems.1)null);
         }

         public EASMoveItems.EASMoveItem[] newArray(int var1) {
            return new EASMoveItems.EASMoveItem[var1];
         }
      }
   }
}
