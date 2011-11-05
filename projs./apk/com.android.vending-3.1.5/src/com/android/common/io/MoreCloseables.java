package com.android.common.io;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import java.io.IOException;

public class MoreCloseables {

   public MoreCloseables() {}

   public static void closeQuietly(AssetFileDescriptor var0) {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var2) {
            ;
         }
      }
   }

   public static void closeQuietly(Cursor var0) {
      if(var0 != null) {
         var0.close();
      }
   }
}
