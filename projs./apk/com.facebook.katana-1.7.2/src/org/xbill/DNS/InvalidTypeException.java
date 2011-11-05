package org.xbill.DNS;


public class InvalidTypeException extends IllegalArgumentException {

   public InvalidTypeException(int var1) {
      String var2 = "Invalid DNS type: " + var1;
      super(var2);
   }
}
