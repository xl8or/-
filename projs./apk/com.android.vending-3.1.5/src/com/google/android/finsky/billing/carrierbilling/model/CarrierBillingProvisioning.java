package com.google.android.finsky.billing.carrierbilling.model;

import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingCredentials;
import com.google.android.finsky.billing.carrierbilling.model.EncryptedSubscriberInfo;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.utils.Objects;

public class CarrierBillingProvisioning {

   private final String mAccountType;
   private final String mAddressSnippet;
   private final int mApiVersion;
   private final String mCountry;
   private final CarrierBillingCredentials mCredentials;
   private final EncryptedSubscriberInfo mEncryptedSubscriberInfo;
   private final boolean mIsProvisioned;
   private final String mPasswordForgotUrl;
   private final String mPasswordPrompt;
   private final boolean mPasswordRequired;
   private final String mProvisioningId;
   private final String mSubscriberCurrency;
   private final SubscriberInfo mSubscriberInfo;
   private final String mTosUrl;
   private final String mTosVersion;
   private final long mTransactionLimit;


   private CarrierBillingProvisioning(CarrierBillingProvisioning.Builder var1) {
      int var2 = var1.apiVersion;
      this.mApiVersion = var2;
      boolean var3 = var1.isProvisioned;
      this.mIsProvisioned = var3;
      String var4 = var1.provisioningId;
      this.mProvisioningId = var4;
      String var5 = var1.tosUrl;
      this.mTosUrl = var5;
      String var6 = var1.tosVersion;
      this.mTosVersion = var6;
      String var7 = var1.subscriberCurrency;
      this.mSubscriberCurrency = var7;
      long var8 = var1.transactionLimit;
      this.mTransactionLimit = var8;
      String var10 = var1.accountType;
      this.mAccountType = var10;
      SubscriberInfo var11 = var1.subscriberInfo;
      this.mSubscriberInfo = var11;
      CarrierBillingCredentials var12 = var1.credentials;
      this.mCredentials = var12;
      boolean var13 = var1.passwordRequired;
      this.mPasswordRequired = var13;
      String var14 = var1.passwordPrompt;
      this.mPasswordPrompt = var14;
      String var15 = var1.passwordForgotUrl;
      this.mPasswordForgotUrl = var15;
      EncryptedSubscriberInfo var16 = var1.encryptedSubscriberInfo;
      this.mEncryptedSubscriberInfo = var16;
      String var17 = var1.addressSnippet;
      this.mAddressSnippet = var17;
      String var18 = var1.country;
      this.mCountry = var18;
   }

   // $FF: synthetic method
   CarrierBillingProvisioning(CarrierBillingProvisioning.Builder var1, CarrierBillingProvisioning.1 var2) {
      this(var1);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(!(var1 instanceof CarrierBillingProvisioning)) {
            var2 = false;
         } else {
            CarrierBillingProvisioning var3 = (CarrierBillingProvisioning)var1;
            Integer var4 = Integer.valueOf(this.mApiVersion);
            Integer var5 = Integer.valueOf(var3.mApiVersion);
            if(Objects.equal(var4, var5)) {
               Boolean var6 = Boolean.valueOf(this.mIsProvisioned);
               Boolean var7 = Boolean.valueOf(var3.mIsProvisioned);
               if(Objects.equal(var6, var7)) {
                  String var8 = this.mProvisioningId;
                  String var9 = var3.mProvisioningId;
                  if(Objects.equal(var8, var9)) {
                     String var10 = this.mTosUrl;
                     String var11 = var3.mTosUrl;
                     if(Objects.equal(var10, var11)) {
                        String var12 = this.mTosVersion;
                        String var13 = var3.mTosVersion;
                        if(Objects.equal(var12, var13)) {
                           String var14 = this.mSubscriberCurrency;
                           String var15 = var3.mSubscriberCurrency;
                           if(Objects.equal(var14, var15)) {
                              Long var16 = Long.valueOf(this.mTransactionLimit);
                              Long var17 = Long.valueOf(var3.mTransactionLimit);
                              if(Objects.equal(var16, var17)) {
                                 String var18 = this.mAccountType;
                                 String var19 = var3.mAccountType;
                                 if(Objects.equal(var18, var19)) {
                                    SubscriberInfo var20 = this.mSubscriberInfo;
                                    SubscriberInfo var21 = var3.mSubscriberInfo;
                                    if(Objects.equal(var20, var21)) {
                                       CarrierBillingCredentials var22 = this.mCredentials;
                                       CarrierBillingCredentials var23 = var3.mCredentials;
                                       if(Objects.equal(var22, var23)) {
                                          Boolean var24 = Boolean.valueOf(this.mPasswordRequired);
                                          Boolean var25 = Boolean.valueOf(var3.mPasswordRequired);
                                          if(Objects.equal(var24, var25)) {
                                             String var26 = this.mPasswordPrompt;
                                             String var27 = var3.mPasswordPrompt;
                                             if(Objects.equal(var26, var27)) {
                                                String var28 = this.mPasswordForgotUrl;
                                                String var29 = var3.mPasswordForgotUrl;
                                                if(Objects.equal(var28, var29)) {
                                                   EncryptedSubscriberInfo var30 = this.mEncryptedSubscriberInfo;
                                                   EncryptedSubscriberInfo var31 = var3.mEncryptedSubscriberInfo;
                                                   if(Objects.equal(var30, var31)) {
                                                      String var32 = this.mAddressSnippet;
                                                      String var33 = var3.mAddressSnippet;
                                                      if(Objects.equal(var32, var33)) {
                                                         String var34 = this.mCountry;
                                                         String var35 = var3.mCountry;
                                                         if(Objects.equal(var34, var35)) {
                                                            return var2;
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
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
      }

      return var2;
   }

   public String getAccountType() {
      return this.mAccountType;
   }

   public String getAddressSnippet() {
      return this.mAddressSnippet;
   }

   public int getApiVersion() {
      return this.mApiVersion;
   }

   public String getCountry() {
      return this.mCountry;
   }

   public CarrierBillingCredentials getCredentials() {
      return this.mCredentials;
   }

   public EncryptedSubscriberInfo getEncryptedSubscriberInfo() {
      return this.mEncryptedSubscriberInfo;
   }

   public String getPasswordForgotUrl() {
      return this.mPasswordForgotUrl;
   }

   public String getPasswordPrompt() {
      return this.mPasswordPrompt;
   }

   public String getProvisioningId() {
      return this.mProvisioningId;
   }

   public String getSubscriberCurrency() {
      return this.mSubscriberCurrency;
   }

   public SubscriberInfo getSubscriberInfo() {
      return this.mSubscriberInfo;
   }

   public String getTosUrl() {
      return this.mTosUrl;
   }

   public String getTosVersion() {
      return this.mTosVersion;
   }

   public long getTransactionLimit() {
      return this.mTransactionLimit;
   }

   public int hashCode() {
      Object[] var1 = new Object[16];
      Integer var2 = Integer.valueOf(this.mApiVersion);
      var1[0] = var2;
      Boolean var3 = Boolean.valueOf(this.mIsProvisioned);
      var1[1] = var3;
      String var4 = this.mProvisioningId;
      var1[2] = var4;
      String var5 = this.mTosUrl;
      var1[3] = var5;
      String var6 = this.mTosVersion;
      var1[4] = var6;
      String var7 = this.mSubscriberCurrency;
      var1[5] = var7;
      Long var8 = Long.valueOf(this.mTransactionLimit);
      var1[6] = var8;
      String var9 = this.mAccountType;
      var1[7] = var9;
      SubscriberInfo var10 = this.mSubscriberInfo;
      var1[8] = var10;
      CarrierBillingCredentials var11 = this.mCredentials;
      var1[9] = var11;
      Boolean var12 = Boolean.valueOf(this.mPasswordRequired);
      var1[10] = var12;
      String var13 = this.mPasswordPrompt;
      var1[11] = var13;
      String var14 = this.mPasswordForgotUrl;
      var1[12] = var14;
      EncryptedSubscriberInfo var15 = this.mEncryptedSubscriberInfo;
      var1[13] = var15;
      String var16 = this.mAddressSnippet;
      var1[14] = var16;
      String var17 = this.mCountry;
      var1[15] = var17;
      return Objects.hashCode(var1);
   }

   public boolean isPasswordRequired() {
      return this.mPasswordRequired;
   }

   public boolean isProvisioned() {
      return this.mIsProvisioned;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder("CarrierBillingProvisioning:")).append("\n").append("  apiVersion             : ");
      int var2 = this.mApiVersion;
      StringBuilder var3 = var1.append(var2).append("\n").append("  isProvisioned          : ");
      boolean var4 = this.mIsProvisioned;
      StringBuilder var5 = var3.append(var4).append("\n").append("  provisioningId         : ");
      String var6 = this.mProvisioningId;
      StringBuilder var7 = var5.append(var6).append("\n").append("  tosUrl                 : ");
      String var8 = this.mTosUrl;
      StringBuilder var9 = var7.append(var8).append("\n").append("  tosVersion             : ");
      String var10 = this.mTosVersion;
      StringBuilder var11 = var9.append(var10).append("\n").append("  subscriberCurrency     : ");
      String var12 = this.mSubscriberCurrency;
      StringBuilder var13 = var11.append(var12).append("\n").append("  transactionLimit       : ");
      long var14 = this.mTransactionLimit;
      StringBuilder var16 = var13.append(var14).append("\n").append("  accountType            : ");
      String var17 = this.mAccountType;
      StringBuilder var18 = var16.append(var17).append("\n").append("  subscriberInfo         : ");
      SubscriberInfo var19 = this.mSubscriberInfo;
      StringBuilder var20 = var18.append(var19).append("\n").append("  credentials            : ");
      CarrierBillingCredentials var21 = this.mCredentials;
      StringBuilder var22 = var20.append(var21).append("\n").append("  passwordRequired       : ");
      boolean var23 = this.mPasswordRequired;
      StringBuilder var24 = var22.append(var23).append("\n").append("  passwordPrompt         : ");
      String var25 = this.mPasswordPrompt;
      StringBuilder var26 = var24.append(var25).append("\n").append("  passwordForgotUrl      : ");
      String var27 = this.mPasswordForgotUrl;
      StringBuilder var28 = var26.append(var27).append("\n").append("  encryptedSubscriberInfo: ");
      EncryptedSubscriberInfo var29 = this.mEncryptedSubscriberInfo;
      StringBuilder var30 = var28.append(var29).append("\n").append("  addressSnippet         : ");
      String var31 = this.mAddressSnippet;
      StringBuilder var32 = var30.append(var31).append("\n").append("  country                : ");
      String var33 = this.mCountry;
      return var32.append(var33).append("\n").toString();
   }

   // $FF: synthetic class
   static class 1 {
   }

   public static class Builder {

      private String accountType;
      private String addressSnippet;
      private int apiVersion;
      private String country;
      private CarrierBillingCredentials credentials;
      private EncryptedSubscriberInfo encryptedSubscriberInfo;
      private boolean isProvisioned;
      private String passwordForgotUrl;
      private String passwordPrompt;
      private boolean passwordRequired;
      private String provisioningId;
      private String subscriberCurrency;
      private SubscriberInfo subscriberInfo;
      private String tosUrl;
      private String tosVersion;
      private long transactionLimit;


      public Builder() {}

      public Builder(CarrierBillingProvisioning var1) {
         int var2 = var1.getApiVersion();
         this.apiVersion = var2;
         boolean var3 = var1.isProvisioned();
         this.isProvisioned = var3;
         String var4 = var1.getProvisioningId();
         this.provisioningId = var4;
         String var5 = var1.getTosUrl();
         this.tosUrl = var5;
         String var6 = var1.getTosVersion();
         this.tosVersion = var6;
         String var7 = var1.getSubscriberCurrency();
         this.subscriberCurrency = var7;
         long var8 = var1.getTransactionLimit();
         this.transactionLimit = var8;
         String var10 = var1.getAccountType();
         this.accountType = var10;
         SubscriberInfo var11 = var1.getSubscriberInfo();
         this.subscriberInfo = var11;
         CarrierBillingCredentials var12 = var1.getCredentials();
         this.credentials = var12;
         boolean var13 = var1.isPasswordRequired();
         this.passwordRequired = var13;
         String var14 = var1.getPasswordPrompt();
         this.passwordPrompt = var14;
         String var15 = var1.getPasswordForgotUrl();
         this.passwordForgotUrl = var15;
         EncryptedSubscriberInfo var16 = var1.getEncryptedSubscriberInfo();
         this.encryptedSubscriberInfo = var16;
         String var17 = var1.getAddressSnippet();
         this.addressSnippet = var17;
         String var18 = var1.getCountry();
         this.country = var18;
      }

      public CarrierBillingProvisioning build() {
         return new CarrierBillingProvisioning(this, (CarrierBillingProvisioning.1)null);
      }

      public CarrierBillingProvisioning.Builder setAccountType(String var1) {
         this.accountType = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setAddressSnippet(String var1) {
         this.addressSnippet = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setApiVersion(int var1) {
         this.apiVersion = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setCountry(String var1) {
         this.country = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setCredentials(CarrierBillingCredentials var1) {
         this.credentials = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setEncryptedSubscriberInfo(EncryptedSubscriberInfo var1) {
         this.encryptedSubscriberInfo = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setIsProvisioned(boolean var1) {
         this.isProvisioned = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setPasswordForgotUrl(String var1) {
         this.passwordForgotUrl = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setPasswordPrompt(String var1) {
         this.passwordPrompt = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setPasswordRequired(boolean var1) {
         this.passwordRequired = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setProvisioningId(String var1) {
         this.provisioningId = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setSubscriberCurrency(String var1) {
         this.subscriberCurrency = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setSubscriberInfo(SubscriberInfo var1) {
         this.subscriberInfo = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setTosUrl(String var1) {
         this.tosUrl = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setTosVersion(String var1) {
         this.tosVersion = var1;
         return this;
      }

      public CarrierBillingProvisioning.Builder setTransactionLimit(long var1) {
         this.transactionLimit = var1;
         return this;
      }
   }
}
