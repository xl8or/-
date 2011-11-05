package myorg.bouncycastle.jce.provider;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.PBEParametersGenerator;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD160Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import myorg.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator;
import myorg.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.jce.provider.JCEPBEKey;
import myorg.bouncycastle.jce.provider.OldPKCS12ParametersGenerator;

public interface BrokenPBE {

   int MD5 = 0;
   int OLD_PKCS12 = 3;
   int PKCS12 = 2;
   int PKCS5S1 = 0;
   int PKCS5S2 = 1;
   int RIPEMD160 = 2;
   int SHA1 = 1;


   public static class Util {

      public Util() {}

      private static PBEParametersGenerator makePBEGenerator(int var0, int var1) {
         Object var3;
         if(var0 == 0) {
            switch(var1) {
            case 0:
               MD5Digest var2 = new MD5Digest();
               var3 = new PKCS5S1ParametersGenerator(var2);
               break;
            case 1:
               SHA1Digest var4 = new SHA1Digest();
               var3 = new PKCS5S1ParametersGenerator(var4);
               break;
            default:
               throw new IllegalStateException("PKCS5 scheme 1 only supports only MD5 and SHA1.");
            }
         } else if(var0 == 1) {
            var3 = new PKCS5S2ParametersGenerator();
         } else if(var0 == 3) {
            switch(var1) {
            case 0:
               MD5Digest var5 = new MD5Digest();
               var3 = new OldPKCS12ParametersGenerator(var5);
               break;
            case 1:
               SHA1Digest var6 = new SHA1Digest();
               var3 = new OldPKCS12ParametersGenerator(var6);
               break;
            case 2:
               RIPEMD160Digest var7 = new RIPEMD160Digest();
               var3 = new OldPKCS12ParametersGenerator(var7);
               break;
            default:
               throw new IllegalStateException("unknown digest scheme for PBE encryption.");
            }
         } else {
            switch(var1) {
            case 0:
               MD5Digest var8 = new MD5Digest();
               var3 = new PKCS12ParametersGenerator(var8);
               break;
            case 1:
               SHA1Digest var9 = new SHA1Digest();
               var3 = new PKCS12ParametersGenerator(var9);
               break;
            case 2:
               RIPEMD160Digest var10 = new RIPEMD160Digest();
               var3 = new PKCS12ParametersGenerator(var10);
               break;
            default:
               throw new IllegalStateException("unknown digest scheme for PBE encryption.");
            }
         }

         return (PBEParametersGenerator)var3;
      }

      static CipherParameters makePBEMacParameters(JCEPBEKey var0, AlgorithmParameterSpec var1, int var2, int var3, int var4) {
         if(var1 != null && var1 instanceof PBEParameterSpec) {
            PBEParameterSpec var5 = (PBEParameterSpec)var1;
            PBEParametersGenerator var6 = makePBEGenerator(var2, var3);
            byte[] var7 = var0.getEncoded();
            byte[] var8 = var5.getSalt();
            int var9 = var5.getIterationCount();
            var6.init(var7, var8, var9);
            CipherParameters var10 = var6.generateDerivedMacParameters(var4);
            int var11 = 0;

            while(true) {
               int var12 = var7.length;
               if(var11 == var12) {
                  return var10;
               }

               var7[var11] = 0;
               ++var11;
            }
         } else {
            throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
         }
      }

      static CipherParameters makePBEParameters(JCEPBEKey var0, AlgorithmParameterSpec var1, int var2, int var3, String var4, int var5, int var6) {
         if(var1 != null && var1 instanceof PBEParameterSpec) {
            PBEParameterSpec var7 = (PBEParameterSpec)var1;
            PBEParametersGenerator var8 = makePBEGenerator(var2, var3);
            byte[] var9 = var0.getEncoded();
            byte[] var10 = var7.getSalt();
            int var11 = var7.getIterationCount();
            var8.init(var9, var10, var11);
            CipherParameters var12;
            if(var6 != 0) {
               var12 = var8.generateDerivedParameters(var5, var6);
            } else {
               var12 = var8.generateDerivedParameters(var5);
            }

            if(var4.startsWith("DES")) {
               if(var12 instanceof ParametersWithIV) {
                  setOddParity(((KeyParameter)((ParametersWithIV)var12).getParameters()).getKey());
               } else {
                  setOddParity(((KeyParameter)var12).getKey());
               }
            }

            int var13 = 0;

            while(true) {
               int var14 = var9.length;
               if(var13 == var14) {
                  return var12;
               }

               var9[var13] = 0;
               ++var13;
            }
         } else {
            throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
         }
      }

      private static void setOddParity(byte[] var0) {
         int var1 = 0;

         while(true) {
            int var2 = var0.length;
            if(var1 >= var2) {
               return;
            }

            byte var3 = var0[var1];
            int var4 = var3 & 254;
            int var5 = var3 >> 1;
            int var6 = var3 >> 2;
            int var7 = var5 ^ var6;
            int var8 = var3 >> 3;
            int var9 = var7 ^ var8;
            int var10 = var3 >> 4;
            int var11 = var9 ^ var10;
            int var12 = var3 >> 5;
            int var13 = var11 ^ var12;
            int var14 = var3 >> 6;
            int var15 = var13 ^ var14;
            int var16 = var3 >> 7;
            int var17 = var15 ^ var16 ^ 1;
            byte var18 = (byte)(var4 | var17);
            var0[var1] = var18;
            ++var1;
         }
      }
   }
}
