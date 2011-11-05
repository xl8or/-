package com.facebook.katana.features.imagefilters;

import android.graphics.Bitmap;
import com.facebook.katana.features.imagefilters.ImageFilter;

public class OriginalImageFilter extends ImageFilter {

   private static final String NAME = "Original";


   public OriginalImageFilter() {
      super("Original");
   }

   public Bitmap applyFilter(Bitmap var1) {
      return var1;
   }
}
