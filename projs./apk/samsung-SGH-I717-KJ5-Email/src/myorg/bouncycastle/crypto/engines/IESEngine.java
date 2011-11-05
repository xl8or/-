package myorg.bouncycastle.crypto.engines;

import java.math.BigInteger;
import myorg.bouncycastle.crypto.BasicAgreement;
import myorg.bouncycastle.crypto.BufferedBlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DerivationFunction;
import myorg.bouncycastle.crypto.InvalidCipherTextException;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.params.IESParameters;
import myorg.bouncycastle.crypto.params.IESWithCipherParameters;
import myorg.bouncycastle.crypto.params.KDFParameters;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class IESEngine {

   BasicAgreement agree;
   BufferedBlockCipher cipher;
   boolean forEncryption;
   DerivationFunction kdf;
   Mac mac;
   byte[] macBuf;
   IESParameters param;
   CipherParameters privParam;
   CipherParameters pubParam;


   public IESEngine(BasicAgreement var1, DerivationFunction var2, Mac var3) {
      this.agree = var1;
      this.kdf = var2;
      this.mac = var3;
      byte[] var4 = new byte[var3.getMacSize()];
      this.macBuf = var4;
      this.cipher = null;
   }

   public IESEngine(BasicAgreement var1, DerivationFunction var2, Mac var3, BufferedBlockCipher var4) {
      this.agree = var1;
      this.kdf = var2;
      this.mac = var3;
      byte[] var5 = new byte[var3.getMacSize()];
      this.macBuf = var5;
      this.cipher = var4;
   }

   private byte[] decryptBlock(byte[] var1, int var2, int var3, byte[] var4) throws InvalidCipherTextException {
      KDFParameters var5 = new KDFParameters;
      byte[] var6 = this.param.getDerivationV();
      var5.<init>(var4, var6);
      int var10 = this.param.getMacKeySize();
      this.kdf.init(var5);
      int var11 = this.mac.getMacSize();
      int var12 = var3 - var11;
      byte[] var18;
      KeyParameter var26;
      if(this.cipher == null) {
         int var13 = var10 / 8 + var12;
         byte[] var17 = this.generateKdfBytes(var5, var13);
         var18 = new byte[var12];

         for(int var19 = 0; var19 != var12; ++var19) {
            int var22 = var2 + var19;
            byte var23 = var1[var22];
            byte var24 = var17[var19];
            byte var25 = (byte)(var23 ^ var24);
            var18[var19] = var25;
         }

         var26 = new KeyParameter;
         int var27 = var10 / 8;
         var26.<init>(var17, var12, var27);
      } else {
         int var56 = ((IESWithCipherParameters)this.param).getCipherKeySize();
         int var57 = var56 / 8;
         int var58 = var10 / 8;
         int var59 = var57 + var58;
         byte[] var63 = this.generateKdfBytes(var5, var59);
         BufferedBlockCipher var64 = this.cipher;
         int var65 = var56 / 8;
         KeyParameter var66 = new KeyParameter(var63, 0, var65);
         var64.init((boolean)0, var66);
         BufferedBlockCipher var67 = this.cipher;
         byte[] var69 = new byte[var67.getOutputSize(var12)];
         BufferedBlockCipher var70 = this.cipher;
         int var74 = var70.processBytes(var1, var2, var12, var69, 0);
         int var75 = this.cipher.doFinal(var69, var74);
         int var76 = var74 + var75;
         var18 = new byte[var76];
         System.arraycopy(var69, 0, var18, 0, var76);
         var26 = new KeyParameter;
         int var77 = var56 / 8;
         int var78 = var10 / 8;
         var26.<init>(var63, var77, var78);
      }

      byte[] var32 = this.param.getEncodingV();
      Mac var33 = this.mac;
      var33.init(var26);
      Mac var35 = this.mac;
      var35.update(var1, var2, var12);
      Mac var39 = this.mac;
      int var40 = var32.length;
      byte var43 = 0;
      var39.update(var32, var43, var40);
      Mac var45 = this.mac;
      byte[] var46 = this.macBuf;
      var45.doFinal(var46, 0);
      int var48 = var2 + var12;
      int var49 = 0;

      while(true) {
         int var50 = this.macBuf.length;
         if(var49 >= var50) {
            return var18;
         }

         byte var53 = this.macBuf[var49];
         int var54 = var48 + var49;
         byte var55 = var1[var54];
         if(var53 != var55) {
            throw new InvalidCipherTextException("Mac codes failed to equal.");
         }

         ++var49;
      }
   }

   private byte[] encryptBlock(byte[] var1, int var2, int var3, byte[] var4) throws InvalidCipherTextException {
      KDFParameters var5 = new KDFParameters;
      byte[] var6 = this.param.getDerivationV();
      var5.<init>(var4, var6);
      int var10 = this.param.getMacKeySize();
      int var17;
      byte[] var16;
      KeyParameter var25;
      if(this.cipher == null) {
         int var11 = var10 / 8 + var3;
         byte[] var15 = this.generateKdfBytes(var5, var11);
         var16 = new byte[this.mac.getMacSize() + var3];
         var17 = var3;

         for(int var18 = 0; var18 != var3; ++var18) {
            int var21 = var2 + var18;
            byte var22 = var1[var21];
            byte var23 = var15[var18];
            byte var24 = (byte)(var22 ^ var23);
            var16[var18] = var24;
         }

         var25 = new KeyParameter;
         int var26 = var10 / 8;
         var25.<init>(var15, var3, var26);
      } else {
         int var41 = ((IESWithCipherParameters)this.param).getCipherKeySize();
         int var42 = var41 / 8;
         int var43 = var10 / 8;
         int var44 = var42 + var43;
         byte[] var48 = this.generateKdfBytes(var5, var44);
         BufferedBlockCipher var49 = this.cipher;
         int var50 = var41 / 8;
         KeyParameter var51 = new KeyParameter(var48, 0, var50);
         var49.init((boolean)1, var51);
         BufferedBlockCipher var52 = this.cipher;
         byte[] var54 = new byte[var52.getOutputSize(var3)];
         BufferedBlockCipher var55 = this.cipher;
         int var59 = var55.processBytes(var1, var2, var3, var54, 0);
         BufferedBlockCipher var60 = this.cipher;
         int var63 = var60.doFinal(var54, var59);
         int var64 = var59 + var63;
         var16 = new byte[this.mac.getMacSize() + var64];
         var17 = var64;
         byte var66 = 0;
         byte var68 = 0;
         System.arraycopy(var54, var66, var16, var68, var64);
         var25 = new KeyParameter;
         int var70 = var41 / 8;
         int var71 = var10 / 8;
         var25.<init>(var48, var70, var71);
      }

      byte[] var31 = this.param.getEncodingV();
      Mac var32 = this.mac;
      var32.init(var25);
      this.mac.update(var16, 0, var17);
      Mac var34 = this.mac;
      int var35 = var31.length;
      byte var38 = 0;
      var34.update(var31, var38, var35);
      this.mac.doFinal(var16, var17);
      return var16;
   }

   private byte[] generateKdfBytes(KDFParameters var1, int var2) {
      byte[] var3 = new byte[var2];
      this.kdf.init(var1);
      DerivationFunction var4 = this.kdf;
      int var5 = var3.length;
      var4.generateBytes(var3, 0, var5);
      return var3;
   }

   public void init(boolean var1, CipherParameters var2, CipherParameters var3, CipherParameters var4) {
      this.forEncryption = var1;
      this.privParam = var2;
      this.pubParam = var3;
      IESParameters var5 = (IESParameters)var4;
      this.param = var5;
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      BasicAgreement var4 = this.agree;
      CipherParameters var5 = this.privParam;
      var4.init(var5);
      BasicAgreement var6 = this.agree;
      CipherParameters var7 = this.pubParam;
      BigInteger var8 = var6.calculateAgreement(var7);
      byte[] var10;
      if(this.forEncryption) {
         byte[] var9 = var8.toByteArray();
         var10 = this.encryptBlock(var1, var2, var3, var9);
      } else {
         byte[] var11 = var8.toByteArray();
         var10 = this.decryptBlock(var1, var2, var3, var11);
      }

      return var10;
   }
}
