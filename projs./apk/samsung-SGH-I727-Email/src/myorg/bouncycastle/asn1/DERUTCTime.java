package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;

public class DERUTCTime extends ASN1Object {

   String time;


   public DERUTCTime(String var1) {
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

   public DERUTCTime(Date var1) {
      SimpleDateFormat var2 = new SimpleDateFormat("yyMMddHHmmss\'Z\'");
      SimpleTimeZone var3 = new SimpleTimeZone(0, "Z");
      var2.setTimeZone(var3);
      String var4 = var2.format(var1);
      this.time = var4;
   }

   DERUTCTime(byte[] var1) {
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

   public static DERUTCTime getInstance(Object var0) {
      DERUTCTime var1;
      if(var0 != null && !(var0 instanceof DERUTCTime)) {
         if(!(var0 instanceof ASN1OctetString)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         byte[] var2 = ((ASN1OctetString)var0).getOctets();
         var1 = new DERUTCTime(var2);
      } else {
         var1 = (DERUTCTime)var0;
      }

      return var1;
   }

   public static DERUTCTime getInstance(ASN1TaggedObject var0, boolean var1) {
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

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DERUTCTime)) {
         var2 = 0;
      } else {
         String var3 = this.time;
         String var4 = ((DERUTCTime)var1).time;
         var2 = var3.equals(var4);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = this.getOctets();
      var1.writeEncoded(23, var2);
   }

   public Date getAdjustedDate() throws ParseException {
      SimpleDateFormat var1 = new SimpleDateFormat("yyyyMMddHHmmssz");
      SimpleTimeZone var2 = new SimpleTimeZone(0, "Z");
      var1.setTimeZone(var2);
      String var3 = this.getAdjustedTime();
      return var1.parse(var3);
   }

   public String getAdjustedTime() {
      String var1 = this.getTime();
      String var2;
      if(var1.charAt(0) < 53) {
         var2 = "20" + var1;
      } else {
         var2 = "19" + var1;
      }

      return var2;
   }

   public Date getDate() throws ParseException {
      SimpleDateFormat var1 = new SimpleDateFormat("yyMMddHHmmssz");
      String var2 = this.getTime();
      return var1.parse(var2);
   }

   public String getTime() {
      String var3;
      if(this.time.indexOf(45) < 0 && this.time.indexOf(43) < 0) {
         if(this.time.length() == 11) {
            StringBuilder var1 = new StringBuilder();
            String var2 = this.time.substring(0, 10);
            var3 = var1.append(var2).append("00GMT+00:00").toString();
         } else {
            StringBuilder var4 = new StringBuilder();
            String var5 = this.time.substring(0, 12);
            var3 = var4.append(var5).append("GMT+00:00").toString();
         }
      } else {
         int var6 = this.time.indexOf(45);
         if(var6 < 0) {
            var6 = this.time.indexOf(43);
         }

         String var7 = this.time;
         int var8 = this.time.length() - 3;
         if(var6 == var8) {
            var7 = var7 + "00";
         }

         if(var6 == 10) {
            StringBuilder var9 = new StringBuilder();
            String var10 = var7.substring(0, 10);
            StringBuilder var11 = var9.append(var10).append("00GMT");
            String var12 = var7.substring(10, 13);
            StringBuilder var13 = var11.append(var12).append(":");
            String var14 = var7.substring(13, 15);
            var3 = var13.append(var14).toString();
         } else {
            StringBuilder var15 = new StringBuilder();
            String var16 = var7.substring(0, 12);
            StringBuilder var17 = var15.append(var16).append("GMT");
            String var18 = var7.substring(12, 15);
            StringBuilder var19 = var17.append(var18).append(":");
            String var20 = var7.substring(15, 17);
            var3 = var19.append(var20).toString();
         }
      }

      return var3;
   }

   public int hashCode() {
      return this.time.hashCode();
   }

   public String toString() {
      return this.time;
   }
}
