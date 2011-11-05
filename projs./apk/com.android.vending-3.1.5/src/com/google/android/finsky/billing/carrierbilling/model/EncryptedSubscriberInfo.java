package com.google.android.finsky.billing.carrierbilling.model;

import android.text.TextUtils;
import com.google.android.finsky.utils.Objects;

public class EncryptedSubscriberInfo {

   public final int mCarrierKeyVersion;
   public final String mEncryptedKey;
   public final int mGoogleKeyVersion;
   public final String mInitVector;
   public final String mMessage;
   public final String mSignature;


   private EncryptedSubscriberInfo(EncryptedSubscriberInfo.Builder var1) {
      String var2 = var1.message;
      this.mMessage = var2;
      String var3 = var1.encryptedKey;
      this.mEncryptedKey = var3;
      String var4 = var1.signature;
      this.mSignature = var4;
      String var5 = var1.initVector;
      this.mInitVector = var5;
      int var6 = var1.carrierKeyVersion;
      this.mCarrierKeyVersion = var6;
      int var7 = var1.googleKeyVersion;
      this.mGoogleKeyVersion = var7;
   }

   // $FF: synthetic method
   EncryptedSubscriberInfo(EncryptedSubscriberInfo.Builder var1, EncryptedSubscriberInfo.1 var2) {
      this(var1);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(!(var1 instanceof EncryptedSubscriberInfo)) {
            var2 = false;
         } else {
            EncryptedSubscriberInfo var3 = (EncryptedSubscriberInfo)var1;
            String var4 = this.mMessage;
            String var5 = var3.mMessage;
            if(Objects.equal(var4, var5)) {
               String var6 = this.mEncryptedKey;
               String var7 = var3.mEncryptedKey;
               if(Objects.equal(var6, var7)) {
                  String var8 = this.mSignature;
                  String var9 = var3.mSignature;
                  if(Objects.equal(var8, var9)) {
                     String var10 = this.mInitVector;
                     String var11 = var3.mInitVector;
                     if(Objects.equal(var10, var11)) {
                        Integer var12 = Integer.valueOf(this.mCarrierKeyVersion);
                        Integer var13 = Integer.valueOf(var3.mCarrierKeyVersion);
                        if(Objects.equal(var12, var13)) {
                           Integer var14 = Integer.valueOf(this.mGoogleKeyVersion);
                           Integer var15 = Integer.valueOf(var3.mGoogleKeyVersion);
                           if(Objects.equal(var14, var15)) {
                              return var2;
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

   public int getCarrierKeyVersion() {
      return this.mCarrierKeyVersion;
   }

   public String getEncryptedKey() {
      return this.mEncryptedKey;
   }

   public int getGoogleKeyVersion() {
      return this.mGoogleKeyVersion;
   }

   public String getInitVector() {
      return this.mInitVector;
   }

   public String getMessage() {
      return this.mMessage;
   }

   public String getSignature() {
      return this.mSignature;
   }

   public int hashCode() {
      Object[] var1 = new Object[6];
      String var2 = this.mMessage;
      var1[0] = var2;
      String var3 = this.mEncryptedKey;
      var1[1] = var3;
      String var4 = this.mSignature;
      var1[2] = var4;
      String var5 = this.mInitVector;
      var1[3] = var5;
      Integer var6 = Integer.valueOf(this.mCarrierKeyVersion);
      var1[4] = var6;
      Integer var7 = Integer.valueOf(this.mGoogleKeyVersion);
      var1[5] = var7;
      return Objects.hashCode(var1);
   }

   public boolean isEmpty() {
      boolean var1;
      if(TextUtils.isEmpty(this.getMessage()) && TextUtils.isEmpty(this.getEncryptedKey()) && TextUtils.isEmpty(this.getSignature()) && TextUtils.isEmpty(this.getInitVector())) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public com.google.android.finsky.remoting.protos.EncryptedSubscriberInfo toProto() {
      com.google.android.finsky.remoting.protos.EncryptedSubscriberInfo var1 = new com.google.android.finsky.remoting.protos.EncryptedSubscriberInfo();
      String var2 = this.mMessage;
      com.google.android.finsky.remoting.protos.EncryptedSubscriberInfo var3 = var1.setData(var2);
      String var4 = this.mEncryptedKey;
      com.google.android.finsky.remoting.protos.EncryptedSubscriberInfo var5 = var3.setEncryptedKey(var4);
      String var6 = this.mSignature;
      com.google.android.finsky.remoting.protos.EncryptedSubscriberInfo var7 = var5.setSignature(var6);
      String var8 = this.mInitVector;
      com.google.android.finsky.remoting.protos.EncryptedSubscriberInfo var9 = var7.setInitVector(var8);
      int var10 = this.mGoogleKeyVersion;
      com.google.android.finsky.remoting.protos.EncryptedSubscriberInfo var11 = var9.setGoogleKeyVersion(var10);
      int var12 = this.mCarrierKeyVersion;
      return var11.setCarrierKeyVersion(var12);
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder("EncryptedSubscriberInfo:")).append("\n").append("  message          : ");
      String var2 = this.mMessage;
      StringBuilder var3 = var1.append(var2).append("\n").append("  encryptedKey     : ");
      String var4 = this.mEncryptedKey;
      StringBuilder var5 = var3.append(var4).append("\n").append("  signature        : ");
      String var6 = this.mSignature;
      StringBuilder var7 = var5.append(var6).append("\n").append("  initVector       : ");
      String var8 = this.mInitVector;
      StringBuilder var9 = var7.append(var8).append("\n").append("  carrierKeyVersion: ");
      int var10 = this.mCarrierKeyVersion;
      StringBuilder var11 = var9.append(var10).append("\n").append("  googleKeyVersion : ");
      int var12 = this.mGoogleKeyVersion;
      return var11.append(var12).append("\n").toString();
   }

   // $FF: synthetic class
   static class 1 {
   }

   public static class Builder {

      private int carrierKeyVersion;
      private String encryptedKey;
      private int googleKeyVersion;
      private String initVector;
      private String message;
      private String signature;


      public Builder() {}

      public EncryptedSubscriberInfo build() {
         return new EncryptedSubscriberInfo(this, (EncryptedSubscriberInfo.1)null);
      }

      public EncryptedSubscriberInfo.Builder setCarrierKeyVersion(int var1) {
         this.carrierKeyVersion = var1;
         return this;
      }

      public EncryptedSubscriberInfo.Builder setEncryptedKey(String var1) {
         this.encryptedKey = var1;
         return this;
      }

      public EncryptedSubscriberInfo.Builder setGoogleKeyVersion(int var1) {
         this.googleKeyVersion = var1;
         return this;
      }

      public EncryptedSubscriberInfo.Builder setInitVector(String var1) {
         this.initVector = var1;
         return this;
      }

      public EncryptedSubscriberInfo.Builder setMessage(String var1) {
         this.message = var1;
         return this;
      }

      public EncryptedSubscriberInfo.Builder setSignature(String var1) {
         this.signature = var1;
         return this;
      }
   }
}
