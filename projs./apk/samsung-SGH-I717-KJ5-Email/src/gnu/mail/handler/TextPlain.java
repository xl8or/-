package gnu.mail.handler;

import gnu.mail.handler.Text;

public final class TextPlain extends Text {

   public TextPlain() {
      super("text/plain", "plaintext");
   }
}
