package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Options;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;

public class SOARecord extends Record {

   private static final long serialVersionUID = 1049740098229303931L;
   private Name admin;
   private long expire;
   private Name host;
   private long minimum;
   private long refresh;
   private long retry;
   private long serial;


   SOARecord() {}

   public SOARecord(Name param1, int param2, long param3, Name param5, Name param6, long param7, long param9, long param11, long param13, long param15) {
      // $FF: Couldn't be decompiled
   }

   public Name getAdmin() {
      return this.admin;
   }

   public long getExpire() {
      return this.expire;
   }

   public Name getHost() {
      return this.host;
   }

   public long getMinimum() {
      return this.minimum;
   }

   Record getObject() {
      return new SOARecord();
   }

   public long getRefresh() {
      return this.refresh;
   }

   public long getRetry() {
      return this.retry;
   }

   public long getSerial() {
      return this.serial;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      Name var3 = var1.getName(var2);
      this.host = var3;
      Name var4 = var1.getName(var2);
      this.admin = var4;
      long var5 = var1.getUInt32();
      this.serial = var5;
      long var7 = var1.getTTLLike();
      this.refresh = var7;
      long var9 = var1.getTTLLike();
      this.retry = var9;
      long var11 = var1.getTTLLike();
      this.expire = var11;
      long var13 = var1.getTTLLike();
      this.minimum = var13;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      Name var2 = new Name(var1);
      this.host = var2;
      Name var3 = new Name(var1);
      this.admin = var3;
      long var4 = var1.readU32();
      this.serial = var4;
      long var6 = var1.readU32();
      this.refresh = var6;
      long var8 = var1.readU32();
      this.retry = var8;
      long var10 = var1.readU32();
      this.expire = var10;
      long var12 = var1.readU32();
      this.minimum = var12;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      Name var2 = this.host;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      Name var5 = this.admin;
      var1.append(var5);
      if(Options.check("multiline")) {
         StringBuffer var7 = var1.append(" (\n\t\t\t\t\t");
         long var8 = this.serial;
         var1.append(var8);
         StringBuffer var11 = var1.append("\t; serial\n\t\t\t\t\t");
         long var12 = this.refresh;
         var1.append(var12);
         StringBuffer var15 = var1.append("\t; refresh\n\t\t\t\t\t");
         long var16 = this.retry;
         var1.append(var16);
         StringBuffer var19 = var1.append("\t; retry\n\t\t\t\t\t");
         long var20 = this.expire;
         var1.append(var20);
         StringBuffer var23 = var1.append("\t; expire\n\t\t\t\t\t");
         long var24 = this.minimum;
         var1.append(var24);
         StringBuffer var27 = var1.append(" )\t; minimum");
      } else {
         StringBuffer var28 = var1.append(" ");
         long var29 = this.serial;
         var1.append(var29);
         StringBuffer var32 = var1.append(" ");
         long var33 = this.refresh;
         var1.append(var33);
         StringBuffer var36 = var1.append(" ");
         long var37 = this.retry;
         var1.append(var37);
         StringBuffer var40 = var1.append(" ");
         long var41 = this.expire;
         var1.append(var41);
         StringBuffer var44 = var1.append(" ");
         long var45 = this.minimum;
         var1.append(var45);
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      this.host.toWire(var1, var2, var3);
      this.admin.toWire(var1, var2, var3);
      long var4 = this.serial;
      var1.writeU32(var4);
      long var6 = this.refresh;
      var1.writeU32(var6);
      long var8 = this.retry;
      var1.writeU32(var8);
      long var10 = this.expire;
      var1.writeU32(var10);
      long var12 = this.minimum;
      var1.writeU32(var12);
   }
}
