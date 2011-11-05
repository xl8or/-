package javax.mail.internet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownServiceException;
import javax.activation.DataSource;
import javax.mail.MessageAware;
import javax.mail.MessageContext;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;

public class MimePartDataSource implements DataSource, MessageAware {

   private MessageContext context;
   protected MimePart part;


   public MimePartDataSource(MimePart var1) {
      this.part = var1;
   }

   public String getContentType() {
      String var1;
      String var2;
      try {
         var1 = this.part.getContentType();
      } catch (MessagingException var4) {
         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public InputStream getInputStream() throws IOException {
      try {
         InputStream var1;
         if(this.part instanceof MimeBodyPart) {
            var1 = ((MimeBodyPart)this.part).getContentStream();
         } else {
            if(!(this.part instanceof MimeMessage)) {
               throw new MessagingException("Unknown part type");
            }

            var1 = ((MimeMessage)this.part).getContentStream();
         }

         String var2 = this.part.getEncoding();
         if(var2 != null) {
            var1 = MimeUtility.decode(var1, var2);
         }

         return var1;
      } catch (MessagingException var4) {
         String var3 = var4.getMessage();
         throw new IOException(var3);
      }
   }

   public MessageContext getMessageContext() {
      if(this.context == null) {
         MimePart var1 = this.part;
         MessageContext var2 = new MessageContext(var1);
         this.context = var2;
      }

      return this.context;
   }

   public String getName() {
      return "";
   }

   public OutputStream getOutputStream() throws IOException {
      throw new UnknownServiceException();
   }
}
