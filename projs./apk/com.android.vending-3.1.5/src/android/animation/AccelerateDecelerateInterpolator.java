package android.animation;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;

public class AccelerateDecelerateInterpolator implements TimeInterpolator {

   public AccelerateDecelerateInterpolator() {}

   public AccelerateDecelerateInterpolator(Context var1, AttributeSet var2) {}

   public float getInterpolation(float var1) {
      return (float)(Math.cos((double)(1.0F + var1) * 3.141592653589793D) / 2.0D) + 0.5F;
   }
}
