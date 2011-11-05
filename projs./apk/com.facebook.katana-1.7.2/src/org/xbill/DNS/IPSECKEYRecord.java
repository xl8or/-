package org.xbill.DNS;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.WireParseException;
import org.xbill.DNS.utils.base64;

public class IPSECKEYRecord extends Record {

   private static final long serialVersionUID = 3050449702765909687L;
   private int algorithmType;
   private Object gateway;
   private int gatewayType;
   private byte[] key;
   private int precedence;


   IPSECKEYRecord() {}

   public IPSECKEYRecord(Name var1, int var2, long var3, int var5, int var6, int var7, Object var8, byte[] var9) {
      super(var1, 45, var2, var3);
      int var15 = checkU8("precedence", var5);
      this.precedence = var15;
      int var16 = checkU8("gatewayType", var6);
      this.gatewayType = var16;
      int var17 = checkU8("algorithmType", var7);
      this.algorithmType = var17;
      switch(var6) {
      case 0:
         this.gateway = null;
         break;
      case 1:
         if(!(var8 instanceof InetAddress)) {
            throw new IllegalArgumentException("\"gateway\" must be an IPv4 address");
         }

         this.gateway = var8;
         break;
      case 2:
         if(!(var8 instanceof Inet6Address)) {
            throw new IllegalArgumentException("\"gateway\" must be an IPv6 address");
         }

         this.gateway = var8;
         break;
      case 3:
         if(!(var8 instanceof Name)) {
            throw new IllegalArgumentException("\"gateway\" must be a DNS name");
         }

         Name var18 = (Name)var8;
         Name var19 = checkName("gateway", var18);
         this.gateway = var19;
         break;
      default:
         throw new IllegalArgumentException("\"gatewayType\" must be between 0 and 3");
      }

      this.key = var9;
   }

   public int getAlgorithmType() {
      return this.algorithmType;
   }

   public Object getGateway() {
      return this.gateway;
   }

   public int getGatewayType() {
      return this.gatewayType;
   }

   public byte[] getKey() {
      return this.key;
   }

   Record getObject() {
      return new IPSECKEYRecord();
   }

   public int getPrecedence() {
      return this.precedence;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = var1.getUInt8();
      this.precedence = var3;
      int var4 = var1.getUInt8();
      this.gatewayType = var4;
      int var5 = var1.getUInt8();
      this.algorithmType = var5;
      switch(this.gatewayType) {
      case 0:
         if(!var1.getString().equals(".")) {
            throw new TextParseException("invalid gateway format");
         }

         this.gateway = null;
         break;
      case 1:
         InetAddress var7 = var1.getAddress(1);
         this.gateway = var7;
         break;
      case 2:
         InetAddress var8 = var1.getAddress(2);
         this.gateway = var8;
         break;
      case 3:
         Name var9 = var1.getName(var2);
         this.gateway = var9;
         break;
      default:
         throw new WireParseException("invalid gateway type");
      }

      byte[] var6 = var1.getBase64((boolean)0);
      this.key = var6;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU8();
      this.precedence = var2;
      int var3 = var1.readU8();
      this.gatewayType = var3;
      int var4 = var1.readU8();
      this.algorithmType = var4;
      switch(this.gatewayType) {
      case 0:
         this.gateway = null;
         break;
      case 1:
         InetAddress var6 = InetAddress.getByAddress(var1.readByteArray(4));
         this.gateway = var6;
         break;
      case 2:
         InetAddress var7 = InetAddress.getByAddress(var1.readByteArray(16));
         this.gateway = var7;
         break;
      case 3:
         Name var8 = new Name(var1);
         this.gateway = var8;
         break;
      default:
         throw new WireParseException("invalid gateway type");
      }

      if(var1.remaining() > 0) {
         byte[] var5 = var1.readByteArray();
         this.key = var5;
      }
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.precedence;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      int var5 = this.gatewayType;
      var1.append(var5);
      StringBuffer var7 = var1.append(" ");
      int var8 = this.algorithmType;
      var1.append(var8);
      StringBuffer var10 = var1.append(" ");
      switch(this.gatewayType) {
      case 0:
         StringBuffer var14 = var1.append(".");
         break;
      case 1:
      case 2:
         String var15 = ((InetAddress)this.gateway).getHostAddress();
         var1.append(var15);
         break;
      case 3:
         Object var17 = this.gateway;
         var1.append(var17);
      }

      if(this.key != null) {
         StringBuffer var11 = var1.append(" ");
         String var12 = base64.toString(this.key);
         var1.append(var12);
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.precedence;
      var1.writeU8(var4);
      int var5 = this.gatewayType;
      var1.writeU8(var5);
      int var6 = this.algorithmType;
      var1.writeU8(var6);
      switch(this.gatewayType) {
      case 0:
      default:
         break;
      case 1:
      case 2:
         byte[] var8 = ((InetAddress)this.gateway).getAddress();
         var1.writeByteArray(var8);
         break;
      case 3:
         ((Name)this.gateway).toWire(var1, (Compression)null, var3);
      }

      if(this.key != null) {
         byte[] var7 = this.key;
         var1.writeByteArray(var7);
      }
   }

   public static class Gateway {

      public static final int IPv4 = 1;
      public static final int IPv6 = 2;
      public static final int Name = 3;
      public static final int None;


      private Gateway() {}
   }

   public static class Algorithm {

      public static final int DSA = 1;
      public static final int RSA = 2;


      private Algorithm() {}
   }
}
