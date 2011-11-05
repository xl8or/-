package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERString;

public class DERIA5String extends ASN1Object implements DERString {

   String string;


   public DERIA5String(String var1) {
      this(var1, (boolean)0);
   }

   public DERIA5String(String var1, boolean var2) {
      if(var1 == null) {
         throw new NullPointerException("string cannot be null");
      } else if(var2 && !isIA5String(var1)) {
         throw new IllegalArgumentException("string contains illegal characters");
      } else {
         this.string = var1;
      }
   }

   public DERIA5String(byte[] var1) {
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

   public static DERIA5String getInstance(Object var0) {
      DERIA5String var1;
      if(var0 != null && !(var0 instanceof DERIA5String)) {
         if(var0 instanceof ASN1OctetString) {
            byte[] var2 = ((ASN1OctetString)var0).getOctets();
            var1 = new DERIA5String(var2);
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
         var1 = (DERIA5String)var0;
      }

      return var1;
   }

   public static DERIA5String getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   public static boolean isIA5String(String var0) {
      int var1 = var0.length() - 1;

      boolean var2;
      while(true) {
         if(var1 < 0) {
            var2 = true;
            break;
         }

         if(var0.charAt(var1) > 127) {
            var2 = false;
            break;
         }

         var1 += -1;
      }

      return var2;
   }

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DERIA5String)) {
         var2 = 0;
      } else {
         DERIA5String var3 = (DERIA5String)var1;
         String var4 = this.getString();
         String var5 = var3.getString();
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = this.getOctets();
      var1.writeEncoded(22, var2);
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
