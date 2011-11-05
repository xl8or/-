package org.xbill.DNS;

import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.U16NameBase;

public class MXRecord extends U16NameBase {

   private static final long serialVersionUID = 2914841027584208546L;


   MXRecord() {}

   public MXRecord(Name var1, int var2, long var3, int var5, Name var6) {
      super(var1, 15, var2, var3, var5, "priority", var6, "target");
   }

   public Name getAdditionalName() {
      return this.getNameField();
   }

   Record getObject() {
      return new MXRecord();
   }

   public int getPriority() {
      return this.getU16Field();
   }

   public Name getTarget() {
      return this.getNameField();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.u16Field;
      var1.writeU16(var4);
      this.nameField.toWire(var1, var2, var3);
   }
}
