package com.android.email.combined;

import android.content.Context;
import com.android.email.Email;
import com.digc.seven.Z7MailHandler;

public class SevenUtils {

   public SevenUtils() {}

   public static void addListener(Context var0, Email.Z7ConnectionListener var1) {
      Z7MailHandler.getInstance(var0).registerListener(var1);
   }

   public static Z7MailHandler getZ7EmailService(Context var0) {
      return Z7MailHandler.getInstance(var0);
   }

   public static void removeListener(Context var0, Email.Z7ConnectionListener var1) {
      Z7MailHandler.getInstance(var0).unRegisterListener(var1);
   }
}
