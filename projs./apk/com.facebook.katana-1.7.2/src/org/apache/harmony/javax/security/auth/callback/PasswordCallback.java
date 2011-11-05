package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.harmony.javax.security.auth.callback.Callback;

public class PasswordCallback implements Callback, Serializable {

   private static final long serialVersionUID = 2267422647454909926L;
   boolean echoOn;
   private char[] inputPassword;
   private String prompt;


   public PasswordCallback(String var1, boolean var2) {
      this.setPrompt(var1);
      this.echoOn = var2;
   }

   private void setPrompt(String var1) throws IllegalArgumentException {
      if(var1 != null && var1.length() != 0) {
         this.prompt = var1;
      } else {
         throw new IllegalArgumentException("auth.14");
      }
   }

   public void clearPassword() {
      if(this.inputPassword != null) {
         Arrays.fill(this.inputPassword, '\u0000');
      }
   }

   public char[] getPassword() {
      char[] var1;
      if(this.inputPassword != null) {
         var1 = new char[this.inputPassword.length];
         char[] var2 = this.inputPassword;
         int var3 = var1.length;
         System.arraycopy(var2, 0, var1, 0, var3);
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getPrompt() {
      return this.prompt;
   }

   public boolean isEchoOn() {
      return this.echoOn;
   }

   public void setPassword(char[] var1) {
      if(var1 == null) {
         this.inputPassword = var1;
      } else {
         char[] var2 = new char[var1.length];
         this.inputPassword = var2;
         char[] var3 = this.inputPassword;
         int var4 = this.inputPassword.length;
         System.arraycopy(var1, 0, var3, 0, var4);
      }
   }
}
