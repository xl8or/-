package myorg.bouncycastle.asn1.x509.sigi;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x500.DirectoryString;
import myorg.bouncycastle.asn1.x509.sigi.NameOrPseudonym;

public class PersonalData extends ASN1Encodable {

   private DERGeneralizedTime dateOfBirth;
   private String gender;
   private BigInteger nameDistinguisher;
   private NameOrPseudonym nameOrPseudonym;
   private DirectoryString placeOfBirth;
   private DirectoryString postalAddress;


   private PersonalData(ASN1Sequence var1) {
      if(var1.size() < 1) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         Enumeration var5 = var1.getObjects();
         NameOrPseudonym var6 = NameOrPseudonym.getInstance(var5.nextElement());
         this.nameOrPseudonym = var6;

         while(var5.hasMoreElements()) {
            ASN1TaggedObject var7 = ASN1TaggedObject.getInstance(var5.nextElement());
            switch(var7.getTagNo()) {
            case 0:
               BigInteger var11 = DERInteger.getInstance(var7, (boolean)0).getValue();
               this.nameDistinguisher = var11;
               break;
            case 1:
               DERGeneralizedTime var12 = DERGeneralizedTime.getInstance(var7, (boolean)0);
               this.dateOfBirth = var12;
               break;
            case 2:
               DirectoryString var13 = DirectoryString.getInstance(var7, (boolean)1);
               this.placeOfBirth = var13;
               break;
            case 3:
               String var14 = DERPrintableString.getInstance(var7, (boolean)0).getString();
               this.gender = var14;
               break;
            case 4:
               DirectoryString var15 = DirectoryString.getInstance(var7, (boolean)1);
               this.postalAddress = var15;
               break;
            default:
               StringBuilder var8 = (new StringBuilder()).append("Bad tag number: ");
               int var9 = var7.getTagNo();
               String var10 = var8.append(var9).toString();
               throw new IllegalArgumentException(var10);
            }
         }

      }
   }

   public PersonalData(NameOrPseudonym var1, BigInteger var2, DERGeneralizedTime var3, DirectoryString var4, String var5, DirectoryString var6) {
      this.nameOrPseudonym = var1;
      this.dateOfBirth = var3;
      this.gender = var5;
      this.nameDistinguisher = var2;
      this.postalAddress = var6;
      this.placeOfBirth = var4;
   }

   public static PersonalData getInstance(Object var0) {
      PersonalData var1;
      if(var0 != null && !(var0 instanceof PersonalData)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PersonalData(var2);
      } else {
         var1 = (PersonalData)var0;
      }

      return var1;
   }

   public DERGeneralizedTime getDateOfBirth() {
      return this.dateOfBirth;
   }

   public String getGender() {
      return this.gender;
   }

   public BigInteger getNameDistinguisher() {
      return this.nameDistinguisher;
   }

   public NameOrPseudonym getNameOrPseudonym() {
      return this.nameOrPseudonym;
   }

   public DirectoryString getPlaceOfBirth() {
      return this.placeOfBirth;
   }

   public DirectoryString getPostalAddress() {
      return this.postalAddress;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      NameOrPseudonym var2 = this.nameOrPseudonym;
      var1.add(var2);
      if(this.nameDistinguisher != null) {
         BigInteger var3 = this.nameDistinguisher;
         DERInteger var4 = new DERInteger(var3);
         DERTaggedObject var5 = new DERTaggedObject((boolean)0, 0, var4);
         var1.add(var5);
      }

      if(this.dateOfBirth != null) {
         DERGeneralizedTime var6 = this.dateOfBirth;
         DERTaggedObject var7 = new DERTaggedObject((boolean)0, 1, var6);
         var1.add(var7);
      }

      if(this.placeOfBirth != null) {
         DirectoryString var8 = this.placeOfBirth;
         DERTaggedObject var9 = new DERTaggedObject((boolean)1, 2, var8);
         var1.add(var9);
      }

      if(this.gender != null) {
         String var10 = this.gender;
         DERPrintableString var11 = new DERPrintableString(var10, (boolean)1);
         DERTaggedObject var12 = new DERTaggedObject((boolean)0, 3, var11);
         var1.add(var12);
      }

      if(this.postalAddress != null) {
         DirectoryString var13 = this.postalAddress;
         DERTaggedObject var14 = new DERTaggedObject((boolean)1, 4, var13);
         var1.add(var14);
      }

      return new DERSequence(var1);
   }
}
