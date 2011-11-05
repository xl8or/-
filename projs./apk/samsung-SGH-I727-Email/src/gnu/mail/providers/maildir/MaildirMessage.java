package gnu.mail.providers.maildir;

import gnu.mail.providers.ReadOnlyMessage;
import gnu.mail.providers.maildir.MaildirFolder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;

public final class MaildirMessage extends ReadOnlyMessage {

   static final String PASSED = "Passed";
   File file;
   String uniq;


   MaildirMessage(MaildirFolder var1, File var2, String var3, String var4, int var5) throws MessagingException {
      super(var1, var5);
      this.file = var2;
      this.uniq = var3;
      if(var4 != null) {
         if(var4.startsWith("2,")) {
            int var6 = var4.length();

            for(int var7 = 2; var7 < var6; ++var7) {
               switch(var4.charAt(var7)) {
               case 68:
                  Flags var8 = this.flags;
                  Flags.Flag var9 = Flags.Flag.DRAFT;
                  var8.add(var9);
                  break;
               case 70:
                  Flags var10 = this.flags;
                  Flags.Flag var11 = Flags.Flag.FLAGGED;
                  var10.add(var11);
                  break;
               case 80:
                  this.flags.add("Passed");
                  break;
               case 82:
                  Flags var12 = this.flags;
                  Flags.Flag var13 = Flags.Flag.ANSWERED;
                  var12.add(var13);
                  break;
               case 83:
                  Flags var14 = this.flags;
                  Flags.Flag var15 = Flags.Flag.SEEN;
                  var14.add(var15);
                  break;
               case 84:
                  Flags var16 = this.flags;
                  Flags.Flag var17 = Flags.Flag.DELETED;
                  var16.add(var17);
               }
            }

         }
      }
   }

   MaildirMessage(MaildirFolder var1, MimeMessage var2, int var3) throws MessagingException {
      super(var2);
      this.folder = var1;
      this.msgnum = var3;
   }

   static String getInfo(Flags var0) throws MessagingException {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append('2');
      StringBuffer var3 = var1.append(',');
      Flags.Flag var4 = Flags.Flag.DRAFT;
      if(var0.contains(var4)) {
         StringBuffer var5 = var1.append('D');
      }

      Flags.Flag var6 = Flags.Flag.FLAGGED;
      if(var0.contains(var6)) {
         StringBuffer var7 = var1.append('F');
      }

      if(var0.contains("Passed")) {
         StringBuffer var8 = var1.append('P');
      }

      Flags.Flag var9 = Flags.Flag.ANSWERED;
      if(var0.contains(var9)) {
         StringBuffer var10 = var1.append('R');
      }

      Flags.Flag var11 = Flags.Flag.SEEN;
      if(var0.contains(var11)) {
         StringBuffer var12 = var1.append('S');
      }

      Flags.Flag var13 = Flags.Flag.DELETED;
      if(var0.contains(var13)) {
         StringBuffer var14 = var1.append('T');
      }

      return var1.toString();
   }

   public boolean equals(Object var1) {
      boolean var6;
      if(var1 instanceof MimeMessage) {
         MimeMessage var7 = (MimeMessage)var1;
         Folder var2 = var7.getFolder();
         Folder var3 = this.getFolder();
         if(var2 == var3) {
            int var4 = var7.getMessageNumber();
            int var5 = this.getMessageNumber();
            if(var4 == var5) {
               var6 = true;
               return var6;
            }
         }

         var6 = false;
      } else {
         var6 = false;
      }

      return var6;
   }

   void fetch() throws MessagingException {
      if(this.content == null) {
         try {
            File var1 = this.file;
            FileInputStream var2 = new FileInputStream(var1);
            BufferedInputStream var3 = new BufferedInputStream(var2);
            this.parse(var3);
            var3.close();
         } catch (IOException var6) {
            String var5 = var6.getMessage();
            throw new MessagingException(var5, var6);
         }
      }
   }

   void fetchHeaders() throws MessagingException {
      if(this.headers == null) {
         try {
            File var1 = this.file;
            FileInputStream var2 = new FileInputStream(var1);
            BufferedInputStream var3 = new BufferedInputStream(var2);
            InternetHeaders var4 = this.createInternetHeaders(var3);
            this.headers = var4;
            var3.close();
         } catch (IOException var7) {
            String var6 = var7.getMessage();
            throw new MessagingException(var6, var7);
         }
      }
   }

   public Enumeration getAllHeaderLines() throws MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      return super.getAllHeaderLines();
   }

   public Enumeration getAllHeaders() throws MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      return super.getAllHeaders();
   }

   protected InputStream getContentStream() throws MessagingException {
      if(this.content == null) {
         this.fetch();
      }

      return super.getContentStream();
   }

   public DataHandler getDataHandler() throws MessagingException {
      if(this.content == null) {
         this.fetch();
      }

      return super.getDataHandler();
   }

   public String getHeader(String var1, String var2) throws MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      return super.getHeader(var1, var2);
   }

   public String[] getHeader(String var1) throws MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      return super.getHeader(var1);
   }

   String getInfo() throws MessagingException {
      return getInfo(this.flags);
   }

   public Enumeration getMatchingHeaderLines(String[] var1) throws MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      return super.getMatchingHeaderLines(var1);
   }

   public Enumeration getMatchingHeaders(String[] var1) throws MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      return super.getMatchingHeaders(var1);
   }

   public Enumeration getNonMatchingHeaderLines(String[] var1) throws MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      return super.getNonMatchingHeaderLines(var1);
   }

   public Enumeration getNonMatchingHeaders(String[] var1) throws MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      return super.getNonMatchingHeaders(var1);
   }

   String getUniq() {
      return this.uniq;
   }

   protected void setExpunged(boolean var1) {
      super.setExpunged(var1);
   }

   public void setFlags(Flags param1, boolean param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }
}
