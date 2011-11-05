package com.google.android.finsky.layout;

import android.app.Activity;
import android.os.Build.VERSION;
import com.google.android.finsky.layout.ActionBarWrapper;
import com.google.android.finsky.layout.CustomActionBar;
import com.google.android.finsky.layout.FakeActionBar;
import com.google.android.finsky.layout.FinskyActionBar;

public class CustomActionBarFactory {

   public CustomActionBarFactory() {}

   public static CustomActionBar getInstance(Activity var0) {
      Object var1;
      if(shouldUseSystemActionBar()) {
         if(var0.getActionBar() == null) {
            var1 = new FakeActionBar();
         } else {
            var1 = new ActionBarWrapper(var0);
         }
      } else {
         var1 = (FinskyActionBar)var0.findViewById(2131755008);
      }

      return (CustomActionBar)var1;
   }

   public static boolean shouldUseSystemActionBar() {
      boolean var0;
      if(VERSION.SDK_INT >= 11) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }
}
