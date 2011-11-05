package com.android.exchange;

import android.content.Context;
import com.android.email.provider.EmailContent;
import com.android.exchange.adapter.Serializer;
import java.io.IOException;

public class DeviceInformation {

   public static final int DEVICE_INFO_ARGUMENT_ERROR = 5002;
   public static final int DEVICE_INFO_ERROR_BASE = 5000;
   public static final int DEVICE_INFO_IOEXCEPTION = 5001;
   public static final int DEVICE_INFO_START = 5000;
   private final String TAG = "DeviceInformation";
   public String mFriendlyName = "";
   public String mId = "";
   public String mModel = "";
   public String mNetworkOperator = null;
   public String mOs = "";
   public String mOsLanguage = "";
   public boolean mOutBoundSms = 0;
   public String mPhoneNumber = "";
   public double mProtocolVersion = 0.0D;
   public String mUserAgent = "";


   public DeviceInformation(double var1) {
      this.mProtocolVersion = var1;
   }

   public Serializer buildCommand() throws IOException {
      Serializer var1;
      if(this.mProtocolVersion == 0.0D) {
         var1 = null;
      } else {
         Serializer var2 = new Serializer();
         Serializer var3 = var2.start(1157).start(1174).start(1160).start(1175);
         String var4 = this.mModel;
         Serializer var5 = var3.text(var4).end().start(1176);
         String var6 = this.mId;
         Serializer var7 = var5.text(var6).end().start(1177);
         String var8 = this.mFriendlyName;
         Serializer var9 = var7.text(var8).end().start(1178);
         String var10 = this.mOs;
         Serializer var11 = var9.text(var10).end().start(1179);
         String var12 = this.mOsLanguage;
         Serializer var13 = var11.text(var12).end().start(1180);
         String var14 = this.mPhoneNumber;
         Serializer var15 = var13.text(var14).end();
         if(this.mProtocolVersion >= 14.0D) {
            Serializer var16 = var2.start(1184);
            String var17 = this.mUserAgent;
            Serializer var18 = var16.text(var17).end().start(1185);
            String var19;
            if(this.mOutBoundSms == 1) {
               var19 = "1";
            } else {
               var19 = "0";
            }

            Serializer var20 = var18.text(var19).end().start(1186);
            String var21 = this.mNetworkOperator;
            Serializer var22 = var20.text(var21).end();
         }

         var2.end().end().end().done();
         var1 = var2;
      }

      return var1;
   }

   public boolean prepareDeviceInformation(Context param1, String param2, EmailContent.Account param3) {
      // $FF: Couldn't be decompiled
   }
}
