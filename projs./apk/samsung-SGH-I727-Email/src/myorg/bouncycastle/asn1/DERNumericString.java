package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERString;

public class DERNumericString extends ASN1Object implements DERString {

   String string;


   public DERNumericString(String var1) {
      this(var1, (boolean)0);
   }

   public DERNumericString(String var1, boolean var2) {
      if(var2 && !isNumericString(var1)) {
         throw new IllegalArgumentException("string contains illegal characters");
      } else {
         this.string = var1;
      }
   }

   public DERNumericString(byte[] var1) {
      char[] var2 = new char[var1.length];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            String var6 = new String(var2);
            this.string = var6;
            return;
         }

         char var5 = (char)(var1[var3] & 255);
         var2[var3] = var5;
         ++var3;
      }
   }

   public static DERNumericString getInstance(Object var0) {
      DERNumericString var1;
      if(var0 != null && !(var0 instanceof DERNumericString)) {
         if(var0 instanceof ASN1OctetString) {
            byte[] var2 = ((ASN1OctetString)var0).getOctets();
            var1 = new DERNumericString(var2);
         } else {
            if(!(var0 instanceof ASN1TaggedObject)) {
               StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
               String var4 = var0.getClass().getName();
               String var5 = var3.append(var4).toString();
               throw new IllegalArgumentException(var5);
            }

            var1 = getInstance(((ASN1TaggedObject)var0).getObject());
         }
      } else {
         var1 = (DERNumericString)var0;
      }

      return var1;
   }

   public static DERNumericString getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   public static boolean isNumericString(String var0) {
      int var1 = var0.length() - 1;

      boolean var3;
      while(true) {
         if(var1 < 0) {
            var3 = true;
            break;
         }

         char var2 = var0.charAt(var1);
         if(var2 > 127) {
            var3 = false;
            break;
         }

         if((48 > var2 || var2 > 57) && var2 != 32) {
            var3 = false;
            break;
         }

         var1 += -1;
      }

      return var3;
   }

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DERNumericString)) {
         var2 = 0;
      } else {
         DERNumericString var3 = (DERNumericString)var1;
         String var4 = this.getString();
         String var5 = var3.getString();
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = this.getOctets();
      var1.writeEncoded(18, var2);
   }

   public byte[] getOctets() {
      char[] var1 = this.string.toCharArray();
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

   public String getString() {
      return this.string;
   }

   public int hashCode() {
      return this.getString().hashCode();
   }

   public String toString() {
      return this.string;
   }
}
