package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManagerImpl;

final class FragmentState implements Parcelable {

   public static final Creator<FragmentState> CREATOR = new FragmentState.1();
   final Bundle mArguments;
   final String mClassName;
   final int mContainerId;
   final boolean mDetached;
   final int mFragmentId;
   final boolean mFromLayout;
   final int mIndex;
   Fragment mInstance;
   final boolean mRetainInstance;
   Bundle mSavedFragmentState;
   final String mTag;


   public FragmentState(Parcel var1) {
      byte var2 = 1;
      super();
      String var3 = var1.readString();
      this.mClassName = var3;
      int var4 = var1.readInt();
      this.mIndex = var4;
      byte var5;
      if(var1.readInt() != 0) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      this.mFromLayout = (boolean)var5;
      int var6 = var1.readInt();
      this.mFragmentId = var6;
      int var7 = var1.readInt();
      this.mContainerId = var7;
      String var8 = var1.readString();
      this.mTag = var8;
      byte var9;
      if(var1.readInt() != 0) {
         var9 = 1;
      } else {
         var9 = 0;
      }

      this.mRetainInstance = (boolean)var9;
      if(var1.readInt() == 0) {
         var2 = 0;
      }

      this.mDetached = (boolean)var2;
      Bundle var10 = var1.readBundle();
      this.mArguments = var10;
      Bundle var11 = var1.readBundle();
      this.mSavedFragmentState = var11;
   }

   public FragmentState(Fragment var1) {
      String var2 = var1.getClass().getName();
      this.mClassName = var2;
      int var3 = var1.mIndex;
      this.mIndex = var3;
      boolean var4 = var1.mFromLayout;
      this.mFromLayout = var4;
      int var5 = var1.mFragmentId;
      this.mFragmentId = var5;
      int var6 = var1.mContainerId;
      this.mContainerId = var6;
      String var7 = var1.mTag;
      this.mTag = var7;
      boolean var8 = var1.mRetainInstance;
      this.mRetainInstance = var8;
      boolean var9 = var1.mDetached;
      this.mDetached = var9;
      Bundle var10 = var1.mArguments;
      this.mArguments = var10;
   }

   public int describeContents() {
      return 0;
   }

   public Fragment instantiate(FragmentActivity var1) {
      Fragment var2;
      if(this.mInstance != null) {
         var2 = this.mInstance;
      } else {
         if(this.mArguments != null) {
            Bundle var3 = this.mArguments;
            ClassLoader var4 = var1.getClassLoader();
            var3.setClassLoader(var4);
         }

         String var5 = this.mClassName;
         Bundle var6 = this.mArguments;
         Fragment var7 = Fragment.instantiate(var1, var5, var6);
         this.mInstance = var7;
         if(this.mSavedFragmentState != null) {
            Bundle var8 = this.mSavedFragmentState;
            ClassLoader var9 = var1.getClassLoader();
            var8.setClassLoader(var9);
            Fragment var10 = this.mInstance;
            Bundle var11 = this.mSavedFragmentState;
            var10.mSavedFragmentState = var11;
         }

         Fragment var12 = this.mInstance;
         int var13 = this.mIndex;
         var12.setIndex(var13);
         Fragment var14 = this.mInstance;
         boolean var15 = this.mFromLayout;
         var14.mFromLayout = var15;
         this.mInstance.mRestored = (boolean)1;
         Fragment var16 = this.mInstance;
         int var17 = this.mFragmentId;
         var16.mFragmentId = var17;
         Fragment var18 = this.mInstance;
         int var19 = this.mContainerId;
         var18.mContainerId = var19;
         Fragment var20 = this.mInstance;
         String var21 = this.mTag;
         var20.mTag = var21;
         Fragment var22 = this.mInstance;
         boolean var23 = this.mRetainInstance;
         var22.mRetainInstance = var23;
         Fragment var24 = this.mInstance;
         boolean var25 = this.mDetached;
         var24.mDetached = var25;
         Fragment var26 = this.mInstance;
         FragmentManagerImpl var27 = var1.mFragments;
         var26.mFragmentManager = var27;
         var2 = this.mInstance;
      }

      return var2;
   }

   public void writeToParcel(Parcel var1, int var2) {
      byte var3 = 1;
      String var4 = this.mClassName;
      var1.writeString(var4);
      int var5 = this.mIndex;
      var1.writeInt(var5);
      byte var6;
      if(this.mFromLayout) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      var1.writeInt(var6);
      int var7 = this.mFragmentId;
      var1.writeInt(var7);
      int var8 = this.mContainerId;
      var1.writeInt(var8);
      String var9 = this.mTag;
      var1.writeString(var9);
      byte var10;
      if(this.mRetainInstance) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      var1.writeInt(var10);
      if(!this.mDetached) {
         var3 = 0;
      }

      var1.writeInt(var3);
      Bundle var11 = this.mArguments;
      var1.writeBundle(var11);
      Bundle var12 = this.mSavedFragmentState;
      var1.writeBundle(var12);
   }

   static class 1 implements Creator<FragmentState> {

      1() {}

      public FragmentState createFromParcel(Parcel var1) {
         return new FragmentState(var1);
      }

      public FragmentState[] newArray(int var1) {
         return new FragmentState[var1];
      }
   }
}
