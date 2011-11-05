package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.util.Arrays;

public class DERInteger extends ASN1Object {

   byte[] bytes;


   public DERInteger(int var1) {
      byte[] var2 = BigInteger.valueOf((long)var1).toByteArray();
      this.bytes = var2;
   }

   public DERInteger(BigInteger var1) {
      byte[] var2 = var1.toByteArray();
      this.bytes = var2;
   }

   public DERInteger(byte[] var1) {
      this.bytes = var1;
   }

   public static DERInteger getInstance(Object var0) {
      DERInteger var1;
      if(var0 != null && !(var0 instanceof DERInteger)) {
         if(var0 instanceof ASN1OctetString) {
            byte[] var2 = ((ASN1OctetString)var0).getOctets();
            var1 = new DERInteger(var2);
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
         var1 = (DERInteger)var0;
      }

      return var1;
   }

   public static DERInteger getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DERInteger)) {
         var2 = 0;
      } else {
         DERInteger var3 = (DERInteger)var1;
         byte[] var4 = this.bytes;
         byte[] var5 = var3.bytes;
         var2 = Arrays.areEqual(var4, var5);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = this.bytes;
      var1.writeEncoded(2, var2);
   }

   public BigInteger getPositiveValue() {
      byte[] var1 = this.bytes;
      return new BigInteger(1, var1);
   }

   public BigInteger getValue() {
      byte[] var1 = this.bytes;
      return new BigInteger(var1);
   }

   public int hashCode() {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         int var3 = this.bytes.length;
         if(var2 == var3) {
            return var1;
         }

         int var4 = this.bytes[var2] & 255;
         int var5 = var2 % 4;
         int var6 = var4 << var5;
         var1 ^= var6;
         ++var2;
      }
   }

   public String toString() {
      return this.getValue().toString();
   }
}
