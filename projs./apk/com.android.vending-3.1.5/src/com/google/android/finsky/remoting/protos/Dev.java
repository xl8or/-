package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.DeviceConfigurationProto;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Dev {

   private Dev() {}

   public static final class Device extends MessageMicro {

      public static final int BUILD_APPROVED_FIELD_NUMBER = 14;
      public static final int CARRIER_ID_DEPRECATED_FIELD_NUMBER = 4;
      public static final int DEVICE_CONFIG_FIELD_NUMBER = 1;
      public static final int DEVICE_FIELD_NUMBER = 12;
      public static final int GOOGLE_SERVICES_FIELD_NUMBER = 8;
      public static final int IP_COUNTRY_FIELD_NUMBER = 2;
      public static final int LATEST_BUILD_FINGERPRINT_FIELD_NUMBER = 5;
      public static final int MAKER_FIELD_NUMBER = 10;
      public static final int MARKET_CLIENT_FEATURE_FIELD_NUMBER = 13;
      public static final int MCCMNC_FIELD_NUMBER = 3;
      public static final int MODEL_FIELD_NUMBER = 11;
      public static final int SDK_VERSION_FIELD_NUMBER = 9;
      private boolean buildApproved_;
      private int cachedSize;
      private int carrierIdDeprecated_ = 0;
      private DeviceConfigurationProto deviceConfig_ = null;
      private String device_ = "";
      private int googleServices_ = 0;
      private boolean hasBuildApproved;
      private boolean hasCarrierIdDeprecated;
      private boolean hasDevice;
      private boolean hasDeviceConfig;
      private boolean hasGoogleServices;
      private boolean hasIpCountry;
      private boolean hasLatestBuildFingerprint;
      private boolean hasMaker;
      private boolean hasMccmnc;
      private boolean hasModel;
      private boolean hasSdkVersion;
      private String ipCountry_ = "";
      private String latestBuildFingerprint_ = "";
      private String maker_ = "";
      private List<Integer> marketClientFeature_;
      private String mccmnc_ = "";
      private String model_ = "";
      private int sdkVersion_ = 0;


      public Device() {
         List var1 = Collections.emptyList();
         this.marketClientFeature_ = var1;
         this.buildApproved_ = (boolean)0;
         this.cachedSize = -1;
      }

      public static Dev.Device parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Dev.Device()).mergeFrom(var0);
      }

      public static Dev.Device parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Dev.Device)((Dev.Device)(new Dev.Device()).mergeFrom(var0));
      }

      public Dev.Device addMarketClientFeature(int var1) {
         if(this.marketClientFeature_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.marketClientFeature_ = var2;
         }

         List var3 = this.marketClientFeature_;
         Integer var4 = Integer.valueOf(var1);
         var3.add(var4);
         return this;
      }

      public final Dev.Device clear() {
         Dev.Device var1 = this.clearDeviceConfig();
         Dev.Device var2 = this.clearIpCountry();
         Dev.Device var3 = this.clearMccmnc();
         Dev.Device var4 = this.clearCarrierIdDeprecated();
         Dev.Device var5 = this.clearLatestBuildFingerprint();
         Dev.Device var6 = this.clearGoogleServices();
         Dev.Device var7 = this.clearSdkVersion();
         Dev.Device var8 = this.clearMaker();
         Dev.Device var9 = this.clearModel();
         Dev.Device var10 = this.clearDevice();
         Dev.Device var11 = this.clearMarketClientFeature();
         Dev.Device var12 = this.clearBuildApproved();
         this.cachedSize = -1;
         return this;
      }

      public Dev.Device clearBuildApproved() {
         this.hasBuildApproved = (boolean)0;
         this.buildApproved_ = (boolean)0;
         return this;
      }

      public Dev.Device clearCarrierIdDeprecated() {
         this.hasCarrierIdDeprecated = (boolean)0;
         this.carrierIdDeprecated_ = 0;
         return this;
      }

      public Dev.Device clearDevice() {
         this.hasDevice = (boolean)0;
         this.device_ = "";
         return this;
      }

      public Dev.Device clearDeviceConfig() {
         this.hasDeviceConfig = (boolean)0;
         this.deviceConfig_ = null;
         return this;
      }

      public Dev.Device clearGoogleServices() {
         this.hasGoogleServices = (boolean)0;
         this.googleServices_ = 0;
         return this;
      }

      public Dev.Device clearIpCountry() {
         this.hasIpCountry = (boolean)0;
         this.ipCountry_ = "";
         return this;
      }

      public Dev.Device clearLatestBuildFingerprint() {
         this.hasLatestBuildFingerprint = (boolean)0;
         this.latestBuildFingerprint_ = "";
         return this;
      }

      public Dev.Device clearMaker() {
         this.hasMaker = (boolean)0;
         this.maker_ = "";
         return this;
      }

      public Dev.Device clearMarketClientFeature() {
         List var1 = Collections.emptyList();
         this.marketClientFeature_ = var1;
         return this;
      }

      public Dev.Device clearMccmnc() {
         this.hasMccmnc = (boolean)0;
         this.mccmnc_ = "";
         return this;
      }

      public Dev.Device clearModel() {
         this.hasModel = (boolean)0;
         this.model_ = "";
         return this;
      }

      public Dev.Device clearSdkVersion() {
         this.hasSdkVersion = (boolean)0;
         this.sdkVersion_ = 0;
         return this;
      }

      public boolean getBuildApproved() {
         return this.buildApproved_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getCarrierIdDeprecated() {
         return this.carrierIdDeprecated_;
      }

      public String getDevice() {
         return this.device_;
      }

      public DeviceConfigurationProto getDeviceConfig() {
         return this.deviceConfig_;
      }

      public int getGoogleServices() {
         return this.googleServices_;
      }

      public String getIpCountry() {
         return this.ipCountry_;
      }

      public String getLatestBuildFingerprint() {
         return this.latestBuildFingerprint_;
      }

      public String getMaker() {
         return this.maker_;
      }

      public int getMarketClientFeature(int var1) {
         return ((Integer)this.marketClientFeature_.get(var1)).intValue();
      }

      public int getMarketClientFeatureCount() {
         return this.marketClientFeature_.size();
      }

      public List<Integer> getMarketClientFeatureList() {
         return this.marketClientFeature_;
      }

      public String getMccmnc() {
         return this.mccmnc_;
      }

      public String getModel() {
         return this.model_;
      }

      public int getSdkVersion() {
         return this.sdkVersion_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasDeviceConfig()) {
            DeviceConfigurationProto var2 = this.getDeviceConfig();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasIpCountry()) {
            String var4 = this.getIpCountry();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasMccmnc()) {
            String var6 = this.getMccmnc();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasCarrierIdDeprecated()) {
            int var8 = this.getCarrierIdDeprecated();
            int var9 = CodedOutputStreamMicro.computeInt32Size(4, var8);
            var1 += var9;
         }

         if(this.hasLatestBuildFingerprint()) {
            String var10 = this.getLatestBuildFingerprint();
            int var11 = CodedOutputStreamMicro.computeStringSize(5, var10);
            var1 += var11;
         }

         if(this.hasGoogleServices()) {
            int var12 = this.getGoogleServices();
            int var13 = CodedOutputStreamMicro.computeInt32Size(8, var12);
            var1 += var13;
         }

         if(this.hasSdkVersion()) {
            int var14 = this.getSdkVersion();
            int var15 = CodedOutputStreamMicro.computeInt32Size(9, var14);
            var1 += var15;
         }

         if(this.hasMaker()) {
            String var16 = this.getMaker();
            int var17 = CodedOutputStreamMicro.computeStringSize(10, var16);
            var1 += var17;
         }

         if(this.hasModel()) {
            String var18 = this.getModel();
            int var19 = CodedOutputStreamMicro.computeStringSize(11, var18);
            var1 += var19;
         }

         if(this.hasDevice()) {
            String var20 = this.getDevice();
            int var21 = CodedOutputStreamMicro.computeStringSize(12, var20);
            var1 += var21;
         }

         int var22 = 0;

         int var24;
         for(Iterator var23 = this.getMarketClientFeatureList().iterator(); var23.hasNext(); var22 += var24) {
            var24 = CodedOutputStreamMicro.computeInt32SizeNoTag(((Integer)var23.next()).intValue());
         }

         int var25 = var1 + var22;
         int var26 = this.getMarketClientFeatureList().size() * 1;
         int var27 = var25 + var26;
         if(this.hasBuildApproved()) {
            boolean var28 = this.getBuildApproved();
            int var29 = CodedOutputStreamMicro.computeBoolSize(14, var28);
            var27 += var29;
         }

         this.cachedSize = var27;
         return var27;
      }

      public boolean hasBuildApproved() {
         return this.hasBuildApproved;
      }

      public boolean hasCarrierIdDeprecated() {
         return this.hasCarrierIdDeprecated;
      }

      public boolean hasDevice() {
         return this.hasDevice;
      }

      public boolean hasDeviceConfig() {
         return this.hasDeviceConfig;
      }

      public boolean hasGoogleServices() {
         return this.hasGoogleServices;
      }

      public boolean hasIpCountry() {
         return this.hasIpCountry;
      }

      public boolean hasLatestBuildFingerprint() {
         return this.hasLatestBuildFingerprint;
      }

      public boolean hasMaker() {
         return this.hasMaker;
      }

      public boolean hasMccmnc() {
         return this.hasMccmnc;
      }

      public boolean hasModel() {
         return this.hasModel;
      }

      public boolean hasSdkVersion() {
         return this.hasSdkVersion;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasDeviceConfig && this.getDeviceConfig().isInitialized()) {
            var1 = true;
         }

         return var1;
      }

      public Dev.Device mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               DeviceConfigurationProto var3 = new DeviceConfigurationProto();
               var1.readMessage(var3);
               this.setDeviceConfig(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setIpCountry(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setMccmnc(var7);
               break;
            case 32:
               int var9 = var1.readInt32();
               this.setCarrierIdDeprecated(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setLatestBuildFingerprint(var11);
               break;
            case 64:
               int var13 = var1.readInt32();
               this.setGoogleServices(var13);
               break;
            case 72:
               int var15 = var1.readInt32();
               this.setSdkVersion(var15);
               break;
            case 82:
               String var17 = var1.readString();
               this.setMaker(var17);
               break;
            case 90:
               String var19 = var1.readString();
               this.setModel(var19);
               break;
            case 98:
               String var21 = var1.readString();
               this.setDevice(var21);
               break;
            case 104:
               int var23 = var1.readInt32();
               this.addMarketClientFeature(var23);
               break;
            case 112:
               boolean var25 = var1.readBool();
               this.setBuildApproved(var25);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public Dev.Device setBuildApproved(boolean var1) {
         this.hasBuildApproved = (boolean)1;
         this.buildApproved_ = var1;
         return this;
      }

      public Dev.Device setCarrierIdDeprecated(int var1) {
         this.hasCarrierIdDeprecated = (boolean)1;
         this.carrierIdDeprecated_ = var1;
         return this;
      }

      public Dev.Device setDevice(String var1) {
         this.hasDevice = (boolean)1;
         this.device_ = var1;
         return this;
      }

      public Dev.Device setDeviceConfig(DeviceConfigurationProto var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDeviceConfig = (boolean)1;
            this.deviceConfig_ = var1;
            return this;
         }
      }

      public Dev.Device setGoogleServices(int var1) {
         this.hasGoogleServices = (boolean)1;
         this.googleServices_ = var1;
         return this;
      }

      public Dev.Device setIpCountry(String var1) {
         this.hasIpCountry = (boolean)1;
         this.ipCountry_ = var1;
         return this;
      }

      public Dev.Device setLatestBuildFingerprint(String var1) {
         this.hasLatestBuildFingerprint = (boolean)1;
         this.latestBuildFingerprint_ = var1;
         return this;
      }

      public Dev.Device setMaker(String var1) {
         this.hasMaker = (boolean)1;
         this.maker_ = var1;
         return this;
      }

      public Dev.Device setMarketClientFeature(int var1, int var2) {
         List var3 = this.marketClientFeature_;
         Integer var4 = Integer.valueOf(var2);
         var3.set(var1, var4);
         return this;
      }

      public Dev.Device setMccmnc(String var1) {
         this.hasMccmnc = (boolean)1;
         this.mccmnc_ = var1;
         return this;
      }

      public Dev.Device setModel(String var1) {
         this.hasModel = (boolean)1;
         this.model_ = var1;
         return this;
      }

      public Dev.Device setSdkVersion(int var1) {
         this.hasSdkVersion = (boolean)1;
         this.sdkVersion_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasDeviceConfig()) {
            DeviceConfigurationProto var2 = this.getDeviceConfig();
            var1.writeMessage(1, var2);
         }

         if(this.hasIpCountry()) {
            String var3 = this.getIpCountry();
            var1.writeString(2, var3);
         }

         if(this.hasMccmnc()) {
            String var4 = this.getMccmnc();
            var1.writeString(3, var4);
         }

         if(this.hasCarrierIdDeprecated()) {
            int var5 = this.getCarrierIdDeprecated();
            var1.writeInt32(4, var5);
         }

         if(this.hasLatestBuildFingerprint()) {
            String var6 = this.getLatestBuildFingerprint();
            var1.writeString(5, var6);
         }

         if(this.hasGoogleServices()) {
            int var7 = this.getGoogleServices();
            var1.writeInt32(8, var7);
         }

         if(this.hasSdkVersion()) {
            int var8 = this.getSdkVersion();
            var1.writeInt32(9, var8);
         }

         if(this.hasMaker()) {
            String var9 = this.getMaker();
            var1.writeString(10, var9);
         }

         if(this.hasModel()) {
            String var10 = this.getModel();
            var1.writeString(11, var10);
         }

         if(this.hasDevice()) {
            String var11 = this.getDevice();
            var1.writeString(12, var11);
         }

         Iterator var12 = this.getMarketClientFeatureList().iterator();

         while(var12.hasNext()) {
            int var13 = ((Integer)var12.next()).intValue();
            var1.writeInt32(13, var13);
         }

         if(this.hasBuildApproved()) {
            boolean var14 = this.getBuildApproved();
            var1.writeBool(14, var14);
         }
      }
   }
}
