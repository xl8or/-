package com.google.android.finsky.billing.carrierbilling.model;

import com.google.android.finsky.utils.Objects;
import java.util.List;

public class CarrierBillingParameters {

   private final int mCarrierApiVersion;
   private final String mCarrierIconId;
   private final String mGetCredentialsUrl;
   private final String mGetProvisioningUrl;
   private final String mId;
   private final List<String> mMncMccs;
   private final String mName;
   private final boolean mPerTransactionCredentialsRequired;
   private final boolean mSendSubscriberInfoWithCarrierRequests;
   private final boolean mShowCarrierTos;


   private CarrierBillingParameters(CarrierBillingParameters.Builder var1) {
      String var2 = var1.id;
      this.mId = var2;
      String var3 = var1.name;
      this.mName = var3;
      List var4 = var1.mncMccs;
      this.mMncMccs = var4;
      String var5 = var1.getProvisioningUrl;
      this.mGetProvisioningUrl = var5;
      String var6 = var1.getCredentialsUrl;
      this.mGetCredentialsUrl = var6;
      String var7 = var1.carrierIconId;
      this.mCarrierIconId = var7;
      boolean var8 = var1.showCarrierTos;
      this.mShowCarrierTos = var8;
      int var9 = var1.carrierApiVersion;
      this.mCarrierApiVersion = var9;
      boolean var10 = var1.perTransactionCredentialsRequired;
      this.mPerTransactionCredentialsRequired = var10;
      boolean var11 = var1.sendSubscriberInfoWithCarrierRequests;
      this.mSendSubscriberInfoWithCarrierRequests = var11;
   }

   // $FF: synthetic method
   CarrierBillingParameters(CarrierBillingParameters.Builder var1, CarrierBillingParameters.1 var2) {
      this(var1);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(!(var1 instanceof CarrierBillingParameters)) {
            var2 = false;
         } else {
            CarrierBillingParameters var3 = (CarrierBillingParameters)var1;
            String var4 = this.mId;
            String var5 = var3.mId;
            if(Objects.equal(var4, var5)) {
               String var6 = this.mName;
               String var7 = var3.mName;
               if(Objects.equal(var6, var7)) {
                  List var8 = this.mMncMccs;
                  List var9 = var3.mMncMccs;
                  if(Objects.equal(var8, var9)) {
                     String var10 = this.mGetProvisioningUrl;
                     String var11 = var3.mGetProvisioningUrl;
                     if(Objects.equal(var10, var11)) {
                        String var12 = this.mGetCredentialsUrl;
                        String var13 = var3.mGetCredentialsUrl;
                        if(Objects.equal(var12, var13)) {
                           String var14 = this.mCarrierIconId;
                           String var15 = var3.mCarrierIconId;
                           if(Objects.equal(var14, var15)) {
                              Boolean var16 = Boolean.valueOf(this.mShowCarrierTos);
                              Boolean var17 = Boolean.valueOf(var3.mShowCarrierTos);
                              if(Objects.equal(var16, var17)) {
                                 Integer var18 = Integer.valueOf(this.mCarrierApiVersion);
                                 Integer var19 = Integer.valueOf(var3.mCarrierApiVersion);
                                 if(Objects.equal(var18, var19)) {
                                    Boolean var20 = Boolean.valueOf(this.mPerTransactionCredentialsRequired);
                                    Boolean var21 = Boolean.valueOf(var3.mPerTransactionCredentialsRequired);
                                    if(Objects.equal(var20, var21)) {
                                       Boolean var22 = Boolean.valueOf(this.mSendSubscriberInfoWithCarrierRequests);
                                       Boolean var23 = Boolean.valueOf(var3.mSendSubscriberInfoWithCarrierRequests);
                                       if(Objects.equal(var22, var23)) {
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

            var2 = false;
         }
      }

      return var2;
   }

   public int getCarrierApiVersion() {
      return this.mCarrierApiVersion;
   }

   public String getCarrierIconId() {
      return this.mCarrierIconId;
   }

   public String getGetCredentialsUrl() {
      return this.mGetCredentialsUrl;
   }

   public String getGetProvisioningUrl() {
      return this.mGetProvisioningUrl;
   }

   public String getId() {
      return this.mId;
   }

   public List<String> getMncMccs() {
      return this.mMncMccs;
   }

   public String getName() {
      return this.mName;
   }

   public int hashCode() {
      Object[] var1 = new Object[10];
      String var2 = this.mId;
      var1[0] = var2;
      String var3 = this.mName;
      var1[1] = var3;
      List var4 = this.mMncMccs;
      var1[2] = var4;
      String var5 = this.mGetProvisioningUrl;
      var1[3] = var5;
      String var6 = this.mGetCredentialsUrl;
      var1[4] = var6;
      String var7 = this.mCarrierIconId;
      var1[5] = var7;
      Boolean var8 = Boolean.valueOf(this.mShowCarrierTos);
      var1[6] = var8;
      Integer var9 = Integer.valueOf(this.mCarrierApiVersion);
      var1[7] = var9;
      Boolean var10 = Boolean.valueOf(this.mPerTransactionCredentialsRequired);
      var1[8] = var10;
      Boolean var11 = Boolean.valueOf(this.mSendSubscriberInfoWithCarrierRequests);
      var1[9] = var11;
      return Objects.hashCode(var1);
   }

   public boolean perTransactionCredentialsRequired() {
      return this.mPerTransactionCredentialsRequired;
   }

   public boolean sendSubscriberInfoWithCarrierRequests() {
      return this.mSendSubscriberInfoWithCarrierRequests;
   }

   public boolean showCarrierTos() {
      return this.mShowCarrierTos;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder("CarrierBillingParameters:")).append("\n").append("  id                                   : ");
      String var2 = this.mId;
      StringBuilder var3 = var1.append(var2).append("\n").append("  name                                 : ");
      String var4 = this.mName;
      StringBuilder var5 = var3.append(var4).append("\n").append("  mncMccs                              : ");
      List var6 = this.mMncMccs;
      StringBuilder var7 = var5.append(var6).append("\n").append("  getProvisioningUrl                   : ");
      String var8 = this.mGetProvisioningUrl;
      StringBuilder var9 = var7.append(var8).append("\n").append("  getCredentialsUrl                    : ");
      String var10 = this.mGetCredentialsUrl;
      StringBuilder var11 = var9.append(var10).append("\n").append("  carrierIconId                        : ");
      String var12 = this.mCarrierIconId;
      StringBuilder var13 = var11.append(var12).append("\n").append("  showCarrierTos                       : ");
      boolean var14 = this.mShowCarrierTos;
      StringBuilder var15 = var13.append(var14).append("\n").append("  carrierApiVersion                    : ");
      int var16 = this.mCarrierApiVersion;
      StringBuilder var17 = var15.append(var16).append("\n").append("  perTransactionCredentialsRequired    : ");
      boolean var18 = this.mPerTransactionCredentialsRequired;
      StringBuilder var19 = var17.append(var18).append("\n").append("  sendSubscriberInfoWithCarrierRequests: ");
      boolean var20 = this.mSendSubscriberInfoWithCarrierRequests;
      return var19.append(var20).append("\n").toString();
   }

   public static class Builder {

      private int carrierApiVersion;
      private String carrierIconId;
      private String getCredentialsUrl;
      private String getProvisioningUrl;
      private String id;
      private List<String> mncMccs;
      private String name;
      private boolean perTransactionCredentialsRequired;
      private boolean sendSubscriberInfoWithCarrierRequests;
      private boolean showCarrierTos;


      public Builder() {}

      public Builder(CarrierBillingParameters var1) {
         String var2 = var1.getId();
         this.id = var2;
         String var3 = var1.getName();
         this.name = var3;
         List var4 = var1.getMncMccs();
         this.mncMccs = var4;
         String var5 = var1.getGetProvisioningUrl();
         this.getProvisioningUrl = var5;
         String var6 = var1.getGetCredentialsUrl();
         this.getCredentialsUrl = var6;
         String var7 = var1.getCarrierIconId();
         this.carrierIconId = var7;
         boolean var8 = var1.showCarrierTos();
         this.showCarrierTos = var8;
         int var9 = var1.getCarrierApiVersion();
         this.carrierApiVersion = var9;
         boolean var10 = var1.perTransactionCredentialsRequired();
         this.perTransactionCredentialsRequired = var10;
         boolean var11 = var1.sendSubscriberInfoWithCarrierRequests();
         this.sendSubscriberInfoWithCarrierRequests = var11;
      }

      public CarrierBillingParameters build() {
         return new CarrierBillingParameters(this, (CarrierBillingParameters.1)null);
      }

      public CarrierBillingParameters.Builder setCarrierApiVersion(int var1) {
         this.carrierApiVersion = var1;
         return this;
      }

      public CarrierBillingParameters.Builder setCarrierIconId(String var1) {
         this.carrierIconId = var1;
         return this;
      }

      public CarrierBillingParameters.Builder setGetCredentialsUrl(String var1) {
         this.getCredentialsUrl = var1;
         return this;
      }

      public CarrierBillingParameters.Builder setGetProvisioningUrl(String var1) {
         this.getProvisioningUrl = var1;
         return this;
      }

      public CarrierBillingParameters.Builder setId(String var1) {
         this.id = var1;
         return this;
      }

      public CarrierBillingParameters.Builder setMncMccs(List<String> var1) {
         this.mncMccs = var1;
         return this;
      }

      public CarrierBillingParameters.Builder setName(String var1) {
         this.name = var1;
         return this;
      }

      public CarrierBillingParameters.Builder setPerTransactionCredentialsRequired(boolean var1) {
         this.perTransactionCredentialsRequired = var1;
         return this;
      }

      public CarrierBillingParameters.Builder setSendSubscriberInfoWithCarrierRequests(boolean var1) {
         this.sendSubscriberInfoWithCarrierRequests = var1;
         return this;
      }

      public CarrierBillingParameters.Builder setShowCarrierTos(boolean var1) {
         this.showCarrierTos = var1;
         return this;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
