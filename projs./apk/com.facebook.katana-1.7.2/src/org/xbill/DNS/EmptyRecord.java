package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

class EmptyRecord extends Record {

   private static final long serialVersionUID = 3601852050646429582L;


   EmptyRecord() {}

   Record getObject() {
      return new EmptyRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {}

   void rrFromWire(DNSInput var1) throws IOException {}

   String rrToString() {
      return "";
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {}
}
