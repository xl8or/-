package javax.mail;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Date;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.search.SearchTerm;

public abstract class Message implements Part {

   protected boolean expunged = 0;
   protected Folder folder;
   protected int msgnum = 0;
   protected Session session;


   protected Message() {
      this.folder = null;
      this.session = null;
   }

   protected Message(Folder var1, int var2) {
      this.folder = var1;
      this.msgnum = var2;
      Session var3 = var1.store.session;
      this.session = var3;
   }

   protected Message(Session var1) {
      this.folder = null;
      this.session = var1;
   }

   public abstract void addFrom(Address[] var1) throws MessagingException;

   public void addRecipient(Message.RecipientType var1, Address var2) throws MessagingException {
      Address[] var3 = new Address[]{var2};
      this.addRecipients(var1, var3);
   }

   public abstract void addRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException;

   public Address[] getAllRecipients() throws MessagingException {
      Message.RecipientType var1 = Message.RecipientType.TO;
      Address[] var2 = this.getRecipients(var1);
      Message.RecipientType var3 = Message.RecipientType.CC;
      Address[] var4 = this.getRecipients(var3);
      Message.RecipientType var5 = Message.RecipientType.BCC;
      Address[] var6 = this.getRecipients(var5);
      if(var4 != null || var6 != null) {
         int var7;
         if(var2 == null) {
            var7 = 0;
         } else {
            var7 = var2.length;
         }

         int var8;
         if(var4 == null) {
            var8 = 0;
         } else {
            var8 = var4.length;
         }

         int var9 = var7 + var8;
         byte var10;
         if(var6 == null) {
            var10 = 0;
         } else {
            int var19 = var6.length;
         }

         Address[] var11 = new Address[var9 + var10];
         int var13;
         if(var2 != null) {
            int var12 = var2.length;
            System.arraycopy(var2, 0, var11, 0, var12);
            var13 = var2.length + 0;
         } else {
            var13 = 0;
         }

         if(var4 != null) {
            int var14 = var4.length;
            System.arraycopy(var4, 0, var11, var13, var14);
            int var15 = var4.length;
            var13 += var15;
         }

         if(var6 != null) {
            int var16 = var6.length;
            System.arraycopy(var6, 0, var11, var13, var16);
            int var17 = var6.length;
            int var10000 = var13 + var17;
         }

         var2 = var11;
      }

      return var2;
   }

   public abstract Flags getFlags() throws MessagingException;

   public Folder getFolder() {
      return this.folder;
   }

   public abstract Address[] getFrom() throws MessagingException;

   public int getMessageNumber() {
      return this.msgnum;
   }

   public abstract Date getReceivedDate() throws MessagingException;

   public abstract Address[] getRecipients(Message.RecipientType var1) throws MessagingException;

   public Address[] getReplyTo() throws MessagingException {
      return this.getFrom();
   }

   public abstract Date getSentDate() throws MessagingException;

   public abstract String getSubject() throws MessagingException;

   public boolean isExpunged() {
      return this.expunged;
   }

   public boolean isSet(Flags.Flag var1) throws MessagingException {
      return this.getFlags().contains(var1);
   }

   public boolean match(SearchTerm var1) throws MessagingException {
      return var1.match(this);
   }

   public abstract Message reply(boolean var1) throws MessagingException;

   public abstract void saveChanges() throws MessagingException;

   protected void setExpunged(boolean var1) {
      this.expunged = var1;
   }

   public void setFlag(Flags.Flag var1, boolean var2) throws MessagingException {
      Flags var3 = new Flags(var1);
      this.setFlags(var3, var2);
   }

   public abstract void setFlags(Flags var1, boolean var2) throws MessagingException;

   public abstract void setFrom() throws MessagingException;

   public abstract void setFrom(Address var1) throws MessagingException;

   protected void setMessageNumber(int var1) {
      this.msgnum = var1;
   }

   public void setRecipient(Message.RecipientType var1, Address var2) throws MessagingException {
      Address[] var3 = new Address[]{var2};
      this.setRecipients(var1, var3);
   }

   public abstract void setRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException;

   public void setReplyTo(Address[] var1) throws MessagingException {
      throw new MethodNotSupportedException();
   }

   public abstract void setSentDate(Date var1) throws MessagingException;

   public abstract void setSubject(String var1) throws MessagingException;

   public static class RecipientType implements Serializable {

      public static final Message.RecipientType BCC = new Message.RecipientType("Bcc");
      public static final Message.RecipientType CC = new Message.RecipientType("Cc");
      public static final Message.RecipientType TO = new Message.RecipientType("To");
      protected String type;


      protected RecipientType(String var1) {
         this.type = var1;
      }

      protected Object readResolve() throws ObjectStreamException {
         Message.RecipientType var1;
         if(this.type.equals("To")) {
            var1 = TO;
         } else if(this.type.equals("Cc")) {
            var1 = CC;
         } else {
            if(!this.type.equals("Bcc")) {
               StringBuilder var2 = (new StringBuilder()).append("Unknown RecipientType: ");
               String var3 = this.type;
               String var4 = var2.append(var3).toString();
               throw new InvalidObjectException(var4);
            }

            var1 = BCC;
         }

         return var1;
      }

      public String toString() {
         return this.type;
      }
   }
}
