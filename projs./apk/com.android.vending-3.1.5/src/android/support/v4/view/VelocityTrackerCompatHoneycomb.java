package android.support.v4.view;

import android.view.VelocityTracker;

class VelocityTrackerCompatHoneycomb {

   VelocityTrackerCompatHoneycomb() {}

   public static float getXVelocity(VelocityTracker var0, int var1) {
      return var0.getXVelocity(var1);
   }

   public static float getYVelocity(VelocityTracker var0, int var1) {
      return var0.getYVelocity(var1);
   }
}
