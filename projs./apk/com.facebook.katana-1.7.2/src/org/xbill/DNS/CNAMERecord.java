package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SingleCompressedNameBase;

public class CNAMERecord extends SingleCompressedNameBase {

   private static final long serialVersionUID = -4020373886892538580L;


   CNAMERecord() {}

   public CNAMERecord(Name var1, int var2, long var3, Name var5) {
      super(var1, 5, var2, var3, var5, "alias");
   }

   public Name getAlias() {
      return this.getSingleName();
   }

   Record getObject() {
      return new CNAMERecord();
   }

   public Name getTarget() {
      return this.getSingleName();
   }
}
