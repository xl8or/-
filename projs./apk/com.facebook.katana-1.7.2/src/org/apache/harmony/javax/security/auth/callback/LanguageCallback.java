package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;
import java.util.Locale;
import org.apache.harmony.javax.security.auth.callback.Callback;

public class LanguageCallback implements Callback, Serializable {

   private static final long serialVersionUID = 2019050433478903213L;
   private Locale locale;


   public LanguageCallback() {}

   public Locale getLocale() {
      return this.locale;
   }

   public void setLocale(Locale var1) {
      this.locale = var1;
   }
}
