package myorg.bouncycastle.openssl;

import java.io.IOException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import myorg.bouncycastle.crypto.PBEParametersGenerator;
import myorg.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import myorg.bouncycastle.crypto.params.KeyParameter;

final class PEMUtilities {

   PEMUtilities() {}

   static byte[] crypt(boolean param0, String param1, byte[] param2, char[] param3, String param4, byte[] param5) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static SecretKey getKey(char[] var0, String var1, int var2, byte[] var3) {
      return getKey(var0, var1, var2, var3, (boolean)0);
   }

   private static SecretKey getKey(char[] var0, String var1, int var2, byte[] var3, boolean var4) {
      OpenSSLPBEParametersGenerator var5 = new OpenSSLPBEParametersGenerator();
      byte[] var6 = PBEParametersGenerator.PKCS5PasswordToBytes(var0);
      var5.init(var6, var3);
      int var7 = var2 * 8;
      byte[] var8 = ((KeyParameter)var5.generateDerivedParameters(var7)).getKey();
      if(var4 && var8.length >= 24) {
         System.arraycopy(var8, 0, var8, 16, 8);
      }

      return new SecretKeySpec(var8, var1);
   }
}
