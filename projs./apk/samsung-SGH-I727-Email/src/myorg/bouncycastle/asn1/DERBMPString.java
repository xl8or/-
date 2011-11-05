package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERString;

public class DERBMPString extends ASN1Object implements DERString {

   String string;


   public DERBMPString(String var1) {
      this.string = var1;
   }

   public DERBMPString(byte[] var1) {
      char[] var2 = new char[var1.length / 2];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            String var10 = new String(var2);
            this.string = var10;
            return;
         }

         int var5 = var3 * 2;
         int var6 = var1[var5] << 8;
         int var7 = var3 * 2 + 1;
         int var8 = var1[var7] & 255;
         char var9 = (char)(var6 | var8);
         var2[var3] = var9;
         ++var3;
      }
   }

   public static DERBMPString getInstance(Object var0) {
      DERBMPString var1;
      if(var0 != null && !(var0 instanceof DERBMPString)) {
         if(var0 instanceof ASN1OctetString) {
            byte[] var2 = ((ASN1OctetString)var0).getOctets();
            var1 = new DERBMPString(var2);
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
         var1 = (DERBMPString)var0;
      }

      return var1;
   }

   public static DERBMPString getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   protected boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DERBMPString)) {
         var2 = 0;
      } else {
         DERBMPString var3 = (DERBMPString)var1;
         String var4 = this.getString();
         String var5 = var3.getString();
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      char[] var2 = this.string.toCharArray();
      byte[] var3 = new byte[var2.length * 2];
      int var4 = 0;

      while(true) {
         int var5 = var2.length;
         if(var4 == var5) {
            var1.writeEncoded(30, var3);
            return;
         }

         int var6 = var4 * 2;
         byte var7 = (byte)(var2[var4] >> 8);
         var3[var6] = var7;
         int var8 = var4 * 2 + 1;
         byte var9 = (byte)var2[var4];
         var3[var8] = var9;
         ++var4;
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
