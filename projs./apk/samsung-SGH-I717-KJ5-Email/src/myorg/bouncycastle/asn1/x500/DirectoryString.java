package myorg.bouncycastle.asn1.x500;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBMPString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.asn1.DERT61String;
import myorg.bouncycastle.asn1.DERUTF8String;
import myorg.bouncycastle.asn1.DERUniversalString;

public class DirectoryString extends ASN1Encodable implements ASN1Choice, DERString {

   private DERString string;


   public DirectoryString(String var1) {
      DERUTF8String var2 = new DERUTF8String(var1);
      this.string = var2;
   }

   private DirectoryString(DERBMPString var1) {
      this.string = var1;
   }

   private DirectoryString(DERPrintableString var1) {
      this.string = var1;
   }

   private DirectoryString(DERT61String var1) {
      this.string = var1;
   }

   private DirectoryString(DERUTF8String var1) {
      this.string = var1;
   }

   private DirectoryString(DERUniversalString var1) {
      this.string = var1;
   }

   public static DirectoryString getInstance(Object var0) {
      DirectoryString var1;
      if(var0 instanceof DirectoryString) {
         var1 = (DirectoryString)var0;
      } else if(var0 instanceof DERT61String) {
         DERT61String var2 = (DERT61String)var0;
         var1 = new DirectoryString(var2);
      } else if(var0 instanceof DERPrintableString) {
         DERPrintableString var3 = (DERPrintableString)var0;
         var1 = new DirectoryString(var3);
      } else if(var0 instanceof DERUniversalString) {
         DERUniversalString var4 = (DERUniversalString)var0;
         var1 = new DirectoryString(var4);
      } else if(var0 instanceof DERUTF8String) {
         DERUTF8String var5 = (DERUTF8String)var0;
         var1 = new DirectoryString(var5);
      } else {
         if(!(var0 instanceof DERBMPString)) {
            StringBuilder var7 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var8 = var0.getClass().getName();
            String var9 = var7.append(var8).toString();
            throw new IllegalArgumentException(var9);
         }

         DERBMPString var6 = (DERBMPString)var0;
         var1 = new DirectoryString(var6);
      }

      return var1;
   }

   public static DirectoryString getInstance(ASN1TaggedObject var0, boolean var1) {
      if(!var1) {
         throw new IllegalArgumentException("choice item must be explicitly tagged");
      } else {
         return getInstance(var0.getObject());
      }
   }

   public String getString() {
      return this.string.getString();
   }

   public DERObject toASN1Object() {
      return ((DEREncodable)this.string).getDERObject();
   }

   public String toString() {
      return this.string.getString();
   }
}
