package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.utils.base16;

public class SSHFPRecord extends Record {

   private static final long serialVersionUID = -8104701402654687025L;
   private int alg;
   private int digestType;
   private byte[] fingerprint;


   SSHFPRecord() {}

   public SSHFPRecord(Name var1, int var2, long var3, int var5, int var6, byte[] var7) {
      super(var1, 44, var2, var3);
      int var13 = checkU8("alg", var5);
      this.alg = var13;
      int var14 = checkU8("digestType", var6);
      this.digestType = var14;
      this.fingerprint = var7;
   }

   public int getAlgorithm() {
      return this.alg;
   }

   public int getDigestType() {
      return this.digestType;
   }

   public byte[] getFingerPrint() {
      return this.fingerprint;
   }

   Record getObject() {
      return new SSHFPRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = var1.getUInt8();
      this.alg = var3;
      int var4 = var1.getUInt8();
      this.digestType = var4;
      byte[] var5 = var1.getHex((boolean)1);
      this.fingerprint = var5;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU8();
      this.alg = var2;
      int var3 = var1.readU8();
      this.digestType = var3;
      byte[] var4 = var1.readByteArray();
      this.fingerprint = var4;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.alg;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      int var5 = this.digestType;
      var1.append(var5);
      StringBuffer var7 = var1.append(" ");
      String var8 = base16.toString(this.fingerprint);
      var1.append(var8);
      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.alg;
      var1.writeU8(var4);
      int var5 = this.digestType;
      var1.writeU8(var5);
      byte[] var6 = this.fingerprint;
      var1.writeByteArray(var6);
   }

   public static class Digest {

      public static final int SHA1 = 1;


      private Digest() {}
   }

   public static class Algorithm {

      public static final int DSS = 2;
      public static final int RSA = 1;


      private Algorithm() {}
   }
}
