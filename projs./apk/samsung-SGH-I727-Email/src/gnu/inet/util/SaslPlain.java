package gnu.inet.util;

import java.io.UnsupportedEncodingException;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;

public class SaslPlain implements SaslClient {

   private boolean complete;
   private String password;
   private String username;


   public SaslPlain(String var1, String var2) {
      this.username = var1;
      this.password = var2;
   }

   public void dispose() {}

   public byte[] evaluateChallenge(byte[] var1) throws SaslException {
      try {
         byte[] var2 = this.username.getBytes("UTF-8");
         byte[] var3 = this.password.getBytes("UTF-8");
         int var4 = var2.length * 2;
         int var5 = var3.length;
         byte[] var6 = new byte[var4 + var5 + 2];
         int var7 = var2.length;
         System.arraycopy(var2, 0, var6, 0, var7);
         int var8 = var2.length + 1;
         int var9 = var2.length;
         System.arraycopy(var2, 0, var6, var8, var9);
         int var10 = var2.length * 2 + 2;
         int var11 = var3.length;
         System.arraycopy(var3, 0, var6, var10, var11);
         this.complete = (boolean)1;
         return var6;
      } catch (UnsupportedEncodingException var13) {
         throw new SaslException("Username or password contains illegal UTF-8", var13);
      }
   }

   public String getMechanismName() {
      return "PLAIN";
   }

   public Object getNegotiatedProperty(String var1) {
      return null;
   }

   public boolean hasInitialResponse() {
      return true;
   }

   public boolean isComplete() {
      return this.complete;
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
