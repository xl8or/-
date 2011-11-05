package com.facebook.katana.service.method;

import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;

public class BaseApiMethodListener implements ApiMethodListener {

   public BaseApiMethodListener() {}

   public void onOperationComplete(ApiMethod var1, int var2, String var3, Exception var4) {}

   public void onOperationProgress(ApiMethod var1, long var2, long var4) {}

   public void onProcessComplete(ApiMethod var1, int var2, String var3, Exception var4) {}
}
