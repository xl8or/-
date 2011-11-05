package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.DNSSEC;
import org.xbill.DNS.Mnemonic;
import org.xbill.DNS.Name;
import org.xbill.DNS.Options;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.utils.base64;

public class CERTRecord extends Record {

   public static final int OID = 254;
   public static final int PGP = 3;
   public static final int PKIX = 1;
   public static final int SPKI = 2;
   public static final int URI = 253;
   private static final long serialVersionUID = 4763014646517016835L;
   private int alg;
   private byte[] cert;
   private int certType;
   private int keyTag;


   CERTRecord() {}

   public CERTRecord(Name var1, int var2, long var3, int var5, int var6, int var7, byte[] var8) {
      super(var1, 37, var2, var3);
      int var14 = checkU16("certType", var5);
      this.certType = var14;
      int var15 = checkU16("keyTag", var6);
      this.keyTag = var15;
      int var16 = checkU8("alg", var7);
      this.alg = var16;
      this.cert = var8;
   }

   public int getAlgorithm() {
      return this.alg;
   }

   public byte[] getCert() {
      return this.cert;
   }

   public int getCertType() {
      return this.certType;
   }

   public int getKeyTag() {
      return this.keyTag;
   }

   Record getObject() {
      return new CERTRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      String var3 = var1.getString();
      int var4 = CERTRecord.CertificateType.value(var3);
      this.certType = var4;
      if(this.certType < 0) {
         String var5 = "Invalid certificate type: " + var3;
         throw var1.exception(var5);
      } else {
         int var6 = var1.getUInt16();
         this.keyTag = var6;
         String var7 = var1.getString();
         int var8 = DNSSEC.Algorithm.value(var7);
         this.alg = var8;
         if(this.alg < 0) {
            String var9 = "Invalid algorithm: " + var7;
            throw var1.exception(var9);
         } else {
            byte[] var10 = var1.getBase64();
            this.cert = var10;
         }
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU16();
      this.certType = var2;
      int var3 = var1.readU16();
      this.keyTag = var3;
      int var4 = var1.readU8();
      this.alg = var4;
      byte[] var5 = var1.readByteArray();
      this.cert = var5;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.certType;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      int var5 = this.keyTag;
      var1.append(var5);
      StringBuffer var7 = var1.append(" ");
      int var8 = this.alg;
      var1.append(var8);
      if(this.cert != null) {
         if(Options.check("multiline")) {
            StringBuffer var10 = var1.append(" (\n");
            String var11 = base64.formatString(this.cert, 64, "\t", (boolean)1);
            var1.append(var11);
         } else {
            StringBuffer var13 = var1.append(" ");
            String var14 = base64.toString(this.cert);
            var1.append(var14);
         }
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.certType;
      var1.writeU16(var4);
      int var5 = this.keyTag;
      var1.writeU16(var5);
      int var6 = this.alg;
      var1.writeU8(var6);
      byte[] var7 = this.cert;
      var1.writeByteArray(var7);
   }

   public static class CertificateType {

      public static final int ACPKIX = 7;
      public static final int IACPKIX = 8;
      public static final int IPGP = 6;
      public static final int IPKIX = 4;
      public static final int ISPKI = 5;
      public static final int OID = 254;
      public static final int PGP = 3;
      public static final int PKIX = 1;
      public static final int SPKI = 2;
      public static final int URI = 253;
      private static Mnemonic types = new Mnemonic("Certificate type", 2);


      static {
         types.setMaximum('\uffff');
         types.setNumericAllowed((boolean)1);
         types.add(1, "PKIX");
         types.add(2, "SPKI");
         types.add(3, "PGP");
         types.add(1, "IPKIX");
         types.add(2, "ISPKI");
         types.add(3, "IPGP");
         types.add(3, "ACPKIX");
         types.add(3, "IACPKIX");
         types.add(253, "URI");
         types.add(254, "OID");
      }

      private CertificateType() {}

      public static String string(int var0) {
         return types.getText(var0);
      }

      public static int value(String var0) {
         return types.getValue(var0);
      }
   }
}
