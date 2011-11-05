package org.xbill.DNS;

import java.util.List;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TXTBase;

public class SPFRecord extends TXTBase {

   private static final long serialVersionUID = -2100754352801658722L;


   SPFRecord() {}

   public SPFRecord(Name var1, int var2, long var3, String var5) {
      super(var1, 99, var2, var3, var5);
   }

   public SPFRecord(Name var1, int var2, long var3, List var5) {
      super(var1, 99, var2, var3, var5);
   }

   Record getObject() {
      return new SPFRecord();
   }
}
