package org.xbill.DNS;

import org.xbill.DNS.Name;

public class RelativeNameException extends IllegalArgumentException {

   public RelativeNameException(String var1) {
      super(var1);
   }

   public RelativeNameException(Name var1) {
      String var2 = "\'" + var1 + "\' is not an absolute name";
      super(var2);
   }
}
