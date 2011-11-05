package com.android.email.mail;

import com.android.email.mail.MessagingException;

public class DeviceAccessException extends MessagingException {

   public DeviceAccessException(int var1, int var2) {
      super(var1, var2);
   }

   public DeviceAccessException(int var1, String var2) {
      super(var1, var2);
   }
}
