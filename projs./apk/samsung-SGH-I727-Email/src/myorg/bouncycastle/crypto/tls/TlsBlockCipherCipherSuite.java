package myorg.bouncycastle.crypto.tls;

import java.io.IOException;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.params.KeyParameter;
import myorg.bouncycastle.crypto.params.ParametersWithIV;
import myorg.bouncycastle.crypto.tls.TlsCipherSuite;
import myorg.bouncycastle.crypto.tls.TlsMac;
import myorg.bouncycastle.crypto.tls.TlsProtocolHandler;
import myorg.bouncycastle.crypto.tls.TlsUtils;

public class TlsBlockCipherCipherSuite extends TlsCipherSuite {

   private int cipherKeySize;
   private BlockCipher decryptCipher;
   private BlockCipher encryptCipher;
   private short keyExchange;
   private Digest readDigest;
   private TlsMac readMac;
   private Digest writeDigest;
   private TlsMac writeMac;


   protected TlsBlockCipherCipherSuite(BlockCipher var1, BlockCipher var2, Digest var3, Digest var4, int var5, short var6) {
      this.encryptCipher = var1;
      this.decryptCipher = var2;
      this.writeDigest = var3;
      this.readDigest = var4;
      this.cipherKeySize = var5;
      this.keyExchange = var6;
   }

   private void initCipher(boolean var1, BlockCipher var2, byte[] var3, int var4, int var5, int var6) {
      KeyParameter var7 = new KeyParameter(var3, var5, var4);
      int var8 = var2.getBlockSize();
      ParametersWithIV var9 = new ParametersWithIV(var7, var3, var6, var8);
      var2.init(var1, var9);
   }

   protected byte[] decodeCiphertext(short var1, byte[] var2, int var3, int var4, TlsProtocolHandler var5) throws IOException {
      int var6 = this.decryptCipher.getBlockSize();
      boolean var7 = false;

      int var8;
      for(var8 = 0; var8 < var4; var8 += var6) {
         BlockCipher var9 = this.decryptCipher;
         int var10 = var8 + var3;
         int var11 = var8 + var3;
         var9.processBlock(var2, var10, var2, var11);
      }

      int var13 = var3 + var4 - 1;
      byte var14 = var2[var13];
      if(var3 + var4 - 1 - var14 < 0) {
         var7 = true;
         var14 = 0;
      } else {
         for(var8 = 0; var8 <= var14; ++var8) {
            int var23 = var3 + var4 - 1 - var8;
            if(var2[var23] != var14) {
               var7 = true;
            }
         }
      }

      int var15 = this.readMac.getSize();
      int var16 = var4 - var15 - var14 - 1;
      byte[] var17 = this.readMac.calculateMac(var1, var2, var3, var16);
      int var18 = 0;

      while(true) {
         int var19 = var17.length;
         if(var18 >= var19) {
            if(var7) {
               var5.failWithError((short)2, (short)20);
            }

            byte[] var24 = new byte[var16];
            System.arraycopy(var2, var3, var24, 0, var16);
            return var24;
         }

         int var20 = var3 + var16 + var18;
         byte var21 = var2[var20];
         byte var22 = var17[var18];
         if(var21 != var22) {
            var7 = true;
         }

         ++var18;
      }
   }

   protected byte[] encodePlaintext(short var1, byte[] var2, int var3, int var4) {
      int var5 = this.encryptCipher.getBlockSize();
      int var6 = (this.writeMac.getSize() + var4 + 1) % var5;
      int var7 = var5 - var6;
      int var8 = this.writeMac.getSize() + var4 + var7 + 1;
      byte[] var9 = new byte[var8];
      System.arraycopy(var2, var3, var9, 0, var4);
      byte[] var10 = this.writeMac.calculateMac(var1, var2, var3, var4);
      int var11 = var10.length;
      System.arraycopy(var10, 0, var9, var4, var11);
      int var12 = var10.length;
      int var13 = var4 + var12;

      for(int var14 = 0; var14 <= var7; ++var14) {
         int var15 = var14 + var13;
         byte var16 = (byte)var7;
         var9[var15] = var16;
      }

      for(int var17 = 0; var17 < var8; var17 += var5) {
         this.encryptCipher.processBlock(var9, var17, var9, var17);
      }

      return var9;
   }

   protected short getKeyExchangeAlgorithm() {
      return this.keyExchange;
   }

   protected void init(byte[] var1, byte[] var2, byte[] var3) {
      int var4 = this.cipherKeySize * 2;
      int var5 = this.writeDigest.getDigestSize() * 2;
      int var6 = var4 + var5;
      int var7 = this.encryptCipher.getBlockSize() * 2;
      byte[] var8 = new byte[var6 + var7];
      int var9 = var2.length;
      int var10 = var3.length;
      byte[] var11 = new byte[var9 + var10];
      int var12 = var3.length;
      int var13 = var2.length;
      System.arraycopy(var2, 0, var11, var12, var13);
      int var14 = var3.length;
      System.arraycopy(var3, 0, var11, 0, var14);
      byte[] var15 = TlsUtils.toByteArray("key expansion");
      TlsUtils.PRF(var1, var15, var11, var8);
      Digest var16 = this.writeDigest;
      int var17 = this.writeDigest.getDigestSize();
      TlsMac var18 = new TlsMac(var16, var8, 0, var17);
      this.writeMac = var18;
      int var19 = this.writeDigest.getDigestSize();
      int var20 = 0 + var19;
      Digest var21 = this.readDigest;
      int var22 = this.readDigest.getDigestSize();
      TlsMac var23 = new TlsMac(var21, var8, var20, var22);
      this.readMac = var23;
      int var24 = this.readDigest.getDigestSize();
      int var25 = var20 + var24;
      BlockCipher var26 = this.encryptCipher;
      int var27 = this.cipherKeySize;
      int var28 = this.cipherKeySize * 2;
      int var29 = var25 + var28;
      this.initCipher((boolean)1, var26, var8, var27, var25, var29);
      int var30 = this.cipherKeySize;
      int var31 = var25 + var30;
      BlockCipher var32 = this.decryptCipher;
      int var33 = this.cipherKeySize;
      int var34 = this.cipherKeySize + var31;
      int var35 = this.decryptCipher.getBlockSize();
      int var36 = var34 + var35;
      byte var38 = 0;
      this.initCipher((boolean)var38, var32, var8, var33, var31, var36);
   }
}
