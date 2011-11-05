package org.apache.qpid.management.common.sasl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.NameCallback;
import org.apache.harmony.javax.security.auth.callback.PasswordCallback;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;

public class UsernameHashedPasswordCallbackHandler implements CallbackHandler {

   private char[] pwchars;
   private String user;


   public UsernameHashedPasswordCallbackHandler(String var1, String var2) throws Exception {
      this.user = var1;
      char[] var3 = getHash(var2);
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

   public static char[] getHash(String var0) throws NoSuchAlgorithmException, UnsupportedEncodingException {
      byte[] var1 = var0.getBytes("utf-8");
      MessageDigest var2 = MessageDigest.getInstance("MD5");
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte var5 = var1[var4];
         var2.update(var5);
      }

      byte[] var6 = var2.digest();
      char[] var7 = new char[var6.length];
      int var8 = var6.length;
      int var9 = 0;

      int var12;
      for(int var10 = 0; var9 < var8; var10 = var12) {
         byte var11 = var6[var9];
         var12 = var10 + 1;
         char var13 = (char)var11;
         var7[var10] = var13;
         ++var9;
      }

      return var7;
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
