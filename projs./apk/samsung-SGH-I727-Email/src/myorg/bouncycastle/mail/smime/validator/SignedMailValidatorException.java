package myorg.bouncycastle.mail.smime.validator;

import myorg.bouncycastle.i18n.ErrorBundle;
import myorg.bouncycastle.i18n.LocalizedException;

public class SignedMailValidatorException extends LocalizedException {

   public SignedMailValidatorException(ErrorBundle var1) {
      super(var1);
   }

   public SignedMailValidatorException(ErrorBundle var1, Throwable var2) {
      super(var1, var2);
   }
}
