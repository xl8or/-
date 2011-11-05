package com.sonyericsson.email.utils.r2r;

import android.content.Context;
import com.sonyericsson.email.utils.customization.AccountData;
import java.io.Closeable;
import java.io.IOException;

public class R2R {

   public static final String R2R_PATH = "content://com.sonyericsson.r2r.client/email?emaildomain=";
   private static final String TAG_FULL_EMAIL_ADDRESS = "email-address";


   public R2R() {}

   public static boolean addR2RSettings(Context param0, AccountData param1) {
      // $FF: Couldn't be decompiled
   }

   private static void closeStream(Closeable var0) {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var2) {
            ;
         }
      }
   }
}
