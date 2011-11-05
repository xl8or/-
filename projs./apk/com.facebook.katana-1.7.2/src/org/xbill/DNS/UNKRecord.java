package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class UNKRecord extends Record {

   private static final long serialVersionUID = -4193583311594626915L;
   private byte[] data;


   UNKRecord() {}

   public byte[] getData() {
      return this.data;
   }

   Record getObject() {
      return new UNKRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      throw var1.exception("invalid unknown RR encoding");
   }

   void rrFromWire(DNSInput var1) throws IOException {
      byte[] var2 = var1.readByteArray();
      this.data = var2;
   }

   String rrToString() {
      return unknownToString(this.data);
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      byte[] var4 = this.data;
      var1.writeByteArray(var4);
   }
}
