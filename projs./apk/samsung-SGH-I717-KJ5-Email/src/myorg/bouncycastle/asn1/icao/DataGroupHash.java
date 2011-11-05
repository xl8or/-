package myorg.bouncycastle.asn1.icao;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;

public class DataGroupHash extends ASN1Encodable {

   ASN1OctetString dataGroupHashValue;
   DERInteger dataGroupNumber;


   public DataGroupHash(int var1, ASN1OctetString var2) {
      DERInteger var3 = new DERInteger(var1);
      this.dataGroupNumber = var3;
      this.dataGroupHashValue = var2;
   }

   public DataGroupHash(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERInteger var3 = DERInteger.getInstance(var2.nextElement());
      this.dataGroupNumber = var3;
      ASN1OctetString var4 = ASN1OctetString.getInstance(var2.nextElement());
      this.dataGroupHashValue = var4;
   }

   public static DataGroupHash getInstance(Object var0) {
      DataGroupHash var1;
      if(var0 != null && !(var0 instanceof DataGroupHash)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new DataGroupHash(var2);
      } else {
         var1 = (DataGroupHash)var0;
      }

      return var1;
   }

   public ASN1OctetString getDataGroupHashValue() {
      return this.dataGroupHashValue;
   }

   public int getDataGroupNumber() {
      return this.dataGroupNumber.getValue().intValue();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.dataGroupNumber;
      var1.add(var2);
      ASN1OctetString var3 = this.dataGroupHashValue;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
