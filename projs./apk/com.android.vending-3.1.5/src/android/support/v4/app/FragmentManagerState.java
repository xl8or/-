package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.BackStackState;
import android.support.v4.app.FragmentState;

final class FragmentManagerState implements Parcelable {

   public static final Creator<FragmentManagerState> CREATOR = new FragmentManagerState.1();
   FragmentState[] mActive;
   int[] mAdded;
   BackStackState[] mBackStack;


   public FragmentManagerState() {}

   public FragmentManagerState(Parcel var1) {
      Creator var2 = FragmentState.CREATOR;
      FragmentState[] var3 = (FragmentState[])var1.createTypedArray(var2);
      this.mActive = var3;
      int[] var4 = var1.createIntArray();
      this.mAdded = var4;
      Creator var5 = BackStackState.CREATOR;
      BackStackState[] var6 = (BackStackState[])var1.createTypedArray(var5);
      this.mBackStack = var6;
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      FragmentState[] var3 = this.mActive;
      var1.writeTypedArray(var3, var2);
      int[] var4 = this.mAdded;
      var1.writeIntArray(var4);
      BackStackState[] var5 = this.mBackStack;
      var1.writeTypedArray(var5, var2);
   }

   static class 1 implements Creator<FragmentManagerState> {

      1() {}

      public FragmentManagerState createFromParcel(Parcel var1) {
         return new FragmentManagerState(var1);
      }

      public FragmentManagerState[] newArray(int var1) {
         return new FragmentManagerState[var1];
      }
   }
}
