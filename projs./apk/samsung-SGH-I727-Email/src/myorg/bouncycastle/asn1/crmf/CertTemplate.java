package myorg.bouncycastle.asn1.crmf;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.crmf.OptionalValidity;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;

public class CertTemplate extends ASN1Encodable {

   private X509Extensions extensions;
   private X509Name issuer;
   private DERBitString issuerUID;
   private SubjectPublicKeyInfo publicKey;
   private DERInteger serialNumber;
   private AlgorithmIdentifier signingAlg;
   private X509Name subject;
   private DERBitString subjectUID;
   private OptionalValidity validity;
   private DERInteger version;


   private CertTemplate(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var2.nextElement();
         switch(var3.getTagNo()) {
         case 0:
            DERInteger var7 = DERInteger.getInstance(var3, (boolean)0);
            this.version = var7;
            break;
         case 1:
            DERInteger var8 = DERInteger.getInstance(var3, (boolean)0);
            this.serialNumber = var8;
            break;
         case 2:
            AlgorithmIdentifier var9 = AlgorithmIdentifier.getInstance(var3, (boolean)0);
            this.signingAlg = var9;
            break;
         case 3:
            X509Name var10 = X509Name.getInstance(var3, (boolean)1);
            this.issuer = var10;
            break;
         case 4:
            OptionalValidity var11 = OptionalValidity.getInstance(ASN1Sequence.getInstance(var3, (boolean)0));
            this.validity = var11;
            break;
         case 5:
            X509Name var12 = X509Name.getInstance(var3, (boolean)1);
            this.subject = var12;
            break;
         case 6:
            SubjectPublicKeyInfo var13 = SubjectPublicKeyInfo.getInstance(var3, (boolean)0);
            this.publicKey = var13;
            break;
         case 7:
            DERBitString var14 = DERBitString.getInstance(var3, (boolean)0);
            this.issuerUID = var14;
            break;
         case 8:
            DERBitString var15 = DERBitString.getInstance(var3, (boolean)0);
            this.subjectUID = var15;
            break;
         case 9:
            X509Extensions var16 = X509Extensions.getInstance(var3, (boolean)0);
            this.extensions = var16;
            break;
         default:
            StringBuilder var4 = (new StringBuilder()).append("unknown tag: ");
            int var5 = var3.getTagNo();
            String var6 = var4.append(var5).toString();
            throw new IllegalArgumentException(var6);
         }
      }

   }

   private void addOptional(ASN1EncodableVector var1, int var2, boolean var3, ASN1Encodable var4) {
      if(var4 != null) {
         DERTaggedObject var5 = new DERTaggedObject(var3, var2, var4);
         var1.add(var5);
      }
   }

   public static CertTemplate getInstance(Object var0) {
      CertTemplate var1;
      if(var0 instanceof CertTemplate) {
         var1 = (CertTemplate)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new CertTemplate(var2);
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      this.addOptional(var1, 0, (boolean)0, var2);
      DERInteger var3 = this.serialNumber;
      this.addOptional(var1, 1, (boolean)0, var3);
      AlgorithmIdentifier var4 = this.signingAlg;
      this.addOptional(var1, 2, (boolean)0, var4);
      X509Name var5 = this.issuer;
      this.addOptional(var1, 3, (boolean)1, var5);
      OptionalValidity var6 = this.validity;
      this.addOptional(var1, 4, (boolean)0, var6);
      X509Name var7 = this.subject;
      this.addOptional(var1, 5, (boolean)1, var7);
      SubjectPublicKeyInfo var8 = this.publicKey;
      this.addOptional(var1, 6, (boolean)0, var8);
      DERBitString var9 = this.issuerUID;
      this.addOptional(var1, 7, (boolean)0, var9);
      DERBitString var10 = this.subjectUID;
      this.addOptional(var1, 8, (boolean)0, var10);
      X509Extensions var11 = this.extensions;
      this.addOptional(var1, 9, (boolean)0, var11);
      return new DERSequence(var1);
   }
}
