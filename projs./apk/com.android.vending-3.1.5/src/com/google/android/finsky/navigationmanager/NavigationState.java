package com.google.android.finsky.navigationmanager;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public enum NavigationState implements Parcelable {

   // $FF: synthetic field
   private static final NavigationState[] $VALUES;
   AGGREGATED_HOME("AGGREGATED_HOME", 1),
   ALL_REVIEWS("ALL_REVIEWS", 7),
   BROWSE("BROWSE", 4),
   CORPUS_HOME("CORPUS_HOME", 2);
   public static final Creator<NavigationState> CREATOR;
   DETAILS("DETAILS", 5),
   FLAG_CONTENT("FLAG_CONTENT", 8),
   INITIAL("INITIAL", 0),
   MY_DOWNLOADS("MY_DOWNLOADS", 3),
   SEARCH("SEARCH", 6);


   static {
      NavigationState[] var0 = new NavigationState[9];
      NavigationState var1 = INITIAL;
      var0[0] = var1;
      NavigationState var2 = AGGREGATED_HOME;
      var0[1] = var2;
      NavigationState var3 = CORPUS_HOME;
      var0[2] = var3;
      NavigationState var4 = MY_DOWNLOADS;
      var0[3] = var4;
      NavigationState var5 = BROWSE;
      var0[4] = var5;
      NavigationState var6 = DETAILS;
      var0[5] = var6;
      NavigationState var7 = SEARCH;
      var0[6] = var7;
      NavigationState var8 = ALL_REVIEWS;
      var0[7] = var8;
      NavigationState var9 = FLAG_CONTENT;
      var0[8] = var9;
      $VALUES = var0;
      CREATOR = new NavigationState.1();
   }

   private NavigationState(String var1, int var2) {}

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.ordinal();
      var1.writeInt(var3);
   }

   static class 1 implements Creator<NavigationState> {

      1() {}

      public NavigationState createFromParcel(Parcel var1) {
         NavigationState[] var2 = NavigationState.values();
         int var3 = var1.readInt();
         return var2[var3];
      }

      public NavigationState[] newArray(int var1) {
         return new NavigationState[var1];
      }
   }
}
