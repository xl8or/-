package myorg.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.MacSpi;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.digests.MD2Digest;
import myorg.bouncycastle.crypto.digests.MD4Digest;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD128Digest;
import myorg.bouncycastle.crypto.digests.RIPEMD160Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.digests.SHA224Digest;
import myorg.bouncycastle.crypto.digests.SHA256Digest;
import myorg.bouncycastle.crypto.digests.SHA384Digest;
import myorg.bouncycastle.crypto.digests.SHA512Digest;
import myorg.bouncycastle.crypto.digests.TigerDigest;
import myorg.bouncycastle.crypto.engines.DESEngine;
import myorg.bouncycastle.crypto.engines.DESedeEngine;
import myorg.bouncycastle.crypto.engines.RC2Engine;
import myorg.bouncycastle.crypto.engines.RC532Engine;
import myorg.bouncycastle.crypto.engines.SkipjackEngine;
import myorg.bouncycastle.crypto.macs.CBCBlockCipherMac;
import myorg.bouncycastle.crypto.macs.CFBBlockCipherMac;
import myorg.bouncycastle.crypto.macs.GOST28147Mac;
import myorg.bouncycastle.crypto.macs.HMac;
import myorg.bouncycastle.crypto.macs.ISO9797Alg3Mac;
import myorg.bouncycastle.crypto.macs.OldHMac;
import myorg.bouncycastle.crypto.macs.VMPCMac;
import myorg.bouncycastle.crypto.paddings.ISO7816d4Padding;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.jce.provider.JCEPBEKey;
import myorg.bouncycastle.jce.provider.PBE;

public class JCEMac extends MacSpi implements PBE {

   private int keySize = 160;
   private Mac macEngine;
   private int pbeHash = 1;
   private int pbeType = 2;


   protected JCEMac(Mac var1) {
      this.macEngine = var1;
   }

   protected JCEMac(Mac var1, int var2, int var3, int var4) {
      this.macEngine = var1;
      this.pbeType = var2;
      this.pbeHash = var3;
      this.keySize = var4;
   }

   protected byte[] engineDoFinal() {
      byte[] var1 = new byte[this.engineGetMacLength()];
      this.macEngine.doFinal(var1, 0);
      return var1;
   }

   protected int engineGetMacLength() {
      return this.macEngine.getMacSize();
   }

   protected void engineInit(Key var1, AlgorithmParameterSpec var2) throws InvalidKeyException, InvalidAlgorithmParameterException {
      if(var1 == null) {
         throw new InvalidKeyException("key is null");
      } else {
         Object var4;
         if(var1 instanceof JCEPBEKey) {
            JCEPBEKey var3 = (JCEPBEKey)var1;
            if(var3.getParam() != null) {
               var4 = var3.getParam();
            } else {
               if(!(var2 instanceof PBEParameterSpec)) {
                  throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
               }

               var4 = PBE.Util.makePBEMacParameters(var3, var2);
            }
         } else if(var2 instanceof IvParameterSpec) {
            byte[] var5 = var1.getEncoded();
            KeyParameter var6 = new KeyParameter(var5);
            byte[] var7 = ((IvParameterSpec)var2).getIV();
            var4 = new ParametersWithIV(var6, var7);
         } else {
            if(var2 != null) {
               throw new InvalidAlgorithmParameterException("unknown parameter type.");
            }

            byte[] var8 = var1.getEncoded();
            var4 = new KeyParameter(var8);
         }

         this.macEngine.init((CipherParameters)var4);
      }
   }

   protected void engineReset() {
      this.macEngine.reset();
   }

   protected void engineUpdate(byte var1) {
      this.macEngine.update(var1);
   }

   protected void engineUpdate(byte[] var1, int var2, int var3) {
      this.macEngine.update(var1, var2, var3);
   }

   public static class OldSHA512 extends JCEMac {

      public OldSHA512() {
         SHA512Digest var1 = new SHA512Digest();
         OldHMac var2 = new OldHMac(var1);
         super(var2);
      }
   }

   public static class DESede64with7816d4 extends JCEMac {

      public DESede64with7816d4() {
         DESedeEngine var1 = new DESedeEngine();
         ISO7816d4Padding var2 = new ISO7816d4Padding();
         CBCBlockCipherMac var3 = new CBCBlockCipherMac(var1, 64, var2);
         super(var3);
      }
   }

   public static class OldSHA384 extends JCEMac {

      public OldSHA384() {
         SHA384Digest var1 = new SHA384Digest();
         OldHMac var2 = new OldHMac(var1);
         super(var2);
      }
   }

   public static class Skipjack extends JCEMac {

      public Skipjack() {
         SkipjackEngine var1 = new SkipjackEngine();
         CBCBlockCipherMac var2 = new CBCBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class DES extends JCEMac {

      public DES() {
         DESEngine var1 = new DESEngine();
         CBCBlockCipherMac var2 = new CBCBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class DES9797Alg3 extends JCEMac {

      public DES9797Alg3() {
         DESEngine var1 = new DESEngine();
         ISO9797Alg3Mac var2 = new ISO9797Alg3Mac(var1);
         super(var2);
      }
   }

   public static class PBEWithRIPEMD160 extends JCEMac {

      public PBEWithRIPEMD160() {
         RIPEMD160Digest var1 = new RIPEMD160Digest();
         HMac var2 = new HMac(var1);
         super(var2, 2, 2, 160);
      }
   }

   public static class RC2 extends JCEMac {

      public RC2() {
         RC2Engine var1 = new RC2Engine();
         CBCBlockCipherMac var2 = new CBCBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class SHA224 extends JCEMac {

      public SHA224() {
         SHA224Digest var1 = new SHA224Digest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class Tiger extends JCEMac {

      public Tiger() {
         TigerDigest var1 = new TigerDigest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class RC2CFB8 extends JCEMac {

      public RC2CFB8() {
         RC2Engine var1 = new RC2Engine();
         CFBBlockCipherMac var2 = new CFBBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class RIPEMD160 extends JCEMac {

      public RIPEMD160() {
         RIPEMD160Digest var1 = new RIPEMD160Digest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class DESedeCFB8 extends JCEMac {

      public DESedeCFB8() {
         DESedeEngine var1 = new DESedeEngine();
         CFBBlockCipherMac var2 = new CFBBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class DESede extends JCEMac {

      public DESede() {
         DESedeEngine var1 = new DESedeEngine();
         CBCBlockCipherMac var2 = new CBCBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class SHA1 extends JCEMac {

      public SHA1() {
         SHA1Digest var1 = new SHA1Digest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class RC5 extends JCEMac {

      public RC5() {
         RC532Engine var1 = new RC532Engine();
         CBCBlockCipherMac var2 = new CBCBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class DESCFB8 extends JCEMac {

      public DESCFB8() {
         DESEngine var1 = new DESEngine();
         CFBBlockCipherMac var2 = new CFBBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class SHA384 extends JCEMac {

      public SHA384() {
         SHA384Digest var1 = new SHA384Digest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class PBEWithSHA extends JCEMac {

      public PBEWithSHA() {
         SHA1Digest var1 = new SHA1Digest();
         HMac var2 = new HMac(var1);
         super(var2, 2, 1, 160);
      }
   }

   public static class VMPC extends JCEMac {

      public VMPC() {
         VMPCMac var1 = new VMPCMac();
         super(var1);
      }
   }

   public static class DES9797Alg3with7816d4 extends JCEMac {

      public DES9797Alg3with7816d4() {
         DESEngine var1 = new DESEngine();
         ISO7816d4Padding var2 = new ISO7816d4Padding();
         ISO9797Alg3Mac var3 = new ISO9797Alg3Mac(var1, var2);
         super(var3);
      }
   }

   public static class DESede64 extends JCEMac {

      public DESede64() {
         DESedeEngine var1 = new DESedeEngine();
         CBCBlockCipherMac var2 = new CBCBlockCipherMac(var1, 64);
         super(var2);
      }
   }

   public static class SHA256 extends JCEMac {

      public SHA256() {
         SHA256Digest var1 = new SHA256Digest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class GOST28147 extends JCEMac {

      public GOST28147() {
         GOST28147Mac var1 = new GOST28147Mac();
         super(var1);
      }
   }

   public static class MD5 extends JCEMac {

      public MD5() {
         MD5Digest var1 = new MD5Digest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class MD4 extends JCEMac {

      public MD4() {
         MD4Digest var1 = new MD4Digest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class MD2 extends JCEMac {

      public MD2() {
         MD2Digest var1 = new MD2Digest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class RIPEMD128 extends JCEMac {

      public RIPEMD128() {
         RIPEMD128Digest var1 = new RIPEMD128Digest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class PBEWithTiger extends JCEMac {

      public PBEWithTiger() {
         TigerDigest var1 = new TigerDigest();
         HMac var2 = new HMac(var1);
         super(var2, 2, 3, 192);
      }
   }

   public static class SkipjackCFB8 extends JCEMac {

      public SkipjackCFB8() {
         SkipjackEngine var1 = new SkipjackEngine();
         CFBBlockCipherMac var2 = new CFBBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class SHA512 extends JCEMac {

      public SHA512() {
         SHA512Digest var1 = new SHA512Digest();
         HMac var2 = new HMac(var1);
         super(var2);
      }
   }

   public static class RC5CFB8 extends JCEMac {

      public RC5CFB8() {
         RC532Engine var1 = new RC532Engine();
         CFBBlockCipherMac var2 = new CFBBlockCipherMac(var1);
         super(var2);
      }
   }
}
