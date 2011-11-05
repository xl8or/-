package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import org.xbill.DNS.Address;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class AAAARecord extends Record {

   private static final long serialVersionUID = -4588601512069748050L;
   private InetAddress address;


   AAAARecord() {}

   public AAAARecord(Name var1, int var2, long var3, InetAddress var5) {
      super(var1, 28, var2, var3);
      if(Address.familyOf(var5) != 2) {
         throw new IllegalArgumentException("invalid IPv6 address");
      } else {
         this.address = var5;
      }
   }

   public InetAddress getAddress() {
      return this.address;
   }

   Record getObject() {
      return new AAAARecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      InetAddress var3 = var1.getAddress(2);
      this.address = var3;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      InetAddress var2 = InetAddress.getByAddress(var1.readByteArray(16));
      this.address = var2;
   }

   String rrToString() {
      return this.address.getHostAddress();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      byte[] var4 = this.address.getAddress();
      var1.writeByteArray(var4);
   }
}
