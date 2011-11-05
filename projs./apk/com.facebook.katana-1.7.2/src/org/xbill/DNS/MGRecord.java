package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SingleNameBase;

public class MGRecord extends SingleNameBase {

   private static final long serialVersionUID = -3980055550863644582L;


   MGRecord() {}

   public MGRecord(Name var1, int var2, long var3, Name var5) {
      super(var1, 8, var2, var3, var5, "mailbox");
   }

   public Name getMailbox() {
      return this.getSingleName();
   }

   Record getObject() {
      return new MGRecord();
   }
}
