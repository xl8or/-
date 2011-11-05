package org.apache.harmony.javax.security.auth.callback;

import org.apache.harmony.javax.security.auth.callback.Callback;

public class UnsupportedCallbackException extends Exception {

   private static final long serialVersionUID = -6873556327655666839L;
   private Callback callback;


   public UnsupportedCallbackException(Callback var1) {
      this.callback = var1;
   }

   public UnsupportedCallbackException(Callback var1, String var2) {
      super(var2);
      this.callback = var1;
   }

   public Callback getCallback() {
      return this.callback;
   }
}
