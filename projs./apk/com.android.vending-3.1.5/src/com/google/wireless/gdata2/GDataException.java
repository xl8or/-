package com.google.wireless.gdata2;


public class GDataException extends Exception {

   private final Throwable cause;


   public GDataException() {
      this.cause = null;
   }

   public GDataException(String var1) {
      super(var1);
      this.cause = null;
   }

   public GDataException(String var1, Throwable var2) {
      super(var1);
      this.cause = var2;
   }

   public GDataException(Throwable var1) {
      this("", var1);
   }

   public Throwable getCause() {
      return this.cause;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = super.toString();
      StringBuilder var3 = var1.append(var2);
      String var6;
      if(this.cause != null) {
         StringBuilder var4 = (new StringBuilder()).append(" ");
         String var5 = this.cause.toString();
         var6 = var4.append(var5).toString();
      } else {
         var6 = "";
      }

      return var3.append(var6).toString();
   }
}
