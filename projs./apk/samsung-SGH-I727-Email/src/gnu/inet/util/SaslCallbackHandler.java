package gnu.inet.util;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public final class SaslCallbackHandler implements CallbackHandler {

   private final String password;
   private final String username;


   public SaslCallbackHandler(String var1, String var2) {
      this.username = var1;
      this.password = var2;
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
            String var5 = this.username;
            var4.setName(var5);
         } else {
            if(!(var1[var2] instanceof PasswordCallback)) {
               Callback var8 = var1[var2];
               throw new UnsupportedCallbackException(var8);
            }

            PasswordCallback var6 = (PasswordCallback)var1[var2];
            char[] var7 = this.password.toCharArray();
            var6.setPassword(var7);
         }

         ++var2;
      }
   }
}
