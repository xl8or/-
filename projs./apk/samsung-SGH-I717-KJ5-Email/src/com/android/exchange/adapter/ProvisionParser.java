package com.android.exchange.adapter;

import android.util.Log;
import com.android.email.SecurityPolicy;
import com.android.exchange.Eas;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.Parser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ProvisionParser extends Parser {

   boolean mIsSupportable = 1;
   String mPolicyKey = "0";
   SecurityPolicy.PolicySet mPolicySet = null;
   int mPolicyStatus = -1;
   boolean mRemoteWipe = 0;
   private EasSyncService mService;


   public ProvisionParser(InputStream var1, EasSyncService var2) throws IOException {
      super(var1);
      this.mService = var2;
   }

   private void parseProvisionDocWbxml() throws IOException {
      int var1 = 0;
      byte var2 = 0;
      int var3 = 0;
      int var4 = 0;
      byte var5 = 0;
      byte var6 = 0;
      int var7 = 0;
      int var8 = 0;
      byte var9 = 1;
      int var10 = 0;
      byte var11 = 1;
      byte var12 = 1;
      byte var13 = 1;
      byte var14 = 1;
      byte var15 = 1;
      byte var16 = 1;
      byte var17 = 1;
      byte var18 = 1;
      byte var19 = 0;
      int var20 = 2;
      int var21 = 0;
      int var22 = 0;
      int var23 = 0;
      int var24 = 0;
      int var25 = 0;
      byte var26 = 0;
      byte var27 = 0;
      int var28 = '\uffff';
      int var29 = '\uffff';
      int var30 = '\uffff';
      byte var31 = 1;
      byte var32 = 1;
      byte var33 = 1;
      byte var34 = 0;
      byte var35 = 0;

      while(true) {
         short var37 = 909;
         if(this.nextTag(var37) == 3) {
            if(false) {
               return;
            }

            EasSyncService var47 = this.mService;
            String[] var48 = new String[]{"ProvisionParser", null};
            StringBuilder var49 = (new StringBuilder()).append("PasswordEnabled = ");
            String var51 = var49.append((boolean)var5).toString();
            var48[1] = var51;
            var47.userLog(var48);
            SecurityPolicy.PolicySet var52 = new SecurityPolicy.PolicySet(var1, var2, var3, var4, (boolean)1, (boolean)var6, var7, var8, (boolean)var9, var10, (boolean)var11, (boolean)var12, (boolean)var13, (boolean)var14, (boolean)var15, (boolean)var16, (boolean)var17, (boolean)var18, (boolean)var19, var20, var21, var22, var23, var24, var25, (boolean)var26, (boolean)var27, var28, var29, var30, (boolean)var31, (boolean)var32, (boolean)var33, (boolean)var34, (boolean)var35);
            this.mPolicySet = var52;
            return;
         }

         switch(this.tag) {
         case 910:
            if(this.getValueInt() == 1) {
               var5 = 1;
               if(var2 == 0) {
                  var2 = 1;
               }
            }
            break;
         case 911:
            if(this.getValueInt() == 1 && var5 != 0) {
               var2 = 4;
            }
            break;
         case 912:
            if(this.hasContent() && var5 != 0) {
               if(this.getValueInt() == 1) {
                  var35 = 1;
               }
               break;
            }

            this.skipTag();
            break;
         case 913:
            if(this.getValueInt() == 1 && var5 != 0) {
               var6 = 1;
            }
            break;
         case 914:
         case 949:
         case 952:
         default:
            this.skipTag();
            break;
         case 915:
            if(this.getValueInt() == 0) {
               var9 = 0;
            }
            break;
         case 916:
            if(var5 != 0 && this.hasContent()) {
               var1 = this.getValueInt();
               break;
            }

            this.skipTag();
            break;
         case 917:
            if(var5 != 0 && this.hasContent()) {
               var4 = this.getValueInt();
               break;
            }

            this.skipTag();
            break;
         case 918:
            if(var5 != 0 && this.hasContent()) {
               var3 = this.getValueInt();
               break;
            }

            this.skipTag();
            break;
         case 919:
            if(var9 != 0 && this.hasContent()) {
               var10 = this.getValueInt();
               break;
            }

            this.skipTag();
            break;
         case 920:
            String var38 = this.getValue();
            break;
         case 921:
            if(var5 != 0 && this.hasContent()) {
               var7 = this.getValueInt();
               if(var7 < 0) {
                  var7 = 0;
               }
               break;
            }

            this.skipTag();
            break;
         case 922:
            if(var5 != 0 && this.hasContent()) {
               var8 = this.getValueInt();
               if(var8 < 0) {
                  var8 = 0;
               }
               break;
            }

            this.skipTag();
            break;
         case 923:
            if(this.getValueInt() == 0) {
               var11 = 0;
            }
            break;
         case 924:
            if(this.getValueInt() == 0) {
               var12 = 0;
            }
            break;
         case 925:
            if(this.hasContent() && var5 != 0) {
               if(this.getValueInt() == 1) {
                  var34 = 1;
               }
               break;
            }

            this.skipTag();
            break;
         case 926:
            if(this.getValueInt() == 0) {
               if(Eas.USER_LOG) {
                  int var39 = Log.e("Parser", "AllowUnsignedApplications is not supported by device");
               }

               byte var40 = 0;
               this.mIsSupportable = (boolean)var40;
            }
            break;
         case 927:
            if(this.getValueInt() == 0) {
               if(Eas.USER_LOG) {
                  int var41 = Log.e("Parser", "AllowUnsignedInstallationPackages is not supported by device");
               }

               byte var42 = 0;
               this.mIsSupportable = (boolean)var42;
            }
            break;
         case 928:
            if(var5 != 0 && this.hasContent()) {
               var21 = this.getValueInt();
               break;
            }

            this.skipTag();
            break;
         case 929:
            if(this.getValueInt() == 0) {
               var13 = 0;
            }
            break;
         case 930:
            if(this.getValueInt() == 0) {
               var14 = 0;
            }
            break;
         case 931:
         case 948:
            if(this.getValueInt() == 0) {
               var15 = 0;
            }
            break;
         case 932:
            var20 = this.getValueInt();
            break;
         case 933:
            if(this.getValueInt() == 0) {
               var33 = 0;
            }
            break;
         case 934:
            if(this.getValueInt() == 1) {
               var19 = 1;
            }
            break;
         case 935:
            if(this.getValueInt() == 0) {
               var32 = 0;
            }
            break;
         case 936:
            var22 = this.getValueInt();
            break;
         case 937:
            if(this.getValueInt() == 0) {
               var16 = 0;
            }
            break;
         case 938:
            var23 = this.getValueInt();
            break;
         case 939:
            var24 = this.getValueInt();
            if(var24 < 0) {
               var24 = 0;
            }
            break;
         case 940:
            var25 = this.getValueInt();
            if(var25 < 0) {
               var25 = 0;
            }
            break;
         case 941:
            if(this.getValueInt() == 1) {
               var26 = 1;
            }
            break;
         case 942:
            if(this.getValueInt() == 1) {
               var27 = 1;
            }
            break;
         case 943:
            if(this.hasContent()) {
               var28 = this.getValueInt();
            }
            break;
         case 944:
            if(this.hasContent()) {
               var29 = this.getValueInt();
            }
            break;
         case 945:
            if(this.hasContent()) {
               var30 = this.getValueInt();
            }
            break;
         case 946:
            if(this.getValueInt() == 0) {
               var31 = 0;
            }
            break;
         case 947:
            if(this.getValueInt() == 0) {
               var17 = 0;
            }
            break;
         case 950:
            if(this.getValueInt() == 0) {
               var18 = 0;
            }
            break;
         case 951:
            if(this.hasContent()) {
               if(Eas.USER_LOG) {
                  int var43 = Log.e("Parser", "UnapprovedInRomApplicationList is not supported by device");
               }

               byte var44 = 0;
               this.mIsSupportable = (boolean)var44;
            }

            this.skipTag();
            break;
         case 953:
            if(this.hasContent()) {
               if(Eas.USER_LOG) {
                  int var45 = Log.e("Parser", "ApprovedApplicationList is not supported by device");
               }

               byte var46 = 0;
               this.mIsSupportable = (boolean)var46;
            }

            this.skipTag();
         }
      }
   }

   public String getPolicyKey() {
      return this.mPolicyKey;
   }

   public SecurityPolicy.PolicySet getPolicySet() {
      return this.mPolicySet;
   }

   public int getPolicyStatus() {
      return this.mPolicyStatus;
   }

   public boolean getRemoteWipe() {
      return this.mRemoteWipe;
   }

   public boolean hasSupportablePolicySet() {
      boolean var1;
      if(this.mPolicySet == null) {
         var1 = false;
      } else if(this.mPolicySet.mMinPasswordLength > 16) {
         var1 = false;
      } else if(this.mPolicySet.mPasswordMode > 4) {
         var1 = false;
      } else if(!this.mIsSupportable) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean parse() throws IOException {
      // $FF: Couldn't be decompiled
   }

   void parseCharacteristic(XmlPullParser var1, ProvisionParser.ShadowPolicySet var2) throws XmlPullParserException, IOException {
      boolean var3 = true;

      while(true) {
         int var4 = var1.nextTag();
         if(var4 == 3) {
            String var5 = var1.getName();
            if("characteristic".equals(var5)) {
               return;
            }
         }

         if(var4 == 2) {
            String var6 = var1.getName();
            if("parm".equals(var6)) {
               String var7 = var1.getAttributeValue((String)null, "name");
               String var8 = var1.getAttributeValue((String)null, "value");
               if("AEFrequencyValue".equals(var7)) {
                  if(var3) {
                     if("0".equals(var8)) {
                        var2.mMaxScreenLockTime = 1;
                     } else {
                        int var9 = Integer.parseInt(var8) * 60;
                        var2.mMaxScreenLockTime = var9;
                     }
                  }
               } else if("AEFrequencyType".equals(var7)) {
                  if("0".equals(var8)) {
                     var3 = false;
                  }
               } else if("DeviceWipeThreshold".equals(var7)) {
                  int var10 = Integer.parseInt(var8);
                  var2.mMaxPasswordFails = var10;
               } else if(!"CodewordFrequency".equals(var7)) {
                  if("MinimumPasswordLength".equals(var7)) {
                     int var11 = Integer.parseInt(var8);
                     var2.mMinPasswordLength = var11;
                  } else if("PasswordComplexity".equals(var7)) {
                     if("0".equals(var8)) {
                        var2.mPasswordMode = 4;
                     } else {
                        var2.mPasswordMode = 2;
                     }
                  }
               }
            }
         }
      }
   }

   public void parsePolicies() throws IOException {
      while(this.nextTag(902) != 3) {
         if(this.tag == 903) {
            this.parsePolicy();
         } else {
            this.skipTag();
         }
      }

   }

   public void parsePolicy() throws IOException {
      String var1 = null;

      while(this.nextTag(903) != 3) {
         switch(this.tag) {
         case 904:
            var1 = this.getValue();
            EasSyncService var2 = this.mService;
            String[] var3 = new String[]{"Policy type: ", var1};
            var2.userLog(var3);
            break;
         case 905:
            String var4 = this.getValue();
            this.mPolicyKey = var4;
            break;
         case 906:
            if(var1 != null && var1.equalsIgnoreCase("MS-WAP-Provisioning-XML")) {
               String var8 = this.getValue();
               this.parseProvisionDocXml(var8);
               break;
            }

            this.parseProvisionData();
            break;
         case 907:
            int var5 = this.getValueInt();
            this.mPolicyStatus = var5;
            EasSyncService var6 = this.mService;
            int var7 = this.mPolicyStatus;
            var6.userLog("Policy status: ", var7);
            break;
         default:
            this.skipTag();
         }
      }

   }

   public void parseProvisionData() throws IOException {
      while(this.nextTag(906) != 3) {
         if(this.tag == 909) {
            this.parseProvisionDocWbxml();
         } else {
            this.skipTag();
         }
      }

   }

   public void parseProvisionDocXml(String var1) throws IOException {
      ProvisionParser.ShadowPolicySet var2 = new ProvisionParser.ShadowPolicySet();

      try {
         XmlPullParser var5 = XmlPullParserFactory.newInstance().newPullParser();
         byte[] var6 = var1.getBytes();
         ByteArrayInputStream var7 = new ByteArrayInputStream(var6);
         String var10 = "UTF-8";
         var5.setInput(var7, var10);
         if(var5.getEventType() == 0) {
            int var11 = var5.next();
            byte var12 = 2;
            if(var11 == var12) {
               String var13 = var5.getName();
               String var14 = "wap-provisioningdoc";
               if(var14.equals(var13)) {
                  this.parseWapProvisioningDoc(var5, var2);
               }
            }
         }
      } catch (XmlPullParserException var55) {
         throw new IOException();
      }

      int var19 = var2.mMinPasswordLength;
      int var20 = var2.mPasswordMode;
      int var21 = var2.mMaxPasswordFails;
      int var22 = var2.mMaxScreenLockTime;
      boolean var23 = var2.mPasswordRecoverable;
      int var24 = var2.mPasswordExpires;
      int var25 = var2.mPasswordHistory;
      boolean var26 = var2.mAttachmentsEnabled;
      int var27 = var2.mMaxAttachmentSize;
      boolean var28 = var2.mAllowStorageCard;
      boolean var29 = var2.mAllowCamera;
      boolean var30 = var2.mAllowWifi;
      boolean var31 = var2.mAllowTextMessaging;
      boolean var32 = var2.mAllowPOPIMAPEmail;
      boolean var33 = var2.mAllowHTMLEmail;
      boolean var34 = var2.mAllowBrowser;
      boolean var35 = var2.mAllowInternetSharing;
      boolean var36 = var2.mRequireManualSyncWhenRoaming;
      int var37 = var2.mAllowBluetoothMode;
      int var38 = var2.mMinPasswordComplexChars;
      int var39 = var2.mMaxCalendarAgeFilter;
      int var40 = var2.mMaxEmailAgeFilter;
      int var41 = var2.mMaxEmailBodyTruncationSize;
      int var42 = var2.mMaxEmailHtmlBodyTruncationSize;
      boolean var43 = var2.mRequireSignedSMIMEMessages;
      boolean var44 = var2.mRequireEncryptedSMIMEMessages;
      int var45 = var2.mRequireSignedSMIMEAlgorithm;
      int var46 = var2.mRequireEncryptionSMIMEAlgorithm;
      int var47 = var2.mAllowSMIMEEncryptionAlgorithmNegotiation;
      boolean var48 = var2.mAllowSMIMESoftCerts;
      boolean var49 = var2.mAllowDesktopSync;
      boolean var50 = var2.mAllowIrDA;
      boolean var51 = var2.mRequireDeviceEncryption;
      boolean var52 = var2.mRequireStorageCardEncryption;
      SecurityPolicy.PolicySet var53 = new SecurityPolicy.PolicySet(var19, var20, var21, var22, (boolean)1, var23, var24, var25, var26, var27, var28, var29, var30, var31, var32, var33, var34, var35, var36, var37, var38, var39, var40, var41, var42, var43, var44, var45, var46, var47, var48, var49, var50, var51, var52);
      this.mPolicySet = var53;
   }

   void parseRegistry(XmlPullParser var1, ProvisionParser.ShadowPolicySet var2) throws XmlPullParserException, IOException {
      while(true) {
         int var3 = var1.nextTag();
         if(var3 == 3) {
            String var4 = var1.getName();
            if("characteristic".equals(var4)) {
               return;
            }
         }

         if(var3 == 2) {
            String var5 = var1.getName();
            if("characteristic".equals(var5)) {
               this.parseCharacteristic(var1, var2);
            }
         }
      }
   }

   boolean parseSecurityPolicy(XmlPullParser var1, ProvisionParser.ShadowPolicySet var2) throws XmlPullParserException, IOException {
      boolean var3 = true;

      while(true) {
         int var4 = var1.nextTag();
         if(var4 == 3) {
            String var5 = var1.getName();
            if("characteristic".equals(var5)) {
               return var3;
            }
         }

         if(var4 == 2) {
            String var6 = var1.getName();
            if("parm".equals(var6)) {
               String var7 = var1.getAttributeValue((String)null, "name");
               if("4131".equals(var7)) {
                  String var8 = var1.getAttributeValue((String)null, "value");
                  if("1".equals(var8)) {
                     var3 = false;
                  }
               }
            }
         }
      }
   }

   void parseWapProvisioningDoc(XmlPullParser var1, ProvisionParser.ShadowPolicySet var2) throws XmlPullParserException, IOException {
      while(true) {
         int var3 = var1.nextTag();
         if(var3 == 3) {
            String var4 = var1.getName();
            if("wap-provisioningdoc".equals(var4)) {
               return;
            }
         }

         if(var3 == 2) {
            String var5 = var1.getName();
            if("characteristic".equals(var5)) {
               String var6 = var1.getAttributeValue((String)null, "type");
               if("SecurityPolicy".equals(var6)) {
                  if(!this.parseSecurityPolicy(var1, var2)) {
                     return;
                  }
               } else if("Registry".equals(var6)) {
                  this.parseRegistry(var1, var2);
                  return;
               }
            }
         }
      }
   }

   class ShadowPolicySet {

      public int mAllowBluetoothMode = 2;
      public boolean mAllowBrowser = 1;
      public boolean mAllowCamera = 1;
      public boolean mAllowDesktopSync = 1;
      public boolean mAllowHTMLEmail = 1;
      public boolean mAllowInternetSharing = 1;
      public boolean mAllowIrDA = 1;
      public boolean mAllowPOPIMAPEmail = 1;
      public int mAllowSMIMEEncryptionAlgorithmNegotiation = -1;
      public boolean mAllowSMIMESoftCerts = 1;
      public boolean mAllowStorageCard = 1;
      public boolean mAllowTextMessaging = 1;
      public boolean mAllowWifi = 1;
      public boolean mAttachmentsEnabled = 1;
      public int mMaxAttachmentSize = 0;
      public int mMaxCalendarAgeFilter = 0;
      public int mMaxEmailAgeFilter = 0;
      public int mMaxEmailBodyTruncationSize = 0;
      public int mMaxEmailHtmlBodyTruncationSize = 0;
      int mMaxPasswordFails = 0;
      int mMaxScreenLockTime = 0;
      public int mMinPasswordComplexChars = 0;
      int mMinPasswordLength = 0;
      public int mPasswordExpires = 0;
      public int mPasswordHistory = 0;
      int mPasswordMode = 0;
      public boolean mPasswordRecoverable = 0;
      public boolean mRequireDeviceEncryption = 0;
      public boolean mRequireEncryptedSMIMEMessages = 0;
      public int mRequireEncryptionSMIMEAlgorithm = -1;
      public boolean mRequireManualSyncWhenRoaming = 0;
      public int mRequireSignedSMIMEAlgorithm = -1;
      public boolean mRequireSignedSMIMEMessages = 0;
      public boolean mRequireStorageCardEncryption = 0;


      ShadowPolicySet() {}
   }
}
