package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.ocsp.CertID;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class Request extends ASN1Encodable {

   CertID reqCert;
   X509Extensions singleRequestExtensions;


   public Request(ASN1Sequence var1) {
      CertID var2 = CertID.getInstance(var1.getObjectAt(0));
      this.reqCert = var2;
      if(var1.size() == 2) {
         X509Extensions var3 = X509Extensions.getInstance((ASN1TaggedObject)var1.getObjectAt(1), (boolean)1);
         this.singleRequestExtensions = var3;
      }
   }

   public Request(CertID var1, X509Extensions var2) {
      this.reqCert = var1;
      this.singleRequestExtensions = var2;
   }

   public static Request getInstance(Object var0) {
      Request var1;
      if(var0 != null && !(var0 instanceof Request)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new Request(var2);
      } else {
         var1 = (Request)var0;
      }

      return var1;
   }

   public static Request getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public CertID getReqCert() {
      return this.reqCert;
   }

   public X509Extensions getSingleRequestExtensions() {
      return this.singleRequestExtensions;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      CertID var2 = this.reqCert;
      var1.add(var2);
      if(this.singleRequestExtensions != null) {
         X509Extensions var3 = this.singleRequestExtensions;
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, 0, var3);
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}
