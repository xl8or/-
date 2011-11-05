package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.util.Arrays;

public class DEREnumerated extends ASN1Object {

   byte[] bytes;


   public DEREnumerated(int var1) {
      byte[] var2 = BigInteger.valueOf((long)var1).toByteArray();
      this.bytes = var2;
   }

   public DEREnumerated(BigInteger var1) {
      byte[] var2 = var1.toByteArray();
      this.bytes = var2;
   }

   public DEREnumerated(byte[] var1) {
      this.bytes = var1;
   }

   public static DEREnumerated getInstance(Object var0) {
      DEREnumerated var1;
      if(var0 != null && !(var0 instanceof DEREnumerated)) {
         if(var0 instanceof ASN1OctetString) {
            byte[] var2 = ((ASN1OctetString)var0).getOctets();
            var1 = new DEREnumerated(var2);
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
         var1 = (DEREnumerated)var0;
      }

      return var1;
   }

   public static DEREnumerated getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DEREnumerated)) {
         var2 = 0;
      } else {
         DEREnumerated var3 = (DEREnumerated)var1;
         byte[] var4 = this.bytes;
         byte[] var5 = var3.bytes;
         var2 = Arrays.areEqual(var4, var5);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = this.bytes;
      var1.writeEncoded(10, var2);
   }

   public BigInteger getValue() {
      byte[] var1 = this.bytes;
      return new BigInteger(var1);
   }

   public int hashCode() {
      return Arrays.hashCode(this.bytes);
   }
}
