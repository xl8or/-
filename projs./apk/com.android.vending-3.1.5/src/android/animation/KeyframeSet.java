package android.animation;

import android.animation.Keyframe;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class KeyframeSet {

   ArrayList<Keyframe> mKeyframes;
   private int mNumKeyframes;


   public KeyframeSet(Keyframe ... var1) {
      ArrayList var2 = new ArrayList();
      this.mKeyframes = var2;
      ArrayList var3 = this.mKeyframes;
      List var4 = Arrays.asList(var1);
      var3.addAll(var4);
      int var6 = this.mKeyframes.size();
      this.mNumKeyframes = var6;
   }

   public static KeyframeSet ofDouble(double ... var0) {
      int var1 = var0.length;
      Keyframe[] var2 = new Keyframe[Math.max(var1, 2)];
      if(var1 == 1) {
         Object var3 = (Object)false;
         Keyframe var4 = new Keyframe(0.0F, var3);
         var2[0] = var4;
         double var5 = var0[0];
         Keyframe var7 = new Keyframe(1.0F, var5);
         var2[1] = var7;
      } else {
         double var8 = var0[0];
         Keyframe var10 = new Keyframe(0.0F, var8);
         var2[0] = var10;

         for(int var11 = 1; var11 < var1; ++var11) {
            float var12 = (float)var11;
            float var13 = (float)(var1 + -1);
            float var14 = var12 / var13;
            double var15 = var0[var11];
            Keyframe var17 = new Keyframe(var14, var15);
            var2[var11] = var17;
         }
      }

      return new KeyframeSet(var2);
   }

   public static KeyframeSet ofFloat(float ... var0) {
      int var1 = var0.length;
      Keyframe[] var2 = new Keyframe[Math.max(var1, 2)];
      if(var1 == 1) {
         Object var3 = (Object)false;
         Keyframe var4 = new Keyframe(0.0F, var3);
         var2[0] = var4;
         float var5 = var0[0];
         Keyframe var6 = new Keyframe(1.0F, var5);
         var2[1] = var6;
      } else {
         float var7 = var0[0];
         Keyframe var8 = new Keyframe(0.0F, var7);
         var2[0] = var8;

         for(int var9 = 1; var9 < var1; ++var9) {
            float var10 = (float)var9;
            float var11 = (float)(var1 + -1);
            float var12 = var10 / var11;
            float var13 = var0[var9];
            Keyframe var14 = new Keyframe(var12, var13);
            var2[var9] = var14;
         }
      }

      return new KeyframeSet(var2);
   }

   public static KeyframeSet ofInt(int ... var0) {
      int var1 = var0.length;
      Keyframe[] var2 = new Keyframe[Math.max(var1, 2)];
      if(var1 == 1) {
         Object var3 = (Object)false;
         Keyframe var4 = new Keyframe(0.0F, var3);
         var2[0] = var4;
         int var5 = var0[0];
         Keyframe var6 = new Keyframe(1.0F, var5);
         var2[1] = var6;
      } else {
         int var7 = var0[0];
         Keyframe var8 = new Keyframe(0.0F, var7);
         var2[0] = var8;

         for(int var9 = 1; var9 < var1; ++var9) {
            float var10 = (float)var9;
            float var11 = (float)(var1 + -1);
            float var12 = var10 / var11;
            int var13 = var0[var9];
            Keyframe var14 = new Keyframe(var12, var13);
            var2[var9] = var14;
         }
      }

      return new KeyframeSet(var2);
   }

   public static KeyframeSet ofLong(long ... var0) {
      int var1 = var0.length;
      Keyframe[] var2 = new Keyframe[Math.max(var1, 2)];
      if(var1 == 1) {
         Object var3 = (Object)false;
         Keyframe var4 = new Keyframe(0.0F, var3);
         var2[0] = var4;
         float var5 = (float)var0[0];
         Keyframe var6 = new Keyframe(1.0F, var5);
         var2[1] = var6;
      } else {
         float var7 = (float)var0[0];
         Keyframe var8 = new Keyframe(0.0F, var7);
         var2[0] = var8;

         for(int var9 = 1; var9 < var1; ++var9) {
            float var10 = (float)var9;
            float var11 = (float)(var1 + -1);
            float var12 = var10 / var11;
            float var13 = (float)var0[var9];
            Keyframe var14 = new Keyframe(var12, var13);
            var2[var9] = var14;
         }
      }

      return new KeyframeSet(var2);
   }

   public static KeyframeSet ofObject(Object ... var0) {
      int var1 = var0.length;
      Keyframe[] var2 = new Keyframe[Math.max(var1, 2)];
      if(var1 == 1) {
         Object var3 = (Object)false;
         Keyframe var4 = new Keyframe(0.0F, var3);
         var2[0] = var4;
         Object var5 = var0[0];
         Keyframe var6 = new Keyframe(1.0F, var5);
         var2[1] = var6;
      } else {
         Object var7 = var0[0];
         Keyframe var8 = new Keyframe(0.0F, var7);
         var2[0] = var8;

         for(int var9 = 1; var9 < var1; ++var9) {
            float var10 = (float)var9;
            float var11 = (float)(var1 + -1);
            float var12 = var10 / var11;
            Object var13 = var0[var9];
            Keyframe var14 = new Keyframe(var12, var13);
            var2[var9] = var14;
         }
      }

      return new KeyframeSet(var2);
   }

   public Object getValue(float var1, TypeEvaluator var2) {
      Keyframe var3;
      Keyframe var4;
      TimeInterpolator var5;
      Object var14;
      if(var1 <= 0.0F) {
         var3 = (Keyframe)this.mKeyframes.get(0);
         var4 = (Keyframe)this.mKeyframes.get(1);
         var5 = var4.getInterpolator();
         if(var5 != null) {
            var1 = var5.getInterpolation(var1);
         }

         float var6 = var3.getFraction();
         float var7 = var1 - var6;
         float var8 = var4.getFraction();
         float var9 = var3.getFraction();
         float var10 = var8 - var9;
         float var11 = var7 / var10;
         Object var12 = var3.getValue();
         Object var13 = var4.getValue();
         var14 = var2.evaluate(var11, var12, var13);
      } else if(var1 >= 1.0F) {
         ArrayList var15 = this.mKeyframes;
         int var16 = this.mNumKeyframes + -2;
         var3 = (Keyframe)var15.get(var16);
         ArrayList var17 = this.mKeyframes;
         int var18 = this.mNumKeyframes + -1;
         var4 = (Keyframe)var17.get(var18);
         var5 = var4.getInterpolator();
         if(var5 != null) {
            var1 = var5.getInterpolation(var1);
         }

         float var19 = var3.getFraction();
         float var20 = var1 - var19;
         float var21 = var4.getFraction();
         float var22 = var3.getFraction();
         float var23 = var21 - var22;
         float var24 = var20 / var23;
         Object var25 = var3.getValue();
         Object var26 = var4.getValue();
         var14 = var2.evaluate(var24, var25, var26);
      } else {
         var3 = (Keyframe)this.mKeyframes.get(0);
         int var27 = 1;

         while(true) {
            int var28 = this.mNumKeyframes;
            if(var27 >= var28) {
               ArrayList var38 = this.mKeyframes;
               int var39 = this.mNumKeyframes + -1;
               var14 = ((Keyframe)var38.get(var39)).getValue();
               break;
            }

            var4 = (Keyframe)this.mKeyframes.get(var27);
            float var29 = var4.getFraction();
            if(var1 < var29) {
               var5 = var4.getInterpolator();
               if(var5 != null) {
                  var1 = var5.getInterpolation(var1);
               }

               float var30 = var3.getFraction();
               float var31 = var1 - var30;
               float var32 = var4.getFraction();
               float var33 = var3.getFraction();
               float var34 = var32 - var33;
               float var35 = var31 / var34;
               Object var36 = var3.getValue();
               Object var37 = var4.getValue();
               var14 = var2.evaluate(var35, var36, var37);
               break;
            }

            var3 = var4;
            ++var27;
         }
      }

      return var14;
   }
}
