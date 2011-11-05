package com.htc.android.mail;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.htc.android.mail.Account;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import java.io.Serializable;

public class MailMessage implements Serializable, Parcelable {

   public static final Creator<MailMessage> CREATOR = new MailMessage.1();
   private static final long serialVersionUID = 1L;
   public long accountId;
   public int flags;
   public String group;
   public long id;
   public String mSubjectCharset;
   public long mailBoxId;
   public String msgIdInHeader;
   public int retryCount;
   public String uid;


   public MailMessage() {
      this.uid = null;
      this.msgIdInHeader = null;
      this.mailBoxId = 65535L;
      this.flags = 0;
      this.accountId = 65535L;
      this.group = null;
      String var1 = Mail.getDefaultCharset();
      this.mSubjectCharset = var1;
      this.retryCount = 0;
   }

   public MailMessage(long var1) {
      this.uid = null;
      this.msgIdInHeader = null;
      this.mailBoxId = 65535L;
      this.flags = 0;
      this.accountId = 65535L;
      this.group = null;
      String var3 = Mail.getDefaultCharset();
      this.mSubjectCharset = var3;
      this.retryCount = 0;
      this.id = var1;
   }

   public MailMessage(long var1, String var3, String var4) {
      this.uid = null;
      this.msgIdInHeader = null;
      this.mailBoxId = 65535L;
      this.flags = 0;
      this.accountId = 65535L;
      this.group = null;
      String var5 = Mail.getDefaultCharset();
      this.mSubjectCharset = var5;
      this.retryCount = 0;
      this.id = var1;
      this.uid = var3;
      this.msgIdInHeader = var4;
   }

   public MailMessage(long var1, String var3, String var4, int var5) {
      this.uid = null;
      this.msgIdInHeader = null;
      this.mailBoxId = 65535L;
      this.flags = 0;
      this.accountId = 65535L;
      this.group = null;
      String var6 = Mail.getDefaultCharset();
      this.mSubjectCharset = var6;
      this.retryCount = 0;
      this.id = var1;
      this.uid = var3;
      this.msgIdInHeader = var4;
      this.retryCount = var5;
   }

   public MailMessage(long var1, String var3, String var4, String var5) {
      this.uid = null;
      this.msgIdInHeader = null;
      this.mailBoxId = 65535L;
      this.flags = 0;
      this.accountId = 65535L;
      this.group = null;
      String var6 = Mail.getDefaultCharset();
      this.mSubjectCharset = var6;
      this.retryCount = 0;
      this.id = var1;
      this.uid = var3;
      this.msgIdInHeader = var4;
      this.mSubjectCharset = var5;
   }

   private MailMessage(Parcel var1) {
      this.uid = null;
      this.msgIdInHeader = null;
      this.mailBoxId = 65535L;
      this.flags = 0;
      this.accountId = 65535L;
      this.group = null;
      String var2 = Mail.getDefaultCharset();
      this.mSubjectCharset = var2;
      this.retryCount = 0;
      this.readFromParcel(var1);
   }

   // $FF: synthetic method
   MailMessage(Parcel var1, MailMessage.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof MailMessage)) {
         var2 = false;
      } else {
         MailMessage var3 = (MailMessage)var1;
         long var4 = this.id;
         long var6 = var3.id;
         if(var4 == var6) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public Account getAccount() {
      Account var1 = MailProvider.getAccount(this.accountId);
      if(var1 == null) {
         throw new Error("account is null");
      } else {
         return var1;
      }
   }

   public int hashCode() {
      long var1 = this.id;
      long var3 = this.id >>> 32;
      return (int)(var1 ^ var3);
   }

   public void readFromParcel(Parcel var1) {
      long var2 = var1.readLong();
      this.id = var2;
      String var4 = var1.readString();
      this.uid = var4;
      String var5 = var1.readString();
      this.msgIdInHeader = var5;
      long var6 = var1.readLong();
      this.mailBoxId = var6;
      int var8 = var1.readInt();
      this.flags = var8;
      long var9 = var1.readLong();
      this.accountId = var9;
      String var11 = var1.readString();
      this.group = var11;
   }

   public void setAccountId(long var1) {
      this.accountId = var1;
   }

   public void setGroup(String var1) {
      this.group = var1;
   }

   public void setId(long var1) {
      this.id = var1;
   }

   public void setMailboxId(long var1) {
      this.mailBoxId = var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.id;
      var1.writeLong(var3);
      String var5 = this.uid;
      var1.writeString(var5);
      String var6 = this.msgIdInHeader;
      var1.writeString(var6);
      long var7 = this.mailBoxId;
      var1.writeLong(var7);
      var1.writeInt(var2);
      long var9 = this.accountId;
      var1.writeLong(var9);
      String var11 = this.group;
      var1.writeString(var11);
   }

   static class 1 implements Creator<MailMessage> {

      1() {}

      public MailMessage createFromParcel(Parcel var1) {
         return new MailMessage(var1, (MailMessage.1)null);
      }

      public MailMessage[] newArray(int var1) {
         return new MailMessage[var1];
      }
   }
}
