package org.xbill.DNS;

import java.io.IOException;
import java.util.Date;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.FormattedTime;
import org.xbill.DNS.Name;
import org.xbill.DNS.Options;
import org.xbill.DNS.Rcode;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.utils.base64;

public class TKEYRecord extends Record {

   public static final int DELETE = 5;
   public static final int DIFFIEHELLMAN = 2;
   public static final int GSSAPI = 3;
   public static final int RESOLVERASSIGNED = 4;
   public static final int SERVERASSIGNED = 1;
   private static final long serialVersionUID = 8828458121926391756L;
   private Name alg;
   private int error;
   private byte[] key;
   private int mode;
   private byte[] other;
   private Date timeExpire;
   private Date timeInception;


   TKEYRecord() {}

   public TKEYRecord(Name var1, int var2, long var3, Name var5, Date var6, Date var7, int var8, int var9, byte[] var10, byte[] var11) {
      super(var1, 249, var2, var3);
      Name var17 = checkName("alg", var5);
      this.alg = var17;
      this.timeInception = var6;
      this.timeExpire = var7;
      String var18 = "mode";
      int var20 = checkU16(var18, var8);
      this.mode = var20;
      String var21 = "error";
      int var23 = checkU16(var21, var9);
      this.error = var23;
      this.key = var10;
      this.other = var11;
   }

   public Name getAlgorithm() {
      return this.alg;
   }

   public int getError() {
      return this.error;
   }

   public byte[] getKey() {
      return this.key;
   }

   public int getMode() {
      return this.mode;
   }

   Record getObject() {
      return new TKEYRecord();
   }

   public byte[] getOther() {
      return this.other;
   }

   public Date getTimeExpire() {
      return this.timeExpire;
   }

   public Date getTimeInception() {
      return this.timeInception;
   }

   protected String modeString() {
      String var1;
      switch(this.mode) {
      case 1:
         var1 = "SERVERASSIGNED";
         break;
      case 2:
         var1 = "DIFFIEHELLMAN";
         break;
      case 3:
         var1 = "GSSAPI";
         break;
      case 4:
         var1 = "RESOLVERASSIGNED";
         break;
      case 5:
         var1 = "DELETE";
         break;
      default:
         var1 = Integer.toString(this.mode);
      }

      return var1;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      throw var1.exception("no text format defined for TKEY");
   }

   void rrFromWire(DNSInput var1) throws IOException {
      Name var2 = new Name(var1);
      this.alg = var2;
      long var3 = var1.readU32() * 1000L;
      Date var5 = new Date(var3);
      this.timeInception = var5;
      long var6 = var1.readU32() * 1000L;
      Date var8 = new Date(var6);
      this.timeExpire = var8;
      int var9 = var1.readU16();
      this.mode = var9;
      int var10 = var1.readU16();
      this.error = var10;
      int var11 = var1.readU16();
      if(var11 > 0) {
         byte[] var12 = var1.readByteArray(var11);
         this.key = var12;
      } else {
         this.key = null;
      }

      int var13 = var1.readU16();
      if(var13 > 0) {
         byte[] var14 = var1.readByteArray(var13);
         this.other = var14;
      } else {
         this.other = null;
      }
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      Name var2 = this.alg;
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      if(Options.check("multiline")) {
         StringBuffer var5 = var1.append("(\n\t");
      }

      String var6 = FormattedTime.format(this.timeInception);
      var1.append(var6);
      StringBuffer var8 = var1.append(" ");
      String var9 = FormattedTime.format(this.timeExpire);
      var1.append(var9);
      StringBuffer var11 = var1.append(" ");
      String var12 = this.modeString();
      var1.append(var12);
      StringBuffer var14 = var1.append(" ");
      String var15 = Rcode.TSIGstring(this.error);
      var1.append(var15);
      if(Options.check("multiline")) {
         StringBuffer var17 = var1.append("\n");
         if(this.key != null) {
            String var18 = base64.formatString(this.key, 64, "\t", (boolean)0);
            var1.append(var18);
            StringBuffer var20 = var1.append("\n");
         }

         if(this.other != null) {
            String var21 = base64.formatString(this.other, 64, "\t", (boolean)0);
            var1.append(var21);
         }

         StringBuffer var23 = var1.append(" )");
      } else {
         StringBuffer var24 = var1.append(" ");
         if(this.key != null) {
            String var25 = base64.toString(this.key);
            var1.append(var25);
            StringBuffer var27 = var1.append(" ");
         }

         if(this.other != null) {
            String var28 = base64.toString(this.other);
            var1.append(var28);
         }
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      this.alg.toWire(var1, (Compression)null, var3);
      long var4 = this.timeInception.getTime() / 1000L;
      var1.writeU32(var4);
      long var6 = this.timeExpire.getTime() / 1000L;
      var1.writeU32(var6);
      int var8 = this.mode;
      var1.writeU16(var8);
      int var9 = this.error;
      var1.writeU16(var9);
      if(this.key != null) {
         int var10 = this.key.length;
         var1.writeU16(var10);
         byte[] var11 = this.key;
         var1.writeByteArray(var11);
      } else {
         var1.writeU16(0);
      }

      if(this.other != null) {
         int var12 = this.other.length;
         var1.writeU16(var12);
         byte[] var13 = this.other;
         var1.writeByteArray(var13);
      } else {
         var1.writeU16(0);
      }
   }
}
