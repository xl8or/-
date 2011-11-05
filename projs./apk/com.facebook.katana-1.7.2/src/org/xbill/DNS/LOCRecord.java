package org.xbill.DNS;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.WireParseException;

public class LOCRecord extends Record {

   private static final long serialVersionUID = 9058224788126750409L;
   private static NumberFormat w2 = new DecimalFormat();
   private static NumberFormat w3;
   private long altitude;
   private long hPrecision;
   private long latitude;
   private long longitude;
   private long size;
   private long vPrecision;


   static {
      w2.setMinimumIntegerDigits(2);
      w3 = new DecimalFormat();
      w3.setMinimumIntegerDigits(3);
   }

   LOCRecord() {}

   public LOCRecord(Name var1, int var2, long var3, double var5, double var7, double var9, double var11, double var13, double var15) {
      super(var1, 29, var2, var3);
      long var22 = (long)(3600.0D * var5 * 1000.0D + 2.147483648E9D);
      this.latitude = var22;
      long var24 = (long)(3600.0D * var7 * 1000.0D + 2.147483648E9D);
      this.longitude = var24;
      long var26 = (long)((100000.0D + var9) * 100.0D);
      this.altitude = var26;
      long var28 = (long)(0.0F * var11);
      this.size = var28;
      long var30 = (long)(0.0F * var13);
      this.hPrecision = var30;
      long var32 = (long)(0.0F * var15);
      this.vPrecision = var32;
   }

   private long parseDouble(Tokenizer var1, String var2, boolean var3, long var4, long var6, long var8) throws IOException {
      Tokenizer.Token var10 = var1.get();
      if(var10.isEOL()) {
         if(var3) {
            String var11 = "Invalid LOC " + var2;
            throw var1.exception(var11);
         }

         var1.unget();
      } else {
         String var14 = var10.value;
         if(var14.length() > 1) {
            int var15 = var14.length() - 1;
            if(var14.charAt(var15) == 109) {
               int var16 = var14.length() - 1;
               var14 = var14.substring(0, var16);
            }
         }

         double var17 = 100.0D;

         try {
            double var19 = this.parseFixedPoint(var14);
            long var25 = (long)(var17 * var19);
            if(var25 < var4 || var25 > var6) {
               String var21 = "Invalid LOC " + var2;
               throw var1.exception(var21);
            }
         } catch (NumberFormatException var24) {
            String var23 = "Invalid LOC " + var2;
            throw var1.exception(var23);
         }
      }

      return var8;
   }

   private double parseFixedPoint(String var1) {
      double var2;
      if(var1.matches("^\\d+$")) {
         var2 = (double)Integer.parseInt(var1);
      } else {
         if(!var1.matches("^\\d+\\.\\d*$")) {
            throw new NumberFormatException();
         }

         String[] var4 = var1.split("\\.");
         double var5 = (double)Integer.parseInt(var4[0]);
         double var7 = (double)Integer.parseInt(var4[1]);
         double var9 = (double)var4[1].length();
         double var11 = Math.pow(10.0D, var9);
         double var13 = var7 / var11;
         var2 = var5 + var13;
      }

      return var2;
   }

   private static long parseLOCformat(int param0) throws WireParseException {
      // $FF: Couldn't be decompiled
   }

   private long parsePosition(Tokenizer param1, String param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private String positionToString(long var1, char var3, char var4) {
      StringBuffer var5 = new StringBuffer();
      long var6 = var1 - 2147483648L;
      char var8;
      if(var6 < 0L) {
         var6 = -var6;
         var8 = var4;
      } else {
         var8 = var3;
      }

      long var9 = var6 / 3600000L;
      var5.append(var9);
      long var12 = var6 % 3600000L;
      StringBuffer var14 = var5.append(" ");
      long var15 = var12 / 60000L;
      var5.append(var15);
      long var18 = var12 % 60000L;
      StringBuffer var20 = var5.append(" ");
      NumberFormat var21 = w3;
      this.renderFixedPoint(var5, var21, var18, 1000L);
      StringBuffer var22 = var5.append(" ");
      var5.append(var8);
      return var5.toString();
   }

   private void renderFixedPoint(StringBuffer var1, NumberFormat var2, long var3, long var5) {
      long var7 = var3 / var5;
      var1.append(var7);
      long var10 = var3 % var5;
      if(var10 != 0L) {
         StringBuffer var12 = var1.append(".");
         String var13 = var2.format(var10);
         var1.append(var13);
      }
   }

   private int toLOCformat(long var1) {
      byte var3 = 0;

      long var4;
      for(var4 = var1; var4 > 9L; var4 /= 10L) {
         ++var3;
      }

      long var6 = var4 << 4;
      long var8 = (long)var3;
      return (int)(var6 + var8);
   }

   public double getAltitude() {
      return (double)(this.altitude - 10000000L) / 100.0D;
   }

   public double getHPrecision() {
      return (double)this.hPrecision / 100.0D;
   }

   public double getLatitude() {
      return (double)(this.latitude - 2147483648L) / 3600000.0D;
   }

   public double getLongitude() {
      return (double)(this.longitude - 2147483648L) / 3600000.0D;
   }

   Record getObject() {
      return new LOCRecord();
   }

   public double getSize() {
      return (double)this.size / 100.0D;
   }

   public double getVPrecision() {
      return (double)this.vPrecision / 100.0D;
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      long var3 = this.parsePosition(var1, "latitude");
      this.latitude = var3;
      long var5 = this.parsePosition(var1, "longitude");
      this.longitude = var5;
      long var9 = this.parseDouble(var1, "altitude", (boolean)1, -10000000L, 4284967295L, 0L) + 10000000L;
      this.altitude = var9;
      long var13 = this.parseDouble(var1, "size", (boolean)0, 0L, 9000000000L, 100L);
      this.size = var13;
      long var17 = this.parseDouble(var1, "horizontal precision", (boolean)0, 0L, 9000000000L, 1000000L);
      this.hPrecision = var17;
      long var21 = this.parseDouble(var1, "vertical precision", (boolean)0, 0L, 9000000000L, 1000L);
      this.vPrecision = var21;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      if(var1.readU8() != 0) {
         throw new WireParseException("Invalid LOC version");
      } else {
         long var2 = parseLOCformat(var1.readU8());
         this.size = var2;
         long var4 = parseLOCformat(var1.readU8());
         this.hPrecision = var4;
         long var6 = parseLOCformat(var1.readU8());
         this.vPrecision = var6;
         long var8 = var1.readU32();
         this.latitude = var8;
         long var10 = var1.readU32();
         this.longitude = var10;
         long var12 = var1.readU32();
         this.altitude = var12;
      }
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      long var2 = this.latitude;
      String var4 = this.positionToString(var2, 'N', 'S');
      var1.append(var4);
      StringBuffer var6 = var1.append(" ");
      long var7 = this.longitude;
      String var9 = this.positionToString(var7, 'E', 'W');
      var1.append(var9);
      StringBuffer var11 = var1.append(" ");
      NumberFormat var12 = w2;
      long var13 = this.altitude - 10000000L;
      this.renderFixedPoint(var1, var12, var13, 100L);
      StringBuffer var15 = var1.append("m ");
      NumberFormat var16 = w2;
      long var17 = this.size;
      this.renderFixedPoint(var1, var16, var17, 100L);
      StringBuffer var19 = var1.append("m ");
      NumberFormat var20 = w2;
      long var21 = this.hPrecision;
      this.renderFixedPoint(var1, var20, var21, 100L);
      StringBuffer var23 = var1.append("m ");
      NumberFormat var24 = w2;
      long var25 = this.vPrecision;
      this.renderFixedPoint(var1, var24, var25, 100L);
      StringBuffer var27 = var1.append("m");
      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      var1.writeU8(0);
      long var4 = this.size;
      int var6 = this.toLOCformat(var4);
      var1.writeU8(var6);
      long var7 = this.hPrecision;
      int var9 = this.toLOCformat(var7);
      var1.writeU8(var9);
      long var10 = this.vPrecision;
      int var12 = this.toLOCformat(var10);
      var1.writeU8(var12);
      long var13 = this.latitude;
      var1.writeU32(var13);
      long var15 = this.longitude;
      var1.writeU32(var15);
      long var17 = this.altitude;
      var1.writeU32(var17);
   }
}
