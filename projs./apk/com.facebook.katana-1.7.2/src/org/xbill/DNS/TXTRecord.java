package org.xbill.DNS;

import java.util.List;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TXTBase;

public class TXTRecord extends TXTBase {

   private static final long serialVersionUID = -5780785764284221342L;


   TXTRecord() {}

   public TXTRecord(Name var1, int var2, long var3, String var5) {
      super(var1, 16, var2, var3, var5);
   }

   public TXTRecord(Name var1, int var2, long var3, List var5) {
      super(var1, 16, var2, var3, var5);
   }

   Record getObject() {
      return new TXTRecord();
   }
}
