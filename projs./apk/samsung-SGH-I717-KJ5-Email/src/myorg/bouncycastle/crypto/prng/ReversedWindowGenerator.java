package myorg.bouncycastle.crypto.prng;

import myorg.bouncycastle.crypto.prng.RandomGenerator;

public class ReversedWindowGenerator implements RandomGenerator {

   private final RandomGenerator generator;
   private byte[] window;
   private int windowCount;


   public ReversedWindowGenerator(RandomGenerator var1, int var2) {
      if(var1 == null) {
         throw new IllegalArgumentException("generator cannot be null");
      } else if(var2 < 2) {
         throw new IllegalArgumentException("windowSize must be at least 2");
      } else {
         this.generator = var1;
         byte[] var3 = new byte[var2];
         this.window = var3;
      }
   }

   private void doNextBytes(byte[] param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   public void addSeedMaterial(long var1) {
      synchronized(this) {
         this.windowCount = 0;
         this.generator.addSeedMaterial(var1);
      }
   }

   public void addSeedMaterial(byte[] var1) {
      synchronized(this) {
         this.windowCount = 0;
         this.generator.addSeedMaterial(var1);
      }
   }

   public void nextBytes(byte[] var1) {
      int var2 = var1.length;
      this.doNextBytes(var1, 0, var2);
   }

   public void nextBytes(byte[] var1, int var2, int var3) {
      this.doNextBytes(var1, var2, var3);
   }
}
