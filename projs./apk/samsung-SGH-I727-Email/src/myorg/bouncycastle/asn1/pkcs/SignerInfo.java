package myorg.bouncycastle.asn1.pkcs;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.pkcs.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class SignerInfo extends ASN1Encodable {

   private ASN1Set authenticatedAttributes;
   private AlgorithmIdentifier digAlgorithm;
   private AlgorithmIdentifier digEncryptionAlgorithm;
   private ASN1OctetString encryptedDigest;
   private IssuerAndSerialNumber issuerAndSerialNumber;
   private ASN1Set unauthenticatedAttributes;
   private DERInteger version;


   public SignerInfo(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      DERInteger var3 = (DERInteger)var2.nextElement();
      this.version = var3;
      IssuerAndSerialNumber var4 = IssuerAndSerialNumber.getInstance(var2.nextElement());
      this.issuerAndSerialNumber = var4;
      AlgorithmIdentifier var5 = AlgorithmIdentifier.getInstance(var2.nextElement());
      this.digAlgorithm = var5;
      Object var6 = var2.nextElement();
      if(var6 instanceof ASN1TaggedObject) {
         ASN1Set var7 = ASN1Set.getInstance((ASN1TaggedObject)var6, (boolean)0);
         this.authenticatedAttributes = var7;
         AlgorithmIdentifier var8 = AlgorithmIdentifier.getInstance(var2.nextElement());
         this.digEncryptionAlgorithm = var8;
      } else {
         this.authenticatedAttributes = null;
         AlgorithmIdentifier var11 = AlgorithmIdentifier.getInstance(var6);
         this.digEncryptionAlgorithm = var11;
      }

      ASN1OctetString var9 = DEROctetString.getInstance(var2.nextElement());
      this.encryptedDigest = var9;
      if(var2.hasMoreElements()) {
         ASN1Set var10 = ASN1Set.getInstance((ASN1TaggedObject)var2.nextElement(), (boolean)0);
         this.unauthenticatedAttributes = var10;
      } else {
         this.unauthenticatedAttributes = null;
      }
   }

   public SignerInfo(DERInteger var1, IssuerAndSerialNumber var2, AlgorithmIdentifier var3, ASN1Set var4, AlgorithmIdentifier var5, ASN1OctetString var6, ASN1Set var7) {
      this.version = var1;
      this.issuerAndSerialNumber = var2;
      this.digAlgorithm = var3;
      this.authenticatedAttributes = var4;
      this.digEncryptionAlgorithm = var5;
      this.encryptedDigest = var6;
      this.unauthenticatedAttributes = var7;
   }

   public static SignerInfo getInstance(Object var0) {
      SignerInfo var1;
      if(var0 instanceof SignerInfo) {
         var1 = (SignerInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SignerInfo(var2);
      }

      return var1;
   }

   public ASN1Set getAuthenticatedAttributes() {
      return this.authenticatedAttributes;
   }

   public AlgorithmIdentifier getDigestAlgorithm() {
      return this.digAlgorithm;
   }

   public AlgorithmIdentifier getDigestEncryptionAlgorithm() {
      return this.digEncryptionAlgorithm;
   }

   public ASN1OctetString getEncryptedDigest() {
      return this.encryptedDigest;
   }

   public IssuerAndSerialNumber getIssuerAndSerialNumber() {
      return this.issuerAndSerialNumber;
   }

   public ASN1Set getUnauthenticatedAttributes() {
      return this.unauthenticatedAttributes;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      IssuerAndSerialNumber var3 = this.issuerAndSerialNumber;
      var1.add(var3);
      AlgorithmIdentifier var4 = this.digAlgorithm;
      var1.add(var4);
      if(this.authenticatedAttributes != null) {
         ASN1Set var5 = this.authenticatedAttributes;
         DERTaggedObject var6 = new DERTaggedObject((boolean)0, 0, var5);
         var1.add(var6);
      }

      AlgorithmIdentifier var7 = this.digEncryptionAlgorithm;
      var1.add(var7);
      ASN1OctetString var8 = this.encryptedDigest;
      var1.add(var8);
      if(this.unauthenticatedAttributes != null) {
         ASN1Set var9 = this.unauthenticatedAttributes;
         DERTaggedObject var10 = new DERTaggedObject((boolean)0, 1, var9);
         var1.add(var10);
      }

      return new DERSequence(var1);
   }
}
