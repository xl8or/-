package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1ParsingException;
import myorg.bouncycastle.asn1.DERObject;

class LazyDERConstructionEnumeration implements Enumeration {

   private ASN1InputStream aIn;
   private Object nextObj;


   public LazyDERConstructionEnumeration(byte[] var1) {
      ASN1InputStream var2 = new ASN1InputStream(var1, (boolean)1);
      this.aIn = var2;
      Object var3 = this.readObject();
      this.nextObj = var3;
   }

   private Object readObject() {
      try {
         DERObject var1 = this.aIn.readObject();
         return var1;
      } catch (IOException var4) {
         String var3 = "malformed DER construction: " + var4;
         throw new ASN1ParsingException(var3, var4);
      }
   }

   public boolean hasMoreElements() {
      boolean var1;
      if(this.nextObj != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Object nextElement() {
      Object var1 = this.nextObj;
      Object var2 = this.readObject();
      this.nextObj = var2;
      return var1;
   }
}
