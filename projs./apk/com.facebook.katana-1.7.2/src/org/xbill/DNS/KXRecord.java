package org.xbill.DNS;

import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.U16NameBase;

public class KXRecord extends U16NameBase {

   private static final long serialVersionUID = 7448568832769757809L;


   KXRecord() {}

   public KXRecord(Name var1, int var2, long var3, int var5, Name var6) {
      super(var1, 36, var2, var3, var5, "preference", var6, "target");
   }

   public Name getAdditionalName() {
      return this.getNameField();
   }

   Record getObject() {
      return new KXRecord();
   }

   public int getPreference() {
      return this.getU16Field();
   }

   public Name getTarget() {
      return this.getNameField();
   }
}
