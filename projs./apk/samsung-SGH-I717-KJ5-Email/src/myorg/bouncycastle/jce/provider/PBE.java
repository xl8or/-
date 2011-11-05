package myorg.bouncycastle.jce.provider;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.PBEParametersGenerator;
import myorg.bouncycastle.crypto.digests.MD2Digest;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD160Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.digests.SHA256Digest;
import myorg.bouncycastle.crypto.digests.TigerDigest;
import myorg.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import myorg.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import myorg.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator;
import myorg.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import myorg.bouncycastle.crypto.params.DESParameters;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.jce.provider.JCEPBEKey;

public interface PBE {

   int MD2 = 5;
   int MD5 = 0;
   int OPENSSL = 3;
   int PKCS12 = 2;
   int PKCS5S1 = 0;
   int PKCS5S2 = 1;
   int RIPEMD160 = 2;
   int SHA1 = 1;
   int SHA256 = 4;
   int TIGER = 3;


   public static class Util {

      public Util() {}

      private static PBEParametersGenerator makePBEGenerator(int var0, int var1) {
         Object var3;
         if(var0 == 0) {
            switch(var1) {
            case 0:
               MD5Digest var4 = new MD5Digest();
               var3 = new PKCS5S1ParametersGenerator(var4);
               break;
            case 1:
               SHA1Digest var5 = new SHA1Digest();
               var3 = new PKCS5S1ParametersGenerator(var5);
               break;
            case 2:
            case 3:
            case 4:
            default:
               throw new IllegalStateException("PKCS5 scheme 1 only supports MD2, MD5 and SHA1.");
            case 5:
               MD2Digest var2 = new MD2Digest();
               var3 = new PKCS5S1ParametersGenerator(var2);
            }
         } else if(var0 == 1) {
            var3 = new PKCS5S2ParametersGenerator();
         } else if(var0 == 2) {
            switch(var1) {
            case 0:
               MD5Digest var7 = new MD5Digest();
               var3 = new PKCS12ParametersGenerator(var7);
               break;
            case 1:
               SHA1Digest var8 = new SHA1Digest();
               var3 = new PKCS12ParametersGenerator(var8);
               break;
            case 2:
               RIPEMD160Digest var9 = new RIPEMD160Digest();
               var3 = new PKCS12ParametersGenerator(var9);
               break;
            case 3:
               TigerDigest var10 = new TigerDigest();
               var3 = new PKCS12ParametersGenerator(var10);
               break;
            case 4:
               SHA256Digest var11 = new SHA256Digest();
               var3 = new PKCS12ParametersGenerator(var11);
               break;
            case 5:
               MD2Digest var6 = new MD2Digest();
               var3 = new PKCS12ParametersGenerator(var6);
               break;
            default:
               throw new IllegalStateException("unknown digest scheme for PBE encryption.");
            }
         } else {
            var3 = new OpenSSLPBEParametersGenerator();
         }

         return (PBEParametersGenerator)var3;
      }

      static CipherParameters makePBEMacParameters(PBEKeySpec var0, int var1, int var2, int var3) {
         PBEParametersGenerator var4 = makePBEGenerator(var1, var2);
         byte[] var5;
         if(var1 == 2) {
            var5 = PBEParametersGenerator.PKCS12PasswordToBytes(var0.getPassword());
         } else {
            var5 = PBEParametersGenerator.PKCS5PasswordToBytes(var0.getPassword());
         }

         byte[] var6 = var0.getSalt();
         int var7 = var0.getIterationCount();
         var4.init(var5, var6, var7);
         CipherParameters var8 = var4.generateDerivedMacParameters(var3);
         int var9 = 0;

         while(true) {
            int var10 = var5.length;
            if(var9 == var10) {
               return var8;
            }

            var5[var9] = 0;
            ++var9;
         }
      }

      static CipherParameters makePBEMacParameters(JCEPBEKey var0, AlgorithmParameterSpec var1) {
         if(var1 != null && var1 instanceof PBEParameterSpec) {
            PBEParameterSpec var2 = (PBEParameterSpec)var1;
            int var3 = var0.getType();
            int var4 = var0.getDigest();
            PBEParametersGenerator var5 = makePBEGenerator(var3, var4);
            byte[] var6 = var0.getEncoded();
            if(var0.shouldTryWrongPKCS12()) {
               var6 = new byte[2];
            }

            byte[] var7 = var2.getSalt();
            int var8 = var2.getIterationCount();
            var5.init(var6, var7, var8);
            int var9 = var0.getKeySize();
            CipherParameters var10 = var5.generateDerivedMacParameters(var9);
            int var11 = 0;

            while(true) {
               int var12 = var6.length;
               if(var11 == var12) {
                  return var10;
               }

               var6[var11] = 0;
               ++var11;
            }
         } else {
            throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
         }
      }

      static CipherParameters makePBEParameters(PBEKeySpec var0, int var1, int var2, int var3, int var4) {
         PBEParametersGenerator var5 = makePBEGenerator(var1, var2);
         byte[] var6;
         if(var1 == 2) {
            var6 = PBEParametersGenerator.PKCS12PasswordToBytes(var0.getPassword());
         } else {
            var6 = PBEParametersGenerator.PKCS5PasswordToBytes(var0.getPassword());
         }

         byte[] var7 = var0.getSalt();
         int var8 = var0.getIterationCount();
         var5.init(var6, var7, var8);
         CipherParameters var9;
         if(var4 != 0) {
            var9 = var5.generateDerivedParameters(var3, var4);
         } else {
            var9 = var5.generateDerivedParameters(var3);
         }

         int var10 = 0;

         while(true) {
            int var11 = var6.length;
            if(var10 == var11) {
               return var9;
            }

            var6[var10] = 0;
            ++var10;
         }
      }

      static CipherParameters makePBEParameters(JCEPBEKey var0, AlgorithmParameterSpec var1, String var2) {
         if(var1 != null && var1 instanceof PBEParameterSpec) {
            PBEParameterSpec var3 = (PBEParameterSpec)var1;
            int var4 = var0.getType();
            int var5 = var0.getDigest();
            PBEParametersGenerator var6 = makePBEGenerator(var4, var5);
            byte[] var7 = var0.getEncoded();
            if(var0.shouldTryWrongPKCS12()) {
               var7 = new byte[2];
            }

            byte[] var8 = var3.getSalt();
            int var9 = var3.getIterationCount();
            var6.init(var7, var8, var9);
            CipherParameters var12;
            if(var0.getIvSize() != 0) {
               int var10 = var0.getKeySize();
               int var11 = var0.getIvSize();
               var12 = var6.generateDerivedParameters(var10, var11);
            } else {
               int var15 = var0.getKeySize();
               var12 = var6.generateDerivedParameters(var15);
            }

            if(var2.startsWith("DES")) {
               if(var12 instanceof ParametersWithIV) {
                  DESParameters.setOddParity(((KeyParameter)((ParametersWithIV)var12).getParameters()).getKey());
               } else {
                  DESParameters.setOddParity(((KeyParameter)var12).getKey());
               }
            }

            int var13 = 0;

            while(true) {
               int var14 = var7.length;
               if(var13 == var14) {
                  return var12;
               }

               var7[var13] = 0;
               ++var13;
            }
         } else {
            throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
         }
      }
   }
}
