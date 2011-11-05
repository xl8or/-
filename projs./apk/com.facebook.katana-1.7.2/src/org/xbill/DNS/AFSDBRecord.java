package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.U16NameBase;

public class AFSDBRecord extends U16NameBase {

   private static final long serialVersionUID = 3034379930729102437L;


   AFSDBRecord() {}

   public AFSDBRecord(Name var1, int var2, long var3, int var5, Name var6) {
      super(var1, 18, var2, var3, var5, "subtype", var6, "host");
   }

   public Name getHost() {
      return this.getNameField();
   }

   Record getObject() {
      return new AFSDBRecord();
   }

   public int getSubtype() {
      return this.getU16Field();
   }
}
