package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Dev;
import com.google.android.finsky.remoting.protos.UserLocale;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class RequestContext extends MessageMicro {

   public static final int AMAS = 6;
   public static final int CARRIER_MCCMNC_FIELD_NUMBER = 9;
   public static final int CUSTOMER_SUPPORT = 3;
   public static final int DEBUG_LEVEL_FIELD_NUMBER = 7;
   public static final int DEVICE_CONFIG_ANDROID_ID_FIELD_NUMBER = 11;
   public static final int DEVICE_CONFIG_FIELD_NUMBER = 12;
   public static final int DEVICE_FRONTEND = 2;
   public static final int EXPERIMENT_ID_FIELD_NUMBER = 14;
   public static final int GAIA_ID_FIELD_NUMBER = 4;
   public static final int INTERNAL = 5;
   public static final int MY_ACCOUNT_DASHBOARD = 4;
   public static final int SAFESEARCH_LEVEL_FIELD_NUMBER = 13;
   public static final int SOURCE_FIELD_NUMBER = 10;
   public static final int UNKNOWN = 0;
   public static final int USER_IP_ADDRESS_FIELD_NUMBER = 3;
   public static final int USER_IP_COUNTRY_FIELD_NUMBER = 2;
   public static final int USER_LOCALE_FIELD_NUMBER = 1;
   public static final int WEB_FRONTEND = 1;
   private int cachedSize;
   private List<String> carrierMccmnc_;
   private int debugLevel_ = 0;
   private List<Long> deviceConfigAndroidId_;
   private List<Dev.Device> deviceConfig_;
   private List<String> experimentId_;
   private long gaiaId_ = 0L;
   private boolean hasDebugLevel;
   private boolean hasGaiaId;
   private boolean hasSafesearchLevel;
   private boolean hasSource;
   private boolean hasUserIpAddress;
   private boolean hasUserIpCountry;
   private boolean hasUserLocale;
   private int safesearchLevel_;
   private int source_;
   private String userIpAddress_ = "";
   private String userIpCountry_ = "US";
   private UserLocale userLocale_ = null;


   public RequestContext() {
      List var1 = Collections.emptyList();
      this.carrierMccmnc_ = var1;
      this.source_ = 0;
      List var2 = Collections.emptyList();
      this.deviceConfigAndroidId_ = var2;
      List var3 = Collections.emptyList();
      this.deviceConfig_ = var3;
      this.safesearchLevel_ = 0;
      List var4 = Collections.emptyList();
      this.experimentId_ = var4;
      this.cachedSize = -1;
   }

   public static RequestContext parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new RequestContext()).mergeFrom(var0);
   }

   public static RequestContext parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (RequestContext)((RequestContext)(new RequestContext()).mergeFrom(var0));
   }

   public RequestContext addCarrierMccmnc(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.carrierMccmnc_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.carrierMccmnc_ = var2;
         }

         this.carrierMccmnc_.add(var1);
         return this;
      }
   }

   public RequestContext addDeviceConfig(Dev.Device var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.deviceConfig_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.deviceConfig_ = var2;
         }

         this.deviceConfig_.add(var1);
         return this;
      }
   }

   public RequestContext addDeviceConfigAndroidId(long var1) {
      if(this.deviceConfigAndroidId_.isEmpty()) {
         ArrayList var3 = new ArrayList();
         this.deviceConfigAndroidId_ = var3;
      }

      List var4 = this.deviceConfigAndroidId_;
      Long var5 = Long.valueOf(var1);
      var4.add(var5);
      return this;
   }

   public RequestContext addExperimentId(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.experimentId_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.experimentId_ = var2;
         }

         this.experimentId_.add(var1);
         return this;
      }
   }

   public final RequestContext clear() {
      RequestContext var1 = this.clearUserLocale();
      RequestContext var2 = this.clearUserIpCountry();
      RequestContext var3 = this.clearUserIpAddress();
      RequestContext var4 = this.clearGaiaId();
      RequestContext var5 = this.clearDebugLevel();
      RequestContext var6 = this.clearCarrierMccmnc();
      RequestContext var7 = this.clearSource();
      RequestContext var8 = this.clearDeviceConfigAndroidId();
      RequestContext var9 = this.clearDeviceConfig();
      RequestContext var10 = this.clearSafesearchLevel();
      RequestContext var11 = this.clearExperimentId();
      this.cachedSize = -1;
      return this;
   }

   public RequestContext clearCarrierMccmnc() {
      List var1 = Collections.emptyList();
      this.carrierMccmnc_ = var1;
      return this;
   }

   public RequestContext clearDebugLevel() {
      this.hasDebugLevel = (boolean)0;
      this.debugLevel_ = 0;
      return this;
   }

   public RequestContext clearDeviceConfig() {
      List var1 = Collections.emptyList();
      this.deviceConfig_ = var1;
      return this;
   }

   public RequestContext clearDeviceConfigAndroidId() {
      List var1 = Collections.emptyList();
      this.deviceConfigAndroidId_ = var1;
      return this;
   }

   public RequestContext clearExperimentId() {
      List var1 = Collections.emptyList();
      this.experimentId_ = var1;
      return this;
   }

   public RequestContext clearGaiaId() {
      this.hasGaiaId = (boolean)0;
      this.gaiaId_ = 0L;
      return this;
   }

   public RequestContext clearSafesearchLevel() {
      this.hasSafesearchLevel = (boolean)0;
      this.safesearchLevel_ = 0;
      return this;
   }

   public RequestContext clearSource() {
      this.hasSource = (boolean)0;
      this.source_ = 0;
      return this;
   }

   public RequestContext clearUserIpAddress() {
      this.hasUserIpAddress = (boolean)0;
      this.userIpAddress_ = "";
      return this;
   }

   public RequestContext clearUserIpCountry() {
      this.hasUserIpCountry = (boolean)0;
      this.userIpCountry_ = "US";
      return this;
   }

   public RequestContext clearUserLocale() {
      this.hasUserLocale = (boolean)0;
      this.userLocale_ = null;
      return this;
   }

   public int getCachedSize() {
      if(this.cachedSize < 0) {
         int var1 = this.getSerializedSize();
      }

      return this.cachedSize;
   }

   public String getCarrierMccmnc(int var1) {
      return (String)this.carrierMccmnc_.get(var1);
   }

   public int getCarrierMccmncCount() {
      return this.carrierMccmnc_.size();
   }

   public List<String> getCarrierMccmncList() {
      return this.carrierMccmnc_;
   }

   public int getDebugLevel() {
      return this.debugLevel_;
   }

   public Dev.Device getDeviceConfig(int var1) {
      return (Dev.Device)this.deviceConfig_.get(var1);
   }

   public long getDeviceConfigAndroidId(int var1) {
      return ((Long)this.deviceConfigAndroidId_.get(var1)).longValue();
   }

   public int getDeviceConfigAndroidIdCount() {
      return this.deviceConfigAndroidId_.size();
   }

   public List<Long> getDeviceConfigAndroidIdList() {
      return this.deviceConfigAndroidId_;
   }

   public int getDeviceConfigCount() {
      return this.deviceConfig_.size();
   }

   public List<Dev.Device> getDeviceConfigList() {
      return this.deviceConfig_;
   }

   public String getExperimentId(int var1) {
      return (String)this.experimentId_.get(var1);
   }

   public int getExperimentIdCount() {
      return this.experimentId_.size();
   }

   public List<String> getExperimentIdList() {
      return this.experimentId_;
   }

   public long getGaiaId() {
      return this.gaiaId_;
   }

   public int getSafesearchLevel() {
      return this.safesearchLevel_;
   }

   public int getSerializedSize() {
      int var1 = 0;
      if(this.hasUserLocale()) {
         UserLocale var2 = this.getUserLocale();
         int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
         var1 = 0 + var3;
      }

      if(this.hasUserIpCountry()) {
         String var4 = this.getUserIpCountry();
         int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
         var1 += var5;
      }

      if(this.hasUserIpAddress()) {
         String var6 = this.getUserIpAddress();
         int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
         var1 += var7;
      }

      if(this.hasGaiaId()) {
         long var8 = this.getGaiaId();
         int var10 = CodedOutputStreamMicro.computeFixed64Size(4, var8);
         var1 += var10;
      }

      if(this.hasDebugLevel()) {
         int var11 = this.getDebugLevel();
         int var12 = CodedOutputStreamMicro.computeInt32Size(7, var11);
         var1 += var12;
      }

      int var13 = 0;

      int var15;
      for(Iterator var14 = this.getCarrierMccmncList().iterator(); var14.hasNext(); var13 += var15) {
         var15 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var14.next());
      }

      int var16 = var1 + var13;
      int var17 = this.getCarrierMccmncList().size() * 1;
      int var18 = var16 + var17;
      if(this.hasSource()) {
         int var19 = this.getSource();
         int var20 = CodedOutputStreamMicro.computeInt32Size(10, var19);
         var18 += var20;
      }

      int var21 = this.getDeviceConfigAndroidIdList().size() * 8;
      int var22 = var18 + var21;
      int var23 = this.getDeviceConfigAndroidIdList().size() * 1;
      int var24 = var22 + var23;

      int var27;
      for(Iterator var25 = this.getDeviceConfigList().iterator(); var25.hasNext(); var24 += var27) {
         Dev.Device var26 = (Dev.Device)var25.next();
         var27 = CodedOutputStreamMicro.computeMessageSize(12, var26);
      }

      if(this.hasSafesearchLevel()) {
         int var28 = this.getSafesearchLevel();
         int var29 = CodedOutputStreamMicro.computeInt32Size(13, var28);
         var24 += var29;
      }

      int var30 = 0;

      int var32;
      for(Iterator var31 = this.getExperimentIdList().iterator(); var31.hasNext(); var30 += var32) {
         var32 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var31.next());
      }

      int var33 = var24 + var30;
      int var34 = this.getExperimentIdList().size() * 1;
      int var35 = var33 + var34;
      this.cachedSize = var35;
      return var35;
   }

   public int getSource() {
      return this.source_;
   }

   public String getUserIpAddress() {
      return this.userIpAddress_;
   }

   public String getUserIpCountry() {
      return this.userIpCountry_;
   }

   public UserLocale getUserLocale() {
      return this.userLocale_;
   }

   public boolean hasDebugLevel() {
      return this.hasDebugLevel;
   }

   public boolean hasGaiaId() {
      return this.hasGaiaId;
   }

   public boolean hasSafesearchLevel() {
      return this.hasSafesearchLevel;
   }

   public boolean hasSource() {
      return this.hasSource;
   }

   public boolean hasUserIpAddress() {
      return this.hasUserIpAddress;
   }

   public boolean hasUserIpCountry() {
      return this.hasUserIpCountry;
   }

   public boolean hasUserLocale() {
      return this.hasUserLocale;
   }

   public final boolean isInitialized() {
      Iterator var1 = this.getDeviceConfigList().iterator();

      boolean var2;
      while(true) {
         if(var1.hasNext()) {
            if(((Dev.Device)var1.next()).isInitialized()) {
               continue;
            }

            var2 = false;
            break;
         }

         var2 = true;
         break;
      }

      return var2;
   }

   public RequestContext mergeFrom(CodedInputStreamMicro var1) throws IOException {
      while(true) {
         int var2 = var1.readTag();
         switch(var2) {
         case 10:
            UserLocale var3 = new UserLocale();
            var1.readMessage(var3);
            this.setUserLocale(var3);
            break;
         case 18:
            String var5 = var1.readString();
            this.setUserIpCountry(var5);
            break;
         case 26:
            String var7 = var1.readString();
            this.setUserIpAddress(var7);
            break;
         case 33:
            long var9 = var1.readFixed64();
            this.setGaiaId(var9);
            break;
         case 56:
            int var12 = var1.readInt32();
            this.setDebugLevel(var12);
            break;
         case 74:
            String var14 = var1.readString();
            this.addCarrierMccmnc(var14);
            break;
         case 80:
            int var16 = var1.readInt32();
            this.setSource(var16);
            break;
         case 89:
            long var18 = var1.readFixed64();
            this.addDeviceConfigAndroidId(var18);
            break;
         case 98:
            Dev.Device var21 = new Dev.Device();
            var1.readMessage(var21);
            this.addDeviceConfig(var21);
            break;
         case 104:
            int var23 = var1.readInt32();
            this.setSafesearchLevel(var23);
            break;
         case 114:
            String var25 = var1.readString();
            this.addExperimentId(var25);
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

   public RequestContext setCarrierMccmnc(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.carrierMccmnc_.set(var1, var2);
         return this;
      }
   }

   public RequestContext setDebugLevel(int var1) {
      this.hasDebugLevel = (boolean)1;
      this.debugLevel_ = var1;
      return this;
   }

   public RequestContext setDeviceConfig(int var1, Dev.Device var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.deviceConfig_.set(var1, var2);
         return this;
      }
   }

   public RequestContext setDeviceConfigAndroidId(int var1, long var2) {
      List var4 = this.deviceConfigAndroidId_;
      Long var5 = Long.valueOf(var2);
      var4.set(var1, var5);
      return this;
   }

   public RequestContext setExperimentId(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.experimentId_.set(var1, var2);
         return this;
      }
   }

   public RequestContext setGaiaId(long var1) {
      this.hasGaiaId = (boolean)1;
      this.gaiaId_ = var1;
      return this;
   }

   public RequestContext setSafesearchLevel(int var1) {
      this.hasSafesearchLevel = (boolean)1;
      this.safesearchLevel_ = var1;
      return this;
   }

   public RequestContext setSource(int var1) {
      this.hasSource = (boolean)1;
      this.source_ = var1;
      return this;
   }

   public RequestContext setUserIpAddress(String var1) {
      this.hasUserIpAddress = (boolean)1;
      this.userIpAddress_ = var1;
      return this;
   }

   public RequestContext setUserIpCountry(String var1) {
      this.hasUserIpCountry = (boolean)1;
      this.userIpCountry_ = var1;
      return this;
   }

   public RequestContext setUserLocale(UserLocale var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         this.hasUserLocale = (boolean)1;
         this.userLocale_ = var1;
         return this;
      }
   }

   public void writeTo(CodedOutputStreamMicro var1) throws IOException {
      if(this.hasUserLocale()) {
         UserLocale var2 = this.getUserLocale();
         var1.writeMessage(1, var2);
      }

      if(this.hasUserIpCountry()) {
         String var3 = this.getUserIpCountry();
         var1.writeString(2, var3);
      }

      if(this.hasUserIpAddress()) {
         String var4 = this.getUserIpAddress();
         var1.writeString(3, var4);
      }

      if(this.hasGaiaId()) {
         long var5 = this.getGaiaId();
         var1.writeFixed64(4, var5);
      }

      if(this.hasDebugLevel()) {
         int var7 = this.getDebugLevel();
         var1.writeInt32(7, var7);
      }

      Iterator var8 = this.getCarrierMccmncList().iterator();

      while(var8.hasNext()) {
         String var9 = (String)var8.next();
         var1.writeString(9, var9);
      }

      if(this.hasSource()) {
         int var10 = this.getSource();
         var1.writeInt32(10, var10);
      }

      Iterator var11 = this.getDeviceConfigAndroidIdList().iterator();

      while(var11.hasNext()) {
         long var12 = ((Long)var11.next()).longValue();
         var1.writeFixed64(11, var12);
      }

      Iterator var14 = this.getDeviceConfigList().iterator();

      while(var14.hasNext()) {
         Dev.Device var15 = (Dev.Device)var14.next();
         var1.writeMessage(12, var15);
      }

      if(this.hasSafesearchLevel()) {
         int var16 = this.getSafesearchLevel();
         var1.writeInt32(13, var16);
      }

      Iterator var17 = this.getExperimentIdList().iterator();

      while(var17.hasNext()) {
         String var18 = (String)var17.next();
         var1.writeString(14, var18);
      }

   }
}
