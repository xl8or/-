package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;

public class DERBoolean extends ASN1Object {

   public static final DERBoolean FALSE = new DERBoolean((boolean)0);
   public static final DERBoolean TRUE = new DERBoolean((boolean)1);
   byte value;


   public DERBoolean(boolean var1) {
      byte var2;
      if(var1) {
         var2 = -1;
      } else {
         var2 = 0;
      }

      this.value = var2;
   }

   public DERBoolean(byte[] var1) {
      byte var2 = var1[0];
      this.value = var2;
   }

   public static DERBoolean getInstance(Object var0) {
      DERBoolean var1;
      if(var0 != null && !(var0 instanceof DERBoolean)) {
         if(var0 instanceof ASN1OctetString) {
            byte[] var2 = ((ASN1OctetString)var0).getOctets();
            var1 = new DERBoolean(var2);
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
         var1 = (DERBoolean)var0;
      }

      return var1;
   }

   public static DERBoolean getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   public static DERBoolean getInstance(boolean var0) {
      DERBoolean var1;
      if(var0) {
         var1 = TRUE;
      } else {
         var1 = FALSE;
      }

      return var1;
   }

   protected boolean asn1Equals(DERObject var1) {
      boolean var2;
      if(var1 != null && var1 instanceof DERBoolean) {
         byte var3 = this.value;
         byte var4 = ((DERBoolean)var1).value;
         if(var3 == var4) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = new byte[1];
      byte var3 = this.value;
      var2[0] = var3;
      var1.writeEncoded(1, var2);
   }

   public int hashCode() {
      return this.value;
   }

   public boolean isTrue() {
      boolean var1;
      if(this.value != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String toString() {
      String var1;
      if(this.value != 0) {
         var1 = "TRUE";
      } else {
         var1 = "FALSE";
      }

      return var1;
   }
}
