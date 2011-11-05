package myorg.bouncycastle.crypto.macs;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class OldHMac implements Mac {

   private static final int BLOCK_LENGTH = 64;
   private static final byte IPAD = 54;
   private static final byte OPAD = 92;
   private Digest digest;
   private int digestSize;
   private byte[] inputPad;
   private byte[] outputPad;


   public OldHMac(Digest var1) {
      byte[] var2 = new byte[64];
      this.inputPad = var2;
      byte[] var3 = new byte[64];
      this.outputPad = var3;
      this.digest = var1;
      int var4 = var1.getDigestSize();
      this.digestSize = var4;
   }

   public int doFinal(byte[] var1, int var2) {
      byte[] var3 = new byte[this.digestSize];
      this.digest.doFinal(var3, 0);
      Digest var5 = this.digest;
      byte[] var6 = this.outputPad;
      int var7 = this.outputPad.length;
      var5.update(var6, 0, var7);
      Digest var8 = this.digest;
      int var9 = var3.length;
      var8.update(var3, 0, var9);
      int var10 = this.digest.doFinal(var1, var2);
      this.reset();
      return var10;
   }

   public String getAlgorithmName() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.digest.getAlgorithmName();
      return var1.append(var2).append("/HMAC").toString();
   }

   public int getMacSize() {
      return this.digestSize;
   }

   public Digest getUnderlyingDigest() {
      return this.digest;
   }

   public void init(CipherParameters var1) {
      this.digest.reset();
      byte[] var2 = ((KeyParameter)var1).getKey();
      int var8;
      if(var2.length > 64) {
         Digest var3 = this.digest;
         int var4 = var2.length;
         var3.update(var2, 0, var4);
         Digest var5 = this.digest;
         byte[] var6 = this.inputPad;
         var5.doFinal(var6, 0);
         var8 = this.digestSize;

         while(true) {
            int var9 = this.inputPad.length;
            if(var8 >= var9) {
               break;
            }

            this.inputPad[var8] = 0;
            ++var8;
         }
      } else {
         byte[] var10 = this.inputPad;
         int var11 = var2.length;
         System.arraycopy(var2, 0, var10, 0, var11);
         var8 = var2.length;

         while(true) {
            int var12 = this.inputPad.length;
            if(var8 >= var12) {
               break;
            }

            this.inputPad[var8] = 0;
            ++var8;
         }
      }

      byte[] var13 = new byte[this.inputPad.length];
      this.outputPad = var13;
      byte[] var14 = this.inputPad;
      byte[] var15 = this.outputPad;
      int var16 = this.inputPad.length;
      System.arraycopy(var14, 0, var15, 0, var16);
      int var17 = 0;

      while(true) {
         int var18 = this.inputPad.length;
         if(var17 >= var18) {
            int var21 = 0;

            while(true) {
               int var22 = this.outputPad.length;
               if(var21 >= var22) {
                  Digest var25 = this.digest;
                  byte[] var26 = this.inputPad;
                  int var27 = this.inputPad.length;
                  var25.update(var26, 0, var27);
                  return;
               }

               byte[] var23 = this.outputPad;
               byte var24 = (byte)(var23[var21] ^ 92);
               var23[var21] = var24;
               ++var21;
            }
         }

         byte[] var19 = this.inputPad;
         byte var20 = (byte)(var19[var17] ^ 54);
         var19[var17] = var20;
         ++var17;
      }
   }

   public void reset() {
      this.digest.reset();
      Digest var1 = this.digest;
      byte[] var2 = this.inputPad;
      int var3 = this.inputPad.length;
      var1.update(var2, 0, var3);
   }

   public void update(byte var1) {
      this.digest.update(var1);
   }

   public void update(byte[] var1, int var2, int var3) {
      this.digest.update(var1, var2, var3);
   }
}
