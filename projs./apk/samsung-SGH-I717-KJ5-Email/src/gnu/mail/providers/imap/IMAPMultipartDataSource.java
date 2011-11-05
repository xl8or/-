package gnu.mail.providers.imap;

import gnu.mail.providers.imap.IMAPBodyPart;
import gnu.mail.providers.imap.IMAPMessage;
import gnu.mail.providers.imap.IMAPMultipart;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.MultipartDataSource;
import javax.mail.Part;

public class IMAPMultipartDataSource implements MultipartDataSource {

   protected IMAPMultipart multipart;


   protected IMAPMultipartDataSource(IMAPMultipart var1) {
      this.multipart = var1;
   }

   public BodyPart getBodyPart(int var1) throws MessagingException {
      return this.multipart.getBodyPart(var1);
   }

   public String getContentType() {
      String var1;
      String var2;
      try {
         var1 = this.multipart.getParent().getContentType();
      } catch (MessagingException var4) {
         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public int getCount() {
      int var1;
      int var2;
      try {
         var1 = this.multipart.getCount();
      } catch (MessagingException var4) {
         var2 = 0;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public InputStream getInputStream() throws IOException {
      try {
         Part var4 = this.multipart.getParent();
         InputStream var1;
         if(var4 instanceof IMAPBodyPart) {
            var1 = ((IMAPBodyPart)var4).getContentStream();
         } else {
            if(!(var4 instanceof IMAPMessage)) {
               throw new IOException("Internal error in part structure");
            }

            var1 = ((IMAPMessage)var4).getContentStream();
         }

         return var1;
      } catch (MessagingException var3) {
         String var2 = var3.getMessage();
         throw new IOException(var2);
      }
   }

   public String getName() {
      String var1;
      String var2;
      try {
         var1 = this.multipart.getParent().getDescription();
      } catch (MessagingException var4) {
         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public OutputStream getOutputStream() throws IOException {
      throw new ProtocolException("IMAP multiparts are read-only");
   }
}
