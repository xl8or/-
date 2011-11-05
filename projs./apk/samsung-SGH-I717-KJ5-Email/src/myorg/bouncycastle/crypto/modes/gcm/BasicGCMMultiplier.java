package myorg.bouncycastle.crypto.modes.gcm;

import myorg.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import myorg.bouncycastle.crypto.modes.gcm.GCMUtil;
import myorg.bouncycastle.util.Arrays;

public class BasicGCMMultiplier implements GCMMultiplier {

   private byte[] H;


   public BasicGCMMultiplier() {}

   public void init(byte[] var1) {
      byte[] var2 = Arrays.clone(var1);
      this.H = var2;
   }

   public void multiplyH(byte[] var1) {
      byte[] var2 = new byte[16];

      for(int var3 = 0; var3 < 16; ++var3) {
         byte var4 = this.H[var3];

         for(int var5 = 7; var5 >= 0; var5 += -1) {
            if((1 << var5 & var4) != 0) {
               GCMUtil.xor(var2, var1);
            }

            boolean var6;
            if((var1[15] & 1) != 0) {
               var6 = true;
            } else {
               var6 = false;
            }

            GCMUtil.shiftRight(var1);
            if(var6) {
               byte var7 = (byte)(var1[0] ^ -31);
               var1[0] = var7;
            }
         }
      }

      System.arraycopy(var2, 0, var1, 0, 16);
   }
}
