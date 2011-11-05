package myorg.bouncycastle.jce;

import java.io.IOException;
import java.security.Principal;
import java.util.Hashtable;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.x509.X509Name;

public class X509Principal extends X509Name implements Principal {

   public X509Principal(String var1) {
      super(var1);
   }

   public X509Principal(Hashtable var1) {
      super(var1);
   }

   public X509Principal(Vector var1, Hashtable var2) {
      super(var1, var2);
   }

   public X509Principal(Vector var1, Vector var2) {
      super(var1, var2);
   }

   public X509Principal(X509Name var1) {
      ASN1Sequence var2 = (ASN1Sequence)var1.getDERObject();
      super(var2);
   }

   public X509Principal(boolean var1, String var2) {
      super(var1, var2);
   }

   public X509Principal(boolean var1, Hashtable var2, String var3) {
      super(var1, var2, var3);
   }

   public X509Principal(byte[] var1) throws IOException {
      ASN1Sequence var2 = readSequence(new ASN1InputStream(var1));
      super(var2);
   }

   private static ASN1Sequence readSequence(ASN1InputStream var0) throws IOException {
      try {
         ASN1Sequence var1 = ASN1Sequence.getInstance(var0.readObject());
         return var1;
      } catch (IllegalArgumentException var4) {
         String var3 = "not an ASN.1 Sequence: " + var4;
         throw new IOException(var3);
      }
   }

   public byte[] getEncoded() {
      try {
         byte[] var1 = this.getEncoded("DER");
         return var1;
      } catch (IOException var3) {
         String var2 = var3.toString();
         throw new RuntimeException(var2);
      }
   }

   public String getName() {
      return this.toString();
   }
}
