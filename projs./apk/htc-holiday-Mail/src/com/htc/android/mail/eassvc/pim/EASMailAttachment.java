package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EASMailAttachment implements Parcelable {

   public static final Creator<EASMailAttachment> CREATOR = new EASMailAttachment.1();
   private static final long serialVersionUID = 1L;
   public byte[] attachContent;
   public long attachContentSize;
   public int attachIsInline;
   public String attachMimeType;
   public String attachName;
   public String attachPath;
   public int attachType;
   public String cid;


   public EASMailAttachment() {}

   private EASMailAttachment(Parcel var1) {
      int var2 = var1.readInt();
      this.attachType = var2;
      int var3 = var1.readInt();
      this.attachIsInline = var3;
      String var4 = var1.readString();
      this.attachPath = var4;
      String var5 = var1.readString();
      this.attachName = var5;
      String var6 = var1.readString();
      this.attachMimeType = var6;
      String var7 = var1.readString();
      this.cid = var7;
      long var8 = var1.readLong();
      this.attachContentSize = var8;
      byte[] var10 = new byte[(int)this.attachContentSize];
      this.attachContent = var10;
      byte[] var11 = this.attachContent;
      var1.readByteArray(var11);
   }

   // $FF: synthetic method
   EASMailAttachment(Parcel var1, EASMailAttachment.1 var2) {
      this(var1);
   }

   private boolean checkByteAryEqual(byte[] var1, byte[] var2) {
      boolean var3;
      if(var1 == null && var2 == null) {
         var3 = true;
      } else if((var1 == null || var2 != null) && (var1 != null || var2 == null)) {
         int var4 = var1.length;
         int var5 = var2.length;
         if(var4 != var5) {
            var3 = false;
         } else {
            var3 = true;
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   private boolean checkStringEqual(String var1, String var2) {
      boolean var3;
      if(var1 != null && !var1.equals(var2)) {
         var3 = false;
      } else if(var2 != null && !var2.equals(var1)) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   public int describeContents() {
      return 0;
   }

   public boolean isEqual(EASMailAttachment var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         int var3 = this.attachType;
         int var4 = var1.attachType;
         if(var3 == var4) {
            int var5 = this.attachIsInline;
            int var6 = var1.attachIsInline;
            if(var5 == var6) {
               long var7 = this.attachContentSize;
               long var9 = var1.attachContentSize;
               if(var7 == var9) {
                  String var11 = this.attachPath;
                  String var12 = var1.attachPath;
                  if(this.checkStringEqual(var11, var12)) {
                     String var13 = this.attachName;
                     String var14 = var1.attachName;
                     if(this.checkStringEqual(var13, var14)) {
                        String var15 = this.attachMimeType;
                        String var16 = var1.attachMimeType;
                        if(this.checkStringEqual(var15, var16)) {
                           String var17 = this.cid;
                           String var18 = var1.cid;
                           if(this.checkStringEqual(var17, var18)) {
                              byte[] var19 = this.attachContent;
                              byte[] var20 = var1.attachContent;
                              if(this.checkByteAryEqual(var19, var20)) {
                                 var2 = true;
                                 return var2;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.attachType;
      var1.writeInt(var3);
      int var4 = this.attachIsInline;
      var1.writeInt(var4);
      String var5 = this.attachPath;
      var1.writeString(var5);
      String var6 = this.attachName;
      var1.writeString(var6);
      String var7 = this.attachMimeType;
      var1.writeString(var7);
      String var8 = this.cid;
      var1.writeString(var8);
      long var9 = this.attachContentSize;
      var1.writeLong(var9);
      byte[] var11 = this.attachContent;
      var1.writeByteArray(var11);
   }

   static class 1 implements Creator<EASMailAttachment> {

      1() {}

      public EASMailAttachment createFromParcel(Parcel var1) {
         return new EASMailAttachment(var1, (EASMailAttachment.1)null);
      }

      public EASMailAttachment[] newArray(int var1) {
         return new EASMailAttachment[var1];
      }
   }
}
