package com.google.android.common.base;

import com.google.android.common.base.Function;

public abstract class Escaper {

   private final Function<String, String> asFunction;


   public Escaper() {
      Escaper.1 var1 = new Escaper.1();
      this.asFunction = var1;
   }

   public Function<String, String> asFunction() {
      return this.asFunction;
   }

   public abstract Appendable escape(Appendable var1);

   public abstract String escape(String var1);

   class 1 implements Function<String, String> {

      1() {}

      public String apply(String var1) {
         return Escaper.this.escape(var1);
      }
   }
}
