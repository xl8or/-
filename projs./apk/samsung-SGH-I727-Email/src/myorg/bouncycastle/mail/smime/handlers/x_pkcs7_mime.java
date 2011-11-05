package myorg.bouncycastle.mail.smime.handlers;

import javax.activation.ActivationDataFlavor;
import javax.mail.internet.MimeBodyPart;
import myjava.awt.datatransfer.DataFlavor;
import myorg.bouncycastle.mail.smime.handlers.PKCS7ContentHandler;

public class x_pkcs7_mime extends PKCS7ContentHandler {

   private static final ActivationDataFlavor ADF = new ActivationDataFlavor(MimeBodyPart.class, "application/x-pkcs7-mime", "Encrypted Data");
   private static final DataFlavor[] DFS;


   static {
      DataFlavor[] var0 = new DataFlavor[1];
      ActivationDataFlavor var1 = ADF;
      var0[0] = var1;
      DFS = var0;
   }

   public x_pkcs7_mime() {
      ActivationDataFlavor var1 = ADF;
      DataFlavor[] var2 = DFS;
      super(var1, var2);
   }
}
