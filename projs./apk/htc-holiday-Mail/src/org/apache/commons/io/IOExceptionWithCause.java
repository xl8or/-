package org.apache.commons.io;

import java.io.IOException;

public class IOExceptionWithCause extends IOException {

   private static final long serialVersionUID = 1L;


   public IOExceptionWithCause(String var1, Throwable var2) {
      super(var1);
      this.initCause(var2);
   }

   public IOExceptionWithCause(Throwable var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toString();
      }

      super(var2);
      this.initCause(var1);
   }
}
