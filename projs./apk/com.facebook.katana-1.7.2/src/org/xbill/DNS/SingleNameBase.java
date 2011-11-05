package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

abstract class SingleNameBase extends Record {

   private static final long serialVersionUID = 262879934209243L;
   protected Name singleName;


   protected SingleNameBase() {}

   protected SingleNameBase(Name var1, int var2, int var3, long var4) {
      super(var1, var2, var3, var4);
   }

   protected SingleNameBase(Name var1, int var2, int var3, long var4, Name var6, String var7) {
      super(var1, var2, var3, var4);
      Name var8 = checkName(var7, var6);
      this.singleName = var8;
   }

   protected Name getSingleName() {
      return this.singleName;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      Name var3 = var1.getName(var2);
      this.singleName = var3;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      Name var2 = new Name(var1);
      this.singleName = var2;
   }

   String rrToString() {
      return this.singleName.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      this.singleName.toWire(var1, (Compression)null, var3);
   }
}
