package myorg.bouncycastle.jce.provider.symmetric;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.misc.IDEACBCPar;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherKeyGenerator;
import myorg.bouncycastle.crypto.engines.IDEAEngine;
import myorg.bouncycastle.crypto.macs.CBCBlockCipherMac;
import myorg.bouncycastle.crypto.macs.CFBBlockCipherMac;
import myorg.bouncycastle.crypto.modes.CBCBlockCipher;
import myorg.bouncycastle.jce.provider.JCEBlockCipher;
import myorg.bouncycastle.jce.provider.JCEKeyGenerator;
import myorg.bouncycastle.jce.provider.JCEMac;
import myorg.bouncycastle.jce.provider.JCESecretKeyFactory;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameterGenerator;
import myorg.bouncycastle.jce.provider.JDKAlgorithmParameters;

public final class IDEA {

   private IDEA() {}

   public static class CFB8Mac extends JCEMac {

      public CFB8Mac() {
         IDEAEngine var1 = new IDEAEngine();
         CFBBlockCipherMac var2 = new CFBBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class CBC extends JCEBlockCipher {

      public CBC() {
         IDEAEngine var1 = new IDEAEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super((BlockCipher)var2, 64);
      }
   }

   public static class PBEWithSHAAndIDEA extends JCEBlockCipher {

      public PBEWithSHAAndIDEA() {
         IDEAEngine var1 = new IDEAEngine();
         CBCBlockCipher var2 = new CBCBlockCipher(var1);
         super(var2);
      }
   }

   public static class KeyGen extends JCEKeyGenerator {

      public KeyGen() {
         CipherKeyGenerator var1 = new CipherKeyGenerator();
         super("IDEA", 128, var1);
      }
   }

   public static class AlgParamGen extends JDKAlgorithmParameterGenerator {

      public AlgParamGen() {}

      protected AlgorithmParameters engineGenerateParameters() {
         byte[] var1 = new byte[8];
         if(this.random == null) {
            SecureRandom var2 = new SecureRandom();
            this.random = var2;
         }

         this.random.nextBytes(var1);

         try {
            AlgorithmParameters var3 = AlgorithmParameters.getInstance("IDEA", "myBC");
            IvParameterSpec var4 = new IvParameterSpec(var1);
            var3.init(var4);
            return var3;
         } catch (Exception var6) {
            String var5 = var6.getMessage();
            throw new RuntimeException(var5);
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException {
         throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for IDEA parameter generation.");
      }
   }

   public static class Mac extends JCEMac {

      public Mac() {
         IDEAEngine var1 = new IDEAEngine();
         CBCBlockCipherMac var2 = new CBCBlockCipherMac(var1);
         super(var2);
      }
   }

   public static class ECB extends JCEBlockCipher {

      public ECB() {
         IDEAEngine var1 = new IDEAEngine();
         super(var1);
      }
   }

   public static class PBEWithSHAAndIDEAKeyGen extends JCESecretKeyFactory.PBEKeyFactory {

      public PBEWithSHAAndIDEAKeyGen() {
         byte var2 = 1;
         super("PBEwithSHAandIDEA-CBC", (DERObjectIdentifier)null, (boolean)1, 2, var2, 128, 64);
      }
   }

   public static class AlgParams extends JDKAlgorithmParameters {

      private byte[] iv;


      public AlgParams() {}

      protected byte[] engineGetEncoded() throws IOException {
         return this.engineGetEncoded("ASN.1");
      }

      protected byte[] engineGetEncoded(String var1) throws IOException {
         byte[] var3;
         if(this.isASN1FormatString(var1)) {
            byte[] var2 = this.engineGetEncoded("RAW");
            var3 = (new IDEACBCPar(var2)).getEncoded();
         } else if(var1.equals("RAW")) {
            byte[] var4 = new byte[this.iv.length];
            byte[] var5 = this.iv;
            int var6 = this.iv.length;
            System.arraycopy(var5, 0, var4, 0, var6);
            var3 = var4;
         } else {
            var3 = null;
         }

         return var3;
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if(!(var1 instanceof IvParameterSpec)) {
            throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
         } else {
            byte[] var2 = ((IvParameterSpec)var1).getIV();
            this.iv = var2;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         byte[] var2 = new byte[var1.length];
         this.iv = var2;
         byte[] var3 = this.iv;
         int var4 = this.iv.length;
         System.arraycopy(var1, 0, var3, 0, var4);
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if(var2.equals("RAW")) {
            this.engineInit(var1);
         } else if(var2.equals("ASN.1")) {
            ASN1Sequence var3 = (ASN1Sequence)(new ASN1InputStream(var1)).readObject();
            byte[] var4 = (new IDEACBCPar(var3)).getIV();
            this.engineInit(var4);
         } else {
            throw new IOException("Unknown parameters format in IV parameters object");
         }
      }

      protected String engineToString() {
         return "IDEA Parameters";
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if(var1 == IvParameterSpec.class) {
            byte[] var2 = this.iv;
            return new IvParameterSpec(var2);
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to IV parameters object.");
         }
      }
   }
}
