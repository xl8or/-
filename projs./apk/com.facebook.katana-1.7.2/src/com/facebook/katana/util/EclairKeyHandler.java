package com.facebook.katana.util;

import android.view.KeyEvent;

public class EclairKeyHandler {

   private EclairKeyHandler() {}

   public static boolean onKeyDown(KeyEvent var0) {
      boolean var1;
      if(var0.getRepeatCount() == 0) {
         var0.startTracking();
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean onKeyUp(KeyEvent var0) {
      boolean var1;
      if(var0.isTracking() && !var0.isCanceled()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
