package com.facebook.katana.util;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

   public Toaster() {}

   public static void toast(Context var0, int var1) {
      String var2 = var0.getString(var1);
      toast(var0, var2);
   }

   public static void toast(Context var0, CharSequence var1) {
      boolean var2;
      if(var1.length() > 60) {
         var2 = true;
      } else {
         var2 = false;
      }

      byte var3;
      if(var2) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      Toast.makeText(var0, var1, var3).show();
   }

   public static void toast(Context var0, String var1, Object ... var2) {
      String var3 = String.format(var1, var2);
      toast(var0, var3);
   }
}
