package com.android.exchange.provider;


public class RecipientCertificates {

   final String mCertificate;
   final int mCertificateCount;
   final String mMiniCertificate;
   final int mRecipientCount;
   final byte mStatus;


   public RecipientCertificates(byte var1, int var2, int var3, String var4, String var5) {
      this.mStatus = var1;
      this.mCertificateCount = var2;
      this.mRecipientCount = var3;
      this.mCertificate = var4;
      this.mMiniCertificate = var5;
   }
}
