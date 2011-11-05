package gnu.mail.providers;

import java.io.InputStream;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.IllegalWriteException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;

public abstract class ReadOnlyMessage extends MimeMessage {

   protected ReadOnlyMessage(Folder var1, int var2) throws MessagingException {
      super(var1, var2);
   }

   protected ReadOnlyMessage(Folder var1, InputStream var2, int var3) throws MessagingException {
      super(var1, var2, var3);
   }

   protected ReadOnlyMessage(Folder var1, InternetHeaders var2, byte[] var3, int var4) throws MessagingException {
      super(var1, var4);
   }

   protected ReadOnlyMessage(MimeMessage var1) throws MessagingException {
      super(var1);
   }

   public void addHeader(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException();
   }

   public void addHeaderLine(String var1) throws MessagingException {
      throw new IllegalWriteException();
   }

   public void removeHeader(String var1) throws MessagingException {
      throw new IllegalWriteException();
   }

   public void saveChanges() throws MessagingException {}

   public void setContent(Object var1, String var2) throws MessagingException {
      throw new IllegalWriteException();
   }

   public void setContent(Multipart var1) throws MessagingException {
      throw new IllegalWriteException();
   }

   public void setFlags(Flags var1, boolean var2) throws MessagingException {
      throw new IllegalWriteException();
   }

   public void setHeader(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException();
   }
}
