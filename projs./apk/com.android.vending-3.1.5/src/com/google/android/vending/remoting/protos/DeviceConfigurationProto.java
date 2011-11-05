package com.google.android.vending.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class DeviceConfigurationProto extends MessageMicro {

   public static final int DEVICE_CLASS_FIELD_NUMBER = 16;
   public static final int DPAD = 2;
   public static final int EXTRA_LARGE = 4;
   public static final int FINGER = 3;
   public static final int GL_ES_VERSION_FIELD_NUMBER = 8;
   public static final int GL_EXTENSION_FIELD_NUMBER = 15;
   public static final int HAS_FIVE_WAY_NAVIGATION_FIELD_NUMBER = 6;
   public static final int HAS_HARD_KEYBOARD_FIELD_NUMBER = 5;
   public static final int KEYBOARD_FIELD_NUMBER = 2;
   public static final int LARGE = 3;
   public static final int MAX_APK_DOWNLOAD_SIZE_MB_FIELD_NUMBER = 17;
   public static final int NATIVE_PLATFORM_FIELD_NUMBER = 11;
   public static final int NAVIGATION_FIELD_NUMBER = 3;
   public static final int NOKEYS = 1;
   public static final int NONAV = 1;
   public static final int NORMAL = 2;
   public static final int NOTOUCH = 1;
   public static final int PHONE = 1;
   public static final int QWERTY = 2;
   public static final int SCREEN_DENSITY_FIELD_NUMBER = 7;
   public static final int SCREEN_HEIGHT_FIELD_NUMBER = 13;
   public static final int SCREEN_LAYOUT_FIELD_NUMBER = 4;
   public static final int SCREEN_WIDTH_FIELD_NUMBER = 12;
   public static final int SMALL = 1;
   public static final int STYLUS = 2;
   public static final int SYSTEM_AVAILABLE_FEATURE_FIELD_NUMBER = 10;
   public static final int SYSTEM_SHARED_LIBRARY_FIELD_NUMBER = 9;
   public static final int SYSTEM_SUPPORTED_LOCALE_FIELD_NUMBER = 14;
   public static final int TABLET = 2;
   public static final int TEAPOT = 4;
   public static final int TOUCH_SCREEN_FIELD_NUMBER = 1;
   public static final int TRACKBALL = 3;
   public static final int TV = 3;
   public static final int TWELVE_KEY = 3;
   public static final int UNDEFINED_KEYBOARD = 0;
   public static final int UNDEFINED_NAVIGATION = 0;
   public static final int UNDEFINED_SCREEN_LAYOUT = 0;
   public static final int UNDEFINED_TOUCH_SCREEN = 0;
   public static final int UNKNOWN = 0;
   public static final int WHEEL = 4;
   private int cachedSize;
   private int deviceClass_;
   private int glEsVersion_ = 0;
   private List<String> glExtension_;
   private boolean hasDeviceClass;
   private boolean hasFiveWayNavigation_ = 0;
   private boolean hasGlEsVersion;
   private boolean hasHardKeyboard_ = 0;
   private boolean hasHasFiveWayNavigation;
   private boolean hasHasHardKeyboard;
   private boolean hasKeyboard;
   private boolean hasMaxApkDownloadSizeMb;
   private boolean hasNavigation;
   private boolean hasScreenDensity;
   private boolean hasScreenHeight;
   private boolean hasScreenLayout;
   private boolean hasScreenWidth;
   private boolean hasTouchScreen;
   private int keyboard_ = 0;
   private int maxApkDownloadSizeMb_;
   private List<String> nativePlatform_;
   private int navigation_ = 0;
   private int screenDensity_ = 0;
   private int screenHeight_ = 0;
   private int screenLayout_ = 0;
   private int screenWidth_ = 0;
   private List<String> systemAvailableFeature_;
   private List<String> systemSharedLibrary_;
   private List<String> systemSupportedLocale_;
   private int touchScreen_ = 0;


   public DeviceConfigurationProto() {
      List var1 = Collections.emptyList();
      this.systemSharedLibrary_ = var1;
      List var2 = Collections.emptyList();
      this.systemAvailableFeature_ = var2;
      List var3 = Collections.emptyList();
      this.nativePlatform_ = var3;
      List var4 = Collections.emptyList();
      this.systemSupportedLocale_ = var4;
      List var5 = Collections.emptyList();
      this.glExtension_ = var5;
      this.deviceClass_ = 1;
      this.maxApkDownloadSizeMb_ = 50;
      this.cachedSize = -1;
   }

   public static DeviceConfigurationProto parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new DeviceConfigurationProto()).mergeFrom(var0);
   }

   public static DeviceConfigurationProto parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (DeviceConfigurationProto)((DeviceConfigurationProto)(new DeviceConfigurationProto()).mergeFrom(var0));
   }

   public DeviceConfigurationProto addGlExtension(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.glExtension_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.glExtension_ = var2;
         }

         this.glExtension_.add(var1);
         return this;
      }
   }

   public DeviceConfigurationProto addNativePlatform(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.nativePlatform_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.nativePlatform_ = var2;
         }

         this.nativePlatform_.add(var1);
         return this;
      }
   }

   public DeviceConfigurationProto addSystemAvailableFeature(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.systemAvailableFeature_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.systemAvailableFeature_ = var2;
         }

         this.systemAvailableFeature_.add(var1);
         return this;
      }
   }

   public DeviceConfigurationProto addSystemSharedLibrary(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.systemSharedLibrary_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.systemSharedLibrary_ = var2;
         }

         this.systemSharedLibrary_.add(var1);
         return this;
      }
   }

   public DeviceConfigurationProto addSystemSupportedLocale(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.systemSupportedLocale_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.systemSupportedLocale_ = var2;
         }

         this.systemSupportedLocale_.add(var1);
         return this;
      }
   }

   public final DeviceConfigurationProto clear() {
      DeviceConfigurationProto var1 = this.clearTouchScreen();
      DeviceConfigurationProto var2 = this.clearKeyboard();
      DeviceConfigurationProto var3 = this.clearNavigation();
      DeviceConfigurationProto var4 = this.clearScreenLayout();
      DeviceConfigurationProto var5 = this.clearHasHardKeyboard();
      DeviceConfigurationProto var6 = this.clearHasFiveWayNavigation();
      DeviceConfigurationProto var7 = this.clearScreenDensity();
      DeviceConfigurationProto var8 = this.clearScreenWidth();
      DeviceConfigurationProto var9 = this.clearScreenHeight();
      DeviceConfigurationProto var10 = this.clearGlEsVersion();
      DeviceConfigurationProto var11 = this.clearSystemSharedLibrary();
      DeviceConfigurationProto var12 = this.clearSystemAvailableFeature();
      DeviceConfigurationProto var13 = this.clearNativePlatform();
      DeviceConfigurationProto var14 = this.clearSystemSupportedLocale();
      DeviceConfigurationProto var15 = this.clearGlExtension();
      DeviceConfigurationProto var16 = this.clearDeviceClass();
      DeviceConfigurationProto var17 = this.clearMaxApkDownloadSizeMb();
      this.cachedSize = -1;
      return this;
   }

   public DeviceConfigurationProto clearDeviceClass() {
      this.hasDeviceClass = (boolean)0;
      this.deviceClass_ = 1;
      return this;
   }

   public DeviceConfigurationProto clearGlEsVersion() {
      this.hasGlEsVersion = (boolean)0;
      this.glEsVersion_ = 0;
      return this;
   }

   public DeviceConfigurationProto clearGlExtension() {
      List var1 = Collections.emptyList();
      this.glExtension_ = var1;
      return this;
   }

   public DeviceConfigurationProto clearHasFiveWayNavigation() {
      this.hasHasFiveWayNavigation = (boolean)0;
      this.hasFiveWayNavigation_ = (boolean)0;
      return this;
   }

   public DeviceConfigurationProto clearHasHardKeyboard() {
      this.hasHasHardKeyboard = (boolean)0;
      this.hasHardKeyboard_ = (boolean)0;
      return this;
   }

   public DeviceConfigurationProto clearKeyboard() {
      this.hasKeyboard = (boolean)0;
      this.keyboard_ = 0;
      return this;
   }

   public DeviceConfigurationProto clearMaxApkDownloadSizeMb() {
      this.hasMaxApkDownloadSizeMb = (boolean)0;
      this.maxApkDownloadSizeMb_ = 50;
      return this;
   }

   public DeviceConfigurationProto clearNativePlatform() {
      List var1 = Collections.emptyList();
      this.nativePlatform_ = var1;
      return this;
   }

   public DeviceConfigurationProto clearNavigation() {
      this.hasNavigation = (boolean)0;
      this.navigation_ = 0;
      return this;
   }

   public DeviceConfigurationProto clearScreenDensity() {
      this.hasScreenDensity = (boolean)0;
      this.screenDensity_ = 0;
      return this;
   }

   public DeviceConfigurationProto clearScreenHeight() {
      this.hasScreenHeight = (boolean)0;
      this.screenHeight_ = 0;
      return this;
   }

   public DeviceConfigurationProto clearScreenLayout() {
      this.hasScreenLayout = (boolean)0;
      this.screenLayout_ = 0;
      return this;
   }

   public DeviceConfigurationProto clearScreenWidth() {
      this.hasScreenWidth = (boolean)0;
      this.screenWidth_ = 0;
      return this;
   }

   public DeviceConfigurationProto clearSystemAvailableFeature() {
      List var1 = Collections.emptyList();
      this.systemAvailableFeature_ = var1;
      return this;
   }

   public DeviceConfigurationProto clearSystemSharedLibrary() {
      List var1 = Collections.emptyList();
      this.systemSharedLibrary_ = var1;
      return this;
   }

   public DeviceConfigurationProto clearSystemSupportedLocale() {
      List var1 = Collections.emptyList();
      this.systemSupportedLocale_ = var1;
      return this;
   }

   public DeviceConfigurationProto clearTouchScreen() {
      this.hasTouchScreen = (boolean)0;
      this.touchScreen_ = 0;
      return this;
   }

   public int getCachedSize() {
      if(this.cachedSize < 0) {
         int var1 = this.getSerializedSize();
      }

      return this.cachedSize;
   }

   public int getDeviceClass() {
      return this.deviceClass_;
   }

   public int getGlEsVersion() {
      return this.glEsVersion_;
   }

   public String getGlExtension(int var1) {
      return (String)this.glExtension_.get(var1);
   }

   public int getGlExtensionCount() {
      return this.glExtension_.size();
   }

   public List<String> getGlExtensionList() {
      return this.glExtension_;
   }

   public boolean getHasFiveWayNavigation() {
      return this.hasFiveWayNavigation_;
   }

   public boolean getHasHardKeyboard() {
      return this.hasHardKeyboard_;
   }

   public int getKeyboard() {
      return this.keyboard_;
   }

   public int getMaxApkDownloadSizeMb() {
      return this.maxApkDownloadSizeMb_;
   }

   public String getNativePlatform(int var1) {
      return (String)this.nativePlatform_.get(var1);
   }

   public int getNativePlatformCount() {
      return this.nativePlatform_.size();
   }

   public List<String> getNativePlatformList() {
      return this.nativePlatform_;
   }

   public int getNavigation() {
      return this.navigation_;
   }

   public int getScreenDensity() {
      return this.screenDensity_;
   }

   public int getScreenHeight() {
      return this.screenHeight_;
   }

   public int getScreenLayout() {
      return this.screenLayout_;
   }

   public int getScreenWidth() {
      return this.screenWidth_;
   }

   public int getSerializedSize() {
      int var1 = 0;
      if(this.hasTouchScreen()) {
         int var2 = this.getTouchScreen();
         int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
         var1 = 0 + var3;
      }

      if(this.hasKeyboard()) {
         int var4 = this.getKeyboard();
         int var5 = CodedOutputStreamMicro.computeInt32Size(2, var4);
         var1 += var5;
      }

      if(this.hasNavigation()) {
         int var6 = this.getNavigation();
         int var7 = CodedOutputStreamMicro.computeInt32Size(3, var6);
         var1 += var7;
      }

      if(this.hasScreenLayout()) {
         int var8 = this.getScreenLayout();
         int var9 = CodedOutputStreamMicro.computeInt32Size(4, var8);
         var1 += var9;
      }

      if(this.hasHasHardKeyboard()) {
         boolean var10 = this.getHasHardKeyboard();
         int var11 = CodedOutputStreamMicro.computeBoolSize(5, var10);
         var1 += var11;
      }

      if(this.hasHasFiveWayNavigation()) {
         boolean var12 = this.getHasFiveWayNavigation();
         int var13 = CodedOutputStreamMicro.computeBoolSize(6, var12);
         var1 += var13;
      }

      if(this.hasScreenDensity()) {
         int var14 = this.getScreenDensity();
         int var15 = CodedOutputStreamMicro.computeInt32Size(7, var14);
         var1 += var15;
      }

      if(this.hasGlEsVersion()) {
         int var16 = this.getGlEsVersion();
         int var17 = CodedOutputStreamMicro.computeInt32Size(8, var16);
         var1 += var17;
      }

      int var18 = 0;

      int var20;
      for(Iterator var19 = this.getSystemSharedLibraryList().iterator(); var19.hasNext(); var18 += var20) {
         var20 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var19.next());
      }

      int var21 = var1 + var18;
      int var22 = this.getSystemSharedLibraryList().size() * 1;
      int var23 = var21 + var22;
      int var24 = 0;

      int var26;
      for(Iterator var25 = this.getSystemAvailableFeatureList().iterator(); var25.hasNext(); var24 += var26) {
         var26 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var25.next());
      }

      int var27 = var23 + var24;
      int var28 = this.getSystemAvailableFeatureList().size() * 1;
      int var29 = var27 + var28;
      int var30 = 0;

      int var32;
      for(Iterator var31 = this.getNativePlatformList().iterator(); var31.hasNext(); var30 += var32) {
         var32 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var31.next());
      }

      int var33 = var29 + var30;
      int var34 = this.getNativePlatformList().size() * 1;
      int var35 = var33 + var34;
      if(this.hasScreenWidth()) {
         int var36 = this.getScreenWidth();
         int var37 = CodedOutputStreamMicro.computeInt32Size(12, var36);
         var35 += var37;
      }

      if(this.hasScreenHeight()) {
         int var38 = this.getScreenHeight();
         int var39 = CodedOutputStreamMicro.computeInt32Size(13, var38);
         var35 += var39;
      }

      int var40 = 0;

      int var42;
      for(Iterator var41 = this.getSystemSupportedLocaleList().iterator(); var41.hasNext(); var40 += var42) {
         var42 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var41.next());
      }

      int var43 = var35 + var40;
      int var44 = this.getSystemSupportedLocaleList().size() * 1;
      int var45 = var43 + var44;
      int var46 = 0;

      int var48;
      for(Iterator var47 = this.getGlExtensionList().iterator(); var47.hasNext(); var46 += var48) {
         var48 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var47.next());
      }

      int var49 = var45 + var46;
      int var50 = this.getGlExtensionList().size() * 1;
      int var51 = var49 + var50;
      if(this.hasDeviceClass()) {
         int var52 = this.getDeviceClass();
         int var53 = CodedOutputStreamMicro.computeInt32Size(16, var52);
         var51 += var53;
      }

      if(this.hasMaxApkDownloadSizeMb()) {
         int var54 = this.getMaxApkDownloadSizeMb();
         int var55 = CodedOutputStreamMicro.computeInt32Size(17, var54);
         var51 += var55;
      }

      this.cachedSize = var51;
      return var51;
   }

   public String getSystemAvailableFeature(int var1) {
      return (String)this.systemAvailableFeature_.get(var1);
   }

   public int getSystemAvailableFeatureCount() {
      return this.systemAvailableFeature_.size();
   }

   public List<String> getSystemAvailableFeatureList() {
      return this.systemAvailableFeature_;
   }

   public String getSystemSharedLibrary(int var1) {
      return (String)this.systemSharedLibrary_.get(var1);
   }

   public int getSystemSharedLibraryCount() {
      return this.systemSharedLibrary_.size();
   }

   public List<String> getSystemSharedLibraryList() {
      return this.systemSharedLibrary_;
   }

   public String getSystemSupportedLocale(int var1) {
      return (String)this.systemSupportedLocale_.get(var1);
   }

   public int getSystemSupportedLocaleCount() {
      return this.systemSupportedLocale_.size();
   }

   public List<String> getSystemSupportedLocaleList() {
      return this.systemSupportedLocale_;
   }

   public int getTouchScreen() {
      return this.touchScreen_;
   }

   public boolean hasDeviceClass() {
      return this.hasDeviceClass;
   }

   public boolean hasGlEsVersion() {
      return this.hasGlEsVersion;
   }

   public boolean hasHasFiveWayNavigation() {
      return this.hasHasFiveWayNavigation;
   }

   public boolean hasHasHardKeyboard() {
      return this.hasHasHardKeyboard;
   }

   public boolean hasKeyboard() {
      return this.hasKeyboard;
   }

   public boolean hasMaxApkDownloadSizeMb() {
      return this.hasMaxApkDownloadSizeMb;
   }

   public boolean hasNavigation() {
      return this.hasNavigation;
   }

   public boolean hasScreenDensity() {
      return this.hasScreenDensity;
   }

   public boolean hasScreenHeight() {
      return this.hasScreenHeight;
   }

   public boolean hasScreenLayout() {
      return this.hasScreenLayout;
   }

   public boolean hasScreenWidth() {
      return this.hasScreenWidth;
   }

   public boolean hasTouchScreen() {
      return this.hasTouchScreen;
   }

   public final boolean isInitialized() {
      boolean var1 = false;
      if(this.hasTouchScreen && this.hasKeyboard && this.hasNavigation && this.hasScreenLayout && this.hasHasHardKeyboard && this.hasHasFiveWayNavigation && this.hasScreenDensity && this.hasGlEsVersion) {
         var1 = true;
      }

      return var1;
   }

   public DeviceConfigurationProto mergeFrom(CodedInputStreamMicro var1) throws IOException {
      while(true) {
         int var2 = var1.readTag();
         switch(var2) {
         case 8:
            int var3 = var1.readInt32();
            this.setTouchScreen(var3);
            break;
         case 16:
            int var5 = var1.readInt32();
            this.setKeyboard(var5);
            break;
         case 24:
            int var7 = var1.readInt32();
            this.setNavigation(var7);
            break;
         case 32:
            int var9 = var1.readInt32();
            this.setScreenLayout(var9);
            break;
         case 40:
            boolean var11 = var1.readBool();
            this.setHasHardKeyboard(var11);
            break;
         case 48:
            boolean var13 = var1.readBool();
            this.setHasFiveWayNavigation(var13);
            break;
         case 56:
            int var15 = var1.readInt32();
            this.setScreenDensity(var15);
            break;
         case 64:
            int var17 = var1.readInt32();
            this.setGlEsVersion(var17);
            break;
         case 74:
            String var19 = var1.readString();
            this.addSystemSharedLibrary(var19);
            break;
         case 82:
            String var21 = var1.readString();
            this.addSystemAvailableFeature(var21);
            break;
         case 90:
            String var23 = var1.readString();
            this.addNativePlatform(var23);
            break;
         case 96:
            int var25 = var1.readInt32();
            this.setScreenWidth(var25);
            break;
         case 104:
            int var27 = var1.readInt32();
            this.setScreenHeight(var27);
            break;
         case 114:
            String var29 = var1.readString();
            this.addSystemSupportedLocale(var29);
            break;
         case 122:
            String var31 = var1.readString();
            this.addGlExtension(var31);
            break;
         case 128:
            int var33 = var1.readInt32();
            this.setDeviceClass(var33);
            break;
         case 136:
            int var35 = var1.readInt32();
            this.setMaxApkDownloadSizeMb(var35);
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

   public DeviceConfigurationProto setDeviceClass(int var1) {
      this.hasDeviceClass = (boolean)1;
      this.deviceClass_ = var1;
      return this;
   }

   public DeviceConfigurationProto setGlEsVersion(int var1) {
      this.hasGlEsVersion = (boolean)1;
      this.glEsVersion_ = var1;
      return this;
   }

   public DeviceConfigurationProto setGlExtension(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.glExtension_.set(var1, var2);
         return this;
      }
   }

   public DeviceConfigurationProto setHasFiveWayNavigation(boolean var1) {
      this.hasHasFiveWayNavigation = (boolean)1;
      this.hasFiveWayNavigation_ = var1;
      return this;
   }

   public DeviceConfigurationProto setHasHardKeyboard(boolean var1) {
      this.hasHasHardKeyboard = (boolean)1;
      this.hasHardKeyboard_ = var1;
      return this;
   }

   public DeviceConfigurationProto setKeyboard(int var1) {
      this.hasKeyboard = (boolean)1;
      this.keyboard_ = var1;
      return this;
   }

   public DeviceConfigurationProto setMaxApkDownloadSizeMb(int var1) {
      this.hasMaxApkDownloadSizeMb = (boolean)1;
      this.maxApkDownloadSizeMb_ = var1;
      return this;
   }

   public DeviceConfigurationProto setNativePlatform(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.nativePlatform_.set(var1, var2);
         return this;
      }
   }

   public DeviceConfigurationProto setNavigation(int var1) {
      this.hasNavigation = (boolean)1;
      this.navigation_ = var1;
      return this;
   }

   public DeviceConfigurationProto setScreenDensity(int var1) {
      this.hasScreenDensity = (boolean)1;
      this.screenDensity_ = var1;
      return this;
   }

   public DeviceConfigurationProto setScreenHeight(int var1) {
      this.hasScreenHeight = (boolean)1;
      this.screenHeight_ = var1;
      return this;
   }

   public DeviceConfigurationProto setScreenLayout(int var1) {
      this.hasScreenLayout = (boolean)1;
      this.screenLayout_ = var1;
      return this;
   }

   public DeviceConfigurationProto setScreenWidth(int var1) {
      this.hasScreenWidth = (boolean)1;
      this.screenWidth_ = var1;
      return this;
   }

   public DeviceConfigurationProto setSystemAvailableFeature(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.systemAvailableFeature_.set(var1, var2);
         return this;
      }
   }

   public DeviceConfigurationProto setSystemSharedLibrary(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.systemSharedLibrary_.set(var1, var2);
         return this;
      }
   }

   public DeviceConfigurationProto setSystemSupportedLocale(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.systemSupportedLocale_.set(var1, var2);
         return this;
      }
   }

   public DeviceConfigurationProto setTouchScreen(int var1) {
      this.hasTouchScreen = (boolean)1;
      this.touchScreen_ = var1;
      return this;
   }

   public void writeTo(CodedOutputStreamMicro var1) throws IOException {
      if(this.hasTouchScreen()) {
         int var2 = this.getTouchScreen();
         var1.writeInt32(1, var2);
      }

      if(this.hasKeyboard()) {
         int var3 = this.getKeyboard();
         var1.writeInt32(2, var3);
      }

      if(this.hasNavigation()) {
         int var4 = this.getNavigation();
         var1.writeInt32(3, var4);
      }

      if(this.hasScreenLayout()) {
         int var5 = this.getScreenLayout();
         var1.writeInt32(4, var5);
      }

      if(this.hasHasHardKeyboard()) {
         boolean var6 = this.getHasHardKeyboard();
         var1.writeBool(5, var6);
      }

      if(this.hasHasFiveWayNavigation()) {
         boolean var7 = this.getHasFiveWayNavigation();
         var1.writeBool(6, var7);
      }

      if(this.hasScreenDensity()) {
         int var8 = this.getScreenDensity();
         var1.writeInt32(7, var8);
      }

      if(this.hasGlEsVersion()) {
         int var9 = this.getGlEsVersion();
         var1.writeInt32(8, var9);
      }

      Iterator var10 = this.getSystemSharedLibraryList().iterator();

      while(var10.hasNext()) {
         String var11 = (String)var10.next();
         var1.writeString(9, var11);
      }

      Iterator var12 = this.getSystemAvailableFeatureList().iterator();

      while(var12.hasNext()) {
         String var13 = (String)var12.next();
         var1.writeString(10, var13);
      }

      Iterator var14 = this.getNativePlatformList().iterator();

      while(var14.hasNext()) {
         String var15 = (String)var14.next();
         var1.writeString(11, var15);
      }

      if(this.hasScreenWidth()) {
         int var16 = this.getScreenWidth();
         var1.writeInt32(12, var16);
      }

      if(this.hasScreenHeight()) {
         int var17 = this.getScreenHeight();
         var1.writeInt32(13, var17);
      }

      Iterator var18 = this.getSystemSupportedLocaleList().iterator();

      while(var18.hasNext()) {
         String var19 = (String)var18.next();
         var1.writeString(14, var19);
      }

      Iterator var20 = this.getGlExtensionList().iterator();

      while(var20.hasNext()) {
         String var21 = (String)var20.next();
         var1.writeString(15, var21);
      }

      if(this.hasDeviceClass()) {
         int var22 = this.getDeviceClass();
         var1.writeInt32(16, var22);
      }

      if(this.hasMaxApkDownloadSizeMb()) {
         int var23 = this.getMaxApkDownloadSizeMb();
         var1.writeInt32(17, var23);
      }
   }
}
