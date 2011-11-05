package myorg.bouncycastle.i18n;

import java.util.Locale;
import myorg.bouncycastle.i18n.ErrorBundle;

public class LocalizedException extends Exception {

   private Throwable cause;
   protected ErrorBundle message;


   public LocalizedException(ErrorBundle var1) {
      Locale var2 = Locale.getDefault();
      String var3 = var1.getText(var2);
      super(var3);
      this.message = var1;
   }

   public LocalizedException(ErrorBundle var1, Throwable var2) {
      Locale var3 = Locale.getDefault();
      String var4 = var1.getText(var3);
      super(var4);
      this.message = var1;
      this.cause = var2;
   }

   public Throwable getCause() {
      return this.cause;
   }

   public ErrorBundle getErrorMessage() {
      return this.message;
   }
}
