package com.google.android.gtalkservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class ConnectionError implements Parcelable {

   public static final int AUTHENTICATION_EXPIRED = 5;
   public static final int AUTHENTICATION_FAILED = 4;
   public static final int CONNECTION_FAILED = 2;
   public static final Creator<ConnectionError> CREATOR = new ConnectionError.1();
   public static final int HEART_BEAT_TIMED_OUT = 6;
   public static final int NONE = 0;
   public static final int NO_NETWORK = 1;
   public static final int SERVER_ERROR = 7;
   public static final int SERVER_REJECT_RATE_LIMITING = 8;
   public static final int SESSION_TERMINATED = 9;
   public static final int UNKNOWN = 10;
   public static final int UNKNOWN_HOST = 3;
   public static final String UNKNOWN_HOST_ERROR_STR = "host-unknown";
   private int mError;


   public ConnectionError(int var1) {
      this.setError(var1);
   }

   public ConnectionError(Parcel var1) {
      int var2 = var1.readInt();
      this.mError = var2;
   }

   public static boolean isAuthenticationError(int var0) {
      boolean var1;
      if(var0 == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isNetworkError(int var0) {
      byte var1 = 1;
      if(var0 != var1 && var0 != 2 && var0 != 3 && var0 != 10) {
         var1 = 0;
      }

      return (boolean)var1;
   }

   public static final String toString(int var0) {
      String var1;
      switch(var0) {
      case 1:
         var1 = "NO NETWORK";
         break;
      case 2:
         var1 = "CONNECTION FAILED";
         break;
      case 3:
         var1 = "UNKNOWN HOST";
         break;
      case 4:
         var1 = "AUTH FAILED";
         break;
      case 5:
         var1 = "AUTH EXPIRED";
         break;
      case 6:
         var1 = "HEARTBEAT TIMEOUT";
         break;
      case 7:
         var1 = "SERVER FAILED";
         break;
      case 8:
         var1 = "SERVER REJECT - RATE LIMIT";
         break;
      case 9:
      default:
         var1 = "NO ERROR";
         break;
      case 10:
         var1 = "UNKNOWN";
      }

      return var1;
   }

   public int describeContents() {
      return 0;
   }

   public int getError() {
      return this.mError;
   }

   public boolean isAuthenticationError() {
      boolean var1;
      if(this.mError == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isAuthenticationExpired() {
      boolean var1;
      if(this.mError == 5) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isNetworkError() {
      return isNetworkError(this.mError);
   }

   public boolean isNoError() {
      boolean var1;
      if(this.mError == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setError(int var1) {
      this.mError = var1;
   }

   public final String toString() {
      return toString(this.mError);
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.mError;
      var1.writeInt(var3);
   }

   static class 1 implements Creator<ConnectionError> {

      1() {}

      public ConnectionError createFromParcel(Parcel var1) {
         return new ConnectionError(var1);
      }

      public ConnectionError[] newArray(int var1) {
         return new ConnectionError[var1];
      }
   }
}
