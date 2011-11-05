package android.animation;

import android.animation.TypeEvaluator;

public class DoubleEvaluator implements TypeEvaluator {

   public DoubleEvaluator() {}

   public Object evaluate(float var1, Object var2, Object var3) {
      double var4 = ((Number)var2).doubleValue();
      double var6 = (double)var1;
      double var8 = ((Number)var3).doubleValue() - var4;
      return Double.valueOf(var6 * var8 + var4);
   }
}
