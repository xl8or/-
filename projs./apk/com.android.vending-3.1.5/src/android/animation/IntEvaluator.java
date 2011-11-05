package android.animation;

import android.animation.TypeEvaluator;

public class IntEvaluator implements TypeEvaluator {

   public IntEvaluator() {}

   public Object evaluate(float var1, Object var2, Object var3) {
      int var4 = ((Number)var2).intValue();
      float var5 = (float)var4;
      float var6 = (float)(((Number)var3).intValue() - var4) * var1;
      return Integer.valueOf((int)(var5 + var6));
   }
}
