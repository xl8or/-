package com.android.exchange.provider;

import com.android.exchange.provider.RecipientCertificates;
import java.util.ArrayList;

public class RRResponse {

   public int recipientCount = 0;
   public ArrayList<RRResponse.RecipientData> recipientData;
   public byte status = 0;
   public String to = null;


   public RRResponse() {
      ArrayList var1 = new ArrayList();
      this.recipientData = var1;
   }

   public void addRecipient(byte var1, String var2, String var3, RecipientCertificates var4, int var5, String var6) {
      ArrayList var7 = this.recipientData;
      RRResponse.RecipientData var14 = new RRResponse.RecipientData(var1, var2, var3, var4, var5, var6);
      var7.add(var14);
   }

   public static class RecipientData {

      public static final int RECIPIENT_TYPE_CONTACT = 2;
      public static final int RECIPIENT_TYPE_GAL = 1;
      public final int mAvailabilityStatus;
      public final RecipientCertificates mCertificates;
      public final String mDisplayName;
      public final String mEmail;
      public final String mMergedFreeBusy;
      public final byte mType;


      public RecipientData(byte var1, String var2, String var3, RecipientCertificates var4, int var5, String var6) {
         this.mType = var1;
         this.mDisplayName = var2;
         this.mEmail = var3;
         this.mCertificates = var4;
         this.mAvailabilityStatus = var5;
         this.mMergedFreeBusy = var6;
      }
   }
}
