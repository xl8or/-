package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.utils.base64;

public class DHCIDRecord extends Record {

   private static final long serialVersionUID = -8214820200808997707L;
   private byte[] data;


   DHCIDRecord() {}

   public DHCIDRecord(Name var1, int var2, long var3, byte[] var5) {
      super(var1, 49, var2, var3);
      this.data = var5;
   }

   public byte[] getData() {
      return this.data;
   }

   Record getObject() {
      return new DHCIDRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      byte[] var3 = var1.getBase64();
      this.data = var3;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      byte[] var2 = var1.readByteArray();
      this.data = var2;
   }

   String rrToString() {
      return base64.toString(this.data);
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      byte[] var4 = this.data;
      var1.writeByteArray(var4);
   }
}
