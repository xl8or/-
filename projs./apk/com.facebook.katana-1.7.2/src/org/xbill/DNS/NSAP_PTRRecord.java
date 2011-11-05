package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SingleNameBase;

public class NSAP_PTRRecord extends SingleNameBase {

   private static final long serialVersionUID = 2386284746382064904L;


   NSAP_PTRRecord() {}

   public NSAP_PTRRecord(Name var1, int var2, long var3, Name var5) {
      super(var1, 23, var2, var3, var5, "target");
   }

   Record getObject() {
      return new NSAP_PTRRecord();
   }

   public Name getTarget() {
      return this.getSingleName();
   }
}
