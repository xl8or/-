package com.google.android.gtalkservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class ConnectionState implements Parcelable {

   public static final int CONNECTING = 2;
   public static final Creator<ConnectionState> CREATOR = new ConnectionState.1();
   public static final int IDLE = 0;
   public static final int LOGGED_IN = 3;
   public static final int ONLINE = 4;
   public static final int PENDING = 1;
   private volatile int mState;


   public ConnectionState(int var1) {
      this.setState(var1);
   }

   public ConnectionState(Parcel var1) {
      int var2 = var1.readInt();
      this.mState = var2;
   }

   public static final String toString(int var0) {
      String var1;
      switch(var0) {
      case 1:
         var1 = "RECONNECTION_SCHEDULED";
         break;
      case 2:
         var1 = "CONNECTING";
         break;
      case 3:
         var1 = "AUTHENTICATED";
         break;
      case 4:
         var1 = "ONLINE";
         break;
      default:
         var1 = "IDLE";
      }

      return var1;
   }

   public int describeContents() {
      return 0;
   }

   public int getState() {
      return this.mState;
   }

   public boolean isDisconnected() {
      boolean var1 = true;
      if(this.mState != 0 && this.mState != 1) {
         var1 = false;
      }

      return var1;
   }

   public boolean isLoggedIn() {
      boolean var1;
      if(this.mState >= 3) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isLoggingIn() {
      boolean var1;
      if(this.mState == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isOnline() {
      boolean var1;
      if(this.mState == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isPendingReconnect() {
      byte var1 = 1;
      if(this.mState != var1) {
         var1 = 0;
      }

      return (boolean)var1;
   }

   public void setState(int var1) {
      this.mState = var1;
   }

   public final String toString() {
      return toString(this.mState);
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.mState;
      var1.writeInt(var3);
   }

   static class 1 implements Creator<ConnectionState> {

      1() {}

      public ConnectionState createFromParcel(Parcel var1) {
         return new ConnectionState(var1);
      }

      public ConnectionState[] newArray(int var1) {
         return new ConnectionState[var1];
      }
   }
}
