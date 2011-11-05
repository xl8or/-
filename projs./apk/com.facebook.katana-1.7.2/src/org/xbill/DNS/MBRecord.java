package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SingleNameBase;

public class MBRecord extends SingleNameBase {

   private static final long serialVersionUID = 532349543479150419L;


   MBRecord() {}

   public MBRecord(Name var1, int var2, long var3, Name var5) {
      super(var1, 7, var2, var3, var5, "mailbox");
   }

   public Name getAdditionalName() {
      return this.getSingleName();
   }

   public Name getMailbox() {
      return this.getSingleName();
   }

   Record getObject() {
      return new MBRecord();
   }
}
