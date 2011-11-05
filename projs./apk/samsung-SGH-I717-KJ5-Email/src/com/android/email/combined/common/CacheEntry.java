package com.android.email.combined.common;


public class CacheEntry {

   private Object im_oValue = null;
   private String im_sKey = null;


   public CacheEntry(String var1, Object var2) {
      this.im_sKey = var1;
      this.im_oValue = var2;
   }

   public String getKey() {
      return this.im_sKey;
   }

   public Object getValue() {
      return this.im_oValue;
   }

   public void setValue(Object var1) {
      this.im_oValue = var1;
   }
}
