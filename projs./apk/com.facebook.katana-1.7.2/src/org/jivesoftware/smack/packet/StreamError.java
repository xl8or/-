package org.jivesoftware.smack.packet;


public class StreamError {

   private String code;


   public StreamError(String var1) {
      this.code = var1;
   }

   public String getCode() {
      return this.code;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("stream:error (");
      String var3 = this.code;
      StringBuilder var4 = var2.append(var3).append(")");
      return var1.toString();
   }
}
