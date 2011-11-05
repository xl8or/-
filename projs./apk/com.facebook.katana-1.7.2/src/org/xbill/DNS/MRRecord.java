package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SingleNameBase;

public class MRRecord extends SingleNameBase {

   private static final long serialVersionUID = -5617939094209927533L;


   MRRecord() {}

   public MRRecord(Name var1, int var2, long var3, Name var5) {
      super(var1, 9, var2, var3, var5, "new name");
   }

   public Name getNewName() {
      return this.getSingleName();
   }

   Record getObject() {
      return new MRRecord();
   }
}
