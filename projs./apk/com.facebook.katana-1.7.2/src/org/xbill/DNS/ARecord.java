package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.xbill.DNS.Address;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class ARecord extends Record {

   private static final long serialVersionUID = -2172609200849142323L;
   private int addr;


   ARecord() {}

   public ARecord(Name var1, int var2, long var3, InetAddress var5) {
      super(var1, 1, var2, var3);
      if(Address.familyOf(var5) != 1) {
         throw new IllegalArgumentException("invalid IPv4 address");
      } else {
         int var11 = fromArray(var5.getAddress());
         this.addr = var11;
      }
   }

   private static final int fromArray(byte[] var0) {
      int var1 = (var0[0] & 255) << 24;
      int var2 = (var0[1] & 255) << 16;
      int var3 = var1 | var2;
      int var4 = (var0[2] & 255) << 8;
      int var5 = var3 | var4;
      int var6 = var0[3] & 255;
      return var5 | var6;
   }

   private static final byte[] toArray(int var0) {
      byte[] var1 = new byte[4];
      byte var2 = (byte)(var0 >>> 24 & 255);
      var1[0] = var2;
      byte var3 = (byte)(var0 >>> 16 & 255);
      var1[1] = var3;
      byte var4 = (byte)(var0 >>> 8 & 255);
      var1[2] = var4;
      byte var5 = (byte)(var0 & 255);
      var1[3] = var5;
      return var1;
   }

   public InetAddress getAddress() {
      InetAddress var1;
      InetAddress var2;
      try {
         var1 = InetAddress.getByAddress(toArray(this.addr));
      } catch (UnknownHostException var4) {
         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   Record getObject() {
      return new ARecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = fromArray(var1.getAddress(1).getAddress());
      this.addr = var3;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = fromArray(var1.readByteArray(4));
      this.addr = var2;
   }

   String rrToString() {
      return Address.toDottedQuad(toArray(this.addr));
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      long var4 = (long)this.addr & 4294967295L;
      var1.writeU32(var4);
   }
}
