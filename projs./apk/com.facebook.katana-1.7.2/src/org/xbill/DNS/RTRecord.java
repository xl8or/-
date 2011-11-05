package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.U16NameBase;

public class RTRecord extends U16NameBase {

   private static final long serialVersionUID = -3206215651648278098L;


   RTRecord() {}

   public RTRecord(Name var1, int var2, long var3, int var5, Name var6) {
      super(var1, 21, var2, var3, var5, "preference", var6, "intermediateHost");
   }

   public Name getIntermediateHost() {
      return this.getNameField();
   }

   Record getObject() {
      return new RTRecord();
   }

   public int getPreference() {
      return this.getU16Field();
   }
}
