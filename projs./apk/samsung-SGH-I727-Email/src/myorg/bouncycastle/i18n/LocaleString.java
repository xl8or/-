package myorg.bouncycastle.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.TimeZone;
import myorg.bouncycastle.i18n.LocalizedMessage;

public class LocaleString extends LocalizedMessage {

   public LocaleString(String var1, String var2) {
      super(var1, var2);
   }

   public LocaleString(String var1, String var2, String var3) throws NullPointerException, UnsupportedEncodingException {
      super(var1, var2, var3);
   }

   public String getLocaleString(Locale var1) {
      return this.getEntry((String)null, var1, (TimeZone)null);
   }
}
