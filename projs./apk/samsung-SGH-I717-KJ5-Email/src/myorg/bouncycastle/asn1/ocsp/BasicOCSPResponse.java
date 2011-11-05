package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.ocsp.ResponseData;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class BasicOCSPResponse extends ASN1Encodable {

   private ASN1Sequence certs;
   private DERBitString signature;
   private AlgorithmIdentifier signatureAlgorithm;
   private ResponseData tbsResponseData;


   public BasicOCSPResponse(ASN1Sequence var1) {
      ResponseData var2 = ResponseData.getInstance(var1.getObjectAt(0));
      this.tbsResponseData = var2;
      AlgorithmIdentifier var3 = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
      this.signatureAlgorithm = var3;
      DERBitString var4 = (DERBitString)var1.getObjectAt(2);
      this.signature = var4;
      if(var1.size() > 3) {
         ASN1Sequence var5 = ASN1Sequence.getInstance((ASN1TaggedObject)var1.getObjectAt(3), (boolean)1);
         this.certs = var5;
      }
   }

   public BasicOCSPResponse(ResponseData var1, AlgorithmIdentifier var2, DERBitString var3, ASN1Sequence var4) {
      this.tbsResponseData = var1;
      this.signatureAlgorithm = var2;
      this.signature = var3;
      this.certs = var4;
   }

   public static BasicOCSPResponse getInstance(Object var0) {
      BasicOCSPResponse var1;
      if(var0 != null && !(var0 instanceof BasicOCSPResponse)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new BasicOCSPResponse(var2);
      } else {
         var1 = (BasicOCSPResponse)var0;
      }

      return var1;
   }

   public static BasicOCSPResponse getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public ASN1Sequence getCerts() {
      return this.certs;
   }

   public DERBitString getSignature() {
      return this.signature;
   }

   public AlgorithmIdentifier getSignatureAlgorithm() {
      return this.signatureAlgorithm;
   }

   public ResponseData getTbsResponseData() {
      return this.tbsResponseData;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ResponseData var2 = this.tbsResponseData;
      var1.add(var2);
      AlgorithmIdentifier var3 = this.signatureAlgorithm;
      var1.add(var3);
      DERBitString var4 = this.signature;
      var1.add(var4);
      if(this.certs != null) {
         ASN1Sequence var5 = this.certs;
         DERTaggedObject var6 = new DERTaggedObject((boolean)1, 0, var5);
         var1.add(var6);
      }

      return new DERSequence(var1);
   }
}
