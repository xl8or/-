package org.xbill.DNS;


public class InvalidTTLException extends IllegalArgumentException {

   public InvalidTTLException(long var1) {
      String var3 = "Invalid DNS TTL: " + var1;
      super(var3);
   }
}
