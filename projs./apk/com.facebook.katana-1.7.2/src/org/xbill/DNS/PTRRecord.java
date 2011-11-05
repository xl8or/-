package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SingleCompressedNameBase;

public class PTRRecord extends SingleCompressedNameBase {

   private static final long serialVersionUID = -8321636610425434192L;


   PTRRecord() {}

   public PTRRecord(Name var1, int var2, long var3, Name var5) {
      super(var1, 12, var2, var3, var5, "target");
   }

   Record getObject() {
      return new PTRRecord();
   }

   public Name getTarget() {
      return this.getSingleName();
   }
}
