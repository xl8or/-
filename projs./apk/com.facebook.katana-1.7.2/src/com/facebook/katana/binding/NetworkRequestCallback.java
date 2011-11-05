package com.facebook.katana.binding;

import android.content.Context;

public interface NetworkRequestCallback<A extends Object, T extends Object, E extends Object> {

   void callback(Context var1, boolean var2, A var3, String var4, T var5, E var6);
}
