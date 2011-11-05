package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class MINFORecord extends Record {

   private static final long serialVersionUID = -3962147172340353796L;
   private Name errorAddress;
   private Name responsibleAddress;


   MINFORecord() {}

   public MINFORecord(Name var1, int var2, long var3, Name var5, Name var6) {
      super(var1, 14, var2, var3);
      Name var12 = checkName("responsibleAddress", var5);
      this.responsibleAddress = var12;
      Name var13 = checkName("errorAddress", var6);
      this.errorAddress = var13;
   }

   public Name getErrorAddress() {
      return this.errorAddress;
   }

   Record getObject() {
      return new MINFORecord();
   }

   public Name getResponsibleAddress() {
      return this.responsibleAddress;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      Name var3 = var1.getName(var2);
      this.responsibleAddress = var3;
      Name var4 = var1.getName(var2);
      this.errorAddress = var4;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      Name var2 = new Name(var1);
      this.responsibleAddress = var2;
      Name var3 = new Name(var1);
      this.errorAddress = var3;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      Name var2 = this.responsibleAddress;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      Name var5 = this.errorAddress;
      var1.append(var5);
      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      this.responsibleAddress.toWire(var1, (Compression)null, var3);
      this.errorAddress.toWire(var1, (Compression)null, var3);
   }
}
