package com.seven.Z7.shared;

import com.seven.Z7.shared.Z7IDLCallbackType;

public class EmailServiceConstants {

   public EmailServiceConstants() {}

   public static enum EmailCallbackType implements Z7IDLCallbackType {

      // $FF: synthetic field
      private static final EmailServiceConstants.EmailCallbackType[] $VALUES;
      EMAIL_CALLBACK_CHECK_MAIL_SUBMITTED("EMAIL_CALLBACK_CHECK_MAIL_SUBMITTED", 0);
      static int base;


      static {
         EmailServiceConstants.EmailCallbackType[] var0 = new EmailServiceConstants.EmailCallbackType[1];
         EmailServiceConstants.EmailCallbackType var1 = EMAIL_CALLBACK_CHECK_MAIL_SUBMITTED;
         var0[0] = var1;
         $VALUES = var0;
         base = 200;
      }

      private EmailCallbackType(String var1, int var2) {}

      public static EmailServiceConstants.EmailCallbackType fromId(int var0) {
         EmailServiceConstants.EmailCallbackType[] var1 = values();
         int var2 = base;
         int var3 = var0 - var2;
         EmailServiceConstants.EmailCallbackType var5;
         if(var3 >= 0) {
            int var4 = var1.length;
            if(var3 < var4) {
               var5 = var1[var3];
               return var5;
            }
         }

         var5 = null;
         return var5;
      }

      public int getEventId() {
         int var1 = this.ordinal();
         int var2 = base;
         return var1 + var2;
      }
   }
}
