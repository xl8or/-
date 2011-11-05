package myorg.bouncycastle.mail.smime;

import java.io.IOException;
import java.io.OutputStream;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;
import myorg.bouncycastle.mail.smime.SMIMEUtil;

public class CMSProcessableBodyPartInbound implements CMSProcessable {

   private final BodyPart bodyPart;
   private final String defaultContentTransferEncoding;


   public CMSProcessableBodyPartInbound(BodyPart var1) {
      this(var1, "7bit");
   }

   public CMSProcessableBodyPartInbound(BodyPart var1, String var2) {
      this.bodyPart = var1;
      this.defaultContentTransferEncoding = var2;
   }

   public Object getContent() {
      return this.bodyPart;
   }

   public void write(OutputStream var1) throws IOException, CMSException {
      try {
         BodyPart var2 = this.bodyPart;
         String var3 = this.defaultContentTransferEncoding;
         SMIMEUtil.outputBodyPart(var1, var2, var3);
      } catch (MessagingException var6) {
         String var5 = "can\'t write BodyPart to stream: " + var6;
         throw new CMSException(var5, var6);
      }
   }
}
