package myorg.bouncycastle.crypto.signers;

import java.util.Hashtable;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.DigestInfo;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import myorg.bouncycastle.crypto.AsymmetricBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.CryptoException;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.Signer;
import myorg.bouncycastle.crypto.encodings.PKCS1Encoding;
import myorg.bouncycastle.crypto.engines.RSABlindedEngine;
import myorg.bouncycastle.crypto.params.AsymmetricKeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithRandom;

public class RSADigestSigner implements Signer {

   private static final Hashtable oidMap = new Hashtable();
   private final AlgorithmIdentifier algId;
   private final Digest digest;
   private boolean forSigning;
   private final AsymmetricBlockCipher rsaEngine;


   static {
      Hashtable var0 = oidMap;
      DERObjectIdentifier var1 = TeleTrusTObjectIdentifiers.ripemd128;
      var0.put("RIPEMD128", var1);
      Hashtable var3 = oidMap;
      DERObjectIdentifier var4 = TeleTrusTObjectIdentifiers.ripemd160;
      var3.put("RIPEMD160", var4);
      Hashtable var6 = oidMap;
      DERObjectIdentifier var7 = TeleTrusTObjectIdentifiers.ripemd256;
      var6.put("RIPEMD256", var7);
      Hashtable var9 = oidMap;
      DERObjectIdentifier var10 = X509ObjectIdentifiers.id_SHA1;
      var9.put("SHA-1", var10);
      Hashtable var12 = oidMap;
      DERObjectIdentifier var13 = NISTObjectIdentifiers.id_sha224;
      var12.put("SHA-224", var13);
      Hashtable var15 = oidMap;
      DERObjectIdentifier var16 = NISTObjectIdentifiers.id_sha256;
      var15.put("SHA-256", var16);
      Hashtable var18 = oidMap;
      DERObjectIdentifier var19 = NISTObjectIdentifiers.id_sha384;
      var18.put("SHA-384", var19);
      Hashtable var21 = oidMap;
      DERObjectIdentifier var22 = NISTObjectIdentifiers.id_sha512;
      var21.put("SHA-512", var22);
      Hashtable var24 = oidMap;
      DERObjectIdentifier var25 = PKCSObjectIdentifiers.md2;
      var24.put("MD2", var25);
      Hashtable var27 = oidMap;
      DERObjectIdentifier var28 = PKCSObjectIdentifiers.md4;
      var27.put("MD4", var28);
      Hashtable var30 = oidMap;
      DERObjectIdentifier var31 = PKCSObjectIdentifiers.md5;
      var30.put("MD5", var31);
   }

   public RSADigestSigner(Digest var1) {
      RSABlindedEngine var2 = new RSABlindedEngine();
      PKCS1Encoding var3 = new PKCS1Encoding(var2);
      this.rsaEngine = var3;
      this.digest = var1;
      Hashtable var4 = oidMap;
      String var5 = var1.getAlgorithmName();
      DERObjectIdentifier var6 = (DERObjectIdentifier)var4.get(var5);
      DERNull var7 = DERNull.INSTANCE;
      AlgorithmIdentifier var8 = new AlgorithmIdentifier(var6, var7);
      this.algId = var8;
   }

   private byte[] derEncode(byte[] var1) {
      AlgorithmIdentifier var2 = this.algId;
      return (new DigestInfo(var2, var1)).getDEREncoded();
   }

   public byte[] generateSignature() throws CryptoException, DataLengthException {
      if(!this.forSigning) {
         throw new IllegalStateException("RSADigestSigner not initialised for signature generation.");
      } else {
         byte[] var1 = new byte[this.digest.getDigestSize()];
         this.digest.doFinal(var1, 0);
         byte[] var3 = this.derEncode(var1);
         AsymmetricBlockCipher var4 = this.rsaEngine;
         int var5 = var3.length;
         return var4.processBlock(var3, 0, var5);
      }
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.digest.getAlgorithmName();
      return var1.append(var2).append("withRSA").toString();
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forSigning = var1;
      AsymmetricKeyParameter var3;
      if(var2 instanceof ParametersWithRandom) {
         var3 = (AsymmetricKeyParameter)((ParametersWithRandom)var2).getParameters();
      } else {
         var3 = (AsymmetricKeyParameter)var2;
      }

      if(var1 && !var3.isPrivate()) {
         throw new IllegalArgumentException("signing requires private key");
      } else if(!var1 && var3.isPrivate()) {
         throw new IllegalArgumentException("verification requires public key");
      } else {
         this.reset();
         this.rsaEngine.init(var1, var2);
      }
   }

   public void reset() {
      this.digest.reset();
   }

   public void update(byte var1) {
      this.digest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }

   public boolean verifySignature(byte[] var1) {
      if(this.forSigning) {
         throw new IllegalStateException("RSADigestSigner not initialised for verification");
      } else {
         byte[] var2 = new byte[this.digest.getDigestSize()];
         this.digest.doFinal(var2, 0);

         byte[] var6;
         byte[] var7;
         boolean var15;
         try {
            AsymmetricBlockCipher var4 = this.rsaEngine;
            int var5 = var1.length;
            var6 = var4.processBlock(var1, 0, var5);
            var7 = this.derEncode(var2);
         } catch (Exception var34) {
            var15 = false;
            return var15;
         }

         byte[] var8 = var7;
         int var9 = var6.length;
         int var10 = var7.length;
         int var11;
         if(var9 == var10) {
            var11 = 0;

            while(true) {
               int var12 = var6.length;
               if(var11 >= var12) {
                  break;
               }

               byte var13 = var6[var11];
               byte var14 = var8[var11];
               if(var13 != var14) {
                  var15 = false;
                  return var15;
               }

               ++var11;
            }
         } else {
            int var17 = var6.length;
            int var18 = var7.length - 2;
            if(var17 != var18) {
               var15 = false;
               return var15;
            }

            int var19 = var6.length;
            int var20 = var2.length;
            int var21 = var19 - var20 - 2;
            int var22 = var7.length;
            int var23 = var2.length;
            int var24 = var22 - var23 - 2;
            byte var25 = (byte)(var7[1] - 2);
            var7[1] = var25;
            byte var26 = (byte)(var7[3] - 2);
            var7[3] = var26;
            var11 = 0;

            while(true) {
               int var27 = var2.length;
               if(var11 >= var27) {
                  for(var11 = 0; var11 < var21; ++var11) {
                     byte var32 = var6[var11];
                     byte var33 = var8[var11];
                     if(var32 != var33) {
                        var15 = false;
                        return var15;
                     }
                  }
                  break;
               }

               int var28 = var21 + var11;
               byte var29 = var6[var28];
               int var30 = var24 + var11;
               byte var31 = var8[var30];
               if(var29 != var31) {
                  var15 = false;
                  return var15;
               }

               ++var11;
            }
         }

         var15 = true;
         return var15;
      }
   }
}
