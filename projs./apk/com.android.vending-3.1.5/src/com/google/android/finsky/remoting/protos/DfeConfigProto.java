package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class DfeConfigProto extends MessageMicro {

   public static final int ALL_CHANNEL_GROUP_FIELD_NUMBER = 7;
   public static final int BLACKLISTED_CONTAINER_ID_FIELD_NUMBER = 5;
   public static final int CATEGORY_NAVIGABLE_CHANNEL_FIELD_NUMBER = 4;
   public static final int CLIENT_VERSION_CHECK_GROUP_FIELD_NUMBER = 3;
   public static final int DEVICE_FE_ERROR_PRONE_PATH_PREFIX_FIELD_NUMBER = 9;
   public static final int DEVICE_FE_ERROR_PRONE_PERCENTAGE_FIELD_NUMBER = 10;
   public static final int ENABLEDCHANNEL_FIELD_NUMBER = 8;
   public static final int MIN_SUPPORTED_CLIENT_VERSION_FIELD_NUMBER = 1;
   public static final int TEST_KEYS_MANDATORY_UPDATE_CHECK_FIELD_NUMBER = 2;
   public static final int WHITELIST_GROUP_FIELD_NUMBER = 6;
   private List<String> allChannelGroup_;
   private List<String> blacklistedContainerId_;
   private int cachedSize;
   private List<Integer> categoryNavigableChannel_;
   private List<String> clientVersionCheckGroup_;
   private String deviceFeErrorPronePathPrefix_;
   private int deviceFeErrorPronePercentage_;
   private List<Integer> enabledChannel_;
   private boolean hasDeviceFeErrorPronePathPrefix;
   private boolean hasDeviceFeErrorPronePercentage;
   private boolean hasMinSupportedClientVersion;
   private boolean hasTestKeysMandatoryUpdateCheck;
   private int minSupportedClientVersion_ = -1;
   private boolean testKeysMandatoryUpdateCheck_ = 0;
   private List<String> whitelistGroup_;


   public DfeConfigProto() {
      List var1 = Collections.emptyList();
      this.clientVersionCheckGroup_ = var1;
      List var2 = Collections.emptyList();
      this.categoryNavigableChannel_ = var2;
      List var3 = Collections.emptyList();
      this.blacklistedContainerId_ = var3;
      List var4 = Collections.emptyList();
      this.whitelistGroup_ = var4;
      List var5 = Collections.emptyList();
      this.allChannelGroup_ = var5;
      List var6 = Collections.emptyList();
      this.enabledChannel_ = var6;
      this.deviceFeErrorPronePathPrefix_ = "/shaky";
      this.deviceFeErrorPronePercentage_ = 20;
      this.cachedSize = -1;
   }

   public static DfeConfigProto parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new DfeConfigProto()).mergeFrom(var0);
   }

   public static DfeConfigProto parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (DfeConfigProto)((DfeConfigProto)(new DfeConfigProto()).mergeFrom(var0));
   }

   public DfeConfigProto addAllChannelGroup(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.allChannelGroup_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.allChannelGroup_ = var2;
         }

         this.allChannelGroup_.add(var1);
         return this;
      }
   }

   public DfeConfigProto addBlacklistedContainerId(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.blacklistedContainerId_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.blacklistedContainerId_ = var2;
         }

         this.blacklistedContainerId_.add(var1);
         return this;
      }
   }

   public DfeConfigProto addCategoryNavigableChannel(int var1) {
      if(this.categoryNavigableChannel_.isEmpty()) {
         ArrayList var2 = new ArrayList();
         this.categoryNavigableChannel_ = var2;
      }

      List var3 = this.categoryNavigableChannel_;
      Integer var4 = Integer.valueOf(var1);
      var3.add(var4);
      return this;
   }

   public DfeConfigProto addClientVersionCheckGroup(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.clientVersionCheckGroup_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.clientVersionCheckGroup_ = var2;
         }

         this.clientVersionCheckGroup_.add(var1);
         return this;
      }
   }

   public DfeConfigProto addEnabledChannel(int var1) {
      if(this.enabledChannel_.isEmpty()) {
         ArrayList var2 = new ArrayList();
         this.enabledChannel_ = var2;
      }

      List var3 = this.enabledChannel_;
      Integer var4 = Integer.valueOf(var1);
      var3.add(var4);
      return this;
   }

   public DfeConfigProto addWhitelistGroup(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.whitelistGroup_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.whitelistGroup_ = var2;
         }

         this.whitelistGroup_.add(var1);
         return this;
      }
   }

   public final DfeConfigProto clear() {
      DfeConfigProto var1 = this.clearMinSupportedClientVersion();
      DfeConfigProto var2 = this.clearTestKeysMandatoryUpdateCheck();
      DfeConfigProto var3 = this.clearClientVersionCheckGroup();
      DfeConfigProto var4 = this.clearCategoryNavigableChannel();
      DfeConfigProto var5 = this.clearBlacklistedContainerId();
      DfeConfigProto var6 = this.clearWhitelistGroup();
      DfeConfigProto var7 = this.clearAllChannelGroup();
      DfeConfigProto var8 = this.clearEnabledChannel();
      DfeConfigProto var9 = this.clearDeviceFeErrorPronePathPrefix();
      DfeConfigProto var10 = this.clearDeviceFeErrorPronePercentage();
      this.cachedSize = -1;
      return this;
   }

   public DfeConfigProto clearAllChannelGroup() {
      List var1 = Collections.emptyList();
      this.allChannelGroup_ = var1;
      return this;
   }

   public DfeConfigProto clearBlacklistedContainerId() {
      List var1 = Collections.emptyList();
      this.blacklistedContainerId_ = var1;
      return this;
   }

   public DfeConfigProto clearCategoryNavigableChannel() {
      List var1 = Collections.emptyList();
      this.categoryNavigableChannel_ = var1;
      return this;
   }

   public DfeConfigProto clearClientVersionCheckGroup() {
      List var1 = Collections.emptyList();
      this.clientVersionCheckGroup_ = var1;
      return this;
   }

   public DfeConfigProto clearDeviceFeErrorPronePathPrefix() {
      this.hasDeviceFeErrorPronePathPrefix = (boolean)0;
      this.deviceFeErrorPronePathPrefix_ = "/shaky";
      return this;
   }

   public DfeConfigProto clearDeviceFeErrorPronePercentage() {
      this.hasDeviceFeErrorPronePercentage = (boolean)0;
      this.deviceFeErrorPronePercentage_ = 20;
      return this;
   }

   public DfeConfigProto clearEnabledChannel() {
      List var1 = Collections.emptyList();
      this.enabledChannel_ = var1;
      return this;
   }

   public DfeConfigProto clearMinSupportedClientVersion() {
      this.hasMinSupportedClientVersion = (boolean)0;
      this.minSupportedClientVersion_ = -1;
      return this;
   }

   public DfeConfigProto clearTestKeysMandatoryUpdateCheck() {
      this.hasTestKeysMandatoryUpdateCheck = (boolean)0;
      this.testKeysMandatoryUpdateCheck_ = (boolean)0;
      return this;
   }

   public DfeConfigProto clearWhitelistGroup() {
      List var1 = Collections.emptyList();
      this.whitelistGroup_ = var1;
      return this;
   }

   public String getAllChannelGroup(int var1) {
      return (String)this.allChannelGroup_.get(var1);
   }

   public int getAllChannelGroupCount() {
      return this.allChannelGroup_.size();
   }

   public List<String> getAllChannelGroupList() {
      return this.allChannelGroup_;
   }

   public String getBlacklistedContainerId(int var1) {
      return (String)this.blacklistedContainerId_.get(var1);
   }

   public int getBlacklistedContainerIdCount() {
      return this.blacklistedContainerId_.size();
   }

   public List<String> getBlacklistedContainerIdList() {
      return this.blacklistedContainerId_;
   }

   public int getCachedSize() {
      if(this.cachedSize < 0) {
         int var1 = this.getSerializedSize();
      }

      return this.cachedSize;
   }

   public int getCategoryNavigableChannel(int var1) {
      return ((Integer)this.categoryNavigableChannel_.get(var1)).intValue();
   }

   public int getCategoryNavigableChannelCount() {
      return this.categoryNavigableChannel_.size();
   }

   public List<Integer> getCategoryNavigableChannelList() {
      return this.categoryNavigableChannel_;
   }

   public String getClientVersionCheckGroup(int var1) {
      return (String)this.clientVersionCheckGroup_.get(var1);
   }

   public int getClientVersionCheckGroupCount() {
      return this.clientVersionCheckGroup_.size();
   }

   public List<String> getClientVersionCheckGroupList() {
      return this.clientVersionCheckGroup_;
   }

   public String getDeviceFeErrorPronePathPrefix() {
      return this.deviceFeErrorPronePathPrefix_;
   }

   public int getDeviceFeErrorPronePercentage() {
      return this.deviceFeErrorPronePercentage_;
   }

   public int getEnabledChannel(int var1) {
      return ((Integer)this.enabledChannel_.get(var1)).intValue();
   }

   public int getEnabledChannelCount() {
      return this.enabledChannel_.size();
   }

   public List<Integer> getEnabledChannelList() {
      return this.enabledChannel_;
   }

   public int getMinSupportedClientVersion() {
      return this.minSupportedClientVersion_;
   }

   public int getSerializedSize() {
      int var1 = 0;
      if(this.hasMinSupportedClientVersion()) {
         int var2 = this.getMinSupportedClientVersion();
         int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
         var1 = 0 + var3;
      }

      if(this.hasTestKeysMandatoryUpdateCheck()) {
         boolean var4 = this.getTestKeysMandatoryUpdateCheck();
         int var5 = CodedOutputStreamMicro.computeBoolSize(2, var4);
         var1 += var5;
      }

      int var6 = 0;

      int var8;
      for(Iterator var7 = this.getClientVersionCheckGroupList().iterator(); var7.hasNext(); var6 += var8) {
         var8 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var7.next());
      }

      int var9 = var1 + var6;
      int var10 = this.getClientVersionCheckGroupList().size() * 1;
      int var11 = var9 + var10;
      int var12 = 0;

      int var14;
      for(Iterator var13 = this.getCategoryNavigableChannelList().iterator(); var13.hasNext(); var12 += var14) {
         var14 = CodedOutputStreamMicro.computeInt32SizeNoTag(((Integer)var13.next()).intValue());
      }

      int var15 = var11 + var12;
      int var16 = this.getCategoryNavigableChannelList().size() * 1;
      int var17 = var15 + var16;
      int var18 = 0;

      int var20;
      for(Iterator var19 = this.getBlacklistedContainerIdList().iterator(); var19.hasNext(); var18 += var20) {
         var20 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var19.next());
      }

      int var21 = var17 + var18;
      int var22 = this.getBlacklistedContainerIdList().size() * 1;
      int var23 = var21 + var22;
      int var24 = 0;

      int var26;
      for(Iterator var25 = this.getWhitelistGroupList().iterator(); var25.hasNext(); var24 += var26) {
         var26 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var25.next());
      }

      int var27 = var23 + var24;
      int var28 = this.getWhitelistGroupList().size() * 1;
      int var29 = var27 + var28;
      int var30 = 0;

      int var32;
      for(Iterator var31 = this.getAllChannelGroupList().iterator(); var31.hasNext(); var30 += var32) {
         var32 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var31.next());
      }

      int var33 = var29 + var30;
      int var34 = this.getAllChannelGroupList().size() * 1;
      int var35 = var33 + var34;
      int var36 = 0;

      int var38;
      for(Iterator var37 = this.getEnabledChannelList().iterator(); var37.hasNext(); var36 += var38) {
         var38 = CodedOutputStreamMicro.computeInt32SizeNoTag(((Integer)var37.next()).intValue());
      }

      int var39 = var35 + var36;
      int var40 = this.getEnabledChannelList().size() * 1;
      int var41 = var39 + var40;
      if(this.hasDeviceFeErrorPronePathPrefix()) {
         String var42 = this.getDeviceFeErrorPronePathPrefix();
         int var43 = CodedOutputStreamMicro.computeStringSize(9, var42);
         var41 += var43;
      }

      if(this.hasDeviceFeErrorPronePercentage()) {
         int var44 = this.getDeviceFeErrorPronePercentage();
         int var45 = CodedOutputStreamMicro.computeInt32Size(10, var44);
         var41 += var45;
      }

      this.cachedSize = var41;
      return var41;
   }

   public boolean getTestKeysMandatoryUpdateCheck() {
      return this.testKeysMandatoryUpdateCheck_;
   }

   public String getWhitelistGroup(int var1) {
      return (String)this.whitelistGroup_.get(var1);
   }

   public int getWhitelistGroupCount() {
      return this.whitelistGroup_.size();
   }

   public List<String> getWhitelistGroupList() {
      return this.whitelistGroup_;
   }

   public boolean hasDeviceFeErrorPronePathPrefix() {
      return this.hasDeviceFeErrorPronePathPrefix;
   }

   public boolean hasDeviceFeErrorPronePercentage() {
      return this.hasDeviceFeErrorPronePercentage;
   }

   public boolean hasMinSupportedClientVersion() {
      return this.hasMinSupportedClientVersion;
   }

   public boolean hasTestKeysMandatoryUpdateCheck() {
      return this.hasTestKeysMandatoryUpdateCheck;
   }

   public final boolean isInitialized() {
      return true;
   }

   public DfeConfigProto mergeFrom(CodedInputStreamMicro var1) throws IOException {
      while(true) {
         int var2 = var1.readTag();
         switch(var2) {
         case 8:
            int var3 = var1.readInt32();
            this.setMinSupportedClientVersion(var3);
            break;
         case 16:
            boolean var5 = var1.readBool();
            this.setTestKeysMandatoryUpdateCheck(var5);
            break;
         case 26:
            String var7 = var1.readString();
            this.addClientVersionCheckGroup(var7);
            break;
         case 32:
            int var9 = var1.readInt32();
            this.addCategoryNavigableChannel(var9);
            break;
         case 42:
            String var11 = var1.readString();
            this.addBlacklistedContainerId(var11);
            break;
         case 50:
            String var13 = var1.readString();
            this.addWhitelistGroup(var13);
            break;
         case 58:
            String var15 = var1.readString();
            this.addAllChannelGroup(var15);
            break;
         case 64:
            int var17 = var1.readInt32();
            this.addEnabledChannel(var17);
            break;
         case 74:
            String var19 = var1.readString();
            this.setDeviceFeErrorPronePathPrefix(var19);
            break;
         case 80:
            int var21 = var1.readInt32();
            this.setDeviceFeErrorPronePercentage(var21);
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

   public DfeConfigProto setAllChannelGroup(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.allChannelGroup_.set(var1, var2);
         return this;
      }
   }

   public DfeConfigProto setBlacklistedContainerId(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.blacklistedContainerId_.set(var1, var2);
         return this;
      }
   }

   public DfeConfigProto setCategoryNavigableChannel(int var1, int var2) {
      List var3 = this.categoryNavigableChannel_;
      Integer var4 = Integer.valueOf(var2);
      var3.set(var1, var4);
      return this;
   }

   public DfeConfigProto setClientVersionCheckGroup(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.clientVersionCheckGroup_.set(var1, var2);
         return this;
      }
   }

   public DfeConfigProto setDeviceFeErrorPronePathPrefix(String var1) {
      this.hasDeviceFeErrorPronePathPrefix = (boolean)1;
      this.deviceFeErrorPronePathPrefix_ = var1;
      return this;
   }

   public DfeConfigProto setDeviceFeErrorPronePercentage(int var1) {
      this.hasDeviceFeErrorPronePercentage = (boolean)1;
      this.deviceFeErrorPronePercentage_ = var1;
      return this;
   }

   public DfeConfigProto setEnabledChannel(int var1, int var2) {
      List var3 = this.enabledChannel_;
      Integer var4 = Integer.valueOf(var2);
      var3.set(var1, var4);
      return this;
   }

   public DfeConfigProto setMinSupportedClientVersion(int var1) {
      this.hasMinSupportedClientVersion = (boolean)1;
      this.minSupportedClientVersion_ = var1;
      return this;
   }

   public DfeConfigProto setTestKeysMandatoryUpdateCheck(boolean var1) {
      this.hasTestKeysMandatoryUpdateCheck = (boolean)1;
      this.testKeysMandatoryUpdateCheck_ = var1;
      return this;
   }

   public DfeConfigProto setWhitelistGroup(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.whitelistGroup_.set(var1, var2);
         return this;
      }
   }

   public void writeTo(CodedOutputStreamMicro var1) throws IOException {
      if(this.hasMinSupportedClientVersion()) {
         int var2 = this.getMinSupportedClientVersion();
         var1.writeInt32(1, var2);
      }

      if(this.hasTestKeysMandatoryUpdateCheck()) {
         boolean var3 = this.getTestKeysMandatoryUpdateCheck();
         var1.writeBool(2, var3);
      }

      Iterator var4 = this.getClientVersionCheckGroupList().iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         var1.writeString(3, var5);
      }

      Iterator var6 = this.getCategoryNavigableChannelList().iterator();

      while(var6.hasNext()) {
         int var7 = ((Integer)var6.next()).intValue();
         var1.writeInt32(4, var7);
      }

      Iterator var8 = this.getBlacklistedContainerIdList().iterator();

      while(var8.hasNext()) {
         String var9 = (String)var8.next();
         var1.writeString(5, var9);
      }

      Iterator var10 = this.getWhitelistGroupList().iterator();

      while(var10.hasNext()) {
         String var11 = (String)var10.next();
         var1.writeString(6, var11);
      }

      Iterator var12 = this.getAllChannelGroupList().iterator();

      while(var12.hasNext()) {
         String var13 = (String)var12.next();
         var1.writeString(7, var13);
      }

      Iterator var14 = this.getEnabledChannelList().iterator();

      while(var14.hasNext()) {
         int var15 = ((Integer)var14.next()).intValue();
         var1.writeInt32(8, var15);
      }

      if(this.hasDeviceFeErrorPronePathPrefix()) {
         String var16 = this.getDeviceFeErrorPronePathPrefix();
         var1.writeString(9, var16);
      }

      if(this.hasDeviceFeErrorPronePercentage()) {
         int var17 = this.getDeviceFeErrorPronePercentage();
         var1.writeInt32(10, var17);
      }
   }
}
