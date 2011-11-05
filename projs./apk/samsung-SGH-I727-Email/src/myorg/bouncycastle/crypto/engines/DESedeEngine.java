package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.engines.DESEngine;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class DESedeEngine extends DESEngine {

   protected static final int BLOCK_SIZE = 8;
   private boolean forEncryption;
   private int[] workingKey1 = null;
   private int[] workingKey2 = null;
   private int[] workingKey3 = null;


   public DESedeEngine() {}

   public String getAlgorithmName() {
      return "DESede";
   }

   public int getBlockSize() {
      return 8;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(!(var2 instanceof KeyParameter)) {
         StringBuilder var3 = (new StringBuilder()).append("invalid parameter passed to DESede init - ");
         String var4 = var2.getClass().getName();
         String var5 = var3.append(var4).toString();
         throw new IllegalArgumentException(var5);
      } else {
         byte[] var6 = ((KeyParameter)var2).getKey();
         if(var6.length > 24) {
            throw new IllegalArgumentException("key size greater than 24 bytes");
         } else {
            this.forEncryption = var1;
            byte[] var7 = new byte[8];
            int var8 = var7.length;
            System.arraycopy(var6, 0, var7, 0, var8);
            int[] var9 = this.generateWorkingKey(var1, var7);
            this.workingKey1 = var9;
            byte[] var10 = new byte[8];
            int var11 = var10.length;
            System.arraycopy(var6, 8, var10, 0, var11);
            byte var12;
            if(!var1) {
               var12 = 1;
            } else {
               var12 = 0;
            }

            int[] var13 = this.generateWorkingKey((boolean)var12, var10);
            this.workingKey2 = var13;
            if(var6.length == 24) {
               byte[] var14 = new byte[8];
               int var15 = var14.length;
               System.arraycopy(var6, 16, var14, 0, var15);
               int[] var16 = this.generateWorkingKey(var1, var14);
               this.workingKey3 = var16;
            } else {
               int[] var17 = this.workingKey1;
               this.workingKey3 = var17;
            }
         }
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(this.workingKey1 == null) {
         throw new IllegalStateException("DESede engine not initialised");
      } else {
         int var5 = var2 + 8;
         int var6 = var1.length;
         if(var5 > var6) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var7 = var4 + 8;
            int var8 = var3.length;
            if(var7 > var8) {
               throw new DataLengthException("output buffer too short");
            } else {
               byte[] var9 = new byte[8];
               if(this.forEncryption) {
                  int[] var10 = this.workingKey1;
                  this.desFunc(var10, var1, var2, var9, 0);
                  int[] var14 = this.workingKey2;
                  byte var17 = 0;
                  this.desFunc(var14, var9, 0, var9, var17);
                  int[] var18 = this.workingKey3;
                  this.desFunc(var18, var9, 0, var3, var4);
               } else {
                  int[] var22 = this.workingKey3;
                  this.desFunc(var22, var1, var2, var9, 0);
                  int[] var26 = this.workingKey2;
                  byte var29 = 0;
                  this.desFunc(var26, var9, 0, var9, var29);
                  int[] var30 = this.workingKey1;
                  this.desFunc(var30, var9, 0, var3, var4);
               }

               return 8;
            }
         }
      }
   }

   public void reset() {}
}
