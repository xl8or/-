package android.support.v4.view;

import android.view.View;

class ViewCompatICS {

   ViewCompatICS() {}

   public static boolean canScrollHorizontally(View var0, int var1) {
      return var0.canScrollHorizontally(var1);
   }

   public static boolean canScrollVertically(View var0, int var1) {
      return var0.canScrollVertically(var1);
   }
}
