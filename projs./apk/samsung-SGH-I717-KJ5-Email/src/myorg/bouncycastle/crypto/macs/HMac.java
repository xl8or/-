package myorg.bouncycastle.crypto.macs;

import java.util.Hashtable;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.ExtendedDigest;
import myorg.bouncycastle.crypto.Mac;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class HMac implements Mac {

   private static final byte IPAD = 54;
   private static final byte OPAD = 92;
   private static Hashtable blockLengths = new Hashtable();
   private int blockLength;
   private Digest digest;
   private int digestSize;
   private byte[] inputPad;
   private byte[] outputPad;


   static {
      Hashtable var0 = blockLengths;
      Integer var1 = new Integer(32);
      var0.put("GOST3411", var1);
      Hashtable var3 = blockLengths;
      Integer var4 = new Integer(16);
      var3.put("MD2", var4);
      Hashtable var6 = blockLengths;
      Integer var7 = new Integer(64);
      var6.put("MD4", var7);
      Hashtable var9 = blockLengths;
      Integer var10 = new Integer(64);
      var9.put("MD5", var10);
      Hashtable var12 = blockLengths;
      Integer var13 = new Integer(64);
      var12.put("RIPEMD128", var13);
      Hashtable var15 = blockLengths;
      Integer var16 = new Integer(64);
      var15.put("RIPEMD160", var16);
      Hashtable var18 = blockLengths;
      Integer var19 = new Integer(64);
      var18.put("SHA-1", var19);
      Hashtable var21 = blockLengths;
      Integer var22 = new Integer(64);
      var21.put("SHA-224", var22);
      Hashtable var24 = blockLengths;
      Integer var25 = new Integer(64);
      var24.put("SHA-256", var25);
      Hashtable var27 = blockLengths;
      Integer var28 = new Integer(128);
      var27.put("SHA-384", var28);
      Hashtable var30 = blockLengths;
      Integer var31 = new Integer(128);
      var30.put("SHA-512", var31);
      Hashtable var33 = blockLengths;
      Integer var34 = new Integer(64);
      var33.put("Tiger", var34);
      Hashtable var36 = blockLengths;
      Integer var37 = new Integer(64);
      var36.put("Whirlpool", var37);
   }

   public HMac(Digest var1) {
      int var2 = getByteLength(var1);
      this(var1, var2);
   }

   private HMac(Digest var1, int var2) {
      this.digest = var1;
      int var3 = var1.getDigestSize();
      this.digestSize = var3;
      this.blockLength = var2;
      byte[] var4 = new byte[this.blockLength];
      this.inputPad = var4;
      byte[] var5 = new byte[this.blockLength];
      this.outputPad = var5;
   }

   private static int getByteLength(Digest var0) {
      int var1;
      if(var0 instanceof ExtendedDigest) {
         var1 = ((ExtendedDigest)var0).getByteLength();
      } else {
         Hashtable var2 = blockLengths;
         String var3 = var0.getAlgorithmName();
         Integer var4 = (Integer)var2.get(var3);
         if(var4 == null) {
            StringBuilder var5 = (new StringBuilder()).append("unknown digest passed: ");
            String var6 = var0.getAlgorithmName();
            String var7 = var5.append(var6).toString();
            throw new IllegalArgumentException(var7);
         }

         var1 = var4.intValue();
      }

      return var1;
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
      int var3 = var2.length;
      int var4 = this.blockLength;
      int var10;
      if(var3 > var4) {
         Digest var5 = this.digest;
         int var6 = var2.length;
         var5.update(var2, 0, var6);
         Digest var7 = this.digest;
         byte[] var8 = this.inputPad;
         var7.doFinal(var8, 0);
         var10 = this.digestSize;

         while(true) {
            int var11 = this.inputPad.length;
            if(var10 >= var11) {
               break;
            }

            this.inputPad[var10] = 0;
            ++var10;
         }
      } else {
         byte[] var12 = this.inputPad;
         int var13 = var2.length;
         System.arraycopy(var2, 0, var12, 0, var13);
         var10 = var2.length;

         while(true) {
            int var14 = this.inputPad.length;
            if(var10 >= var14) {
               break;
            }

            this.inputPad[var10] = 0;
            ++var10;
         }
      }

      byte[] var15 = new byte[this.inputPad.length];
      this.outputPad = var15;
      byte[] var16 = this.inputPad;
      byte[] var17 = this.outputPad;
      int var18 = this.inputPad.length;
      System.arraycopy(var16, 0, var17, 0, var18);
      int var19 = 0;

      while(true) {
         int var20 = this.inputPad.length;
         if(var19 >= var20) {
            int var23 = 0;

            while(true) {
               int var24 = this.outputPad.length;
               if(var23 >= var24) {
                  Digest var27 = this.digest;
                  byte[] var28 = this.inputPad;
                  int var29 = this.inputPad.length;
                  var27.update(var28, 0, var29);
                  return;
               }

               byte[] var25 = this.outputPad;
               byte var26 = (byte)(var25[var23] ^ 92);
               var25[var23] = var26;
               ++var23;
            }
         }

         byte[] var21 = this.inputPad;
         byte var22 = (byte)(var21[var19] ^ 54);
         var21[var19] = var22;
         ++var19;
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
