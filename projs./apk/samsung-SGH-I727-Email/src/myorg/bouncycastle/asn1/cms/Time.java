package myorg.bouncycastle.asn1.cms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERUTCTime;

public class Time extends ASN1Encodable implements ASN1Choice {

   DERObject time;


   public Time(Date var1) {
      SimpleTimeZone var2 = new SimpleTimeZone(0, "Z");
      SimpleDateFormat var3 = new SimpleDateFormat("yyyyMMddHHmmss");
      var3.setTimeZone(var2);
      StringBuilder var4 = new StringBuilder();
      String var5 = var3.format(var1);
      String var6 = var4.append(var5).append("Z").toString();
      int var7 = Integer.parseInt(var6.substring(0, 4));
      if(var7 >= 1950 && var7 <= 2049) {
         String var9 = var6.substring(2);
         DERUTCTime var10 = new DERUTCTime(var9);
         this.time = var10;
      } else {
         DERGeneralizedTime var8 = new DERGeneralizedTime(var6);
         this.time = var8;
      }
   }

   public Time(DERObject var1) {
      if(!(var1 instanceof DERUTCTime) && !(var1 instanceof DERGeneralizedTime)) {
         throw new IllegalArgumentException("unknown object passed to Time");
      } else {
         this.time = var1;
      }
   }

   public static Time getInstance(Object var0) {
      Time var1;
      if(var0 instanceof Time) {
         var1 = (Time)var0;
      } else if(var0 instanceof DERUTCTime) {
         DERUTCTime var2 = (DERUTCTime)var0;
         var1 = new Time(var2);
      } else {
         if(!(var0 instanceof DERGeneralizedTime)) {
            StringBuilder var4 = (new StringBuilder()).append("unknown object in factory: ");
            String var5 = var0.getClass().getName();
            String var6 = var4.append(var5).toString();
            throw new IllegalArgumentException(var6);
         }

         DERGeneralizedTime var3 = (DERGeneralizedTime)var0;
         var1 = new Time(var3);
      }

      return var1;
   }

   public static Time getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   public Date getDate() {
      // $FF: Couldn't be decompiled
   }

   public String getTime() {
      String var1;
      if(this.time instanceof DERUTCTime) {
         var1 = ((DERUTCTime)this.time).getAdjustedTime();
      } else {
         var1 = ((DERGeneralizedTime)this.time).getTime();
      }

      return var1;
   }

   public DERObject toASN1Object() {
      return this.time;
   }
}
