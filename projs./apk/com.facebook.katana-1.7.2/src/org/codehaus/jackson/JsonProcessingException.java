package org.codehaus.jackson;

import java.io.IOException;
import org.codehaus.jackson.JsonLocation;

public class JsonProcessingException extends IOException {

   static final long serialVersionUID = 123L;
   protected JsonLocation mLocation;


   protected JsonProcessingException(String var1) {
      super(var1);
   }

   protected JsonProcessingException(String var1, Throwable var2) {
      this(var1, (JsonLocation)null, var2);
   }

   protected JsonProcessingException(String var1, JsonLocation var2) {
      this(var1, var2, (Throwable)null);
   }

   protected JsonProcessingException(String var1, JsonLocation var2, Throwable var3) {
      super(var1);
      if(var3 != null) {
         this.initCause(var3);
      }

      this.mLocation = var2;
   }

   protected JsonProcessingException(Throwable var1) {
      this((String)null, (JsonLocation)null, var1);
   }

   public JsonLocation getLocation() {
      return this.mLocation;
   }

   public String getMessage() {
      String var1 = super.getMessage();
      if(var1 == null) {
         var1 = "N/A";
      }

      JsonLocation var2 = this.getLocation();
      if(var2 != null) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var1);
         StringBuilder var5 = var3.append('\n');
         StringBuilder var6 = var3.append(" at ");
         String var7 = var2.toString();
         var3.append(var7);
         var1 = var3.toString();
      }

      return var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.getClass().getName();
      StringBuilder var3 = var1.append(var2).append(": ");
      String var4 = this.getMessage();
      return var3.append(var4).toString();
   }
}
