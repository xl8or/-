package myorg.bouncycastle.asn1.pkcs;

import java.io.IOException;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PrivateKeyInfo extends ASN1Encodable {

   private AlgorithmIdentifier algId;
   private ASN1Set attributes;
   private DERObject privKey;


   public PrivateKeyInfo(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      if(((DERInteger)var2.nextElement()).getValue().intValue() != 0) {
         throw new IllegalArgumentException("wrong version for private key info");
      } else {
         ASN1Sequence var3 = (ASN1Sequence)var2.nextElement();
         AlgorithmIdentifier var4 = new AlgorithmIdentifier(var3);
         this.algId = var4;

         try {
            byte[] var5 = ((ASN1OctetString)var2.nextElement()).getOctets();
            DERObject var6 = (new ASN1InputStream(var5)).readObject();
            this.privKey = var6;
         } catch (IOException var9) {
            throw new IllegalArgumentException("Error recoverying private key from sequence");
         }

         if(var2.hasMoreElements()) {
            ASN1Set var7 = ASN1Set.getInstance((ASN1TaggedObject)var2.nextElement(), (boolean)0);
            this.attributes = var7;
         }
      }
   }

   public PrivateKeyInfo(AlgorithmIdentifier var1, DERObject var2) {
      this(var1, var2, (ASN1Set)null);
   }

   public PrivateKeyInfo(AlgorithmIdentifier var1, DERObject var2, ASN1Set var3) {
      this.privKey = var2;
      this.algId = var1;
      this.attributes = var3;
   }

   public static PrivateKeyInfo getInstance(Object var0) {
      PrivateKeyInfo var1;
      if(var0 instanceof PrivateKeyInfo) {
         var1 = (PrivateKeyInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PrivateKeyInfo(var2);
      }

      return var1;
   }

   public static PrivateKeyInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public AlgorithmIdentifier getAlgorithmId() {
      return this.algId;
   }

   public ASN1Set getAttributes() {
      return this.attributes;
   }

   public DERObject getPrivateKey() {
      return this.privKey;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = new DERInteger(0);
      var1.add(var2);
      AlgorithmIdentifier var3 = this.algId;
      var1.add(var3);
      DERObject var4 = this.privKey;
      DEROctetString var5 = new DEROctetString(var4);
      var1.add(var5);
      if(this.attributes != null) {
         ASN1Set var6 = this.attributes;
         DERTaggedObject var7 = new DERTaggedObject((boolean)0, 0, var6);
         var1.add(var7);
      }

      return new DERSequence(var1);
   }
}
