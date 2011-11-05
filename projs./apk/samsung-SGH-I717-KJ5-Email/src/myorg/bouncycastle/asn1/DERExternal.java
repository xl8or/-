package myorg.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROutputStream;
import myorg.bouncycastle.asn1.DERTaggedObject;

public class DERExternal extends ASN1Object {

   private ASN1Object dataValueDescriptor;
   private DERObjectIdentifier directReference;
   private int encoding;
   private DERObject externalContent;
   private DERInteger indirectReference;


   public DERExternal(ASN1EncodableVector var1) {
      int var2 = 0;
      DERObject var3 = var1.get(var2).getDERObject();
      if(var3 instanceof DERObjectIdentifier) {
         DERObjectIdentifier var4 = (DERObjectIdentifier)var3;
         this.directReference = var4;
         var2 = 0 + 1;
         var3 = var1.get(var2).getDERObject();
      }

      if(var3 instanceof DERInteger) {
         DERInteger var5 = (DERInteger)var3;
         this.indirectReference = var5;
         ++var2;
         var3 = var1.get(var2).getDERObject();
      }

      if(!(var3 instanceof DERTaggedObject)) {
         ASN1Object var6 = (ASN1Object)var3;
         this.dataValueDescriptor = var6;
         int var7 = var2 + 1;
         var3 = var1.get(var7).getDERObject();
      }

      if(!(var3 instanceof DERTaggedObject)) {
         throw new IllegalArgumentException("No tagged object found in vector. Structure doesn\'t seem to be of type External");
      } else {
         DERTaggedObject var8 = (DERTaggedObject)var3;
         int var9 = var8.getTagNo();
         this.setEncoding(var9);
         DERObject var10 = var8.getObject();
         this.externalContent = var10;
      }
   }

   public DERExternal(DERObjectIdentifier var1, DERInteger var2, ASN1Object var3, int var4, DERObject var5) {
      this.setDirectReference(var1);
      this.setIndirectReference(var2);
      this.setDataValueDescriptor(var3);
      this.setEncoding(var4);
      DERObject var6 = var5.getDERObject();
      this.setExternalContent(var6);
   }

   public DERExternal(DERObjectIdentifier var1, DERInteger var2, ASN1Object var3, DERTaggedObject var4) {
      int var5 = var4.getTagNo();
      DERObject var6 = var4.getDERObject();
      this(var1, var2, var3, var5, var6);
   }

   private void setDataValueDescriptor(ASN1Object var1) {
      this.dataValueDescriptor = var1;
   }

   private void setDirectReference(DERObjectIdentifier var1) {
      this.directReference = var1;
   }

   private void setEncoding(int var1) {
      if(var1 >= 0 && var1 <= 2) {
         this.encoding = var1;
      } else {
         String var2 = "invalid encoding value: " + var1;
         throw new IllegalArgumentException(var2);
      }
   }

   private void setExternalContent(DERObject var1) {
      this.externalContent = var1;
   }

   private void setIndirectReference(DERInteger var1) {
      this.indirectReference = var1;
   }

   boolean asn1Equals(DERObject var1) {
      byte var2;
      if(!(var1 instanceof DERExternal)) {
         var2 = 0;
      } else if(this == var1) {
         var2 = 1;
      } else {
         DERExternal var3 = (DERExternal)var1;
         if(this.directReference != null) {
            label56: {
               if(var3.directReference != null) {
                  DERObjectIdentifier var4 = var3.directReference;
                  DERObjectIdentifier var5 = this.directReference;
                  if(var4.equals(var5)) {
                     break label56;
                  }
               }

               var2 = 0;
               return (boolean)var2;
            }
         }

         if(this.indirectReference != null) {
            label57: {
               if(var3.indirectReference != null) {
                  DERInteger var6 = var3.indirectReference;
                  DERInteger var7 = this.indirectReference;
                  if(var6.equals(var7)) {
                     break label57;
                  }
               }

               var2 = 0;
               return (boolean)var2;
            }
         }

         if(this.dataValueDescriptor != null) {
            label58: {
               if(var3.dataValueDescriptor != null) {
                  ASN1Object var8 = var3.dataValueDescriptor;
                  ASN1Object var9 = this.dataValueDescriptor;
                  if(var8.equals(var9)) {
                     break label58;
                  }
               }

               var2 = 0;
               return (boolean)var2;
            }
         }

         DERObject var10 = this.externalContent;
         DERObject var11 = var3.externalContent;
         var2 = var10.equals(var11);
      }

      return (boolean)var2;
   }

   void encode(DEROutputStream var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      if(this.directReference != null) {
         byte[] var3 = this.directReference.getDEREncoded();
         var2.write(var3);
      }

      if(this.indirectReference != null) {
         byte[] var4 = this.indirectReference.getDEREncoded();
         var2.write(var4);
      }

      if(this.dataValueDescriptor != null) {
         byte[] var5 = this.dataValueDescriptor.getDEREncoded();
         var2.write(var5);
      }

      int var6 = this.encoding;
      DERObject var7 = this.externalContent;
      byte[] var8 = (new DERTaggedObject(var6, var7)).getDEREncoded();
      var2.write(var8);
      byte[] var9 = var2.toByteArray();
      var1.writeEncoded(32, 8, var9);
   }

   public ASN1Object getDataValueDescriptor() {
      return this.dataValueDescriptor;
   }

   public DERObjectIdentifier getDirectReference() {
      return this.directReference;
   }

   public int getEncoding() {
      return this.encoding;
   }

   public DERObject getExternalContent() {
      return this.externalContent;
   }

   public DERInteger getIndirectReference() {
      return this.indirectReference;
   }

   public int hashCode() {
      int var1 = 0;
      if(this.directReference != null) {
         var1 = this.directReference.hashCode();
      }

      if(this.indirectReference != null) {
         int var2 = this.indirectReference.hashCode();
         var1 ^= var2;
      }

      if(this.dataValueDescriptor != null) {
         int var3 = this.dataValueDescriptor.hashCode();
         var1 ^= var3;
      }

      int var4 = this.externalContent.hashCode();
      return var1 ^ var4;
   }
}
