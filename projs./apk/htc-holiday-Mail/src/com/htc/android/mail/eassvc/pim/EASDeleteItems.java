package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class EASDeleteItems implements Parcelable {

   public static final Creator<EASDeleteItems> CREATOR = new EASDeleteItems.1();
   public boolean addToTrack;
   public ArrayList<String> mailId;
   public ArrayList<String> mailSvrId;
   public ArrayList<String> mailSvrId_messageId;
   public long mailboxId;
   public String mailboxSvrId;


   public EASDeleteItems() {
      ArrayList var1 = new ArrayList();
      this.mailSvrId = var1;
      ArrayList var2 = new ArrayList();
      this.mailSvrId_messageId = var2;
      ArrayList var3 = new ArrayList();
      this.mailId = var3;
      this.mailboxId = 0L;
      this.mailboxSvrId = "";
      this.addToTrack = (boolean)0;
      this.mailSvrId.clear();
      this.mailSvrId_messageId.clear();
      this.mailId.clear();
      this.mailboxId = 0L;
      this.mailboxSvrId = "";
      this.addToTrack = (boolean)0;
   }

   private EASDeleteItems(Parcel var1) {
      ArrayList var2 = new ArrayList();
      this.mailSvrId = var2;
      ArrayList var3 = new ArrayList();
      this.mailSvrId_messageId = var3;
      ArrayList var4 = new ArrayList();
      this.mailId = var4;
      this.mailboxId = 0L;
      this.mailboxSvrId = "";
      this.addToTrack = (boolean)0;
      int var5 = var1.readInt();

      for(int var6 = 0; var6 < var5; ++var6) {
         ArrayList var7 = this.mailSvrId;
         String var8 = var1.readString();
         var7.add(var8);
      }

      int var10 = var1.readInt();

      for(int var11 = 0; var11 < var10; ++var11) {
         ArrayList var12 = this.mailSvrId_messageId;
         String var13 = var1.readString();
         var12.add(var13);
      }

      int var15 = var1.readInt();

      for(int var16 = 0; var16 < var15; ++var16) {
         ArrayList var17 = this.mailId;
         String var18 = var1.readString();
         var17.add(var18);
      }

      long var20 = var1.readLong();
      this.mailboxId = var20;
      String var22 = var1.readString();
      this.mailboxSvrId = var22;
      byte var23;
      if(var1.readInt() != 0) {
         var23 = 1;
      } else {
         var23 = 0;
      }

      this.addToTrack = (boolean)var23;
   }

   // $FF: synthetic method
   EASDeleteItems(Parcel var1, EASDeleteItems.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var4;
      if(this.mailSvrId != null) {
         int var3 = this.mailSvrId.size();
         var1.writeInt(var3);

         for(var4 = 0; var4 < var3; ++var4) {
            String var5 = (String)this.mailSvrId.get(var4);
            var1.writeString(var5);
         }
      }

      if(this.mailSvrId_messageId != null) {
         int var6 = this.mailSvrId_messageId.size();
         var1.writeInt(var6);

         for(var4 = 0; var4 < var6; ++var4) {
            String var7 = (String)this.mailSvrId_messageId.get(var4);
            var1.writeString(var7);
         }
      }

      if(this.mailId != null) {
         int var8 = this.mailId.size();
         var1.writeInt(var8);

         for(var4 = 0; var4 < var8; ++var4) {
            String var9 = (String)this.mailId.get(var4);
            var1.writeString(var9);
         }
      }

      long var10 = this.mailboxId;
      var1.writeLong(var10);
      String var12 = this.mailboxSvrId;
      var1.writeString(var12);
      byte var13;
      if(this.addToTrack == 1) {
         var13 = 1;
      } else {
         var13 = 0;
      }

      var1.writeInt(var13);
   }

   static class 1 implements Creator<EASDeleteItems> {

      1() {}

      public EASDeleteItems createFromParcel(Parcel var1) {
         return new EASDeleteItems(var1, (EASDeleteItems.1)null);
      }

      public EASDeleteItems[] newArray(int var1) {
         return new EASDeleteItems[var1];
      }
   }
}
