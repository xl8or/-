package myorg.bouncycastle.asn1.pkcs;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.pkcs.EncryptionScheme;
import myorg.bouncycastle.asn1.pkcs.KeyDerivationFunc;
import myorg.bouncycastle.asn1.pkcs.PBKDF2Params;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PBES2Algorithms extends AlgorithmIdentifier implements PKCSObjectIdentifiers {

   private KeyDerivationFunc func;
   private DERObjectIdentifier objectId;
   private EncryptionScheme scheme;


   public PBES2Algorithms(ASN1Sequence var1) {
      super(var1);
      Enumeration var2 = var1.getObjects();
      DERObjectIdentifier var3 = (DERObjectIdentifier)var2.nextElement();
      this.objectId = var3;
      Enumeration var4 = ((ASN1Sequence)var2.nextElement()).getObjects();
      ASN1Sequence var5 = (ASN1Sequence)var4.nextElement();
      DEREncodable var6 = var5.getObjectAt(0);
      DERObjectIdentifier var7 = id_PBKDF2;
      if(var6.equals(var7)) {
         DERObjectIdentifier var8 = id_PBKDF2;
         PBKDF2Params var9 = PBKDF2Params.getInstance(var5.getObjectAt(1));
         KeyDerivationFunc var10 = new KeyDerivationFunc(var8, var9);
         this.func = var10;
      } else {
         KeyDerivationFunc var13 = new KeyDerivationFunc(var5);
         this.func = var13;
      }

      ASN1Sequence var11 = (ASN1Sequence)var4.nextElement();
      EncryptionScheme var12 = new EncryptionScheme(var11);
      this.scheme = var12;
   }

   public DERObject getDERObject() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1EncodableVector var2 = new ASN1EncodableVector();
      DERObjectIdentifier var3 = this.objectId;
      var1.add(var3);
      KeyDerivationFunc var4 = this.func;
      var2.add(var4);
      EncryptionScheme var5 = this.scheme;
      var2.add(var5);
      DERSequence var6 = new DERSequence(var2);
      var1.add(var6);
      return new DERSequence(var1);
   }

   public EncryptionScheme getEncryptionScheme() {
      return this.scheme;
   }

   public KeyDerivationFunc getKeyDerivationFunc() {
      return this.func;
   }

   public DERObjectIdentifier getObjectId() {
      return this.objectId;
   }
}
