package myorg.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.util.Arrays;
import myorg.bouncycastle.crypto.digests.SHA1Digest;

public class PKIXCertPathValidatorSpi extends CertPathValidatorSpi {

   private static final byte[][] PUBLIC_KEY_SHA1_BLACKLIST;


   static {
      byte[] var0 = new byte[5];
      byte[] var1 = new byte[]{(byte)65, (byte)15, (byte)54, (byte)54, (byte)50, (byte)88, (byte)243, (byte)11, (byte)52, (byte)125, (byte)18, (byte)206, (byte)72, (byte)99, (byte)228, (byte)51, (byte)67, (byte)120, (byte)6, (byte)168};
      var0[0] = (byte)var1;
      byte[] var2 = new byte[]{(byte)186, (byte)62, (byte)123, (byte)211, (byte)140, (byte)215, (byte)225, (byte)230, (byte)185, (byte)205, (byte)76, (byte)33, (byte)153, (byte)98, (byte)229, (byte)157, (byte)122, (byte)47, (byte)78, (byte)55};
      var0[1] = (byte)var2;
      byte[] var3 = new byte[]{(byte)226, (byte)59, (byte)141, (byte)16, (byte)95, (byte)135, (byte)113, (byte)10, (byte)104, (byte)217, (byte)36, (byte)128, (byte)80, (byte)235, (byte)239, (byte)198, (byte)39, (byte)190, (byte)76, (byte)166};
      var0[2] = (byte)var3;
      byte[] var4 = new byte[]{(byte)123, (byte)46, (byte)22, (byte)188, (byte)57, (byte)188, (byte)215, (byte)43, (byte)69, (byte)110, (byte)159, (byte)5, (byte)93, (byte)29, (byte)230, (byte)21, (byte)183, (byte)73, (byte)69, (byte)219};
      var0[3] = (byte)var4;
      byte[] var5 = new byte[]{(byte)232, (byte)249, (byte)18, (byte)0, (byte)198, (byte)92, (byte)238, (byte)22, (byte)224, (byte)57, (byte)185, (byte)248, (byte)131, (byte)132, (byte)22, (byte)97, (byte)99, (byte)95, (byte)129, (byte)197};
      var0[4] = (byte)var5;
      PUBLIC_KEY_SHA1_BLACKLIST = (byte[][])var0;
   }

   public PKIXCertPathValidatorSpi() {}

   private static boolean isPublicKeyBlackListed(PublicKey var0) {
      byte[] var1 = var0.getEncoded();
      SHA1Digest var2 = new SHA1Digest();
      int var3 = var1.length;
      var2.update(var1, 0, var3);
      byte[] var4 = new byte[var2.getDigestSize()];
      var2.doFinal(var4, 0);
      byte[][] var6 = PUBLIC_KEY_SHA1_BLACKLIST;
      int var7 = var6.length;
      int var8 = 0;

      boolean var10;
      while(true) {
         if(var8 >= var7) {
            var10 = false;
            break;
         }

         byte[] var9 = var6[var8];
         if(Arrays.equals(var4, var9)) {
            var10 = true;
            break;
         }

         ++var8;
      }

      return var10;
   }

   public CertPathValidatorResult engineValidate(CertPath param1, CertPathParameters param2) throws CertPathValidatorException, InvalidAlgorithmParameterException {
      // $FF: Couldn't be decompiled
   }
}
