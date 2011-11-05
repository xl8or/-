package gnu.mail.providers.imap;

import gnu.mail.providers.imap.IMAPMessage;
import javax.mail.Part;
import javax.mail.internet.MimeMultipart;

public class IMAPMultipart extends MimeMultipart {

   protected IMAPMessage message;


   protected IMAPMultipart(IMAPMessage var1, Part var2, String var3) {
      super(var3);
      this.setParent(var2);
      this.message = var1;
   }
}
