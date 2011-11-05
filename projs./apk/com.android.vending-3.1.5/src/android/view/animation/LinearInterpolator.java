package android.view.animation;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;

public class LinearInterpolator implements TimeInterpolator {

   public LinearInterpolator() {}

   public LinearInterpolator(Context var1, AttributeSet var2) {}

   public float getInterpolation(float var1) {
      return var1;
   }
}
