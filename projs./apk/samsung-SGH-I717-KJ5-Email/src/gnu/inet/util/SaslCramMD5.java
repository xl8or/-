package gnu.inet.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;

public class SaslCramMD5 implements SaslClient {

   private boolean complete;
   private String password;
   private String username;


   public SaslCramMD5(String var1, String var2) {
      this.username = var1;
      this.password = var2;
   }

   private static byte[] hmac_md5(byte[] var0, byte[] var1) throws NoSuchAlgorithmException {
      byte[] var2 = new byte[64];
      byte[] var3 = new byte[64];
      MessageDigest var4 = MessageDigest.getInstance("MD5");
      byte[] var5;
      if(var0.length > 64) {
         var4.update(var0);
         var5 = var4.digest();
      } else {
         var5 = var0;
      }

      int var6 = var5.length;
      System.arraycopy(var5, 0, var2, 0, var6);
      int var7 = var5.length;
      System.arraycopy(var5, 0, var3, 0, var7);

      int var11;
      for(byte var8 = 0; var8 < 64; var11 = var8 + 1) {
         byte var9 = (byte)(var2[var8] ^ 54);
         var2[var8] = var9;
         byte var10 = (byte)(var3[var8] ^ 92);
         var3[var8] = var10;
      }

      var4.reset();
      var4.update(var2);
      var4.update(var1);
      byte[] var12 = var4.digest();
      var4.reset();
      var4.update(var3);
      var4.update(var12);
      return var4.digest();
   }

   public void dispose() {}

   public byte[] evaluateChallenge(byte[] var1) throws SaslException {
      try {
         byte[] var2 = hmac_md5(this.password.getBytes("US-ASCII"), var1);
         byte[] var3 = this.username.getBytes("US-ASCII");
         int var4 = var3.length;
         int var5 = var2.length;
         byte[] var6 = new byte[var4 + var5 + 1];
         int var7 = var3.length;
         System.arraycopy(var3, 0, var6, 0, var7);
         int var8 = var3.length;
         var6[var8] = 32;
         int var9 = var3.length + 1;
         int var10 = var2.length;
         System.arraycopy(var2, 0, var6, var9, var10);
         this.complete = (boolean)1;
         return var6;
      } catch (UnsupportedEncodingException var13) {
         throw new SaslException("Username or password contains non-ASCII characters", var13);
      } catch (NoSuchAlgorithmException var14) {
         throw new SaslException("MD5 algorithm not available", var14);
      }
   }

   public String getMechanismName() {
      return "CRAM-MD5";
   }

   public Object getNegotiatedProperty(String var1) {
      return null;
   }

   public boolean hasInitialResponse() {
      return false;
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
