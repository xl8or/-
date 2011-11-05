package com.google.android.finsky.billing.carrierbilling.debug;

import com.google.android.finsky.billing.carrierbilling.debug.DcbDetail;

public class SimpleDetail implements DcbDetail {

   private final String mTitle;
   private final String mValue;


   public SimpleDetail(String var1, String var2) {
      this.mTitle = var1;
      this.mValue = var2;
   }

   public String getTitle() {
      return this.mTitle;
   }

   public String getValue() {
      return this.mValue;
   }
}
