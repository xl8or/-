package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;

public class DERGeneralizedTime extends ASN1Object {

   String time;


   public DERGeneralizedTime(String var1) {
      this.time = var1;

      try {
         Date var2 = this.getDate();
      } catch (ParseException var7) {
         StringBuilder var4 = (new StringBuilder()).append("invalid date string: ");
         String var5 = var7.getMessage();
         String var6 = var4.append(var5).toString();
         throw new IllegalArgumentException(var6);
      }
   }

   public DERGeneralizedTime(Date var1) {
      SimpleDateFormat var2 = new SimpleDateFormat("yyyyMMddHHmmss\'Z\'");
      SimpleTimeZone var3 = new SimpleTimeZone(0, "Z");
      var2.setTimeZone(var3);
      String var4 = var2.format(var1);
      this.time = var4;
   }

   DERGeneralizedTime(byte[] var1) {
      char[] var2 = new char[var1.length];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            String var6 = new String(var2);
            this.time = var6;
            return;
         }

         char var5 = (char)(var1[var3] & 255);
         var2[var3] = var5;
         ++var3;
      }
   }

   private String calculateGMTOffset() {
      String var1 = "+";
      TimeZone var2 = TimeZone.getDefault();
      int var3 = var2.getRawOffset();
      if(var3 < 0) {
         var1 = "-";
         var3 = -var3;
      }

      int var4 = var3 / 3600000;
      int var5 = var4 * 60 * 60 * 1000;
      int var6 = (var3 - var5) / '\uea60';

      label32: {
         boolean var8;
         try {
            if(!var2.useDaylightTime()) {
               break label32;
            }

            Date var7 = this.getDate();
            if(!var2.inDaylightTime(var7)) {
               break label32;
            }

            var8 = "+".equals(var1);
         } catch (ParseException var15) {
            break label32;
         }

         byte var9;
         if(var8) {
            var9 = 1;
         } else {
            var9 = -1;
         }

         var4 += var9;
      }

      StringBuilder var10 = (new StringBuilder()).append("GMT").append(var1);
      String var11 = this.convert(var4);
      StringBuilder var12 = var10.append(var11).append(":");
      String var13 = this.convert(var6);
      return var12.append(var13).toString();
   }

   private String convert(int var1) {
      String var2;
      if(var1 < 10) {
         var2 = "0" + var1;
      } else {
         var2 = Integer.toString(var1);
      }

      return var2;
   }

   public static DERGeneralizedTime getInstance(Object var0) {
      DERGeneralizedTime var1;
      if(var0 != null && !(var0 instanceof DERGeneralizedTime)) {
         if(!(var0 instanceof ASN1OctetString)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         byte[] var2 = ((ASN1OctetString)var0).getOctets();
         var1 = new DERGeneralizedTime(var2);
      } else {
         var1 = (DERGeneralizedTime)var0;
      }

      return var1;
   }

   public static DERGeneralizedTime getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   private byte[] getOctets() {
      char[] var1 = this.time.toCharArray();
      byte[] var2 = new byte[var1.length];
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 == var4) {
            return var2;
         }

         byte var5 = (byte)var1[var3];
         var2[var3] = var5;
         ++var3;
      }
   }

   private boolean hasFractionalSeconds() {
      boolean var1;
      if(this.time.indexOf(46) == 14) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DERGeneralizedTime)) {
         var2 = 0;
      } else {
         String var3 = this.time;
         String var4 = ((DERGeneralizedTime)var1).time;
         var2 = var3.equals(var4);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = this.getOctets();
      var1.writeEncoded(24, var2);
   }

   public Date getDate() throws ParseException {
      String var1 = this.time;
      SimpleDateFormat var2;
      if(this.time.endsWith("Z")) {
         if(this.hasFractionalSeconds()) {
            var2 = new SimpleDateFormat("yyyyMMddHHmmss.SSS\'Z\'");
         } else {
            var2 = new SimpleDateFormat("yyyyMMddHHmmss\'Z\'");
         }

         SimpleTimeZone var3 = new SimpleTimeZone(0, "Z");
         var2.setTimeZone(var3);
      } else if(this.time.indexOf(45) <= 0 && this.time.indexOf(43) <= 0) {
         if(this.hasFractionalSeconds()) {
            var2 = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
         } else {
            var2 = new SimpleDateFormat("yyyyMMddHHmmss");
         }

         String var16 = TimeZone.getDefault().getID();
         SimpleTimeZone var17 = new SimpleTimeZone(0, var16);
         var2.setTimeZone(var17);
      } else {
         var1 = this.getTime();
         if(this.hasFractionalSeconds()) {
            var2 = new SimpleDateFormat("yyyyMMddHHmmss.SSSz");
         } else {
            var2 = new SimpleDateFormat("yyyyMMddHHmmssz");
         }

         SimpleTimeZone var15 = new SimpleTimeZone(0, "Z");
         var2.setTimeZone(var15);
      }

      if(this.hasFractionalSeconds()) {
         String var4 = var1.substring(14);
         int var5 = 1;

         while(true) {
            int var6 = var4.length();
            if(var5 >= var6) {
               break;
            }

            char var7 = var4.charAt(var5);
            if(48 > var7 || var7 > 57) {
               break;
            }

            ++var5;
         }

         if(var5 - 1 > 3) {
            StringBuilder var8 = new StringBuilder();
            String var9 = var4.substring(0, 4);
            StringBuilder var10 = var8.append(var9);
            String var11 = var4.substring(var5);
            String var12 = var10.append(var11).toString();
            StringBuilder var13 = new StringBuilder();
            String var14 = var1.substring(0, 14);
            var1 = var13.append(var14).append(var12).toString();
         }
      }

      return var2.parse(var1);
   }

   public String getTime() {
      String var1 = this.time;
      int var2 = this.time.length() - 1;
      String var7;
      if(var1.charAt(var2) == 90) {
         StringBuilder var3 = new StringBuilder();
         String var4 = this.time;
         int var5 = this.time.length() - 1;
         String var6 = var4.substring(0, var5);
         var7 = var3.append(var6).append("GMT+00:00").toString();
      } else {
         int var8 = this.time.length() - 5;
         char var9 = this.time.charAt(var8);
         if(var9 != 45 && var9 != 43) {
            var8 = this.time.length() - 3;
            var9 = this.time.charAt(var8);
            if(var9 != 45 && var9 != 43) {
               StringBuilder var24 = new StringBuilder();
               String var25 = this.time;
               StringBuilder var26 = var24.append(var25);
               String var27 = this.calculateGMTOffset();
               var7 = var26.append(var27).toString();
            } else {
               StringBuilder var20 = new StringBuilder();
               String var21 = this.time.substring(0, var8);
               StringBuilder var22 = var20.append(var21).append("GMT");
               String var23 = this.time.substring(var8);
               var7 = var22.append(var23).append(":00").toString();
            }
         } else {
            StringBuilder var10 = new StringBuilder();
            String var11 = this.time.substring(0, var8);
            StringBuilder var12 = var10.append(var11).append("GMT");
            String var13 = this.time;
            int var14 = var8 + 3;
            String var15 = var13.substring(var8, var14);
            StringBuilder var16 = var12.append(var15).append(":");
            String var17 = this.time;
            int var18 = var8 + 3;
            String var19 = var17.substring(var18);
            var7 = var16.append(var19).toString();
         }
      }

      return var7;
   }

   public String getTimeString() {
      return this.time;
   }

   public int hashCode() {
      return this.time.hashCode();
   }
}
