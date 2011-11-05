package com.google.android.finsky.billing.carrierbilling.debug;

import com.google.android.finsky.billing.carrierbilling.debug.DcbDetail;
import com.google.android.finsky.config.GservicesValue;

public class GServicesDetail<E extends Object> implements DcbDetail {

   private final GservicesValue<E> mValue;


   public GServicesDetail(GservicesValue<E> var1) {
      this.mValue = var1;
   }

   public String getTitle() {
      return this.mValue.getKey();
   }

   public String getValue() {
      Object var1 = this.mValue.get();
      String var2;
      if(var1 == null) {
         var2 = "null";
      } else {
         var2 = var1.toString();
      }

      return var2;
   }
}
