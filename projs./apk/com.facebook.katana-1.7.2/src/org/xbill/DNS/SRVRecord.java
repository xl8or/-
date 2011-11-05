package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class SRVRecord extends Record {

   private static final long serialVersionUID = -3886460132387522052L;
   private int port;
   private int priority;
   private Name target;
   private int weight;


   SRVRecord() {}

   public SRVRecord(Name var1, int var2, long var3, int var5, int var6, int var7, Name var8) {
      super(var1, 33, var2, var3);
      int var14 = checkU16("priority", var5);
      this.priority = var14;
      int var15 = checkU16("weight", var6);
      this.weight = var15;
      int var16 = checkU16("port", var7);
      this.port = var16;
      Name var17 = checkName("target", var8);
      this.target = var17;
   }

   public Name getAdditionalName() {
      return this.target;
   }

   Record getObject() {
      return new SRVRecord();
   }

   public int getPort() {
      return this.port;
   }

   public int getPriority() {
      return this.priority;
   }

   public Name getTarget() {
      return this.target;
   }

   public int getWeight() {
      return this.weight;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = var1.getUInt16();
      this.priority = var3;
      int var4 = var1.getUInt16();
      this.weight = var4;
      int var5 = var1.getUInt16();
      this.port = var5;
      Name var6 = var1.getName(var2);
      this.target = var6;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU16();
      this.priority = var2;
      int var3 = var1.readU16();
      this.weight = var3;
      int var4 = var1.readU16();
      this.port = var4;
      Name var5 = new Name(var1);
      this.target = var5;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      StringBuilder var2 = new StringBuilder();
      int var3 = this.priority;
      String var4 = var2.append(var3).append(" ").toString();
      var1.append(var4);
      StringBuilder var6 = new StringBuilder();
      int var7 = this.weight;
      String var8 = var6.append(var7).append(" ").toString();
      var1.append(var8);
      StringBuilder var10 = new StringBuilder();
      int var11 = this.port;
      String var12 = var10.append(var11).append(" ").toString();
      var1.append(var12);
      Name var14 = this.target;
      var1.append(var14);
      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.priority;
      var1.writeU16(var4);
      int var5 = this.weight;
      var1.writeU16(var5);
      int var6 = this.port;
      var1.writeU16(var6);
      this.target.toWire(var1, (Compression)null, var3);
   }
}
