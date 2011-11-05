package org.apache.qpid.management.common.sasl;

import java.io.IOException;
import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.NameCallback;
import org.apache.harmony.javax.security.auth.callback.PasswordCallback;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;

public class UserPasswordCallbackHandler implements CallbackHandler {

   private char[] pwchars;
   private String user;


   public UserPasswordCallbackHandler(String var1, String var2) {
      this.user = var1;
      char[] var3 = var2.toCharArray();
      this.pwchars = var3;
   }

   private void clearPassword() {
      if(this.pwchars != null) {
         int var1 = 0;

         while(true) {
            int var2 = this.pwchars.length;
            if(var1 >= var2) {
               this.pwchars = null;
               return;
            }

            this.pwchars[var1] = 0;
            ++var1;
         }
      }
   }

   protected void finalize() {
      this.clearPassword();
   }

   public void handle(Callback[] var1) throws IOException, UnsupportedCallbackException {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         if(var1[var2] instanceof NameCallback) {
            NameCallback var4 = (NameCallback)var1[var2];
            String var5 = this.user;
            var4.setName(var5);
         } else {
            if(!(var1[var2] instanceof PasswordCallback)) {
               Callback var8 = var1[var2];
               throw new UnsupportedCallbackException(var8);
            }

            PasswordCallback var6 = (PasswordCallback)var1[var2];
            char[] var7 = this.pwchars;
            var6.setPassword(var7);
         }

         ++var2;
      }
   }
}
