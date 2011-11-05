package myorg.bouncycastle.asn1;

import java.io.IOException;
import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObjectParser;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROutputStream;

public abstract class ASN1TaggedObject extends ASN1Object implements ASN1TaggedObjectParser {

   boolean empty = 0;
   boolean explicit = 1;
   DEREncodable obj = null;
   int tagNo;


   public ASN1TaggedObject(int var1, DEREncodable var2) {
      this.explicit = (boolean)1;
      this.tagNo = var1;
      this.obj = var2;
   }

   public ASN1TaggedObject(boolean var1, int var2, DEREncodable var3) {
      if(var3 instanceof ASN1Choice) {
         this.explicit = (boolean)1;
      } else {
         this.explicit = var1;
      }

      this.tagNo = var2;
      this.obj = var3;
   }

   public static ASN1TaggedObject getInstance(Object var0) {
      if(var0 != null && !(var0 instanceof ASN1TaggedObject)) {
         StringBuilder var1 = (new StringBuilder()).append("unknown object in getInstance: ");
         String var2 = var0.getClass().getName();
         String var3 = var1.append(var2).toString();
         throw new IllegalArgumentException(var3);
      } else {
         return (ASN1TaggedObject)var0;
      }
   }

   public static ASN1TaggedObject getInstance(ASN1TaggedObject var0, boolean var1) {
      if(var1) {
         return (ASN1TaggedObject)var0.getObject();
      } else {
         throw new IllegalArgumentException("implicitly tagged tagged object");
      }
   }

   boolean asn1Equals(DERObject var1) {
      boolean var2;
      if(!(var1 instanceof ASN1TaggedObject)) {
         var2 = false;
      } else {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var1;
         int var4 = this.tagNo;
         int var5 = var3.tagNo;
         if(var4 == var5) {
            boolean var6 = this.empty;
            boolean var7 = var3.empty;
            if(var6 == var7) {
               boolean var8 = this.explicit;
               boolean var9 = var3.explicit;
               if(var8 == var9) {
                  if(this.obj == null) {
                     if(var3.obj != null) {
                        var2 = false;
                        return var2;
                     }
                  } else {
                     DERObject var10 = this.obj.getDERObject();
                     DERObject var11 = var3.obj.getDERObject();
                     if(!var10.equals(var11)) {
                        var2 = false;
                        return var2;
                     }
                  }

                  var2 = true;
                  return var2;
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   abstract void encode(DEROutputStream var1) throws IOException;

   public DERObject getObject() {
      DERObject var1;
      if(this.obj != null) {
         var1 = this.obj.getDERObject();
      } else {
         var1 = null;
      }

      return var1;
   }

   public DEREncodable getObjectParser(int var1, boolean var2) {
      Object var3;
      switch(var1) {
      case 4:
         var3 = ASN1OctetString.getInstance(this, var2).parser();
         break;
      case 16:
         var3 = ASN1Sequence.getInstance(this, var2).parser();
         break;
      case 17:
         var3 = ASN1Set.getInstance(this, var2).parser();
         break;
      default:
         if(!var2) {
            String var4 = "implicit tagging not implemented for tag: " + var1;
            throw new RuntimeException(var4);
         }

         var3 = this.getObject();
      }

      return (DEREncodable)var3;
   }

   public int getTagNo() {
      return this.tagNo;
   }

   public int hashCode() {
      int var1 = this.tagNo;
      if(this.obj != null) {
         int var2 = this.obj.hashCode();
         var1 ^= var2;
      }

      return var1;
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public boolean isExplicit() {
      return this.explicit;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("[");
      int var2 = this.tagNo;
      StringBuilder var3 = var1.append(var2).append("]");
      DEREncodable var4 = this.obj;
      return var3.append(var4).toString();
   }
}
