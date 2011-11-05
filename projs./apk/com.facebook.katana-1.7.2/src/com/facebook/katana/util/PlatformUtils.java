package com.facebook.katana.util;

import android.content.Context;
import com.facebook.katana.platform.PlatformStorage;

public final class PlatformUtils {

   private static boolean sPlatformEclairOrLater;
   private static boolean sPlatformEclairOrLaterDetected = 0;
   private static boolean sStorageSupported;
   private static boolean sStorageSupportedDetected = 0;


   public PlatformUtils() {}

   public static void fixContacts(Context var0) {
      if(isEclairOrLater()) {
         if(!platformStorageSupported(var0)) {
            PlatformStorage.fixContactsHelper(var0);
         }
      }
   }

   public static boolean isEclairOrLater() {
      // $FF: Couldn't be decompiled
   }

   public static boolean platformStorageSupported(Context param0) {
      // $FF: Couldn't be decompiled
   }
}
