package org.xbill.DNS;

import org.xbill.DNS.WireParseException;

public class NameTooLongException extends WireParseException {

   public NameTooLongException() {}

   public NameTooLongException(String var1) {
      super(var1);
   }
}
