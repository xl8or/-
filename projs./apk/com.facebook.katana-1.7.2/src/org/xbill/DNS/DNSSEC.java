package org.xbill.DNS;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSKEYRecord;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Header;
import org.xbill.DNS.KEYBase;
import org.xbill.DNS.KEYRecord;
import org.xbill.DNS.Message;
import org.xbill.DNS.Mnemonic;
import org.xbill.DNS.Name;
import org.xbill.DNS.RRSIGRecord;
import org.xbill.DNS.RRset;
import org.xbill.DNS.Record;
import org.xbill.DNS.SIGBase;
import org.xbill.DNS.SIGRecord;

public class DNSSEC {

   private static final int ASN1_INT = 2;
   private static final int ASN1_SEQ = 48;
   private static final int DSA_LEN = 20;


   private DNSSEC() {}

   private static int BigIntegerLength(BigInteger var0) {
      return (var0.bitLength() + 7) / 8;
   }

   private static byte[] DSASignaturefromDNS(byte[] var0) throws DNSSEC.DNSSECException, IOException {
      if(var0.length != 41) {
         throw new DNSSEC.SignatureVerificationException();
      } else {
         DNSInput var1 = new DNSInput(var0);
         DNSOutput var2 = new DNSOutput();
         int var3 = var1.readU8();
         byte[] var4 = var1.readByteArray(20);
         int var5;
         if(var4[0] < 0) {
            var5 = 20 + 1;
         } else {
            var5 = 20;
         }

         byte[] var6 = var1.readByteArray(20);
         int var7;
         if(var6[0] < 0) {
            var7 = 20 + 1;
         } else {
            var7 = 20;
         }

         var2.writeU8(48);
         int var8 = var5 + var7 + 4;
         var2.writeU8(var8);
         var2.writeU8(2);
         var2.writeU8(var5);
         if(var5 > 20) {
            var2.writeU8(0);
         }

         var2.writeByteArray(var4);
         var2.writeU8(2);
         var2.writeU8(var7);
         if(var7 > 20) {
            var2.writeU8(0);
         }

         var2.writeByteArray(var6);
         return var2.toByteArray();
      }
   }

   private static byte[] DSASignaturetoDNS(byte[] var0, int var1) throws IOException {
      DNSInput var2 = new DNSInput(var0);
      DNSOutput var3 = new DNSOutput();
      var3.writeU8(var1);
      if(var2.readU8() != 48) {
         throw new IOException();
      } else {
         int var4 = var2.readU8();
         if(var2.readU8() != 2) {
            throw new IOException();
         } else {
            int var5 = var2.readU8();
            if(var5 == 21) {
               if(var2.readU8() != 0) {
                  throw new IOException();
               }
            } else if(var5 != 20) {
               throw new IOException();
            }

            byte[] var6 = var2.readByteArray(20);
            var3.writeByteArray(var6);
            if(var2.readU8() != 2) {
               throw new IOException();
            } else {
               int var7 = var2.readU8();
               if(var7 == 21) {
                  if(var2.readU8() != 0) {
                     throw new IOException();
                  }
               } else if(var7 != 20) {
                  throw new IOException();
               }

               byte[] var8 = var2.readByteArray(20);
               var3.writeByteArray(var8);
               return var3.toByteArray();
            }
         }
      }
   }

   private static String algString(int var0) throws DNSSEC.UnsupportedAlgorithmException {
      String var1;
      switch(var0) {
      case 1:
         var1 = "MD5withRSA";
         break;
      case 2:
      case 4:
      case 9:
      default:
         throw new DNSSEC.UnsupportedAlgorithmException(var0);
      case 3:
      case 6:
         var1 = "SHA1withDSA";
         break;
      case 5:
      case 7:
         var1 = "SHA1withRSA";
         break;
      case 8:
         var1 = "SHA256withRSA";
         break;
      case 10:
         var1 = "SHA512withRSA";
      }

      return var1;
   }

   static void checkAlgorithm(PrivateKey var0, int var1) throws DNSSEC.UnsupportedAlgorithmException {
      switch(var1) {
      case 1:
      case 5:
      case 7:
      case 8:
      case 10:
         if(var0 instanceof RSAPrivateKey) {
            return;
         }

         throw new DNSSEC.IncompatibleKeyException();
      case 2:
      case 4:
      case 9:
      default:
         throw new DNSSEC.UnsupportedAlgorithmException(var1);
      case 3:
      case 6:
         if(!(var0 instanceof DSAPrivateKey)) {
            throw new DNSSEC.IncompatibleKeyException();
         }
      }
   }

   public static byte[] digestMessage(SIGRecord var0, Message var1, byte[] var2) {
      DNSOutput var3 = new DNSOutput();
      digestSIG(var3, var0);
      if(var2 != null) {
         var3.writeByteArray(var2);
      }

      var1.toWire(var3);
      return var3.toByteArray();
   }

   public static byte[] digestRRset(RRSIGRecord var0, RRset var1) {
      DNSOutput var2 = new DNSOutput();
      digestSIG(var2, var0);
      int var3 = var1.size();
      Record[] var4 = new Record[var3];
      Iterator var5 = var1.rrs();
      Name var6 = var1.getName();
      Name var7 = null;
      int var8 = var0.getLabels() + 1;
      if(var6.labels() > var8) {
         int var9 = var6.labels() - var8;
         var7 = var6.wild(var9);
      }

      while(var5.hasNext()) {
         int var10 = var3 + -1;
         Record var11 = (Record)var5.next();
         var4[var10] = var11;
         var3 = var10;
      }

      Arrays.sort(var4);
      DNSOutput var12 = new DNSOutput();
      if(var7 != null) {
         var7.toWireCanonical(var12);
      } else {
         var6.toWireCanonical(var12);
      }

      int var13 = var1.getType();
      var12.writeU16(var13);
      int var14 = var1.getDClass();
      var12.writeU16(var14);
      long var15 = var0.getOrigTTL();
      var12.writeU32(var15);
      int var17 = 0;

      while(true) {
         int var18 = var4.length;
         if(var17 >= var18) {
            return var2.toByteArray();
         }

         byte[] var19 = var12.toByteArray();
         var2.writeByteArray(var19);
         int var20 = var2.current();
         var2.writeU16(0);
         byte[] var21 = var4[var17].rdataToWireCanonical();
         var2.writeByteArray(var21);
         int var22 = var2.current() - var20 - 2;
         var2.save();
         var2.jump(var20);
         var2.writeU16(var22);
         var2.restore();
         ++var17;
      }
   }

   private static void digestSIG(DNSOutput var0, SIGBase var1) {
      int var2 = var1.getTypeCovered();
      var0.writeU16(var2);
      int var3 = var1.getAlgorithm();
      var0.writeU8(var3);
      int var4 = var1.getLabels();
      var0.writeU8(var4);
      long var5 = var1.getOrigTTL();
      var0.writeU32(var5);
      long var7 = var1.getExpire().getTime() / 1000L;
      var0.writeU32(var7);
      long var9 = var1.getTimeSigned().getTime() / 1000L;
      var0.writeU32(var9);
      int var11 = var1.getFootprint();
      var0.writeU16(var11);
      var1.getSigner().toWireCanonical(var0);
   }

   private static byte[] fromDSAPublicKey(DSAPublicKey var0) {
      DNSOutput var1 = new DNSOutput();
      BigInteger var2 = var0.getParams().getQ();
      BigInteger var3 = var0.getParams().getP();
      BigInteger var4 = var0.getParams().getG();
      BigInteger var5 = var0.getY();
      int var6 = (var3.toByteArray().length - 64) / 8;
      var1.writeU8(var6);
      writeBigInteger(var1, var2);
      writeBigInteger(var1, var3);
      writeBigInteger(var1, var4);
      writeBigInteger(var1, var5);
      return var1.toByteArray();
   }

   static byte[] fromPublicKey(PublicKey var0, int var1) throws DNSSEC.DNSSECException {
      byte[] var2;
      switch(var1) {
      case 1:
      case 5:
      case 7:
      case 8:
      case 10:
         if(!(var0 instanceof RSAPublicKey)) {
            throw new DNSSEC.IncompatibleKeyException();
         }

         var2 = fromRSAPublicKey((RSAPublicKey)var0);
         break;
      case 2:
      case 4:
      case 9:
      default:
         throw new DNSSEC.UnsupportedAlgorithmException(var1);
      case 3:
      case 6:
         if(!(var0 instanceof DSAPublicKey)) {
            throw new DNSSEC.IncompatibleKeyException();
         }

         var2 = fromDSAPublicKey((DSAPublicKey)var0);
      }

      return var2;
   }

   private static byte[] fromRSAPublicKey(RSAPublicKey var0) {
      DNSOutput var1 = new DNSOutput();
      BigInteger var2 = var0.getPublicExponent();
      BigInteger var3 = var0.getModulus();
      int var4 = BigIntegerLength(var2);
      if(var4 < 256) {
         var1.writeU8(var4);
      } else {
         var1.writeU8(0);
         var1.writeU16(var4);
      }

      writeBigInteger(var1, var2);
      writeBigInteger(var1, var3);
      return var1.toByteArray();
   }

   static byte[] generateDS(DNSKEYRecord param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   private static boolean matches(SIGBase var0, KEYBase var1) {
      int var2 = var1.getAlgorithm();
      int var3 = var0.getAlgorithm();
      boolean var8;
      if(var2 == var3) {
         int var4 = var1.getFootprint();
         int var5 = var0.getFootprint();
         if(var4 == var5) {
            Name var6 = var1.getName();
            Name var7 = var0.getSigner();
            if(var6.equals(var7)) {
               var8 = true;
               return var8;
            }
         }
      }

      var8 = false;
      return var8;
   }

   private static BigInteger readBigInteger(DNSInput var0) {
      byte[] var1 = var0.readByteArray();
      return new BigInteger(1, var1);
   }

   private static BigInteger readBigInteger(DNSInput var0, int var1) throws IOException {
      byte[] var2 = var0.readByteArray(var1);
      return new BigInteger(1, var2);
   }

   public static RRSIGRecord sign(RRset var0, DNSKEYRecord var1, PrivateKey var2, Date var3, Date var4) throws DNSSEC.DNSSECException {
      int var5 = var1.getAlgorithm();
      checkAlgorithm(var2, var5);
      Name var8 = var0.getName();
      int var9 = var0.getDClass();
      long var10 = var0.getTTL();
      int var12 = var0.getType();
      long var13 = var0.getTTL();
      int var15 = var1.getFootprint();
      Name var16 = var1.getName();
      RRSIGRecord var19 = new RRSIGRecord(var8, var9, var10, var12, var5, var13, var4, var3, var15, var16, (byte[])null);
      PublicKey var20 = var1.getPublicKey();
      byte[] var23 = digestRRset(var19, var0);
      byte[] var28 = sign(var2, var20, var5, var23);
      var19.setSignature(var28);
      return var19;
   }

   private static byte[] sign(PrivateKey var0, PublicKey var1, int var2, byte[] var3) throws DNSSEC.DNSSECException {
      byte[] var5;
      try {
         Signature var4 = Signature.getInstance(algString(var2));
         var4.initSign(var0);
         var4.update(var3);
         var5 = var4.sign();
      } catch (GeneralSecurityException var12) {
         String var9 = var12.toString();
         throw new DNSSEC.DNSSECException(var9);
      }

      byte[] var6 = var5;
      if(var1 instanceof DSAPublicKey) {
         try {
            int var7 = (BigIntegerLength(((DSAPublicKey)var1).getParams().getP()) - 64) / 8;
            var5 = DSASignaturetoDNS(var6, var7);
         } catch (IOException var11) {
            throw new IllegalStateException();
         }
      }

      return var5;
   }

   static SIGRecord signMessage(Message var0, SIGRecord var1, KEYRecord var2, PrivateKey var3, Date var4, Date var5) throws DNSSEC.DNSSECException {
      int var6 = var2.getAlgorithm();
      checkAlgorithm(var3, var6);
      Name var9 = Name.root;
      int var10 = var2.getFootprint();
      Name var11 = var2.getName();
      SIGRecord var14 = new SIGRecord(var9, 255, 0L, 0, var6, 0L, var5, var4, var10, var11, (byte[])null);
      DNSOutput var15 = new DNSOutput();
      digestSIG(var15, var14);
      if(var1 != null) {
         byte[] var18 = var1.getSignature();
         var15.writeByteArray(var18);
      }

      var0.toWire(var15);
      PublicKey var23 = var2.getPublicKey();
      byte[] var24 = var15.toByteArray();
      byte[] var29 = sign(var3, var23, var6, var24);
      var14.setSignature(var29);
      return var14;
   }

   private static PublicKey toDSAPublicKey(KEYBase var0) throws IOException, GeneralSecurityException, DNSSEC.MalformedKeyException {
      byte[] var1 = var0.getKey();
      DNSInput var2 = new DNSInput(var1);
      int var3 = var2.readU8();
      if(var3 > 8) {
         throw new DNSSEC.MalformedKeyException(var0);
      } else {
         BigInteger var4 = readBigInteger(var2, 20);
         int var5 = var3 * 8 + 64;
         BigInteger var6 = readBigInteger(var2, var5);
         int var7 = var3 * 8 + 64;
         BigInteger var8 = readBigInteger(var2, var7);
         int var9 = var3 * 8 + 64;
         BigInteger var10 = readBigInteger(var2, var9);
         KeyFactory var11 = KeyFactory.getInstance("DSA");
         DSAPublicKeySpec var12 = new DSAPublicKeySpec(var10, var6, var4, var8);
         return var11.generatePublic(var12);
      }
   }

   static PublicKey toPublicKey(KEYBase param0) throws DNSSEC.DNSSECException {
      // $FF: Couldn't be decompiled
   }

   private static PublicKey toRSAPublicKey(KEYBase var0) throws IOException, GeneralSecurityException {
      byte[] var1 = var0.getKey();
      DNSInput var2 = new DNSInput(var1);
      int var3 = var2.readU8();
      if(var3 == 0) {
         var3 = var2.readU16();
      }

      BigInteger var4 = readBigInteger(var2, var3);
      BigInteger var5 = readBigInteger(var2);
      KeyFactory var6 = KeyFactory.getInstance("RSA");
      RSAPublicKeySpec var7 = new RSAPublicKeySpec(var5, var4);
      return var6.generatePublic(var7);
   }

   private static void verify(PublicKey var0, int var1, byte[] var2, byte[] var3) throws DNSSEC.DNSSECException {
      byte[] var5;
      if(var0 instanceof DSAPublicKey) {
         byte[] var4;
         try {
            var4 = DSASignaturefromDNS(var3);
         } catch (IOException var10) {
            throw new IllegalStateException();
         }

         var5 = var4;
      } else {
         var5 = var3;
      }

      try {
         Signature var6 = Signature.getInstance(algString(var1));
         var6.initVerify(var0);
         var6.update(var2);
         if(!var6.verify(var5)) {
            throw new DNSSEC.SignatureVerificationException();
         }
      } catch (GeneralSecurityException var9) {
         String var7 = var9.toString();
         throw new DNSSEC.DNSSECException(var7);
      }
   }

   public static void verify(RRset var0, RRSIGRecord var1, DNSKEYRecord var2) throws DNSSEC.DNSSECException {
      if(!matches(var1, var2)) {
         throw new DNSSEC.KeyMismatchException(var2, var1);
      } else {
         Date var3 = new Date();
         Date var4 = var1.getExpire();
         if(var3.compareTo(var4) > 0) {
            Date var5 = var1.getExpire();
            throw new DNSSEC.SignatureExpiredException(var5, var3);
         } else {
            Date var6 = var1.getTimeSigned();
            if(var3.compareTo(var6) < 0) {
               Date var7 = var1.getTimeSigned();
               throw new DNSSEC.SignatureNotYetValidException(var7, var3);
            } else {
               PublicKey var8 = var2.getPublicKey();
               int var9 = var1.getAlgorithm();
               byte[] var10 = digestRRset(var1, var0);
               byte[] var11 = var1.getSignature();
               verify(var8, var9, var10, var11);
            }
         }
      }
   }

   static void verifyMessage(Message var0, byte[] var1, SIGRecord var2, SIGRecord var3, KEYRecord var4) throws DNSSEC.DNSSECException {
      if(!matches(var2, var4)) {
         throw new DNSSEC.KeyMismatchException(var4, var2);
      } else {
         Date var5 = new Date();
         Date var6 = var2.getExpire();
         if(var5.compareTo(var6) > 0) {
            Date var7 = var2.getExpire();
            throw new DNSSEC.SignatureExpiredException(var7, var5);
         } else {
            Date var8 = var2.getTimeSigned();
            if(var5.compareTo(var8) < 0) {
               Date var9 = var2.getTimeSigned();
               throw new DNSSEC.SignatureNotYetValidException(var9, var5);
            } else {
               DNSOutput var10 = new DNSOutput();
               digestSIG(var10, var2);
               if(var3 != null) {
                  byte[] var11 = var3.getSignature();
                  var10.writeByteArray(var11);
               }

               Header var12 = (Header)var0.getHeader().clone();
               var12.decCount(3);
               byte[] var13 = var12.toWire();
               var10.writeByteArray(var13);
               int var14 = var0.sig0start - 12;
               var10.writeByteArray(var1, 12, var14);
               PublicKey var15 = var4.getPublicKey();
               int var16 = var2.getAlgorithm();
               byte[] var17 = var10.toByteArray();
               byte[] var18 = var2.getSignature();
               verify(var15, var16, var17, var18);
            }
         }
      }
   }

   private static void writeBigInteger(DNSOutput var0, BigInteger var1) {
      byte[] var2 = var1.toByteArray();
      if(var2[0] == 0) {
         int var3 = var2.length - 1;
         var0.writeByteArray(var2, 1, var3);
      } else {
         var0.writeByteArray(var2);
      }
   }

   public static class DNSSECException extends Exception {

      DNSSECException(String var1) {
         super(var1);
      }
   }

   public static class SignatureVerificationException extends DNSSEC.DNSSECException {

      SignatureVerificationException() {
         super("signature verification failed");
      }
   }

   public static class KeyMismatchException extends DNSSEC.DNSSECException {

      private KEYBase key;
      private SIGBase sig;


      KeyMismatchException(KEYBase var1, SIGBase var2) {
         StringBuilder var3 = (new StringBuilder()).append("key ");
         Name var4 = var1.getName();
         StringBuilder var5 = var3.append(var4).append("/");
         String var6 = DNSSEC.Algorithm.string(var1.getAlgorithm());
         StringBuilder var7 = var5.append(var6).append("/");
         int var8 = var1.getFootprint();
         StringBuilder var9 = var7.append(var8).append(" ").append("does not match signature ");
         Name var10 = var2.getSigner();
         StringBuilder var11 = var9.append(var10).append("/");
         String var12 = DNSSEC.Algorithm.string(var2.getAlgorithm());
         StringBuilder var13 = var11.append(var12).append("/");
         int var14 = var2.getFootprint();
         String var15 = var13.append(var14).toString();
         super(var15);
      }
   }

   public static class MalformedKeyException extends DNSSEC.DNSSECException {

      MalformedKeyException(KEYBase var1) {
         StringBuilder var2 = (new StringBuilder()).append("Invalid key data: ");
         String var3 = var1.rdataToString();
         String var4 = var2.append(var3).toString();
         super(var4);
      }
   }

   public static class SignatureExpiredException extends DNSSEC.DNSSECException {

      private Date now;
      private Date when;


      SignatureExpiredException(Date var1, Date var2) {
         super("signature expired");
         this.when = var1;
         this.now = var2;
      }

      public Date getExpiration() {
         return this.when;
      }

      public Date getVerifyTime() {
         return this.now;
      }
   }

   public static class SignatureNotYetValidException extends DNSSEC.DNSSECException {

      private Date now;
      private Date when;


      SignatureNotYetValidException(Date var1, Date var2) {
         super("signature is not yet valid");
         this.when = var1;
         this.now = var2;
      }

      public Date getExpiration() {
         return this.when;
      }

      public Date getVerifyTime() {
         return this.now;
      }
   }

   public static class Algorithm {

      public static final int DH = 2;
      public static final int DSA = 3;
      public static final int DSA_NSEC3_SHA1 = 6;
      public static final int ECC = 4;
      public static final int INDIRECT = 252;
      public static final int PRIVATEDNS = 253;
      public static final int PRIVATEOID = 254;
      public static final int RSAMD5 = 1;
      public static final int RSASHA1 = 5;
      public static final int RSASHA256 = 8;
      public static final int RSASHA512 = 10;
      public static final int RSA_NSEC3_SHA1 = 7;
      private static Mnemonic algs = new Mnemonic("DNSSEC algorithm", 2);


      static {
         algs.setMaximum(255);
         algs.setNumericAllowed((boolean)1);
         algs.add(1, "RSAMD5");
         algs.add(2, "DH");
         algs.add(3, "DSA");
         algs.add(4, "ECC");
         algs.add(5, "RSASHA1");
         algs.add(6, "DSA-NSEC3-SHA1");
         algs.add(7, "RSA-NSEC3-SHA1");
         algs.add(8, "RSASHA256");
         algs.add(10, "RSASHA512");
         algs.add(252, "INDIRECT");
         algs.add(253, "PRIVATEDNS");
         algs.add(254, "PRIVATEOID");
      }

      private Algorithm() {}

      public static String string(int var0) {
         return algs.getText(var0);
      }

      public static int value(String var0) {
         return algs.getValue(var0);
      }
   }

   public static class UnsupportedAlgorithmException extends DNSSEC.DNSSECException {

      UnsupportedAlgorithmException(int var1) {
         String var2 = "Unsupported algorithm: " + var1;
         super(var2);
      }
   }

   public static class IncompatibleKeyException extends IllegalArgumentException {

      IncompatibleKeyException() {
         super("incompatible keys");
      }
   }
}
