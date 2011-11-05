package gnu.mail.handler;

import gnu.mail.handler.Multipart;

public final class MultipartAlternative extends Multipart {

   public MultipartAlternative() {
      super("multipart/alternative", "multipart");
   }
}
