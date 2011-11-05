package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.util.Strings;

public class DERUTF8String extends ASN1Object implements DERString {

   String string;


   public DERUTF8String(String var1) {
      this.string = var1;
   }

   DERUTF8String(byte[] var1) {
      String var2 = Strings.fromUTF8ByteArray(var1);
      this.string = var2;
   }

   public static DERUTF8String getInstance(Object var0) {
      DERUTF8String var1;
      if(var0 != null && !(var0 instanceof DERUTF8String)) {
         if(var0 instanceof ASN1OctetString) {
            byte[] var2 = ((ASN1OctetString)var0).getOctets();
            var1 = new DERUTF8String(var2);
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
         var1 = (DERUTF8String)var0;
      }

      return var1;
   }

   public static DERUTF8String getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DERUTF8String)) {
         var2 = 0;
      } else {
         DERUTF8String var3 = (DERUTF8String)var1;
         String var4 = this.getString();
         String var5 = var3.getString();
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = Strings.toUTF8ByteArray(this.string);
      var1.writeEncoded(12, var2);
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
