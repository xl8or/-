package com.facebook.katana.service.method;

import com.facebook.katana.service.method.ApiMethod;

public interface ApiMethodListener {

   void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4);

   void onOperationProgress(ApiMethod var1, long var2, long var4);

   void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4);
}
