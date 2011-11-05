package com.android.email.wds;


abstract class SimpleIdentifier {

   private String name;


   protected SimpleIdentifier(String var1) {
      this.name = var1;
   }

   public String toString() {
      return this.name;
   }
}
