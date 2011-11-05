package gnu.mail.providers.imap;

import gnu.inet.imap.IMAPConstants;
import gnu.inet.imap.MessageStatus;
import gnu.inet.imap.Pair;
import gnu.mail.providers.imap.IMAPMessage;
import gnu.mail.providers.imap.IMAPMultipart;
import gnu.mail.providers.imap.IMAPMultipartDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;

public class IMAPBodyPart extends MimeBodyPart implements IMAPConstants {

   protected int lines;
   protected IMAPMessage message;
   IMAPMultipart multipart = null;
   protected String section;
   protected int size;


   protected IMAPBodyPart(IMAPMessage var1, IMAPMultipart var2, String var3, InternetHeaders var4, int var5, int var6) throws MessagingException {
      super(var4, (byte[])null);
      this.parent = var2;
      this.message = var1;
      this.section = var3;
      this.size = var5;
      this.lines = var6;
   }

   void fetch(String[] param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   void fetchContent() throws MessagingException {
      String[] var1 = new String[1];
      StringBuilder var2 = (new StringBuilder()).append("BODY.PEEK[");
      String var3 = this.section;
      String var4 = var2.append(var3).append("]").toString();
      var1[0] = var4;
      this.fetch(var1);
   }

   public Object getContent() throws MessagingException, IOException {
      String var1 = this.getContentType();
      String var2 = (new ContentType(var1)).getPrimaryType();
      Object var3;
      if("multipart".equalsIgnoreCase(var2)) {
         var3 = this.multipart;
      } else {
         var3 = super.getContent();
      }

      return var3;
   }

   protected InputStream getContentStream() throws MessagingException {
      if(this.content == null) {
         this.fetchContent();
      }

      return super.getContentStream();
   }

   public DataHandler getDataHandler() throws MessagingException {
      String var1 = this.getContentType();
      String var2 = (new ContentType(var1)).getPrimaryType();
      DataHandler var5;
      if("multipart".equalsIgnoreCase(var2)) {
         IMAPMultipart var3 = this.multipart;
         IMAPMultipartDataSource var4 = new IMAPMultipartDataSource(var3);
         var5 = new DataHandler(var4);
      } else {
         if(this.content == null) {
            this.fetchContent();
         }

         var5 = super.getDataHandler();
      }

      return var5;
   }

   public int getLineCount() throws MessagingException {
      return this.lines;
   }

   public int getSize() throws MessagingException {
      return this.size;
   }

   void update(MessageStatus var1) throws MessagingException {
      List var2 = var1.getCode();
      int var3 = var2.size();

      for(int var4 = 0; var4 < var3; var4 += 2) {
         Object var5 = var2.get(var4);
         List var6 = Collections.EMPTY_LIST;
         String var8;
         List var9;
         if(var5 instanceof Pair) {
            Pair var7 = (Pair)var5;
            var8 = var7.getKey();
            var9 = var7.getValue();
         } else {
            if(!(var5 instanceof String)) {
               String var18 = "Unexpected status item: " + var5;
               throw new MessagingException(var18);
            }

            String var16 = (String)var5;
            var8 = var16;
            var9 = var6;
         }

         if(var8 != "BODY") {
            String var28 = "Unknown section status key: " + var8;
            throw new MessagingException(var28);
         }

         if(var9.size() <= 0) {
            throw new MessagingException("Not a section!");
         }

         Object var10 = var9.get(0);
         if(!(var10 instanceof String)) {
            String var19 = "Unexpected status item: " + var10;
            throw new MessagingException(var19);
         }

         String var11 = (String)var10;
         String var12 = this.section;
         if(!var11.equals(var12)) {
            String var27 = "Unexpected section number: " + var11;
            throw new MessagingException(var27);
         }

         int var13 = var4 + 1;
         Object var14 = var2.get(var13);
         if(var14 instanceof byte[]) {
            byte[] var15 = (byte[])((byte[])var14);
            this.content = var15;
         } else {
            if(!(var14 instanceof String)) {
               String var26 = "Unexpected MIME body part content: " + var14;
               throw new MessagingException(var26);
            }

            String var20 = this.getContentType();
            String var21 = (new ContentType(var20)).getParameter("charset");
            if(var21 == null) {
               ;
            }

            try {
               byte[] var22 = ((String)var14).getBytes(var21);
               this.content = var22;
            } catch (IOException var29) {
               MessagingException var24 = new MessagingException();
               Throwable var25 = var24.initCause(var29);
               throw var24;
            }
         }
      }

   }
}
