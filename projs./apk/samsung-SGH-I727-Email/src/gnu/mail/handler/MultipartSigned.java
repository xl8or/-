package gnu.mail.handler;

import gnu.mail.handler.Multipart;

public final class MultipartSigned extends Multipart {

   public MultipartSigned() {
      super("multipart/signed", "multipart");
   }
}
