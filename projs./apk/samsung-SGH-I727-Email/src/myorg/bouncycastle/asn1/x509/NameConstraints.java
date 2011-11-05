package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.GeneralSubtree;

public class NameConstraints extends ASN1Encodable {

   private ASN1Sequence excluded;
   private ASN1Sequence permitted;


   public NameConstraints(Vector var1, Vector var2) {
      if(var1 != null) {
         DERSequence var3 = this.createSequence(var1);
         this.permitted = var3;
      }

      if(var2 != null) {
         DERSequence var4 = this.createSequence(var2);
         this.excluded = var4;
      }
   }

   public NameConstraints(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var3 = ASN1TaggedObject.getInstance(var2.nextElement());
         switch(var3.getTagNo()) {
         case 0:
            ASN1Sequence var4 = ASN1Sequence.getInstance(var3, (boolean)0);
            this.permitted = var4;
            break;
         case 1:
            ASN1Sequence var5 = ASN1Sequence.getInstance(var3, (boolean)0);
            this.excluded = var5;
         }
      }

   }

   private DERSequence createSequence(Vector var1) {
      ASN1EncodableVector var2 = new ASN1EncodableVector();
      Enumeration var3 = var1.elements();

      while(var3.hasMoreElements()) {
         GeneralSubtree var4 = (GeneralSubtree)var3.nextElement();
         var2.add(var4);
      }

      return new DERSequence(var2);
   }

   public ASN1Sequence getExcludedSubtrees() {
      return this.excluded;
   }

   public ASN1Sequence getPermittedSubtrees() {
      return this.permitted;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.permitted != null) {
         ASN1Sequence var2 = this.permitted;
         DERTaggedObject var3 = new DERTaggedObject((boolean)0, 0, var2);
         var1.add(var3);
      }

      if(this.excluded != null) {
         ASN1Sequence var4 = this.excluded;
         DERTaggedObject var5 = new DERTaggedObject((boolean)0, 1, var4);
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}
