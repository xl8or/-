package gnu.mail.providers.nntp;

import gnu.mail.providers.nntp.NNTPFolder;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;

public final class NNTPMessage extends MimeMessage {

   String messageId;


   NNTPMessage(NNTPFolder var1, int var2, String var3) {
      super(var1, var2);
      this.messageId = var3;
      this.headers = null;
      Flags var4 = var1.getPermanentFlags();
      this.flags = var4;
      if(var1.isSeen(var2)) {
         Flags var5 = this.flags;
         Flags.Flag var6 = Flags.Flag.SEEN;
         var5.add(var6);
      } else {
         Flags var7 = this.flags;
         Flags.Flag var8 = Flags.Flag.SEEN;
         var7.remove(var8);
      }
   }

   public Enumeration getAllHeaderLines() throws MessagingException {
      if(this.headers == null) {
         this.requestHeaders();
      }

      return super.getAllHeaderLines();
   }

   public Enumeration getAllHeaders() throws MessagingException {
      if(this.headers == null) {
         this.requestHeaders();
      }

      return super.getAllHeaders();
   }

   public InputStream getContentStream() throws MessagingException {
      if(this.content == null) {
         this.requestContent();
      }

      return super.getContentStream();
   }

   public String getHeader(String var1, String var2) throws MessagingException {
      if(this.headers == null) {
         this.requestHeaders();
      }

      return super.getHeader(var1, var2);
   }

   public String[] getHeader(String var1) throws MessagingException {
      if(this.headers == null) {
         this.requestHeaders();
      }

      return super.getHeader(var1);
   }

   public int getLineCount() throws MessagingException {
      String var1 = this.getHeader("Lines", ",");
      int var3;
      if(var1 != null) {
         label23: {
            int var2;
            try {
               var2 = Integer.parseInt(var1.trim());
            } catch (NumberFormatException var5) {
               break label23;
            }

            var3 = var2;
            return var3;
         }
      }

      var3 = -1;
      return var3;
   }

   public Enumeration getMatchingHeaderLines(String[] var1) throws MessagingException {
      if(this.headers == null) {
         this.requestHeaders();
      }

      return super.getMatchingHeaderLines(var1);
   }

   public Enumeration getMatchingHeaders(String[] var1) throws MessagingException {
      if(this.headers == null) {
         this.requestHeaders();
      }

      return super.getMatchingHeaders(var1);
   }

   public String getMessageId() {
      return this.messageId;
   }

   public Enumeration getNonMatchingHeaderLines(String[] var1) throws MessagingException {
      if(this.headers == null) {
         this.requestHeaders();
      }

      return super.getNonMatchingHeaderLines(var1);
   }

   public Enumeration getNonMatchingHeaders(String[] var1) throws MessagingException {
      if(this.headers == null) {
         this.requestHeaders();
      }

      return super.getNonMatchingHeaders(var1);
   }

   public int getSize() throws MessagingException {
      if(this.content == null) {
         this.requestContent();
      }

      return super.getSize();
   }

   void requestContent() throws MessagingException {
      FetchProfile var1 = new FetchProfile();
      FetchProfile.Item var2 = FetchProfile.Item.CONTENT_INFO;
      var1.add(var2);
      NNTPMessage[] var3 = new NNTPMessage[]{this};
      this.folder.fetch(var3, var1);
   }

   void requestHeaders() throws MessagingException {
      FetchProfile var1 = new FetchProfile();
      FetchProfile.Item var2 = FetchProfile.Item.ENVELOPE;
      var1.add(var2);
      NNTPMessage[] var3 = new NNTPMessage[]{this};
      this.folder.fetch(var3, var1);
   }

   public void saveChanges() throws MessagingException {
      if(this.headers == null) {
         this.requestHeaders();
      }

      if(this.content == null) {
         this.requestContent();
      }
   }

   public void setFlags(Flags var1, boolean var2) throws MessagingException {
      Flags.Flag var3 = Flags.Flag.SEEN;
      if(var1.contains(var3)) {
         NNTPFolder var4 = (NNTPFolder)this.folder;
         int var5 = this.msgnum;
         var4.setSeen(var5, var2);
      }

      super.setFlags(var1, var2);
   }

   void updateContent(byte[] var1) {
      this.content = var1;
   }

   void updateHeaders(InputStream var1) throws MessagingException, IOException {
      InternetHeaders var2 = new InternetHeaders(var1);
      this.headers = var2;
   }
}
