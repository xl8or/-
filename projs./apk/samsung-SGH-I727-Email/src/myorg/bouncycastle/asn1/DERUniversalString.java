package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERString;

public class DERUniversalString extends ASN1Object implements DERString {

   private static final char[] table = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
   private byte[] string;


   public DERUniversalString(byte[] var1) {
      this.string = var1;
   }

   public static DERUniversalString getInstance(Object var0) {
      DERUniversalString var1;
      if(var0 != null && !(var0 instanceof DERUniversalString)) {
         if(!(var0 instanceof ASN1OctetString)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         byte[] var2 = ((ASN1OctetString)var0).getOctets();
         var1 = new DERUniversalString(var2);
      } else {
         var1 = (DERUniversalString)var0;
      }

      return var1;
   }

   public static DERUniversalString getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DERUniversalString)) {
         var2 = 0;
      } else {
         String var3 = this.getString();
         String var4 = ((DERUniversalString)var1).getString();
         var2 = var3.equals(var4);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      byte[] var2 = this.getOctets();
      var1.writeEncoded(28, var2);
   }

   public byte[] getOctets() {
      return this.string;
   }

   public String getString() {
      StringBuffer var1 = new StringBuffer("#");
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      ASN1OutputStream var3 = new ASN1OutputStream(var2);

      try {
         var3.writeObject(this);
      } catch (IOException var16) {
         throw new RuntimeException("internal error encoding BitString");
      }

      byte[] var4 = var2.toByteArray();
      int var5 = 0;

      while(true) {
         int var6 = var4.length;
         if(var5 == var6) {
            return var1.toString();
         }

         char[] var7 = table;
         int var8 = var4[var5] >>> 4 & 15;
         char var9 = var7[var8];
         var1.append(var9);
         char[] var11 = table;
         int var12 = var4[var5] & 15;
         char var13 = var11[var12];
         var1.append(var13);
         ++var5;
      }
   }

   public int hashCode() {
      return this.getString().hashCode();
   }

   public String toString() {
      return this.getString();
   }
}
