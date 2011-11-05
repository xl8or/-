package com.android.email.service;

import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import com.android.email.service.EmnWbxmlParser;
import com.android.email.service.MailPushSecurity;

public final class EmailPushNotification {

   private static final int BIT_SHIFT_4 = 4;
   private static final int MASK_HEX_0F = 15;
   private static final int MASK_HEX_2F = 47;
   private static final int MAX_TIMESTAMP_LEN = 14;
   private static final int MIN_TIMESTAMP_LEN = 8;
   private static final String TAG = "EmailPushNotification";
   private String mMps;
   private EmnWbxmlParser mParser = null;
   private int mPduType = -1;
   private String mPpg;
   private int mTransactionId = -1;
   private int mWapAppIdValue = -1;


   public EmailPushNotification() {}

   private String bytesToHexString(byte[] var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         int var3 = var1.length * 2;
         StringBuilder var4 = new StringBuilder(var3);
         int var5 = 0;

         while(true) {
            int var6 = var1.length;
            if(var5 >= var6) {
               var2 = var4.toString();
               break;
            }

            int var7 = var1[var5] >> 4 & 15;
            char var8 = "0123456789abcdef".charAt(var7);
            var4.append(var8);
            int var10 = var1[var5] & 15;
            char var11 = "0123456789abcdef".charAt(var10);
            var4.append(var11);
            ++var5;
         }
      }

      return var2;
   }

   public boolean analyzeWapPush(Context var1) {
      boolean var3;
      if(this.mParser == null) {
         int var2 = Log.e("EmailPushNotification", "mParser is null. ");
         var3 = false;
      } else {
         try {
            this.mParser.parse();
         } catch (Exception var13) {
            StringBuilder var9 = (new StringBuilder()).append("Failed to parse : ");
            String var10 = var13.getMessage();
            String var11 = var9.append(var10).toString();
            int var12 = Log.e("EmailPushNotification", var11);
            this.mParser = null;
            var3 = false;
            return var3;
         }

         MailPushSecurity var4 = MailPushSecurity.getMailPushSecurity(var1);
         String var5 = this.mMps;
         String var6 = this.mPpg;
         int var7 = this.mWapAppIdValue;
         var3 = var4.checkSecurity(var5, var6, var7);
      }

      return var3;
   }

   public String getEmnMailbox() {
      String var1 = null;
      if(this.mParser != null) {
         var1 = this.mParser.getMailbox();
      }

      String var2 = "getEmnMailbox : " + var1;
      int var3 = Log.d("EmailPushNotification", var2);
      return var1;
   }

   public long getEmnTimestamp() {
      long var1;
      if(this.mParser == null) {
         var1 = 0L;
      } else {
         StringBuffer var3 = new StringBuffer();
         String var4 = this.mParser.getTimestamp();
         var3.insert(0, var4);
         if(var3 == null) {
            var1 = 0L;
         } else {
            long var6 = 0L;
            int var8 = var3.length();
            if(8 <= var8 && 14 >= var8) {
               label40: {
                  for(int var9 = var8; 14 > var9; ++var9) {
                     var3.insert(var9, "0");
                  }

                  StringBuilder var11 = new StringBuilder();
                  String var12 = var3.substring(0, 8);
                  StringBuilder var13 = var11.append(var12).append("T");
                  String var14 = var3.substring(8, 14);
                  String var15 = var13.append(var14).append("Z").toString();
                  Time var16 = new Time();

                  long var17;
                  try {
                     if(!var16.parse(var15)) {
                        break label40;
                     }

                     var17 = var16.normalize((boolean)0);
                  } catch (Exception var26) {
                     StringBuilder var22 = (new StringBuilder()).append("Failed to convert timestamp: ");
                     String var23 = var26.getMessage();
                     String var24 = var22.append(var23).toString();
                     int var25 = Log.e("EmailPushNotification", var24);
                     break label40;
                  }

                  var6 = var17;
               }
            }

            String var19 = "getEmnTimestamp : " + var6;
            int var20 = Log.d("EmailPushNotification", var19);
            var1 = var6;
         }
      }

      return var1;
   }

   public void setWapPushIntent(Intent var1) {
      int var2 = var1.getIntExtra("transactionId", -1);
      this.mTransactionId = var2;
      int var3 = var1.getIntExtra("pduType", -1);
      this.mPduType = var3;
      int var4 = var1.getIntExtra("wapAppID", -1);
      this.mWapAppIdValue = var4;
      byte[] var5 = var1.getByteArrayExtra("data");
      EmnWbxmlParser var6 = new EmnWbxmlParser(var5);
      this.mParser = var6;
      String var7 = var1.getStringExtra("mps");
      this.mMps = var7;
      String var8 = var1.getStringExtra("ppg");
      this.mPpg = var8;
      StringBuilder var9 = (new StringBuilder()).append("Wsp Transaction ID : ");
      int var10 = this.mTransactionId;
      String var11 = var9.append(var10).toString();
      int var12 = Log.d("EmailPushNotification", var11);
      StringBuilder var13 = (new StringBuilder()).append("Wsp Type : ");
      int var14 = this.mPduType;
      String var15 = var13.append(var14).toString();
      int var16 = Log.d("EmailPushNotification", var15);
      StringBuilder var17 = (new StringBuilder()).append("Wap Application ID : ");
      int var18 = this.mWapAppIdValue;
      String var19 = var17.append(var18).toString();
      int var20 = Log.d("EmailPushNotification", var19);
      StringBuilder var21 = (new StringBuilder()).append("Wap data : ");
      String var22 = this.bytesToHexString(var5);
      String var23 = var21.append(var22).toString();
      int var24 = Log.d("EmailPushNotification", var23);
      StringBuilder var25 = (new StringBuilder()).append("Service Center Address : ");
      String var26 = this.mMps;
      String var27 = var25.append(var26).toString();
      int var28 = Log.d("EmailPushNotification", var27);
      StringBuilder var29 = (new StringBuilder()).append("Push Proxy Gateway Originating Address : ");
      String var30 = this.mPpg;
      String var31 = var29.append(var30).toString();
      int var32 = Log.d("EmailPushNotification", var31);
   }
}
