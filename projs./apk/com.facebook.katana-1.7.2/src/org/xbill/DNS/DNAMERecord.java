package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SingleNameBase;

public class DNAMERecord extends SingleNameBase {

   private static final long serialVersionUID = 2670767677200844154L;


   DNAMERecord() {}

   public DNAMERecord(Name var1, int var2, long var3, Name var5) {
      super(var1, 39, var2, var3, var5, "alias");
   }

   public Name getAlias() {
      return this.getSingleName();
   }

   Record getObject() {
      return new DNAMERecord();
   }

   public Name getTarget() {
      return this.getSingleName();
   }
}
