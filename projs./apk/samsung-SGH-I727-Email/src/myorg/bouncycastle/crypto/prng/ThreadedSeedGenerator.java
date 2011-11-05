package myorg.bouncycastle.crypto.prng;


public class ThreadedSeedGenerator {

   public ThreadedSeedGenerator() {}

   public byte[] generateSeed(int var1, boolean var2) {
      return (new ThreadedSeedGenerator.SeedGenerator((ThreadedSeedGenerator.1)null)).generateSeed(var1, var2);
   }

   private class SeedGenerator implements Runnable {

      private volatile int counter;
      private volatile boolean stop;


      private SeedGenerator() {
         this.counter = 0;
         this.stop = (boolean)0;
      }

      // $FF: synthetic method
      SeedGenerator(ThreadedSeedGenerator.1 var2) {
         this();
      }

      public byte[] generateSeed(int var1, boolean var2) {
         Thread var3 = new Thread(this);
         byte[] var4 = new byte[var1];
         this.counter = 0;
         this.stop = (boolean)0;
         int var5 = 0;
         var3.start();
         int var6;
         if(var2) {
            var6 = var1;
         } else {
            var6 = var1 * 8;
         }

         for(int var7 = 0; var7 < var6; ++var7) {
            while(this.counter == var5) {
               long var8 = 1L;

               try {
                  Thread.sleep(var8);
               } catch (InterruptedException var16) {
                  ;
               }
            }

            var5 = this.counter;
            if(var2) {
               byte var11 = (byte)(var5 & 255);
               var4[var7] = var11;
            } else {
               int var12 = var7 / 8;
               int var13 = var4[var12] << 1;
               int var14 = var5 & 1;
               byte var15 = (byte)(var13 | var14);
               var4[var12] = var15;
            }
         }

         this.stop = (boolean)1;
         return var4;
      }

      public void run() {
         while(!this.stop) {
            int var1 = this.counter + 1;
            this.counter = var1;
         }

      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
