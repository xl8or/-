package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EASAccount implements Parcelable {

   public static final Creator<EASAccount> CREATOR = new EASAccount.1();
   public int account_flags;
   public int alwaysBccMyself;
   public int askBeforeDelete;
   public int defaultAccount;
   public int deleteFromServer;
   public String desc;
   public String draftFolder;
   public String draftFolderText;
   public String emailAddress;
   public int enableSDSave;
   public int fetchMailNum;
   public int fontSize;
   public int inPort;
   public String inServer;
   public int initalScale;
   public String name;
   public int nextFetchTime;
   public int outPort;
   public String outServer;
   public String password;
   public int pollFrequencyNumber;
   public int previewLinesNumber;
   public int protocol;
   public String provider;
   public int replyWithText;
   public String sentFolder;
   public String sentFolderText;
   public String signature;
   public int sizeLimit;
   public String trashFolder;
   public String trashFolderText;
   public int useSSLin;
   public int useSSLout;
   public int useSignature;
   public String userName;


   public EASAccount() {
      this.protocol = 0;
      this.useSSLin = 1;
      this.useSSLout = 1;
      this.useSignature = 0;
      this.sizeLimit = 0;
      this.pollFrequencyNumber = 0;
      this.fetchMailNum = 0;
      this.previewLinesNumber = 2;
      this.fontSize = 1;
      this.deleteFromServer = 0;
      this.alwaysBccMyself = 0;
      this.askBeforeDelete = 1;
      this.enableSDSave = 0;
      this.replyWithText = 1;
      this.defaultAccount = 0;
      this.draftFolder = "draft";
      this.draftFolderText = "draft";
      this.initalScale = 0;
   }

   private EASAccount(Parcel var1) {
      String var2 = var1.readString();
      this.name = var2;
      String var3 = var1.readString();
      this.emailAddress = var3;
      String var4 = var1.readString();
      this.userName = var4;
      String var5 = var1.readString();
      this.password = var5;
      String var6 = var1.readString();
      this.desc = var6;
      int var7 = var1.readInt();
      this.protocol = var7;
      String var8 = var1.readString();
      this.inServer = var8;
      int var9 = var1.readInt();
      this.inPort = var9;
      String var10 = var1.readString();
      this.outServer = var10;
      int var11 = var1.readInt();
      this.outPort = var11;
      int var12 = var1.readInt();
      this.useSSLin = var12;
      int var13 = var1.readInt();
      this.useSSLout = var13;
      int var14 = var1.readInt();
      this.useSignature = var14;
      int var15 = var1.readInt();
      this.sizeLimit = var15;
      int var16 = var1.readInt();
      this.pollFrequencyNumber = var16;
      int var17 = var1.readInt();
      this.fetchMailNum = var17;
      int var18 = var1.readInt();
      this.previewLinesNumber = var18;
      int var19 = var1.readInt();
      this.fontSize = var19;
      int var20 = var1.readInt();
      this.deleteFromServer = var20;
      int var21 = var1.readInt();
      this.alwaysBccMyself = var21;
      int var22 = var1.readInt();
      this.askBeforeDelete = var22;
      int var23 = var1.readInt();
      this.enableSDSave = var23;
      String var24 = var1.readString();
      this.signature = var24;
      int var25 = var1.readInt();
      this.nextFetchTime = var25;
      String var26 = var1.readString();
      this.provider = var26;
      int var27 = var1.readInt();
      this.replyWithText = var27;
      int var28 = var1.readInt();
      this.defaultAccount = var28;
      String var29 = var1.readString();
      this.trashFolder = var29;
      String var30 = var1.readString();
      this.trashFolderText = var30;
      String var31 = var1.readString();
      this.sentFolder = var31;
      String var32 = var1.readString();
      this.sentFolderText = var32;
      String var33 = var1.readString();
      this.draftFolder = var33;
      String var34 = var1.readString();
      this.draftFolderText = var34;
      int var35 = var1.readInt();
      this.account_flags = var35;
      int var36 = var1.readInt();
      this.initalScale = var36;
   }

   // $FF: synthetic method
   EASAccount(Parcel var1, EASAccount.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.name;
      var1.writeString(var3);
      String var4 = this.emailAddress;
      var1.writeString(var4);
      String var5 = this.userName;
      var1.writeString(var5);
      String var6 = this.password;
      var1.writeString(var6);
      String var7 = this.desc;
      var1.writeString(var7);
      int var8 = this.protocol;
      var1.writeInt(var8);
      String var9 = this.inServer;
      var1.writeString(var9);
      int var10 = this.inPort;
      var1.writeInt(var10);
      String var11 = this.outServer;
      var1.writeString(var11);
      int var12 = this.outPort;
      var1.writeInt(var12);
      int var13 = this.useSSLin;
      var1.writeInt(var13);
      int var14 = this.useSSLout;
      var1.writeInt(var14);
      int var15 = this.useSignature;
      var1.writeInt(var15);
      int var16 = this.sizeLimit;
      var1.writeInt(var16);
      int var17 = this.pollFrequencyNumber;
      var1.writeInt(var17);
      int var18 = this.fetchMailNum;
      var1.writeInt(var18);
      int var19 = this.previewLinesNumber;
      var1.writeInt(var19);
      int var20 = this.fontSize;
      var1.writeInt(var20);
      int var21 = this.deleteFromServer;
      var1.writeInt(var21);
      int var22 = this.alwaysBccMyself;
      var1.writeInt(var22);
      int var23 = this.askBeforeDelete;
      var1.writeInt(var23);
      int var24 = this.enableSDSave;
      var1.writeInt(var24);
      String var25 = this.signature;
      var1.writeString(var25);
      int var26 = this.nextFetchTime;
      var1.writeInt(var26);
      String var27 = this.provider;
      var1.writeString(var27);
      int var28 = this.replyWithText;
      var1.writeInt(var28);
      int var29 = this.defaultAccount;
      var1.writeInt(var29);
      String var30 = this.trashFolder;
      var1.writeString(var30);
      String var31 = this.trashFolderText;
      var1.writeString(var31);
      String var32 = this.sentFolder;
      var1.writeString(var32);
      String var33 = this.sentFolderText;
      var1.writeString(var33);
      String var34 = this.draftFolder;
      var1.writeString(var34);
      String var35 = this.draftFolderText;
      var1.writeString(var35);
      int var36 = this.account_flags;
      var1.writeInt(var36);
      int var37 = this.initalScale;
      var1.writeInt(var37);
   }

   static class 1 implements Creator<EASAccount> {

      1() {}

      public EASAccount createFromParcel(Parcel var1) {
         return new EASAccount(var1, (EASAccount.1)null);
      }

      public EASAccount[] newArray(int var1) {
         return new EASAccount[var1];
      }
   }
}
