package gnu.mail.providers.pop3;

import gnu.mail.providers.ReadOnlyMessage;
import gnu.mail.providers.pop3.POP3Folder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.Flags;
import javax.mail.IllegalWriteException;
import javax.mail.MessagingException;

public final class POP3Message extends ReadOnlyMessage {

   int size;
   String uid;


   POP3Message(POP3Folder var1, int var2, int var3) throws MessagingException {
      super(var1, var2);
      this.size = var3;
   }

   void fetchContent() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   void fetchHeaders() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   void fetchUid() throws MessagingException {
      // $FF: Couldn't be decompiled
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
         this.fetchContent();
      }

      return super.getContentStream();
   }

   public DataHandler getDataHandler() throws MessagingException {
      if(this.content == null) {
         this.fetchContent();
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

   public int getSize() throws MessagingException {
      int var1;
      if(this.size > -1) {
         var1 = this.size;
      } else {
         if(this.content == null) {
            this.fetchContent();
         }

         var1 = super.getSize();
      }

      return var1;
   }

   public String getUID() throws MessagingException {
      if(this.uid == null) {
         this.fetchUid();
      }

      return this.uid;
   }

   public void setFlags(Flags var1, boolean var2) throws MessagingException {
      Flags.Flag[] var3 = var1.getSystemFlags();
      int var4 = 0;

      while(true) {
         int var5 = var3.length;
         if(var4 >= var5) {
            return;
         }

         Flags.Flag var6 = var3[var4];
         if(var2 && !this.flags.contains(var6)) {
            this.flags.add(var6);
         } else if(!var2 && this.flags.contains(var6)) {
            this.flags.remove(var6);
         }

         Flags.Flag var7 = Flags.Flag.DELETED;
         if(var6.equals(var7)) {
            POP3Folder var8 = (POP3Folder)this.folder;
            if(var2 && !var8.deleted.contains(this)) {
               int var9 = this.folder.getMode();
               if(2 != var9) {
                  throw new IllegalWriteException();
               }

               var8.deleted.add(this);
            } else if(!var2 && var8.deleted.contains(this)) {
               var8.deleted.remove(this);
            }
         }

         ++var4;
      }
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      if(this.content == null) {
         this.fetchContent();
      }

      super.writeTo(var1);
   }

   public void writeTo(OutputStream var1, String[] var2) throws IOException, MessagingException {
      if(this.headers == null) {
         this.fetchHeaders();
      }

      if(this.content == null) {
         this.fetchContent();
      }

      super.writeTo(var1, var2);
   }
}
