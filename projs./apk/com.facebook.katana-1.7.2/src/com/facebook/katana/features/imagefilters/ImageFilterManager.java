package com.facebook.katana.features.imagefilters;

import com.facebook.katana.features.imagefilters.ImageFilter;
import com.facebook.katana.features.imagefilters.OriginalImageFilter;

public class ImageFilterManager {

   public ImageFilterManager() {}

   public static ImageFilter createFilter(ImageFilterManager.Filters var0) {
      int[] var1 = ImageFilterManager.1.$SwitchMap$com$facebook$katana$features$imagefilters$ImageFilterManager$Filters;
      int var2 = var0.ordinal();
      OriginalImageFilter var3;
      switch(var1[var2]) {
      case 1:
         var3 = new OriginalImageFilter();
         break;
      default:
         var3 = null;
      }

      return var3;
   }

   public int getNumFilters() {
      return ImageFilterManager.Filters.values().length;
   }

   public static enum Filters {

      // $FF: synthetic field
      private static final ImageFilterManager.Filters[] $VALUES;
      ORIGINAL_FILTER("ORIGINAL_FILTER", 0);


      static {
         ImageFilterManager.Filters[] var0 = new ImageFilterManager.Filters[1];
         ImageFilterManager.Filters var1 = ORIGINAL_FILTER;
         var0[0] = var1;
         $VALUES = var0;
      }

      private Filters(String var1, int var2) {}
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$facebook$katana$features$imagefilters$ImageFilterManager$Filters = new int[ImageFilterManager.Filters.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$facebook$katana$features$imagefilters$ImageFilterManager$Filters;
            int var1 = ImageFilterManager.Filters.ORIGINAL_FILTER.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }
      }
   }
}
