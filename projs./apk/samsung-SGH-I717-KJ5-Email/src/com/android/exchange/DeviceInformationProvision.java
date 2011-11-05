package com.android.exchange;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.android.email.provider.EmailContent;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;
import java.util.Locale;

public class DeviceInformationProvision {

   public static final int DEVICE_INFO_ARGUMENT_ERROR = 5002;
   public static final int DEVICE_INFO_ERROR_BASE = 5000;
   public static final int DEVICE_INFO_IOEXCEPTION = 5001;
   public static final int DEVICE_INFO_START = 5000;
   public String mFriendlyName = "";
   public String mId = "";
   public String mModel = "";
   public String mNetworkOperator = null;
   public String mOs = "";
   public String mOsLanguage = "";
   public boolean mOutBoundSms = 0;
   public String mPhoneNumber = "";
   public String mUserAgent = "";


   public DeviceInformationProvision() {}

   public static Serializer buildCommand(DeviceInformationProvision var0, double var1, Serializer var3) throws IOException {
      Serializer var4 = var3.start(1174).start(1160).start(1175);
      String var5 = var0.mModel;
      Serializer var6 = var4.text(var5).end().start(1176);
      String var7 = var0.mId;
      Serializer var8 = var6.text(var7).end().start(1177);
      String var9 = var0.mFriendlyName;
      Serializer var10 = var8.text(var9).end().start(1178);
      String var11 = var0.mOs;
      Serializer var12 = var10.text(var11).end().start(1179);
      String var13 = var0.mOsLanguage;
      Serializer var14 = var12.text(var13).end().start(1180);
      String var15 = var0.mPhoneNumber;
      Serializer var16 = var14.text(var15).end();
      if(var1 >= 14.0D) {
         Serializer var17 = var16.start(1184);
         String var18 = var0.mUserAgent;
         Serializer var19 = var17.text(var18).end().start(1185);
         String var20;
         if(var0.mOutBoundSms == 1) {
            var20 = "1";
         } else {
            var20 = "0";
         }

         Serializer var21 = var19.text(var20).end().start(1186);
         String var22 = var0.mNetworkOperator;
         Serializer var23 = var21.text(var22).end();
      }

      Serializer var24 = var16.end().end();
      return var16;
   }

   public static DeviceInformationProvision prepareDeviceInformation(Context var0, String var1, EmailContent.Account var2) throws IllegalArgumentException, IOException {
      if(var0 != null && var1 != null) {
         TelephonyManager var3 = (TelephonyManager)var0.getSystemService("phone");
         DeviceInformationProvision var4;
         if(var3 == null) {
            var4 = null;
         } else {
            DeviceInformationProvision var5 = new DeviceInformationProvision();
            var5.mUserAgent = var1;
            String var6;
            if(Build.MODEL != null) {
               var6 = Build.MODEL;
            } else {
               var6 = "Android";
            }

            var5.mModel = var6;
            String var7 = var3.getDeviceId();
            var5.mId = var7;
            if(var5.mId == null) {
               var5.mId = "";
            }

            String var8;
            if(Build.PRODUCT != null) {
               var8 = Build.PRODUCT;
            } else {
               var8 = "Android";
            }

            var5.mFriendlyName = var8;
            var5.mOs = "Android";
            String var9 = Locale.getDefault().getDisplayLanguage();
            var5.mOsLanguage = var9;
            if(var5.mOsLanguage == null) {
               var5.mOsLanguage = "English";
            }

            String var10 = var3.getLine1Number();
            var5.mPhoneNumber = var10;
            if(var5.mPhoneNumber == null) {
               var5.mPhoneNumber = "0000000000";
            }

            String var11 = var3.getNetworkOperatorName();
            var5.mNetworkOperator = var11;
            if(var2 != null) {
               byte var12;
               if((var2.mFlags & 512) != 0) {
                  var12 = 1;
               } else {
                  var12 = 0;
               }

               var5.mOutBoundSms = (boolean)var12;
            } else {
               var5.mOutBoundSms = (boolean)0;
            }

            var4 = var5;
         }

         return var4;
      } else {
         throw new IllegalArgumentException("Context cannot be null");
      }
   }
}
