package myorg.bouncycastle.asn1.pkcs;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
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

public class PBES2Parameters extends ASN1Encodable implements PKCSObjectIdentifiers {

   private KeyDerivationFunc func;
   private EncryptionScheme scheme;


   public PBES2Parameters(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      ASN1Sequence var3 = (ASN1Sequence)var2.nextElement();
      DEREncodable var4 = var3.getObjectAt(0);
      DERObjectIdentifier var5 = id_PBKDF2;
      if(var4.equals(var5)) {
         DERObjectIdentifier var6 = id_PBKDF2;
         PBKDF2Params var7 = PBKDF2Params.getInstance(var3.getObjectAt(1));
         KeyDerivationFunc var8 = new KeyDerivationFunc(var6, var7);
         this.func = var8;
      } else {
         KeyDerivationFunc var11 = new KeyDerivationFunc(var3);
         this.func = var11;
      }

      ASN1Sequence var9 = (ASN1Sequence)var2.nextElement();
      EncryptionScheme var10 = new EncryptionScheme(var9);
      this.scheme = var10;
   }

   public EncryptionScheme getEncryptionScheme() {
      return this.scheme;
   }

   public KeyDerivationFunc getKeyDerivationFunc() {
      return this.func;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      KeyDerivationFunc var2 = this.func;
      var1.add(var2);
      EncryptionScheme var3 = this.scheme;
      var1.add(var3);
      return new DERSequence(var1);
   }
}
