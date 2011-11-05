package myorg.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x500.DirectoryString;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.IssuerSerial;

public class ProcurationSyntax extends ASN1Encodable {

   private IssuerSerial certRef;
   private String country;
   private GeneralName thirdPerson;
   private DirectoryString typeOfSubstitution;


   public ProcurationSyntax(String var1, DirectoryString var2, GeneralName var3) {
      this.country = var1;
      this.typeOfSubstitution = var2;
      this.thirdPerson = var3;
      this.certRef = null;
   }

   public ProcurationSyntax(String var1, DirectoryString var2, IssuerSerial var3) {
      this.country = var1;
      this.typeOfSubstitution = var2;
      this.thirdPerson = null;
      this.certRef = var3;
   }

   private ProcurationSyntax(ASN1Sequence var1) {
      if(var1.size() >= 1 && var1.size() <= 3) {
         Enumeration var5 = var1.getObjects();

         while(var5.hasMoreElements()) {
            ASN1TaggedObject var6 = ASN1TaggedObject.getInstance(var5.nextElement());
            switch(var6.getTagNo()) {
            case 1:
               String var10 = DERPrintableString.getInstance(var6, (boolean)1).getString();
               this.country = var10;
               break;
            case 2:
               DirectoryString var11 = DirectoryString.getInstance(var6, (boolean)1);
               this.typeOfSubstitution = var11;
               break;
            case 3:
               DERObject var12 = var6.getObject();
               if(var12 instanceof ASN1TaggedObject) {
                  GeneralName var13 = GeneralName.getInstance(var12);
                  this.thirdPerson = var13;
               } else {
                  IssuerSerial var14 = IssuerSerial.getInstance(var12);
                  this.certRef = var14;
               }
               break;
            default:
               StringBuilder var7 = (new StringBuilder()).append("Bad tag number: ");
               int var8 = var6.getTagNo();
               String var9 = var7.append(var8).toString();
               throw new IllegalArgumentException(var9);
            }
         }

      } else {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public static ProcurationSyntax getInstance(Object var0) {
      ProcurationSyntax var1;
      if(var0 != null && !(var0 instanceof ProcurationSyntax)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ProcurationSyntax(var2);
      } else {
         var1 = (ProcurationSyntax)var0;
      }

      return var1;
   }

   public IssuerSerial getCertRef() {
      return this.certRef;
   }

   public String getCountry() {
      return this.country;
   }

   public GeneralName getThirdPerson() {
      return this.thirdPerson;
   }

   public DirectoryString getTypeOfSubstitution() {
      return this.typeOfSubstitution;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.country != null) {
         String var2 = this.country;
         DERPrintableString var3 = new DERPrintableString(var2, (boolean)1);
         DERTaggedObject var4 = new DERTaggedObject((boolean)1, 1, var3);
         var1.add(var4);
      }

      if(this.typeOfSubstitution != null) {
         DirectoryString var5 = this.typeOfSubstitution;
         DERTaggedObject var6 = new DERTaggedObject((boolean)1, 2, var5);
         var1.add(var6);
      }

      if(this.thirdPerson != null) {
         GeneralName var7 = this.thirdPerson;
         DERTaggedObject var8 = new DERTaggedObject((boolean)1, 3, var7);
         var1.add(var8);
      } else {
         IssuerSerial var9 = this.certRef;
         DERTaggedObject var10 = new DERTaggedObject((boolean)1, 3, var9);
         var1.add(var10);
      }

      return new DERSequence(var1);
   }
}
