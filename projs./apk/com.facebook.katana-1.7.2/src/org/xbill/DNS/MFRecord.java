package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SingleNameBase;

public class MFRecord extends SingleNameBase {

   private static final long serialVersionUID = -6670449036843028169L;


   MFRecord() {}

   public MFRecord(Name var1, int var2, long var3, Name var5) {
      super(var1, 4, var2, var3, var5, "mail agent");
   }

   public Name getAdditionalName() {
      return this.getSingleName();
   }

   public Name getMailAgent() {
      return this.getSingleName();
   }

   Record getObject() {
      return new MFRecord();
   }
}
