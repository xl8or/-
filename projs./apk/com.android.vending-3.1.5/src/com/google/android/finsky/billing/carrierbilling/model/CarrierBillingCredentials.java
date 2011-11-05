package com.google.android.finsky.billing.carrierbilling.model;

import com.google.android.finsky.utils.Objects;

public class CarrierBillingCredentials {

   private final int mApiVersion;
   private final String mCredentials;
   private final long mExpirationTime;
   private final boolean mInvalidPassword;
   private final boolean mIsProvisioned;


   private CarrierBillingCredentials(CarrierBillingCredentials.Builder var1) {
      int var2 = var1.apiVersion;
      this.mApiVersion = var2;
      String var3 = var1.credentials;
      this.mCredentials = var3;
      long var4 = var1.expirationTime;
      this.mExpirationTime = var4;
      boolean var6 = var1.isProvisioned;
      this.mIsProvisioned = var6;
      boolean var7 = var1.invalidPassword;
      this.mInvalidPassword = var7;
   }

   // $FF: synthetic method
   CarrierBillingCredentials(CarrierBillingCredentials.Builder var1, CarrierBillingCredentials.1 var2) {
      this(var1);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(!(var1 instanceof CarrierBillingCredentials)) {
            var2 = false;
         } else {
            CarrierBillingCredentials var3 = (CarrierBillingCredentials)var1;
            Integer var4 = Integer.valueOf(this.mApiVersion);
            Integer var5 = Integer.valueOf(var3.mApiVersion);
            if(Objects.equal(var4, var5)) {
               String var6 = this.mCredentials;
               String var7 = var3.mCredentials;
               if(Objects.equal(var6, var7)) {
                  Long var8 = Long.valueOf(this.mExpirationTime);
                  Long var9 = Long.valueOf(var3.mExpirationTime);
                  if(Objects.equal(var8, var9)) {
                     Boolean var10 = Boolean.valueOf(this.mIsProvisioned);
                     Boolean var11 = Boolean.valueOf(var3.mIsProvisioned);
                     if(Objects.equal(var10, var11)) {
                        Boolean var12 = Boolean.valueOf(this.mInvalidPassword);
                        Boolean var13 = Boolean.valueOf(var3.mInvalidPassword);
                        if(Objects.equal(var12, var13)) {
                           return var2;
                        }
                     }
                  }
               }
            }

            var2 = false;
         }
      }

      return var2;
   }

   public int getApiVersion() {
      return this.mApiVersion;
   }

   public String getCredentials() {
      return this.mCredentials;
   }

   public long getExpirationTime() {
      return this.mExpirationTime;
   }

   public int hashCode() {
      Object[] var1 = new Object[5];
      Integer var2 = Integer.valueOf(this.mApiVersion);
      var1[0] = var2;
      String var3 = this.mCredentials;
      var1[1] = var3;
      Long var4 = Long.valueOf(this.mExpirationTime);
      var1[2] = var4;
      Boolean var5 = Boolean.valueOf(this.mIsProvisioned);
      var1[3] = var5;
      Boolean var6 = Boolean.valueOf(this.mInvalidPassword);
      var1[4] = var6;
      return Objects.hashCode(var1);
   }

   public boolean invalidPassword() {
      return this.mInvalidPassword;
   }

   public boolean isProvisioned() {
      return this.mIsProvisioned;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder("CarrierBillingCredentials: ")).append("  apiVersion     : ");
      int var2 = this.mApiVersion;
      StringBuilder var3 = var1.append(var2).append("\n").append("  credentials    : ");
      String var4 = this.mCredentials;
      StringBuilder var5 = var3.append(var4).append("\n").append("  expirationTime : ");
      long var6 = this.mExpirationTime;
      StringBuilder var8 = var5.append(var6).append("\n").append("  isProvisioned  : ");
      boolean var9 = this.mIsProvisioned;
      StringBuilder var10 = var8.append(var9).append("\n").append("  invalidPassword: ");
      boolean var11 = this.mInvalidPassword;
      return var10.append(var11).append("\n").toString();
   }

   public static class Builder {

      private int apiVersion;
      private String credentials;
      private long expirationTime;
      private boolean invalidPassword;
      private boolean isProvisioned;


      public Builder() {}

      public Builder(CarrierBillingCredentials var1) {
         int var2 = var1.mApiVersion;
         this.apiVersion = var2;
         String var3 = var1.mCredentials;
         this.credentials = var3;
         long var4 = var1.mExpirationTime;
         this.expirationTime = var4;
         boolean var6 = var1.mIsProvisioned;
         this.isProvisioned = var6;
         boolean var7 = var1.mInvalidPassword;
         this.invalidPassword = var7;
      }

      public CarrierBillingCredentials build() {
         return new CarrierBillingCredentials(this, (CarrierBillingCredentials.1)null);
      }

      public CarrierBillingCredentials.Builder setApiVersion(int var1) {
         this.apiVersion = var1;
         return this;
      }

      public CarrierBillingCredentials.Builder setCredentials(String var1) {
         this.credentials = var1;
         return this;
      }

      public CarrierBillingCredentials.Builder setExpirationTime(long var1) {
         this.expirationTime = var1;
         return this;
      }

      public CarrierBillingCredentials.Builder setInvalidPassword(boolean var1) {
         this.invalidPassword = var1;
         return this;
      }

      public CarrierBillingCredentials.Builder setIsProvisioned(boolean var1) {
         this.isProvisioned = var1;
         return this;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
