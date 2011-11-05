package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class NULLRecord extends Record {

   private static final long serialVersionUID = -5796493183235216538L;
   private byte[] data;


   NULLRecord() {}

   public NULLRecord(Name var1, int var2, long var3, byte[] var5) {
      super(var1, 10, var2, var3);
      if(var5.length > '\uffff') {
         throw new IllegalArgumentException("data must be <65536 bytes");
      } else {
         this.data = var5;
      }
   }

   public byte[] getData() {
      return this.data;
   }

   Record getObject() {
      return new NULLRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      throw var1.exception("no defined text format for NULL records");
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
