package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SingleNameBase;

public class MDRecord extends SingleNameBase {

   private static final long serialVersionUID = 5268878603762942202L;


   MDRecord() {}

   public MDRecord(Name var1, int var2, long var3, Name var5) {
      super(var1, 3, var2, var3, var5, "mail agent");
   }

   public Name getAdditionalName() {
      return this.getSingleName();
   }

   public Name getMailAgent() {
      return this.getSingleName();
   }

   Record getObject() {
      return new MDRecord();
   }
}
