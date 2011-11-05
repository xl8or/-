package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class PolicyMappings extends ASN1Encodable {

   ASN1Sequence seq = null;


   public PolicyMappings(Hashtable var1) {
      ASN1EncodableVector var2 = new ASN1EncodableVector();
      Enumeration var3 = var1.keys();

      while(var3.hasMoreElements()) {
         String var4 = (String)var3.nextElement();
         String var5 = (String)var1.get(var4);
         ASN1EncodableVector var6 = new ASN1EncodableVector();
         DERObjectIdentifier var7 = new DERObjectIdentifier(var4);
         var6.add(var7);
         DERObjectIdentifier var8 = new DERObjectIdentifier(var5);
         var6.add(var8);
         DERSequence var9 = new DERSequence(var6);
         var2.add(var9);
      }

      DERSequence var10 = new DERSequence(var2);
      this.seq = var10;
   }

   public PolicyMappings(ASN1Sequence var1) {
      this.seq = var1;
   }

   public DERObject toASN1Object() {
      return this.seq;
   }
}
