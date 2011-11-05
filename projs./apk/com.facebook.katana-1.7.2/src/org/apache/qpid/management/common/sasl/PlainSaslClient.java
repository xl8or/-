package org.apache.qpid.management.common.sasl;

import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslException;

public class PlainSaslClient implements SaslClient {

   private static byte SEPARATOR = 0;
   private String authenticationID;
   private String authorizationID;
   private CallbackHandler cbh;
   private boolean completed = 0;
   private byte[] password;


   public PlainSaslClient(String var1, CallbackHandler var2) throws SaslException {
      this.cbh = var2;
      Object[] var3 = this.getUserInfo();
      this.authorizationID = var1;
      String var4 = (String)var3[0];
      this.authenticationID = var4;
      byte[] var5 = (byte[])((byte[])var3[1]);
      this.password = var5;
      if(this.authenticationID == null || this.password == null) {
         throw new SaslException("PLAIN: authenticationID and password must be specified");
      }
   }

   private void clearPassword() {
      if(this.password != null) {
         int var1 = 0;

         while(true) {
            int var2 = this.password.length;
            if(var1 >= var2) {
               this.password = null;
               return;
            }

            this.password[var1] = 0;
            ++var1;
         }
      }
   }

   private Object[] getUserInfo() throws SaslException {
      // $FF: Couldn't be decompiled
   }

   public void dispose() throws SaslException {
      this.clearPassword();
   }

   public byte[] evaluateChallenge(byte[] param1) throws SaslException {
      // $FF: Couldn't be decompiled
   }

   protected void finalize() {
      this.clearPassword();
   }

   public String getMechanismName() {
      return "PLAIN";
   }

   public Object getNegotiatedProperty(String var1) {
      if(this.completed) {
         String var2;
         if(var1.equals("javax.security.sasl.qop")) {
            var2 = "auth";
         } else {
            var2 = null;
         }

         return var2;
      } else {
         throw new IllegalStateException("PLAIN: authentication not completed");
      }
   }

   public boolean hasInitialResponse() {
      return true;
   }

   public boolean isComplete() {
      return this.completed;
   }

   public byte[] unwrap(byte[] var1, int var2, int var3) throws SaslException {
      if(this.completed) {
         throw new IllegalStateException("PLAIN: this mechanism supports neither integrity nor privacy");
      } else {
         throw new IllegalStateException("PLAIN: authentication not completed");
      }
   }

   public byte[] wrap(byte[] var1, int var2, int var3) throws SaslException {
      if(this.completed) {
         throw new IllegalStateException("PLAIN: this mechanism supports neither integrity nor privacy");
      } else {
         throw new IllegalStateException("PLAIN: authentication not completed");
      }
   }
}
