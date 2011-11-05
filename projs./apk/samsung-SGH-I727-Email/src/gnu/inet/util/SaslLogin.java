package gnu.inet.util;

import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;

public class SaslLogin implements SaslClient {

   private static final int STATE_COMPLETE = 2;
   private static final int STATE_PASSWORD = 1;
   private static final int STATE_USERNAME;
   private String password;
   private int state;
   private String username;


   public SaslLogin(String var1, String var2) {
      this.username = var1;
      this.password = var2;
      this.state = 0;
   }

   public void dispose() {}

   public byte[] evaluateChallenge(byte[] param1) throws SaslException {
      // $FF: Couldn't be decompiled
   }

   public String getMechanismName() {
      return "LOGIN";
   }

   public Object getNegotiatedProperty(String var1) {
      return null;
   }

   public boolean hasInitialResponse() {
      return false;
   }

   public boolean isComplete() {
      boolean var1;
      if(this.state == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public byte[] unwrap(byte[] var1, int var2, int var3) throws SaslException {
      byte[] var4 = new byte[var3 - var2];
      System.arraycopy(var1, var2, var4, 0, var3);
      return var4;
   }

   public byte[] wrap(byte[] var1, int var2, int var3) throws SaslException {
      byte[] var4 = new byte[var3 - var2];
      System.arraycopy(var1, var2, var4, 0, var3);
      return var4;
   }
}
