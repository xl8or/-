package org.mozilla.intl.chardet;


public class nsEUCSampler {

   public int[] mFirstByteCnt;
   public float[] mFirstByteFreq;
   public int[] mSecondByteCnt;
   public float[] mSecondByteFreq;
   int mState = 0;
   int mThreshold = 200;
   int mTotal = 0;


   public nsEUCSampler() {
      int[] var1 = new int[94];
      this.mFirstByteCnt = var1;
      int[] var2 = new int[94];
      this.mSecondByteCnt = var2;
      float[] var3 = new float[94];
      this.mFirstByteFreq = var3;
      float[] var4 = new float[94];
      this.mSecondByteFreq = var4;
      this.Reset();
   }

   void CalFreq() {
      for(int var1 = 0; var1 < 94; ++var1) {
         float[] var2 = this.mFirstByteFreq;
         float var3 = (float)this.mFirstByteCnt[var1];
         float var4 = (float)this.mTotal;
         float var5 = var3 / var4;
         var2[var1] = var5;
         float[] var6 = this.mSecondByteFreq;
         float var7 = (float)this.mSecondByteCnt[var1];
         float var8 = (float)this.mTotal;
         float var9 = var7 / var8;
         var6[var1] = var9;
      }

   }

   boolean EnoughData() {
      int var1 = this.mTotal;
      int var2 = this.mThreshold;
      boolean var3;
      if(var1 > var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   float GetScore(float[] var1, float var2, float[] var3, float var4) {
      float[] var5 = this.mFirstByteFreq;
      float var6 = this.GetScore(var1, var5) * var2;
      float[] var7 = this.mSecondByteFreq;
      float var8 = this.GetScore(var3, var7) * var4;
      return var6 + var8;
   }

   float GetScore(float[] var1, float[] var2) {
      float var3 = 0.0F;

      for(int var4 = 0; var4 < 94; ++var4) {
         float var5 = var1[var4];
         float var6 = var2[var4];
         float var7 = var5 - var6;
         float var8 = var7 * var7;
         var3 += var8;
      }

      return (float)Math.sqrt((double)var3) / 94.0F;
   }

   boolean GetSomeData() {
      boolean var1;
      if(this.mTotal > 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void Reset() {
      this.mTotal = 0;
      this.mState = 0;

      for(int var1 = 0; var1 < 94; ++var1) {
         int[] var2 = this.mFirstByteCnt;
         this.mSecondByteCnt[var1] = 0;
         var2[var1] = 0;
      }

   }

   boolean Sample(byte[] var1, int var2) {
      boolean var3;
      if(this.mState == 1) {
         var3 = false;
      } else {
         int var4 = 0;

         for(int var5 = 0; var5 < var2; ++var4) {
            int var6 = this.mState;
            if(1 == var6) {
               break;
            }

            switch(this.mState) {
            case 0:
               if((var1[var4] & 128) != 0) {
                  label40: {
                     int var7 = var1[var4] & 255;
                     if(255 != var7) {
                        int var8 = var1[var4] & 255;
                        if(161 <= var8) {
                           int var9 = this.mTotal + 1;
                           this.mTotal = var9;
                           int[] var10 = this.mFirstByteCnt;
                           int var11 = (var1[var4] & 255) - 161;
                           int var12 = var10[var11] + 1;
                           var10[var11] = var12;
                           this.mState = 2;
                           break label40;
                        }
                     }

                     this.mState = 1;
                  }
               }
            case 1:
               break;
            case 2:
               if((var1[var4] & 128) != 0) {
                  int var13 = var1[var4] & 255;
                  if(255 != var13) {
                     int var14 = var1[var4] & 255;
                     if(161 <= var14) {
                        int var15 = this.mTotal + 1;
                        this.mTotal = var15;
                        int[] var16 = this.mSecondByteCnt;
                        int var17 = (var1[var4] & 255) - 161;
                        int var18 = var16[var17] + 1;
                        var16[var17] = var18;
                        this.mState = 0;
                        break;
                     }
                  }

                  this.mState = 1;
               } else {
                  this.mState = 1;
               }
               break;
            default:
               this.mState = 1;
            }

            ++var5;
         }

         int var19 = this.mState;
         if(1 != var19) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }
}
