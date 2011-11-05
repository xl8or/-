package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

abstract class U16NameBase extends Record {

   private static final long serialVersionUID = -8315884183112502995L;
   protected Name nameField;
   protected int u16Field;


   protected U16NameBase() {}

   protected U16NameBase(Name var1, int var2, int var3, long var4) {
      super(var1, var2, var3, var4);
   }

   protected U16NameBase(Name var1, int var2, int var3, long var4, int var6, String var7, Name var8, String var9) {
      super(var1, var2, var3, var4);
      int var10 = checkU16(var7, var6);
      this.u16Field = var10;
      Name var11 = checkName(var9, var8);
      this.nameField = var11;
   }

   protected Name getNameField() {
      return this.nameField;
   }

   protected int getU16Field() {
      return this.u16Field;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = var1.getUInt16();
      this.u16Field = var3;
      Name var4 = var1.getName(var2);
      this.nameField = var4;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU16();
      this.u16Field = var2;
      Name var3 = new Name(var1);
      this.nameField = var3;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.u16Field;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      Name var5 = this.nameField;
      var1.append(var5);
      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.u16Field;
      var1.writeU16(var4);
      this.nameField.toWire(var1, (Compression)null, var3);
   }
}
