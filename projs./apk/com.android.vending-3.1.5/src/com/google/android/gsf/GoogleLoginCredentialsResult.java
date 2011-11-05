package com.google.android.gsf;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GoogleLoginCredentialsResult implements Parcelable {

   public static final Creator<GoogleLoginCredentialsResult> CREATOR = new GoogleLoginCredentialsResult.1();
   private String mAccount;
   private Intent mCredentialsIntent;
   private String mCredentialsString;


   public GoogleLoginCredentialsResult() {
      this.mCredentialsString = null;
      this.mCredentialsIntent = null;
      this.mAccount = null;
   }

   private GoogleLoginCredentialsResult(Parcel var1) {
      this.readFromParcel(var1);
   }

   // $FF: synthetic method
   GoogleLoginCredentialsResult(Parcel var1, GoogleLoginCredentialsResult.1 var2) {
      this(var1);
   }

   public int describeContents() {
      int var1;
      if(this.mCredentialsIntent != null) {
         var1 = this.mCredentialsIntent.describeContents();
      } else {
         var1 = 0;
      }

      return var1;
   }

   public String getAccount() {
      return this.mAccount;
   }

   public Intent getCredentialsIntent() {
      return this.mCredentialsIntent;
   }

   public String getCredentialsString() {
      return this.mCredentialsString;
   }

   public void readFromParcel(Parcel var1) {
      String var2 = var1.readString();
      this.mAccount = var2;
      String var3 = var1.readString();
      this.mCredentialsString = var3;
      int var4 = var1.readInt();
      this.mCredentialsIntent = null;
      if(var4 == 1) {
         Intent var5 = new Intent();
         this.mCredentialsIntent = var5;
         this.mCredentialsIntent.readFromParcel(var1);
         Intent var6 = this.mCredentialsIntent;
         ClassLoader var7 = this.getClass().getClassLoader();
         var6.setExtrasClassLoader(var7);
      }
   }

   public void setAccount(String var1) {
      this.mAccount = var1;
   }

   public void setCredentialsIntent(Intent var1) {
      this.mCredentialsIntent = var1;
   }

   public void setCredentialsString(String var1) {
      this.mCredentialsString = var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.mAccount;
      var1.writeString(var3);
      String var4 = this.mCredentialsString;
      var1.writeString(var4);
      if(this.mCredentialsIntent != null) {
         var1.writeInt(1);
         this.mCredentialsIntent.writeToParcel(var1, 0);
      } else {
         var1.writeInt(0);
      }
   }

   static class 1 implements Creator<GoogleLoginCredentialsResult> {

      1() {}

      public GoogleLoginCredentialsResult createFromParcel(Parcel var1) {
         return new GoogleLoginCredentialsResult(var1, (GoogleLoginCredentialsResult.1)null);
      }

      public GoogleLoginCredentialsResult[] newArray(int var1) {
         return new GoogleLoginCredentialsResult[var1];
      }
   }
}
