package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;

public interface ApiMethodCallback {

   void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7);
}
