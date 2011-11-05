package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SingleCompressedNameBase;

public class NSRecord extends SingleCompressedNameBase {

   private static final long serialVersionUID = 487170758138268838L;


   NSRecord() {}

   public NSRecord(Name var1, int var2, long var3, Name var5) {
      super(var1, 2, var2, var3, var5, "target");
   }

   public Name getAdditionalName() {
      return this.getSingleName();
   }

   Record getObject() {
      return new NSRecord();
   }

   public Name getTarget() {
      return this.getSingleName();
   }
}
