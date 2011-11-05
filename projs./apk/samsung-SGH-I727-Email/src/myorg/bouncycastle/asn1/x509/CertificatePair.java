package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.X509CertificateStructure;

public class CertificatePair extends ASN1Encodable {

   private X509CertificateStructure forward;
   private X509CertificateStructure reverse;


   private CertificatePair(ASN1Sequence var1) {
      if(var1.size() != 1 && var1.size() != 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         Enumeration var5 = var1.getObjects();

         while(var5.hasMoreElements()) {
            ASN1TaggedObject var6 = ASN1TaggedObject.getInstance(var5.nextElement());
            if(var6.getTagNo() == 0) {
               X509CertificateStructure var7 = X509CertificateStructure.getInstance(var6, (boolean)1);
               this.forward = var7;
            } else {
               if(var6.getTagNo() != 1) {
                  StringBuilder var9 = (new StringBuilder()).append("Bad tag number: ");
                  int var10 = var6.getTagNo();
                  String var11 = var9.append(var10).toString();
                  throw new IllegalArgumentException(var11);
               }

               X509CertificateStructure var8 = X509CertificateStructure.getInstance(var6, (boolean)1);
               this.reverse = var8;
            }
         }

      }
   }

   public CertificatePair(X509CertificateStructure var1, X509CertificateStructure var2) {
      this.forward = var1;
      this.reverse = var2;
   }

   public static CertificatePair getInstance(Object var0) {
      CertificatePair var1;
      if(var0 != null && !(var0 instanceof CertificatePair)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertificatePair(var2);
      } else {
         var1 = (CertificatePair)var0;
      }

      return var1;
   }

   public X509CertificateStructure getForward() {
      return this.forward;
   }

   public X509CertificateStructure getReverse() {
      return this.reverse;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.forward != null) {
         X509CertificateStructure var2 = this.forward;
         DERTaggedObject var3 = new DERTaggedObject(0, var2);
         var1.add(var3);
      }

      if(this.reverse != null) {
         X509CertificateStructure var4 = this.reverse;
         DERTaggedObject var5 = new DERTaggedObject(1, var4);
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}
