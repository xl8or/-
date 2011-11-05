package myorg.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cmp.PKIBody;
import myorg.bouncycastle.asn1.cmp.PKIHeader;

public class PKIMessage extends ASN1Encodable {

   private PKIBody body;
   private ASN1Sequence extraCerts;
   private PKIHeader header;
   private DERBitString protection;


   private PKIMessage(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      PKIHeader var3 = PKIHeader.getInstance(var2.nextElement());
      this.header = var3;
      PKIBody var4 = PKIBody.getInstance(var2.nextElement());
      this.body = var4;

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var5 = (ASN1TaggedObject)var2.nextElement();
         if(var5.getTagNo() == 0) {
            DERBitString var6 = DERBitString.getInstance(var5, (boolean)1);
            this.protection = var6;
         } else {
            ASN1Sequence var7 = ASN1Sequence.getInstance(var5, (boolean)1);
            this.extraCerts = var7;
         }
      }

   }

   private void addOptional(ASN1EncodableVector var1, int var2, ASN1Encodable var3) {
      if(var3 != null) {
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, var2, var3);
         var1.add(var4);
      }
   }

   public static PKIMessage getInstance(Object var0) {
      PKIMessage var1;
      if(var0 instanceof PKIMessage) {
         var1 = (PKIMessage)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid object: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PKIMessage(var2);
      }

      return var1;
   }

   public PKIBody getBody() {
      return this.body;
   }

   public PKIHeader getHeader() {
      return this.header;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      PKIHeader var2 = this.header;
      var1.add(var2);
      PKIBody var3 = this.body;
      var1.add(var3);
      DERBitString var4 = this.protection;
      this.addOptional(var1, 0, var4);
      ASN1Sequence var5 = this.extraCerts;
      this.addOptional(var1, 1, var5);
      return new DERSequence(var1);
   }
}
