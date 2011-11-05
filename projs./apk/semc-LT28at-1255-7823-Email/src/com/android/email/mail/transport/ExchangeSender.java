package com.android.email.mail.transport;

import android.app.Activity;
import android.content.Context;
import com.android.email.mail.Sender;

public class ExchangeSender extends Sender {

   private ExchangeSender(Context var1, String var2) {}

   public static Sender newInstance(Context var0, String var1) {
      return new ExchangeSender(var0, var1);
   }

   public void close() {}

   public Class<? extends Activity> getSettingActivityClass() {
      return null;
   }

   public void open() {}

   public void sendMessage(long var1) {}
}
