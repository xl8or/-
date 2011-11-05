package myorg.bouncycastle.asn1.x509;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.x509.X509Extension;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class X509ExtensionsGenerator {

   private Vector extOrdering;
   private Hashtable extensions;


   public X509ExtensionsGenerator() {
      Hashtable var1 = new Hashtable();
      this.extensions = var1;
      Vector var2 = new Vector();
      this.extOrdering = var2;
   }

   public void addExtension(DERObjectIdentifier var1, boolean var2, DEREncodable var3) {
      try {
         byte[] var4 = var3.getDERObject().getEncoded("DER");
         this.addExtension(var1, var2, var4);
      } catch (IOException var7) {
         String var6 = "error encoding value: " + var7;
         throw new IllegalArgumentException(var6);
      }
   }

   public void addExtension(DERObjectIdentifier var1, boolean var2, byte[] var3) {
      if(this.extensions.containsKey(var1)) {
         String var4 = "extension " + var1 + " already added";
         throw new IllegalArgumentException(var4);
      } else {
         this.extOrdering.addElement(var1);
         Hashtable var5 = this.extensions;
         DEROctetString var6 = new DEROctetString(var3);
         X509Extension var7 = new X509Extension(var2, var6);
         var5.put(var1, var7);
      }
   }

   public X509Extensions generate() {
      Vector var1 = this.extOrdering;
      Hashtable var2 = this.extensions;
      return new X509Extensions(var1, var2);
   }

   public boolean isEmpty() {
      return this.extOrdering.isEmpty();
   }

   public void reset() {
      Hashtable var1 = new Hashtable();
      this.extensions = var1;
      Vector var2 = new Vector();
      this.extOrdering = var2;
   }
}
