package com.google.android.finsky.download.obb;

import com.google.android.finsky.utils.Maps;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;

public enum ObbState {

   // $FF: synthetic field
   private static final ObbState[] $VALUES;
   DOWNLOADED("DOWNLOADED", 2),
   DOWNLOADING("DOWNLOADING", 1),
   DOWNLOAD_PENDING("DOWNLOAD_PENDING", 0),
   NOT_APPLICABLE("NOT_APPLICABLE", 4),
   NOT_ON_STORAGE("NOT_ON_STORAGE", 3);
   private static final Map<Integer, ObbState> sLookup;


   static {
      ObbState[] var0 = new ObbState[5];
      ObbState var1 = DOWNLOAD_PENDING;
      var0[0] = var1;
      ObbState var2 = DOWNLOADING;
      var0[1] = var2;
      ObbState var3 = DOWNLOADED;
      var0[2] = var3;
      ObbState var4 = NOT_ON_STORAGE;
      var0[3] = var4;
      ObbState var5 = NOT_APPLICABLE;
      var0[4] = var5;
      $VALUES = var0;
      sLookup = Maps.newHashMap();
      Iterator var6 = EnumSet.allOf(ObbState.class).iterator();

      while(var6.hasNext()) {
         ObbState var7 = (ObbState)var6.next();
         Map var8 = sLookup;
         Integer var9 = Integer.valueOf(var7.ordinal());
         var8.put(var9, var7);
      }

   }

   private ObbState(String var1, int var2) {}

   public static ObbState getMatchingState(int var0) {
      Map var1 = sLookup;
      Integer var2 = Integer.valueOf(var0);
      return (ObbState)var1.get(var2);
   }

   public static boolean shouldDownload(ObbState var0) {
      ObbState var1 = NOT_ON_STORAGE;
      return var0.equals(var1);
   }
}
