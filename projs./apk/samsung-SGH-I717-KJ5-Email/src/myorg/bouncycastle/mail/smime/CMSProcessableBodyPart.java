package myorg.bouncycastle.mail.smime;

import java.io.IOException;
import java.io.OutputStream;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;

public class CMSProcessableBodyPart implements CMSProcessable {

   private BodyPart bodyPart;


   public CMSProcessableBodyPart(BodyPart var1) {
      this.bodyPart = var1;
   }

   public Object getContent() {
      return this.bodyPart;
   }

   public void write(OutputStream var1) throws IOException, CMSException {
      try {
         this.bodyPart.writeTo(var1);
      } catch (MessagingException var3) {
         throw new CMSException("can\'t write BodyPart to stream.", var3);
      }
   }
}
