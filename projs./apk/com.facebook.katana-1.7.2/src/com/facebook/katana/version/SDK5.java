package com.facebook.katana.version;

import android.media.ExifInterface;
import android.webkit.WebStorage;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import java.io.IOException;

public class SDK5 {

   public SDK5() {}

   public static void clearWebStorage() {
      WebStorage.getInstance().deleteAllData();
   }

   public static int getJpegExifOrientation(String var0) {
      ExifInterface var1;
      short var2;
      try {
         var1 = new ExifInterface(var0);
      } catch (IOException var4) {
         var2 = -1;
         return var2;
      }

      switch(var1.getAttributeInt("Orientation", 1)) {
      case 1:
         var2 = 0;
         break;
      case 2:
      case 4:
      case 5:
      case 7:
      default:
         var2 = -1;
         break;
      case 3:
         var2 = 180;
         break;
      case 6:
         var2 = 90;
         break;
      case 8:
         var2 = 270;
      }

      return var2;
   }

   public static boolean isQuickContactBadge(ImageView var0) {
      return var0 instanceof QuickContactBadge;
   }
}
