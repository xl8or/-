package org.apache.harmony.javax.security.sasl;

import org.apache.harmony.javax.security.auth.callback.TextInputCallback;

public class RealmCallback extends TextInputCallback {

   private static final long serialVersionUID = -4342673378785456908L;


   public RealmCallback(String var1) {
      super(var1);
   }

   public RealmCallback(String var1, String var2) {
      super(var1, var2);
   }
}
