package myorg.bouncycastle.crypto.engines;

import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;

public class NullEngine implements BlockCipher {

   protected static final int BLOCK_SIZE = 1;
   private boolean initialised;


   public NullEngine() {}

   public String getAlgorithmName() {
      return "Null";
   }

   public int getBlockSize() {
      return 1;
   }

   public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException {
      this.initialised = (boolean)1;
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException {
      if(!this.initialised) {
         throw new IllegalStateException("Null engine not initialised");
      } else {
         int var5 = var2 + 1;
         int var6 = var1.length;
         if(var5 > var6) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var7 = var4 + 1;
            int var8 = var3.length;
            if(var7 > var8) {
               throw new DataLengthException("output buffer too short");
            } else {
               for(int var9 = 0; var9 < 1; ++var9) {
                  int var10 = var4 + var9;
                  int var11 = var2 + var9;
                  byte var12 = var1[var11];
                  var3[var10] = var12;
               }

               return 1;
            }
         }
      }
   }

   public void reset() {}
}
