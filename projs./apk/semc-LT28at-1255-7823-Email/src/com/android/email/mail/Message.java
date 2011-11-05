package com.android.email.mail;

import com.android.email.mail.Address;
import com.android.email.mail.Body;
import com.android.email.mail.Flag;
import com.android.email.mail.Folder;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Part;
import java.util.Date;
import java.util.HashSet;

public abstract class Message implements Part, Body {

   public static final Message[] EMPTY_ARRAY = new Message[0];
   private HashSet<Flag> mFlags = null;
   protected Folder mFolder;
   protected Date mInternalDate;
   protected String mUid;


   public Message() {}

   private HashSet<Flag> getFlagSet() {
      if(this.mFlags == null) {
         HashSet var1 = new HashSet();
         this.mFlags = var1;
      }

      return this.mFlags;
   }

   public abstract void addHeader(String var1, String var2) throws MessagingException;

   public abstract Body getBody() throws MessagingException;

   public abstract String getContentType() throws MessagingException;

   public Flag[] getFlags() {
      HashSet var1 = this.getFlagSet();
      Flag[] var2 = new Flag[0];
      return (Flag[])var1.toArray(var2);
   }

   public Folder getFolder() {
      return this.mFolder;
   }

   public abstract Address[] getFrom() throws MessagingException;

   public abstract String[] getHeader(String var1) throws MessagingException;

   public Date getInternalDate() {
      return this.mInternalDate;
   }

   public abstract String getMessageId() throws MessagingException;

   public abstract Date getReceivedDate() throws MessagingException;

   public abstract Address[] getRecipients(Message.RecipientType var1) throws MessagingException;

   public abstract Address[] getReplyTo() throws MessagingException;

   public abstract Date getSentDate() throws MessagingException;

   public abstract String getSubject() throws MessagingException;

   public String getUid() {
      return this.mUid;
   }

   public boolean isMimeType(String var1) throws MessagingException {
      return this.getContentType().startsWith(var1);
   }

   public boolean isSet(Flag var1) {
      return this.getFlagSet().contains(var1);
   }

   public abstract void removeHeader(String var1) throws MessagingException;

   public abstract void saveChanges() throws MessagingException;

   public abstract void setBody(Body var1) throws MessagingException;

   public void setFlag(Flag var1, boolean var2) throws MessagingException {
      this.setFlagDirectlyForTest(var1, var2);
   }

   public final void setFlagDirectlyForTest(Flag var1, boolean var2) throws MessagingException {
      if(var2) {
         boolean var3 = this.getFlagSet().add(var1);
      } else {
         boolean var4 = this.getFlagSet().remove(var1);
      }
   }

   public void setFlags(Flag[] var1, boolean var2) throws MessagingException {
      Flag[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Flag var6 = var3[var5];
         this.setFlag(var6, var2);
      }

   }

   public abstract void setFrom(Address var1) throws MessagingException;

   public abstract void setHeader(String var1, String var2) throws MessagingException;

   public void setInternalDate(Date var1) {
      this.mInternalDate = var1;
   }

   public abstract void setMessageId(String var1) throws MessagingException;

   public void setRecipient(Message.RecipientType var1, Address var2) throws MessagingException {
      Address[] var3 = new Address[]{var2};
      this.setRecipients(var1, var3);
   }

   public abstract void setRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException;

   public abstract void setReplyTo(Address[] var1) throws MessagingException;

   public abstract void setSentDate(Date var1) throws MessagingException;

   public abstract void setSubject(String var1) throws MessagingException;

   public void setUid(String var1) {
      this.mUid = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.getClass().getSimpleName();
      StringBuilder var3 = var1.append(var2).append(':');
      String var4 = this.mUid;
      return var3.append(var4).toString();
   }

   public static enum RecipientType {

      // $FF: synthetic field
      private static final Message.RecipientType[] $VALUES;
      BCC("BCC", 2),
      CC("CC", 1),
      TO("TO", 0);


      static {
         Message.RecipientType[] var0 = new Message.RecipientType[3];
         Message.RecipientType var1 = TO;
         var0[0] = var1;
         Message.RecipientType var2 = CC;
         var0[1] = var2;
         Message.RecipientType var3 = BCC;
         var0[2] = var3;
         $VALUES = var0;
      }

      private RecipientType(String var1, int var2) {}
   }
}
