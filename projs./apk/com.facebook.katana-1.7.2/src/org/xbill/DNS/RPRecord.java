package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class RPRecord extends Record {

   private static final long serialVersionUID = 8124584364211337460L;
   private Name mailbox;
   private Name textDomain;


   RPRecord() {}

   public RPRecord(Name var1, int var2, long var3, Name var5, Name var6) {
      super(var1, 17, var2, var3);
      Name var12 = checkName("mailbox", var5);
      this.mailbox = var12;
      Name var13 = checkName("textDomain", var6);
      this.textDomain = var13;
   }

   public Name getMailbox() {
      return this.mailbox;
   }

   Record getObject() {
      return new RPRecord();
   }

   public Name getTextDomain() {
      return this.textDomain;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      Name var3 = var1.getName(var2);
      this.mailbox = var3;
      Name var4 = var1.getName(var2);
      this.textDomain = var4;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      Name var2 = new Name(var1);
      this.mailbox = var2;
      Name var3 = new Name(var1);
      this.textDomain = var3;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      Name var2 = this.mailbox;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      Name var5 = this.textDomain;
      var1.append(var5);
      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      this.mailbox.toWire(var1, (Compression)null, var3);
      this.textDomain.toWire(var1, (Compression)null, var3);
   }
}
