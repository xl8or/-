package com.seven.Z7.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.seven.Z7.shared.Z7IDLCallbackType;

public class Z7CallbackFilter implements Parcelable {

   public static final Creator<Z7CallbackFilter> CREATOR = new Z7CallbackFilter.1();
   protected int mMessageGroup;


   public Z7CallbackFilter(int var1) {
      this.mMessageGroup = var1;
   }

   public Z7CallbackFilter(Parcel var1) {
      int var2 = var1.readInt();
      this(var2);
   }

   public int describeContents() {
      return 0;
   }

   public boolean filter(Z7IDLCallbackType var1, Object var2) {
      return false;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.mMessageGroup;
      var1.writeInt(var3);
   }

   static class 1 implements Creator<Z7CallbackFilter> {

      1() {}

      public Z7CallbackFilter createFromParcel(Parcel var1) {
         return new Z7CallbackFilter(var1);
      }

      public Z7CallbackFilter[] newArray(int var1) {
         return new Z7CallbackFilter[var1];
      }
   }
}
