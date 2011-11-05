package myorg.bouncycastle.crypto.prng;

import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.prng.RandomGenerator;

public class DigestRandomGenerator implements RandomGenerator {

   private static long CYCLE_COUNT = 10L;
   private Digest digest;
   private byte[] seed;
   private long seedCounter;
   private byte[] state;
   private long stateCounter;


   public DigestRandomGenerator(Digest var1) {
      this.digest = var1;
      byte[] var2 = new byte[var1.getDigestSize()];
      this.seed = var2;
      this.seedCounter = 1L;
      byte[] var3 = new byte[var1.getDigestSize()];
      this.state = var3;
      this.stateCounter = 1L;
   }

   private void cycleSeed() {
      byte[] var1 = this.seed;
      this.digestUpdate(var1);
      long var2 = this.seedCounter;
      long var4 = 1L + var2;
      this.seedCounter = var4;
      this.digestAddCounter(var2);
      byte[] var6 = this.seed;
      this.digestDoFinal(var6);
   }

   private void digestAddCounter(long var1) {
      for(int var3 = 0; var3 != 8; ++var3) {
         Digest var4 = this.digest;
         byte var5 = (byte)((int)var1);
         var4.update(var5);
         var1 >>>= 8;
      }

   }

   private void digestDoFinal(byte[] var1) {
      this.digest.doFinal(var1, 0);
   }

   private void digestUpdate(byte[] var1) {
      Digest var2 = this.digest;
      int var3 = var1.length;
      var2.update(var1, 0, var3);
   }

   private void generateState() {
      long var1 = this.stateCounter;
      long var3 = 1L + var1;
      this.stateCounter = var3;
      this.digestAddCounter(var1);
      byte[] var5 = this.state;
      this.digestUpdate(var5);
      byte[] var6 = this.seed;
      this.digestUpdate(var6);
      byte[] var7 = this.state;
      this.digestDoFinal(var7);
      long var8 = this.stateCounter;
      long var10 = CYCLE_COUNT;
      if(var8 % var10 == 0L) {
         this.cycleSeed();
      }
   }

   public void addSeedMaterial(long var1) {
      synchronized(this) {
         this.digestAddCounter(var1);
         byte[] var3 = this.seed;
         this.digestUpdate(var3);
         byte[] var4 = this.seed;
         this.digestDoFinal(var4);
      }
   }

   public void addSeedMaterial(byte[] var1) {
      synchronized(this) {
         this.digestUpdate(var1);
         byte[] var2 = this.seed;
         this.digestUpdate(var2);
         byte[] var3 = this.seed;
         this.digestDoFinal(var3);
      }
   }

   public void nextBytes(byte[] var1) {
      int var2 = var1.length;
      this.nextBytes(var1, 0, var2);
   }

   public void nextBytes(byte[] param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }
}
