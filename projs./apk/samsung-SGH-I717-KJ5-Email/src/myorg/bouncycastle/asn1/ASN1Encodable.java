package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;

public abstract class ASN1Encodable implements DEREncodable {

   public static final String BER = "BER";
   public static final String DER = "DER";


   public ASN1Encodable() {}

   public boolean equals(Object var1) {
      byte var2;
      if(this == var1) {
         var2 = 1;
      } else if(!(var1 instanceof DEREncodable)) {
         var2 = 0;
      } else {
         DEREncodable var3 = (DEREncodable)var1;
         DERObject var4 = this.toASN1Object();
         DERObject var5 = var3.getDERObject();
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   public byte[] getDEREncoded() {
      byte[] var1;
      byte[] var2;
      try {
         var1 = this.getEncoded("DER");
      } catch (IOException var4) {
         var2 = null;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public DERObject getDERObject() {
      return this.toASN1Object();
   }

   public byte[] getEncoded() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      (new ASN1OutputStream(var1)).writeObject(this);
      return var1.toByteArray();
   }

   public byte[] getEncoded(String var1) throws IOException {
      byte[] var3;
      if(var1.equals("DER")) {
         ByteArrayOutputStream var2 = new ByteArrayOutputStream();
         (new DEROutputStream(var2)).writeObject(this);
         var3 = var2.toByteArray();
      } else {
         var3 = this.getEncoded();
      }

      return var3;
   }

   public int hashCode() {
      return this.toASN1Object().hashCode();
   }

   public abstract DERObject toASN1Object();
}
