package com.seven.Z7.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Z7EmailId implements Parcelable {

   public static final Creator<Z7EmailId> CREATOR = new Z7EmailId.1();
   private int mAccountId;
   private int mFolderId;
   private long mId;


   public Z7EmailId(int var1, int var2, long var3) {
      this.mAccountId = var1;
      this.mFolderId = var2;
      this.mId = var3;
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else if(var1 == null) {
         var2 = false;
      } else {
         Class var3 = this.getClass();
         Class var4 = var1.getClass();
         if(var3 != var4) {
            var2 = false;
         } else {
            Z7EmailId var5 = (Z7EmailId)var1;
            int var6 = this.mAccountId;
            int var7 = var5.mAccountId;
            if(var6 != var7) {
               var2 = false;
            } else {
               int var8 = this.mFolderId;
               int var9 = var5.mFolderId;
               if(var8 != var9) {
                  var2 = false;
               } else {
                  long var10 = this.mId;
                  long var12 = var5.mId;
                  if(var10 != var12) {
                     var2 = false;
                  } else {
                     var2 = true;
                  }
               }
            }
         }
      }

      return var2;
   }

   public int getAccountId() {
      return this.mAccountId;
   }

   public int getFolderId() {
      return this.mFolderId;
   }

   public long getId() {
      return this.mId;
   }

   public int hashCode() {
      int var1 = 1 * 31;
      int var2 = (this.mAccountId + 31) * 31;
      int var3 = this.mFolderId;
      int var4 = (var2 + var3) * 31;
      long var5 = this.mId;
      long var7 = this.mId >>> 32;
      int var9 = (int)(var5 ^ var7);
      return var4 + var9;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("[email id=");
      long var3 = this.mId;
      var2.append(var3);
      StringBuilder var6 = var1.append(" folder=");
      int var7 = this.mFolderId;
      var6.append(var7);
      StringBuilder var9 = var1.append(" account=");
      int var10 = this.mAccountId;
      var9.append(var10);
      StringBuilder var12 = var1.append("]");
      return var1.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.mAccountId;
      var1.writeInt(var3);
      int var4 = this.mFolderId;
      var1.writeInt(var4);
      long var5 = this.mId;
      var1.writeLong(var5);
   }

   static class 1 implements Creator<Z7EmailId> {

      1() {}

      public Z7EmailId createFromParcel(Parcel var1) {
         int var2 = var1.readInt();
         int var3 = var1.readInt();
         long var4 = var1.readLong();
         return new Z7EmailId(var2, var3, var4);
      }

      public Z7EmailId[] newArray(int var1) {
         return new Z7EmailId[var1];
      }
   }
}
