package myorg.bouncycastle.asn1.isismtt.x509;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBoolean;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;

public class DeclarationOfMajority extends ASN1Encodable implements ASN1Choice {

   public static final int dateOfBirth = 2;
   public static final int fullAgeAtCountry = 1;
   public static final int notYoungerThan;
   private ASN1TaggedObject declaration;


   public DeclarationOfMajority(int var1) {
      DERInteger var2 = new DERInteger(var1);
      DERTaggedObject var3 = new DERTaggedObject((boolean)0, 0, var2);
      this.declaration = var3;
   }

   private DeclarationOfMajority(ASN1TaggedObject var1) {
      if(var1.getTagNo() > 2) {
         StringBuilder var2 = (new StringBuilder()).append("Bad tag number: ");
         int var3 = var1.getTagNo();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         this.declaration = var1;
      }
   }

   public DeclarationOfMajority(DERGeneralizedTime var1) {
      DERTaggedObject var2 = new DERTaggedObject((boolean)0, 2, var1);
      this.declaration = var2;
   }

   public DeclarationOfMajority(boolean var1, String var2) {
      if(var2.length() > 2) {
         throw new IllegalArgumentException("country can only be 2 characters");
      } else if(var1) {
         DERPrintableString var3 = new DERPrintableString(var2, (boolean)1);
         DERSequence var4 = new DERSequence(var3);
         DERTaggedObject var5 = new DERTaggedObject((boolean)0, 1, var4);
         this.declaration = var5;
      } else {
         ASN1EncodableVector var6 = new ASN1EncodableVector();
         DERBoolean var7 = DERBoolean.FALSE;
         var6.add(var7);
         DERPrintableString var8 = new DERPrintableString(var2, (boolean)1);
         var6.add(var8);
         DERSequence var9 = new DERSequence(var6);
         DERTaggedObject var10 = new DERTaggedObject((boolean)0, 1, var9);
         this.declaration = var10;
      }
   }

   public static DeclarationOfMajority getInstance(Object var0) {
      DeclarationOfMajority var1;
      if(var0 != null && !(var0 instanceof DeclarationOfMajority)) {
         if(!(var0 instanceof ASN1TaggedObject)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1TaggedObject var2 = (ASN1TaggedObject)var0;
         var1 = new DeclarationOfMajority(var2);
      } else {
         var1 = (DeclarationOfMajority)var0;
      }

      return var1;
   }

   public ASN1Sequence fullAgeAtCountry() {
      ASN1Sequence var1;
      if(this.declaration.getTagNo() != 1) {
         var1 = null;
      } else {
         var1 = ASN1Sequence.getInstance(this.declaration, (boolean)0);
      }

      return var1;
   }

   public DERGeneralizedTime getDateOfBirth() {
      DERGeneralizedTime var1;
      if(this.declaration.getTagNo() != 2) {
         var1 = null;
      } else {
         var1 = DERGeneralizedTime.getInstance(this.declaration, (boolean)0);
      }

      return var1;
   }

   public int getType() {
      return this.declaration.getTagNo();
   }

   public int notYoungerThan() {
      int var1;
      if(this.declaration.getTagNo() != 0) {
         var1 = -1;
      } else {
         var1 = DERInteger.getInstance(this.declaration, (boolean)0).getValue().intValue();
      }

      return var1;
   }

   public DERObject toASN1Object() {
      return this.declaration;
   }
}
