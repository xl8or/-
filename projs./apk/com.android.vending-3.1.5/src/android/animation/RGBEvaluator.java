package android.animation;

import android.animation.TypeEvaluator;

public class RGBEvaluator implements TypeEvaluator {

   public RGBEvaluator() {}

   public Object evaluate(float var1, Object var2, Object var3) {
      int var4 = ((Integer)var2).intValue();
      int var5 = var4 >> 24;
      int var6 = var4 >> 16 & 255;
      int var7 = var4 >> 8 & 255;
      int var8 = var4 & 255;
      int var9 = ((Integer)var3).intValue();
      int var10 = var9 >> 24;
      int var11 = var9 >> 16 & 255;
      int var12 = var9 >> 8 & 255;
      int var13 = var9 & 255;
      int var14 = (int)((float)(var10 - var5) * var1) + var5 << 24;
      int var15 = (int)((float)(var11 - var6) * var1) + var6 << 16;
      int var16 = var14 | var15;
      int var17 = (int)((float)(var12 - var7) * var1) + var7 << 8;
      int var18 = var16 | var17;
      int var19 = (int)((float)(var13 - var8) * var1) + var8;
      return Integer.valueOf(var18 | var19);
   }
}
