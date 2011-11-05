package myorg.bouncycastle.crypto;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.util.Strings;

public abstract class PBEParametersGenerator {

   protected int iterationCount;
   protected byte[] password;
   protected byte[] salt;


   protected PBEParametersGenerator() {}

   public static byte[] PKCS12PasswordToBytes(char[] var0) {
      byte[] var8;
      if(var0.length > 0) {
         byte[] var1 = new byte[(var0.length + 1) * 2];
         int var2 = 0;

         while(true) {
            int var3 = var0.length;
            if(var2 == var3) {
               var8 = var1;
               break;
            }

            int var4 = var2 * 2;
            byte var5 = (byte)(var0[var2] >>> 8);
            var1[var4] = var5;
            int var6 = var2 * 2 + 1;
            byte var7 = (byte)var0[var2];
            var1[var6] = var7;
            ++var2;
         }
      } else {
         var8 = new byte[0];
      }

      return var8;
   }

   public static byte[] PKCS5PasswordToBytes(char[] var0) {
      byte[] var1 = new byte[var0.length];
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 == var3) {
            return var1;
         }

         byte var4 = (byte)var0[var2];
         var1[var2] = var4;
         ++var2;
      }
   }

   public static byte[] PKCS5PasswordToUTF8Bytes(char[] var0) {
      return Strings.toUTF8ByteArray(var0);
   }

   public abstract CipherParameters generateDerivedMacParameters(int var1);

   public abstract CipherParameters generateDerivedParameters(int var1);

   public abstract CipherParameters generateDerivedParameters(int var1, int var2);

   public int getIterationCount() {
      return this.iterationCount;
   }

   public byte[] getPassword() {
      return this.password;
   }

   public byte[] getSalt() {
      return this.salt;
   }

   public void init(byte[] var1, byte[] var2, int var3) {
      this.password = var1;
      this.salt = var2;
      this.iterationCount = var3;
   }
}
