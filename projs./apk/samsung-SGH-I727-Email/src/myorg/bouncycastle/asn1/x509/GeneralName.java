package myorg.bouncycastle.asn1.x509;

import java.util.StringTokenizer;
import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.util.IPAddress;

public class GeneralName extends ASN1Encodable implements ASN1Choice {

   public static final int dNSName = 2;
   public static final int directoryName = 4;
   public static final int ediPartyName = 5;
   public static final int iPAddress = 7;
   public static final int otherName = 0;
   public static final int registeredID = 8;
   public static final int rfc822Name = 1;
   public static final int uniformResourceIdentifier = 6;
   public static final int x400Address = 3;
   DEREncodable obj;
   int tag;


   public GeneralName(int var1, String var2) {
      this.tag = var1;
      if(var1 != 1 && var1 != 2 && var1 != 6) {
         if(var1 == 8) {
            DERObjectIdentifier var4 = new DERObjectIdentifier(var2);
            this.obj = var4;
         } else if(var1 == 4) {
            X509Name var5 = new X509Name(var2);
            this.obj = var5;
         } else if(var1 == 7) {
            byte[] var6 = this.toGeneralNameEncoding(var2);
            if(var6 != null) {
               DEROctetString var7 = new DEROctetString(var6);
               this.obj = var7;
            } else {
               throw new IllegalArgumentException("IP Address is invalid");
            }
         } else {
            String var8 = "can\'t process String for tag: " + var1;
            throw new IllegalArgumentException(var8);
         }
      } else {
         DERIA5String var3 = new DERIA5String(var2);
         this.obj = var3;
      }
   }

   public GeneralName(int var1, ASN1Encodable var2) {
      this.obj = var2;
      this.tag = var1;
   }

   public GeneralName(DERObject var1, int var2) {
      this.obj = var1;
      this.tag = var2;
   }

   public GeneralName(X509Name var1) {
      this.obj = var1;
      this.tag = 4;
   }

   private void copyInts(int[] var1, byte[] var2, int var3) {
      int var4 = 0;

      while(true) {
         int var5 = var1.length;
         if(var4 == var5) {
            return;
         }

         int var6 = var4 * 2 + var3;
         byte var7 = (byte)(var1[var4] >> 8);
         var2[var6] = var7;
         int var8 = var4 * 2 + 1 + var3;
         byte var9 = (byte)var1[var4];
         var2[var8] = var9;
         ++var4;
      }
   }

   public static GeneralName getInstance(Object var0) {
      GeneralName var1;
      if(var0 != null && !(var0 instanceof GeneralName)) {
         if(var0 instanceof ASN1TaggedObject) {
            ASN1TaggedObject var2 = (ASN1TaggedObject)var0;
            int var3 = var2.getTagNo();
            switch(var3) {
            case 0:
               ASN1Sequence var7 = ASN1Sequence.getInstance(var2, (boolean)0);
               var1 = new GeneralName(var3, var7);
               return var1;
            case 1:
               DERIA5String var8 = DERIA5String.getInstance(var2, (boolean)0);
               var1 = new GeneralName(var3, var8);
               return var1;
            case 2:
               DERIA5String var9 = DERIA5String.getInstance(var2, (boolean)0);
               var1 = new GeneralName(var3, var9);
               return var1;
            case 3:
               String var10 = "unknown tag: " + var3;
               throw new IllegalArgumentException(var10);
            case 4:
               X509Name var11 = X509Name.getInstance(var2, (boolean)1);
               var1 = new GeneralName(var3, var11);
               return var1;
            case 5:
               ASN1Sequence var12 = ASN1Sequence.getInstance(var2, (boolean)0);
               var1 = new GeneralName(var3, var12);
               return var1;
            case 6:
               DERIA5String var13 = DERIA5String.getInstance(var2, (boolean)0);
               var1 = new GeneralName(var3, var13);
               return var1;
            case 7:
               ASN1OctetString var14 = ASN1OctetString.getInstance(var2, (boolean)0);
               var1 = new GeneralName(var3, var14);
               return var1;
            case 8:
               DERObjectIdentifier var15 = DERObjectIdentifier.getInstance(var2, (boolean)0);
               var1 = new GeneralName(var3, var15);
               return var1;
            }
         }

         StringBuilder var4 = (new StringBuilder()).append("unknown object in getInstance: ");
         String var5 = var0.getClass().getName();
         String var6 = var4.append(var5).toString();
         throw new IllegalArgumentException(var6);
      } else {
         var1 = (GeneralName)var0;
         return var1;
      }
   }

   public static GeneralName getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1TaggedObject.getInstance(var0, (boolean)1));
   }

   private void parseIPv4(String var1, byte[] var2, int var3) {
      StringTokenizer var4 = new StringTokenizer(var1, "./");

      int var6;
      for(int var5 = 0; var4.hasMoreTokens(); var5 = var6) {
         var6 = var5 + 1;
         int var7 = var3 + var5;
         byte var8 = (byte)Integer.parseInt(var4.nextToken());
         var2[var7] = var8;
      }

   }

   private void parseIPv4Mask(String var1, byte[] var2, int var3) {
      int var4 = Integer.parseInt(var1);

      for(int var5 = 0; var5 != var4; ++var5) {
         int var6 = var5 / 8 + var3;
         byte var7 = var2[var6];
         int var8 = var5 % 8;
         int var9 = 1 << var8;
         byte var10 = (byte)(var7 | var9);
         var2[var6] = var10;
      }

   }

   private int[] parseIPv6(String var1) {
      StringTokenizer var2 = new StringTokenizer(var1, ":", (boolean)1);
      int var3 = 0;
      int[] var4 = new int[8];
      if(var1.charAt(0) == 58 && var1.charAt(1) == 58) {
         String var5 = var2.nextToken();
      }

      int var6 = -1;

      while(var2.hasMoreTokens()) {
         String var7 = var2.nextToken();
         if(":".equals(var7)) {
            var6 = var3;
            int var8 = var3 + 1;
            var4[var3] = 0;
            var3 = var8;
         } else if(var7.indexOf(46) < 0) {
            int var9 = var3 + 1;
            int var10 = Integer.parseInt(var7, 16);
            var4[var3] = var10;
            if(var2.hasMoreTokens()) {
               String var11 = var2.nextToken();
               var3 = var9;
            } else {
               var3 = var9;
            }
         } else {
            StringTokenizer var12 = new StringTokenizer(var7, ".");
            int var13 = var3 + 1;
            int var14 = Integer.parseInt(var12.nextToken()) << 8;
            int var15 = Integer.parseInt(var12.nextToken());
            int var16 = var14 | var15;
            var4[var3] = var16;
            var3 = var13 + 1;
            int var17 = Integer.parseInt(var12.nextToken()) << 8;
            int var18 = Integer.parseInt(var12.nextToken());
            int var19 = var17 | var18;
            var4[var13] = var19;
         }
      }

      int var20 = var4.length;
      if(var3 != var20) {
         int var21 = var4.length;
         int var22 = var3 - var6;
         int var23 = var21 - var22;
         int var24 = var3 - var6;
         System.arraycopy(var4, var6, var4, var23, var24);
         int var25 = var6;

         while(true) {
            int var26 = var4.length;
            int var27 = var3 - var6;
            int var28 = var26 - var27;
            if(var25 == var28) {
               break;
            }

            var4[var25] = 0;
            ++var25;
         }
      }

      return var4;
   }

   private int[] parseMask(String var1) {
      int[] var2 = new int[8];
      int var3 = Integer.parseInt(var1);

      for(int var4 = 0; var4 != var3; ++var4) {
         int var5 = var4 / 16;
         int var6 = var2[var5];
         int var7 = var4 % 16;
         int var8 = 1 << var7;
         int var9 = var6 | var8;
         var2[var5] = var9;
      }

      return var2;
   }

   private byte[] toGeneralNameEncoding(String var1) {
      int var2;
      byte[] var5;
      byte[] var6;
      String var10;
      if(!IPAddress.isValidIPv6WithNetmask(var1) && !IPAddress.isValidIPv6(var1)) {
         if(!IPAddress.isValidIPv4WithNetmask(var1) && !IPAddress.isValidIPv4(var1)) {
            var5 = null;
         } else {
            var2 = var1.indexOf(47);
            if(var2 < 0) {
               byte[] var12 = new byte[4];
               this.parseIPv4(var1, var12, 0);
               var5 = var12;
            } else {
               var6 = new byte[8];
               String var13 = var1.substring(0, var2);
               this.parseIPv4(var13, var6, 0);
               int var14 = var2 + 1;
               var10 = var1.substring(var14);
               if(var10.indexOf(46) > 0) {
                  this.parseIPv4(var10, var6, 4);
               } else {
                  this.parseIPv4Mask(var10, var6, 4);
               }

               var5 = var6;
            }
         }
      } else {
         var2 = var1.indexOf(47);
         if(var2 < 0) {
            byte[] var3 = new byte[16];
            int[] var4 = this.parseIPv6(var1);
            this.copyInts(var4, var3, 0);
            var5 = var3;
         } else {
            var6 = new byte[32];
            String var7 = var1.substring(0, var2);
            int[] var8 = this.parseIPv6(var7);
            this.copyInts(var8, var6, 0);
            int var9 = var2 + 1;
            var10 = var1.substring(var9);
            int[] var11;
            if(var10.indexOf(58) > 0) {
               var11 = this.parseIPv6(var10);
            } else {
               var11 = this.parseMask(var10);
            }

            this.copyInts(var11, var6, 16);
            var5 = var6;
         }
      }

      return var5;
   }

   public DEREncodable getName() {
      return this.obj;
   }

   public int getTagNo() {
      return this.tag;
   }

   public DERObject toASN1Object() {
      DERTaggedObject var3;
      if(this.tag == 4) {
         int var1 = this.tag;
         DEREncodable var2 = this.obj;
         var3 = new DERTaggedObject((boolean)1, var1, var2);
      } else {
         int var4 = this.tag;
         DEREncodable var5 = this.obj;
         var3 = new DERTaggedObject((boolean)0, var4, var5);
      }

      return var3;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.tag;
      var1.append(var2);
      StringBuffer var4 = var1.append(": ");
      switch(this.tag) {
      case 1:
      case 2:
      case 6:
         String var7 = DERIA5String.getInstance(this.obj).getString();
         var1.append(var7);
         break;
      case 3:
      case 5:
      default:
         String var5 = this.obj.toString();
         var1.append(var5);
         break;
      case 4:
         String var9 = X509Name.getInstance(this.obj).toString();
         var1.append(var9);
      }

      return var1.toString();
   }
}
