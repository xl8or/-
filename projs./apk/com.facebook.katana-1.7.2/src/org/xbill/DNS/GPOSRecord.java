package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.WireParseException;

public class GPOSRecord extends Record {

   private static final long serialVersionUID = -6349714958085750705L;
   private byte[] altitude;
   private byte[] latitude;
   private byte[] longitude;


   GPOSRecord() {}

   public GPOSRecord(Name var1, int var2, long var3, double var5, double var7, double var9) {
      super(var1, 27, var2, var3);
      this.validate(var5, var7);
      byte[] var16 = Double.toString(var5).getBytes();
      this.longitude = var16;
      byte[] var17 = Double.toString(var7).getBytes();
      this.latitude = var17;
      byte[] var18 = Double.toString(var9).getBytes();
      this.altitude = var18;
   }

   public GPOSRecord(Name var1, int var2, long var3, String var5, String var6, String var7) {
      super(var1, 27, var2, var3);

      try {
         byte[] var13 = byteArrayFromString(var5);
         this.longitude = var13;
         byte[] var14 = byteArrayFromString(var6);
         this.latitude = var14;
         double var15 = this.getLongitude();
         double var17 = this.getLatitude();
         this.validate(var15, var17);
         byte[] var19 = byteArrayFromString(var7);
         this.altitude = var19;
      } catch (TextParseException var21) {
         String var20 = var21.getMessage();
         throw new IllegalArgumentException(var20);
      }
   }

   private void validate(double var1, double var3) throws IllegalArgumentException {
      if(var1 >= -90.0D && var1 <= 90.0D) {
         if(var3 < -180.0D || var3 > 180.0D) {
            String var6 = "illegal latitude " + var3;
            throw new IllegalArgumentException(var6);
         }
      } else {
         String var5 = "illegal longitude " + var1;
         throw new IllegalArgumentException(var5);
      }
   }

   public double getAltitude() {
      return Double.parseDouble(this.getAltitudeString());
   }

   public String getAltitudeString() {
      return byteArrayToString(this.altitude, (boolean)0);
   }

   public double getLatitude() {
      return Double.parseDouble(this.getLatitudeString());
   }

   public String getLatitudeString() {
      return byteArrayToString(this.latitude, (boolean)0);
   }

   public double getLongitude() {
      return Double.parseDouble(this.getLongitudeString());
   }

   public String getLongitudeString() {
      return byteArrayToString(this.longitude, (boolean)0);
   }

   Record getObject() {
      return new GPOSRecord();
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      try {
         byte[] var3 = byteArrayFromString(var1.getString());
         this.longitude = var3;
         byte[] var4 = byteArrayFromString(var1.getString());
         this.latitude = var4;
         byte[] var5 = byteArrayFromString(var1.getString());
         this.altitude = var5;
      } catch (TextParseException var13) {
         String var10 = var13.getMessage();
         throw var1.exception(var10);
      }

      try {
         double var6 = this.getLongitude();
         double var8 = this.getLatitude();
         this.validate(var6, var8);
      } catch (IllegalArgumentException var12) {
         String var11 = var12.getMessage();
         throw new WireParseException(var11);
      }
   }

   void rrFromWire(DNSInput var1) throws IOException {
      byte[] var2 = var1.readCountedString();
      this.longitude = var2;
      byte[] var3 = var1.readCountedString();
      this.latitude = var3;
      byte[] var4 = var1.readCountedString();
      this.altitude = var4;

      try {
         double var5 = this.getLongitude();
         double var7 = this.getLatitude();
         this.validate(var5, var7);
      } catch (IllegalArgumentException var10) {
         String var9 = var10.getMessage();
         throw new WireParseException(var9);
      }
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = byteArrayToString(this.longitude, (boolean)1);
      var1.append(var2);
      StringBuffer var4 = var1.append(" ");
      String var5 = byteArrayToString(this.latitude, (boolean)1);
      var1.append(var5);
      StringBuffer var7 = var1.append(" ");
      String var8 = byteArrayToString(this.altitude, (boolean)1);
      var1.append(var8);
      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      byte[] var4 = this.longitude;
      var1.writeCountedString(var4);
      byte[] var5 = this.latitude;
      var1.writeCountedString(var5);
      byte[] var6 = this.altitude;
      var1.writeCountedString(var6);
   }
}
