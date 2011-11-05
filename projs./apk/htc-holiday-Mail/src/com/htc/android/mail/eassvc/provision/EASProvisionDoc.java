package com.htc.android.mail.eassvc.provision;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EASProvisionDoc implements Parcelable {

   public static final Creator<EASProvisionDoc> CREATOR = new EASProvisionDoc.1();
   private static final long serialVersionUID = 100L;
   public String AEFrequencyType;
   public String AEFrequencyValue;
   public String AllowBluetooth;
   public String AllowBrowser;
   public String AllowCamera;
   public String AllowConsumerEmail;
   public String AllowDesktopSync;
   public String AllowHTMLEmail;
   public String AllowInternetSharing;
   public String AllowIrDA;
   public String AllowPOPIMAPEmail;
   public String AllowRemoteDesktop;
   public String AllowSMIMEEncryptionAlgorithmNegotiation;
   public String AllowSMIMESoftCerts;
   public String AllowSimpleDevicePassword;
   public String AllowStorageCard;
   public String AllowTextMessaging;
   public String AllowUnsignedApplications;
   public String AllowUnsignedInstallationPackages;
   public String AllowWiFi;
   public String AlphanumericDevicePasswordRequired;
   public String[] ApprovedApplicationList;
   public String AttachmentsEnabled;
   public String CodewordFrequency;
   public String DeviceEncryptionEnabled;
   public String DevicePasswordEnabled;
   public String DevicePasswordExpiration;
   public String DevicePasswordHistory;
   public String DeviceWipeThreshold;
   public String MaxAttachmentSize;
   public String MaxCalendarAgeFilter;
   public String MaxDevicePasswordFailedAttempts;
   public String MaxEmailAgeFilter;
   public String MaxEmailBodyTruncationSize;
   public String MaxEmailHTMLBodyTruncationSize;
   public String MaxInactivityTimeDeviceLock;
   public String MinDevicePasswordComplexCharacters;
   public String MinDevicePasswordLength;
   public String MinimumPasswordLength;
   public String PasswordComplexity;
   public String PasswordRecoveryEnabled;
   public String RequireDeviceEncryption;
   public String RequireEncryptedSMIMEMessages;
   public String RequireEncryptionSMIMEAlgorithm;
   public String RequireManualSyncWhenRoaming;
   public String RequireSignedSMIMEAlgorithm;
   public String RequireSignedSMIMEMessages;
   public String RequireStorageCardEncryption;
   public String[] UnapprovedInROMApplicationList;
   public String code4131;
   public String code4133;
   public String protocolVersion;


   public EASProvisionDoc() {}

   private EASProvisionDoc(Parcel var1) {
      String var2 = var1.readString();
      this.protocolVersion = var2;
      String var3 = var1.readString();
      this.code4131 = var3;
      String var4 = var1.readString();
      this.code4133 = var4;
      String var5 = var1.readString();
      this.AEFrequencyType = var5;
      String var6 = var1.readString();
      this.AEFrequencyValue = var6;
      String var7 = var1.readString();
      this.DeviceWipeThreshold = var7;
      String var8 = var1.readString();
      this.CodewordFrequency = var8;
      String var9 = var1.readString();
      this.MinimumPasswordLength = var9;
      String var10 = var1.readString();
      this.PasswordComplexity = var10;
      String var11 = var1.readString();
      this.DevicePasswordEnabled = var11;
      String var12 = var1.readString();
      this.AlphanumericDevicePasswordRequired = var12;
      String var13 = var1.readString();
      this.PasswordRecoveryEnabled = var13;
      String var14 = var1.readString();
      this.DeviceEncryptionEnabled = var14;
      String var15 = var1.readString();
      this.AttachmentsEnabled = var15;
      String var16 = var1.readString();
      this.MinDevicePasswordLength = var16;
      String var17 = var1.readString();
      this.MaxInactivityTimeDeviceLock = var17;
      String var18 = var1.readString();
      this.MaxDevicePasswordFailedAttempts = var18;
      String var19 = var1.readString();
      this.MaxAttachmentSize = var19;
      String var20 = var1.readString();
      this.AllowSimpleDevicePassword = var20;
      String var21 = var1.readString();
      this.DevicePasswordExpiration = var21;
      String var22 = var1.readString();
      this.DevicePasswordHistory = var22;
      String var23 = var1.readString();
      this.AllowStorageCard = var23;
      String var24 = var1.readString();
      this.AllowCamera = var24;
      String var25 = var1.readString();
      this.RequireDeviceEncryption = var25;
      String var26 = var1.readString();
      this.RequireStorageCardEncryption = var26;
      String var27 = var1.readString();
      this.AllowUnsignedApplications = var27;
      String var28 = var1.readString();
      this.AllowUnsignedInstallationPackages = var28;
      String var29 = var1.readString();
      this.MinDevicePasswordComplexCharacters = var29;
      String var30 = var1.readString();
      this.AllowWiFi = var30;
      String var31 = var1.readString();
      this.AllowTextMessaging = var31;
      String var32 = var1.readString();
      this.AllowPOPIMAPEmail = var32;
      String var33 = var1.readString();
      this.AllowBluetooth = var33;
      String var34 = var1.readString();
      this.AllowIrDA = var34;
      String var35 = var1.readString();
      this.RequireManualSyncWhenRoaming = var35;
      String var36 = var1.readString();
      this.AllowDesktopSync = var36;
      String var37 = var1.readString();
      this.MaxCalendarAgeFilter = var37;
      String var38 = var1.readString();
      this.AllowHTMLEmail = var38;
      String var39 = var1.readString();
      this.MaxEmailAgeFilter = var39;
      String var40 = var1.readString();
      this.MaxEmailBodyTruncationSize = var40;
      String var41 = var1.readString();
      this.MaxEmailHTMLBodyTruncationSize = var41;
      String var42 = var1.readString();
      this.RequireSignedSMIMEMessages = var42;
      String var43 = var1.readString();
      this.RequireEncryptedSMIMEMessages = var43;
      String var44 = var1.readString();
      this.RequireSignedSMIMEAlgorithm = var44;
      String var45 = var1.readString();
      this.RequireEncryptionSMIMEAlgorithm = var45;
      String var46 = var1.readString();
      this.AllowSMIMEEncryptionAlgorithmNegotiation = var46;
      String var47 = var1.readString();
      this.AllowSMIMESoftCerts = var47;
      String var48 = var1.readString();
      this.AllowBrowser = var48;
      String var49 = var1.readString();
      this.AllowConsumerEmail = var49;
      String var50 = var1.readString();
      this.AllowRemoteDesktop = var50;
      String var51 = var1.readString();
      this.AllowInternetSharing = var51;
      String[] var52 = var1.readStringArray();
      this.UnapprovedInROMApplicationList = var52;
      String[] var53 = var1.readStringArray();
      this.ApprovedApplicationList = var53;
   }

   // $FF: synthetic method
   EASProvisionDoc(Parcel var1, EASProvisionDoc.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.protocolVersion;
      var1.writeString(var3);
      String var4 = this.code4131;
      var1.writeString(var4);
      String var5 = this.code4133;
      var1.writeString(var5);
      String var6 = this.AEFrequencyType;
      var1.writeString(var6);
      String var7 = this.AEFrequencyValue;
      var1.writeString(var7);
      String var8 = this.DeviceWipeThreshold;
      var1.writeString(var8);
      String var9 = this.CodewordFrequency;
      var1.writeString(var9);
      String var10 = this.MinimumPasswordLength;
      var1.writeString(var10);
      String var11 = this.PasswordComplexity;
      var1.writeString(var11);
      String var12 = this.DevicePasswordEnabled;
      var1.writeString(var12);
      String var13 = this.AlphanumericDevicePasswordRequired;
      var1.writeString(var13);
      String var14 = this.PasswordRecoveryEnabled;
      var1.writeString(var14);
      String var15 = this.DeviceEncryptionEnabled;
      var1.writeString(var15);
      String var16 = this.AttachmentsEnabled;
      var1.writeString(var16);
      String var17 = this.MinDevicePasswordLength;
      var1.writeString(var17);
      String var18 = this.MaxInactivityTimeDeviceLock;
      var1.writeString(var18);
      String var19 = this.MaxDevicePasswordFailedAttempts;
      var1.writeString(var19);
      String var20 = this.MaxAttachmentSize;
      var1.writeString(var20);
      String var21 = this.AllowSimpleDevicePassword;
      var1.writeString(var21);
      String var22 = this.DevicePasswordExpiration;
      var1.writeString(var22);
      String var23 = this.DevicePasswordHistory;
      var1.writeString(var23);
      String var24 = this.AllowStorageCard;
      var1.writeString(var24);
      String var25 = this.AllowCamera;
      var1.writeString(var25);
      String var26 = this.RequireDeviceEncryption;
      var1.writeString(var26);
      String var27 = this.RequireStorageCardEncryption;
      var1.writeString(var27);
      String var28 = this.AllowUnsignedApplications;
      var1.writeString(var28);
      String var29 = this.AllowUnsignedInstallationPackages;
      var1.writeString(var29);
      String var30 = this.MinDevicePasswordComplexCharacters;
      var1.writeString(var30);
      String var31 = this.AllowWiFi;
      var1.writeString(var31);
      String var32 = this.AllowTextMessaging;
      var1.writeString(var32);
      String var33 = this.AllowPOPIMAPEmail;
      var1.writeString(var33);
      String var34 = this.AllowBluetooth;
      var1.writeString(var34);
      String var35 = this.AllowIrDA;
      var1.writeString(var35);
      String var36 = this.RequireManualSyncWhenRoaming;
      var1.writeString(var36);
      String var37 = this.AllowDesktopSync;
      var1.writeString(var37);
      String var38 = this.MaxCalendarAgeFilter;
      var1.writeString(var38);
      String var39 = this.AllowHTMLEmail;
      var1.writeString(var39);
      String var40 = this.MaxEmailAgeFilter;
      var1.writeString(var40);
      String var41 = this.MaxEmailBodyTruncationSize;
      var1.writeString(var41);
      String var42 = this.MaxEmailHTMLBodyTruncationSize;
      var1.writeString(var42);
      String var43 = this.RequireSignedSMIMEMessages;
      var1.writeString(var43);
      String var44 = this.RequireEncryptedSMIMEMessages;
      var1.writeString(var44);
      String var45 = this.RequireSignedSMIMEAlgorithm;
      var1.writeString(var45);
      String var46 = this.RequireEncryptionSMIMEAlgorithm;
      var1.writeString(var46);
      String var47 = this.AllowSMIMEEncryptionAlgorithmNegotiation;
      var1.writeString(var47);
      String var48 = this.AllowSMIMESoftCerts;
      var1.writeString(var48);
      String var49 = this.AllowBrowser;
      var1.writeString(var49);
      String var50 = this.AllowConsumerEmail;
      var1.writeString(var50);
      String var51 = this.AllowRemoteDesktop;
      var1.writeString(var51);
      String var52 = this.AllowInternetSharing;
      var1.writeString(var52);
      String[] var53 = this.UnapprovedInROMApplicationList;
      var1.writeStringArray(var53);
      String[] var54 = this.ApprovedApplicationList;
      var1.writeStringArray(var54);
   }

   static class 1 implements Creator<EASProvisionDoc> {

      1() {}

      public EASProvisionDoc createFromParcel(Parcel var1) {
         return new EASProvisionDoc(var1, (EASProvisionDoc.1)null);
      }

      public EASProvisionDoc[] newArray(int var1) {
         return new EASProvisionDoc[var1];
      }
   }
}
