package com.google.android.finsky.adapters;


public enum ImageLoadState {

   // $FF: synthetic field
   private static final ImageLoadState[] $VALUES;
   ALL("ALL", 2),
   CACHED_ONLY("CACHED_ONLY", 1),
   NONE("NONE", 0);


   static {
      ImageLoadState[] var0 = new ImageLoadState[3];
      ImageLoadState var1 = NONE;
      var0[0] = var1;
      ImageLoadState var2 = CACHED_ONLY;
      var0[1] = var2;
      ImageLoadState var3 = ALL;
      var0[2] = var3;
      $VALUES = var0;
   }

   private ImageLoadState(String var1, int var2) {}
}
