package myorg.bouncycastle.mail.smime;

import java.io.IOException;
import java.io.OutputStream;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;
import myorg.bouncycastle.mail.smime.SMIMEUtil;
import myorg.bouncycastle.mail.smime.util.CRLFOutputStream;

public class CMSProcessableBodyPartOutbound implements CMSProcessable {

   private BodyPart bodyPart;
   private String defaultContentTransferEncoding;


   public CMSProcessableBodyPartOutbound(BodyPart var1) {
      this.bodyPart = var1;
   }

   public CMSProcessableBodyPartOutbound(BodyPart var1, String var2) {
      this.bodyPart = var1;
      this.defaultContentTransferEncoding = var2;
   }

   public Object getContent() {
      return this.bodyPart;
   }

   public void write(OutputStream var1) throws IOException, CMSException {
      try {
         MimeBodyPart var2 = (MimeBodyPart)this.bodyPart;
         String var3 = this.defaultContentTransferEncoding;
         if(SMIMEUtil.isCanonicalisationRequired(var2, var3)) {
            var1 = new CRLFOutputStream((OutputStream)var1);
         }

         this.bodyPart.writeTo((OutputStream)var1);
      } catch (MessagingException var5) {
         throw new CMSException("can\'t write BodyPart to stream.", var5);
      }
   }
}
