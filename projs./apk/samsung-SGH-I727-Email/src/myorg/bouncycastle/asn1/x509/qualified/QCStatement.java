package myorg.bouncycastle.asn1.x509.qualified;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.qualified.ETSIQCObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.qualified.RFC3739QCObjectIdentifiers;

public class QCStatement extends ASN1Encodable implements ETSIQCObjectIdentifiers, RFC3739QCObjectIdentifiers {

   DERObjectIdentifier qcStatementId;
   ASN1Encodable qcStatementInfo;


   public QCStatement(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERObjectIdentifier var3 = DERObjectIdentifier.getInstance(var2.nextElement());
      this.qcStatementId = var3;
      if(var2.hasMoreElements()) {
         ASN1Encodable var4 = (ASN1Encodable)var2.nextElement();
         this.qcStatementInfo = var4;
      }
   }

   public QCStatement(DERObjectIdentifier var1) {
      this.qcStatementId = var1;
      this.qcStatementInfo = null;
   }

   public QCStatement(DERObjectIdentifier var1, ASN1Encodable var2) {
      this.qcStatementId = var1;
      this.qcStatementInfo = var2;
   }

   public static QCStatement getInstance(Object var0) {
      QCStatement var1;
      if(var0 != null && !(var0 instanceof QCStatement)) {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("unknown object in getInstance");
         }

         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new QCStatement(var2);
      } else {
         var1 = (QCStatement)var0;
      }

      return var1;
   }

   public DERObjectIdentifier getStatementId() {
      return this.qcStatementId;
   }

   public ASN1Encodable getStatementInfo() {
      return this.qcStatementInfo;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.qcStatementId;
      var1.add(var2);
      if(this.qcStatementInfo != null) {
         ASN1Encodable var3 = this.qcStatementInfo;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}
