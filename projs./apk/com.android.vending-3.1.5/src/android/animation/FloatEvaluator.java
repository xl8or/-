package android.animation;

import android.animation.TypeEvaluator;

public class FloatEvaluator implements TypeEvaluator {

   public FloatEvaluator() {}

   public Object evaluate(float var1, Object var2, Object var3) {
      float var4 = ((Number)var2).floatValue();
      return Float.valueOf((((Number)var3).floatValue() - var4) * var1 + var4);
   }
}
