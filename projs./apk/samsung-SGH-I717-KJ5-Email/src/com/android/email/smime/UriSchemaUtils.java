package com.android.email.smime;

import android.content.Context;
import android.net.Uri;
import java.io.IOException;

public class UriSchemaUtils {

   private static String TAG = "UriSchemaUtils";


   public UriSchemaUtils() {}

   public static UriSchemaUtils.UtilFile getFile(Context param0, Uri param1, String param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   static class UtilFile {

      public String mFileName;
      public boolean mIsTemp;


      UtilFile(String var1, boolean var2) {
         this.mIsTemp = var2;
         this.mFileName = var1;
      }
   }
}
