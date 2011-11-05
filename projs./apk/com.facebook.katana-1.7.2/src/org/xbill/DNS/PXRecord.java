package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class PXRecord extends Record {

   private static final long serialVersionUID = 1811540008806660667L;
   private Name map822;
   private Name mapX400;
   private int preference;


   PXRecord() {}

   public PXRecord(Name var1, int var2, long var3, int var5, Name var6, Name var7) {
      super(var1, 26, var2, var3);
      int var13 = checkU16("preference", var5);
      this.preference = var13;
      Name var14 = checkName("map822", var6);
      this.map822 = var14;
      Name var15 = checkName("mapX400", var7);
      this.mapX400 = var15;
   }

   public Name getMap822() {
      return this.map822;
   }

   public Name getMapX400() {
      return this.mapX400;
   }

   Record getObject() {
      return new PXRecord();
   }

   public int getPreference() {
      return this.preference;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      int var3 = var1.getUInt16();
      this.preference = var3;
      Name var4 = var1.getName(var2);
      this.map822 = var4;
      Name var5 = var1.getName(var2);
      this.mapX400 = var5;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      int var2 = var1.readU16();
      this.preference = var2;
      Name var3 = new Name(var1);
      this.map822 = var3;
      Name var4 = new Name(var1);
      this.mapX400 = var4;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.preference;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      Name var5 = this.map822;
      var1.append(var5);
      StringBuffer var7 = var1.append(" ");
      Name var8 = this.mapX400;
      var1.append(var8);
      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      int var4 = this.preference;
      var1.writeU16(var4);
      this.map822.toWire(var1, (Compression)null, var3);
      this.mapX400.toWire(var1, (Compression)null, var3);
   }
}
