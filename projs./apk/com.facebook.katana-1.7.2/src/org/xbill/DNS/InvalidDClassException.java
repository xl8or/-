package org.xbill.DNS;


public class InvalidDClassException extends IllegalArgumentException {

   public InvalidDClassException(int var1) {
      String var2 = "Invalid DNS class: " + var1;
      super(var2);
   }
}
